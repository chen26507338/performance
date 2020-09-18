package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.decorator.ManServiceMemberDecorator;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.ManService;
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.assess.service.IManServiceMemberService;
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
import com.stylefeng.guns.modular.assess.model.ManService;
import com.stylefeng.guns.modular.assess.service.IManServiceService;
import com.stylefeng.guns.modular.assess.decorator.ManServiceDecorator;

/**
 * 管理服务控制器
 *
 * @author
 * @Date 2020-09-17 16:39:45
 */
@Controller
@RequestMapping("${guns.admin-prefix}/manService")
public class ManServiceController extends BaseController {

    private String PREFIX = "/assess/manService/";

    @Autowired
    private IManServiceService manServiceService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private IManServiceMemberService manServiceMemberService;

    /**
     * 跳转到管理服务首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/manService/list"})
    public String index() {
        return PREFIX + "manService.html";
    }

    /**
     * 跳转到添加管理服务
     */
    @RequestMapping("/manService_add")
    @RequiresPermissions(value = {"/manService/add"})
    public String manServiceAdd() {
        return PREFIX + "manService_add.html";
    }

    /**
     * 跳转到修改管理服务
     */
    @RequestMapping("/manService_update/{manServiceId}")
    @RequiresPermissions(value = {"/manService/update"})
    public String manServiceUpdate(@PathVariable String manServiceId, Model model) {
        ManService manService = manServiceService.selectById(manServiceId);
        model.addAttribute("item",manService);
        LogObjectHolder.me().set(manService);
        return PREFIX + "manService_edit.html";
    }

    /**
     * 获取管理服务列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/manService/list"})
    @ResponseBody
    public Object list(ManService manService) {
        Page<ManService> page = new PageFactory<ManService>().defaultPage();
        EntityWrapper< ManService> wrapper = new EntityWrapper<>();
        manServiceService.selectPage(page,wrapper);
        page.setRecords(new ManServiceDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增管理服务
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/manService/add"})
    @ResponseBody
    public Object add(ManService manService) {
        manServiceService.insert(manService);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理服务
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/manService/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long manServiceId) {
        manServiceService.deleteById(manServiceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理服务
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/manService/update"})
    @ResponseBody
    public Object update(ManService manService) {
        manServiceService.updateById(manService);
        return SUCCESS_TIP;
    }

    /**
     * 管理服务详情
     */
    @RequestMapping(value = "/detail/{manServiceId}")
    @ResponseBody
    public Object detail(@PathVariable("manServiceId") String manServiceId) {
        return manServiceService.selectById(manServiceId);
    }

    /**
     * 考核申请
     */
    @RequestMapping("/manService_apply")
    public String applyApproval() {
        return PREFIX + "manService_apply.html";
    }

    /**
     * 专业建设立项申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(ManService manService) {
        manServiceService.apply(manService);
        return SUCCESS_TIP;
    }

    /**
     * 专业建设立项审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(ManService manService) {
        manServiceService.audit(manService);
        return SUCCESS_TIP;
    }

    /**
     * 立项流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object scientificAchievementProcData(ManService manService) {
        ManServiceMember params = new ManServiceMember();
        params.setStatus(YesNo.NO.getCode());
        params.setMServiceId(manService.getId());
        List<ManServiceMember> datas = new ManServiceMemberDecorator(manServiceMemberService.selectList(new EntityWrapper<>(params))).decorate();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(ManService manService, Model model) {
        manService.setProcInsId(manService.getAct().getProcInsId());
        EntityWrapper<ManService> wrapper = new EntityWrapper<>(manService);
//        wrapper.last("limit 1");
        ManService data = manService.selectOne(wrapper);
//        User user = userService.selectIgnorePointById(data.getUserId());
        AssessNorm mainNorm = assessNormService.selectById(data.getNormId());
        model.addAttribute("norm", mainNorm);
        model.addAttribute("item", data);
        model.addAttribute("act", manService.getAct());
        return PREFIX + "manService_audit.html";
    }
}
