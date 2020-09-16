package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.MajorBuild;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class MajorBuildDecorator extends BaseListDecorator<MajorBuild> {

    private IUserService userService;
    public MajorBuildDecorator(List<MajorBuild> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(MajorBuild majorBuild) {
        User user = userService.selectIgnorePointById(majorBuild.getPrincipalId());
        if (user != null) {
            majorBuild.putExpand("user", user);
        }
        majorBuild.putExpand("statusDict", ConstantFactory.me().getDictsByName("专业建设考核状态", majorBuild.getStatus()));
    }
}
