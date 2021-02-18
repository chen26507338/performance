package com.stylefeng.guns.modular.pay.controller;

import com.stylefeng.guns.common.persistence.model.User;
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
import com.stylefeng.guns.modular.pay.model.PaySetting;
import com.stylefeng.guns.modular.pay.service.IPaySettingService;
import com.stylefeng.guns.modular.pay.decorator.PaySettingDecorator;

/**
 * 薪酬设置控制器
 *
 * @author 
 * @Date 2021-02-14 11:12:00
 */
@Controller
@RequestMapping("${guns.admin-prefix}/paySetting")
public class PaySettingController extends BaseController {

    private String PREFIX = "/pay/paySetting/";

    @Autowired
    private IPaySettingService paySettingService;

    /**
     * 跳转到薪酬设置首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/paySetting/list"})
    public String index() {
        return PREFIX + "paySetting.html";
    }

    /**
     * 跳转到添加薪酬设置
     */
    @RequestMapping("/paySetting_add")
    @RequiresPermissions(value = {"/paySetting/add"})
    public String paySettingAdd() {
        return PREFIX + "paySetting_add.html";
    }

    /**
     * 跳转到修改薪酬设置
     */
    @RequestMapping("/paySetting_update/{paySettingId}")
    @RequiresPermissions(value = {"/paySetting/update"})
    public String paySettingUpdate(@PathVariable String paySettingId, Model model) {
        PaySetting paySetting = paySettingService.selectById(paySettingId);
        model.addAttribute("item",paySetting);
        LogObjectHolder.me().set(paySetting);
        return PREFIX + "paySetting_edit.html";
    }

    /**
     * 获取薪酬设置列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/paySetting/list"})
    @ResponseBody
    public Object list(PaySetting paySetting) {
        Page<PaySetting> page = new PageFactory<PaySetting>().defaultPage();
        EntityWrapper< PaySetting> wrapper = new EntityWrapper<>();
        paySettingService.selectPage(page,wrapper);
        page.setRecords(new PaySettingDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增薪酬设置
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/paySetting/add"})
    @ResponseBody
    public Object add(PaySetting paySetting) {
        paySettingService.insert(paySetting);
        return SUCCESS_TIP;
    }

    /**
     * 删除薪酬设置
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/paySetting/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long paySettingId) {
        paySettingService.deleteById(paySettingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改薪酬设置
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/paySetting/update"})
    @ResponseBody
    public Object update(PaySetting paySetting) {
        paySettingService.updateById(paySetting);
        return SUCCESS_TIP;
    }

    /**
     * 薪酬设置详情
     */
    @RequestMapping(value = "/detail/{paySettingId}")
    @ResponseBody
    public Object detail(@PathVariable("paySettingId") String paySettingId) {
        return paySettingService.selectById(paySettingId);
    }



}
