package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;

import java.util.List;

/**
 * 考核系数服务类
 *
 * @author 
 * @Date 2020-02-25 10:45:56
 */
public interface IAssessCoefficientService extends IService<AssessCoefficient> {
    String CACHE_LIST = "assess_coefficient_list_";
    String CACHE_ENTITY = "assess_coefficient_entity_";
    /**
     * 查询所有
     */
    List<AssessCoefficient> selectAll();
}