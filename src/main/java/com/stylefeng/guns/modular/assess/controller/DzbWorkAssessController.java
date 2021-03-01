package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.assess.decorator.DzbWorkAssessDecorator;
import com.stylefeng.guns.modular.assess.model.DzbWorkAssess;
import com.stylefeng.guns.modular.assess.service.IDzbWorkAssessService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
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
 * 党支部工作考核控制器
 *
 * @author 
 * @Date 2020-09-24 13:06:50
 */
@Controller
@RequestMapping("${guns.admin-prefix}/dzbWorkAssess")
public class DzbWorkAssessController extends BaseController {

    private String PREFIX = "/assess/dzbWorkAssess/";

    @Autowired
    private IDzbWorkAssessService dzbWorkAssessService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOtherInfoService otherInfoService;

    /**
     * 跳转到党支部工作考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/dzbWorkAssess/list"})
    public String index() {
        return PREFIX + "dzbWorkAssess.html";
    }

    /**
     * 跳转到添加党支部工作考核
     */
    @RequestMapping("/dzbWorkAssess_add")
    @RequiresPermissions(value = {"/dzbWorkAssess/add"})
    public String dzbWorkAssessAdd() {
        return PREFIX + "dzbWorkAssess_add.html";
    }

    /**
     * 跳转到修改党支部工作考核
     */
    @RequestMapping("/dzbWorkAssess_update/{dzbWorkAssessId}")
    @RequiresPermissions(value = {"/dzbWorkAssess/update"})
    public String dzbWorkAssessUpdate(@PathVariable String dzbWorkAssessId, Model model) {
        DzbWorkAssess dzbWorkAssess = dzbWorkAssessService.selectById(dzbWorkAssessId);
        model.addAttribute("item",dzbWorkAssess);
        LogObjectHolder.me().set(dzbWorkAssess);
        return PREFIX + "dzbWorkAssess_edit.html";
    }

    /**
     * 获取党支部工作考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/dzbWorkAssess/list"})
    @ResponseBody
    public Object list(DzbWorkAssess dzbWorkAssess) {
        Page<DzbWorkAssess> page = new PageFactory<DzbWorkAssess>().defaultPage();
        EntityWrapper< DzbWorkAssess> wrapper = new EntityWrapper<>();
        dzbWorkAssessService.selectPage(page,wrapper);
        page.setRecords(new DzbWorkAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增党支部工作考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/dzbWorkAssess/add"})
    @ResponseBody
    public Object add(DzbWorkAssess dzbWorkAssess) {
        dzbWorkAssessService.insert(dzbWorkAssess);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/dzbWorkAssess_import")
    public String dzbWorkAssessImport() {
        return PREFIX + "dzbWorkAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(DzbWorkAssess dzbWorkAssess) {
        dzbWorkAssessService.importAssess(dzbWorkAssess);
        return SUCCESS_TIP;
    }
    
    /**
     * 删除党支部工作考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/dzbWorkAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long dzbWorkAssessId) {
        dzbWorkAssessService.deleteById(dzbWorkAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改党支部工作考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/dzbWorkAssess/update"})
    @ResponseBody
    public Object update(DzbWorkAssess dzbWorkAssess) {
        dzbWorkAssessService.updateById(dzbWorkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 党支部工作考核详情
     */
    @RequestMapping(value = "/detail/{dzbWorkAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("dzbWorkAssessId") String dzbWorkAssessId) {
        return dzbWorkAssessService.selectById(dzbWorkAssessId);
    }



    /**
     * 考核申请
     */
    @RequestMapping("/dzbWorkAssess_apply")
    public String applyApproval(Model model) {
        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "dzbWorkAssess_apply.html";
    }

    /**
     * 考核申请
     */
    @RequestMapping("/dzbWorkAssess_allocation")
    public String applyAllocation(Model model) {
        User params = new User();
        params.setDeptId(ShiroKit.getUser().deptId);
        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        DzbWorkAssess assessParams = new DzbWorkAssess();
        assessParams.setYear(currentYears.getOtherValue());
        assessParams.setDeptId(ShiroKit.getUser().deptId);
        List<DzbWorkAssess> assessList = dzbWorkAssessService.selectList(new EntityWrapper<>(assessParams));
        double sum = IDzbWorkAssessService.MAX_POINT;
        for (DzbWorkAssess dzbWorkAssess : assessList) {
            sum -= dzbWorkAssess.getMainNormPoint();
        }
        model.addAttribute("maxPoint", sum);
        model.addAttribute("userList", userService.selectList(new EntityWrapper<>(params)));
        return PREFIX + "dzbWorkAssess_allocation.html";
    }

    /**
     * 分配分数
     */
    @RequestMapping(value = "/doAllocation")
    @ResponseBody
    public Object doAllocation(DzbWorkAssess dzbWorkAssess) {
        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");

        DzbWorkAssess assessParams = new DzbWorkAssess();
        assessParams.setYear(currentYears.getOtherValue());
        assessParams.setDeptId(ShiroKit.getUser().deptId);
        List<DzbWorkAssess> assessList = dzbWorkAssessService.selectList(new EntityWrapper<>(assessParams));
        double sum = IDzbWorkAssessService.MAX_POINT;
        for (DzbWorkAssess item : assessList) {
            sum -= item.getMainNormPoint();
        }
        dzbWorkAssess.putExpand("maxPoint", sum);
        dzbWorkAssessService.doAllocation(dzbWorkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(DzbWorkAssess dzbWorkAssess) {
        dzbWorkAssessService.apply(dzbWorkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(DzbWorkAssess dzbWorkAssess) {
        dzbWorkAssessService.audit(dzbWorkAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(DzbWorkAssess dzbWorkAssess) {
        DzbWorkAssess params = new DzbWorkAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(dzbWorkAssess.getAct().getProcInsId());
        List<Map<String,Object>> datas = new DzbWorkAssessDecorator(dzbWorkAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(DzbWorkAssess dzbWorkAssess, Model model) {
        dzbWorkAssess.setProcInsId(dzbWorkAssess.getAct().getProcInsId());
        EntityWrapper<DzbWorkAssess> wrapper = new EntityWrapper<>(dzbWorkAssess);
//        wrapper.last("limit 1");
        DzbWorkAssess data = dzbWorkAssess.selectOne(wrapper);
//        User user = userService.selectIgnorePointById(data.getUserId());
//        AssessNorm mainNorm = assessNormService.selectById(data.getNormId());
//        model.addAttribute("norm", mainNorm);
        model.addAttribute("item", data);
        model.addAttribute("act", dzbWorkAssess.getAct());
        return PREFIX + "dzbWorkAssess_audit.html";
    }
}
