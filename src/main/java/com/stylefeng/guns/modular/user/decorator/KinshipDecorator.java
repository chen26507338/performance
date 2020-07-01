package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.Kinship;

import java.util.List;

public class KinshipDecorator extends BaseListDecorator<Kinship> {

    public KinshipDecorator(List<Kinship> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(Kinship kinship) {
        kinship.setRelationshipDict( ConstantFactory.me().getDictsByName("亲属关系",kinship.getRelationship()));
        kinship.setPoliticsStatusDict(ConstantFactory.me().getDictsByName("政治面貌",kinship.getPoliticsStatus()));
    }
}
