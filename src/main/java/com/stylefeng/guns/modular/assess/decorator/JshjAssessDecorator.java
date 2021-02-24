package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.JshjAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.net.UnknownServiceException;
import java.util.List;

public class JshjAssessDecorator extends BaseListDecorator<JshjAssess> {

    private final IUserService userService;

    public JshjAssessDecorator(List<JshjAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JshjAssess jshjAssess) {
        User user = userService.selectIgnorePointById(jshjAssess.getUserId());
        jshjAssess.putExpand("user", user);
    }
}
