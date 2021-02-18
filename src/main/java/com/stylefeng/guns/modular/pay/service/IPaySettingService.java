package com.stylefeng.guns.modular.pay.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.pay.model.PaySetting;

/**
 * 薪酬设置服务类
 *
 * @author 
 * @Date 2021-02-14 11:12:00
 */
public interface IPaySettingService extends IService<PaySetting> {

    String CACHE_NAME = "pay_setting";
    String CACHE_ENTITY = "pay_setting_entity";

    void importSetting(PaySetting paySetting);

    PaySetting getByName(String name);
}