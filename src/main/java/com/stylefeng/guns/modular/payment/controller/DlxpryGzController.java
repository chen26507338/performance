package com.stylefeng.guns.modular.payment.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.payment.decorator.DlxpryGzDecorator;
import com.stylefeng.guns.modular.payment.model.DlxpryGz;
import com.stylefeng.guns.modular.payment.service.IDlxpryGzService;
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
 * 代理校聘人员控制器
 *
 * @author 
 * @Date 2021-02-27 21:17:01
 */
@Controller
@RequestMapping("${guns.admin-prefix}/dlxpryGz")
public class DlxpryGzController extends BaseController {

    private String PREFIX = "/payment/dlxpryGz/";

    @Autowired
    private IDlxpryGzService dlxpryGzService;

    /**
     * 跳转到代理校聘人员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/dlxpryGz/list"})
    public String index() {
        return PREFIX + "dlxpryGz.html";
    }

    /**
     * 跳转到添加代理校聘人员
     */
    @RequestMapping("/dlxpryGz_add")
    @RequiresPermissions(value = {"/dlxpryGz/add"})
    public String dlxpryGzAdd() {
        return PREFIX + "dlxpryGz_add.html";
    }

    /**
     * 跳转到修改代理校聘人员
     */
    @RequestMapping("/dlxpryGz_update/{dlxpryGzId}")
    @RequiresPermissions(value = {"/dlxpryGz/update"})
    public String dlxpryGzUpdate(@PathVariable String dlxpryGzId, Model model) {
        DlxpryGz dlxpryGz = dlxpryGzService.selectById(dlxpryGzId);
        model.addAttribute("item",dlxpryGz);
        LogObjectHolder.me().set(dlxpryGz);
        return PREFIX + "dlxpryGz_edit.html";
    }

    /**
     * 获取代理校聘人员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/dlxpryGz/list"})
    @ResponseBody
    public Object list(DlxpryGz dlxpryGz) {
        Page<DlxpryGz> page = new PageFactory<DlxpryGz>().defaultPage();
        EntityWrapper< DlxpryGz> wrapper = new EntityWrapper<>();
        dlxpryGzService.selectPage(page,wrapper);
        page.setRecords(new DlxpryGzDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增代理校聘人员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/dlxpryGz/add"})
    @ResponseBody
    public Object add(DlxpryGz dlxpryGz) {
        dlxpryGzService.insert(dlxpryGz);
        return SUCCESS_TIP;
    }

    /**
     * 删除代理校聘人员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/dlxpryGz/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long dlxpryGzId) {
        dlxpryGzService.deleteById(dlxpryGzId);
        return SUCCESS_TIP;
    }

    /**
     * 修改代理校聘人员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/dlxpryGz/update"})
    @ResponseBody
    public Object update(DlxpryGz dlxpryGz) {
        dlxpryGzService.updateById(dlxpryGz);
        return SUCCESS_TIP;
    }

    /**
     * 代理校聘人员详情
     */
    @RequestMapping(value = "/detail/{dlxpryGzId}")
    @ResponseBody
    public Object detail(@PathVariable("dlxpryGzId") String dlxpryGzId) {
        return dlxpryGzService.selectById(dlxpryGzId);
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/dlxpryGz_import")
    public String dlxpryGzImport() {
        return PREFIX + "dlxpryGz_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importData")
    @ResponseBody
    public Object importData(DlxpryGz dlxpryGz) {
        dlxpryGzService.importData(dlxpryGz);
        return SUCCESS_TIP;
    }
}
