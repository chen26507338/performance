package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.SxjxAssess;

import java.util.List;

public class SxjxAssessDecorator extends BaseListDecorator<SxjxAssess> {

    public SxjxAssessDecorator(List<SxjxAssess> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(SxjxAssess sxjxAssess) {
        sxjxAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",sxjxAssess.getStatus()));
    }
}
