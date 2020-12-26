package com.stylefeng.guns.modular.assess.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.assess.model.YearJsAssess;

/**
 * 教师考核服务类
 *
 * @author
 * @Date 2020-12-21 22:58:22
 */
public interface IYearJsAssessService extends IService<YearJsAssess> {

    int STATUS_WAIT_AUDIT = 1;

    /**
     * 考核类型：教师考核
     */
    int TYPE_JS = 1;

    void apply(YearJsAssess yearJsAssess);

    void audit(YearJsAssess yearJsAssess);
}