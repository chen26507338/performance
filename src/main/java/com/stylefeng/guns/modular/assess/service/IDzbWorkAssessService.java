package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.DzbWorkAssess;

/**
 * 党支部工作考核服务类
 *
 * @author 
 * @Date 2020-09-24 13:06:50
 */
public interface IDzbWorkAssessService extends IService<DzbWorkAssess> {
    double MAX_POINT = 200;

    void apply(DzbWorkAssess dzbWorkAssess);

    void audit(DzbWorkAssess dzbWorkAssess);

    void doAllocation(DzbWorkAssess dzbWorkAssess);
}