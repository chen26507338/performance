package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.system.service.IUserService;
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
import com.stylefeng.guns.modular.assess.model.SpecialAssessMember;
import com.stylefeng.guns.modular.assess.service.ISpecialAssessMemberService;
import com.stylefeng.guns.modular.assess.decorator.SpecialAssessMemberDecorator;

/**
 * 专项工作考核控制器
 *
 * @author 
 * @Date 2021-02-25 18:46:34
 */
@Controller
@RequestMapping("${guns.admin-prefix}/specialAssessMember")
public class SpecialAssessMemberController extends BaseController {

    private String PREFIX = "/assess/specialAssessMember/";

    @Autowired
    private ISpecialAssessMemberService specialAssessMemberService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到专项工作考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/specialAssessMember/list"})
    public String index() {
        return PREFIX + "specialAssessMember.html";
    }

    /**
     * 跳转到添加专项工作考核
     */
    @RequestMapping("/specialAssessMember_add")
    @RequiresPermissions(value = {"/specialAssessMember/add"})
    public String specialAssessMemberAdd() {
        return PREFIX + "specialAssessMember_add.html";
    }

    /**
     * 跳转到修改专项工作考核
     */
    @RequestMapping("/specialAssessMember_update/{specialAssessMemberId}")
    @RequiresPermissions(value = {"/specialAssessMember/update"})
    public String specialAssessMemberUpdate(@PathVariable String specialAssessMemberId, Model model) {
        SpecialAssessMember specialAssessMember = specialAssessMemberService.selectById(specialAssessMemberId);
        model.addAttribute("item",specialAssessMember);
        LogObjectHolder.me().set(specialAssessMember);
        return PREFIX + "specialAssessMember_edit.html";
    }

    /**
     * 获取专项工作考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/specialAssessMember/list"})
    @ResponseBody
    public Object list(SpecialAssessMember specialAssessMember) {
        Page<SpecialAssessMember> page = new PageFactory<SpecialAssessMember>().defaultPage();
        EntityWrapper< SpecialAssessMember> wrapper = new EntityWrapper<>();
        if (ToolUtil.isNotEmpty(specialAssessMember.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) specialAssessMember.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        specialAssessMemberService.selectPage(page,wrapper);
        page.setRecords(new SpecialAssessMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增专项工作考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/specialAssessMember/add"})
    @ResponseBody
    public Object add(SpecialAssessMember specialAssessMember) {
        specialAssessMemberService.insert(specialAssessMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除专项工作考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/specialAssessMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long specialAssessMemberId) {
        specialAssessMemberService.deleteById(specialAssessMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改专项工作考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/specialAssessMember/update"})
    @ResponseBody
    public Object update(SpecialAssessMember specialAssessMember) {
        specialAssessMemberService.updateById(specialAssessMember);
        return SUCCESS_TIP;
    }

    /**
     * 专项工作考核详情
     */
    @RequestMapping(value = "/detail/{specialAssessMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("specialAssessMemberId") String specialAssessMemberId) {
        return specialAssessMemberService.selectById(specialAssessMemberId);
    }
}
