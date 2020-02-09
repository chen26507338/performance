package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
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
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.decorator.AssessNormPointDecorator;

/**
 * 考核分控制器
 *
 * @author 
 * @Date 2020-02-09 10:15:21
 */
@Controller
@RequestMapping("${guns.admin-prefix}/assessNormPoint")
public class AssessNormPointController extends BaseController {

    private String PREFIX = "/assess/assessNormPoint/";

    @Autowired
    private IAssessNormPointService assessNormPointService;

    /**
     * 跳转到考核分首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/assessNormPoint/list"})
    public String index() {
        return PREFIX + "assessNormPoint.html";
    }

    /**
     * 跳转到添加考核分
     */
    @RequestMapping("/assessNormPoint_add")
    @RequiresPermissions(value = {"/assessNormPoint/add"})
    public String assessNormPointAdd() {
        return PREFIX + "assessNormPoint_add.html";
    }

    /**
     * 跳转到修改考核分
     */
    @RequestMapping("/assessNormPoint_update/{assessNormPointId}")
    @RequiresPermissions(value = {"/assessNormPoint/update"})
    public String assessNormPointUpdate(@PathVariable String assessNormPointId, Model model) {
        AssessNormPoint assessNormPoint = assessNormPointService.selectById(assessNormPointId);
        model.addAttribute("item",assessNormPoint);
        LogObjectHolder.me().set(assessNormPoint);
        return PREFIX + "assessNormPoint_edit.html";
    }

    /**
     * 获取考核分列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/assessNormPoint/list"})
    @ResponseBody
    public Object list(AssessNormPoint assessNormPoint) {
        Page<AssessNormPoint> page = new PageFactory<AssessNormPoint>().defaultPage();
        EntityWrapper< AssessNormPoint> wrapper = new EntityWrapper<>();
         if(assessNormPoint.getUserId() != null)
            wrapper.eq("user_id",assessNormPoint.getUserId());
         if(StringUtils.isNotBlank(assessNormPoint.getYear()))
            wrapper.eq("year",assessNormPoint.getYear());
         if(assessNormPoint.getDeptId() != null)
            wrapper.eq("dept_id",assessNormPoint.getDeptId());
        assessNormPointService.selectPage(page,wrapper);
        page.setRecords(new AssessNormPointDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增考核分
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/assessNormPoint/add"})
    @ResponseBody
    public Object add(AssessNormPoint assessNormPoint) {
        assessNormPointService.insert(assessNormPoint);
        return SUCCESS_TIP;
    }

    /**
     * 删除考核分
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/assessNormPoint/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long assessNormPointId) {
        assessNormPointService.deleteById(assessNormPointId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考核分
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/assessNormPoint/update"})
    @ResponseBody
    public Object update(AssessNormPoint assessNormPoint) {
        assessNormPointService.updateById(assessNormPoint);
        return SUCCESS_TIP;
    }

    /**
     * 考核分详情
     */
    @RequestMapping(value = "/detail/{assessNormPointId}")
    @ResponseBody
    public Object detail(@PathVariable("assessNormPointId") String assessNormPointId) {
        return assessNormPointService.selectById(assessNormPointId);
    }
}
