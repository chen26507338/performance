package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.ShpxgzAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class ShpxgzAssessDecorator extends BaseListDecorator<ShpxgzAssess> {
    private final IUserService userService;


    public ShpxgzAssessDecorator(List<ShpxgzAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(ShpxgzAssess shpxgzAssess) {
        User user = userService.selectIgnorePointById(shpxgzAssess.getUserId());
        if (user != null) {
            shpxgzAssess.putExpand("user",user);
        }
    }
}
