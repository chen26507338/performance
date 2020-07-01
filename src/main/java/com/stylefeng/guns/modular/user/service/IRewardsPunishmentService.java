package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;

import java.util.List;

/**
 * 考核奖惩服务类
 *
 * @author cp
 * @Date 2020-07-01 15:23:57
 */
public interface IRewardsPunishmentService extends IService<RewardsPunishment> {

    void audit(RewardsPunishment rewardsPunishment);

    void addApply(List<RewardsPunishment> rewardsPunishments);
}