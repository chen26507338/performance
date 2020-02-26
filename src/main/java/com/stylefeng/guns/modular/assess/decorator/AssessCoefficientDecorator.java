package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;

import java.util.List;

public class AssessCoefficientDecorator extends BaseListDecorator<AssessCoefficient> {

    public AssessCoefficientDecorator(List<AssessCoefficient> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(AssessCoefficient assessCoefficient) {
    }
}
