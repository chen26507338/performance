package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.assess.decorator.JfwcqkAssessDecorator;
import com.stylefeng.guns.modular.assess.model.JfwcqkAssess;
import com.stylefeng.guns.modular.assess.service.IJfwcqkAssessService;
import com.stylefeng.guns.modular.system.service.IUserService;
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
 * 经费完成情况考核控制器
 *
 * @author 
 * @Date 2021-03-02 15:27:56
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jfwcqkAssess")
public class JfwcqkAssessController extends BaseController {

    private String PREFIX = "/assess/jfwcqkAssess/";

    @Autowired
    private IJfwcqkAssessService jfwcqkAssessService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到经费完成情况考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jfwcqkAssess/list"})
    public String index() {
        return PREFIX + "jfwcqkAssess.html";
    }

    /**
     * 跳转到添加经费完成情况考核
     */
    @RequestMapping("/jfwcqkAssess_add")
    @RequiresPermissions(value = {"/jfwcqkAssess/add"})
    public String jfwcqkAssessAdd() {
        return PREFIX + "jfwcqkAssess_add.html";
    }

    /**
     * 跳转到修改经费完成情况考核
     */
    @RequestMapping("/jfwcqkAssess_update/{jfwcqkAssessId}")
    @RequiresPermissions(value = {"/jfwcqkAssess/update"})
    public String jfwcqkAssessUpdate(@PathVariable String jfwcqkAssessId, Model model) {
        JfwcqkAssess jfwcqkAssess = jfwcqkAssessService.selectById(jfwcqkAssessId);
        model.addAttribute("item",jfwcqkAssess);
        LogObjectHolder.me().set(jfwcqkAssess);
        return PREFIX + "jfwcqkAssess_edit.html";
    }

    /**
     * 获取经费完成情况考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jfwcqkAssess/list"})
    @ResponseBody
    public Object list(JfwcqkAssess jfwcqkAssess) {
        Page<JfwcqkAssess> page = new PageFactory<JfwcqkAssess>().defaultPage();
        EntityWrapper< JfwcqkAssess> wrapper = new EntityWrapper<>();

        if (ToolUtil.isNotEmpty(jfwcqkAssess.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) jfwcqkAssess.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        jfwcqkAssessService.selectPage(page,wrapper);
        page.setRecords(new JfwcqkAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }
    

    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/jfwcqkAssess_import")
    public String jfwcqkAssessImport() {
        return PREFIX + "jfwcqkAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(JfwcqkAssess jfwcqkAssess) {
        jfwcqkAssessService.importAssess(jfwcqkAssess);
        return SUCCESS_TIP;
    }
    
    /**
     * 新增经费完成情况考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jfwcqkAssess/add"})
    @ResponseBody
    public Object add(JfwcqkAssess jfwcqkAssess) {
        jfwcqkAssessService.insert(jfwcqkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除经费完成情况考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jfwcqkAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jfwcqkAssessId) {
        jfwcqkAssessService.deleteById(jfwcqkAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改经费完成情况考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jfwcqkAssess/update"})
    @ResponseBody
    public Object update(JfwcqkAssess jfwcqkAssess) {
        jfwcqkAssessService.updateById(jfwcqkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 经费完成情况考核详情
     */
    @RequestMapping(value = "/detail/{jfwcqkAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("jfwcqkAssessId") String jfwcqkAssessId) {
        return jfwcqkAssessService.selectById(jfwcqkAssessId);
    }
}
