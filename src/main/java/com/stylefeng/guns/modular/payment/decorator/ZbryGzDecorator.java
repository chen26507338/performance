package com.stylefeng.guns.modular.payment.decorator;

import cn.hutool.core.date.DateUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.payment.model.ZbryGz;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class ZbryGzDecorator extends BaseListDecorator<ZbryGz> {

    private final IUserService userService;

    public ZbryGzDecorator(List<ZbryGz> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(ZbryGz zbryGz) {
        User user = userService.selectIgnorePointById(zbryGz.getUserId());
        zbryGz.putExpand("user", user);
        zbryGz.putExpand("time", DateUtil.format(zbryGz.getInTime(), "yyyy.MM"));
    }
}
