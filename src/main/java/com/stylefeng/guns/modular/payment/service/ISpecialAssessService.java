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

    void importProject(SpecialAssess specialAssess);
}