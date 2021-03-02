package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;

import java.util.List;

/**
 * 考核系数服务类
 *
 * @author 
 * @Date 2020-02-25 10:45:56
 */
public interface IAssessCoefficientService extends IService<AssessCoefficient> {
    String CACHE_LIST = "assess_coefficient_list_";
    String CACHE_ENTITY = "assess_coefficient_entity_";

    /**
     * 类型：科研工作
     */
    String TYPE_KYGZ = "kygz";

    /**
     * 类型：专业建设
     */
    String TYPE_ZYJS = "zyjs";
    /**
     * 类型：学生工作
     */
    String TYPE_XSGZ = "xsgz";
    /**
     * 类型：教学工作
     */
    String TYPE_JXGZ = "jxgz";
    /**
     * 类型：管理服务
     */
    String TYPE_GLFW = "glfw";
    /**
     * 类型：专项工作
     */
    String TYPE_ZXGZ = "zxgz";
    /**
     * 类型：竞赛获奖
     */
    String TYPE_JSHJ = "jshj";
    /**
     * 类型：党支部工作
     */
    String TYPE_DZBGZ = "dzbgz";
    /**
     * 类型：社会培训工作
     */
    String TYPE_SHPXGZ = "shpxgz";
    /**
     * 类型：经费完成情况
     */
    String TYPE_JFWCQK = "jfwcqk";
    /**
     * 类型：实训绩效
     */
    String TYPE_SXJX = "sxjx";
    /**
     * 类型：辅导员思政工作
     */
    String TYPE_FDYSZGZ = "fdyszgz";
    /**
     * 类型：辅导员日常工作
     */
    String TYPE_FDYRCGZ = "fdyrcgz";
    /**
     * 类型：思政教师思政工作
     */
    String TYPE_SZJSSZGZ = "szjsszgz";
    /**
     * 类型：思政教师思政工作
     */
    String TYPE_BZRY = "bzry";
    /**
     * 查询所有
     */
    List<AssessCoefficient> selectAll();
}