package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.dao.TeachingLoadMapper;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.*;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.TeachingLoadAssessMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教学考核服务实现类
 *
 * @author
 * @Date 2020-09-20 15:34:09
 */
@Service
public class TeachingLoadAssessServiceImpl extends ServiceImpl<TeachingLoadAssessMapper, TeachingLoadAssess> implements ITeachingLoadAssessService {

    @Resource
    private TeachingLoadMapper teachingLoadMapper;
    @Autowired
    private IOtherInfoService otherInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;

    @Autowired
    private IAssessNormPointService assessNormPointService;

    @Override
    @Transactional
    public void apply(TeachingLoadAssess teachingLoadAssess) {
        //当前年度
        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        TeachingLoad params = new TeachingLoad();
        params.setDeptId(ShiroKit.getUser().deptId);
        params.setYear(currentYears.getOtherValue());
        List<TeachingLoad> teachingLoads = teachingLoadMapper.selectGroup(params);

        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setType(IAssessCoefficientService.TYPE_JXGZ);
        mainNorm = assessNormService.getByCode(mainNorm);
        if (mainNorm == null) {
            throw new GunsException("校级考核指标不存在");
        }

        AssessNorm collegeNorm = new AssessNorm();
        collegeNorm.setDeptId(ShiroKit.getUser().deptId);
        collegeNorm.setType(IAssessCoefficientService.TYPE_JXGZ);
        collegeNorm = assessNormService.getByCode(collegeNorm);
        if (collegeNorm == null) {
            throw new GunsException("院级考核指标不存在");
        }


        EntityWrapper<User> wrapper = new EntityWrapper<>();
        //院长
        wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User dean = userService.selectOne(wrapper);

        //考核专员
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_TEACHING_HR + "");
        User teachingHr = userService.selectOne(wrapper);

        //人事经办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrHandle = userService.selectOne(wrapper);

        //教务处处长
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEAN_OFFICE_LEADER + "");
        User deanOfficeLeader = userService.selectOne(wrapper);

        Map<String, Object> vars = new HashMap<>();
        vars.put("dept_teaching", ShiroKit.getUser().id);
        vars.put("dean_user", dean.getId());
        vars.put("deans_office_leader", deanOfficeLeader.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("dean_office_commissioner", teachingHr.getId());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_TEACHING_LOAD_ASSESS[0], ActUtils.PD_TASK_TEACHING_LOAD_ASSESS[1], "教学工作考核", vars);

        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_XSGZ);

        List<TeachingLoadAssess> teachingLoadAssesses = new ArrayList<>();
        for (TeachingLoad teachingLoad : teachingLoads) {
            TeachingLoadAssess data = new TeachingLoadAssess();
            data.setCoePoint(coefficient.getCoefficient());
            data.setDeptId(ShiroKit.getUser().deptId);
            data.setDeanUserId(dean.getId());
            data.setDeanOfficeLeaderId(deanOfficeLeader.getId());
            data.setDeanOfficeCommissioner(teachingHr.getId());
            data.setHrHandleId(hrHandle.getId());
            data.setProcInsId(procInsId);
            data.setResult(teachingLoad.getCourseTimes());
            data.setYear(currentYears.getOtherValue());
            data.setNormId(collegeNorm.getId());
            data.setUserId(teachingLoad.getUserId());
            data.setMainNormPoint(mainNorm.getPoint());
            data.setCollegeNormPoint(collegeNorm.getPoint());
            teachingLoadAssesses.add(data);
        }
        this.insertBatch(teachingLoadAssesses);

        TeachingLoad newTL = new TeachingLoad();
        newTL.setStatus(ITeachingLoadService.STATUS_IN_AUDIT);
        teachingLoadMapper.update(newTL, new EntityWrapper<>(params));
    }

    @Override
    @Transactional
    public void audit(TeachingLoadAssess teachingLoadAssess) {
        String pass = (String) teachingLoadAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (teachingLoadAssess.getExpand().get("comment") != null) {
            comment.append(teachingLoadAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        if (pass.equals(YesNo.YES.getCode() + "")) {

            String dataJson = (String) teachingLoadAssess.getExpand().get("data");
            List<Map> maps = JSON.parseArray(dataJson, Map.class);
            if (CollUtil.isNotEmpty(maps)) {
                List<TeachingLoadAssess> updates = new ArrayList<>();
                for (Map map : maps) {
                    TeachingLoadAssess temp = new TeachingLoadAssess();
                    temp.setId(Long.parseLong(map.get("id").toString()));
                    temp.setResult(Integer.parseInt(map.get("result").toString()));
                    updates.add(temp);
                }
                this.updateBatchById(updates);
            }

            EntityWrapper<TeachingLoadAssess> wrapper = new EntityWrapper<>();
            wrapper.eq("proc_ins_id", teachingLoadAssess.getAct().getProcInsId());
            TeachingLoadAssess update = new TeachingLoadAssess();
            if ("hr_handle_audit".equals(teachingLoadAssess.getAct().getTaskDefKey())) {//计算考核分入库
                TeachingLoadAssess assessParams = new TeachingLoadAssess();
                assessParams.setProcInsId(teachingLoadAssess.getProcInsId());
                List<TeachingLoadAssess> assessList = this.selectList(new EntityWrapper<>(assessParams));
                for (TeachingLoadAssess assess : assessList) {
                    AssessNormPoint assessNormPoint = new AssessNormPoint();
                    assessNormPoint.setUserId(assess.getUserId());
                    assessNormPoint.setYear(assess.getYear());
                    assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                    AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_JXGZ);
                    if (assessNormPoint != null) {
                        Double mainPoint = assessNormPoint.getJxgzMain();
                        mainPoint += assess.getMainNormPoint() * assess.getResult() * assessCoefficient.getCoefficient();
                        assessNormPoint.setJxgzMain(mainPoint);
                        Double collegePoint = assessNormPoint.getJxgzCollege();
                        collegePoint += (1 + assess.getCollegeNormPoint()) * mainPoint;
                        assessNormPoint.setJxgzCollege(collegePoint);
                    } else {
                        assessNormPoint = new AssessNormPoint();
                        double mainPoint = assess.getMainNormPoint() * assess.getResult() * assessCoefficient.getCoefficient();
                        assessNormPoint.setJxgzMain(mainPoint);
                        assessNormPoint.setJxgzCollege(mainPoint * (1 + assess.getCollegeNormPoint()));
                    }
                    assessNormPoint.setYear(assess.getYear());
                    assessNormPoint.setDeptId(assess.getDeptId());
                    assessNormPoint.setUserId(assess.getUserId());
                    assessNormPointService.insertOrUpdate(assessNormPoint);
                }
                update.setStatus(YesNo.YES.getCode());
                this.update(update, wrapper);

                wrapper.last("1");
                TeachingLoadAssess one = this.selectOne(wrapper);
                TeachingLoad tlParams = new TeachingLoad();
                tlParams.setYear(one.getYear());
                tlParams.setDeptId(one.getDeptId());
                TeachingLoad tlNew = new TeachingLoad();
                tlNew.setStatus(ITeachingLoadService.STATUS_PASS);
                teachingLoadMapper.update(tlNew, new EntityWrapper<>(tlParams));
            }
        }
        actTaskService.complete(teachingLoadAssess.getAct().getTaskId(), teachingLoadAssess.getAct().getProcInsId(), comment.toString(), vars);
    }
}