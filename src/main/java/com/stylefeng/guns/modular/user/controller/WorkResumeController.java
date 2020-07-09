package com.stylefeng.guns.modular.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.decorator.WorkResumeDecorator;
import com.stylefeng.guns.modular.user.model.WorkResume;
import com.stylefeng.guns.modular.user.model.WorkResume;
import com.stylefeng.guns.modular.user.service.IWorkResumeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;
;

/**
 * 工作简历控制器
 *
 * @author cp
 * @Date 2020-06-30 09:54:17
 */
@Controller
@RequestMapping("${guns.admin-prefix}/workResume")
public class WorkResumeController extends BaseController {

    private String PREFIX = "/user/workResume/";

    @Autowired
    private IWorkResumeService workResumeService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到工作简历首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/workResume/list"})
    public String index(User user,Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "workResume.html";
    }

    /**
     * 跳转到添加工作简历
     */
    @RequestMapping("/workResume_add")
    @RequiresPermissions(value = {"/workResume/add"})
    public String workResumeAdd() {
        return PREFIX + "workResume_add.html";
    }

    /**
     * 跳转到修改工作简历
     */
    @RequestMapping("/workResume_update/{workResumeId}")
    @RequiresPermissions(value = {"/workResume/update"})
    public String workResumeUpdate(@PathVariable String workResumeId, Model model) {
        WorkResume workResume = workResumeService.selectById(workResumeId);
        model.addAttribute("item",workResume);
        LogObjectHolder.me().set(workResume);
        return PREFIX + "workResume_edit.html";
    }

    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        WorkResume params = new WorkResume();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<WorkResume> wrapper = new EntityWrapper<>(params);
        List<WorkResume> list = workResumeService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "workResume_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/workResume_act")
    public String workResumeAct(WorkResume workResume,Model model) {
        workResume.setProcInsId(workResume.getAct().getProcInsId());
        EntityWrapper<WorkResume> wrapper = new EntityWrapper<>(workResume);
        wrapper.last("limit 1");
        WorkResume data = workResume.selectOne(wrapper);
        User user = userService.selectIgnorePointById(data.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", workResume.getAct());
        return PREFIX + "workResume_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(WorkResume workResume) {
        workResumeService.audit(workResume);
        return SUCCESS_TIP;
    }


    /**
     * 获取工作简历列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/workResume/list"})
    @ResponseBody
    public Object list(WorkResume workResume) {
        Page<WorkResume> page = new PageFactory<WorkResume>().defaultPage();
        EntityWrapper< WorkResume> wrapper = new EntityWrapper<>();
        if (workResume.getStatus() != null) {
            wrapper.eq("status", workResume.getStatus());
        }
        if (workResume.getUserId() != null) {
            wrapper.eq("user_id", workResume.getUserId());
        }
        workResumeService.selectPage(page,wrapper);
        page.setRecords(new WorkResumeDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @ResponseBody
    public Object doAddApply(@RequestBody List<WorkResume> workResumes) {
        workResumeService.addApply(workResumes);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/workResumeProcData")
    @ResponseBody
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public Object workResumeProcData(WorkResume workResume) {
        List<WorkResume> datas = workResume.selectList(new EntityWrapper<>(workResume));
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }


    /**
     * 新增工作简历
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/workResume/add"})
    @ResponseBody
    public Object add(WorkResume workResume) {
        workResumeService.insert(workResume);
        return SUCCESS_TIP;
    }

    /**
     * 删除工作简历
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/workResume/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long workResumeId) {
        workResumeService.deleteById(workResumeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工作简历
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/workResume/update"})
    @ResponseBody
    public Object update(WorkResume workResume) {
        workResumeService.updateById(workResume);
        return SUCCESS_TIP;
    }

    /**
     * 工作简历详情
     */
    @RequestMapping(value = "/detail/{workResumeId}")
    @ResponseBody
    public Object detail(@PathVariable("workResumeId") String workResumeId) {
        return workResumeService.selectById(workResumeId);
    }
}
