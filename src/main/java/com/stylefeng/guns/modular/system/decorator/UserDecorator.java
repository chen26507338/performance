package com.stylefeng.guns.modular.system.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;

import java.util.List;

public class UserDecorator extends BaseListDecorator<User> {
    private IDeptService deptService;
    private IJobService jobService;
    public UserDecorator(List<User> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        jobService = SpringContextHolder.getBean(IJobService.class);
    }

    @Override
    protected void decorateTheEntity(User user) {
        Dept dept = deptService.selectById(user.getDeptId());
        if (dept != null) {
            user.putExpand("deptName", dept.getName());
        }
        Job job = jobService.selectById(user.getJobId());
        if (job != null) {
            user.putExpand("jobName", job.getName());
        }
        user.putExpand("statusName", ConstantFactory.me().getDictsByName("账号状态",user.getStatus()));
        user.putExpand("sexName", ConstantFactory.me().getDictsByName("性别",user.getSex()));
        user.setAvatar(KaptchaUtil.formatFileUrl(user.getAvatar()));
    }
}
