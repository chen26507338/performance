package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.TeachingLoad;

/**
 * 教学工作量服务类
 *
 * @author 
 * @Date 2020-09-20 09:20:09
 */
public interface ITeachingLoadService extends IService<TeachingLoad> {
    int STATUS_IN_AUDIT = 1;

    /**
     * 通过
     */
    int STATUS_PASS = 2;

    void add(TeachingLoad teachingLoad);
}