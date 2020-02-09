package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;

import java.util.List;

public class AssessNormPointDecorator extends BaseListDecorator<AssessNormPoint> {

    public AssessNormPointDecorator(List<AssessNormPoint> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(AssessNormPoint assessNormPoint) {
    }
}
