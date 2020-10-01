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
import com.stylefeng.guns.modular.assess.dao.StuWorkMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生工作考核服务实现类
 *
 * @author cp
 * @Date 2020-09-16 15:25:30
 */
@Service
public class StuWorkServiceImpl extends ServiceImpl<StuWorkMapper, StuWork> implements IStuWorkService {


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
    private IStuWorkMemberService stuWorkMemberService;
    
    @Override
    @Transactional
    public void apply(StuWork stuWork) {
        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(stuWork.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_XSGZ);
        mainNorm = assessNormService.getByCode(mainNorm);
        if (mainNorm == null) {
            throw new GunsException("考核指标不存在");
        }
        if (!mainNorm.getType().equals(IAssessCoefficientService.TYPE_XSGZ)) {
            throw new GunsException("请填写学生工作考核相关指标代码");
        }

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        //院长
        wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User dean = userService.selectOne(wrapper);

        //考核专员
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_STU_WORK_HR + "");
        User stuWorkHandle = userService.selectOne(wrapper);

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
        vars.put("dean_user", dean.getId());
        vars.put("stu_work_handle", stuWorkHandle.getId());
        vars.put("stu_office_leader", stuOfficeLeader.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("hr_leader", hrLeader.getId());
        vars.put("committee_secretary", committeeSecretary.getId());
        vars.put("secretary", secretary.getId());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_STU_WORK_ASSESS[0], ActUtils.PD_TASK_STU_WORK_ASSESS[1], "学生工作考核", vars);


        stuWork.setNormId(mainNorm.getId());
        stuWork.setProcInsId(procInsId);
        stuWork.setSecretaryId(secretary.getId());
        stuWork.setDeanUserId(dean.getId());
        stuWork.setStudentsOfficeLeaderId(stuOfficeLeader.getId());
        stuWork.setHrHandleId(hrHandle.getId());
        stuWork.setHrLeaderId(hrLeader.getId());
        stuWork.setCommitteeSecretaryId(committeeSecretary.getId());
        stuWork.setStuWorkCommissioner(stuWorkHandle.getId());
        stuWork.insert();
    }

    @Override
    @Transactional
    public void audit(StuWork stuWork) {
        String pass = (String) stuWork.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (stuWork.getExpand().get("comment") != null) {
            comment.append(stuWork.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (stuWork.getAct().getTaskDefKey()) {
                case "re_choice":
                    if (StrUtil.isBlank(stuWork.getNormCode())) {
                        throw new GunsException("请输入考核代码");
                    }
                    AssessNorm mainNorm = new AssessNorm();
                    mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                    mainNorm.setCode(stuWork.getNormCode());
                    mainNorm.setType(IAssessCoefficientService.TYPE_XSGZ);
                    mainNorm = assessNormService.getByCode(mainNorm);
                    if (mainNorm == null) {
                        throw new GunsException("考核指标不存在");
                    }
                    if (!mainNorm.getType().equals(IAssessCoefficientService.TYPE_XSGZ)) {
                        throw new GunsException("请填写学生工作考核相关指标代码");
                    }
                    stuWork.setNormId(mainNorm.getId());
                    stuWork.updateById();
                    break;
                case "committee_secretary_audit":
                    //项目负责人设置成员积分
                    AssessNorm assessNorm = assessNormService.selectById(stuWork.getNormId());

                    StuWork tempStuWork = this.selectById(stuWork.getId());
                    User dean = userService.selectById(tempStuWork.getDeanUserId());
                    AssessNorm collegeNorm = new AssessNorm();
                    collegeNorm.setDeptId(dean.getDeptId());
                    collegeNorm.setCode(assessNorm.getCode());
                    collegeNorm.setType(IAssessCoefficientService.TYPE_XSGZ);
                    collegeNorm = assessNormService.getByCode(collegeNorm);
                    //考核系数
                    AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_XSGZ);

                    String dataJson = (String) stuWork.getExpand().get("data");
                    List<String> addAccounts = new ArrayList<>();
                    List<Map> stuWorkMembers = JSON.parseArray(dataJson, Map.class);
                    List<StuWorkMember> stuWorkMemberList = new ArrayList<>();
                    if (CollUtil.isNotEmpty(stuWorkMembers)) {
                        double sum = 0;
                        for (Map stuWorkMember : stuWorkMembers) {
                            String account = (String) stuWorkMember.get("userNo");
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

                            double point = Double.parseDouble(stuWorkMember.get("mainPoint") + "");
                            sum += point;
                            StuWorkMember member = new StuWorkMember();
                            member.setUserId(user.getId());
                            member.setSWorkId(stuWork.getId());
                            member.setMainNormPoint(point);
                            member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                            member.setYear(tempStuWork.getYear());
                            member.setCoePoint(coefficient.getCoefficient());
                            stuWorkMemberList.add(member);
//                        member.set
                        }
                        if (sum > assessNorm.getPoint()) {
                            throw new GunsException("总分数不能高于" + assessNorm.getPoint());
                        }
                    } else {
                        throw new GunsException("请添加项目组成员");
                    }
                    stuWorkMemberService.insertBatch(stuWorkMemberList);
                    break;
                case "hr_handle_audit": {
                    //人事经办审核
                    if (StrUtil.isBlank(stuWork.getYear())) {
                        throw new GunsException("请设置年度");
                    }
                    stuWork.updateById();
                    break;
                }
                case "secretary_audit": {
                    //二级学院书记审核
                    stuWork.setStatus(YesNo.YES.getCode());
                    stuWork.updateById();
                    StuWorkMember param = new StuWorkMember();
                    param.setSWorkId(stuWork.getId());
                    param.setStatus(YesNo.NO.getCode());
                    List<StuWorkMember> members = stuWorkMemberService.selectList(new EntityWrapper<>(param));
                    for (StuWorkMember entity : members) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(entity.getUserId());
                        assessNormPoint.setYear(entity.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_XSGZ);
                        if (assessNormPoint != null) {
                            Double mainPoint = assessNormPoint.getKygzMain();
                            mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setXsgzMain(mainPoint);
                            Double collegePoint = assessNormPoint.getZyjsCollege();
                            collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
                            assessNormPoint.setXsgzCollege(collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setXsgzMain(mainPoint);
                            assessNormPoint.setXsgzCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                            assessNormPoint.setYear(entity.getYear());
                            assessNormPoint.setUserId(entity.getUserId());
                        }
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }

                    StuWorkMember member = new StuWorkMember();
                    member.setStatus(YesNo.YES.getCode());
                    stuWorkMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
            }
        } else {
            if ("secretary_audit".equals(stuWork.getAct().getTaskDefKey())) {
                StuWorkMember param = new StuWorkMember();
                param.setSWorkId(stuWork.getId());
                param.setStatus(YesNo.NO.getCode());
                stuWorkMemberService.delete(new EntityWrapper<>(param));
            }
        }
        actTaskService.complete(stuWork.getAct().getTaskId(), stuWork.getAct().getProcInsId(), comment.toString(), vars);
    }
}