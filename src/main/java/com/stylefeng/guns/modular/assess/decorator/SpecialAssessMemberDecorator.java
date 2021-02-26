package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.SpecialAssessMember;

import java.util.List;

public class SpecialAssessMemberDecorator extends BaseListDecorator<SpecialAssessMember> {

    public SpecialAssessMemberDecorator(List<SpecialAssessMember> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(SpecialAssessMember specialAssessMember) {
    }
}
