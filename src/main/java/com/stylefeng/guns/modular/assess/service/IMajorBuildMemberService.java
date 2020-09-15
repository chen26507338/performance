package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.MajorBuildMember;

/**
 * 专业建设项目成员服务类
 *
 * @author 
 * @Date 2020-08-19 16:35:13
 */
public interface IMajorBuildMemberService extends IService<MajorBuildMember> {
    /**
     * 待立项
     */
    int STATS_APPROVAL_WAIT = 0;
    /**
     * 已立项
     */
    int STATS_APPROVAL_SUCCESS = 1;
}