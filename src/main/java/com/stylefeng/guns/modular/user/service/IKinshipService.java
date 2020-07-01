package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.model.WorkResume;

import java.util.List;

/**
 * 亲属关系服务类
 *
 * @author cp
 * @Date 2020-06-30 17:27:31
 */
public interface IKinshipService extends IService<Kinship> {

    /**
     *
     * @param kinship
     */
    void audit(Kinship kinship);

    /**
     * 添加申请
     * @param kinships
     */
    void addApply(List<Kinship> kinships);
}