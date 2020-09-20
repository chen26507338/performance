package com.stylefeng.guns.modular.assess.service.impl;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.dao.TeachingLoadMapper;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.TeachingLoad;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.TeachingLoadAssess;
import com.stylefeng.guns.modular.assess.dao.TeachingLoadAssessMapper;
import com.stylefeng.guns.modular.assess.service.ITeachingLoadAssessService;
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
            throw new GunsException("考核指标不存在");
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
        vars.put("user", ShiroKit.getUser().id);
        vars.put("dean_user", dean.getId());
        vars.put("deans_office_leader_audit", deanOfficeLeader.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("dean_office_commissioner_audit", teachingHr.getId());
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
            data.setNormId(mainNorm.getId());
            teachingLoadAssesses.add(data);
        }
        this.insertBatch(teachingLoadAssesses);
    }

    @Override
    @Transactional
    public void audit(TeachingLoadAssess teachingLoadAssess) {

    }
}