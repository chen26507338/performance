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
import com.stylefeng.guns.modular.assess.dao.ManServiceMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理服务服务实现类
 *
 * @author
 * @Date 2020-09-17 16:39:45
 */
@Service
public class ManServiceServiceImpl extends ServiceImpl<ManServiceMapper, ManService> implements IManServiceService {

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
    private IManServiceMemberService manServiceMemberService;

    @Override
    @Transactional
    public void apply(ManService manService) {
        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(manService.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_GLFW);
        mainNorm = assessNormService.getByCode(mainNorm);
        if (mainNorm == null) {
            throw new GunsException("考核指标不存在");
        }
        if (!mainNorm.getType().equals(IAssessCoefficientService.TYPE_GLFW)) {
            throw new GunsException("请填写管理服务考核相关指标代码");
        }

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        //部门领导
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User deptLeader = userService.selectOne(wrapper);

        //人事经办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrHandle = userService.selectOne(wrapper);

        //部门综办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_GENERAL + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User deptGeneral = userService.selectOne(wrapper);

        //人事领导
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", IDeptService.HR);
        User hrLeader = userService.selectOne(wrapper);

        Map<String, Object> vars = new HashMap<>();
        vars.put("user", ShiroKit.getUser().id);
        vars.put("dept_leader", deptLeader.getId());
        vars.put("general_man", deptGeneral.getId());
        vars.put("hr_handle", hrHandle.getId());
        vars.put("hr_leader", hrLeader.getId());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_MAN_SERVICE_ASSESS[0], ActUtils.PD_TASK_MAN_SERVICE_ASSESS[1], "管理服务考核", vars);

        manService.setNormId(mainNorm.getId());
        manService.setProcInsId(procInsId);
        manService.setDeptLeaderId(deptLeader.getId());
        manService.setGeneralManId(deptGeneral.getId());
        manService.setHrHandleId(hrHandle.getId());
        manService.setHrLeaderId(hrLeader.getId());
        manService.insert();
    }

    @Override
    @Transactional
    public void audit(ManService manService) {
        String pass = (String) manService.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (manService.getExpand().get("comment") != null) {
            comment.append(manService.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (manService.getAct().getTaskDefKey()) {
                case "re_choice":
                    //重新选择
                    if (StrUtil.isBlank(manService.getNormCode())) {
                        throw new GunsException("请输入考核代码");
                    }
                    AssessNorm mainNorm = new AssessNorm();
                    mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                    mainNorm.setCode(manService.getNormCode());
                    mainNorm.setType(IAssessCoefficientService.TYPE_GLFW);
                    mainNorm = assessNormService.getByCode(mainNorm);
                    if (mainNorm == null) {
                        throw new GunsException("考核指标不存在");
                    }
                    if (!mainNorm.getType().equals(IAssessCoefficientService.TYPE_GLFW)) {
                        throw new GunsException("请填写管理服务考核相关指标代码");
                    }
                    manService.setNormId(mainNorm.getId());
                    manService.updateById();
                    break;
                case "general_audit":
                    //部门综办，分配积分至个人
                    AssessNorm assessNorm = assessNormService.selectById(manService.getNormId());

                    ManService tempManService = this.selectById(manService.getId());
                    User dean = userService.selectById(tempManService.getDeptLeaderId());
                    AssessNorm collegeNorm = new AssessNorm();
                    collegeNorm.setDeptId(dean.getDeptId());
                    collegeNorm.setCode(assessNorm.getCode());
                    collegeNorm.setType(IAssessCoefficientService.TYPE_GLFW);
                    collegeNorm = assessNormService.getByCode(collegeNorm);
                    //考核系数
                    AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_GLFW);

                    String dataJson = (String) manService.getExpand().get("data");
                    List<String> addAccounts = new ArrayList<>();
                    List<Map> manServiceMembers = JSON.parseArray(dataJson, Map.class);
                    List<ManServiceMember> manServiceMemberList = new ArrayList<>();
                    if (CollUtil.isNotEmpty(manServiceMembers)) {
                        double sum = 0;
                        for (Map manServiceMember : manServiceMembers) {
                            String account = (String) manServiceMember.get("userNo");
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

                            double point = Double.parseDouble(manServiceMember.get("mainPoint") + "");
                            sum += point;
                            ManServiceMember member = new ManServiceMember();
                            member.setUserId(user.getId());
                            member.setMServiceId(manService.getId());
                            member.setMainNormPoint(point);
                            member.setCollegeNormPoint(member.getMainNormPoint() * collegeNorm.getPoint());
                            member.setYear(tempManService.getYear());
                            member.setCoePoint(coefficient.getCoefficient());
                            manServiceMemberList.add(member);
//                        member.set
                        }
                        if (sum > assessNorm.getPoint()) {
                            throw new GunsException("总分数不能高于" + assessNorm.getPoint());
                        }
                    } else {
                        throw new GunsException("请添加项目组成员");
                    }
                    manServiceMemberService.insertBatch(manServiceMemberList);
                    break;
                case "hr_handle_audit": {
                    //人事经办审核
                    if (StrUtil.isBlank(manService.getYear())) {
                        throw new GunsException("请设置年度");
                    }
                    manService.updateById();
                    break;
                }
                case "dept_leader_audit_again": {
                    //部门长最终审核
                    manService.setStatus(YesNo.YES.getCode());
                    manService.updateById();
                    ManServiceMember param = new ManServiceMember();
                    param.setMServiceId(manService.getId());
                    param.setStatus(YesNo.NO.getCode());
                    List<ManServiceMember> members = manServiceMemberService.selectList(new EntityWrapper<>(param));
                    for (ManServiceMember entity : members) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(entity.getUserId());
                        assessNormPoint.setYear(entity.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_GLFW);
                        if (assessNormPoint != null) {
                            Double mainPoint = assessNormPoint.getKygzMain();
                            mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setGlfwMain(mainPoint);
                            Double collegePoint = assessNormPoint.getZyjsCollege();
                            collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
                            assessNormPoint.setGlfwCollege(collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                            assessNormPoint.setGlfwMain(mainPoint);
                            assessNormPoint.setGlfwCollege(mainPoint * (1 + entity.getCollegeNormPoint()));
                            assessNormPoint.setYear(entity.getYear());
                            assessNormPoint.setUserId(entity.getUserId());
                        }
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }

                    ManServiceMember member = new ManServiceMember();
                    member.setStatus(YesNo.YES.getCode());
                    manServiceMemberService.update(member, new EntityWrapper<>(param));
                    break;
                }
            }
        } else {
            if ("dept_leader_audit_again".equals(manService.getAct().getTaskDefKey())) {
                ManServiceMember param = new ManServiceMember();
                param.setMServiceId(manService.getId());
                param.setStatus(YesNo.NO.getCode());
                manServiceMemberService.delete(new EntityWrapper<>(param));
            }
        }
        actTaskService.complete(manService.getAct().getTaskId(), manService.getAct().getProcInsId(), comment.toString(), vars);
    }
}