package com.stylefeng.guns.modular.user.service.impl;

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
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.dao.ScientificAchievementMapper;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
import com.stylefeng.guns.modular.user.service.IScientificAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 科研成果服务实现类
 *
 * @author cp
 * @Date 2020-08-14 15:55:50
 */
@Service
public class ScientificAchievementServiceImpl extends ServiceImpl<ScientificAchievementMapper, ScientificAchievement> implements IScientificAchievementService {


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

    @Override
    @Transactional
    public void addApply(List<ScientificAchievement> scientificAchievements) {
        this.handList(scientificAchievements, true);
        this.insertBatch(scientificAchievements);
    }

    @Override
    @Transactional
    public void audit(ScientificAchievement scientificAchievement) {
        String pass = (String) scientificAchievement.getExpand().get("pass");
        StringBuilder comment;
        switch (scientificAchievement.getAct().getTaskDefKey()) {
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
        if (scientificAchievement.getExpand().get("comment") != null) {
            comment.append(scientificAchievement.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) scientificAchievement.getExpand().get("data");
        List<ScientificAchievement> auditDatas = JSON.parseArray(dataJson, ScientificAchievement.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas, false);
            this.updateBatchById(auditDatas);
        }


        ScientificAchievement param = new ScientificAchievement();
        param.setUserId((Long) actTaskService.getTaskService().getVariable(scientificAchievement.getAct().getTaskId(), "user"));

        ScientificAchievement newEntity = new ScientificAchievement();
        param.setProcInsId(scientificAchievement.getAct().getProcInsId());
        if (pass.equals(YesNo.YES.getCode() + "")) {
            if (scientificAchievement.getAct().getTaskDefKey().equals("hr_leader_audit")) {
                //本次审核的数据标识为已通过
                List<ScientificAchievement> assessList = this.selectList(new EntityWrapper<>(param));
                for (ScientificAchievement entity : assessList) {
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
            } else if (scientificAchievement.getAct().getTaskDefKey().equals("hr_handle_audit")) {
                //设置年度

                newEntity.setYear(scientificAchievement.getYear());
                this.update(newEntity, new EntityWrapper<>(param));
            }
        }


        actTaskService.complete(scientificAchievement.getAct().getTaskId(), scientificAchievement.getAct().getProcInsId(), comment.toString(), vars);

    }

    private void handList(List<ScientificAchievement> scientificAchievements, boolean isImport) {

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

            vars.put("act_path", "/scientificAchievement/scientificAchievement_act");
            proIncId = actTaskService.startProcessOnly(ActUtils.PD_SCIENTIFIC_ASSESS, "scientific_achievement", ShiroKit.getUser().name + " 科研成果审核", vars);
        }

        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (ScientificAchievement scientificAchievement : scientificAchievements) {
            if (!p.matcher(scientificAchievement.getTime()).find()) {
                throw new GunsException("时间格式不正确，正确格式xxxx-xx-xx");
            }

            if (isImport) {
//                scientificAchievement.setDeptId(employee.getDeptId());
                scientificAchievement.setHrHandleId(hrHandle.getId());
                scientificAchievement.setHrLeaderId(hrLeader.getId());
                scientificAchievement.setSciCommissioner(sciCommissioner.getId());
                scientificAchievement.setSciLeaderId(sciLeader.getId());
//                scientificAchievement.set(new Date());
            }

            String normCode = scientificAchievement.getNormCode();
            if (StrUtil.isNotBlank(normCode)) {
                //校级标准分
                AssessNorm mainNorm = new AssessNorm();
                mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                mainNorm.setCode(normCode);
                mainNorm.setType(IAssessCoefficientService.TYPE_KYGZ);
                mainNorm = assessNormService.getByCode(mainNorm);
                scientificAchievement.setMainNormPoint(mainNorm.getPoint());
                scientificAchievement.setNormId(mainNorm.getId());
                //院级浮动值
//                AssessNorm collegeNorm = new AssessNorm();
//                collegeNorm.setDeptId(ShiroKit.getUser().deptId);
//                collegeNorm.setCode(normCode);
//                collegeNorm.setType(IAssessCoefficientService.TYPE_KYGZ);
//                collegeNorm = assessNormService.getByCode(collegeNorm);
//                scientificAchievement.setCollegeNormPoint(collegeNorm.getPoint());

                //考核系数
                AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_KYGZ);
                scientificAchievement.setCoePoint(coefficient.getCoefficient());
            }

            scientificAchievement.setProcInsId(proIncId);
            scientificAchievement.setUserId(ShiroKit.getUser().id);
        }
    }
}