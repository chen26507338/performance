package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;

/**
 * 科研项目服务类
 *
 * @author cp
 * @Date 2020-07-02 12:39:00
 */
public interface IScientificProjectService extends IService<ScientificProject> {

    void addApply(List<ScientificProject> scientificProjects);

    void audit(ScientificProject scientificProject);

    /**
     * 导入现有绩效
     * @param scientificProject
     */
    void importAssess(ScientificProject scientificProject);
}