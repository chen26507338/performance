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
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.assess.service.IManServiceMemberService;
import com.stylefeng.guns.modular.assess.decorator.ManServiceMemberDecorator;

/**
 * 管理服务成员控制器
 *
 * @author 
 * @Date 2020-09-17 18:39:27
 */
@Controller
@RequestMapping("${guns.admin-prefix}/manServiceMember")
public class ManServiceMemberController extends BaseController {

    private String PREFIX = "/assess/manServiceMember/";

    @Autowired
    private IManServiceMemberService manServiceMemberService;

    /**
     * 跳转到管理服务成员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/manServiceMember/list"})
    public String index() {
        return PREFIX + "manServiceMember.html";
    }

    /**
     * 跳转到添加管理服务成员
     */
    @RequestMapping("/manServiceMember_add")
    @RequiresPermissions(value = {"/manServiceMember/add"})
    public String manServiceMemberAdd() {
        return PREFIX + "manServiceMember_add.html";
    }

    /**
     * 跳转到修改管理服务成员
     */
    @RequestMapping("/manServiceMember_update/{manServiceMemberId}")
    @RequiresPermissions(value = {"/manServiceMember/update"})
    public String manServiceMemberUpdate(@PathVariable String manServiceMemberId, Model model) {
        ManServiceMember manServiceMember = manServiceMemberService.selectById(manServiceMemberId);
        model.addAttribute("item",manServiceMember);
        LogObjectHolder.me().set(manServiceMember);
        return PREFIX + "manServiceMember_edit.html";
    }

    /**
     * 获取管理服务成员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/manServiceMember/list"})
    @ResponseBody
    public Object list(ManServiceMember manServiceMember) {
        Page<ManServiceMember> page = new PageFactory<ManServiceMember>().defaultPage();
        EntityWrapper< ManServiceMember> wrapper = new EntityWrapper<>();
        manServiceMemberService.selectPage(page,wrapper);
        page.setRecords(new ManServiceMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增管理服务成员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/manServiceMember/add"})
    @ResponseBody
    public Object add(ManServiceMember manServiceMember) {
        manServiceMemberService.insert(manServiceMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理服务成员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/manServiceMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long manServiceMemberId) {
        manServiceMemberService.deleteById(manServiceMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理服务成员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/manServiceMember/update"})
    @ResponseBody
    public Object update(ManServiceMember manServiceMember) {
        manServiceMemberService.updateById(manServiceMember);
        return SUCCESS_TIP;
    }

    /**
     * 管理服务成员详情
     */
    @RequestMapping(value = "/detail/{manServiceMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("manServiceMemberId") String manServiceMemberId) {
        return manServiceMemberService.selectById(manServiceMemberId);
    }
}
