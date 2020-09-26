package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.job.model.AllocationPointLog;

import java.util.List;

public class AllocationPointLogDecorator extends BaseListDecorator<AllocationPointLog> {

    public AllocationPointLogDecorator(List<AllocationPointLog> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(AllocationPointLog allocationPointLog) {
    }
}
