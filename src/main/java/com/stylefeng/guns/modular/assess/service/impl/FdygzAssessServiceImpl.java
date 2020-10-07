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
import com.stylefeng.guns.modular.assess.dao.FdygzAssessMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辅导员工作考核服务实现类
 *
 * @author
 * @Date 2020-10-07 11:53:54
 */
@Service
public class FdygzAssessServiceImpl extends ServiceImpl<FdygzAssessMapper, FdygzAssess> implements IFdygzAssessService {


    @Autowired
    private IUserService userService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IFdygzAssessMemberService fdygzAssessMemberService;
    
    @Override
    @Transactional
    public void apply(FdygzAssess fdygzAssess) {
        if (StrUtil.isBlank(fdygzAssess.getContent())) {
            throw new GunsException("请填写考核内容");
        }

        //考核专员
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_STU_WORK_HR + "");
        User fdygzAssessHandle = userService.selectOne(wrapper);

        //人事经办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrHandle = userService.selectOne(wrapper);

        //学生处处长
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", IDeptService.XSC);
        User stuOfficeLeader = userService.selectOne(wrapper);

        //人事领导
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrLeader = userService.selectOne(wrapper);

        //团委书记
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_COMMITTEE_SECRETARY + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User committeeSecretary = userService.selectOne(wrapper);

        //书记
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_SECRETARY + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User secretary  = userService.selectOne(wrapper);

        Map<String, Object> vars = new HashMap<>();
        vars.put("user", ShiroKit.getUser().id);
        vars.put("commissioner", fdygzAssessHandle.getId());
        vars.put("stu_office_leader", stuOfficeLeader.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("hr_leader", hrLeader.getId());
        vars.put("ftwsj", committeeSecretary.getId());
        vars.put("sj", secretary.getId());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_FDYGZ_ASSESS[0], ActUtils.PD_TASK_FDYGZ_ASSESS[1], "辅导员日常工作考核", vars);


        fdygzAssess.setProcInsId(procInsId);
        fdygzAssess.setTwsjId(committeeSecretary.getId());
        fdygzAssess.setSjId(secretary.getId());
        fdygzAssess.setStudentsOfficeLeaderId(stuOfficeLeader.getId());
        fdygzAssess.setHrHandleId(hrHandle.getId());
        fdygzAssess.setHrLeaderId(hrLeader.getId());
        fdygzAssess.setCommissionerId(committeeSecretary.getId());
        fdygzAssess.insert();
    }

    @Override
    @Transactional
    public void audit(FdygzAssess fdygzAssess) {
        String pass = (String) fdygzAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (fdygzAssess.getExpand().get("comment") != null) {
            comment.append(fdygzAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (fdygzAssess.getAct().getTaskDefKey()) {
                case "re_commit":
                    if (StrUtil.isBlank(fdygzAssess.getContent())) {
                        throw new GunsException("请填写考核内容");
                    }
                    fdygzAssess.updateById();
                    break;
                case "ftwsj_audit":
                    //项目负责人设置成员积分
                    FdygzAssess tempFdygzAssess = this.selectById(fdygzAssess.getId());
                    //考核系数
                    AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_FDYRCGZ);

                    String dataJson = (String) fdygzAssess.getExpand().get("data");
                    List<String> addAccounts = new ArrayList<>();
                    List<Map> fdygzAssessMembers = JSON.parseArray(dataJson, Map.class);
                    List<FdygzAssessMember> fdygzAssessMemberList = new ArrayList<>();
                    if (CollUtil.isNotEmpty(fdygzAssessMembers)) {
                        double sum = 0;
                        for (Map fdygzAssessMember : fdygzAssessMembers) {
                            String account = (String) fdygzAssessMember.get("userNo");
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

                            double point = Double.parseDouble(fdygzAssessMember.get("mainPoint") + "");
                            sum += point;
                            FdygzAssessMember member = new FdygzAssessMember();
                            member.setUserId(user.getId());
                            member.setFWorkId(fdygzAssess.getId());
                            member.setMainNormPoint(point);
//                            member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                            member.setYear(tempFdygzAssess.getYear());
                            member.setCoePoint(coefficient.getCoefficient());
                            fdygzAssessMemberList.add(member);
                        }
                        if (sum > tempFdygzAssess.getPoint()) {
                            throw new GunsException("总分数不能高于" + tempFdygzAssess.getPoint());
                        }
                    } else {
                        throw new GunsException("请添加项目组成员");
                    }
                    fdygzAssessMemberService.insertBatch(fdygzAssessMemberList);
                    break;
                case "hr_handle_audit": {
                    //人事经办审核
                    if (StrUtil.isBlank(fdygzAssess.getYear())) {
                        throw new GunsException("请设置年度");
                    }
                    if (fdygzAssess.getPoint() == null) {
                        throw new GunsException("请设置分值");
                    }
                    fdygzAssess.updateById();
                    break;
                }
                case "sj_audit": {
                    //二级学院书记审核
                    fdygzAssess.setStatus(YesNo.YES.getCode());
                    fdygzAssess.updateById();
                    FdygzAssessMember param = new FdygzAssessMember();
                    param.setFWorkId(fdygzAssess.getId());
                    param.setStatus(YesNo.NO.getCode());
                    List<FdygzAssessMember> members = fdygzAssessMemberService.selectList(new EntityWrapper<>(param));
                    for (FdygzAssessMember entity : members) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(entity.getUserId());
                        assessNormPoint.setYear(entity.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_FDYRCGZ);
                        if (assessNormPoint != null) {
                            Double mainPoint = assessNormPoint.getKygzMain();
                            mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setFdyrcgzMain(mainPoint);
//                            Double collegePoint = assessNormPoint.getZyjsCollege();
//                            collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
//                            assessNormPoint.setXsgzCollege(collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setFdyrcgzMain(mainPoint);
//                            assessNormPoint.setXsgzCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                            assessNormPoint.setYear(entity.getYear());
                            assessNormPoint.setUserId(entity.getUserId());
                        }
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }

                    FdygzAssessMember member = new FdygzAssessMember();
                    member.setStatus(YesNo.YES.getCode());
                    fdygzAssessMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
            }
        } else {
            if ("sj_audit".equals(fdygzAssess.getAct().getTaskDefKey())) {
                FdygzAssessMember param = new FdygzAssessMember();
                param.setFWorkId(fdygzAssess.getId());
                param.setStatus(YesNo.NO.getCode());
                fdygzAssessMemberService.delete(new EntityWrapper<>(param));
            }
        }
        actTaskService.complete(fdygzAssess.getAct().getTaskId(), fdygzAssess.getAct().getProcInsId(), comment.toString(), vars);

    }
}