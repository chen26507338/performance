package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.JobDuties;
import com.stylefeng.guns.modular.job.model.JobTask;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobDutiesService;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JobTaskDecorator extends BaseListDecorator<JobTask> {

    private IUserService userService;
    private IJobDutiesService jobDutiesService;
    private IDeptService deptService;
    public JobTaskDecorator(List<JobTask> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        jobDutiesService = SpringContextHolder.getBean(IJobDutiesService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(JobTask jobTask) {
        User user = userService.selectById(jobTask.getUserId());
        if (user != null) {
            jobTask.putExpand("userName", user.getName());
        }
        User appointUser = userService.selectById(jobTask.getAppointUserId());
        if (appointUser != null) {
            jobTask.putExpand("appointUserName", appointUser.getName());
        }
        User applyUser = userService.selectById(jobTask.getApplyUserId());
        if (applyUser != null) {
            jobTask.putExpand("applyUserName", applyUser.getName());
        }
//        JobDuties jobDuties = jobDutiesService.selectById(jobTask.getDutiesId());
//        if (jobDuties != null) {
//            jobTask.putExpand("jobDutiesDes", jobDuties.getDes());
//        }
        Dept dept = deptService.selectById(jobTask.getDeptId());
        if (dept != null) {
            jobTask.putExpand("deptName", dept.getName());
        }
    }
}
