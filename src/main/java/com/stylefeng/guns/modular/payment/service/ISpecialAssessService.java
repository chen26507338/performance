package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.SpecialAssess;

/**
 * 专项工作奖项目列表服务类
 *
 * @author 
 * @Date 2021-02-25 18:10:30
 */
public interface ISpecialAssessService extends IService<SpecialAssess> {
    String CACHE_NAME = "special_assess";
    String CACHE_ENTITY = "special_assess_entity";

    void importProject(SpecialAssess specialAssess);

    void importAssess(SpecialAssess specialAssess);
}