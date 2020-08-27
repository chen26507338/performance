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
import com.stylefeng.guns.modular.assess.model.MajorBuildMember;
import com.stylefeng.guns.modular.assess.service.IMajorBuildMemberService;
import com.stylefeng.guns.modular.assess.decorator.MajorBuildMemberDecorator;

/**
 * 专业建设项目成员控制器
 *
 * @author 
 * @Date 2020-08-19 16:35:13
 */
@Controller
@RequestMapping("${guns.admin-prefix}/MajorBuildMember")
public class MajorBuildMemberController extends BaseController {

    private String PREFIX = "/assess/MajorBuildMember/";

    @Autowired
    private IMajorBuildMemberService MajorBuildMemberService;

    /**
     * 跳转到专业建设项目成员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/MajorBuildMember/list"})
    public String index() {
        return PREFIX + "MajorBuildMember.html";
    }

    /**
     * 跳转到添加专业建设项目成员
     */
    @RequestMapping("/MajorBuildMember_add")
    @RequiresPermissions(value = {"/MajorBuildMember/add"})
    public String MajorBuildMemberAdd() {
        return PREFIX + "MajorBuildMember_add.html";
    }

    /**
     * 跳转到修改专业建设项目成员
     */
    @RequestMapping("/MajorBuildMember_update/{MajorBuildMemberId}")
    @RequiresPermissions(value = {"/MajorBuildMember/update"})
    public String MajorBuildMemberUpdate(@PathVariable String MajorBuildMemberId, Model model) {
        MajorBuildMember MajorBuildMember = MajorBuildMemberService.selectById(MajorBuildMemberId);
        model.addAttribute("item",MajorBuildMember);
        LogObjectHolder.me().set(MajorBuildMember);
        return PREFIX + "MajorBuildMember_edit.html";
    }

    /**
     * 获取专业建设项目成员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/MajorBuildMember/list"})
    @ResponseBody
    public Object list(MajorBuildMember MajorBuildMember) {
        Page<MajorBuildMember> page = new PageFactory<MajorBuildMember>().defaultPage();
        EntityWrapper< MajorBuildMember> wrapper = new EntityWrapper<>();
        MajorBuildMemberService.selectPage(page,wrapper);
        page.setRecords(new MajorBuildMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增专业建设项目成员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/MajorBuildMember/add"})
    @ResponseBody
    public Object add(MajorBuildMember MajorBuildMember) {
        MajorBuildMemberService.insert(MajorBuildMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除专业建设项目成员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/MajorBuildMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long MajorBuildMemberId) {
        MajorBuildMemberService.deleteById(MajorBuildMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改专业建设项目成员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/MajorBuildMember/update"})
    @ResponseBody
    public Object update(MajorBuildMember MajorBuildMember) {
        MajorBuildMemberService.updateById(MajorBuildMember);
        return SUCCESS_TIP;
    }

    /**
     * 专业建设项目成员详情
     */
    @RequestMapping(value = "/detail/{MajorBuildMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("MajorBuildMemberId") String MajorBuildMemberId) {
        return MajorBuildMemberService.selectById(MajorBuildMemberId);
    }
}
