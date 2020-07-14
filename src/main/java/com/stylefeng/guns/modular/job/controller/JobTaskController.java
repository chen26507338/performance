package com.stylefeng.guns.modular.job.controller;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.job.decorator.JobTaskApplyDecorator;
import com.stylefeng.guns.modular.job.model.*;
import com.stylefeng.guns.modular.job.service.IJobDutiesService;
import com.stylefeng.guns.modular.job.service.IJobTaskPointService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.log.LogObjectHolder;

import java.util.*;

import com.stylefeng.guns.modular.job.service.IJobTaskService;
import com.stylefeng.guns.modular.job.decorator.JobTaskDecorator;

import javax.jws.soap.SOAPBinding;

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
    @Autowired
    private IJobTaskPointService jobTaskPointService;
    @Autowired
    private IUserService userService;

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
    public String jobTaskAdd(Model model) {
        User user = (User) ShiroKit.getUser().getUser();
        List<User> users = userService.selectByDeptWithOutUser(user);
        model.addAttribute("userList", users);
        return PREFIX + "jobTask_add.html";
    }

    /**
     * 跳转到汇报任务
     */
    @RequestMapping("/jobTask_report")
    public String jobTaskReport(Model model) {
        model.addAttribute("userId", ShiroKit.getUser().id);
        return PREFIX + "jobTask_report.html";
    }

    /**
     * 新增汇报任务
     */
    @RequestMapping(value = "/reportAdd")
    @ResponseBody
    public Object reportAdd(JobTask jobTask) {
        jobTask.setCreateTime(new Date());
        User user = (User) ShiroKit.getUser().getUser();
        jobTask.setUserId(user.getId());
        jobTask.setDeptId(user.getDeptId());
        jobTaskService.addReport(jobTask);
        return SUCCESS_TIP;
    }



    /**
     * 跳转到汇报工作任务
     */
    @RequestMapping("/doReport")
    public String doReport(JobTask jobTask, Model model) {
        JobTask temp = jobTaskService.selectById(jobTask.getId());

        switch (jobTask.getAct().getTaskDefKey()) {
            case "score":
                double userPoint;
                double applyPoint = 0;
                double appointPoint = 0;
                if (temp.getApplyUserId() == null && temp.getAppointUserId() == null) {
                    userPoint = temp.getPoint();
                } else {
                    userPoint = temp.getPoint() * 0.7;
                    double leftPoint = temp.getPoint() - userPoint;
                    if (temp.getAppointUserId() == null && temp.getApplyUserId() != null) {
                        applyPoint = leftPoint;
                    } else if (temp.getAppointUserId() != null && temp.getApplyUserId() == null) {
                        appointPoint = leftPoint;
                    } else {
                        applyPoint = leftPoint / 2;
                        appointPoint = applyPoint;
                    }
                }
                model.addAttribute("userPoint", userPoint);
                model.addAttribute("applyPoint", applyPoint);
                model.addAttribute("appointPoint", appointPoint);
                break;
            default:
        }
        model.addAttribute("userId", ShiroKit.getUser().id);
        model.addAttribute("item",temp);
        model.addAttribute("act",jobTask.getAct());
        LogObjectHolder.me().set(jobTask);
        return PREFIX + "jobTask_doReport.html";
    }

    /**
     * 修改工作任务
     */
    @RequestMapping(value = "/handleReport")
    @ResponseBody
    public Object handleReport(JobTask jobTask) {
        jobTaskService.handleReport(jobTask);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到修改工作任务
     */
    @RequestMapping("/jobTask_update")
    public String jobTaskUpdate(JobTask jobTask, Model model) {
        JobTask temp = jobTaskService.selectById(jobTask.getId());
        User user = (User) ShiroKit.getUser().getUser();
        List<User> userList = userService.selectByDeptWithOutUser(user);

        switch (jobTask.getAct().getTaskDefKey()) {
            case "re_nominate_appoint":
                //去除经办人和经办协助人
                userList.removeIf(tempUser -> tempUser.getId().equals(temp.getUserId())
                        ||tempUser.getId().equals(temp.getApplyUserId()));
                model.addAttribute("userList", userList);
                break;
            case "re_nominate_apply":
            case "user_confirm":
                //去除发起人和委派协助人
                userList.removeIf(tempUser -> tempUser.getId().equals(temp.getAppointUserId()) ||
                        tempUser.getId().equals(temp.getStartUserId()));
                model.addAttribute("userList", userList);
                break;
            case "score":
                double userPoint;
                double applyPoint = 0;
                double appointPoint = 0;
                if (temp.getApplyUserId() == null && temp.getAppointUserId() == null) {
                    userPoint = temp.getPoint();
                } else {
                    userPoint = temp.getPoint() * 0.7;
                    double leftPoint = temp.getPoint() - userPoint;
                    if (temp.getAppointUserId() == null && temp.getApplyUserId() != null) {
                        applyPoint = leftPoint;
                    } else if (temp.getAppointUserId() != null && temp.getApplyUserId() == null) {
                        appointPoint = leftPoint;
                    } else {
                        applyPoint = leftPoint / 2;
                        appointPoint = applyPoint;
                    }
                }
                model.addAttribute("userPoint", userPoint);
                model.addAttribute("applyPoint", applyPoint);
                model.addAttribute("appointPoint", appointPoint);
            case "user_summary":
            case "summary_confirm":
                JobTaskApply params = new JobTaskApply();
                params.setTaskId(jobTask.getId());
                List<JobTaskApply> jobTaskApplies = params.selectList(new EntityWrapper<>(params));
                model.addAttribute("jobTaskApplies", new JobTaskApplyDecorator(jobTaskApplies).decorateMaps());
                break;
            default:
        }
        model.addAttribute("item",temp);
        model.addAttribute("act",jobTask.getAct());
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
        if (jobTask.getStatus() != null) {
            wrapper.eq("status", jobTask.getStatus());
        }
        if (jobTask.getType() != null) {
            wrapper.eq("type", jobTask.getType());
        }
        jobTaskService.selectPage(page,wrapper);
        page.setRecords(new JobTaskDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增工作任务
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(JobTask jobTask) {
        jobTask.setCreateTime(new Date());
        User user = (User) ShiroKit.getUser().getUser();
        jobTask.setStartUserId(user.getId());
        jobTask.setDeptId(user.getDeptId());
        jobTaskService.insert(jobTask);
        return SUCCESS_TIP;
    }

    /**
     * 删除工作任务
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long jobTaskId) {
        jobTaskService.deleteById(jobTaskId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工作任务
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(JobTask jobTask) {
        jobTaskService.updateById(jobTask);
        return SUCCESS_TIP;
    }

    /**
     * 工作任务详情
     */
    @GetMapping(value = "/detail/{jobTaskId}")
    public String detail(@PathVariable("jobTaskId") String jobTaskId, Model model) {
        JobTask jobTask = jobTaskService.selectById(jobTaskId);
        JobTaskPoint taskPointParams = new JobTaskPoint();
        taskPointParams.setTaskId(jobTask.getId());
        taskPointParams.setUserId(jobTask.getUserId());
        JobTaskPoint userPoint = jobTaskPointService.selectOne(new EntityWrapper<>(taskPointParams));
        model.addAttribute("userPoint", userPoint);
        model.addAttribute("item", jobTask);
        if (jobTask.getType() == IJobTaskService.TYPE_APPOINT) {
            return "";
        } else {
            return PREFIX + "jobTask_report_detail.html";
        }
    }
}
