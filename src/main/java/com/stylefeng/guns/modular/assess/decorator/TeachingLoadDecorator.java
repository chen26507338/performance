package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.TeachingLoad;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class TeachingLoadDecorator extends BaseListDecorator<TeachingLoad> {

    private final IUserService userService;
    private final IDeptService deptService;
    public TeachingLoadDecorator(List<TeachingLoad> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(TeachingLoad teachingLoad) {
        User user = userService.selectIgnorePointById(teachingLoad.getUserId());
        if (user != null) {
            teachingLoad.putExpand("user", user);
        }
        Dept dept = deptService.selectById(teachingLoad.getDeptId());
        if (dept != null) {
            teachingLoad.putExpand("dept", dept);
        }
        teachingLoad.putExpand("statusDict", ConstantFactory.me().getDictsByName("教学工作量状态", teachingLoad.getStatus()));
    }
}
