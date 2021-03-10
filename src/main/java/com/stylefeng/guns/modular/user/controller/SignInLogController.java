package com.stylefeng.guns.modular.user.controller;

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
import com.stylefeng.guns.modular.user.model.SignInLog;
import com.stylefeng.guns.modular.user.service.ISignInLogService;
import com.stylefeng.guns.modular.user.decorator.SignInLogDecorator;

/**
 * 打卡签到管理控制器
 *
 * @author 
 * @Date 2021-03-10 19:38:41
 */
@Controller
@RequestMapping("${guns.admin-prefix}/signInLog")
public class SignInLogController extends BaseController {

    private String PREFIX = "/user/signInLog/";

    @Autowired
    private ISignInLogService signInLogService;

    /**
     * 跳转到打卡签到管理首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/signInLog/list"})
    public String index() {
        return PREFIX + "signInLog.html";
    }

    /**
     * 跳转到添加打卡签到管理
     */
    @RequestMapping("/signInLog_add")
    @RequiresPermissions(value = {"/signInLog/add"})
    public String signInLogAdd() {
        return PREFIX + "signInLog_add.html";
    }

    /**
     * 跳转到修改打卡签到管理
     */
    @RequestMapping("/signInLog_update/{signInLogId}")
    @RequiresPermissions(value = {"/signInLog/update"})
    public String signInLogUpdate(@PathVariable String signInLogId, Model model) {
        SignInLog signInLog = signInLogService.selectById(signInLogId);
        model.addAttribute("item",signInLog);
        LogObjectHolder.me().set(signInLog);
        return PREFIX + "signInLog_edit.html";
    }

    /**
     * 获取打卡签到管理列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/signInLog/list"})
    @ResponseBody
    public Object list(SignInLog signInLog) {
        Page<SignInLog> page = new PageFactory<SignInLog>().defaultPage();
        EntityWrapper< SignInLog> wrapper = new EntityWrapper<>();
        signInLogService.selectPage(page,wrapper);
        page.setRecords(new SignInLogDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增打卡签到管理
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/signInLog/add"})
    @ResponseBody
    public Object add(SignInLog signInLog) {
        signInLog.setCreateTime(new Date());
        signInLogService.insert(signInLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除打卡签到管理
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/signInLog/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long signInLogId) {
        signInLogService.deleteById(signInLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改打卡签到管理
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/signInLog/update"})
    @ResponseBody
    public Object update(SignInLog signInLog) {
        signInLogService.updateById(signInLog);
        return SUCCESS_TIP;
    }

    /**
     * 打卡签到管理详情
     */
    @RequestMapping(value = "/detail/{signInLogId}")
    @ResponseBody
    public Object detail(@PathVariable("signInLogId") String signInLogId) {
        return signInLogService.selectById(signInLogId);
    }
}
