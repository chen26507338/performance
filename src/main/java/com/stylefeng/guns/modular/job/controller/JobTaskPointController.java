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
import com.stylefeng.guns.modular.job.model.JobTaskPoint;
import com.stylefeng.guns.modular.job.service.IJobTaskPointService;
import com.stylefeng.guns.modular.job.decorator.JobTaskPointDecorator;

/**
 * 工作得分控制器
 *
 * @author cp
 * @Date 2020-01-17 09:44:27
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jobTaskPoint")
public class JobTaskPointController extends BaseController {

    private String PREFIX = "/job/jobTaskPoint/";

    @Autowired
    private IJobTaskPointService jobTaskPointService;

    /**
     * 跳转到工作得分首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jobTaskPoint/list"})
    public String index() {
        return PREFIX + "jobTaskPoint.html";
    }

    /**
     * 跳转到添加工作得分
     */
    @RequestMapping("/jobTaskPoint_add")
    @RequiresPermissions(value = {"/jobTaskPoint/add"})
    public String jobTaskPointAdd() {
        return PREFIX + "jobTaskPoint_add.html";
    }

    /**
     * 跳转到修改工作得分
     */
    @RequestMapping("/jobTaskPoint_update/{jobTaskPointId}")
    @RequiresPermissions(value = {"/jobTaskPoint/update"})
    public String jobTaskPointUpdate(@PathVariable String jobTaskPointId, Model model) {
        JobTaskPoint jobTaskPoint = jobTaskPointService.selectById(jobTaskPointId);
        model.addAttribute("item",jobTaskPoint);
        LogObjectHolder.me().set(jobTaskPoint);
        return PREFIX + "jobTaskPoint_edit.html";
    }

    /**
     * 获取工作得分列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jobTaskPoint/list"})
    @ResponseBody
    public Object list(JobTaskPoint jobTaskPoint) {
        Page<JobTaskPoint> page = new PageFactory<JobTaskPoint>().defaultPage();
        EntityWrapper< JobTaskPoint> wrapper = new EntityWrapper<>();
         if(jobTaskPoint.getStartCreateTime() != null)
            wrapper.ge("create_time",jobTaskPoint.getStartCreateTime());
         if(jobTaskPoint.getEndCreateTime() != null)
            wrapper.le("create_time",jobTaskPoint.getEndCreateTime());
        jobTaskPointService.selectPage(page,wrapper);
        page.setRecords(new JobTaskPointDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增工作得分
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jobTaskPoint/add"})
    @ResponseBody
    public Object add(JobTaskPoint jobTaskPoint) {
        jobTaskPoint.setCreateTime(new Date());
        jobTaskPointService.insert(jobTaskPoint);
        return SUCCESS_TIP;
    }

    /**
     * 删除工作得分
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jobTaskPoint/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobTaskPointId) {
        jobTaskPointService.deleteById(jobTaskPointId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工作得分
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jobTaskPoint/update"})
    @ResponseBody
    public Object update(JobTaskPoint jobTaskPoint) {
        jobTaskPointService.updateById(jobTaskPoint);
        return SUCCESS_TIP;
    }

    /**
     * 工作得分详情
     */
    @RequestMapping(value = "/detail/{jobTaskPointId}")
    @ResponseBody
    public Object detail(@PathVariable("jobTaskPointId") String jobTaskPointId) {
        return jobTaskPointService.selectById(jobTaskPointId);
    }
}
