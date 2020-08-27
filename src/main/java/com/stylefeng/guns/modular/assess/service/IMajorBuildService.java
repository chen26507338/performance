package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.MajorBuild;

/**
 * 专业建设考核服务类
 *
 * @author 
 * @Date 2020-08-19 16:34:21
 */
public interface IMajorBuildService extends IService<MajorBuild> {
    void applyApproval(MajorBuild majorBuild);

    void auditApproval(MajorBuild majorBuild);
}