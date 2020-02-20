package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.NormalAssess;

/**
 * 考核指标库服务类
 *
 * @author 
 * @Date 2020-02-02 13:18:03
 */
public interface INormalAssessService extends IService<NormalAssess> {
    /**
     * 审核
     */
    void audit(NormalAssess normalAssess);
}