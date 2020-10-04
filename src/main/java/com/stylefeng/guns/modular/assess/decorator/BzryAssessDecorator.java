package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.BzryAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class BzryAssessDecorator extends BaseListDecorator<BzryAssess> {

    private final IUserService userService;
    public BzryAssessDecorator(List<BzryAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(BzryAssess bzryAssess) {
        User user = userService.selectById(bzryAssess.getUserId());
        if (user != null) {
            bzryAssess.putExpand("account", user.getAccount());
            bzryAssess.putExpand("name", user.getName());
        }
        bzryAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态", bzryAssess.getStatus()));

    }
}
