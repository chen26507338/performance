package com.stylefeng.guns.modular.payment.controller;

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
import com.stylefeng.guns.modular.payment.model.JobPriceYear;
import com.stylefeng.guns.modular.payment.service.IJobPriceYearService;
import com.stylefeng.guns.modular.payment.decorator.JobPriceYearDecorator;

/**
 * 年度岗位责任奖控制器
 *
 * @author 
 * @Date 2020-10-19 22:04:01
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jobPriceYear")
public class JobPriceYearController extends BaseController {

    private String PREFIX = "/payment/jobPriceYear/";

    @Autowired
    private IJobPriceYearService jobPriceYearService;

    /**
     * 跳转到年度岗位责任奖首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jobPriceYear/list"})
    public String index() {
        return PREFIX + "jobPriceYear.html";
    }

    /**
     * 跳转到添加年度岗位责任奖
     */
    @RequestMapping("/jobPriceYear_add")
    @RequiresPermissions(value = {"/jobPriceYear/add"})
    public String jobPriceYearAdd() {
        return PREFIX + "jobPriceYear_add.html";
    }

    /**
     * 跳转到修改年度岗位责任奖
     */
    @RequestMapping("/jobPriceYear_update/{jobPriceYearId}")
    @RequiresPermissions(value = {"/jobPriceYear/update"})
    public String jobPriceYearUpdate(@PathVariable String jobPriceYearId, Model model) {
        JobPriceYear jobPriceYear = jobPriceYearService.selectById(jobPriceYearId);
        model.addAttribute("item",jobPriceYear);
        LogObjectHolder.me().set(jobPriceYear);
        return PREFIX + "jobPriceYear_edit.html";
    }

    /**
     * 获取年度岗位责任奖列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jobPriceYear/list"})
    @ResponseBody
    public Object list(JobPriceYear jobPriceYear) {
        Page<JobPriceYear> page = new PageFactory<JobPriceYear>().defaultPage();
        EntityWrapper< JobPriceYear> wrapper = new EntityWrapper<>();
        jobPriceYearService.selectPage(page,wrapper);
        page.setRecords(new JobPriceYearDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增年度岗位责任奖
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jobPriceYear/add"})
    @ResponseBody
    public Object add(JobPriceYear jobPriceYear) {
        jobPriceYearService.insert(jobPriceYear);
        return SUCCESS_TIP;
    }

    /**
     * 删除年度岗位责任奖
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jobPriceYear/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobPriceYearId) {
        jobPriceYearService.deleteById(jobPriceYearId);
        return SUCCESS_TIP;
    }

    /**
     * 修改年度岗位责任奖
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jobPriceYear/update"})
    @ResponseBody
    public Object update(JobPriceYear jobPriceYear) {
        jobPriceYearService.updateById(jobPriceYear);
        return SUCCESS_TIP;
    }

    /**
     * 年度岗位责任奖详情
     */
    @RequestMapping(value = "/detail/{jobPriceYearId}")
    @ResponseBody
    public Object detail(@PathVariable("jobPriceYearId") String jobPriceYearId) {
        return jobPriceYearService.selectById(jobPriceYearId);
    }
}
