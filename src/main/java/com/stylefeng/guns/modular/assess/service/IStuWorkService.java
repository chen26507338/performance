package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.StuWork;

/**
 * 学生工作考核服务类
 *
 * @author cp
 * @Date 2020-09-16 15:25:30
 */
public interface IStuWorkService extends IService<StuWork> {
    /**
     * 申请
     * @param stuWork
     */
    void apply(StuWork stuWork);

    void audit(StuWork stuWork);
}