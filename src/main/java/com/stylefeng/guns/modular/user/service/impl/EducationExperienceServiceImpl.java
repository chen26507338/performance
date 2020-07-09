package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.Dict;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.dao.EducationExperienceMapper;
import com.stylefeng.guns.modular.user.service.IEducationExperienceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 学历培训服务实现类
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
@Service
public class EducationExperienceServiceImpl extends ServiceImpl<EducationExperienceMapper, EducationExperience> implements IEducationExperienceService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void addApply(List<EducationExperience> educationExperiences) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_EDU_EXP_HR + "");
        User eduExpHr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", eduExpHr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/educationExperience/educationExperience_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "education_experience", ShiroKit.getUser().name + " 个人信息审核", vars);
        for (EducationExperience educationExperience : educationExperiences) {
            educationExperience.setProcInsId(proIncId);
            educationExperience.setUserId(ShiroKit.getUser().id);
        }
        this.handList(educationExperiences, true);
        this.insertBatch(educationExperiences);
    }

    private void handList(List<EducationExperience> educationExperiences, boolean isAdd) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (EducationExperience educationExperience : educationExperiences) {
            if (!p.matcher(educationExperience.getEnrollmentTime()).find()) {
                throw new GunsException("入学时间格式不正确，正确格式xxxx-xx-xx");
            }
            if (!p.matcher(educationExperience.getGraduateTime()).find()) {
                throw new GunsException("毕业时间格式不正确，正确格式xxxx-xx-xx");
            }

            if (!isAdd) {
                String educationBackground = ConstantFactory.me().getDictValueByName("学历", educationExperience.getEducationBackgroundDict());
                if (StrUtil.isBlank(educationBackground)) {
                    throw new GunsException("学历填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("学历").iterator(), ","));
                }
                educationExperience.setEducationBackground(Integer.parseInt(educationBackground));

                String learnStyleDict = ConstantFactory.me().getDictValueByName("学习方式", educationExperience.getLearnStyleDict());
                if (StrUtil.isBlank(learnStyleDict)) {
                    throw new GunsException("学习方式填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("学习方式").iterator(), ","));
                }
                educationExperience.setLearnStyle(Integer.parseInt(learnStyleDict));

                String degreeDict = ConstantFactory.me().getDictValueByName("学位", educationExperience.getDegreeDict());
                if (StrUtil.isBlank(degreeDict)) {
                    throw new GunsException("学位填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("学位").iterator(), ","));
                }
                educationExperience.setDegree(Integer.parseInt(degreeDict));
            }
        }
    }

    @Override
    @Transactional
    public void audit(EducationExperience educationExperience) {
        String pass = (String) educationExperience.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (educationExperience.getExpand().get("comment") != null) {
            comment.append(educationExperience.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) educationExperience.getExpand().get("data");
        List<EducationExperience> educationExperiences = JSON.parseArray(dataJson, EducationExperience.class);
        if (CollUtil.isNotEmpty(educationExperiences)) {
            this.handList(educationExperiences, false);
            this.updateBatchById(educationExperiences);
        }

        if (educationExperience.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            EducationExperience param = new EducationExperience();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(educationExperience.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            EducationExperience newEntity = new EducationExperience();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(educationExperience.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(educationExperience.getAct().getTaskId(), educationExperience.getAct().getProcInsId(), comment.toString(), vars);
    }
}