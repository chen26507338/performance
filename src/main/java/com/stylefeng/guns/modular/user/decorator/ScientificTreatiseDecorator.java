package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;

import java.util.List;

public class ScientificTreatiseDecorator extends BaseListDecorator<ScientificTreatise> {

    public ScientificTreatiseDecorator(List<ScientificTreatise> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(ScientificTreatise scientificTreatise) {
    }
}
