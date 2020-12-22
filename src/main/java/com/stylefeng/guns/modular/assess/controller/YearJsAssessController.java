package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.modular.assess.decorator.YearJsAssessDecorator;
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.assess.service.IYearJsAssessService;
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
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.assess.service.IYearJsAssessService;
import com.stylefeng.guns.modular.assess.decorator.YearJsAssessDecorator;

/**
 * 教师考核控制器
 *
 * @author 
 * @Date 2020-12-21 22:58:22
 */
@Controller
@RequestMapping("${guns.admin-prefix}/yearJsAssess")
public class YearJsAssessController extends BaseController {

    private String PREFIX = "/assess/yearJsAssess/";

    @Autowired
    private IYearJsAssessService yearJsAssessService;

    /**
     * 跳转到教师考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/yearJsAssess/list"})
    public String index() {
        return PREFIX + "yearJsAssess.html";
    }

    /**
     * 跳转到添加教师考核
     */
    @RequestMapping("/yearJsAssess_add")
    @RequiresPermissions(value = {"/yearJsAssess/add"})
    public String yearJsAssessAdd() {
        return PREFIX + "yearJsAssess_add.html";
    }

    /**
     * 跳转到修改教师考核
     */
    @RequestMapping("/yearJsAssess_update/{yearJsAssessId}")
    @RequiresPermissions(value = {"/yearJsAssess/update"})
    public String yearJsAssessUpdate(@PathVariable String yearJsAssessId, Model model) {
        YearJsAssess yearJsAssess = yearJsAssessService.selectById(yearJsAssessId);
        model.addAttribute("item",yearJsAssess);
        LogObjectHolder.me().set(yearJsAssess);
        return PREFIX + "yearJsAssess_edit.html";
    }

    /**
     * 获取教师考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/yearJsAssess/list"})
    @ResponseBody
    public Object list(YearJsAssess yearJsAssess) {
        Page<YearJsAssess> page = new PageFactory<YearJsAssess>().defaultPage();
        EntityWrapper< YearJsAssess> wrapper = new EntityWrapper<>();
        yearJsAssessService.selectPage(page,wrapper);
        page.setRecords(new YearJsAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增教师考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/yearJsAssess/add"})
    @ResponseBody
    public Object add(YearJsAssess yearJsAssess) {
        yearJsAssessService.insert(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除教师考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/yearJsAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long yearJsAssessId) {
        yearJsAssessService.deleteById(yearJsAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教师考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/yearJsAssess/update"})
    @ResponseBody
    public Object update(YearJsAssess yearJsAssess) {
        yearJsAssessService.updateById(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 教师考核详情
     */
    @RequestMapping(value = "/detail/{yearJsAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("yearJsAssessId") String yearJsAssessId) {
        return yearJsAssessService.selectById(yearJsAssessId);
    }



    /**
     * 考核申请
     */
    @RequestMapping("/yearJsAssess_apply")
    public String applyApproval(Model model) {
//        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "yearJsAssess_apply.html";
    }
    
    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(YearJsAssess yearJsAssess) {
        yearJsAssessService.apply(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(YearJsAssess yearJsAssess) {
        yearJsAssessService.audit(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(YearJsAssess yearJsAssess) {
        YearJsAssess params = new YearJsAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(yearJsAssess.getAct().getProcInsId());
        List<Map<String, Object>> datas = new YearJsAssessDecorator(yearJsAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(YearJsAssess yearJsAssess, Model model) {
        yearJsAssess.setProcInsId(yearJsAssess.getAct().getProcInsId());
        EntityWrapper<YearJsAssess> wrapper = new EntityWrapper<>(yearJsAssess);
//        wrapper.last("limit 1");
        YearJsAssess data = yearJsAssess.selectOne(wrapper);
        data.setKygz(
                data.getKygz().replaceFirst(",","课题名称、级别：")
                        .replaceFirst(",","\n本人承担任务：")
                        .replaceFirst(",","\n完成任务情况及成果：")
        );
        data.setSysgz(
                data.getSysgz().replaceFirst(",","工作内容：")
                        .replaceFirst(",","\n本人承担任务：")
                        .replaceFirst(",","\n完成任务情况及成果：")
        );
//        data.setProblemUrl(KaptchaUtil.formatFileUrl(data.getProblemUrl()));
//        data.setResultUrl(KaptchaUtil.formatFileUrl(data.getResultUrl()));
//        if (yearJsAssess.getAct().getTaskDefKey().equals("dean_audit")) {
//            User params = new User();
//            params.setDeptId(data.getDeptId());
//            List<User> users = userService.selectList(new EntityWrapper<>(params));
//            model.addAttribute("users", users);
//        }
        model.addAttribute("item", data);
        model.addAttribute("act", yearJsAssess.getAct());
        return PREFIX + "yearJsAssess_audit.html";
    }
}
