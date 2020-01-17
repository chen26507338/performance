package com.stylefeng.guns.modular.job.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.JobTask;
import com.stylefeng.guns.modular.job.dao.JobTaskMapper;
import com.stylefeng.guns.modular.job.service.IJobTaskService;

/**
 * 工作任务服务实现类
 *
 * @author cp
 * @Date 2020-01-17 09:42:16
 */
 @Service
public class JobTaskServiceImpl extends ServiceImpl<JobTaskMapper, JobTask> implements IJobTaskService {

}