package com.stylefeng.guns.modular.user.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.model.Kinship;
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

import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.service.IKinshipService;
import com.stylefeng.guns.modular.user.decorator.KinshipDecorator;

/**
 * 亲属关系控制器
 *
 * @author cp
 * @Date 2020-06-30 17:27:31
 */
@Controller
@RequestMapping("${guns.admin-prefix}/kinship")
public class KinshipController extends BaseController {

    private String PREFIX = "/user/kinship/";

    @Autowired
    private IKinshipService kinshipService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到亲属关系首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/kinship/list"})
    public String index(User user,Model model) {
        if (user.getId() == null) {
            user.setId(ShiroKit.getUser().id);
        }
        model.addAttribute(user);
        return PREFIX + "kinship.html";
    }

    /**
     * 跳转到添加亲属关系
     */
    @RequestMapping("/kinship_add")
    @RequiresPermissions(value = {"/kinship/add"})
    public String kinshipAdd() {
        return PREFIX + "kinship_add.html";
    }


    /**
     * 跳转到申请修改
     */
    @RequestMapping("/addApply")
    public String addApply(Model model) {
        Kinship params = new Kinship();
        params.setUserId(ShiroKit.getUser().id);
        params.setStatus(YesNo.YES.getCode());
        EntityWrapper<Kinship> wrapper = new EntityWrapper<>(params);
        List<Kinship> list = kinshipService.selectList(wrapper);
        model.addAttribute("list", list);
        return PREFIX + "kinship_apply.html";
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/kinship_act")
    public String kinshipAct(Kinship kinship, Model model) {
        kinship.setProcInsId(kinship.getAct().getProcInsId());
        EntityWrapper<Kinship> wrapper = new EntityWrapper<>(kinship);
        wrapper.last("limit 1");
        Kinship ks = kinshipService.selectOne(wrapper);
        User user = userService.selectIgnorePointById(ks.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("act", kinship.getAct());
        return PREFIX + "kinship_audit.html";
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(Kinship kinship) {
        kinshipService.audit(kinship);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到修改亲属关系
     */
    @RequestMapping("/kinship_update/{kinshipId}")
    @RequiresPermissions(value = {"/kinship/update"})
    public String kinshipUpdate(@PathVariable String kinshipId, Model model) {
        Kinship kinship = kinshipService.selectById(kinshipId);
        model.addAttribute("item", kinship);
        LogObjectHolder.me().set(kinship);
        return PREFIX + "kinship_edit.html";
    }

    /**
     * 获取亲属关系列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/kinship/list"})
    @ResponseBody
    public Object list(Kinship kinship) {
        Page<Kinship> page = new PageFactory<Kinship>().defaultPage();
        EntityWrapper<Kinship> wrapper = new EntityWrapper<>();
        if (kinship.getStatus() != null) {
            wrapper.eq("status", kinship.getStatus());
        }
        if (kinship.getUserId() != null) {
            wrapper.eq("user_id", kinship.getUserId());
        }
        kinshipService.selectPage(page, wrapper);
        page.setRecords(new KinshipDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }


    /**
     * 流程数据
     */
    @RequestMapping("/kinshipProcData")
    @ResponseBody
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public Object kinshipProcData(Kinship kinship) {
        List<Kinship> datas = kinship.selectList(new EntityWrapper<>(kinship));
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", new KinshipDecorator(datas).decorate());
        return result;
    }

    /**
     * 新增申请
     */
    @RequestMapping(value = "/doAddApply")
    @ResponseBody
    public Object doAddApply(@RequestBody List<Kinship> kinships) {
        kinshipService.addApply(kinships);
        return SUCCESS_TIP;
    }
    
    /**
     * 新增亲属关系
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/kinship/add"})
    @ResponseBody
    public Object add(Kinship kinship) {
        kinshipService.insert(kinship);
        return SUCCESS_TIP;
    }

    /**
     * 删除亲属关系
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/kinship/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long kinshipId) {
        kinshipService.deleteById(kinshipId);
        return SUCCESS_TIP;
    }

    /**
     * 修改亲属关系
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/kinship/update"})
    @ResponseBody
    public Object update(Kinship kinship) {
        kinshipService.updateById(kinship);
        return SUCCESS_TIP;
    }

    /**
     * 亲属关系详情
     */
    @RequestMapping(value = "/detail/{kinshipId}")
    @ResponseBody
    public Object detail(@PathVariable("kinshipId") String kinshipId) {
        return kinshipService.selectById(kinshipId);
    }
}
