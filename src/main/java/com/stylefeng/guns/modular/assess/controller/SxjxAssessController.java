package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.assess.decorator.SxjxAssessDecorator;
import com.stylefeng.guns.modular.assess.model.SxjxAssess;
import com.stylefeng.guns.modular.assess.service.ISxjxAssessService;
import com.stylefeng.guns.modular.job.service.IDeptService;
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
 * 实训绩效考核控制器
 *
 * @author
 * @Date 2020-10-10 11:53:51
 */
@Controller
@RequestMapping("${guns.admin-prefix}/sxjxAssess")
public class SxjxAssessController extends BaseController {

    private String PREFIX = "/assess/sxjxAssess/";

    @Autowired
    private ISxjxAssessService sxjxAssessService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;


    /**
     * 跳转到实训绩效考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/sxjxAssess/list"})
    public String index() {
        return PREFIX + "sxjxAssess.html";
    }

    /**
     * 跳转到添加实训绩效考核
     */
    @RequestMapping("/sxjxAssess_add")
    @RequiresPermissions(value = {"/sxjxAssess/add"})
    public String sxjxAssessAdd() {
        return PREFIX + "sxjxAssess_add.html";
    }

    /**
     * 跳转到修改实训绩效考核
     */
    @RequestMapping("/sxjxAssess_update/{sxjxAssessId}")
    @RequiresPermissions(value = {"/sxjxAssess/update"})
    public String sxjxAssessUpdate(@PathVariable String sxjxAssessId, Model model) {
        SxjxAssess sxjxAssess = sxjxAssessService.selectById(sxjxAssessId);
        model.addAttribute("item", sxjxAssess);
        LogObjectHolder.me().set(sxjxAssess);
        return PREFIX + "sxjxAssess_edit.html";
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/sxjxAssess_import")
    public String sxjxAssessImport() {
        return PREFIX + "sxjxAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(SxjxAssess sxjxAssess) {
        sxjxAssessService.importAssess(sxjxAssess);
        return SUCCESS_TIP;
    }
    
    /**
     * 获取实训绩效考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/sxjxAssess/list"})
    @ResponseBody
    public Object list(SxjxAssess sxjxAssess) {
        Page<SxjxAssess> page = new PageFactory<SxjxAssess>().defaultPage();
        EntityWrapper<SxjxAssess> wrapper = new EntityWrapper<>();
        if (ToolUtil.isNotEmpty(sxjxAssess.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) sxjxAssess.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        sxjxAssessService.selectPage(page, wrapper);
        page.setRecords(new SxjxAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增实训绩效考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/sxjxAssess/add"})
    @ResponseBody
    public Object add(SxjxAssess sxjxAssess) {
        sxjxAssessService.insert(sxjxAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除实训绩效考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/sxjxAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long sxjxAssessId) {
        sxjxAssessService.deleteById(sxjxAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改实训绩效考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/sxjxAssess/update"})
    @ResponseBody
    public Object update(SxjxAssess sxjxAssess) {
        sxjxAssessService.updateById(sxjxAssess);
        return SUCCESS_TIP;
    }

    /**
     * 实训绩效考核详情
     */
    @RequestMapping(value = "/detail/{sxjxAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("sxjxAssessId") String sxjxAssessId) {
        return sxjxAssessService.selectById(sxjxAssessId);
    }


    /**
     * 考核申请
     */
    @RequestMapping("/sxjxAssess_apply")
    public String applyApproval(Model model) {
        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "sxjxAssess_apply.html";
    }

    /**
     * 考核申请
     */
    @RequestMapping("/sxjxAssess_allocation")
    public String applyAllocation(Model model) {
        User params = new User();
        params.setDeptId(ShiroKit.getUser().deptId);
//        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        SxjxAssess assessParams = new SxjxAssess();
//        assessParams.setYear(currentYears.getOtherValue());
        assessParams.setDeptId(ShiroKit.getUser().deptId);
        List<SxjxAssess> assessList = sxjxAssessService.selectList(new EntityWrapper<>(assessParams));
        double sum = ISxjxAssessService.MAX_POINT;
        for (SxjxAssess sxjxAssess : assessList) {
            sum -= sxjxAssess.getMainNormPoint();
        }
        model.addAttribute("maxPoint", sum);
        model.addAttribute("userList", userService.selectList(new EntityWrapper<>(params)));
        return PREFIX + "sxjxAssess_allocation.html";
    }

    /**
     * 分配分数
     */
    @RequestMapping(value = "/doAllocation")
    @ResponseBody
    public Object doAllocation(SxjxAssess sxjxAssess) {
//        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");

        SxjxAssess assessParams = new SxjxAssess();
//        assessParams.setYear(currentYears.getOtherValue());
        assessParams.setDeptId(ShiroKit.getUser().deptId);
        List<SxjxAssess> assessList = sxjxAssessService.selectList(new EntityWrapper<>(assessParams));
        double sum = ISxjxAssessService.MAX_POINT;
        for (SxjxAssess item : assessList) {
            sum -= item.getMainNormPoint();
        }
        sxjxAssess.setYear(assessList.get(0).getYear());
        sxjxAssess.putExpand("maxPoint", sum);
        sxjxAssessService.doAllocation(sxjxAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(SxjxAssess sxjxAssess) {
        sxjxAssessService.apply(sxjxAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(SxjxAssess sxjxAssess) {
        sxjxAssessService.audit(sxjxAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(SxjxAssess sxjxAssess) {
        SxjxAssess params = new SxjxAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(sxjxAssess.getAct().getProcInsId());
        List<Map<String, Object>> datas = new SxjxAssessDecorator(sxjxAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(SxjxAssess sxjxAssess, Model model) {
        sxjxAssess.setProcInsId(sxjxAssess.getAct().getProcInsId());
        EntityWrapper<SxjxAssess> wrapper = new EntityWrapper<>(sxjxAssess);
//        wrapper.last("limit 1");
        SxjxAssess data = sxjxAssess.selectOne(wrapper);
        data.setProblemUrl(KaptchaUtil.formatFileUrl(data.getProblemUrl()));
        data.setResultUrl(KaptchaUtil.formatFileUrl(data.getResultUrl()));
        if (sxjxAssess.getAct().getTaskDefKey().equals("dean_audit")) {
            User params = new User();
            params.setDeptId(data.getDeptId());
            List<User> users = userService.selectList(new EntityWrapper<>(params));
            model.addAttribute("users", users);
        }
        model.addAttribute("item", data);
        model.addAttribute("act", sxjxAssess.getAct());
        return PREFIX + "sxjxAssess_audit.html";
    }
}
