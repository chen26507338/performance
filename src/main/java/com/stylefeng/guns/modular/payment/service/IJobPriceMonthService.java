package com.stylefeng.guns.modular.payment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.payment.model.JobPriceMonth;

/**
 * 月度岗位责任奖服务类
 *
 * @author 
 * @Date 2020-10-18 11:22:30
 */
public interface IJobPriceMonthService extends IService<JobPriceMonth> {

    void audit(JobPriceMonth jobPriceMonth);

    void apply(JobPriceMonth jobPriceMonth);

    void importData(JobPriceMonth jobPriceMonth);
}