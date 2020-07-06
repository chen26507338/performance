package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.PersonalInfo;

import java.util.List;

public class PersonalInfoDecorator extends BaseListDecorator<PersonalInfo> {

    public PersonalInfoDecorator(List<PersonalInfo> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(PersonalInfo personalInfo) {
        personalInfo.putExpand("jobTypeDict", ConstantFactory.me().getDictsByName("用工类型",personalInfo.getJobType()));
        personalInfo.putExpand("emergencyRelationDict", ConstantFactory.me().getDictsByName("紧急联系人关系",personalInfo.getEmergencyRelation()));
        personalInfo.putExpand("sexDict", ConstantFactory.me().getDictsByName("性别",personalInfo.getSex()));
        personalInfo.putExpand("personalStateDict", ConstantFactory.me().getDictsByName("人员状态",personalInfo.getPersonalState()));
    }
}
