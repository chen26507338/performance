package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.TeachingLoadAssess;

/**
 * 教学考核服务类
 *
 * @author 
 * @Date 2020-09-20 15:34:09
 */
public interface ITeachingLoadAssessService extends IService<TeachingLoadAssess> {

    void apply(TeachingLoadAssess teachingLoadAssess);

    void audit(TeachingLoadAssess teachingLoadAssess);
}