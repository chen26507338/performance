package com.stylefeng.guns.modular.assess.controller;

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
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.decorator.AssessCoefficientDecorator;

/**
 * 考核系数控制器
 *
 * @author 
 * @Date 2020-02-25 10:45:56
 */
@Controller
@RequestMapping("${guns.admin-prefix}/assessCoefficient")
public class AssessCoefficientController extends BaseController {

    private String PREFIX = "/assess/assessCoefficient/";

    @Autowired
    private IAssessCoefficientService assessCoefficientService;

    /**
     * 跳转到考核系数首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/assessCoefficient/list"})
    public String index() {
        return PREFIX + "assessCoefficient.html";
    }

    /**
     * 跳转到添加考核系数
     */
    @RequestMapping("/assessCoefficient_add")
    @RequiresPermissions(value = {"/assessCoefficient/add"})
    public String assessCoefficientAdd() {
        return PREFIX + "assessCoefficient_add.html";
    }

    /**
     * 跳转到修改考核系数
     */
    @RequestMapping("/assessCoefficient_update/{assessCoefficientId}")
    @RequiresPermissions(value = {"/assessCoefficient/update"})
    public String assessCoefficientUpdate(@PathVariable String assessCoefficientId, Model model) {
        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(assessCoefficientId);
        model.addAttribute("item",assessCoefficient);
        LogObjectHolder.me().set(assessCoefficient);
        return PREFIX + "assessCoefficient_edit.html";
    }

    /**
     * 获取考核系数列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/assessCoefficient/list"})
    @ResponseBody
    public Object list(AssessCoefficient assessCoefficient) {
        Page<AssessCoefficient> page = new PageFactory<AssessCoefficient>().defaultPage();
        EntityWrapper< AssessCoefficient> wrapper = new EntityWrapper<>();
        assessCoefficientService.selectPage(page,wrapper);
        page.setRecords(new AssessCoefficientDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增考核系数
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/assessCoefficient/add"})
    @ResponseBody
    public Object add(AssessCoefficient assessCoefficient) {
        assessCoefficientService.insert(assessCoefficient);
        return SUCCESS_TIP;
    }

    /**
     * 删除考核系数
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/assessCoefficient/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long assessCoefficientId) {
        assessCoefficientService.deleteById(assessCoefficientId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考核系数
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/assessCoefficient/update"})
    @ResponseBody
    public Object update(AssessCoefficient assessCoefficient) {
        assessCoefficientService.updateById(assessCoefficient);
        return SUCCESS_TIP;
    }

    /**
     * 考核系数详情
     */
    @RequestMapping(value = "/detail/{assessCoefficientId}")
    @ResponseBody
    public Object detail(@PathVariable("assessCoefficientId") String assessCoefficientId) {
        return assessCoefficientService.selectById(assessCoefficientId);
    }
}
