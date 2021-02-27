package com.stylefeng.guns.modular.payment.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.payment.decorator.PqryGzDecorator;
import com.stylefeng.guns.modular.payment.model.PqryGz;
import com.stylefeng.guns.modular.payment.service.IPqryGzService;
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
 * 派遣人员控制器
 *
 * @author 
 * @Date 2021-02-27 20:56:41
 */
@Controller
@RequestMapping("${guns.admin-prefix}/pqryGz")
public class PqryGzController extends BaseController {

    private String PREFIX = "/payment/pqryGz/";

    @Autowired
    private IPqryGzService pqryGzService;

    /**
     * 跳转到派遣人员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/pqryGz/list"})
    public String index() {
        return PREFIX + "pqryGz.html";
    }

    /**
     * 跳转到添加派遣人员
     */
    @RequestMapping("/pqryGz_add")
    @RequiresPermissions(value = {"/pqryGz/add"})
    public String pqryGzAdd() {
        return PREFIX + "pqryGz_add.html";
    }

    /**
     * 跳转到修改派遣人员
     */
    @RequestMapping("/pqryGz_update/{pqryGzId}")
    @RequiresPermissions(value = {"/pqryGz/update"})
    public String pqryGzUpdate(@PathVariable String pqryGzId, Model model) {
        PqryGz pqryGz = pqryGzService.selectById(pqryGzId);
        model.addAttribute("item",pqryGz);
        LogObjectHolder.me().set(pqryGz);
        return PREFIX + "pqryGz_edit.html";
    }

    /**
     * 获取派遣人员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/pqryGz/list"})
    @ResponseBody
    public Object list(PqryGz pqryGz) {
        Page<PqryGz> page = new PageFactory<PqryGz>().defaultPage();
        EntityWrapper< PqryGz> wrapper = new EntityWrapper<>();
        pqryGzService.selectPage(page,wrapper);
        page.setRecords(new PqryGzDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增派遣人员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/pqryGz/add"})
    @ResponseBody
    public Object add(PqryGz pqryGz) {
        pqryGzService.insert(pqryGz);
        return SUCCESS_TIP;
    }

    /**
     * 删除派遣人员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/pqryGz/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long pqryGzId) {
        pqryGzService.deleteById(pqryGzId);
        return SUCCESS_TIP;
    }

    /**
     * 修改派遣人员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/pqryGz/update"})
    @ResponseBody
    public Object update(PqryGz pqryGz) {
        pqryGzService.updateById(pqryGz);
        return SUCCESS_TIP;
    }

    /**
     * 派遣人员详情
     */
    @RequestMapping(value = "/detail/{pqryGzId}")
    @ResponseBody
    public Object detail(@PathVariable("pqryGzId") String pqryGzId) {
        return pqryGzService.selectById(pqryGzId);
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/pqryGz_import")
    public String pqryGzImport() {
        return PREFIX + "pqryGz_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importData")
    @ResponseBody
    public Object importData(PqryGz pqryGz) {
        pqryGzService.importData(pqryGz);
        return SUCCESS_TIP;
    }
}
