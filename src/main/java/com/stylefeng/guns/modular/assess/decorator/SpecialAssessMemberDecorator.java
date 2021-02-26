package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.SpecialAssessMember;
import com.stylefeng.guns.modular.payment.service.ISpecialAssessService;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class SpecialAssessMemberDecorator extends BaseListDecorator<SpecialAssessMember> {
    private final IUserService userService;
    private final ISpecialAssessService specialAssessService;

    public SpecialAssessMemberDecorator(List<SpecialAssessMember> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        specialAssessService = SpringContextHolder.getBean(ISpecialAssessService.class);
    }

    @Override
    protected void decorateTheEntity(SpecialAssessMember specialAssessMember) {
        specialAssessMember.putExpand("user", userService.selectIgnorePointById(specialAssessMember.getUserId()));
        specialAssessMember.putExpand("sp", specialAssessService.selectById(specialAssessMember.getSaId()));
    }
}
