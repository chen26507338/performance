package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.job.model.Dept;

import java.util.List;

public class DeptDecorator extends BaseListDecorator<Dept> {

    public DeptDecorator(List<Dept> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(Dept dept) {
        dept.putExpand("statusDict", ConstantFactory.me().getDictsByName("通用状态",dept.getStatus()));
    }
}
