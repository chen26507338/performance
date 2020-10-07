package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.FdygzAssess;

/**
 * 辅导员工作考核服务类
 *
 * @author 
 * @Date 2020-10-07 11:53:54
 */
public interface IFdygzAssessService extends IService<FdygzAssess> {

    void apply(FdygzAssess fdygzAssess);

    void audit(FdygzAssess fdygzAssess);
}