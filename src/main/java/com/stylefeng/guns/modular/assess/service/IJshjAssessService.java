package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.JshjAssess;

/**
 * 竞赛获奖服务类
 *
 * @author 
 * @Date 2021-02-24 13:44:31
 */
public interface IJshjAssessService extends IService<JshjAssess> {

    void importAssess(JshjAssess jshjAssess);
}