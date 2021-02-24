package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.assess.decorator.JshjAssessDecorator;
import com.stylefeng.guns.modular.assess.model.JshjAssess;
import com.stylefeng.guns.modular.assess.service.IJshjAssessService;
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
 * 竞赛获奖控制器
 *
 * @author
 * @Date 2021-02-24 13:44:31
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jshjAssess")
public class JshjAssessController extends BaseController {

    private String PREFIX = "/assess/jshjAssess/";

    @Autowired
    private IJshjAssessService jshjAssessService;

    /**
     * 跳转到竞赛获奖首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jshjAssess/list"})
    public String index() {
        return PREFIX + "jshjAssess.html";
    }

    /**
     * 跳转到添加竞赛获奖
     */
    @RequestMapping("/jshjAssess_add")
    @RequiresPermissions(value = {"/jshjAssess/add"})
    public String jshjAssessAdd() {
        return PREFIX + "jshjAssess_add.html";
    }

    /**
     * 跳转到修改竞赛获奖
     */
    @RequestMapping("/jshjAssess_update/{jshjAssessId}")
    @RequiresPermissions(value = {"/jshjAssess/update"})
    public String jshjAssessUpdate(@PathVariable String jshjAssessId, Model model) {
        JshjAssess jshjAssess = jshjAssessService.selectById(jshjAssessId);
        model.addAttribute("item",jshjAssess);
        LogObjectHolder.me().set(jshjAssess);
        return PREFIX + "jshjAssess_edit.html";
    }

    /**
     * 获取竞赛获奖列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jshjAssess/list"})
    @ResponseBody
    public Object list(JshjAssess jshjAssess) {
        Page<JshjAssess> page = new PageFactory<JshjAssess>().defaultPage();
        EntityWrapper< JshjAssess> wrapper = new EntityWrapper<>();
        jshjAssessService.selectPage(page,wrapper);
        page.setRecords(new JshjAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增竞赛获奖
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jshjAssess/add"})
    @ResponseBody
    public Object add(JshjAssess jshjAssess) {
        jshjAssessService.insert(jshjAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除竞赛获奖
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jshjAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jshjAssessId) {
        jshjAssessService.deleteById(jshjAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改竞赛获奖
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jshjAssess/update"})
    @ResponseBody
    public Object update(JshjAssess jshjAssess) {
        jshjAssessService.updateById(jshjAssess);
        return SUCCESS_TIP;
    }

    /**
     * 竞赛获奖详情
     */
    @RequestMapping(value = "/detail/{jshjAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("jshjAssessId") String jshjAssessId) {
        return jshjAssessService.selectById(jshjAssessId);
    }

    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/jshjAssess_import")
    public String jshjAssessImport() {
        return PREFIX + "jshjAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(JshjAssess jshjAssess) {
        jshjAssessService.importAssess(jshjAssess);
        return SUCCESS_TIP;
    }
}
