package com.stylefeng.guns.modular.payment.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.payment.model.JobPriceYear;

import java.util.List;

public class JobPriceYearDecorator extends BaseListDecorator<JobPriceYear> {

    public JobPriceYearDecorator(List<JobPriceYear> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(JobPriceYear jobPriceYear) {
        jobPriceYear.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",jobPriceYear.getStatus()));
    }
}
