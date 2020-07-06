package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.PersonalInfo;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;

import java.util.List;

/**
 * 自然信息服务类
 *
 * @author cp
 * @Date 2020-07-06 10:02:41
 */
public interface IPersonalInfoService extends IService<PersonalInfo> {

    /**
     * 审核
     */
    void audit(PersonalInfo personalInfo);

    /**
     * 新增申请
     */
    void addApply(PersonalInfo personalInfo);
}