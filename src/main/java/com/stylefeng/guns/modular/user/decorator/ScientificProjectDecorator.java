package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;

public class ScientificProjectDecorator extends BaseListDecorator<ScientificProject> {

    private final IAssessNormService assessNormService;


    public ScientificProjectDecorator(List<ScientificProject> list) {
        super(list);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(ScientificProject scientificProject) {
        AssessNorm assessNorm = assessNormService.selectById(scientificProject.getNormId());
        if (assessNorm != null) {
            scientificProject.setNormCode(assessNorm.getCode());
            scientificProject.setNormName(assessNorm.getContent());
        }
    }
}
