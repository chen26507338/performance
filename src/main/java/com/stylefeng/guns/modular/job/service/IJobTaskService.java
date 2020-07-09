package com.stylefeng.guns.modular.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.job.model.JobTask;

/**
 * 工作任务服务类
 *
 * @author cp
 * @Date 2020-01-17 09:42:16
 */
public interface IJobTaskService extends IService<JobTask> {

    /**
     * 类型：下发任务
     */
    int TYPE_APPOINT = 1;

    /**
     * 类型：汇报任务
     */
    int TYPE_REPORT = 2;

    /**
     * 添加汇报任务
     * @param jobTask
     */
    void addReport(JobTask jobTask);

    /**
     * 处理汇报任务
     * @param jobTask
     */
    void handleReport(JobTask jobTask);
}