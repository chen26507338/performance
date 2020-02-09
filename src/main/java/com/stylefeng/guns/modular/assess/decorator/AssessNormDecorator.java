package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.sun.mail.imap.protocol.ID;

import java.util.List;

public class AssessNormDecorator extends BaseListDecorator<AssessNorm> {

    private IDeptService deptService;

    public AssessNormDecorator(List<AssessNorm> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(AssessNorm assessNorm) {
        assessNorm.putExpand("typeDict", ConstantFactory.me().getDictsByName("考核项目",assessNorm.getType()));
        Dept dept = deptService.selectById(assessNorm.getDeptId());
        assessNorm.putExpand("deptDict", dept == null ? "校级" : dept.getName());
    }
}
