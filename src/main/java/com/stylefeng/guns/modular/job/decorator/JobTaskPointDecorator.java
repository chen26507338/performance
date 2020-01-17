package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.job.model.JobTaskPoint;

import java.util.List;

public class JobTaskPointDecorator extends BaseListDecorator<JobTaskPoint> {

    public JobTaskPointDecorator(List<JobTaskPoint> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(JobTaskPoint jobTaskPoint) {
    }
}
