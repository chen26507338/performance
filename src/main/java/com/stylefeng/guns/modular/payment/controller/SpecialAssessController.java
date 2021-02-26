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
import com.stylefeng.guns.modular.payment.model.SpecialAssess;
import com.stylefeng.guns.modular.payment.service.ISpecialAssessService;
import com.stylefeng.guns.modular.payment.decorator.SpecialAssessDecorator;

/**
 * 专项工作奖项目列表控制器
 *
 * @author 
 * @Date 2021-02-25 18:10:30
 */
@Controller
@RequestMapping("${guns.admin-prefix}/specialAssess")
public class SpecialAssessController extends BaseController {

    private String PREFIX = "/payment/specialAssess/";

    @Autowired
    private ISpecialAssessService specialAssessService;

    /**
     * 跳转到专项工作奖项目列表首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/specialAssess/list"})
    public String index() {
        return PREFIX + "specialAssess.html";
    }

    /**
     * 跳转到添加专项工作奖项目列表
     */
    @RequestMapping("/specialAssess_add")
    @RequiresPermissions(value = {"/specialAssess/add"})
    public String specialAssessAdd() {
        return PREFIX + "specialAssess_add.html";
    }

    /**
     * 跳转到修改专项工作奖项目列表
     */
    @RequestMapping("/specialAssess_update/{specialAssessId}")
    @RequiresPermissions(value = {"/specialAssess/update"})
    public String specialAssessUpdate(@PathVariable String specialAssessId, Model model) {
        SpecialAssess specialAssess = specialAssessService.selectById(specialAssessId);
        model.addAttribute("item",specialAssess);
        LogObjectHolder.me().set(specialAssess);
        return PREFIX + "specialAssess_edit.html";
    }

    /**
     * 获取专项工作奖项目列表列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/specialAssess/list"})
    @ResponseBody
    public Object list(SpecialAssess specialAssess) {
        Page<SpecialAssess> page = new PageFactory<SpecialAssess>().defaultPage();
        EntityWrapper< SpecialAssess> wrapper = new EntityWrapper<>();
        specialAssessService.selectPage(page,wrapper);
        page.setRecords(new SpecialAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增专项工作奖项目列表
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/specialAssess/add"})
    @ResponseBody
    public Object add(SpecialAssess specialAssess) {
        specialAssess.setCreateTime(new Date());
        specialAssessService.insert(specialAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除专项工作奖项目列表
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/specialAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long specialAssessId) {
        specialAssessService.deleteById(specialAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改专项工作奖项目列表
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/specialAssess/update"})
    @ResponseBody
    public Object update(SpecialAssess specialAssess) {
        specialAssessService.updateById(specialAssess);
        return SUCCESS_TIP;
    }

    /**
     * 专项工作奖项目列表详情
     */
    @RequestMapping(value = "/detail/{specialAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("specialAssessId") String specialAssessId) {
        return specialAssessService.selectById(specialAssessId);
    }

    /**
     * 跳转到导入考核项目
     */
    @RequestMapping("/specialProject_import")
    public String specialAssessImport() {
        return PREFIX + "specialProject_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importProject")
    @ResponseBody
    public Object importProject(SpecialAssess specialAssess) {
        specialAssessService.importProject(specialAssess);
        return SUCCESS_TIP;
    }
}
