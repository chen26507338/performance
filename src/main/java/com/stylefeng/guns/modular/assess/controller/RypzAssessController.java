package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
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
import com.stylefeng.guns.modular.assess.model.RypzAssess;
import com.stylefeng.guns.modular.assess.service.IRypzAssessService;
import com.stylefeng.guns.modular.assess.decorator.RypzAssessDecorator;

/**
 * 人员配置考核控制器
 *
 * @author 
 * @Date 2021-02-21 17:45:46
 */
@Controller
@RequestMapping("${guns.admin-prefix}/rypzAssess")
public class RypzAssessController extends BaseController {

    private String PREFIX = "/assess/rypzAssess/";

    @Autowired
    private IRypzAssessService rypzAssessService;

    /**
     * 跳转到人员配置考核首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "rypzAssess.html";
    }

    /**
     * 跳转到添加人员配置考核
     */
    @RequestMapping("/rypzAssess_add")
    @RequiresPermissions(value = {"/rypzAssess/add"})
    public String rypzAssessAdd() {
        return PREFIX + "rypzAssess_add.html";
    }

    /**
     * 跳转到修改人员配置考核
     */
    @RequestMapping("/rypzAssess_update/{rypzAssessId}")
    @RequiresPermissions(value = {"/rypzAssess/update"})
    public String rypzAssessUpdate(@PathVariable String rypzAssessId, Model model) {
        RypzAssess rypzAssess = rypzAssessService.selectById(rypzAssessId);
        model.addAttribute("item",rypzAssess);
        LogObjectHolder.me().set(rypzAssess);
        return PREFIX + "rypzAssess_edit.html";
    }

    /**
     * 获取人员配置考核列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(RypzAssess rypzAssess) {
        Page<RypzAssess> page = new PageFactory<RypzAssess>().defaultPage();
        EntityWrapper< RypzAssess> wrapper = new EntityWrapper<>();
        rypzAssessService.selectPage(page,wrapper);
        page.setRecords(new RypzAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增人员配置考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/rypzAssess/add"})
    @ResponseBody
    public Object add(RypzAssess rypzAssess) {
        rypzAssessService.insert(rypzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除人员配置考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/rypzAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long rypzAssessId) {
        rypzAssessService.deleteById(rypzAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改人员配置考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/rypzAssess/update"})
    @ResponseBody
    public Object update(RypzAssess rypzAssess) {
        rypzAssessService.updateById(rypzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 人员配置考核详情
     */
    @RequestMapping(value = "/detail/{rypzAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("rypzAssessId") String rypzAssessId) {
        return rypzAssessService.selectById(rypzAssessId);
    }

    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/rypzAssess_import")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    public String rypzAssessImport() {
        return PREFIX + "rypzAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    @ResponseBody
    public Object importAssess(RypzAssess rypzAssess) {
        rypzAssessService.importAssess(rypzAssess);
        return SUCCESS_TIP;
    }
}
