package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificAchievement;
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
import com.stylefeng.guns.modular.assess.model.MajorBuild;
import com.stylefeng.guns.modular.assess.service.IMajorBuildService;
import com.stylefeng.guns.modular.assess.decorator.MajorBuildDecorator;

/**
 * 专业建设考核控制器
 *
 * @author 
 * @Date 2020-08-19 16:34:21
 */
@Controller
@RequestMapping("${guns.admin-prefix}/majorBuild")
public class MajorBuildController extends BaseController {

    private String PREFIX = "/assess/majorBuild/";

    @Autowired
    private IMajorBuildService majorBuildService;
    @Autowired
    private IAssessNormService assessNormService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到专业建设考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/majorBuild/list"})
    public String index() {
        return PREFIX + "majorBuild.html";
    }

    /**
     * 跳转到添加专业建设考核
     */
    @RequestMapping("/majorBuild_add")
    @RequiresPermissions(value = {"/majorBuild/add"})
    public String majorBuildAdd() {
        return PREFIX + "majorBuild_add.html";
    }

    /**
     * 立项申请
     */
    @RequestMapping("/applyApproval")
    public String applyApproval() {
        return PREFIX + "majorBuild_apply_approval.html";
    }

    /**
     * 跳转到修改专业建设考核
     */
    @RequestMapping("/majorBuild_update/{majorBuildId}")
    @RequiresPermissions(value = {"/majorBuild/update"})
    public String majorBuildUpdate(@PathVariable String majorBuildId, Model model) {
        MajorBuild majorBuild = majorBuildService.selectById(majorBuildId);
        model.addAttribute("item",majorBuild);
        LogObjectHolder.me().set(majorBuild);
        return PREFIX + "majorBuild_edit.html";
    }

    /**
     * 获取专业建设考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/majorBuild/list"})
    @ResponseBody
    public Object list(MajorBuild majorBuild) {
        Page<MajorBuild> page = new PageFactory<MajorBuild>().defaultPage();
        EntityWrapper< MajorBuild> wrapper = new EntityWrapper<>();
        majorBuildService.selectPage(page,wrapper);
        page.setRecords(new MajorBuildDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增专业建设考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/majorBuild/add"})
    @ResponseBody
    public Object add(MajorBuild majorBuild) {
        majorBuildService.insert(majorBuild);
        return SUCCESS_TIP;
    }

    /**
     * 删除专业建设考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/majorBuild/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long majorBuildId) {
        majorBuildService.deleteById(majorBuildId);
        return SUCCESS_TIP;
    }

    /**
     * 修改专业建设考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/majorBuild/update"})
    @ResponseBody
    public Object update(MajorBuild majorBuild) {
        majorBuildService.updateById(majorBuild);
        return SUCCESS_TIP;
    }

    /**
     * 专业建设考核详情
     */
    @RequestMapping(value = "/detail/{majorBuildId}")
    @ResponseBody
    public Object detail(@PathVariable("majorBuildId") String majorBuildId) {
        return majorBuildService.selectById(majorBuildId);
    }


    /**
     * 专业建设立项申请
     */
    @RequestMapping(value = "/act/apply/approval")
    @ResponseBody
    public Object applyApproval(MajorBuild majorBuild) {
        majorBuildService.applyApproval(majorBuild);
        return SUCCESS_TIP;
    }

    /**
     * 专业建设立项审核
     */
    @RequestMapping(value = "/act/audit/approval")
    @ResponseBody
    public Object auditApproval(MajorBuild majorBuild) {
        majorBuildService.auditApproval(majorBuild);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act/approval")
    public String act(MajorBuild majorBuild, Model model) {
        majorBuild.setProcInsId(majorBuild.getAct().getProcInsId());
        EntityWrapper<MajorBuild> wrapper = new EntityWrapper<>(majorBuild);
//        wrapper.last("limit 1");
        MajorBuild data = majorBuild.selectOne(wrapper);
//        User user = userService.selectIgnorePointById(data.getUserId());
        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(majorBuild.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_ZYJS);
        mainNorm = assessNormService.getByCode(mainNorm);
        model.addAttribute("norm", mainNorm);
        model.addAttribute("item", data);
        model.addAttribute("act", majorBuild.getAct());
        return PREFIX + "majorBuild_approval_audit.html";
    }

}
