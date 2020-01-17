package com.stylefeng.guns.modular.job.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
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
import com.stylefeng.guns.modular.job.model.JobTask;
import com.stylefeng.guns.modular.job.service.IJobTaskService;
import com.stylefeng.guns.modular.job.decorator.JobTaskDecorator;

/**
 * 工作任务控制器
 *
 * @author cp
 * @Date 2020-01-17 09:42:16
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jobTask")
public class JobTaskController extends BaseController {

    private String PREFIX = "/job/jobTask/";

    @Autowired
    private IJobTaskService jobTaskService;

    /**
     * 跳转到工作任务首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jobTask/list"})
    public String index() {
        return PREFIX + "jobTask.html";
    }

    /**
     * 跳转到添加工作任务
     */
    @RequestMapping("/jobTask_add")
    @RequiresPermissions(value = {"/jobTask/add"})
    public String jobTaskAdd() {
        return PREFIX + "jobTask_add.html";
    }

    /**
     * 跳转到修改工作任务
     */
    @RequestMapping("/jobTask_update/{jobTaskId}")
    @RequiresPermissions(value = {"/jobTask/update"})
    public String jobTaskUpdate(@PathVariable String jobTaskId, Model model) {
        JobTask jobTask = jobTaskService.selectById(jobTaskId);
        model.addAttribute("item",jobTask);
        LogObjectHolder.me().set(jobTask);
        return PREFIX + "jobTask_edit.html";
    }

    /**
     * 获取工作任务列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jobTask/list"})
    @ResponseBody
    public Object list(JobTask jobTask) {
        Page<JobTask> page = new PageFactory<JobTask>().defaultPage();
        EntityWrapper< JobTask> wrapper = new EntityWrapper<>();
         if(jobTask.getStartCreateTime() != null)
            wrapper.ge("create_time",jobTask.getStartCreateTime());
         if(jobTask.getEndCreateTime() != null)
            wrapper.le("create_time",jobTask.getEndCreateTime());
         if(jobTask.getStartEndTime() != null)
            wrapper.ge("end_time",jobTask.getStartEndTime());
         if(jobTask.getEndEndTime() != null)
            wrapper.le("end_time",jobTask.getEndEndTime());
        jobTaskService.selectPage(page,wrapper);
        page.setRecords(new JobTaskDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增工作任务
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jobTask/add"})
    @ResponseBody
    public Object add(JobTask jobTask) {
        jobTask.setCreateTime(new Date());
        jobTaskService.insert(jobTask);
        return SUCCESS_TIP;
    }

    /**
     * 删除工作任务
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jobTask/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobTaskId) {
        jobTaskService.deleteById(jobTaskId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工作任务
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jobTask/update"})
    @ResponseBody
    public Object update(JobTask jobTask) {
        jobTaskService.updateById(jobTask);
        return SUCCESS_TIP;
    }

    /**
     * 工作任务详情
     */
    @RequestMapping(value = "/detail/{jobTaskId}")
    @ResponseBody
    public Object detail(@PathVariable("jobTaskId") String jobTaskId) {
        return jobTaskService.selectById(jobTaskId);
    }
}
