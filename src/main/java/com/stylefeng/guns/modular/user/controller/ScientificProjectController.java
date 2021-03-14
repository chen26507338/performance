package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.assess.model.RypzAssess;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.log.LogObjectHolder;

import java.util.*;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.service.IScientificProjectService;
import com.stylefeng.guns.modular.user.decorator.ScientificProjectDecorator;

/**
 * 科研项目控制器
 *
 * @author cp
 * @Date 2020-07-02 12:39:00
 */
@Controller
@RequestMapping("${guns.admin-prefix}/scientificProject")
public class ScientificProjectController extends BaseController {

    private String PREFIX = "/user/scientificProject/";

    @Autowired
    private IScientificProjectService scientificProjectService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到科研项目首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/scientificProject/list"})
    public String index(User user, Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "scientificProject.html";
    }

    /**
     * 跳转到科研项目首页
     */
    @RequestMapping("/index")
    public String index() {
        return PREFIX + "scientificProject.html";
    }

    /**
     * 跳转到添加科研项目
     */
    @RequestMapping("/scientificProject_add")
    @RequiresPermissions(value = {"/scientificProject/add"})
    public String scientificProjectAdd() {
        return PREFIX + "scientificProject_add.html";
    }

    /**
     * 跳转到修改科研项目
     */
    @RequestMapping("/scientificProject_update/{scientificProjectId}")
    @RequiresPermissions(value = {"/scientificProject/update"})
    public String scientificProjectUpdate(@PathVariable String scientificProjectId, Model model) {
        ScientificProject scientificProject = scientificProjectService.selectById(scientificProjectId);
        model.addAttribute("item",scientificProject);
        LogObjectHolder.me().set(scientificProject);
        return PREFIX + "scientificProject_edit.html";
    }

    /**
     * 获取科研项目列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(ScientificProject scientificProject) {
        Page<ScientificProject> page = new PageFactory<ScientificProject>().defaultPage();
        EntityWrapper< ScientificProject> wrapper = new EntityWrapper<>();
        if (scientificProject.getStatus() != null) {
            wrapper.eq("status", scientificProject.getStatus());
        }
        if (scientificProject.getUserId() != null) {
            wrapper.eq("user_id", scientificProject.getUserId());
        }
        if (ToolUtil.isNotEmpty(scientificProject.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) scientificProject.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        scientificProjectService.selectPage(page,wrapper);
        page.setRecords(new ScientificProjectDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增科研项目
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/scientificProject/add"})
    @ResponseBody
    public Object add(ScientificProject scientificProject) {
        scientificProjectService.insert(scientificProject);
        return SUCCESS_TIP;
    }

    /**
     * 删除科研项目
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/scientificProject/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long scientificProjectId) {
        scientificProjectService.deleteById(scientificProjectId);
        return SUCCESS_TIP;
    }

    /**
     * 修改科研项目
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/scientificProject/update"})
    @ResponseBody
    public Object update(ScientificProject scientificProject) {
        scientificProjectService.updateById(scientificProject);
        return SUCCESS_TIP;
    }

    /**
     * 科研项目详情
     */
    @RequestMapping(value = "/detail/{scientificProjectId}")
    @ResponseBody
    public Object detail(@PathVariable("scientificProjectId") String scientificProjectId) {
        return scientificProjectService.selectById(scientificProjectId);
    }


    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        ScientificProject params = new ScientificProject();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<ScientificProject> wrapper = new EntityWrapper<>(params);
        List<ScientificProject> list = scientificProjectService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "scientificProject_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/scientificProject_act")
    public String scientificProjectAct(ScientificProject scientificProject, Model model) {
        scientificProject.setProcInsId(scientificProject.getAct().getProcInsId());
        EntityWrapper<ScientificProject> wrapper = new EntityWrapper<>(scientificProject);
        wrapper.last("limit 1");
        ScientificProject data = scientificProject.selectOne(wrapper);
        User user = userService.selectIgnorePointById(data.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", scientificProject.getAct());
        return PREFIX + "scientificProject_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(ScientificProject scientificProject) {
        scientificProjectService.audit(scientificProject);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/scientificProjectProcData")
    @ResponseBody
    public Object scientificProjectProcData(ScientificProject scientificProject) {
        List<ScientificProject> datas = new ScientificProjectDecorator(scientificProject.selectList(new EntityWrapper<>(scientificProject))).decorate();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @ResponseBody
    public Object doAddApply(@RequestBody List<ScientificProject> scientificProjects) {
        scientificProjectService.addApply(scientificProjects);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/scientificProject_import")
    public String scientificProjectImport() {
        return PREFIX + "scientificProject_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(ScientificProject scientificProject) {
        scientificProjectService.importAssess(scientificProject);
        return SUCCESS_TIP;
    }
}
