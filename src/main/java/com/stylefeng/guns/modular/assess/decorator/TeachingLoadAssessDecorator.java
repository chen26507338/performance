package com.stylefeng.guns.modular.assess.decorator;

import cn.hutool.core.text.UnicodeUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.TeachingLoadAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class TeachingLoadAssessDecorator extends BaseListDecorator<TeachingLoadAssess> {

    private final IUserService userService;
    public TeachingLoadAssessDecorator(List<TeachingLoadAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(TeachingLoadAssess teachingLoadAssess) {
        User user = userService.selectIgnorePointById(teachingLoadAssess.getUserId());
        if (user != null) {
            teachingLoadAssess.putExpand("account", user.getAccount());
            teachingLoadAssess.putExpand("name", user.getName());
        }
    }
}
