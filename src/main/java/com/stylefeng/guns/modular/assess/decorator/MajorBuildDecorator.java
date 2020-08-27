package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.MajorBuild;

import java.util.List;

public class MajorBuildDecorator extends BaseListDecorator<MajorBuild> {

    public MajorBuildDecorator(List<MajorBuild> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(MajorBuild majorBuild) {
    }
}
