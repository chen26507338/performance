package com.stylefeng.guns.modular.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.job.model.Dept;

import java.util.List;

/**
 * 部门管理服务类
 *
 * @author cp
 * @Date 2020-01-17 09:34:46
 */
public interface IDeptService extends IService<Dept> {
    String CACHE_LIST = "dept_list_";
    String CACHE_ENTITY = "dept_entity_";

    /**
     * 部门ID：人事处
     */
    long HR = 1224197239301328897L;

    /**
     * 部门ID：学生处
     */
    long STU_WORK = 1306141570545934337L;

    /**
     * 部门ID：科技处
     */
    long SCI = 1293432010613932034L;

    /**
     * 获取所有启用部门
     * @return
     */
    List<Dept> selectAllOn();
}