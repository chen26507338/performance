package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.SxjxAssess;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class SxjxAssessDecorator extends BaseListDecorator<SxjxAssess> {
    private final IUserService userService;

    public SxjxAssessDecorator(List<SxjxAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(SxjxAssess sxjxAssess) {
        sxjxAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",sxjxAssess.getStatus()));
        User user = userService.selectIgnorePointById(sxjxAssess.getUserId());
        if (user != null) {
            sxjxAssess.putExpand("user",user);
        }
    }
}
