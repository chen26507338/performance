package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.MajorBuildMember;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class MajorBuildMemberDecorator extends BaseListDecorator<MajorBuildMember> {

    private IUserService userService;

    public MajorBuildMemberDecorator(List<MajorBuildMember> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(MajorBuildMember majorBuildMember) {
        User user = userService.selectIgnorePointById(majorBuildMember.getUserId());
        if (user != null) {
            majorBuildMember.setName(user.getName());
        }
    }
}
