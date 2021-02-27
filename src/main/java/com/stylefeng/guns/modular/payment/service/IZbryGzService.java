package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.ZbryGz;

/**
 * 在编人员服务类
 *
 * @author 
 * @Date 2021-02-27 14:33:44
 */
public interface IZbryGzService extends IService<ZbryGz> {

    void importData(ZbryGz zbryGz);
}