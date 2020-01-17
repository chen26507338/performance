package com.stylefeng.guns.modular.job.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.JobDuties;
import com.stylefeng.guns.modular.job.dao.JobDutiesMapper;
import com.stylefeng.guns.modular.job.service.IJobDutiesService;

/**
 * 岗位职责管理服务实现类
 *
 * @author cp
 * @Date 2020-01-17 09:39:44
 */
 @Service
public class JobDutiesServiceImpl extends ServiceImpl<JobDutiesMapper, JobDuties> implements IJobDutiesService {

}