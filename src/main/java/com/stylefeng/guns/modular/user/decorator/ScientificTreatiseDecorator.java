package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;

import java.util.List;

public class ScientificTreatiseDecorator extends BaseListDecorator<ScientificTreatise> {

    private final IAssessNormService assessNormService;


    public ScientificTreatiseDecorator(List<ScientificTreatise> list) {
        super(list);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(ScientificTreatise scientificTreatise) {
        AssessNorm assessNorm = assessNormService.selectById(scientificTreatise.getNormId());
        if (assessNorm != null) {
            scientificTreatise.setNormCode(assessNorm.getCode());
            scientificTreatise.setNormName(assessNorm.getContent());
        }
    }
}
