package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.PersonalInfo;
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
import com.stylefeng.guns.modular.user.model.PersonalInfo;
import com.stylefeng.guns.modular.user.service.IPersonalInfoService;
import com.stylefeng.guns.modular.user.decorator.PersonalInfoDecorator;

/**
 * 自然信息控制器
 *
 * @author cp
 * @Date 2020-07-06 10:02:41
 */
@Controller
@RequestMapping("${guns.admin-prefix}/personalInfo")
public class PersonalInfoController extends BaseController {

    private String PREFIX = "/user/personalInfo/";

    @Autowired
    private IPersonalInfoService personalInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到自然信息首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/personalInfo/list"})
    public String index(Model model) {
        PersonalInfo params = new PersonalInfo();
        params.setStatus(YesNo.YES.getCode());
        params.setUserId(ShiroKit.getUser().id);
        PersonalInfo item = personalInfoService.selectOne(new EntityWrapper<>(params));
        if (item != null) {
            User user = userService.selectIgnorePointById(item.getUserId());
            Dept dept = deptService.selectById(user.getDeptId());
            model.addAttribute("dept", dept);
        }
        model.addAttribute("item", item);
        return PREFIX + "personalInfo.html";
    }

    /**
     * 跳转到添加自然信息
     */
    @RequestMapping("/personalInfo_add")
    public String personalInfoAdd() {
        return PREFIX + "personalInfo_add.html";
    }

    /**
     * 跳转到修改自然信息
     */
    @RequestMapping("/personalInfo_update/{personalInfoId}")
    @RequiresPermissions(value = {"/personalInfo/update"})
    public String personalInfoUpdate(@PathVariable String personalInfoId, Model model) {
        PersonalInfo personalInfo = personalInfoService.selectById(personalInfoId);
        model.addAttribute("item",personalInfo);
        LogObjectHolder.me().set(personalInfo);
        return PREFIX + "personalInfo_edit.html";
    }

    /**
     * 获取自然信息列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/personalInfo/list"})
    @ResponseBody
    public Object list(PersonalInfo personalInfo) {
        Page<PersonalInfo> page = new PageFactory<PersonalInfo>().defaultPage();
        EntityWrapper< PersonalInfo> wrapper = new EntityWrapper<>();
        personalInfoService.selectPage(page,wrapper);
        page.setRecords(new PersonalInfoDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增自然信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PersonalInfo personalInfo) {
        personalInfoService.addApply(personalInfo);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/personalInfo_act")
    public String personalInfoAct(PersonalInfo personalInfo, Model model) {
        model.addAttribute("act", personalInfo.getAct());
        PersonalInfo params = new PersonalInfo();
        params.setProcInsId(personalInfo.getAct().getProcInsId());
        EntityWrapper<PersonalInfo> wrapper = new EntityWrapper<>();
        PersonalInfo item = personalInfoService.selectOne(wrapper);
        User user = userService.selectIgnorePointById(item.getUserId());
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        return PREFIX + "personalInfo_edit.html";
    }
    
    /**
     * 删除自然信息
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/personalInfo/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long personalInfoId) {
        personalInfoService.deleteById(personalInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改自然信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PersonalInfo personalInfo) {
        personalInfoService.audit(personalInfo);
        return SUCCESS_TIP;
    }

    /**
     * 自然信息详情
     */
    @RequestMapping(value = "/detail/{personalInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("personalInfoId") String personalInfoId) {
        return personalInfoService.selectById(personalInfoId);
    }
}
