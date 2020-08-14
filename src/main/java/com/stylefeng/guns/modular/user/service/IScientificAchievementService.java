package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;

/**
 * 科研成果服务类
 *
 * @author cp
 * @Date 2020-08-14 15:55:50
 */
public interface IScientificAchievementService extends IService<ScientificAchievement> {
    void addApply(List<ScientificAchievement> scientificAchievements);

    void audit(ScientificAchievement scientificAchievement);
}