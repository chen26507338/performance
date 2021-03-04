package com.stylefeng.guns.modular.payment.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.payment.model.JsAward;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class JsAwardDecorator extends BaseListDecorator<JsAward> {
    private final IUserService userService;

    public JsAwardDecorator(List<JsAward> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JsAward jsAward) {
        User user = userService.selectIgnorePointById(jsAward.getUserId());
        jsAward.putExpand("user", user);
    }
}
