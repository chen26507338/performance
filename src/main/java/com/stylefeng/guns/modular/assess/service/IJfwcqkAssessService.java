package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.JfwcqkAssess;

/**
 * 经费完成情况考核服务类
 *
 * @author 
 * @Date 2021-03-02 15:27:56
 */
public interface IJfwcqkAssessService extends IService<JfwcqkAssess> {

    void importAssess(JfwcqkAssess jfwcqkAssess);
}