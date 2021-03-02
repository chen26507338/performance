package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.ShpxgzAssess;

/**
 * 社会培训工作考核服务类
 *
 * @author 
 * @Date 2021-03-01 23:40:34
 */
public interface IShpxgzAssessService extends IService<ShpxgzAssess> {

    void importAssess(ShpxgzAssess shpxgzAssess);
}