package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.*;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.MajorBuildMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专业建设考核服务实现类
 *
 * @author
 * @Date 2020-08-19 16:34:21
 */
@Service
public class MajorBuildServiceImpl extends ServiceImpl<MajorBuildMapper, MajorBuild> implements IMajorBuildService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IMajorBuildMemberService majorBuildMemberService;

    @Override
    @Transactional
    public void applyApproval(MajorBuild majorBuild) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User dean = userService.selectOne(wrapper);

        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_MAJOR_BUILD_HR + "");
        User majorHandle = userService.selectOne(wrapper);

        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrHandle = userService.selectOne(wrapper);

        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEAN_OFFICE_LEADER + "");
        User deanOfficeLeader = userService.selectOne(wrapper);

        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrLeader = userService.selectOne(wrapper);

        Map<String, Object> vars = new HashMap<>();
        vars.put("principal_user", ShiroKit.getUser().id);
        vars.put("dean_user", dean.getId());
        vars.put("major_handle", majorHandle.getId());
        vars.put("deans_office_leader", deanOfficeLeader.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("hr_leader", hrLeader.getId());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_MAJOR_BUILD_APPROVAL[0], ActUtils.PD_TASK_MAJOR_BUILD_APPROVAL[1], majorBuild.getName()+" 专业建设立项", vars);


        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(majorBuild.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_ZYJS);
        mainNorm = assessNormService.getByCode(mainNorm);
        if (mainNorm == null) {
            throw new GunsException("考核指标不存在");
        }
        majorBuild.setNormId(mainNorm.getId());

        majorBuild.setProcInsId(procInsId);
        majorBuild.setPrincipalId(ShiroKit.getUser().id);
        majorBuild.setDeanUserId(dean.getId());
        majorBuild.setDeanOfficeLeaderId(deanOfficeLeader.getId());
        majorBuild.setHrHandleId(hrHandle.getId());
        majorBuild.setHrLeaderId(hrLeader.getId());
        majorBuild.setMajorCommissioner(majorHandle.getId());
        majorBuild.insert();
    }

    @Override
    @Transactional
    public void auditApproval(MajorBuild majorBuild) {
        String pass = (String) majorBuild.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【拒绝】");
        if (majorBuild.getExpand().get("comment") != null) {
            comment.append(majorBuild.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            if (majorBuild.getAct().getTaskDefKey().equals("principal_audit")) {
                AssessNorm assessNorm = assessNormService.selectById(majorBuild.getNormId());

                User principalUser = userService.selectById(majorBuild.getPrincipalId());
                AssessNorm collegeNorm = new AssessNorm();
                collegeNorm.setDeptId(principalUser.getDeptId());
                collegeNorm.setCode(assessNorm.getCode());
                collegeNorm.setType(IAssessCoefficientService.TYPE_ZYJS);
                collegeNorm = assessNormService.getByCode(collegeNorm);
                //考核系数
                AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_ZYJS);

                String dataJson = (String) majorBuild.getExpand().get("data");
                List<Map> majorBuildMembers = JSON.parseArray(dataJson, Map.class);
                List<MajorBuildMember> majorBuildMemberList = new ArrayList<>();
                if (CollUtil.isNotEmpty(majorBuildMembers)) {
                    double sum = 0;
                    for (Map majorBuildMember : majorBuildMembers) {
                        String account = (String) majorBuildMember.get("userNo");
                        if (StrUtil.isBlank(account)) {
                            throw new GunsException("职工编号不能为空");
                        }
                        User user = userService.getByAccount(account);
                        if (user == null) {
                            throw new GunsException(StrUtil.format("职工编号 {} 不存在", account));
                        }
                        double point = Double.parseDouble(majorBuildMember.get("mainPoint") + "");
                        sum += point;
                        MajorBuildMember member = new MajorBuildMember();
                        member.setUserId(user.getId());
                        member.setBuildId(majorBuild.getId());
                        member.setMainNormPoint(point);
                        member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                        member.setCoePoint(coefficient.getCoefficient());
                        majorBuildMemberList.add(member);
//                        member.set
                    }
                    if (sum > assessNorm.getPoint() / 2) {
                        throw new GunsException("总分数不能高于" + assessNorm.getPoint() / 2);
                    }
                    MajorBuildMember member = new MajorBuildMember();
                    member.setUserId(principalUser.getId());
                    member.setBuildId(majorBuild.getId());
                    member.setMainNormPoint(assessNorm.getPoint() / 2 - sum);
                    member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                    member.setCoePoint(coefficient.getCoefficient());
                    majorBuildMemberList.add(member);
                } else {
                    throw new GunsException("请添加项目组成员");
                }
                majorBuildMemberService.insertBatch(majorBuildMemberList);
            }
        } else if (pass.equals(YesNo.NO.getCode() + "")){
            switch (majorBuild.getAct().getTaskDefKey()) {
                case "dean_audit":
                case "major_build_handle_audit":
                case "deans_office_leader_audit":
                    majorBuild.deleteById();break;
            }
        }
        actTaskService.complete(majorBuild.getAct().getTaskId(), majorBuild.getAct().getProcInsId(), comment.toString(), vars);
    }
}