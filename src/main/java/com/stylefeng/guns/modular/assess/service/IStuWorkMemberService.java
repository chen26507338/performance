package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.StuWorkMember;

/**
 * 学生工作成员服务类
 *
 * @author cp
 * @Date 2020-09-16 15:26:17
 */
public interface IStuWorkMemberService extends IService<StuWorkMember> {

    void importAssess(StuWorkMember stuWorkMember);
}