package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.PqryGz;

/**
 * 派遣人员服务类
 *
 * @author 
 * @Date 2021-02-27 20:56:41
 */
public interface IPqryGzService extends IService<PqryGz> {

    void importData(PqryGz pqryGz);
}