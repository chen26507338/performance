package com.stylefeng.guns.modular.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;

import java.util.List;

/**
 * 岗位管理服务类
 *
 * @author 
 * @Date 2020-01-17 09:37:54
 */
public interface IJobService extends IService<Job> {
    String CACHE_LIST = "job_list_";
    String CACHE_ENTITY = "job_entity_";

    /**
     * 获取所有启用岗位
     * @return
     */
    List<Job> selectAllOn();

}