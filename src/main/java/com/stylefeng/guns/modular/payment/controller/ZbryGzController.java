package com.stylefeng.guns.modular.payment.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.payment.decorator.ZbryGzDecorator;
import com.stylefeng.guns.modular.payment.model.ZbryGz;
import com.stylefeng.guns.modular.payment.service.IZbryGzService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

;
;

/**
 * 在编人员控制器
 *
 * @author 
 * @Date 2021-02-27 14:33:44
 */
@Controller
@RequestMapping("${guns.admin-prefix}/zbryGz")
public class ZbryGzController extends BaseController {

    private String PREFIX = "/payment/zbryGz/";

    @Autowired
    private IZbryGzService zbryGzService;

    /**
     * 跳转到在编人员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/zbryGz/list"})
    public String index() {
        return PREFIX + "zbryGz.html";
    }

    /**
     * 跳转到添加在编人员
     */
    @RequestMapping("/zbryGz_add")
    @RequiresPermissions(value = {"/zbryGz/add"})
    public String zbryGzAdd() {
        return PREFIX + "zbryGz_add.html";
    }

    /**
     * 跳转到修改在编人员
     */
    @RequestMapping("/zbryGz_update/{zbryGzId}")
    @RequiresPermissions(value = {"/zbryGz/update"})
    public String zbryGzUpdate(@PathVariable String zbryGzId, Model model) {
        ZbryGz zbryGz = zbryGzService.selectById(zbryGzId);
        model.addAttribute("item",zbryGz);
        LogObjectHolder.me().set(zbryGz);
        return PREFIX + "zbryGz_edit.html";
    }

    /**
     * 获取在编人员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/zbryGz/list"})
    @ResponseBody
    public Object list(ZbryGz zbryGz) {
        Page<ZbryGz> page = new PageFactory<ZbryGz>().defaultPage();
        EntityWrapper< ZbryGz> wrapper = new EntityWrapper<>();
        zbryGzService.selectPage(page,wrapper);
        page.setRecords(new ZbryGzDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增在编人员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/zbryGz/add"})
    @ResponseBody
    public Object add(ZbryGz zbryGz) {
        zbryGzService.insert(zbryGz);
        return SUCCESS_TIP;
    }

    /**
     * 删除在编人员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/zbryGz/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long zbryGzId) {
        zbryGzService.deleteById(zbryGzId);
        return SUCCESS_TIP;
    }

    /**
     * 修改在编人员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/zbryGz/update"})
    @ResponseBody
    public Object update(ZbryGz zbryGz) {
        zbryGzService.updateById(zbryGz);
        return SUCCESS_TIP;
    }

    /**
     * 在编人员详情
     */
    @RequestMapping(value = "/detail/{zbryGzId}")
    @ResponseBody
    public Object detail(@PathVariable("zbryGzId") String zbryGzId) {
        return zbryGzService.selectById(zbryGzId);
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/zbryGz_import")
    public String zbryGzImport() {
        return PREFIX + "zbryGz_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importData")
    @ResponseBody
    public Object importData(ZbryGz zbryGz) {
        zbryGzService.importData(zbryGz);
        return SUCCESS_TIP;
    }
}
