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
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.decorator.DeptDecorator;

/**
 * 部门管理控制器
 *
 * @author cp
 * @Date 2020-01-17 09:51:36
 */
@Controller
@RequestMapping("${guns.admin-prefix}/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/job/dept/";

    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/dept/list"})
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门管理
     */
    @RequestMapping("/dept_add")
    @RequiresPermissions(value = {"/dept/add"})
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门管理
     */
    @RequestMapping("/dept_update/{deptId}")
    @RequiresPermissions(value = {"/dept/update"})
    public String deptUpdate(@PathVariable String deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute("item",dept);
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门管理列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/dept/list"})
    @ResponseBody
    public Object list(Dept dept) {
        Page<Dept> page = new PageFactory<Dept>().defaultPage();
        EntityWrapper< Dept> wrapper = new EntityWrapper<>();
        deptService.selectPage(page,wrapper);
        page.setRecords(new DeptDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增部门管理
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/dept/add"})
    @ResponseBody
    public Object add(Dept dept) {
        deptService.insert(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门管理
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/dept/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long deptId) {
        deptService.deleteById(deptId);
        return SUCCESS_TIP;
    }

    /**
     * 修改部门管理
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/dept/update"})
    @ResponseBody
    public Object update(Dept dept) {
        deptService.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 部门管理详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @ResponseBody
    public Object detail(@PathVariable("deptId") String deptId) {
        return deptService.selectById(deptId);
    }
}
