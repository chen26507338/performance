package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.*;
import com.stylefeng.guns.modular.job.model.AllocationPointLog;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.DeptAssess;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.DzbWorkAssessMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 党支部工作考核服务实现类
 *
 * @author
 * @Date 2020-09-24 13:06:50
 */
@Service
public class DzbWorkAssessServiceImpl extends ServiceImpl<DzbWorkAssessMapper, DzbWorkAssess> implements IDzbWorkAssessService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IOtherInfoService otherInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;

    @Override
    @Transactional
    public void apply(DzbWorkAssess entity) {
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

    private List<DzbWorkAssess> handleMap(List<Map> normalAssesses, boolean isImport) {
        User zzbLeader = null;
        User hrHandle = null;
        User hrLeader = null;
        String procInsId = null;

        if (isImport) {
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", IDeptService.ZZB);
            zzbLeader = userService.selectOne(wrapper);

            //人事经办
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrHandle = userService.selectOne(wrapper);

            //人事领导
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrLeader = userService.selectOne(wrapper);

            Map<String, Object> vars = new HashMap<>();
            vars.put("hr_leader", hrLeader.getId());
            vars.put("zzb_leader", zzbLeader.getId());
            vars.put("hr_handle", hrHandle.getId());
            vars.put("dzb_commissioner", ShiroKit.getUser().id);
            procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_DZB_WORK_ASSESS[0], ActUtils.PD_TASK_DZB_WORK_ASSESS[1], "党支部工作考核", vars);
        }
        List<DzbWorkAssess> datas = new ArrayList<>();
        for (Map excelMap : normalAssesses) {
            DzbWorkAssess assess = new DzbWorkAssess();

            if (isImport) {
                assess.setDeptId(Long.parseLong(excelMap.get("deptId").toString()));
                assess.setHrHandleId(hrHandle.getId());
                assess.setHrLeaderId(hrLeader.getId());
                assess.setZzbLeaderId(zzbLeader.getId());
                assess.setDzbCommissioner(ShiroKit.getUser().id);
                assess.setProcInsId(procInsId);
            }

            String normCode = (String) excelMap.get("normCode");
            if (StrUtil.isNotBlank(normCode)) {
                //校级标准分
                AssessNorm mainNorm = new AssessNorm();
                mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                mainNorm.setCode(normCode);
                mainNorm.setType(IAssessCoefficientService.TYPE_DZBGZ);
                mainNorm = assessNormService.getByCode(mainNorm);
                if (mainNorm == null) {
                    throw new GunsException("考核指标不存在");
                }
                assess.setMainNormPoint(mainNorm.getPoint());
                assess.setNormId(mainNorm.getId());
                //院级浮动值
//                AssessNorm collegeNorm = new AssessNorm();
//                collegeNorm.setDeptId(employee.getDeptId());
//                collegeNorm.setCode(normCode);
//                collegeNorm.setType(IAssessCoefficientService.TYPE_DZBGZ);
//                collegeNorm = assessNormService.getByCode(collegeNorm);
//                assess.setCollegeNormPoint(collegeNorm.getPoint());
//                assess.setProcInsId(procInsId);
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
    public void audit(DzbWorkAssess dzbWorkAssess) {
        String pass = (String) dzbWorkAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (dzbWorkAssess.getExpand().get("comment") != null) {
            comment.append(dzbWorkAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        if (pass.equals(YesNo.YES.getCode() + "")) {

            String dataJson = (String) dzbWorkAssess.getExpand().get("data");
            List<Map> maps = JSON.parseArray(dataJson, Map.class);
            if (CollUtil.isNotEmpty(maps)) {
                List<DzbWorkAssess> updates = new ArrayList<>();
                for (Map map : maps) {
                    DzbWorkAssess temp = new DzbWorkAssess();
                    temp.setId(Long.parseLong(map.get("id").toString()));
                    AssessNorm mainNorm = new AssessNorm();
                    mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                    mainNorm.setCode(map.get("normCode").toString());
                    mainNorm.setType(IAssessCoefficientService.TYPE_DZBGZ);
                    mainNorm = assessNormService.getByCode(mainNorm);
                    if (mainNorm == null) {
                        throw new GunsException("考核指标不存在");
                    }
                    temp.setMainNormPoint(mainNorm.getPoint());
                    temp.setNormId(mainNorm.getId());
                    updates.add(temp);
                }
                this.updateBatchById(updates);
            }

            EntityWrapper<DzbWorkAssess> wrapper = new EntityWrapper<>();
            wrapper.eq("proc_ins_id", dzbWorkAssess.getAct().getProcInsId());
            DzbWorkAssess update = new DzbWorkAssess();
            if ("hr_leader_audit".equals(dzbWorkAssess.getAct().getTaskDefKey())) {
                //计算考核分入库
                update.setStatus(YesNo.YES.getCode());
                this.update(update, wrapper);
            } else if ("hr_handle_audit".equals(dzbWorkAssess.getAct().getTaskDefKey())) {
                //人事经办审核
                if (StrUtil.isBlank(dzbWorkAssess.getYear())) {
                    throw new GunsException("请设置年度");
                }
                update.setYear(dzbWorkAssess.getYear());
                this.update(update, wrapper);
            }
        }
        actTaskService.complete(dzbWorkAssess.getAct().getTaskId(), dzbWorkAssess.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void doAllocation(DzbWorkAssess dzbWorkAssess) {
        String dataJson = (String) dzbWorkAssess.getExpand().get("data");
        List<Map> maps = JSON.parseArray(dataJson, Map.class);

        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        AllocationPointLog allocationPointLog = new AllocationPointLog();
        allocationPointLog.setDeptId(ShiroKit.getUser().deptId);
        allocationPointLog.setType(IAssessCoefficientService.TYPE_DZBGZ);
        allocationPointLog.setYear(currentYears.getOtherValue());
        if (allocationPointLog.selectCount(new EntityWrapper<>(allocationPointLog)) > 0) {
            throw new GunsException("当前年度已分配");
        }

        //考核系数
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_DZBGZ);

        double maxPoint = (double) dzbWorkAssess.getExpand().get("maxPoint");
        double currentPoint = 0;
        for (Map map : maps) {
            double point = Double.parseDouble(map.get("point") + "");
            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(Long.parseLong(map.get("userId").toString()));
            assessNormPoint.setYear(currentYears.getOtherValue());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getKygzMain();
                mainPoint += point * coefficient.getCoefficient();
                assessNormPoint.setDzbgzMain(mainPoint);
//                Double collegePoint = assessNormPoint.getZyjsCollege();
//                collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
//                assessNormPoint.setXsgzCollege(collegePoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = point * coefficient.getCoefficient();
                assessNormPoint.setDzbgzMain(mainPoint);
//                assessNormPoint.setXsgzCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                assessNormPoint.setYear(currentYears.getOtherValue());
                assessNormPoint.setUserId(Long.parseLong(map.get("userId").toString()));
            }
            currentPoint += point;
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }

        if (currentPoint > maxPoint) {
            throw new GunsException(StrUtil.format("最多只能分配 {}", maxPoint));
        }

        allocationPointLog.insert();
    }
}