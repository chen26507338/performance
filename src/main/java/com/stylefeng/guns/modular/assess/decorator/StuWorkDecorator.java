package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.StuWork;

import java.util.List;

public class StuWorkDecorator extends BaseListDecorator<StuWork> {

    public StuWorkDecorator(List<StuWork> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(StuWork stuWork) {
    }
}
