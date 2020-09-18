package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.ManService;

import java.util.List;

public class ManServiceDecorator extends BaseListDecorator<ManService> {

    public ManServiceDecorator(List<ManService> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(ManService manService) {
    }
}
