package com.stylefeng.guns.modular.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.user.model.WorkResume;

import java.util.List;

/**
 * 工作简历服务类
 *
 * @author cp
 * @Date 2020-06-30 09:54:17
 */
public interface IWorkResumeService extends IService<WorkResume> {

    /**
     * 新增申请
     * @param workResumes
     */
    void addApply(List<WorkResume> workResumes);

    /**
     * 审核
     * @param workResume
     */
    void audit(WorkResume workResume);
}