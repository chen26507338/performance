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
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.model.AllocationPointLog;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.SxjxAssessMapper;
import com.stylefeng.guns.modular.assess.service.ISxjxAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实训绩效考核服务实现类
 *
 * @author
 * @Date 2020-10-10 11:53:51
 */
@Service
public class SxjxAssessServiceImpl extends ServiceImpl<SxjxAssessMapper, SxjxAssess> implements ISxjxAssessService {

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
    public void doAllocation(SxjxAssess sxjxAssess) {
        String dataJson = (String) sxjxAssess.getExpand().get("data");
        List<Map> maps = JSON.parseArray(dataJson, Map.class);

        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        AllocationPointLog allocationPointLog = new AllocationPointLog();
        allocationPointLog.setDeptId(ShiroKit.getUser().deptId);
        allocationPointLog.setType(IAssessCoefficientService.TYPE_SXJX);
        allocationPointLog.setYear(currentYears.getOtherValue());
        if (allocationPointLog.selectCount(new EntityWrapper<>(allocationPointLog)) > 0) {
            throw new GunsException("当前年度已分配");
        }

        //考核系数
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_SXJX);

        double maxPoint = (double) sxjxAssess.getExpand().get("maxPoint");
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
                assessNormPoint.setSxjxMain(mainPoint);
//                Double collegePoint = assessNormPoint.getZyjsCollege();
//                collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
//                assessNormPoint.setXsgzCollege(collegePoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = point * coefficient.getCoefficient();
                assessNormPoint.setSxjxMain(mainPoint);
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

    @Override
    @Transactional
    public void apply(SxjxAssess sxjxAssess) {

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
        wrapper.eq("dept_id", sxjxAssess.getDeptId());
        User dean = userService.selectOne(wrapper);

        //人事经办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrHandle = userService.selectOne(wrapper);

        //人事领导
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrLeader = userService.selectOne(wrapper);

        Map<String, Object> vars = new HashMap<>();
        vars.put("hr_leader", hrLeader.getId());
        vars.put("dean", dean.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("commissioner", ShiroKit.getUser().id);
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_SXJX_ASSESS[0], ActUtils.PD_TASK_SXJX_ASSESS[1], "实训室工作量工作", vars);

        sxjxAssess.setProcInsId(procInsId);
        sxjxAssess.setDeanId(dean.getId());
        sxjxAssess.setHrHandleId(hrHandle.getId());
        sxjxAssess.setHrLeaderId(hrLeader.getId());
        sxjxAssess.setSxjxCommissioner(ShiroKit.getUser().id);
        sxjxAssess.insert();
    }

    @Override
    @Transactional
    public void audit(SxjxAssess sxjxAssess) {
        String pass = (String) sxjxAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (sxjxAssess.getExpand().get("comment") != null) {
            comment.append(sxjxAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (sxjxAssess.getAct().getTaskDefKey()) {
                case "dean_audit":
                    if (sxjxAssess.getSxsglUser() == null) {
                        throw new GunsException("请选择实训管理人员");
                    }
                    vars.put("glry",sxjxAssess.getSxsglUser());
                    break;
                case "glry_audit":
                    if (StrUtil.isBlank(sxjxAssess.getResult())) {
                        throw new GunsException("请填写整改结果");
                    }
                    break;
                case "commissioner_audit":
                    if (sxjxAssess.getMainNormPoint() == null) {
                        throw new GunsException("请填写考核分");
                    }
                    break;
                case "hr_leader_audit":
                    sxjxAssess.setStatus(YesNo.YES.getCode());
            }
            sxjxAssess.updateById();
        }
        actTaskService.complete(sxjxAssess.getAct().getTaskId(), sxjxAssess.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void importAssess(SxjxAssess sxjxAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + sxjxAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "content");
        reader.addHeaderAlias("考核结果", "result");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("中心名称", "zxmc");
        reader.addHeaderAlias("教师工号", "account");
        reader.addHeaderAlias("积分归属年份", "year");
        List<SxjxAssess> normalAssesses = reader.readAll(SxjxAssess.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_SXJX);
        for (SxjxAssess assess : normalAssesses) {
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }
            assess.setUserId(user.getId());
            assess.setStatus(YesNo.YES.getCode());
            assess.setCoePoint(coefficient.getCoefficient());

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getSxjxMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setSxjxMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setSxjxMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }
}