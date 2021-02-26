package com.stylefeng.guns.modular.payment.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.payment.model.SpecialAssess;

import java.util.List;

public class SpecialAssessDecorator extends BaseListDecorator<SpecialAssess> {

    private final IDeptService deptService;
    public SpecialAssessDecorator(List<SpecialAssess> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(SpecialAssess specialAssess) {
        specialAssess.putExpand("isJrDict", ConstantFactory.me().getDictsByName("是否",specialAssess.getIsJr()));
        specialAssess.putExpand("isYjkhDict", ConstantFactory.me().getDictsByName("是否",specialAssess.getIsYjkh()));
        specialAssess.putExpand("isImportDict", ConstantFactory.me().getDictsByName("是否",specialAssess.getIsImport()));
        specialAssess.putExpand("dept", deptService.selectById(specialAssess.getDeptId()));
    }
}
