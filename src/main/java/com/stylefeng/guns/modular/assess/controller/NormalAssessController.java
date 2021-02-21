package com.stylefeng.guns.modular.assess.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.dictmap.DictMap;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
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
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import com.stylefeng.guns.modular.assess.service.INormalAssessService;
import com.stylefeng.guns.modular.assess.decorator.NormalAssessDecorator;

import static com.stylefeng.guns.common.constant.factory.MutiStrFactory.parseKeyValue;

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
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到考核指标库首页
     */
    @RequestMapping("")
//    @RequiresPermissions(value = {"/normalAssess/list"})
    public String index(NormalAssess normalAssess,Model model) {
        model.addAttribute("type", normalAssess.getType());
        model.addAttribute("account", normalAssess.getExpand().get("account"));
        model.addAttribute("year", normalAssess.getYear());
        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "normalAssess.html";
    }

    /**
     * 跳转到导入考核绩效
     */
    @RequestMapping("/normalAssess_import")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    public String normalAssessImport(String type,Model model) {
        model.addAttribute("type", type);
        return PREFIX + "normalAssess_import.html";
    }

    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/normal_importAssess")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    public String normalImportAssess(String type,Model model) {
        model.addAttribute("type", type);
        return PREFIX + "normal_importAssess.html";
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
        params.setProcInsId(normalAssess.getAct().getProcInsId());
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
        EntityWrapper< NormalAssess> wrapper = new EntityWrapper<>(normalAssess);
        normalAssessService.selectPage(page,wrapper);
        page.setRecords(new NormalAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestBody Map data) {
        if (CollUtil.isEmpty(data)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        NormalAssess normalAssess = new NormalAssess();
        normalAssess.setType((String) data.get("type"));
        normalAssess.putExpand("data", data.get("data"));
        normalAssessService.insert(normalAssess);
        return SUCCESS_TIP;
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/import")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    @ResponseBody
    public Object dataImport(NormalAssess normalAssess) {
        normalAssessService.insert(normalAssess);
        return SUCCESS_TIP;
    }
    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
//    @RequiresPermissions(value = {"/normalAssess/add"})
    @ResponseBody
    public Object importAssess(NormalAssess normalAssess) {
        normalAssessService.importAssess(normalAssess);
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
