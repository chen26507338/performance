package com.stylefeng.guns.modular.user.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;

import java.util.List;

public class RewardsPunishmentDecorator extends BaseListDecorator<RewardsPunishment> {

    public RewardsPunishmentDecorator(List<RewardsPunishment> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(RewardsPunishment rewardsPunishment) {
        rewardsPunishment.setTypeDict( ConstantFactory.me().getDictsByName("奖惩种类",rewardsPunishment.getType()));
    }
}
