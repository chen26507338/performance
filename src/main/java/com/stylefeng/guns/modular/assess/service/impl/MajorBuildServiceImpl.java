package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.dao.MajorBuildMapper;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.*;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(majorBuild.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_ZYJS);
        mainNorm = assessNormService.getByCode(mainNorm);
        if (mainNorm == null) {
            throw new GunsException("考核指标不存在");
        }
        if (!mainNorm.getType().equals(IAssessCoefficientService.TYPE_ZYJS)) {
            throw new GunsException("请填写专业建设考核相关指标代码");
        }

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
    public void applyCheck(MajorBuild majorBuild) {
        MajorBuild build = this.selectById(majorBuild.getId());
        if (build.getStatus() != IMajorBuildMemberService.STATS_APPROVAL_SUCCESS) {
            throw new GunsException("只有已立项才可以申请验收");
        }

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
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_MAJOR_BUILD_CHECK[0], ActUtils.PD_TASK_MAJOR_BUILD_CHECK[1], build.getName()+" 专业建设验收", vars);


        build.setProcInsId(procInsId);
        build.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
        build.updateById();
    }

    @Override
    @Transactional
    public void auditApproval(MajorBuild majorBuild) {
        String pass = (String) majorBuild.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (majorBuild.getExpand().get("comment") != null) {
            comment.append(majorBuild.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (majorBuild.getAct().getTaskDefKey()) {
                case "principal_audit":
                    //项目负责人设置成员积分
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
                    List<String> addAccounts = new ArrayList<>();
                    List<Map> majorBuildMembers = JSON.parseArray(dataJson, Map.class);
                    List<MajorBuildMember> majorBuildMemberList = new ArrayList<>();
                    if (CollUtil.isNotEmpty(majorBuildMembers)) {
                        double sum = 0;
                        for (Map majorBuildMember : majorBuildMembers) {
                            String account = (String) majorBuildMember.get("userNo");
                            if (StrUtil.isBlank(account)) {
                                throw new GunsException("职工编号不能为空");
                            }

                            //判断工号是否重复
                            if (addAccounts.contains(account)) {
                                throw new GunsException(StrUtil.format("职工编号 {} 重复添加", account));
                            } else {
                                addAccounts.add(account);
                            }

                            User user = userService.getByAccount(account);
                            if (user == null) {
                                throw new GunsException(StrUtil.format("职工编号 {} 不存在", account));
                            }
                            if (user.getId().equals(principalUser.getId())) {
                                throw new GunsException("不需要填写项目负责人分数，系统自动分配");
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
                    break;
                case "hr_handle_audit": {
                    //人事经办审核
                    if (StrUtil.isBlank(majorBuild.getApprovalYear())) {
                        throw new GunsException("请设置年度");
                    }
                    majorBuild.updateById();
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_APPROVAL_WAIT);
                    MajorBuildMember member = new MajorBuildMember();
                    member.setYear(majorBuild.getApprovalYear());
                    majorBuildMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
                case "hr_leader_audit": {
                    //人事领导审核
                    majorBuild.setStatus(IMajorBuildMemberService.STATS_APPROVAL_SUCCESS);
                    majorBuild.updateById();
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_APPROVAL_WAIT);
                    List<MajorBuildMember> members = majorBuildMemberService.selectList(new EntityWrapper<>(param));
                    for (MajorBuildMember entity : members) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(entity.getUserId());
                        assessNormPoint.setYear(entity.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_ZYJS);
                        if (assessNormPoint != null) {
                            Double mainPoint = assessNormPoint.getKygzMain();
                            mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setZyjsMain(mainPoint);
                            Double collegePoint = assessNormPoint.getZyjsCollege();
                            collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
                            assessNormPoint.setZyjsCollege(collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setZyjsMain(mainPoint);
                            assessNormPoint.setZyjsCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                            assessNormPoint.setYear(entity.getYear());
                            assessNormPoint.setUserId(entity.getUserId());
                        }
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }

                    MajorBuildMember member = new MajorBuildMember();
                    member.setStatus(IMajorBuildMemberService.STATS_APPROVAL_SUCCESS);
                    majorBuildMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
            }
        } else if (pass.equals(YesNo.NO.getCode() + "")){
            switch (majorBuild.getAct().getTaskDefKey()) {
                case "dean_audit":
                case "major_build_handle_audit":
                case "deans_office_leader_audit":
                    majorBuild.deleteById();break;
                case "dean_again_audit":
                case "hr_handle_audit":
                case "hr_leader_audit":
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_APPROVAL_WAIT);
                    majorBuildMemberService.delete(new EntityWrapper<>(param));break;
            }
        }
        actTaskService.complete(majorBuild.getAct().getTaskId(), majorBuild.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void auditCheck(MajorBuild majorBuild) {
        String pass = (String) majorBuild.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【拒绝】");
        if (majorBuild.getExpand().get("comment") != null) {
            comment.append(majorBuild.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (majorBuild.getAct().getTaskDefKey()) {
                case "principal_audit":
                    //项目负责人设置成员积分
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
                    List<String> addAccounts = new ArrayList<>();
                    List<Map> majorBuildMembers = JSON.parseArray(dataJson, Map.class);
                    List<MajorBuildMember> majorBuildMemberList = new ArrayList<>();
                    if (CollUtil.isNotEmpty(majorBuildMembers)) {
                        double sum = 0;
                        for (Map majorBuildMember : majorBuildMembers) {
                            String account = (String) majorBuildMember.get("userNo");
                            if (StrUtil.isBlank(account)) {
                                throw new GunsException("职工编号不能为空");
                            }

                            //判断工号是否重复
                            if (addAccounts.contains(account)) {
                                throw new GunsException(StrUtil.format("职工编号 {} 重复添加", account));
                            } else {
                                addAccounts.add(account);
                            }

                            User user = userService.getByAccount(account);
                            if (user == null) {
                                throw new GunsException(StrUtil.format("职工编号 {} 不存在", account));
                            }
                            if (user.getId().equals(principalUser.getId())) {
                                throw new GunsException("不需要填写项目负责人分数，系统自动分配");
                            }
                            double point = Double.parseDouble(majorBuildMember.get("mainPoint") + "");
                            sum += point;
                            MajorBuildMember member = new MajorBuildMember();
                            member.setUserId(user.getId());
                            member.setBuildId(majorBuild.getId());
                            member.setMainNormPoint(point);
                            member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                            member.setCoePoint(coefficient.getCoefficient());
                            member.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
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
                        member.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
                        majorBuildMemberList.add(member);
                    } else {
                        throw new GunsException("请添加项目组成员");
                    }
                    majorBuildMemberService.insertBatch(majorBuildMemberList);
                    break;
                case "hr_handle_audit": {
                    //人事经办审核
                    if (StrUtil.isBlank(majorBuild.getCheckYear())) {
                        throw new GunsException("请设置年度");
                    }
                    majorBuild.updateById();
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
                    MajorBuildMember member = new MajorBuildMember();
                    member.setYear(majorBuild.getCheckYear());
                    majorBuildMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
                case "hr_leader_audit": {
                    //人事领导审核
                    majorBuild.setStatus(IMajorBuildMemberService.STATS_CHECK_SUCCESS);
                    majorBuild.updateById();
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
                    List<MajorBuildMember> members = majorBuildMemberService.selectList(new EntityWrapper<>(param));
                    for (MajorBuildMember entity : members) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(entity.getUserId());
                        assessNormPoint.setYear(entity.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_ZYJS);
                        if (assessNormPoint != null) {
                            Double mainPoint = assessNormPoint.getKygzMain();
                            mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setZyjsMain(mainPoint);
                            Double collegePoint = assessNormPoint.getZyjsCollege();
                            collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
                            assessNormPoint.setZyjsCollege(collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setZyjsMain(mainPoint);
                            assessNormPoint.setZyjsCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                            assessNormPoint.setYear(entity.getYear());
                            assessNormPoint.setUserId(entity.getUserId());
                        }
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }

                    MajorBuildMember member = new MajorBuildMember();
                    member.setStatus(IMajorBuildMemberService.STATS_CHECK_SUCCESS);
                    majorBuildMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
            }
        } else if (pass.equals(YesNo.NO.getCode() + "")){
            switch (majorBuild.getAct().getTaskDefKey()) {
                case "dean_audit":
                case "hr_handle_audit":
                case "hr_leader_audit":
                    MajorBuildMember param = new MajorBuildMember();
                    param.setBuildId(majorBuild.getId());
                    param.setStatus(IMajorBuildMemberService.STATS_CHECK_WAIT);
                    majorBuildMemberService.delete(new EntityWrapper<>(param));break;
            }
        }
        actTaskService.complete(majorBuild.getAct().getTaskId(), majorBuild.getAct().getProcInsId(), comment.toString(), vars);
    }
}