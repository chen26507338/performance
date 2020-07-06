package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.PersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.PersonalInfo;
import com.stylefeng.guns.modular.user.dao.PersonalInfoMapper;
import com.stylefeng.guns.modular.user.service.IPersonalInfoService;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自然信息服务实现类
 *
 * @author cp
 * @Date 2020-07-06 10:02:41
 */
@Service
public class PersonalInfoServiceImpl extends ServiceImpl<PersonalInfoMapper, PersonalInfo> implements IPersonalInfoService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void audit(PersonalInfo personalInfo) {
        String pass = (String) personalInfo.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (personalInfo.getExpand().get("comment") != null) {
            comment.append(personalInfo.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);


        if (pass.equals(YesNo.YES.getCode() + "")) {
            this.updateById(personalInfo);
            if (personalInfo.getAct().getTaskDefKey().equals("audit") ) {
                PersonalInfo param = new PersonalInfo();
                param.setUserId((Long) actTaskService.getTaskService().getVariable(personalInfo.getAct().getTaskId(), "user"));

                //将所有状态标识为拒绝
                PersonalInfo newEntity = new PersonalInfo();
                newEntity.setStatus(YesNo.NO.getCode());
                this.update(newEntity, new EntityWrapper<>(param));
                //本次审核的数据标识为已通过
                param.setProcInsId(personalInfo.getAct().getProcInsId());
                newEntity.setStatus(YesNo.YES.getCode());
                this.update(newEntity, new EntityWrapper<>(param));

                //更新用户信息
                PersonalInfo p = this.selectById(personalInfo.getId());
                User user = userService.selectById(p.getUserId());
                Field[] files = ReflectUtil.getFields(p.getClass());
                for (Field file : files) {
                    if (!file.getName().equals("id") && !file.getName().equals("serialVersionUID")
                            && ReflectUtil.hasField(user.getClass(),file.getName())) {
                        Object value = ReflectUtil.getFieldValue(p,file);
                        ReflectUtil.setFieldValue(user, file.getName(), value);
                    }
                }
                userService.updateById(user);
            }
        }

        actTaskService.complete(personalInfo.getAct().getTaskId(), personalInfo.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void addApply(PersonalInfo personalInfo) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_PERSONAL_HR + "");
        User hr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", hr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/personalInfo/personalInfo_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "personal_info", ShiroKit.getUser().name + " 自然信息审核", vars);
        personalInfo.setProcInsId(proIncId);
        personalInfo.setUserId(ShiroKit.getUser().id);
        this.insert(personalInfo);
    }
}