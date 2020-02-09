package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.NormalAssess;

/**
 *  考核指标库服务类
 *
 * @author 
 * @Date 2020-02-02 13:38:54
 */
public interface IAssessNormService extends IService<AssessNorm> {
    String CACHE_ENTITY = "assess_norm_";

    /**
     * 类型：院级标准分ID
     */
    long TYPE_MAIN_DEPT = 1;

    /**
     * 通过指标代码获取
     * @param assessNorm
     * @return
     */
    AssessNorm getByCode(AssessNorm assessNorm);
}