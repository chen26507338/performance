package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.DlxpryGz;

/**
 * 代理校聘人员服务类
 *
 * @author 
 * @Date 2021-02-27 21:17:01
 */
public interface IDlxpryGzService extends IService<DlxpryGz> {

    void importData(DlxpryGz dlxpryGz);
}