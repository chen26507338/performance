package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.decorator.RewardsPunishmentDecorator;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;
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
import com.stylefeng.guns.modular.user.model.RewardsPunishment;
import com.stylefeng.guns.modular.user.service.IRewardsPunishmentService;
import com.stylefeng.guns.modular.user.decorator.RewardsPunishmentDecorator;

/**
 * 考核奖惩控制器
 *
 * @author cp
 * @Date 2020-07-01 15:23:57
 */
@Controller
@RequestMapping("${guns.admin-prefix}/rewardsPunishment")
public class RewardsPunishmentController extends BaseController {

    private String PREFIX = "/user/rewardsPunishment/";

    @Autowired
    private IRewardsPunishmentService rewardsPunishmentService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到考核奖惩首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/rewardsPunishment/list"})
    public String index(User user,Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "rewardsPunishment.html";
    }

    /**
     * 跳转到添加考核奖惩
     */
    @RequestMapping("/rewardsPunishment_add")
    @RequiresPermissions(value = {"/rewardsPunishment/add"})
    public String rewardsPunishmentAdd() {
        return PREFIX + "rewardsPunishment_add.html";
    }

    /**
     * 跳转到修改考核奖惩
     */
    @RequestMapping("/rewardsPunishment_update/{rewardsPunishmentId}")
    @RequiresPermissions(value = {"/rewardsPunishment/update"})
    public String rewardsPunishmentUpdate(@PathVariable String rewardsPunishmentId, Model model) {
        RewardsPunishment rewardsPunishment = rewardsPunishmentService.selectById(rewardsPunishmentId);
        model.addAttribute("item",rewardsPunishment);
        LogObjectHolder.me().set(rewardsPunishment);
        return PREFIX + "rewardsPunishment_edit.html";
    }

    /**
     * 获取考核奖惩列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/rewardsPunishment/list"})
    @ResponseBody
    public Object list(RewardsPunishment rewardsPunishment) {
        Page<RewardsPunishment> page = new PageFactory<RewardsPunishment>().defaultPage();
        EntityWrapper< RewardsPunishment> wrapper = new EntityWrapper<>();
        if (rewardsPunishment.getStatus() != null) {
            wrapper.eq("status", rewardsPunishment.getStatus());
        }
        if (rewardsPunishment.getUserId() != null) {
            wrapper.eq("user_id", rewardsPunishment.getUserId());
        }
        rewardsPunishmentService.selectPage(page,wrapper);
        page.setRecords(new RewardsPunishmentDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增考核奖惩
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/rewardsPunishment/add"})
    @ResponseBody
    public Object add(RewardsPunishment rewardsPunishment) {
        rewardsPunishmentService.insert(rewardsPunishment);
        return SUCCESS_TIP;
    }

    /**
     * 删除考核奖惩
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/rewardsPunishment/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long rewardsPunishmentId) {
        rewardsPunishmentService.deleteById(rewardsPunishmentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考核奖惩
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/rewardsPunishment/update"})
    @ResponseBody
    public Object update(RewardsPunishment rewardsPunishment) {
        rewardsPunishmentService.updateById(rewardsPunishment);
        return SUCCESS_TIP;
    }

    /**
     * 考核奖惩详情
     */
    @RequestMapping(value = "/detail/{rewardsPunishmentId}")
    @ResponseBody
    public Object detail(@PathVariable("rewardsPunishmentId") String rewardsPunishmentId) {
        return rewardsPunishmentService.selectById(rewardsPunishmentId);
    }


    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        RewardsPunishment params = new RewardsPunishment();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<RewardsPunishment> wrapper = new EntityWrapper<>(params);
        List<RewardsPunishment> list = rewardsPunishmentService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "rewardsPunishment_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/rewardsPunishment_act")
    public String rewardsPunishmentAct(RewardsPunishment rewardsPunishment, Model model) {
        rewardsPunishment.setProcInsId(rewardsPunishment.getAct().getProcInsId());
        EntityWrapper<RewardsPunishment> wrapper = new EntityWrapper<>(rewardsPunishment);
        wrapper.last("limit 1");
        RewardsPunishment data = rewardsPunishment.selectOne(wrapper);
        User user = userService.selectIgnorePointById(data.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", rewardsPunishment.getAct());
        return PREFIX + "rewardsPunishment_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(RewardsPunishment rewardsPunishment) {
        rewardsPunishmentService.audit(rewardsPunishment);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/rewardsPunishmentProcData")
    @ResponseBody
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public Object rewardsPunishmentProcData(RewardsPunishment rewardsPunishment) {
        List<RewardsPunishment> datas = rewardsPunishment.selectList(new EntityWrapper<>(rewardsPunishment));
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", new RewardsPunishmentDecorator(datas).decorate());
        return result;
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @ResponseBody
    public Object doAddApply(@RequestBody List<RewardsPunishment> rewardsPunishments) {
        rewardsPunishmentService.addApply(rewardsPunishments);
        return SUCCESS_TIP;
    }
}
