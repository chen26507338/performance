package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.BzryAssess;

/**
 * 表彰荣誉考核服务类
 *
 * @author 
 * @Date 2020-10-04 12:11:14
 */
public interface IBzryAssessService extends IService<BzryAssess> {

    void apply(BzryAssess bzryAssess);

    void audit(BzryAssess bzryAssess);
}