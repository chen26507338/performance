package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.SxjxAssess;

/**
 * 实训绩效考核服务类
 *
 * @author 
 * @Date 2020-10-10 11:53:51
 */
public interface ISxjxAssessService extends IService<SxjxAssess> {

    double MAX_POINT = 200;

    void doAllocation(SxjxAssess sxjxAssess);

    void apply(SxjxAssess sxjxAssess);

    void audit(SxjxAssess sxjxAssess);

    void importAssess(SxjxAssess sxjxAssess);
}