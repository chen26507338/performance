package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.model.SzgzAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.SzgzAssessMapper;
import com.stylefeng.guns.modular.assess.service.ISzgzAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思政工作服务实现类
 *
 * @author
 * @Date 2020-09-30 13:31:44
 */
@Service
public class SzgzAssessServiceImpl extends ServiceImpl<SzgzAssessMapper, SzgzAssess> implements ISzgzAssessService {
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
    public void apply(SzgzAssess entity) {
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

        this.insertBatch(handleMap(assesses, entity.getType(), true));
    }

    private List<SzgzAssess> handleMap(List<Map> normalAssesses, String type, boolean isImport) {
        User xscLeader = null;
        User myDean = null;
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

            wrapper = new EntityWrapper<>();
            if (type.equalsIgnoreCase("fdyszgz")) {
                wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
                wrapper.eq("dept_id", IDeptService.XSC);
                xscLeader = userService.selectOne(wrapper);
                vars.put("stu_office_leader", xscLeader.getId());
            } else {
                wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
                wrapper.eq("dept_id", IDeptService.MY);
                myDean = userService.selectOne(wrapper);
                vars.put("dean_user", myDean.getId());
            }

            vars.put("hr_leader", hrLeader.getId());
            vars.put("hr_handle", hrHandle.getId());
            vars.put("commissioner", ShiroKit.getUser().id);
            String[] act = type.equalsIgnoreCase("fdyszgz") ? ActUtils.PD_TASK_FDYSZGZ_ASSESS : ActUtils.PD_TASK_SZJSSZGZ_ASSESS;
            procInsId = actTaskService.startProcessOnly(act[0], act[1], "思政工作考核", vars);
        }
        List<SzgzAssess> datas = new ArrayList<>();
        for (Map excelMap : normalAssesses) {
            SzgzAssess assess = new SzgzAssess();

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
                assess.setResult((String) excelMap.get("result"));
                assess.setContent((String) excelMap.get("content"));
                assess.setHrHandleId(hrHandle.getId());
                assess.setHrLeaderId(hrLeader.getId());
                if (type.equalsIgnoreCase("fdyszgz")) {
                    assess.setStudentsOfficeLeaderId(xscLeader.getId());
                }else {
                    assess.setDeanId(myDean.getId());
                }
                assess.setCommissionerId(ShiroKit.getUser().id);
                assess.setType(type);
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
    public void audit(SzgzAssess szgzAssess) {
        String pass = (String) szgzAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (szgzAssess.getExpand().get("comment") != null) {
            comment.append(szgzAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        if (pass.equals(YesNo.YES.getCode() + "")) {

            String dataJson = (String) szgzAssess.getExpand().get("data");
            List<Map> maps = JSON.parseArray(dataJson, Map.class);
            if (CollUtil.isNotEmpty(maps)) {
                List<SzgzAssess> updates = new ArrayList<>();
                for (Map map : maps) {
                    SzgzAssess temp = new SzgzAssess();
                    temp.setId(Long.parseLong(map.get("id").toString()));
                    temp.setMainNormPoint(Double.parseDouble(map.get("mainNormPoint").toString()));
                    temp.setContent((String) map.get("content"));
                    temp.setResult((String) map.get("result"));
                    updates.add(temp);
                }
                this.updateBatchById(updates);
            }

            EntityWrapper<SzgzAssess> wrapper = new EntityWrapper<>();
            wrapper.eq("proc_ins_id", szgzAssess.getAct().getProcInsId());
            SzgzAssess update = new SzgzAssess();
            if ("hr_leader_audit".equals(szgzAssess.getAct().getTaskDefKey())) {
                AssessCoefficient assessCoefficient = null;
                List<SzgzAssess> assessList = this.selectList(wrapper);
                for (SzgzAssess assess : assessList) {
                    if (assessCoefficient == null) {
                        assessCoefficient = assessCoefficientService.selectById(assess.getType().equals("fdyszgz") ?
                                IAssessCoefficientService.TYPE_FDYSZGZ : IAssessCoefficientService.TYPE_SZJSSZGZ);
                    }

                    AssessNormPoint assessNormPoint = new AssessNormPoint();
                    assessNormPoint.setUserId(assess.getUserId());
                    assessNormPoint.setYear(assess.getYear());
                    assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                    if (assessNormPoint != null) {
                        Double mainPoint = assessNormPoint.getJxgzMain();
                        mainPoint += assess.getMainNormPoint()  * assessCoefficient.getCoefficient();
                        if (assess.getType().equals("fdyszgz")) {
                            assessNormPoint.setFdyszgzMain(mainPoint);
                        } else {
                            assessNormPoint.setSzjsszgzMain(mainPoint);
                        }
//                        Double collegePoint = assessNormPoint.getJxgzCollege();
//                        collegePoint += (1 + assess.getCollegeNormPoint()) * mainPoint;
//                        assessNormPoint.setJxgzCollege(collegePoint);
                    } else {
                        assessNormPoint = new AssessNormPoint();
                        double mainPoint = assess.getMainNormPoint()  * assessCoefficient.getCoefficient();
                        if (assess.getType().equals("fdyszgz")) {
                            assessNormPoint.setFdyszgzMain(mainPoint);
                        } else {
                            assessNormPoint.setSzjsszgzMain(mainPoint);
                        }
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

            } else if ("hr_handle_audit".equals(szgzAssess.getAct().getTaskDefKey())) {
                //人事经办审核
                if (StrUtil.isBlank(szgzAssess.getYear())) {
                    throw new GunsException("请设置年度");
                }
                update.setYear(szgzAssess.getYear());
                this.update(update, wrapper);
            }
        }
        actTaskService.complete(szgzAssess.getAct().getTaskId(), szgzAssess.getAct().getProcInsId(), comment.toString(), vars);
    }
}