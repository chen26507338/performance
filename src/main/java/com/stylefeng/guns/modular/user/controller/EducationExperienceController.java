package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
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
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.service.IEducationExperienceService;
import com.stylefeng.guns.modular.user.decorator.EducationExperienceDecorator;

/**
 * 学历培训控制器
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
@Controller
@RequestMapping("${guns.admin-prefix}/educationExperience")
public class EducationExperienceController extends BaseController {

    private String PREFIX = "/user/educationExperience/";

    @Autowired
    private IEducationExperienceService educationExperienceService;

    /**
     * 跳转到学历培训首页e'x
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/educationExperience/list"})
    public String index(User user,Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "educationExperience.html";
    }

    /**
     * 跳转到添加学历培训
     */
    @RequestMapping("/educationExperience_add")
    @RequiresPermissions(value = {"/educationExperience/add"})
    public String educationExperienceAdd(User user,Model model) {
        if (user.getId() == null) {
            model.addAttribute("userId",ShiroKit.getUser().id);
        }
        return PREFIX + "educationExperience_add.html";
    }

    /**
     * 跳转到修改学历培训
     */
    @RequestMapping("/educationExperience_update/{educationExperienceId}")
    @RequiresPermissions(value = {"/educationExperience/update"})
    public String educationExperienceUpdate(@PathVariable String educationExperienceId, Model model) {
        EducationExperience educationExperience = educationExperienceService.selectById(educationExperienceId);
        model.addAttribute("item",educationExperience);
        LogObjectHolder.me().set(educationExperience);
        return PREFIX + "educationExperience_edit.html";
    }

    /**
     * 获取学历培训列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/educationExperience/list"})
    @ResponseBody
    public Object list(EducationExperience educationExperience) {
        Page<EducationExperience> page = new PageFactory<EducationExperience>().defaultPage();
        EntityWrapper< EducationExperience> wrapper = new EntityWrapper<>();
        educationExperienceService.selectPage(page,wrapper);
        page.setRecords(new EducationExperienceDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 获取学历培训列表
     */
    @RequestMapping(value = "/test")
    @RequiresPermissions(value = {"/educationExperience/list"})
    @ResponseBody
    public Object test(EducationExperience educationExperience) {
//        Page<EducationExperience> page = new PageFactory<EducationExperience>().defaultPage();
        EntityWrapper< EducationExperience> wrapper = new EntityWrapper<>();
        return educationExperience.selectList(wrapper);
    }

    /**
     * 新增学历培训
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/educationExperience/add"})
    @ResponseBody
    public Object add(EducationExperience educationExperience) {
        educationExperienceService.insert(educationExperience);
        return SUCCESS_TIP;
    }

    /**
     * 删除学历培训
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/educationExperience/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long educationExperienceId) {
        educationExperienceService.deleteById(educationExperienceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改学历培训
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/educationExperience/update"})
    @ResponseBody
    public Object update(EducationExperience educationExperience) {
        educationExperienceService.updateById(educationExperience);
        return SUCCESS_TIP;
    }

    /**
     * 学历培训详情
     */
    @RequestMapping(value = "/detail/{educationExperienceId}")
    @ResponseBody
    public Object detail(@PathVariable("educationExperienceId") String educationExperienceId) {
        return educationExperienceService.selectById(educationExperienceId);
    }
}
