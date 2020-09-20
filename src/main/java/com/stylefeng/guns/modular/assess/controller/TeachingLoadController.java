package com.stylefeng.guns.modular.assess.controller;

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
import com.stylefeng.guns.modular.assess.model.TeachingLoad;
import com.stylefeng.guns.modular.assess.service.ITeachingLoadService;
import com.stylefeng.guns.modular.assess.decorator.TeachingLoadDecorator;

/**
 * 教学工作量控制器
 *
 * @author 
 * @Date 2020-09-20 09:20:09
 */
@Controller
@RequestMapping("${guns.admin-prefix}/teachingLoad")
public class TeachingLoadController extends BaseController {

    private String PREFIX = "/assess/teachingLoad/";

    @Autowired
    private ITeachingLoadService teachingLoadService;

    /**
     * 跳转到教学工作量首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/teachingLoad/list"})
    public String index() {
        return PREFIX + "teachingLoad.html";
    }

    /**
     * 跳转到添加教学工作量
     */
    @RequestMapping("/teachingLoad_add")
    @RequiresPermissions(value = {"/teachingLoad/add"})
    public String teachingLoadAdd() {
        return PREFIX + "teachingLoad_add.html";
    }

    /**
     * 跳转到修改教学工作量
     */
    @RequestMapping("/teachingLoad_update/{teachingLoadId}")
    @RequiresPermissions(value = {"/teachingLoad/update"})
    public String teachingLoadUpdate(@PathVariable String teachingLoadId, Model model) {
        TeachingLoad teachingLoad = teachingLoadService.selectById(teachingLoadId);
        model.addAttribute("item",teachingLoad);
        LogObjectHolder.me().set(teachingLoad);
        return PREFIX + "teachingLoad_edit.html";
    }

    /**
     * 获取教学工作量列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/teachingLoad/list"})
    @ResponseBody
    public Object list(TeachingLoad teachingLoad) {
        Page<TeachingLoad> page = new PageFactory<TeachingLoad>().defaultPage();
        EntityWrapper< TeachingLoad> wrapper = new EntityWrapper<>();
        teachingLoadService.selectPage(page,wrapper);
        page.setRecords(new TeachingLoadDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增教学工作量
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/teachingLoad/add"})
    @ResponseBody
    public Object add(TeachingLoad teachingLoad) {
        teachingLoadService.add(teachingLoad);
        return SUCCESS_TIP;
    }

    /**
     * 删除教学工作量
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/teachingLoad/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long teachingLoadId) {
        teachingLoadService.deleteById(teachingLoadId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教学工作量
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/teachingLoad/update"})
    @ResponseBody
    public Object update(TeachingLoad teachingLoad) {
        teachingLoadService.updateById(teachingLoad);
        return SUCCESS_TIP;
    }

    /**
     * 教学工作量详情
     */
    @RequestMapping(value = "/detail/{teachingLoadId}")
    @ResponseBody
    public Object detail(@PathVariable("teachingLoadId") String teachingLoadId) {
        return teachingLoadService.selectById(teachingLoadId);
    }
}
