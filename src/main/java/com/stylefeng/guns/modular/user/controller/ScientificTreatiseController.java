package com.stylefeng.guns.modular.user.controller;

import cn.hutool.core.util.StrUtil;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.decorator.ScientificTreatiseDecorator;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;
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
import com.stylefeng.guns.modular.user.model.ScientificTreatise;
import com.stylefeng.guns.modular.user.service.IScientificTreatiseService;
import com.stylefeng.guns.modular.user.decorator.ScientificTreatiseDecorator;

/**
 * 科研论著控制器
 *
 * @author cp
 * @Date 2020-07-02 10:13:49
 */
@Controller
@RequestMapping("${guns.admin-prefix}/scientificTreatise")
public class ScientificTreatiseController extends BaseController {

    private String PREFIX = "/user/scientificTreatise/";

    @Autowired
    private IScientificTreatiseService scientificTreatiseService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ActTaskService taskService;

    /**
     * 跳转到科研论著首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/scientificTreatise/list"})
    public String index(User user, Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "scientificTreatise.html";
    }

    /**
     * 跳转到添加科研论著
     */
    @RequestMapping("/scientificTreatise_add")
    @RequiresPermissions(value = {"/scientificTreatise/add"})
    public String scientificTreatiseAdd() {
        return PREFIX + "scientificTreatise_add.html";
    }

    /**
     * 跳转到修改科研论著
     */
    @RequestMapping("/scientificTreatise_update/{scientificTreatiseId}")
    @RequiresPermissions(value = {"/scientificTreatise/update"})
    public String scientificTreatiseUpdate(@PathVariable String scientificTreatiseId, Model model) {
        ScientificTreatise scientificTreatise = scientificTreatiseService.selectById(scientificTreatiseId);
        model.addAttribute("item",scientificTreatise);
        LogObjectHolder.me().set(scientificTreatise);
        return PREFIX + "scientificTreatise_edit.html";
    }

    /**
     * 获取科研论著列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/scientificTreatise/list"})
    @ResponseBody
    public Object list(ScientificTreatise scientificTreatise) {
        Page<ScientificTreatise> page = new PageFactory<ScientificTreatise>().defaultPage();
        EntityWrapper< ScientificTreatise> wrapper = new EntityWrapper<>();
        if (scientificTreatise.getStatus() != null) {
            wrapper.eq("status", scientificTreatise.getStatus());
        }
        if (scientificTreatise.getUserId() != null) {
            wrapper.eq("user_id", scientificTreatise.getUserId());
        }
        scientificTreatiseService.selectPage(page,wrapper);
        page.setRecords(new ScientificTreatiseDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增科研论著
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/scientificTreatise/add"})
    @ResponseBody
    public Object add(ScientificTreatise scientificTreatise) {
        scientificTreatiseService.insert(scientificTreatise);
        return SUCCESS_TIP;
    }

    /**
     * 删除科研论著
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/scientificTreatise/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long scientificTreatiseId) {
        scientificTreatiseService.deleteById(scientificTreatiseId);
        return SUCCESS_TIP;
    }

    /**
     * 修改科研论著
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/scientificTreatise/update"})
    @ResponseBody
    public Object update(ScientificTreatise scientificTreatise) {
        scientificTreatiseService.updateById(scientificTreatise);
        return SUCCESS_TIP;
    }

    /**
     * 科研论著详情
     */
    @RequestMapping(value = "/detail/{scientificTreatiseId}")
    @ResponseBody
    public Object detail(@PathVariable("scientificTreatiseId") String scientificTreatiseId) {
        return scientificTreatiseService.selectById(scientificTreatiseId);
    }

    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
//        ScientificTreatise params = new ScientificTreatise();
//        params.setUserId(ShiroKit.getUser().id);
//        params.setStatus(YesNo.YES.getCode());
//        EntityWrapper<ScientificTreatise> wrapper = new EntityWrapper<>(params);
//        List<ScientificTreatise> list = scientificTreatiseService.selectList(wrapper);
//        model.addAttribute("list", list);
        return PREFIX + "scientificTreatise_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/scientificTreatise_act")
    public String scientificTreatiseAct(ScientificTreatise scientificTreatise, Model model) {
        String path = (String) taskService.getTaskService().getVariable(scientificTreatise.getAct().getTaskId(), "act_path");
        if (StrUtil.isNotBlank(path)) {
            return "forward:" + path;
        }
        scientificTreatise.setProcInsId(scientificTreatise.getAct().getProcInsId());
        EntityWrapper<ScientificTreatise> wrapper = new EntityWrapper<>(scientificTreatise);
        wrapper.last("limit 1");
        ScientificTreatise data = scientificTreatise.selectOne(wrapper);
        User user = userService.selectIgnorePointById(data.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", scientificTreatise.getAct());
        return PREFIX + "scientificTreatise_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(ScientificTreatise scientificTreatise) {
        scientificTreatiseService.audit(scientificTreatise);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/scientificTreatiseProcData")
    @ResponseBody
    public Object scientificTreatiseProcData(ScientificTreatise scientificTreatise) {
        List<ScientificTreatise> datas = new ScientificTreatiseDecorator(scientificTreatise.selectList(new EntityWrapper<>(scientificTreatise))).decorate();
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
    public Object doAddApply(@RequestBody List<ScientificTreatise> scientificTreatises) {
        scientificTreatiseService.addApply(scientificTreatises);
        return SUCCESS_TIP;
    }
}
