package com.stylefeng.guns.modular.payment.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.JsonMapper;
import com.stylefeng.guns.modular.payment.decorator.JsAwardDecorator;
import com.stylefeng.guns.modular.payment.model.JsAward;
import com.stylefeng.guns.modular.payment.service.IJsAwardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

;
;

/**
 * 竞赛奖励控制器
 *
 * @author 
 * @Date 2021-03-04 18:48:16
 */
@Controller
@RequestMapping("${guns.admin-prefix}/jsAward")
public class JsAwardController extends BaseController {

    private String PREFIX = "/payment/jsAward/";

    @Autowired
    private IJsAwardService jsAwardService;


    /**
     * 跳转到竞赛奖励首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/jsAward/list"})
    public String index(String type,Model model) {
        String typeJson = "{" +
                "'jsjl':'竞赛奖励'," +
                "'ryjl':'荣誉奖励'," +
                "'tcgxjl':'突出贡献奖励'," +
                "'shfwj':'社会服务奖'," +
                "'zxgzj':'专项工作奖'," +
                "'gryjkhj':'个人优绩考核奖'," +
                "'bmyjkhj':'部门优绩考核奖'," +
                "'zzszjsbt':'专职思政教师补贴'," +
                "'zzfdybt':'专职辅导员补贴'," +
                "'jxwxc':'绩效外薪酬'" +
                "}";
        model.addAttribute("type", type);
        Map<String, String> types = JsonMapper.fromJsonString(typeJson, Map.class);
        model.addAttribute("typeName", types.get(type));
        return PREFIX + "jsAward.html";
    }

    /**
     * 跳转到添加竞赛奖励
     */
    @RequestMapping("/jsAward_add")
    @RequiresPermissions(value = {"/jsAward/add"})
    public String jsAwardAdd() {
        return PREFIX + "jsAward_add.html";
    }

    /**
     * 跳转到修改竞赛奖励
     */
    @RequestMapping("/jsAward_update/{jsAwardId}")
    @RequiresPermissions(value = {"/jsAward/update"})
    public String jsAwardUpdate(@PathVariable String jsAwardId, Model model) {
        JsAward jsAward = jsAwardService.selectById(jsAwardId);
        model.addAttribute("item",jsAward);
        LogObjectHolder.me().set(jsAward);
        return PREFIX + "jsAward_edit.html";
    }

    /**
     * 获取竞赛奖励列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/jsAward/list"})
    @ResponseBody
    public Object list(JsAward jsAward) {
        Page<JsAward> page = new PageFactory<JsAward>().defaultPage();
        EntityWrapper< JsAward> wrapper = new EntityWrapper<>();
        if (StrUtil.isNotBlank(jsAward.getType())) {
            wrapper.eq("type", jsAward.getType());
        }
        jsAwardService.selectPage(page,wrapper);
        page.setRecords(new JsAwardDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增竞赛奖励
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/jsAward/add"})
    @ResponseBody
    public Object add(JsAward jsAward) {
        jsAwardService.insert(jsAward);
        return SUCCESS_TIP;
    }

    /**
     * 删除竞赛奖励
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/jsAward/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long jsAwardId) {
        jsAwardService.deleteById(jsAwardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改竞赛奖励
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/jsAward/update"})
    @ResponseBody
    public Object update(JsAward jsAward) {
        jsAwardService.updateById(jsAward);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到导入现有考核绩效
     */
    @RequestMapping("/jsAward_import")
    public String jsAwardImport(String type,Model model) {
        model.addAttribute("type", type);
        return PREFIX + "jsAward_import.html";
    }

    /**
     * 导入考核
     */
    @RequestMapping(value = "/importData")
    @ResponseBody
    public Object importData(JsAward jsAward) {
        jsAwardService.importData(jsAward);
        return SUCCESS_TIP;
    }

    /**
     * 竞赛奖励详情
     */
    @RequestMapping(value = "/detail/{jsAwardId}")
    @ResponseBody
    public Object detail(@PathVariable("jsAwardId") String jsAwardId) {
        return jsAwardService.selectById(jsAwardId);
    }
}
