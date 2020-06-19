package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.EducationExperience;

import java.util.List;

public class EducationExperienceDecorator extends BaseListDecorator<EducationExperience> {

    public EducationExperienceDecorator(List<EducationExperience> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(EducationExperience educationExperience) {
        educationExperience.putExpand("educationBackgroundDict", ConstantFactory.me().getDictsByName("学历",educationExperience.getEducationBackground()));
        educationExperience.putExpand("degreeDict", ConstantFactory.me().getDictsByName("学位",educationExperience.getDegree()));
        educationExperience.putExpand("learnStyleDict", ConstantFactory.me().getDictsByName("学习方式",educationExperience.getLearnStyle()));
    }
}
