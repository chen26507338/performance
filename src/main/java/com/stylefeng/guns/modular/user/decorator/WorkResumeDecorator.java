package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.WorkResume;

import java.util.List;

public class WorkResumeDecorator extends BaseListDecorator<WorkResume> {

    public WorkResumeDecorator(List<WorkResume> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(WorkResume workResume) {
    }
}
