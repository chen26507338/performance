package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;

import java.util.List;

public class ScientificAchievementDecorator extends BaseListDecorator<ScientificAchievement> {
    private final IAssessNormService assessNormService;

    public ScientificAchievementDecorator(List<ScientificAchievement> list) {
        super(list);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(ScientificAchievement scientificAchievement) {
        AssessNorm assessNorm = assessNormService.selectById(scientificAchievement.getNormId());
        if (assessNorm != null) {
            scientificAchievement.setNormCode(assessNorm.getCode());
            scientificAchievement.setNormName(assessNorm.getContent());
        }
    }
}
