package com.stylefeng.guns.modular.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.User;
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
//  wrapper.eq("dept_id", ShiroKit.getUser().deptId);
  User eduExpHr = userService.selectOne(wrapper);
  Map<String, Object> vars = new HashMap<>();
  vars.put("audit_user", eduExpHr.getId());
  String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, ShiroKit.getUser().name + "个人信息审核", vars);
  for (EducationExperience educationExperience : educationExperiences) {
   educationExperience.setProcInsId(proIncId);
   educationExperience.setUserId(ShiroKit.getUser().id);
  }
  this.insertBatch(educationExperiences);
 }
}