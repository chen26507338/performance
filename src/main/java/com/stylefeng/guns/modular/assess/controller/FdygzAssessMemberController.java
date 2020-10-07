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
import com.stylefeng.guns.modular.assess.model.FdygzAssessMember;
import com.stylefeng.guns.modular.assess.service.IFdygzAssessMemberService;
import com.stylefeng.guns.modular.assess.decorator.FdygzAssessMemberDecorator;

/**
 * 辅导员日常工作考核成员控制器
 *
 * @author 
 * @Date 2020-10-07 11:56:09
 */
@Controller
@RequestMapping("${guns.admin-prefix}/fdygzAssessMember")
public class FdygzAssessMemberController extends BaseController {

    private String PREFIX = "/assess/fdygzAssessMember/";

    @Autowired
    private IFdygzAssessMemberService fdygzAssessMemberService;

    /**
     * 跳转到辅导员日常工作考核成员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/fdygzAssessMember/list"})
    public String index() {
        return PREFIX + "fdygzAssessMember.html";
    }

    /**
     * 跳转到添加辅导员日常工作考核成员
     */
    @RequestMapping("/fdygzAssessMember_add")
    @RequiresPermissions(value = {"/fdygzAssessMember/add"})
    public String fdygzAssessMemberAdd() {
        return PREFIX + "fdygzAssessMember_add.html";
    }

    /**
     * 跳转到修改辅导员日常工作考核成员
     */
    @RequestMapping("/fdygzAssessMember_update/{fdygzAssessMemberId}")
    @RequiresPermissions(value = {"/fdygzAssessMember/update"})
    public String fdygzAssessMemberUpdate(@PathVariable String fdygzAssessMemberId, Model model) {
        FdygzAssessMember fdygzAssessMember = fdygzAssessMemberService.selectById(fdygzAssessMemberId);
        model.addAttribute("item",fdygzAssessMember);
        LogObjectHolder.me().set(fdygzAssessMember);
        return PREFIX + "fdygzAssessMember_edit.html";
    }

    /**
     * 获取辅导员日常工作考核成员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/fdygzAssessMember/list"})
    @ResponseBody
    public Object list(FdygzAssessMember fdygzAssessMember) {
        Page<FdygzAssessMember> page = new PageFactory<FdygzAssessMember>().defaultPage();
        EntityWrapper< FdygzAssessMember> wrapper = new EntityWrapper<>();
        fdygzAssessMemberService.selectPage(page,wrapper);
        page.setRecords(new FdygzAssessMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增辅导员日常工作考核成员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/fdygzAssessMember/add"})
    @ResponseBody
    public Object add(FdygzAssessMember fdygzAssessMember) {
        fdygzAssessMemberService.insert(fdygzAssessMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除辅导员日常工作考核成员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/fdygzAssessMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long fdygzAssessMemberId) {
        fdygzAssessMemberService.deleteById(fdygzAssessMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改辅导员日常工作考核成员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/fdygzAssessMember/update"})
    @ResponseBody
    public Object update(FdygzAssessMember fdygzAssessMember) {
        fdygzAssessMemberService.updateById(fdygzAssessMember);
        return SUCCESS_TIP;
    }

    /**
     * 辅导员日常工作考核成员详情
     */
    @RequestMapping(value = "/detail/{fdygzAssessMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("fdygzAssessMemberId") String fdygzAssessMemberId) {
        return fdygzAssessMemberService.selectById(fdygzAssessMemberId);
    }
}
