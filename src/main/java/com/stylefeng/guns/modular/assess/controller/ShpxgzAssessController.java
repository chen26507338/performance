package com.stylefeng.guns.modular.assess.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.assess.decorator.ShpxgzAssessDecorator;
import com.stylefeng.guns.modular.assess.model.ShpxgzAssess;
import com.stylefeng.guns.modular.assess.service.IShpxgzAssessService;
import com.stylefeng.guns.modular.system.service.IUserService;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

;
;

/**
 * 社会培训工作考核控制器
 *
 * @author 
 * @Date 2021-03-01 23:40:34
 */
@Controller
@RequestMapping("${guns.admin-prefix}/shpxgzAssess")
public class ShpxgzAssessController extends BaseController {

    private String PREFIX = "/assess/shpxgzAssess/";

    @Autowired
    private IShpxgzAssessService shpxgzAssessService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到社会培训工作考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/shpxgzAssess/list"})
    public String index() {
        return PREFIX + "shpxgzAssess.html";
    }

    /**
     * 跳转到添加社会培训工作考核
     */
    @RequestMapping("/shpxgzAssess_add")
    @RequiresPermissions(value = {"/shpxgzAssess/add"})
    public String shpxgzAssessAdd() {
        return PREFIX + "shpxgzAssess_add.html";
    }

    /**
     * 跳转到修改社会培训工作考核
     */
    @RequestMapping("/shpxgzAssess_update/{shpxgzAssessId}")
    @RequiresPermissions(value = {"/shpxgzAssess/update"})
    public String shpxgzAssessUpdate(@PathVariable String shpxgzAssessId, Model model) {
        ShpxgzAssess shpxgzAssess = shpxgzAssessService.selectById(shpxgzAssessId);
        model.addAttribute("item",shpxgzAssess);
        LogObjectHolder.me().set(shpxgzAssess);
        return PREFIX + "shpxgzAssess_edit.html";
    }

    /**
     * 获取社会培训工作考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/shpxgzAssess/list"})
    @ResponseBody
    public Object list(ShpxgzAssess shpxgzAssess) {
        Page<ShpxgzAssess> page = new PageFactory<ShpxgzAssess>().defaultPage();
        EntityWrapper< ShpxgzAssess> wrapper = new EntityWrapper<>();

        if (ToolUtil.isNotEmpty(shpxgzAssess.getExpand().get("user"))) {
            User user = userService.fuzzyFind((String) shpxgzAssess.getExpand().get("user"));
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                return packForBT(new PageFactory<User>().defaultPage());
            }
        }
        shpxgzAssessService.selectPage(page,wrapper);
        page.setRecords(new ShpxgzAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/shpxgzAssess_import")
    public String shpxgzAssessImport() {
        return PREFIX + "shpxgzAssess_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importAssess")
    @ResponseBody
    public Object importAssess(ShpxgzAssess shpxgzAssess) {
        shpxgzAssessService.importAssess(shpxgzAssess);
        return SUCCESS_TIP;
    }
    
    /**
     * 新增社会培训工作考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/shpxgzAssess/add"})
    @ResponseBody
    public Object add(ShpxgzAssess shpxgzAssess) {
        shpxgzAssessService.insert(shpxgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除社会培训工作考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/shpxgzAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long shpxgzAssessId) {
        shpxgzAssessService.deleteById(shpxgzAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改社会培训工作考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/shpxgzAssess/update"})
    @ResponseBody
    public Object update(ShpxgzAssess shpxgzAssess) {
        shpxgzAssessService.updateById(shpxgzAssess);
        return SUCCESS_TIP;
    }

    /**
     * 社会培训工作考核详情
     */
    @RequestMapping(value = "/detail/{shpxgzAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("shpxgzAssessId") String shpxgzAssessId) {
        return shpxgzAssessService.selectById(shpxgzAssessId);
    }
}
