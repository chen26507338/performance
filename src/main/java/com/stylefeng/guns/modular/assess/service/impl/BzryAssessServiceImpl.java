package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.dao.BzryAssessMapper;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.BzryAssess;
import com.stylefeng.guns.modular.assess.model.BzryAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IBzryAssessService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表彰荣誉考核服务实现类
 *
 * @author
 * @Date 2020-10-04 12:11:14
 */
@Service
public class BzryAssessServiceImpl extends ServiceImpl<BzryAssessMapper, BzryAssess> implements IBzryAssessService {
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;

    @Override
    @Transactional
    public void apply(BzryAssess entity) {
        List<Map> assesses;
        if (entity.getExpand().get("fileName") != null) {
            ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + entity.getExpand().get("fileName"));
            reader.addHeaderAlias("分院名称", "deptName");
            reader.addHeaderAlias("考核项目", "assessName");
            reader.addHeaderAlias("考核指标代码", "normCode");
            reader.addHeaderAlias("考核结果", "result");
            reader.addHeaderAlias("职工姓名", "userName");
            reader.addHeaderAlias("职工代码", "account");
            assesses = reader.readAll(Map.class);
        } else {
            assesses = JSON.parseArray((String) entity.getExpand().get("data"), Map.class);
        }
        this.insertBatch(handleMap(assesses, true));
    }
    private List<BzryAssess> handleMap(List<Map> normalAssesses, boolean isImport) {
        User hrHandle = null;
        User hrLeader = null;
        String procInsId = null;

        if (isImport) {
            Map<String, Object> vars = new HashMap<>();

            //人事经办
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrHandle = userService.selectOne(wrapper);

            //人事领导
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrLeader = userService.selectOne(wrapper);


            vars.put("hr_leader", hrLeader.getId());
            vars.put("hr_handle", hrHandle.getId());
            vars.put("commissioner", ShiroKit.getUser().id);
            procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_BZRY_ASSESS[0], ActUtils.PD_TASK_BZRY_ASSESS[1], "表彰荣誉考核", vars);
        }
        List<BzryAssess> datas = new ArrayList<>();
        for (Map excelMap : normalAssesses) {
            BzryAssess assess = new BzryAssess();

            String account = (String) excelMap.get("account");
            if (StrUtil.isBlank(account)) {
                throw new GunsException("职工编号不能为空");
            }

            User user = userService.getByAccount(account);
            if (user == null) {
                throw new GunsException("职工不存在");
            }

            if (isImport) {
                assess.setUserId(user.getId());
                assess.setLevel((String) excelMap.get("level"));
                assess.setType((String) excelMap.get("type"));
                assess.setHrHandleId(hrHandle.getId());
                assess.setHrLeaderId(hrLeader.getId());
                assess.setCommissionerId(ShiroKit.getUser().id);
                assess.setProcInsId(procInsId);
            }

            String id = (String) excelMap.get("id");
            if (StrUtil.isNotBlank(id)) {
                assess.setId(Long.valueOf(id));
            }
            datas.add(assess);
        }
        return datas;
    }

    @Override
    @Transactional
    public void audit(BzryAssess bzryAssess) {
        String pass = (String) bzryAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (bzryAssess.getExpand().get("comment") != null) {
            comment.append(bzryAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        if (pass.equals(YesNo.YES.getCode() + "")) {

            String dataJson = (String) bzryAssess.getExpand().get("data");
            List<Map> maps = JSON.parseArray(dataJson, Map.class);
            if (CollUtil.isNotEmpty(maps)) {
                List<BzryAssess> updates = new ArrayList<>();
                for (Map map : maps) {
                    BzryAssess temp = new BzryAssess();
                    temp.setId(Long.parseLong(map.get("id").toString()));
                    temp.setMainNormPoint(Double.parseDouble(map.get("mainNormPoint").toString()));
                    temp.setLevel((String) map.get("level"));
                    temp.setType((String) map.get("type"));
                    updates.add(temp);
                }
                this.updateBatchById(updates);
            }

            AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_BZRY);
            EntityWrapper<BzryAssess> wrapper = new EntityWrapper<>();
            wrapper.eq("proc_ins_id", bzryAssess.getAct().getProcInsId());
            BzryAssess update = new BzryAssess();
            if ("hr_leader_audit".equals(bzryAssess.getAct().getTaskDefKey())) {
                List<BzryAssess> assessList = this.selectList(wrapper);
                for (BzryAssess assess : assessList) {

                    AssessNormPoint assessNormPoint = new AssessNormPoint();
                    assessNormPoint.setUserId(assess.getUserId());
                    assessNormPoint.setYear(assess.getYear());
                    assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                    if (assessNormPoint != null) {
                        Double mainPoint = assessNormPoint.getBzryMain();
                        mainPoint += assess.getMainNormPoint()  * assessCoefficient.getCoefficient();
                        assessNormPoint.setBzryMain(mainPoint);
//                        Double collegePoint = assessNormPoint.getJxgzCollege();
//                        collegePoint += (1 + assess.getCollegeNormPoint()) * mainPoint;
//                        assessNormPoint.setJxgzCollege(collegePoint);
                    } else {
                        assessNormPoint = new AssessNormPoint();
                        double mainPoint = assess.getMainNormPoint()  * assessCoefficient.getCoefficient();
                        assessNormPoint.setBzryMain(mainPoint);
//                        assessNormPoint.setJxgzCollege(mainPoint * (1 + assess.getCollegeNormPoint()));
                    }
                    assessNormPoint.setYear(assess.getYear());
//                    assessNormPoint.setDeptId(assess.getDeptId());
                    assessNormPoint.setUserId(assess.getUserId());
                    assessNormPointService.insertOrUpdate(assessNormPoint);
                }

                //计算考核分入库
                update.setCoePoint(assessCoefficient.getCoefficient());
                update.setStatus(YesNo.YES.getCode());
                this.update(update, wrapper);
            } else if ("hr_handle_audit".equals(bzryAssess.getAct().getTaskDefKey())) {
                //人事经办审核
                if (StrUtil.isBlank(bzryAssess.getYear())) {
                    throw new GunsException("请设置年度");
                }
                update.setYear(bzryAssess.getYear());
                this.update(update, wrapper);
            }
        }
        actTaskService.complete(bzryAssess.getAct().getTaskId(), bzryAssess.getAct().getProcInsId(), comment.toString(), vars);
    }
}