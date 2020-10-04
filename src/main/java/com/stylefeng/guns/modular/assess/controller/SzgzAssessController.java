package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.assess.decorator.SzgzAssessDecorator;
import com.stylefeng.guns.modular.assess.model.SzgzAssess;
import com.stylefeng.guns.modular.assess.service.ISzgzAssessService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
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
import com.stylefeng.guns.modular.assess.model.SzgzAssess;
import com.stylefeng.guns.modular.assess.service.ISzgzAssessService;
import com.stylefeng.guns.modular.assess.decorator.SzgzAssessDecorator;

/**
 * 思政工作控制器
 *
 * @author 
 * @Date 2020-09-30 13:31:44
 */
@Controller
@RequestMapping("${guns.admin-prefix}/szgzAssess")
public class SzgzAssessController extends BaseController {

    private String PREFIX = "/assess/szgzAssess/";

    @Autowired
    private ISzgzAssessService szgzAssessService;

    /**
     * 跳转到思政工作首页
     */
    @RequestMapping("")
    public String index(String type, Model model) {
        model.addAttribute("type", type);
        return PREFIX + "szgzAssess.html";
    }

    /**
     * 跳转到添加思政工作
     */
    @RequestMapping("/szgzAssess_add")
    @RequiresPermissions(value = {"/szgzAssess/add"})
    public String szgzAssessAdd() {
        return PREFIX + "szgzAssess_add.html";
    }

    /**
     * 跳转到修改思政工作
     */
    @RequestMapping("/szgzAssess_update/{szgzAssessId}")
    @RequiresPermissions(value = {"/szgzAssess/update"})
    public String szgzAssessUpdate(@PathVariable String szgzAssessId, Model model) {
        SzgzAssess szgzAssess = szgzAssessService.selectById(szgzAssessId);
        model.addAttribute("item",szgzAssess);
        LogObjectHolder.me().set(szgzAssess);
        return PREFIX + "szgzAssess_edit.html";
    }

    /**
     * 获取思政工作列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(SzgzAssess szgzAssess) {
        Page<SzgzAssess> page = new PageFactory<SzgzAssess>().defaultPage();
        EntityWrapper< SzgzAssess> wrapper = new EntityWrapper<>();
        szgzAssessService.selectPage(page,wrapper);
        page.setRecords(new SzgzAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增思政工作
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/szgzAssess/add"})
    @ResponseBody
    public Object add(SzgzAssess szgzAssess) {
        szgzAssessService.insert(szgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除思政工作
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/szgzAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long szgzAssessId) {
        szgzAssessService.deleteById(szgzAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改思政工作
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/szgzAssess/update"})
    @ResponseBody
    public Object update(SzgzAssess szgzAssess) {
        szgzAssessService.updateById(szgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 思政工作详情
     */
    @RequestMapping(value = "/detail/{szgzAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("szgzAssessId") String szgzAssessId) {
        return szgzAssessService.selectById(szgzAssessId);
    }


    /**
     * 考核申请
     */
    @RequestMapping("/szgzAssess_apply")
    public String applyApproval(String type, Model model) {
        model.addAttribute("type", type);
        return PREFIX + "szgzAssess_apply.html";
    }


    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(SzgzAssess szgzAssess) {
        szgzAssessService.apply(szgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(SzgzAssess szgzAssess) {
        szgzAssessService.audit(szgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(SzgzAssess szgzAssess) {
        SzgzAssess params = new SzgzAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(szgzAssess.getAct().getProcInsId());
        List<Map<String,Object>> datas = new SzgzAssessDecorator(szgzAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(SzgzAssess szgzAssess, Model model) {
        szgzAssess.setProcInsId(szgzAssess.getAct().getProcInsId());
        EntityWrapper<SzgzAssess> wrapper = new EntityWrapper<>(szgzAssess);
        SzgzAssess data = szgzAssess.selectOne(wrapper);
        model.addAttribute("item", data);
        model.addAttribute("act", szgzAssess.getAct());
        return PREFIX + "szgzAssess_audit.html";
    }
}
