package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.JfwcqkAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class JfwcqkAssessDecorator extends BaseListDecorator<JfwcqkAssess> {
    private final IUserService userService;

    public JfwcqkAssessDecorator(List<JfwcqkAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(JfwcqkAssess jfwcqkAssess) {
        User user = userService.selectIgnorePointById(jfwcqkAssess.getUserId());
        if (user != null) {
            jfwcqkAssess.putExpand("user",user);
        }
    }
}
