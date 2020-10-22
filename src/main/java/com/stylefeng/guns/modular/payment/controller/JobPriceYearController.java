package com.stylefeng.guns.modular.payment.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.modular.payment.decorator.JobPriceMonthDecorator;
import com.stylefeng.guns.modular.payment.model.JobPriceMonth;
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

import java.io.IOException;
import java.util.*;
import com.stylefeng.guns.modular.payment.model.JobPriceYear;
import com.stylefeng.guns.modular.payment.service.IJobPriceYearService;
import com.stylefeng.guns.modular.payment.decorator.JobPriceYearDecorator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping("/exportData")
    public void exportData() throws IOException {
        List<JobPriceYear> jobPriceMonths = jobPriceYearService.selectList(new EntityWrapper<>());
        JobPriceYear total = new JobPriceYear();
        total.putExpand("account", "总计");
        total.putExpand("name", " ");
        total.putExpand("totalYear",0d);
        for (int i = 1; i < 13; i++) {
            ReflectUtil.setFieldValue(total, "month" + i, 0d);
        }
        for (JobPriceYear priceYear : jobPriceMonths) {
            double totalYear = 0;
            double sumTotalYear = (double) total.getExpand().get("totalYear");
            for (int i = 1; i < 13; i++) {
                double monthPrice = (double) ReflectUtil.getFieldValue(priceYear, "month" + i);
                double totalMonthPrice = (double) ReflectUtil.getFieldValue(total, "month" + i);
                ReflectUtil.setFieldValue(total, "month" + i, monthPrice + totalMonthPrice);
                totalYear += monthPrice;
            }
            priceYear.putExpand("totalYear", totalYear);
            total.putExpand("totalYear", totalYear + sumTotalYear);
        }
        jobPriceMonths.add(total);
        List<Map<String, Object>> datas = new JobPriceYearDecorator(jobPriceMonths).decorateMaps();

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("account", "职工编号");
        writer.addHeaderAlias("name", "职工姓名");
        writer.addHeaderAlias("month1", "一月");
        writer.addHeaderAlias("month2", "二月");
        writer.addHeaderAlias("month3", "三月");
        writer.addHeaderAlias("month4", "四月");
        writer.addHeaderAlias("month5", "五月");
        writer.addHeaderAlias("month6", "六月");
        writer.addHeaderAlias("month7", "七月");
        writer.addHeaderAlias("month8", "八月");
        writer.addHeaderAlias("month9", "九月");
        writer.addHeaderAlias("month10", "十月");
        writer.addHeaderAlias("month11", "十一月");
        writer.addHeaderAlias("month12", "十二月");
        writer.addHeaderAlias("totalYear", "总计");
        writer.setOnlyAlias(true);
        writer.write(datas, true);
        HttpServletResponse response = HttpKit.getResponse();

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", URLUtil.encode(StrUtil.format("attachment;filename=年度责任岗位奖.xlsx")));
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

}
