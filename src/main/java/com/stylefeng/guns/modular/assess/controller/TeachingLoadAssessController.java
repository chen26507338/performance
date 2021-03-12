package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.assess.decorator.TeachingLoadAssessDecorator;
import com.stylefeng.guns.modular.assess.model.TeachingLoadAssess;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.assess.service.ITeachingLoadAssessService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;
;

/**
 * 教学考核控制器
 *
 * @author 
 * @Date 2020-09-20 15:34:09
 */
@Controller
@RequestMapping("${guns.admin-prefix}/teachingLoadAssess")
public class TeachingLoadAssessController extends BaseController {

    private String PREFIX = "/assess/teachingLoadAssess/";
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeachingLoadAssessService teachingLoadAssessService;
    @Autowired
    private IAssessNormService assessNormService;

    /**
     * 跳转到教学考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/teachingLoadAssess/list"})
    public String index() {
        return PREFIX + "teachingLoadAssess.html";
    }

    /**
     * 跳转到添加教学考核
     */
    @RequestMapping("/teachingLoadAssess_add")
    @RequiresPermissions(value = {"/teachingLoadAssess/add"})
    public String teachingLoadAssessAdd() {
        return PREFIX + "teachingLoadAssess_add.html";
    }

    /**
     * 跳转到修改教学考核
     */
    @RequestMapping("/teachingLoadAssess_update/{teachingLoadAssessId}")
    @RequiresPermissions(value = {"/teachingLoadAssess/update"})
    public String teachingLoadAssessUpdate(@PathVariable String teachingLoadAssessId, Model model) {
        TeachingLoadAssess teachingLoadAssess = teachingLoadAssessService.selectById(teachingLoadAssessId);
        model.addAttribute("item",teachingLoadAssess);
        LogObjectHolder.me().set(teachingLoadAssess);
        return PREFIX + "teachingLoadAssess_edit.html";
    }

    /**
     * 获取教学考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/teachingLoadAssess/list"})
    @ResponseBody
    public Object list(TeachingLoadAssess teachingLoadAssess) {
        Page<TeachingLoadAssess> page = new PageFactory<TeachingLoadAssess>().defaultPage();
        EntityWrapper< TeachingLoadAssess> wrapper = new EntityWrapper<>();
        if (ToolUtil.isNotEmpty(teachingLoadAssess.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) teachingLoadAssess.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        teachingLoadAssessService.selectPage(page,wrapper);
        page.setRecords(new TeachingLoadAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增教学考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/teachingLoadAssess/add"})
    @ResponseBody
    public Object add(TeachingLoadAssess teachingLoadAssess) {
        teachingLoadAssessService.insert(teachingLoadAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除教学考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/teachingLoadAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long teachingLoadAssessId) {
        teachingLoadAssessService.deleteById(teachingLoadAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教学考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/teachingLoadAssess/update"})
    @ResponseBody
    public Object update(TeachingLoadAssess teachingLoadAssess) {
        teachingLoadAssessService.updateById(teachingLoadAssess);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/teachingLoadAssess_import")
    public String teachingLoadAssessImport() {
        return PREFIX + "teachingLoadAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(TeachingLoadAssess teachingLoadAssess) {
        teachingLoadAssessService.importAssess(teachingLoadAssess);
        return SUCCESS_TIP;
    }

    /**
     * 教学考核详情
     */
    @RequestMapping(value = "/detail/{teachingLoadAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("teachingLoadAssessId") String teachingLoadAssessId) {
        return teachingLoadAssessService.selectById(teachingLoadAssessId);
    }


    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(TeachingLoadAssess teachingLoadAssess) {
        teachingLoadAssessService.apply(teachingLoadAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(TeachingLoadAssess teachingLoadAssess) {
        teachingLoadAssessService.audit(teachingLoadAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(TeachingLoadAssess teachingLoadAssess) {
        TeachingLoadAssess params = new TeachingLoadAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(teachingLoadAssess.getAct().getProcInsId());
        List<Map<String,Object>> datas = new TeachingLoadAssessDecorator(teachingLoadAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(TeachingLoadAssess teachingLoadAssess, Model model) {
        teachingLoadAssess.setProcInsId(teachingLoadAssess.getAct().getProcInsId());
        EntityWrapper<TeachingLoadAssess> wrapper = new EntityWrapper<>(teachingLoadAssess);
//        wrapper.last("limit 1");
        TeachingLoadAssess data = teachingLoadAssess.selectOne(wrapper);
//        User user = userService.selectIgnorePointById(data.getUserId());
//        AssessNorm mainNorm = assessNormService.selectById(data.getNormId());
//        model.addAttribute("norm", mainNorm);
        model.addAttribute("item", data);
        model.addAttribute("act", teachingLoadAssess.getAct());
        return PREFIX + "teachingLoadAssess_audit.html";
    }
}
