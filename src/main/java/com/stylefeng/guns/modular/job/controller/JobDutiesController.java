package com.stylefeng.guns.modular.job.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.job.service.IJobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.stylefeng.guns.modular.job.model.JobDuties;
import com.stylefeng.guns.modular.job.service.IJobDutiesService;
import com.stylefeng.guns.modular.job.decorator.JobDutiesDecorator;

/**
 * 岗位职责管理控制器
 *
 * @author cp
 * @Date 2020-01-17 09:39:44
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jobDuties")
public class JobDutiesController extends BaseController {

    private String PREFIX = "/job/jobDuties/";

    @Autowired
    private IJobDutiesService jobDutiesService;
    @Autowired
    private IJobService jobService;
    /**
     * 跳转到岗位职责管理首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jobDuties/list"})
    public String index(Long jobId,Model model) {
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobList", jobService.selectAllOn());
        return PREFIX + "jobDuties.html";
    }

    /**
     * 跳转到添加岗位职责管理
     */
    @RequestMapping("/jobDuties_add")
    @RequiresPermissions(value = {"/jobDuties/add"})
    public String jobDutiesAdd(Long jobId,Model model) {
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobList", jobService.selectAllOn());
        return PREFIX + "jobDuties_add.html";
    }

    /**
     * 跳转到修改岗位职责管理
     */
    @RequestMapping("/jobDuties_update/{jobDutiesId}")
    @RequiresPermissions(value = {"/jobDuties/update"})
    public String jobDutiesUpdate(@PathVariable String jobDutiesId, Model model) {
        JobDuties jobDuties = jobDutiesService.selectById(jobDutiesId);
        model.addAttribute("jobList", jobService.selectAllOn());
        model.addAttribute("item",jobDuties);
        LogObjectHolder.me().set(jobDuties);
        return PREFIX + "jobDuties_edit.html";
    }

    /**
     * 获取岗位职责管理列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jobDuties/list"})
    @ResponseBody
    public Object list(JobDuties jobDuties) {
        Page<JobDuties> page = new PageFactory<JobDuties>().defaultPage();
        EntityWrapper< JobDuties> wrapper = new EntityWrapper<>();
        if (jobDuties.getJobId() != null) {
            wrapper.eq("job_id", jobDuties.getJobId());
        }
        jobDutiesService.selectPage(page,wrapper);
        page.setRecords(new JobDutiesDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增岗位职责管理
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jobDuties/add"})
    @ResponseBody
    public Object add(JobDuties jobDuties) {
        jobDutiesService.insert(jobDuties);
        return SUCCESS_TIP;
    }

    /**
     * 删除岗位职责管理
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jobDuties/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobDutiesId) {
        jobDutiesService.deleteById(jobDutiesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改岗位职责管理
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jobDuties/update"})
    @ResponseBody
    public Object update(JobDuties jobDuties) {
        jobDutiesService.updateById(jobDuties);
        return SUCCESS_TIP;
    }

    /**
     * 岗位职责管理详情
     */
    @RequestMapping(value = "/detail/{jobDutiesId}")
    @ResponseBody
    public Object detail(@PathVariable("jobDutiesId") String jobDutiesId) {
        return jobDutiesService.selectById(jobDutiesId);
    }
}
