package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.decorator.BzryAssessDecorator;
import com.stylefeng.guns.modular.assess.model.BzryAssess;
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
import com.stylefeng.guns.modular.assess.model.BzryAssess;
import com.stylefeng.guns.modular.assess.service.IBzryAssessService;
import com.stylefeng.guns.modular.assess.decorator.BzryAssessDecorator;

/**
 * 表彰荣誉考核控制器
 *
 * @author 
 * @Date 2020-10-04 12:11:14
 */
@Controller
@RequestMapping("${guns.admin-prefix}/bzryAssess")
public class BzryAssessController extends BaseController {

    private String PREFIX = "/assess/bzryAssess/";

    @Autowired
    private IBzryAssessService bzryAssessService;

    /**
     * 跳转到表彰荣誉考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/bzryAssess/list"})
    public String index() {
        return PREFIX + "bzryAssess.html";
    }

    /**
     * 跳转到添加表彰荣誉考核
     */
    @RequestMapping("/bzryAssess_add")
    @RequiresPermissions(value = {"/bzryAssess/add"})
    public String bzryAssessAdd() {
        return PREFIX + "bzryAssess_add.html";
    }

    /**
     * 跳转到修改表彰荣誉考核
     */
    @RequestMapping("/bzryAssess_update/{bzryAssessId}")
    @RequiresPermissions(value = {"/bzryAssess/update"})
    public String bzryAssessUpdate(@PathVariable String bzryAssessId, Model model) {
        BzryAssess bzryAssess = bzryAssessService.selectById(bzryAssessId);
        model.addAttribute("item",bzryAssess);
        LogObjectHolder.me().set(bzryAssess);
        return PREFIX + "bzryAssess_edit.html";
    }

    /**
     * 获取表彰荣誉考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/bzryAssess/list"})
    @ResponseBody
    public Object list(BzryAssess bzryAssess) {
        Page<BzryAssess> page = new PageFactory<BzryAssess>().defaultPage();
        EntityWrapper< BzryAssess> wrapper = new EntityWrapper<>();
        bzryAssessService.selectPage(page,wrapper);
        page.setRecords(new BzryAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增表彰荣誉考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/bzryAssess/add"})
    @ResponseBody
    public Object add(BzryAssess bzryAssess) {
        bzryAssessService.insert(bzryAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除表彰荣誉考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/bzryAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long bzryAssessId) {
        bzryAssessService.deleteById(bzryAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改表彰荣誉考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/bzryAssess/update"})
    @ResponseBody
    public Object update(BzryAssess bzryAssess) {
        bzryAssessService.updateById(bzryAssess);
        return SUCCESS_TIP;
    }

    /**
     * 表彰荣誉考核详情
     */
    @RequestMapping(value = "/detail/{bzryAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("bzryAssessId") String bzryAssessId) {
        return bzryAssessService.selectById(bzryAssessId);
    }


    /**
     * 考核申请
     */
    @RequestMapping("/bzryAssess_apply")
    public String applyApproval() {
        return PREFIX + "bzryAssess_apply.html";
    }


    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(BzryAssess bzryAssess) {
        bzryAssessService.apply(bzryAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(BzryAssess bzryAssess) {
        bzryAssessService.audit(bzryAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(BzryAssess bzryAssess) {
        BzryAssess params = new BzryAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(bzryAssess.getAct().getProcInsId());
        List<Map<String,Object>> datas = new BzryAssessDecorator(bzryAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(BzryAssess bzryAssess, Model model) {
        bzryAssess.setProcInsId(bzryAssess.getAct().getProcInsId());
        EntityWrapper<BzryAssess> wrapper = new EntityWrapper<>(bzryAssess);
        BzryAssess data = bzryAssess.selectOne(wrapper);
        model.addAttribute("item", data);
        model.addAttribute("act", bzryAssess.getAct());
        return PREFIX + "bzryAssess_audit.html";
    }
}
