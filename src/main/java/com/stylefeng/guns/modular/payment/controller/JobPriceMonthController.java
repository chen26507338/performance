package com.stylefeng.guns.modular.payment.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.modular.payment.decorator.JobPriceMonthDecorator;
import com.stylefeng.guns.modular.payment.model.JobPriceMonth;
import com.stylefeng.guns.modular.payment.service.IJobPriceMonthService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;
;

/**
 * 月度岗位责任奖控制器
 *
 * @author 
 * @Date 2020-10-18 11:22:30
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jobPriceMonth")
public class JobPriceMonthController extends BaseController {

    private String PREFIX = "/payment/jobPriceMonth/";

    @Autowired
    private IJobPriceMonthService jobPriceMonthService;

    /**
     * 跳转到月度岗位责任奖首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jobPriceMonth/list"})
    public String index() {
        return PREFIX + "jobPriceMonth.html";
    }

    /**
     * 跳转到添加月度岗位责任奖
     */
    @RequestMapping("/jobPriceMonth_add")
    @RequiresPermissions(value = {"/jobPriceMonth/add"})
    public String jobPriceMonthAdd() {
        return PREFIX + "jobPriceMonth_add.html";
    }

    /**
     * 跳转到修改月度岗位责任奖
     */
    @RequestMapping("/jobPriceMonth_update/{jobPriceMonthId}")
    @RequiresPermissions(value = {"/jobPriceMonth/update"})
    public String jobPriceMonthUpdate(@PathVariable String jobPriceMonthId, Model model) {
        JobPriceMonth jobPriceMonth = jobPriceMonthService.selectById(jobPriceMonthId);
        model.addAttribute("item",jobPriceMonth);
        LogObjectHolder.me().set(jobPriceMonth);
        return PREFIX + "jobPriceMonth_edit.html";
    }

    /**
     * 获取月度岗位责任奖列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jobPriceMonth/list"})
    @ResponseBody
    public Object list(JobPriceMonth jobPriceMonth) {
        Page<JobPriceMonth> page = new PageFactory<JobPriceMonth>().defaultPage();
        EntityWrapper< JobPriceMonth> wrapper = new EntityWrapper<>();
        if (StrUtil.isNotBlank(jobPriceMonth.getMonth())) {
            wrapper.eq("month", jobPriceMonth.getMonth());
        }
        jobPriceMonthService.selectPage(page,wrapper);
        page.setRecords(new JobPriceMonthDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增月度岗位责任奖
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jobPriceMonth/add"})
    @ResponseBody
    public Object add(JobPriceMonth jobPriceMonth) {
        jobPriceMonthService.insert(jobPriceMonth);
        return SUCCESS_TIP;
    }

    /**
     * 删除月度岗位责任奖
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jobPriceMonth/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jobPriceMonthId) {
        jobPriceMonthService.deleteById(jobPriceMonthId);
        return SUCCESS_TIP;
    }

    /**
     * 修改月度岗位责任奖
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jobPriceMonth/update"})
    @ResponseBody
    public Object update(JobPriceMonth jobPriceMonth) {
        jobPriceMonthService.updateById(jobPriceMonth);
        return SUCCESS_TIP;
    }

    /**
     * 月度岗位责任奖详情
     */
    @RequestMapping(value = "/detail/{jobPriceMonthId}")
    @ResponseBody
    public Object detail(@PathVariable("jobPriceMonthId") String jobPriceMonthId) {
        return jobPriceMonthService.selectById(jobPriceMonthId);
    }



    /**
     * 考核申请
     */
    @RequestMapping("/jobPriceMonth_import")
    public String jobPriceMonthImport() {
        return PREFIX + "jobPriceMonth_import.html";
    }

    /**
     * 考核申请
     */
    @RequestMapping("/import_data")
    public String jobPriceImport() {
        return PREFIX + "jobPriceMonth_importdata.html";
    }

    /**
     *
     */
    @RequestMapping("/importData")
    @ResponseBody
    public Object importData(JobPriceMonth jobPriceMonth) {
        jobPriceMonthService.importData(jobPriceMonth);
        return SUCCESS_TIP;
    }

    /**
     *
     */
    @RequestMapping("/exportData")
    public void exportData(JobPriceMonth jobPriceMonth) throws IOException {
        List<JobPriceMonth> jobPriceMonths = jobPriceMonthService.selectList(new EntityWrapper<>(jobPriceMonth));
        JobPriceMonth total = new JobPriceMonth();
        total.putExpand("account", "总计");
        total.putExpand("name", " ");
        total.setResultPrice(0d);
        total.setShouldPrice(0d);
        total.setBasePrice(0d);
        total.setMgrPrice(0d);
        total.setRetroactivePrice(0d);
        total.setGarnishedPrice(0d);
        for (JobPriceMonth priceMonth : jobPriceMonths) {
            total.setBasePrice(priceMonth.getBasePrice() + total.getBasePrice());
            total.setMgrPrice(priceMonth.getMgrPrice() + total.getMgrPrice());
            total.setRetroactivePrice(priceMonth.getRetroactivePrice() + total.getRetroactivePrice());
            total.setShouldPrice(priceMonth.getShouldPrice() + total.getShouldPrice());
            total.setGarnishedPrice(priceMonth.getGarnishedPrice() + total.getGarnishedPrice());
            total.setResultPrice(priceMonth.getResultPrice() + total.getResultPrice());
        }
        jobPriceMonths.add(total);
        List<Map<String, Object>> datas = new JobPriceMonthDecorator(jobPriceMonths).decorateMaps();

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("account", "职工编号");
        writer.addHeaderAlias("name", "职工姓名");
        writer.addHeaderAlias("basePrice", "基本岗位责任奖");
        writer.addHeaderAlias("mgrPrice", "管理服务工作奖");
        writer.addHeaderAlias("retroactivePrice", "补发");
        writer.addHeaderAlias("shouldPrice", "应发数");
        writer.addHeaderAlias("garnishedPrice", "扣发");
        writer.addHeaderAlias("resultPrice", "实发数");
        writer.addHeaderAlias("remark", "备注");
        writer.setOnlyAlias(true);
        writer.write(datas, true);
        HttpServletResponse response = HttpKit.getResponse();

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", URLUtil.encode(StrUtil.format("attachment;filename={}月份责任岗位奖.xlsx",jobPriceMonth.getMonth())));
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(JobPriceMonth jobPriceMonth) {
        jobPriceMonthService.apply(jobPriceMonth);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(JobPriceMonth jobPriceMonth) {
        jobPriceMonthService.audit(jobPriceMonth);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(JobPriceMonth jobPriceMonth) {
        JobPriceMonth params = new JobPriceMonth();
        params.setProcInsId(jobPriceMonth.getProcInsId());
        List<Map<String,Object>> datas = new JobPriceMonthDecorator(params.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(JobPriceMonth jobPriceMonth, Model model) {
        jobPriceMonth.setProcInsId(jobPriceMonth.getAct().getProcInsId());
        EntityWrapper<JobPriceMonth> wrapper = new EntityWrapper<>(jobPriceMonth);
        JobPriceMonth data = jobPriceMonth.selectOne(wrapper);
        model.addAttribute("item", data);
        model.addAttribute("act", jobPriceMonth.getAct());
        return PREFIX + "jobPriceMonth_audit.html";
    }
}
