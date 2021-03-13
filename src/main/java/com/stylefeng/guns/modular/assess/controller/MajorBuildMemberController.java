package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.assess.decorator.MajorBuildMemberDecorator;
import com.stylefeng.guns.modular.assess.model.MajorBuildMember;
import com.stylefeng.guns.modular.assess.service.IMajorBuildMemberService;
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
 * 专业建设项目成员控制器
 *
 * @author 
 * @Date 2020-08-19 16:35:13
 */
@Controller
@RequestMapping("${guns.admin-prefix}/majorBuildMember")
public class MajorBuildMemberController extends BaseController {

    private String PREFIX = "/assess/majorBuildMember/";

    @Autowired
    private IMajorBuildMemberService majorBuildMemberService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到专业建设项目成员首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "majorBuildMember.html";
    }

    /**
     * 跳转到添加专业建设项目成员
     */
    @RequestMapping("/majorBuildMember_add")
    @RequiresPermissions(value = {"/majorBuildMember/add"})
    public String majorBuildMemberAdd() {
        return PREFIX + "majorBuildMember_add.html";
    }

    /**
     * 跳转到修改专业建设项目成员
     */
    @RequestMapping("/majorBuildMember_update/{majorBuildMemberId}")
    @RequiresPermissions(value = {"/majorBuildMember/update"})
    public String majorBuildMemberUpdate(@PathVariable String majorBuildMemberId, Model model) {
        MajorBuildMember majorBuildMember = majorBuildMemberService.selectById(majorBuildMemberId);
        model.addAttribute("item",majorBuildMember);
        LogObjectHolder.me().set(majorBuildMember);
        return PREFIX + "majorBuildMember_edit.html";
    }

    /**
     * 获取专业建设项目成员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(MajorBuildMember majorBuildMember) {
        Page<MajorBuildMember> page = new PageFactory<MajorBuildMember>().defaultPage();
        EntityWrapper< MajorBuildMember> wrapper = new EntityWrapper<>();
        if (ToolUtil.isNotEmpty(majorBuildMember.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) majorBuildMember.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        majorBuildMemberService.selectPage(page,wrapper);
        page.setRecords(new MajorBuildMemberDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增专业建设项目成员
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/majorBuildMember/add"})
    @ResponseBody
    public Object add(MajorBuildMember majorBuildMember) {
        majorBuildMemberService.insert(majorBuildMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除专业建设项目成员
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/majorBuildMember/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long majorBuildMemberId) {
        majorBuildMemberService.deleteById(majorBuildMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改专业建设项目成员
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/majorBuildMember/update"})
    @ResponseBody
    public Object update(MajorBuildMember majorBuildMember) {
        majorBuildMemberService.updateById(majorBuildMember);
        return SUCCESS_TIP;
    }

    /**
     * 专业建设项目成员详情
     */
    @RequestMapping(value = "/detail/{majorBuildMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("majorBuildMemberId") String majorBuildMemberId) {
        return majorBuildMemberService.selectById(majorBuildMemberId);
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/majorBuildMember_import")
    public String majorBuildMemberImport() {
        return PREFIX + "majorBuildMember_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(MajorBuildMember majorBuildMember) {
        majorBuildMemberService.importAssess(majorBuildMember);
        return SUCCESS_TIP;
    }
}
