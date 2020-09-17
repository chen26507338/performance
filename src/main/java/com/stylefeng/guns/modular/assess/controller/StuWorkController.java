package com.stylefeng.guns.modular.assess.controller;

import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.assess.decorator.StuWorkMemberDecorator;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.StuWork;
import com.stylefeng.guns.modular.assess.model.StuWorkMember;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.assess.service.IStuWorkMemberService;
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
import com.stylefeng.guns.modular.assess.model.StuWork;
import com.stylefeng.guns.modular.assess.service.IStuWorkService;
import com.stylefeng.guns.modular.assess.decorator.StuWorkDecorator;

/**
 * 学生工作考核控制器
 *
 * @author cp
 * @Date 2020-09-16 15:25:30
 */
@Controller
@RequestMapping("${guns.admin-prefix}/stuWork")
public class StuWorkController extends BaseController {

    private String PREFIX = "/assess/stuWork/";

    @Autowired
    private IStuWorkService stuWorkService;
    @Autowired
    private IStuWorkMemberService stuWorkMemberService;
    @Autowired
    private IAssessNormService assessNormService;

    /**
     * 跳转到学生工作考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/stuWork/list"})
    public String index() {
        return PREFIX + "stuWork.html";
    }

    /**
     * 跳转到添加学生工作考核
     */
    @RequestMapping("/stuWork_add")
    @RequiresPermissions(value = {"/stuWork/add"})
    public String stuWorkAdd() {
        return PREFIX + "stuWork_add.html";
    }

    /**
     * 跳转到修改学生工作考核
     */
    @RequestMapping("/stuWork_update/{stuWorkId}")
    @RequiresPermissions(value = {"/stuWork/update"})
    public String stuWorkUpdate(@PathVariable String stuWorkId, Model model) {
        StuWork stuWork = stuWorkService.selectById(stuWorkId);
        model.addAttribute("item",stuWork);
        LogObjectHolder.me().set(stuWork);
        return PREFIX + "stuWork_edit.html";
    }

    /**
     * 获取学生工作考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/stuWork/list"})
    @ResponseBody
    public Object list(StuWork stuWork) {
        Page<StuWork> page = new PageFactory<StuWork>().defaultPage();
        EntityWrapper< StuWork> wrapper = new EntityWrapper<>();
        stuWorkService.selectPage(page,wrapper);
        page.setRecords(new StuWorkDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增学生工作考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/stuWork/add"})
    @ResponseBody
    public Object add(StuWork stuWork) {
        stuWorkService.insert(stuWork);
        return SUCCESS_TIP;
    }

    /**
     * 删除学生工作考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/stuWork/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long stuWorkId) {
        stuWorkService.deleteById(stuWorkId);
        return SUCCESS_TIP;
    }

    /**
     * 修改学生工作考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/stuWork/update"})
    @ResponseBody
    public Object update(StuWork stuWork) {
        stuWorkService.updateById(stuWork);
        return SUCCESS_TIP;
    }

    /**
     * 学生工作考核详情
     */
    @RequestMapping(value = "/detail/{stuWorkId}")
    @ResponseBody
    public Object detail(@PathVariable("stuWorkId") String stuWorkId) {
        return stuWorkService.selectById(stuWorkId);
    }

    /**
     * 立项申请
     */
    @RequestMapping("/stuWork_apply")
    public String applyApproval() {
        return PREFIX + "stuWork_apply.html";
    }

    /**
     * 专业建设立项申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(StuWork stuWork) {
        stuWorkService.apply(stuWork);
        return SUCCESS_TIP;
    }
    
    /**
     * 专业建设立项审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(StuWork stuWork) {
        stuWorkService.audit(stuWork);
        return SUCCESS_TIP;
    }

    /**
     * 立项流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object scientificAchievementProcData(StuWork stuWork) {
        StuWorkMember params = new StuWorkMember();
        params.setStatus(YesNo.NO.getCode());
        params.setSWorkId(stuWork.getId());
        List<StuWorkMember> datas = new StuWorkMemberDecorator(stuWorkMemberService.selectList(new EntityWrapper<>(params))).decorate();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(StuWork stuWork, Model model) {
        stuWork.setProcInsId(stuWork.getAct().getProcInsId());
        EntityWrapper<StuWork> wrapper = new EntityWrapper<>(stuWork);
//        wrapper.last("limit 1");
        StuWork data = stuWork.selectOne(wrapper);
//        User user = userService.selectIgnorePointById(data.getUserId());
        AssessNorm mainNorm = new AssessNorm();
        mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
        mainNorm.setCode(stuWork.getNormCode());
        mainNorm.setType(IAssessCoefficientService.TYPE_ZYJS);
        mainNorm = assessNormService.getByCode(mainNorm);
        model.addAttribute("norm", mainNorm);
        model.addAttribute("item", data);
        model.addAttribute("act", stuWork.getAct());
        return PREFIX + "stuWork_audit.html";
    }
}
