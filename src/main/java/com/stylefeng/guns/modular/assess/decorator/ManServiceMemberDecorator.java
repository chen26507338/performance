package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class ManServiceMemberDecorator extends BaseListDecorator<ManServiceMember> {

    private final IUserService userService;

    public ManServiceMemberDecorator(List<ManServiceMember> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(ManServiceMember manServiceMember) {
        User user = userService.selectIgnorePointById(manServiceMember.getUserId());
        if (user != null) {
            manServiceMember.putExpand("user",user);
        }
    }
}
