package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.StuWorkMember;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class StuWorkMemberDecorator extends BaseListDecorator<StuWorkMember> {

    private final IUserService userService;
    public StuWorkMemberDecorator(List<StuWorkMember> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(StuWorkMember stuWorkMember) {
        User user = userService.selectIgnorePointById(stuWorkMember.getUserId());
        if (user != null) {
            stuWorkMember.setName(user.getName());
        }
    }
}
