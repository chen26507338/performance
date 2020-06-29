package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.assess.decorator.NormalAssessDecorator;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import org.activiti.engine.TaskService;
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
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.service.IEducationExperienceService;
import com.stylefeng.guns.modular.user.decorator.EducationExperienceDecorator;

/**
 * 学历培训控制器
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
@Controller
@RequestMapping("${guns.admin-prefix}/educationExperience")
public class EducationExperienceController extends BaseController {

    private String PREFIX = "/user/educationExperience/";

    @Autowired
    private IEducationExperienceService educationExperienceService;
    @Autowired
    private ActTaskService taskService;

    /**
     * 跳转到学历培训首页e'x
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/educationExperience/list"})
    public String index(User user,Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "educationExperience.html";
    }

    /**
     * 跳转到添加学历培训
     */
    @RequestMapping("/educationExperience_add")
    @RequiresPermissions(value = {"/educationExperience/add"})
    public String educationExperienceAdd(User user,Model model) {
        if (user.getId() == null) {
            model.addAttribute("userId",ShiroKit.getUser().id);
        }
        return PREFIX + "educationExperience_add.html";
    }
    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        EducationExperience params = new EducationExperience();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<EducationExperience> wrapper = new EntityWrapper<>(params);
        List<EducationExperience> list = educationExperienceService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "educationExperience_apply.html";
    }

    /**
     * 跳转到修改学历培训
     */
    @RequestMapping("/educationExperience_update/{educationExperienceId}")
    @RequiresPermissions(value = {"/educationExperience/update"})
    public String educationExperienceUpdate(@PathVariable String educationExperienceId, Model model) {
        EducationExperience educationExperience = educationExperienceService.selectById(educationExperienceId);
        model.addAttribute("item",educationExperience);
        LogObjectHolder.me().set(educationExperience);
        return PREFIX + "educationExperience_edit.html";
    }

    /**
     * 跳转到修改学历培训
     */
    @RequestMapping("/educationExperience_act")
    public String educationExperienceAct(EducationExperience educationExperience,Model model) {
        String path = (String) taskService.getTaskService().getVariable(educationExperience.getAct().getTaskId(), "act_path");
        if (!path.equals("/educationExperience/educationExperience_act")) {
            return "forward:" + path;
        }
        model.addAttribute("act", educationExperience.getAct());
        return PREFIX + "educationExperience_audit.html";
    }

    /**
     * 获取学历培训列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/educationExperience/list"})
    @ResponseBody
    public Object list(EducationExperience educationExperience) {
        Page<EducationExperience> page = new PageFactory<EducationExperience>().defaultPage();
        EntityWrapper< EducationExperience> wrapper = new EntityWrapper<>();
        if (educationExperience.getStatus() != null) {
            wrapper.eq("status", educationExperience.getStatus());
        }
        if (educationExperience.getUserId() != null) {
            wrapper.eq("user_id", educationExperience.getUserId());
        }
        if (educationExperience.getProcInsId() != null) {
            wrapper.eq("proc_ins_id", educationExperience.getProcInsId());
        }
        educationExperienceService.selectPage(page,wrapper);
        page.setRecords(new EducationExperienceDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 学历流程数据
     */
    @RequestMapping("/educationExperienceProcData")
    @ResponseBody
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public Object normalAssessProcData(EducationExperience educationExperience) {
        List<EducationExperience> educationExperiences = educationExperienceService.selectList(new EntityWrapper<>(educationExperience));
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        List<Map<String, Object>> datas = new EducationExperienceDecorator(educationExperiences).decorateMaps();
        for (Map<String, Object> data : datas) {
            data.remove("act");
        }
        result.put("data", datas);
        return result;
    }

    /**
     * 新增学历培训
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/educationExperience/add"})
    @ResponseBody
    public Object add(EducationExperience educationExperience) {
        educationExperienceService.insert(educationExperience);
        return SUCCESS_TIP;
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(EducationExperience educationExperience) {
        educationExperienceService.audit(educationExperience);
        return SUCCESS_TIP;
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @RequiresPermissions(value = {"/educationExperience/add"})
    @ResponseBody
    public Object doAddApply(@RequestBody List<EducationExperience> educationExperiences) {
        educationExperienceService.addApply(educationExperiences);
        return SUCCESS_TIP;
    }

    /**
     * 删除学历培训
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/educationExperience/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long educationExperienceId) {
        educationExperienceService.deleteById(educationExperienceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改学历培训
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/educationExperience/update"})
    @ResponseBody
    public Object update(EducationExperience educationExperience) {
        educationExperienceService.updateById(educationExperience);
        return SUCCESS_TIP;
    }

    /**
     * 学历培训详情
     */
    @RequestMapping(value = "/detail/{educationExperienceId}")
    @ResponseBody
    public Object detail(@PathVariable("educationExperienceId") String educationExperienceId) {
        return educationExperienceService.selectById(educationExperienceId);
    }
}
