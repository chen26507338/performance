package com.stylefeng.guns.modular.payment.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.payment.model.JobPriceYear;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class JobPriceYearDecorator extends BaseListDecorator<JobPriceYear> {

    private final IUserService userService;
    public JobPriceYearDecorator(List<JobPriceYear> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JobPriceYear jobPriceYear) {
        User user = userService.selectIgnorePointById(jobPriceYear.getUserId());
        if (user != null) {
            jobPriceYear.putExpand("account", user.getAccount());
            jobPriceYear.putExpand("name", user.getName());
        }
        jobPriceYear.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",jobPriceYear.getStatus()));
    }
}
