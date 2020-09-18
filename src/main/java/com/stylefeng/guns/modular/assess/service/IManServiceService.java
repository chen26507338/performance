package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.ManService;

/**
 * 管理服务服务类
 *
 * @author 
 * @Date 2020-09-17 16:39:45
 */
public interface IManServiceService extends IService<ManService> {

    void apply(ManService manService);

    void audit(ManService manService);
}