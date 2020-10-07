package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.FdygzAssessMember;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class FdygzAssessMemberDecorator extends BaseListDecorator<FdygzAssessMember> {

    private final IUserService userService;

    public FdygzAssessMemberDecorator(List<FdygzAssessMember> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(FdygzAssessMember fdygzAssessMember) {
        User user = userService.selectIgnorePointById(fdygzAssessMember.getUserId());
        if (user != null) {
            fdygzAssessMember.putExpand("name",user.getName());
            fdygzAssessMember.putExpand("account",user.getAccount());
        }
    }
}
