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
import com.stylefeng.guns.modular.job.model.AllocationPointLog;
import com.stylefeng.guns.modular.job.service.IAllocationPointLogService;
import com.stylefeng.guns.modular.job.decorator.AllocationPointLogDecorator;

/**
 * 分配分数记录控制器
 *
 * @author 
 * @Date 2020-09-25 23:04:38
 */
@Controller
@RequestMapping("${guns.admin-prefix}/allocationPointLog")
public class AllocationPointLogController extends BaseController {

    private String PREFIX = "/job/allocationPointLog/";

    @Autowired
    private IAllocationPointLogService allocationPointLogService;

    /**
     * 跳转到分配分数记录首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/allocationPointLog/list"})
    public String index() {
        return PREFIX + "allocationPointLog.html";
    }

    /**
     * 跳转到添加分配分数记录
     */
    @RequestMapping("/allocationPointLog_add")
    @RequiresPermissions(value = {"/allocationPointLog/add"})
    public String allocationPointLogAdd() {
        return PREFIX + "allocationPointLog_add.html";
    }

    /**
     * 跳转到修改分配分数记录
     */
    @RequestMapping("/allocationPointLog_update/{allocationPointLogId}")
    @RequiresPermissions(value = {"/allocationPointLog/update"})
    public String allocationPointLogUpdate(@PathVariable String allocationPointLogId, Model model) {
        AllocationPointLog allocationPointLog = allocationPointLogService.selectById(allocationPointLogId);
        model.addAttribute("item",allocationPointLog);
        LogObjectHolder.me().set(allocationPointLog);
        return PREFIX + "allocationPointLog_edit.html";
    }

    /**
     * 获取分配分数记录列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/allocationPointLog/list"})
    @ResponseBody
    public Object list(AllocationPointLog allocationPointLog) {
        Page<AllocationPointLog> page = new PageFactory<AllocationPointLog>().defaultPage();
        EntityWrapper< AllocationPointLog> wrapper = new EntityWrapper<>();
        allocationPointLogService.selectPage(page,wrapper);
        page.setRecords(new AllocationPointLogDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增分配分数记录
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/allocationPointLog/add"})
    @ResponseBody
    public Object add(AllocationPointLog allocationPointLog) {
        allocationPointLogService.insert(allocationPointLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除分配分数记录
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/allocationPointLog/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long allocationPointLogId) {
        allocationPointLogService.deleteById(allocationPointLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改分配分数记录
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/allocationPointLog/update"})
    @ResponseBody
    public Object update(AllocationPointLog allocationPointLog) {
        allocationPointLogService.updateById(allocationPointLog);
        return SUCCESS_TIP;
    }

    /**
     * 分配分数记录详情
     */
    @RequestMapping(value = "/detail/{allocationPointLogId}")
    @ResponseBody
    public Object detail(@PathVariable("allocationPointLogId") String allocationPointLogId) {
        return allocationPointLogService.selectById(allocationPointLogId);
    }
}
