package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.SzgzAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class SzgzAssessDecorator extends BaseListDecorator<SzgzAssess> {

    private IUserService userService;
    public SzgzAssessDecorator(List<SzgzAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(SzgzAssess szgzAssess) {
        User user = userService.selectById(szgzAssess.getUserId());
        if (user != null) {
            szgzAssess.putExpand("account", user.getAccount());
            szgzAssess.putExpand("name", user.getName());
        }
    }
}
