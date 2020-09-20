package com.stylefeng.guns.modular.system.decorator;

import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.system.model.OtherInfo;

import java.util.List;

public class OtherInfoDecorator extends BaseListDecorator<OtherInfo> {

    public OtherInfoDecorator(List<OtherInfo> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(OtherInfo otherInfo) {
    }
}
