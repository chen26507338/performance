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
import com.stylefeng.guns.modular.assess.model.StuWorkMember;
import com.stylefeng.guns.modular.assess.service.IStuWorkMemberService;
import com.stylefeng.guns.modular.assess.decorator.StuWorkMemberDecorator;

/**
 * 学生工作成员控制器
 *
 * @author cp
 * @Date 2020-09-16 15:26:17
 */
@Controller
@RequestMapping("${guns.admin-prefix}/stuWorkMember")
public class StuWorkMemberController extends BaseController {

    private String PREFIX = "/assess/stuWorkMember/";

    @Autowired
    private IStuWorkMemberService stuWorkMemberService;

    /**
     * 跳转到学生工作成员首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/stuWorkMember/list"})
    public String index() {
        return PREFIX + "stuWorkMember.html";
    }

    /**
     * 跳转到添加学生工作成员
     */
    @RequestMapping("/stuWorkMember_add")
    @RequiresPermissions(value = {"/stuWorkMember/add"})
    public String stuWorkMemberAdd() {
        return PREFIX + "stuWorkMember_add.html";
    }

    /**
     * 跳转到修改学生工作成员
     */
    @RequestMapping("/stuWorkMember_update/{stuWorkMemberId}")
    @RequiresPermissions(value = {"/stuWorkMember/update"})
    public String stuWorkMemberUpdate(@PathVariable String stuWorkMemberId, Model model) {
        StuWorkMember stuWorkMember = stuWorkMemberService.selectById(stuWorkMemberId);
        model.addAttribute("item",stuWorkMember);
        LogObjectHolder.me().set(stuWorkMember);
        return PREFIX + "stuWorkMember_edit.html";
    }

    /**
     * 获取学生工作成员列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/stuWorkMember/list"})
    @ResponseBody
    public Object list(StuWorkMember stuWorkMember) {
        Page<StuWorkMember> page = new PageFactory<StuWorkMember>().defaultPage();
        EntityWrapper< StuWorkMember> wrapper = new EntityWrapper<>();
        stuWorkMemberService.selectPage(page,wrapper);
        page.setRecords(new StuWorkMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增学生工作成员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/stuWorkMember/add"})
    @ResponseBody
    public Object add(StuWorkMember stuWorkMember) {
        stuWorkMemberService.insert(stuWorkMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除学生工作成员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/stuWorkMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long stuWorkMemberId) {
        stuWorkMemberService.deleteById(stuWorkMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改学生工作成员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/stuWorkMember/update"})
    @ResponseBody
    public Object update(StuWorkMember stuWorkMember) {
        stuWorkMemberService.updateById(stuWorkMember);
        return SUCCESS_TIP;
    }

    /**
     * 学生工作成员详情
     */
    @RequestMapping(value = "/detail/{stuWorkMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("stuWorkMemberId") String stuWorkMemberId) {
        return stuWorkMemberService.selectById(stuWorkMemberId);
    }
}
