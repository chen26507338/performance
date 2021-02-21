package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.RypzAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class RypzAssessDecorator extends BaseListDecorator<RypzAssess> {

    private final IUserService userService;

    public RypzAssessDecorator(List<RypzAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(RypzAssess rypzAssess) {
        User user = userService.selectIgnorePointById(rypzAssess.getUserId());
        rypzAssess.putExpand("user", user);
    }
}
