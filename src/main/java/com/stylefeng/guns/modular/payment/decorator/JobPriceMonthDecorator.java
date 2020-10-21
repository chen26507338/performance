package com.stylefeng.guns.modular.payment.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.payment.model.JobPriceMonth;
import com.stylefeng.guns.modular.system.service.IUserService;

import javax.swing.*;
import java.util.List;

public class JobPriceMonthDecorator extends BaseListDecorator<JobPriceMonth> {

    private final IDeptService deptService;
    private final IUserService userService;
    public JobPriceMonthDecorator(List<JobPriceMonth> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JobPriceMonth jobPriceMonth) {
//        jobPriceMonth.setShouldPrice(jobPriceMonth.getBasePrice()+jobPriceMonth.getMgrPrice()+jobPriceMonth.getRetroactivePrice());
//        jobPriceMonth.setResultPrice(jobPriceMonth.getShouldPrice() - jobPriceMonth.getGarnishedPrice());
        User user = userService.selectIgnorePointById(jobPriceMonth.getUserId());
        jobPriceMonth.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",jobPriceMonth.getStatus()));
        if (user != null) {
            jobPriceMonth.putExpand("account", user.getAccount());
            jobPriceMonth.putExpand("name", user.getName());
        }
        Dept dept = deptService.selectById(jobPriceMonth.getDeptId());
        if (dept != null) {
            jobPriceMonth.putExpand("deptName", dept.getName());
        }
    }
}
