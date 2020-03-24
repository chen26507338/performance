package com.stylefeng.guns.modular.assess.controller;

import cn.hutool.core.util.StrUtil;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.system.service.IRoleService;
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
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.assess.decorator.AssessNormDecorator;

/**
 *  考核指标库控制器
 *
 * @author 
 * @Date 2020-02-02 13:38:54
 */
@Controller
@RequestMapping("${guns.admin-prefix}/assessNorm")
public class AssessNormController extends BaseController {

    private String PREFIX = "/assess/assessNorm/";

    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;


    /**
     * 跳转到 考核指标库首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/assessNorm/list"})
    public String index(Model model) {
        model.addAttribute("typeList", assessCoefficientService.selectAll());
        return PREFIX + "assessNorm.html";
    }

    /**
     * 跳转到添加 考核指标库
     */
    @RequestMapping("/assessNorm_add")
    @RequiresPermissions(value = {"/assessNorm/add"})
    public String assessNormAdd(Model model) {
        model.addAttribute("typeList", assessCoefficientService.selectAll());
        return PREFIX + "assessNorm_add.html";
    }

    /**
     * 跳转到修改 考核指标库
     */
    @RequestMapping("/assessNorm_update/{assessNormId}")
    @RequiresPermissions(value = {"/assessNorm/update"})
    public String assessNormUpdate(@PathVariable String assessNormId, Model model) {
        AssessNorm assessNorm = assessNormService.selectById(assessNormId);
        if (assessNorm.getDeptId() != IAssessNormService.TYPE_MAIN_DEPT) {
            AssessNorm param = new AssessNorm();
            param.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
            param.setCode(assessNorm.getCode());
            param.setType(assessNorm.getType());
            AssessNorm mainNorm = assessNormService.getByCode(param);
            model.addAttribute("mainPoint", mainNorm.getPoint());
        }
        model.addAttribute("item",assessNorm);
        model.addAttribute("typeList", assessCoefficientService.selectAll());
        LogObjectHolder.me().set(assessNorm);
        return PREFIX + "assessNorm_edit.html";
    }

    /**
     * 获取 考核指标库列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/assessNorm/list"})
    @ResponseBody
    public Object list(AssessNorm assessNorm) {
        Page<AssessNorm> page = new PageFactory<AssessNorm>().defaultPage();
        EntityWrapper< AssessNorm> wrapper = new EntityWrapper<>();
        if (StrUtil.isNotBlank(assessNorm.getCode())) {
            wrapper.eq("code", assessNorm.getCode());
        }
        if (StrUtil.isNotBlank(assessNorm.getContent())) {
            wrapper.like("content", assessNorm.getContent());
        }
        if (StrUtil.isNotBlank(assessNorm.getType())) {
            wrapper.eq("type", assessNorm.getType());
        }
        assessNormService.selectPage(page,wrapper);
        page.setRecords(new AssessNormDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增 考核指标库
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/assessNorm/add"})
    @ResponseBody
    public Object add(AssessNorm assessNorm) {
        //如果不是校级指标编辑者，编辑院级指标
        if (!ShiroKit.getUser().roleList.contains(IRoleService.TYPE_MAIN_ASSESS_HANDLE)) {
            assessNorm.setDeptId(ShiroKit.getUser().deptId);
        }
        assessNormService.insert(assessNorm);
        return SUCCESS_TIP;
    }

    /**
     * 删除 考核指标库
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/assessNorm/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long assessNormId) {
        assessNormService.deleteById(assessNormId);
        return SUCCESS_TIP;
    }

    /**
     * 修改 考核指标库
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/assessNorm/update"})
    @ResponseBody
    public Object update(AssessNorm assessNorm) {
        assessNormService.updateById(assessNorm);
        return SUCCESS_TIP;
    }

    /**
     *  考核指标库详情
     */
    @RequestMapping(value = "/detail/{assessNormId}")
    @ResponseBody
    public Object detail(@PathVariable("assessNormId") String assessNormId) {
        return assessNormService.selectById(assessNormId);
    }
}
