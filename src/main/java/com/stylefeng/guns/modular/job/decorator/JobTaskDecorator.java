package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.job.model.JobTask;

import java.util.List;

public class JobTaskDecorator extends BaseListDecorator<JobTask> {

    public JobTaskDecorator(List<JobTask> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(JobTask jobTask) {
    }
}
