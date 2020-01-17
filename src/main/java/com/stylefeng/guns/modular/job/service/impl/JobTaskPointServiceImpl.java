package com.stylefeng.guns.modular.job.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.JobTaskPoint;
import com.stylefeng.guns.modular.job.dao.JobTaskPointMapper;
import com.stylefeng.guns.modular.job.service.IJobTaskPointService;

/**
 * 工作得分服务实现类
 *
 * @author cp
 * @Date 2020-01-17 09:44:27
 */
 @Service
public class JobTaskPointServiceImpl extends ServiceImpl<JobTaskPointMapper, JobTaskPoint> implements IJobTaskPointService {

}