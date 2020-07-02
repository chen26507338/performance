package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;

import java.util.List;

/**
 * 科研论著服务类
 *
 * @author cp
 * @Date 2020-07-02 10:13:49
 */
public interface IScientificTreatiseService extends IService<ScientificTreatise> {

    /**
     * 审核
     * @param scientificTreatise
     */
    void audit(ScientificTreatise scientificTreatise);

    /**
     * 新增申请
     * @param scientificTreatises
     */
    void addApply(List<ScientificTreatise> scientificTreatises);
}