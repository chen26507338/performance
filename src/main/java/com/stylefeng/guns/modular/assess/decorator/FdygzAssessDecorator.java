package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.FdygzAssess;

import java.util.List;

public class FdygzAssessDecorator extends BaseListDecorator<FdygzAssess> {

    public FdygzAssessDecorator(List<FdygzAssess> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(FdygzAssess fdygzAssess) {
    }
}
