package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.SignInLog;

import java.util.List;

public class SignInLogDecorator extends BaseListDecorator<SignInLog> {

    private final IUserService userService;

    public SignInLogDecorator(List<SignInLog> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(SignInLog signInLog) {
        signInLog.putExpand("typeDict", ConstantFactory.me().getDictsByName("打卡类型",signInLog.getType()));
        User user = userService.selectIgnorePointById(signInLog.getUserId());
        signInLog.putExpand("user", user);
    }
}
