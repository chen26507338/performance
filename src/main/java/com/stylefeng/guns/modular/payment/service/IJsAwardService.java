package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.JsAward;

/**
 * 竞赛奖励服务类
 *
 * @author 
 * @Date 2021-03-04 18:48:16
 */
public interface IJsAwardService extends IService<JsAward> {

    void importData(JsAward jsAward);
}