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
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.job.decorator.JobDecorator;

/**
 * 岗位管理控制器
 *
 * @author 
 * @Date 2020-01-17 09:37:54
 */
@Controller
@RequestMapping("${guns.admin-prefix}/job")
public class JobController extends BaseController {

    private String PREFIX = "/job/job/";

    @Autowired
    private IJobService jobService;

    /**
     * 跳转到岗位管理首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/job/list"})
    public String index() {
        return PREFIX + "job.html";
    }

    /**
     * 跳转到添加岗位管理
     */
    @RequestMapping("/job_add")
    @RequiresPermissions(value = {"/job/add"})
    public String jobAdd() {
        return PREFIX + "job_add.html";
    }

    /**
     * 跳转到修改岗位管理
     */
    @RequestMapping("/job_update/{jobId}")
    @RequiresPermissions(value = {"/job/update"})
    public String jobUpdate(@PathVariable String jobId, Model model) {
        Job job = jobService.selectById(jobId);
        model.addAttribute("item",job);
        LogObjectHolder.me().set(job);
        return PREFIX + "job_edit.html";
    }

    /**
     * 获取岗位管理列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/job/list"})
    @ResponseBody
    public Object list(Job job) {
        Page<Job> page = new PageFactory<Job>().defaultPage();
        EntityWrapper< Job> wrapper = new EntityWrapper<>();
        jobService.selectPage(page,wrapper);
        page.setRecords(new JobDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增岗位管理
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/job/add"})
    @ResponseBody
    public Object add(Job job) {
        jobService.insert(job);
        return SUCCESS_TIP;
    }

    /**
     * 删除岗位管理
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/job/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobId) {
        jobService.deleteById(jobId);
        return SUCCESS_TIP;
    }

    /**
     * 修改岗位管理
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/job/update"})
    @ResponseBody
    public Object update(Job job) {
        jobService.updateById(job);
        return SUCCESS_TIP;
    }

    /**
     * 岗位管理详情
     */
    @RequestMapping(value = "/detail/{jobId}")
    @ResponseBody
    public Object detail(@PathVariable("jobId") String jobId) {
        return jobService.selectById(jobId);
    }
}
