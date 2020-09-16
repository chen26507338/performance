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
    /**
     * 申请立项
     * @param majorBuild
     */
    void applyApproval(MajorBuild majorBuild);
    /**
     * 申请验收
     * @param majorBuild
     */
    void applyCheck(MajorBuild majorBuild);

    /**
     * 立项审核
     * @param majorBuild
     */
    void auditApproval(MajorBuild majorBuild);

    /**
     * 验收审核
     * @param majorBuild
     */
    void auditCheck(MajorBuild majorBuild);
}