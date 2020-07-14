package com.stylefeng.guns.modular.job.decorator;

import cn.hutool.core.text.UnicodeUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.JobTaskApply;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class JobTaskApplyDecorator extends BaseListDecorator<JobTaskApply> {

    private IUserService userService;
    public JobTaskApplyDecorator(List<JobTaskApply> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JobTaskApply jobTaskApply) {
        User user = userService.selectIgnorePointById(jobTaskApply.getUserId());
        jobTaskApply.putExpand("account", user.getAccount());
        jobTaskApply.putExpand("name", user.getName());
    }
}
