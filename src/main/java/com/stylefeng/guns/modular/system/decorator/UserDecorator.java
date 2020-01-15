package com.stylefeng.guns.modular.system.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import java.util.List;

public class UserDecorator extends BaseListDecorator<User> {
    public UserDecorator(List<User> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(User user) {
        user.putExpand("statusName", ConstantFactory.me().getStatusName(user.getStatus()));
        user.setAvatar(KaptchaUtil.formatFileUrl(user.getAvatar()));
    }
}
