package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.decorator.ScientificAchievementDecorator;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
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
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
import com.stylefeng.guns.modular.user.service.IScientificAchievementService;
import com.stylefeng.guns.modular.user.decorator.ScientificAchievementDecorator;

/**
 * 科研成果控制器
 *
 * @author cp
 * @Date 2020-08-14 15:55:50
 */
@Controller
@RequestMapping("${guns.admin-prefix}/scientificAchievement")
public class ScientificAchievementController extends BaseController {

    private String PREFIX = "/user/scientificAchievement/";

    @Autowired
    private IScientificAchievementService scientificAchievementService;
    @Autowired
    private IUserService userService;
    /**
     * 跳转到科研成果首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/scientificAchievement/list"})
    public String index(User user, Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "scientificAchievement.html";
    }

    /**
     * 跳转到添加科研成果
     */
    @RequestMapping("/scientificAchievement_add")
    @RequiresPermissions(value = {"/scientificAchievement/add"})
    public String scientificAchievementAdd() {
        return PREFIX + "scientificAchievement_add.html";
    }

    /**
     * 跳转到修改科研成果
     */
    @RequestMapping("/scientificAchievement_update/{scientificAchievementId}")
    @RequiresPermissions(value = {"/scientificAchievement/update"})
    public String scientificAchievementUpdate(@PathVariable String scientificAchievementId, Model model) {
        ScientificAchievement scientificAchievement = scientificAchievementService.selectById(scientificAchievementId);
        model.addAttribute("item",scientificAchievement);
        LogObjectHolder.me().set(scientificAchievement);
        return PREFIX + "scientificAchievement_edit.html";
    }

    /**
     * 获取科研成果列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/scientificAchievement/list"})
    @ResponseBody
    public Object list(ScientificAchievement scientificAchievement) {
        Page<ScientificAchievement> page = new PageFactory<ScientificAchievement>().defaultPage();
        EntityWrapper< ScientificAchievement> wrapper = new EntityWrapper<>();
        if (scientificAchievement.getStatus() != null) {
            wrapper.eq("status", scientificAchievement.getStatus());
        }
        if (scientificAchievement.getUserId() != null) {
            wrapper.eq("user_id", scientificAchievement.getUserId());
        }
        scientificAchievementService.selectPage(page,wrapper);
        page.setRecords(new ScientificAchievementDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增科研成果
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/scientificAchievement/add"})
    @ResponseBody
    public Object add(ScientificAchievement scientificAchievement) {
        scientificAchievementService.insert(scientificAchievement);
        return SUCCESS_TIP;
    }

    /**
     * 删除科研成果
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/scientificAchievement/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long scientificAchievementId) {
        scientificAchievementService.deleteById(scientificAchievementId);
        return SUCCESS_TIP;
    }

    /**
     * 修改科研成果
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/scientificAchievement/update"})
    @ResponseBody
    public Object update(ScientificAchievement scientificAchievement) {
        scientificAchievementService.updateById(scientificAchievement);
        return SUCCESS_TIP;
    }

    /**
     * 科研成果详情
     */
    @RequestMapping(value = "/detail/{scientificAchievementId}")
    @ResponseBody
    public Object detail(@PathVariable("scientificAchievementId") String scientificAchievementId) {
        return scientificAchievementService.selectById(scientificAchievementId);
    }

    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        ScientificAchievement params = new ScientificAchievement();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<ScientificAchievement> wrapper = new EntityWrapper<>(params);
        List<ScientificAchievement> list = scientificAchievementService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "scientificAchievement_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/scientificAchievement_act")
    public String scientificAchievementAct(ScientificAchievement scientificAchievement, Model model) {
        scientificAchievement.setProcInsId(scientificAchievement.getAct().getProcInsId());
        EntityWrapper<ScientificAchievement> wrapper = new EntityWrapper<>(scientificAchievement);
        wrapper.last("limit 1");
        ScientificAchievement data = scientificAchievement.selectOne(wrapper);
        User user = userService.selectIgnorePointById(data.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", scientificAchievement.getAct());
        return PREFIX + "scientificAchievement_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(ScientificAchievement scientificAchievement) {
        scientificAchievementService.audit(scientificAchievement);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/scientificAchievementProcData")
    @ResponseBody
    public Object scientificAchievementProcData(ScientificAchievement scientificAchievement) {
        List<ScientificAchievement> datas = new ScientificAchievementDecorator(scientificAchievement.selectList(new EntityWrapper<>(scientificAchievement))).decorate();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @ResponseBody
    public Object doAddApply(@RequestBody List<ScientificAchievement> scientificAchievements) {
        scientificAchievementService.addApply(scientificAchievements);
        return SUCCESS_TIP;
    }
}
