package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.assess.model.MajorBuildMember;

import java.util.List;

public class MajorBuildMemberDecorator extends BaseListDecorator<MajorBuildMember> {

    public MajorBuildMemberDecorator(List<MajorBuildMember> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(MajorBuildMember majorBuildMember) {
    }
}
