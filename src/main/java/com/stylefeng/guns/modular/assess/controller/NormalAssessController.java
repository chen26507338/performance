package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
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
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import com.stylefeng.guns.modular.assess.service.INormalAssessService;
import com.stylefeng.guns.modular.assess.decorator.NormalAssessDecorator;

/**
 * 考核指标库控制器
 *
 * @author 
 * @Date 2020-02-02 13:18:03
 */
@Controller
@RequestMapping("${guns.admin-prefix}/normalAssess")
public class NormalAssessController extends BaseController {

    private String PREFIX = "/assess/normalAssess/";

    @Autowired
    private INormalAssessService normalAssessService;
    @Autowired
    private IAssessNormService assessNormService;

    /**
     * 跳转到考核指标库首页
     */
    @RequestMapping("")
//    @RequiresPermissions(value = {"/normalAssess/list"})
    public String index(String type,Model model) {
        model.addAttribute("type", type);
        return PREFIX + "normalAssess.html";
    }

    /**
     * 跳转到添加考核指标库
     */
    @RequestMapping("/normalAssess_add")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    public String normalAssessAdd(String type,Model model) {
        model.addAttribute("type", type);
        return PREFIX + "normalAssess_add.html";
    }

    /**
     * 跳转到修改考核指标库
     */
    @RequestMapping("/normalAssess_update")
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public String normalAssessUpdate(NormalAssess normalAssess, Model model) {
        NormalAssess params = new NormalAssess();
        params.setProcInsId(normalAssess.getProcInsId());
        EntityWrapper<NormalAssess> wrapper = new EntityWrapper<>(params);
        wrapper.last("limit 1");
        params = normalAssessService.selectOne(wrapper);
        AssessNorm norm = assessNormService.selectById(params.getNormId());

        model.addAttribute("proInsId", normalAssess.getAct().getProcInsId());
        model.addAttribute("act", normalAssess.getAct());
        model.addAttribute("type", norm.getType());
        LogObjectHolder.me().set(normalAssess);
        return PREFIX + "normalAssess_audit.html";
    }

    /**
     * 考核流程数据
     */
    @RequestMapping("/normalAssessProcData")
    @ResponseBody
//    @RequiresPermissions(value = {"/normalAssess/update"})
    public Object normalAssessProcData(NormalAssess normalAssess) {
        List<NormalAssess> normalAssesses = normalAssessService.selectList(new EntityWrapper<>(normalAssess));
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        List<Map<String, Object>> datas = new NormalAssessDecorator(normalAssesses).decorateMaps();
        for (Map<String, Object> data : datas) {
            data.remove("act");
        }
        result.put("data", datas);
        return result;
    }



    /**
     * 获取考核指标库列表
     */
    @RequestMapping(value = "/list")
//    @RequiresPermissions(value = {"/normalAssess/list"})
    @ResponseBody
    public Object list(NormalAssess normalAssess) {
        Page<NormalAssess> page = new PageFactory<NormalAssess>().defaultPage();
        EntityWrapper< NormalAssess> wrapper = new EntityWrapper<>();
         if(normalAssess.getCreateTime() != null) {
             wrapper.eq("create_time",normalAssess.getCreateTime());
         }
        normalAssessService.selectPage(page,wrapper);
        page.setRecords(new NormalAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增考核指标库
     */
    @RequestMapping(value = "/add")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    @ResponseBody
    public Object add(NormalAssess normalAssess) {
        normalAssessService.insert(normalAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除考核指标库
     */
    @RequestMapping(value = "/delete")
//    @RequiresPermissions(value = {"/normalAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long normalAssessId) {
        normalAssessService.deleteById(normalAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考核指标库
     */
    @RequestMapping(value = "/update")
//    @RequiresPermissions(value = {"/normalAssess/update"})
    @ResponseBody
    public Object update(NormalAssess normalAssess) {
        normalAssessService.updateById(normalAssess);
        return SUCCESS_TIP;
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/audit")
//    @RequiresPermissions(value = {"/normalAssess/update"})
    @ResponseBody
    public Object audit(NormalAssess normalAssess) {
        normalAssessService.audit(normalAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核指标库详情
     */
    @RequestMapping(value = "/detail/{normalAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("normalAssessId") String normalAssessId) {
        return normalAssessService.selectById(normalAssessId);
    }
}
