package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.RypzAssess;

/**
 * 人员配置考核服务类
 *
 * @author 
 * @Date 2021-02-21 17:45:46
 */
public interface IRypzAssessService extends IService<RypzAssess> {

    /**
     * 导入
     * @param rypzAssess
     */
    void importAssess(RypzAssess rypzAssess);
}