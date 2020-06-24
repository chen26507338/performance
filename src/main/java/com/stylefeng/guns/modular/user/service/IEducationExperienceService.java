package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.EducationExperience;

import java.util.List;

/**
 * 学历培训服务类
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
public interface IEducationExperienceService extends IService<EducationExperience> {
    void addApply(List<EducationExperience> educationExperiences);
}