package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.SignInLog;

import java.util.List;

public class SignInLogDecorator extends BaseListDecorator<SignInLog> {

    public SignInLogDecorator(List<SignInLog> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(SignInLog signInLog) {
        signInLog.putExpand("typeDict", ConstantFactory.me().getDictsByName("打卡类型",signInLog.getType()));
    }
}
