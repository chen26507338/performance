package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.DzbWorkAssess;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.assess.service.INormalAssessService;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;
import java.util.PrimitiveIterator;

public class DzbWorkAssessDecorator extends BaseListDecorator<DzbWorkAssess> {

    private final IDeptService deptService;
    private final IAssessNormService assessNormService;
    public DzbWorkAssessDecorator(List<DzbWorkAssess> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(DzbWorkAssess dzbWorkAssess) {
        Dept dept = deptService.selectById(dzbWorkAssess.getDeptId());
        if (dept != null) {
            dzbWorkAssess.putExpand("deptName", dept.getName());
        }

        AssessNorm norm = assessNormService.selectById(dzbWorkAssess.getNormId());
        if (norm != null) {
            dzbWorkAssess.putExpand("normCode", norm.getCode());
            dzbWorkAssess.putExpand("normContent", norm.getContent());
        }
        dzbWorkAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态", dzbWorkAssess.getStatus()));
    }
}
