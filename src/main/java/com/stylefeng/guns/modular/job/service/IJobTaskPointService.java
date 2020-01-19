package com.stylefeng.guns.modular.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.job.model.JobTaskPoint;

/**
 * 工作得分服务类
 *
 * @author cp
 * @Date 2020-01-17 09:44:27
 */
public interface IJobTaskPointService extends IService<JobTaskPoint> {
    /**
     * 类型：经办得分
     */
    int TYPE_MAIN_HANDLE = 1;
    /**
     * 类型：协助得分
     */
    int TYPE_ASSIST_HANDLE = 2;



}