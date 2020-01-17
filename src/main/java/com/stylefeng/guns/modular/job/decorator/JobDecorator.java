package com.stylefeng.guns.modular.job.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;

import java.util.List;

public class JobDecorator extends BaseListDecorator<Job> {

    private IDeptService deptService;

    public JobDecorator(List<Job> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(Job job) {
        Dept dept = deptService.selectById(job.getDeptId());
        if (dept != null) {
            job.putExpand("deptIdDict", dept.getName());
        }
        job.putExpand("statusDict", ConstantFactory.me().getDictsByName("通用状态", job.getStatus()));
    }
}
