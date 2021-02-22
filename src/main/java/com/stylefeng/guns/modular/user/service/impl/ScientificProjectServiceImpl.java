package com.stylefeng.guns.modular.user.service.impl;

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
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.dao.ScientificProjectMapper;
import com.stylefeng.guns.modular.user.service.IScientificProjectService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 科研项目服务实现类
 *
 * @author cp
 * @Date 2020-07-02 12:39:00
 */
@Service
public class ScientificProjectServiceImpl extends ServiceImpl<ScientificProjectMapper, ScientificProject> implements IScientificProjectService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Resource
    private GunsProperties gunsProperties;

    @Override
    @Transactional
    public void addApply(List<ScientificProject> scientificProjects) {
        this.handList(scientificProjects, true);
        this.insertBatch(scientificProjects);
    }

    @Override
    @Transactional
    public void audit(ScientificProject scientificProject) {
        String pass = (String) scientificProject.getExpand().get("pass");
        StringBuilder comment;
        switch (scientificProject.getAct().getTaskDefKey()) {
            case "user_re_submit":
                comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【重新提交】" : "【放弃审核】");
                break;
            case "hr_leader_audit":
                switch (pass) {
                    case "0":
                        comment = new StringBuilder("【重新提交】");
                        break;
                    case "1":
                        comment = new StringBuilder("【重新设置年份】");
                        break;
                    default:
                        comment = new StringBuilder("【通过】");
                        break;
                }
                break;
            default:
                comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        }
        if (scientificProject.getExpand().get("comment") != null) {
            comment.append(scientificProject.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) scientificProject.getExpand().get("data");
        List<ScientificProject> auditDatas = JSON.parseArray(dataJson, ScientificProject.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas, false);
            this.updateBatchById(auditDatas);
        }


        ScientificProject param = new ScientificProject();
        param.setUserId((Long) actTaskService.getTaskService().getVariable(scientificProject.getAct().getTaskId(), "user"));

        ScientificProject newEntity = new ScientificProject();
        param.setProcInsId(scientificProject.getAct().getProcInsId());
        if (pass.equals(YesNo.YES.getCode() + "")) {
            if (scientificProject.getAct().getTaskDefKey().equals("hr_leader_audit")) {
                //本次审核的数据标识为已通过
                List<ScientificProject> assessList = this.selectList(new EntityWrapper<>(param));
                for (ScientificProject entity : assessList) {
                    AssessNormPoint assessNormPoint = new AssessNormPoint();
                    assessNormPoint.setUserId(entity.getUserId());
                    assessNormPoint.setYear(entity.getYear());
                    assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                    AssessCoefficient assessCoefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_KYGZ);
                    if (assessNormPoint != null) {
                        Double mainPoint = assessNormPoint.getKygzMain();
                        mainPoint += entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                        assessNormPoint.setKygzMain(mainPoint);
//                    Double collegePoint = (Double) ReflectUtil.getFieldValue(assessNormPoint, normalAssess.getType() + "College");
//                    collegePoint += (1 + entity.getCollegeNormPoint()) * mainPoint;
//                    ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "College", collegePoint);
                    } else {
                        assessNormPoint = new AssessNormPoint();
                        double mainPoint = entity.getMainNormPoint() * assessCoefficient.getCoefficient();
                        assessNormPoint.setKygzMain(mainPoint);
//                    ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "Main", mainPoint);
//                    ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "College", mainPoint * (1 + entity.getCollegeNormPoint()));
                    }
                    assessNormPoint.setYear(entity.getYear());
//                assessNormPoint.setDeptId(entity.getDeptId());
                    assessNormPoint.setUserId(entity.getUserId());
                    assessNormPointService.insertOrUpdate(assessNormPoint);
                }
                newEntity.setStatus(YesNo.YES.getCode());
                this.update(newEntity, new EntityWrapper<>(param));
            } else if (scientificProject.getAct().getTaskDefKey().equals("hr_handle_audit") ) {
                //设置年度

                newEntity.setYear(scientificProject.getYear());
                this.update(newEntity, new EntityWrapper<>(param));
            }
        }



        actTaskService.complete(scientificProject.getAct().getTaskId(), scientificProject.getAct().getProcInsId(), comment.toString(), vars);

    }

    @Override
    @Transactional
    public void importAssess(ScientificProject scientificProject) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + scientificProject.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("名称", "name");
        reader.addHeaderAlias("期刊名称/排名", "rank");
        reader.addHeaderAlias("分类/级别", "type");
        reader.addHeaderAlias("积分归属年份", "year");
        reader.addHeaderAlias("教师工号", "account");
        List<ScientificProject> normalAssesses = reader.readAll(ScientificProject.class);
        for (ScientificProject assess : normalAssesses) {
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }
            assess.setUserId(user.getId());
            assess.setStatus(YesNo.YES.getCode());

            ScientificProject param = new ScientificProject();
            param.setAccount(assess.getAccount());
            param.setName(assess.getName());
            param.setAssessName(assess.getAssessName());
            if (this.selectCount(new EntityWrapper<>(param)) > 0) {
                throw new GunsException(StrUtil.format("职工编号：{},考核项目：{},名称：{} 已存在",assess.getAccount(),
                        assess.getAssessName(),assess.getName()));
            }

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getKygzMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setKygzMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setKygzMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }

    private void handList(List<ScientificProject> scientificProjects, boolean isImport) {

        User sciCommissioner = null;
        User sciLeader = null;
        User hrHandle = null;
        User hrLeader = null;
        String proIncId = null;

        if (isImport) {
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_SCI_HANDLER + "");
            wrapper.eq("dept_id", IDeptService.SCI);
            sciCommissioner = userService.selectOne(wrapper);

            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", IDeptService.SCI);
            sciLeader = userService.selectOne(wrapper);

            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrHandle = userService.selectOne(wrapper);

            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrLeader = userService.selectOne(wrapper);

            Map<String, Object> vars = new HashMap<>();
            vars.put("user", ShiroKit.getUser().id);
            vars.put("sci_commissioner", sciCommissioner.getId());
            vars.put("sci_leader_user", sciLeader.getId());
            vars.put("hr_handle", hrHandle.getId());
            vars.put("hr_leader", hrLeader.getId());

            vars.put("act_path", "/scientificProject/scientificProject_act");
            proIncId = actTaskService.startProcessOnly(ActUtils.PD_SCIENTIFIC_ASSESS, "scientific_project", ShiroKit.getUser().name + " 科研项目审核", vars);
        }

        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (ScientificProject scientificProject : scientificProjects) {
            if (!p.matcher(scientificProject.getStartTime()).find()) {
                throw new GunsException("开题时间格式不正确，正确格式xxxx-xx-xx");
            }
            if (!p.matcher(scientificProject.getEndTime()).find()) {
                throw new GunsException("结题时间格式不正确，正确格式xxxx-xx-xx");
            }

            if (isImport) {
//                scientificProject.setDeptId(employee.getDeptId());
                scientificProject.setHrHandleId(hrHandle.getId());
                scientificProject.setHrLeaderId(hrLeader.getId());
                scientificProject.setSciCommissioner(sciCommissioner.getId());
                scientificProject.setSciLeaderId(sciLeader.getId());
//                scientificProject.set(new Date());
            }

            String normCode = scientificProject.getNormCode();
            if (StrUtil.isNotBlank(normCode)) {
                //校级标准分
                AssessNorm mainNorm = new AssessNorm();
                mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                mainNorm.setCode(normCode);
                mainNorm.setType(IAssessCoefficientService.TYPE_KYGZ);
                mainNorm = assessNormService.getByCode(mainNorm);
                scientificProject.setMainNormPoint(mainNorm.getPoint());
                scientificProject.setNormId(mainNorm.getId());
                //院级浮动值
//                AssessNorm collegeNorm = new AssessNorm();
//                collegeNorm.setDeptId(ShiroKit.getUser().deptId);
//                collegeNorm.setCode(normCode);
//                collegeNorm.setType(IAssessCoefficientService.TYPE_KYGZ);
//                collegeNorm = assessNormService.getByCode(collegeNorm);
//                scientificProject.setCollegeNormPoint(collegeNorm.getPoint());

                //考核系数
                AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_KYGZ);
                scientificProject.setCoePoint(coefficient.getCoefficient());
            }

            scientificProject.setProcInsId(proIncId);
            scientificProject.setUserId(ShiroKit.getUser().id);
        }


    }
}