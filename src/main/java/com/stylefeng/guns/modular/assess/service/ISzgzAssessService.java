package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.SzgzAssess;

/**
 * 思政工作服务类
 *
 * @author 
 * @Date 2020-09-30 13:31:44
 */
public interface ISzgzAssessService extends IService<SzgzAssess> {

    void apply(SzgzAssess szgzAssess);

    void audit(SzgzAssess szgzAssess);
}