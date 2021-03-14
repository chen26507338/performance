package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.decorator.FdygzAssessDecorator;
import com.stylefeng.guns.modular.assess.decorator.FdygzAssessMemberDecorator;
import com.stylefeng.guns.modular.assess.model.FdygzAssess;
import com.stylefeng.guns.modular.assess.model.FdygzAssessMember;
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
import com.stylefeng.guns.modular.assess.model.FdygzAssess;
import com.stylefeng.guns.modular.assess.service.IFdygzAssessService;
import com.stylefeng.guns.modular.assess.decorator.FdygzAssessDecorator;

/**
 * 辅导员工作考核控制器
 *
 * @author 
 * @Date 2020-10-07 11:53:54
 */
@Controller
@RequestMapping("${guns.admin-prefix}/fdygzAssess")
public class FdygzAssessController extends BaseController {

    private String PREFIX = "/assess/fdygzAssess/";

    @Autowired
    private IFdygzAssessService fdygzAssessService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到辅导员工作考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/fdygzAssess/list"})
    public String index() {
        return PREFIX + "fdygzAssess.html";
    }

    /**
     * 跳转到添加辅导员工作考核
     */
    @RequestMapping("/fdygzAssess_add")
    @RequiresPermissions(value = {"/fdygzAssess/add"})
    public String fdygzAssessAdd() {
        return PREFIX + "fdygzAssess_add.html";
    }

    /**
     * 跳转到修改辅导员工作考核
     */
    @RequestMapping("/fdygzAssess_update/{fdygzAssessId}")
    @RequiresPermissions(value = {"/fdygzAssess/update"})
    public String fdygzAssessUpdate(@PathVariable String fdygzAssessId, Model model) {
        FdygzAssess fdygzAssess = fdygzAssessService.selectById(fdygzAssessId);
        model.addAttribute("item",fdygzAssess);
        LogObjectHolder.me().set(fdygzAssess);
        return PREFIX + "fdygzAssess_edit.html";
    }

    /**
     * 获取辅导员工作考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/fdygzAssess/list"})
    @ResponseBody
    public Object list(FdygzAssess fdygzAssess) {
        Page<FdygzAssess> page = new PageFactory<FdygzAssess>().defaultPage();
        EntityWrapper< FdygzAssess> wrapper = new EntityWrapper<>();

        if (ToolUtil.isNotEmpty(fdygzAssess.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) fdygzAssess.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        fdygzAssessService.selectPage(page,wrapper);
        page.setRecords(new FdygzAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增辅导员工作考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/fdygzAssess/add"})
    @ResponseBody
    public Object add(FdygzAssess fdygzAssess) {
        fdygzAssessService.insert(fdygzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除辅导员工作考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/fdygzAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long fdygzAssessId) {
        fdygzAssessService.deleteById(fdygzAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改辅导员工作考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/fdygzAssess/update"})
    @ResponseBody
    public Object update(FdygzAssess fdygzAssess) {
        fdygzAssessService.updateById(fdygzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 辅导员工作考核详情
     */
    @RequestMapping(value = "/detail/{fdygzAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("fdygzAssessId") String fdygzAssessId) {
        return fdygzAssessService.selectById(fdygzAssessId);
    }



    /**
     * 考核申请
     */
    @RequestMapping("/fdygzAssess_apply")
    public String applyApproval() {
        return PREFIX + "fdygzAssess_apply.html";
    }


    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(FdygzAssess fdygzAssess) {
        fdygzAssessService.apply(fdygzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(FdygzAssess fdygzAssess) {
        fdygzAssessService.audit(fdygzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(FdygzAssess fdygzAssess) {
        FdygzAssessMember params = new FdygzAssessMember();
        params.setStatus(YesNo.NO.getCode());
        params.setFWorkId(fdygzAssess.getId());
        List<Map<String,Object>> datas = new FdygzAssessMemberDecorator(params.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(FdygzAssess fdygzAssess, Model model) {
        fdygzAssess.setProcInsId(fdygzAssess.getAct().getProcInsId());
        EntityWrapper<FdygzAssess> wrapper = new EntityWrapper<>(fdygzAssess);
        FdygzAssess data = fdygzAssess.selectOne(wrapper);
        model.addAttribute("item", data);
        model.addAttribute("act", fdygzAssess.getAct());
        return PREFIX + "fdygzAssess_audit.html";
    }
}
