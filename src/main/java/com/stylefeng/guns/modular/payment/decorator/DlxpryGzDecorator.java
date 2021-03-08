package com.stylefeng.guns.modular.payment.decorator;

import cn.hutool.core.date.DateUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.payment.model.DlxpryGz;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class DlxpryGzDecorator extends BaseListDecorator<DlxpryGz> {
    private final IUserService userService;


    public DlxpryGzDecorator(List<DlxpryGz> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(DlxpryGz dlxpryGz) {
        User user = userService.selectIgnorePointById(dlxpryGz.getUserId());
        dlxpryGz.putExpand("user", user);
    }
}
