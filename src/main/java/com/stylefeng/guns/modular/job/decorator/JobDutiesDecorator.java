package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.model.JobDuties;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;

import java.util.List;

public class JobDutiesDecorator extends BaseListDecorator<JobDuties> {
    private final IJobService jobService;
    private final IDeptService deptService;

    public JobDutiesDecorator(List<JobDuties> list) {
        super(list);
        jobService = SpringContextHolder.getBean(IJobService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(JobDuties jobDuties) {
        Job job = jobService.selectById(jobDuties.getJobId());
        if (job != null) {
            jobDuties.putExpand("jobIdDict", job.getName());
            Dept dept = deptService.selectById(job.getDeptId());
            jobDuties.putExpand("dept", dept);
        }
    }
}
