package com.stylefeng.guns.modular.post.controller;

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
import com.stylefeng.guns.modular.post.model.PostSetting;
import com.stylefeng.guns.modular.post.service.IPostSettingService;
import com.stylefeng.guns.modular.post.decorator.PostSettingDecorator;

/**
 * 职务设置控制器
 *
 * @author 
 * @Date 2021-02-18 15:39:51
 */
@Controller
@RequestMapping("${guns.admin-prefix}/postSetting")
public class PostSettingController extends BaseController {

    private String PREFIX = "/post/postSetting/";

    @Autowired
    private IPostSettingService postSettingService;

    /**
     * 跳转到职务设置首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/postSetting/list"})
    public String index() {
        return PREFIX + "postSetting.html";
    }

    /**
     * 跳转到添加职务设置
     */
    @RequestMapping("/postSetting_add")
    @RequiresPermissions(value = {"/postSetting/add"})
    public String postSettingAdd() {
        return PREFIX + "postSetting_add.html";
    }

    /**
     * 跳转到修改职务设置
     */
    @RequestMapping("/postSetting_update/{postSettingId}")
    @RequiresPermissions(value = {"/postSetting/update"})
    public String postSettingUpdate(@PathVariable String postSettingId, Model model) {
        PostSetting postSetting = postSettingService.selectById(postSettingId);
        model.addAttribute("item",postSetting);
        LogObjectHolder.me().set(postSetting);
        return PREFIX + "postSetting_edit.html";
    }

    /**
     * 获取职务设置列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/postSetting/list"})
    @ResponseBody
    public Object list(PostSetting postSetting) {
        Page<PostSetting> page = new PageFactory<PostSetting>().defaultPage();
        EntityWrapper< PostSetting> wrapper = new EntityWrapper<>();
        postSettingService.selectPage(page,wrapper);
        page.setRecords(new PostSettingDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增职务设置
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/postSetting/add"})
    @ResponseBody
    public Object add(PostSetting postSetting) {
        postSettingService.insert(postSetting);
        return SUCCESS_TIP;
    }

    /**
     * 删除职务设置
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/postSetting/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long postSettingId) {
        postSettingService.deleteById(postSettingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改职务设置
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/postSetting/update"})
    @ResponseBody
    public Object update(PostSetting postSetting) {
        postSettingService.updateById(postSetting);
        return SUCCESS_TIP;
    }

    /**
     * 职务设置详情
     */
    @RequestMapping(value = "/detail/{postSettingId}")
    @ResponseBody
    public Object detail(@PathVariable("postSettingId") String postSettingId) {
        return postSettingService.selectById(postSettingId);
    }
}
