package com.stylefeng.guns.modular.pay.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.pay.model.PaySetting;

import java.util.List;

public class PaySettingDecorator extends BaseListDecorator<PaySetting> {

    public PaySettingDecorator(List<PaySetting> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(PaySetting paySetting) {
    }
}
