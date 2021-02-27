package com.stylefeng.guns.modular.payment.decorator;

import cn.hutool.core.date.DateUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.payment.model.PqryGz;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class PqryGzDecorator extends BaseListDecorator<PqryGz> {
    private final IUserService userService;

    public PqryGzDecorator(List<PqryGz> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(PqryGz pqryGz) {
        User user = userService.selectIgnorePointById(pqryGz.getUserId());
        pqryGz.putExpand("user", user);
        pqryGz.putExpand("time", DateUtil.format(pqryGz.getInTime(), "yyyy.MM"));
    }
}
