package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;

public class ScientificProjectDecorator extends BaseListDecorator<ScientificProject> {

    public ScientificProjectDecorator(List<ScientificProject> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(ScientificProject scientificProject) {
    }
}
