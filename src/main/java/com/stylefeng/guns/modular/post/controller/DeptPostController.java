package com.stylefeng.guns.modular.post.controller;

import com.stylefeng.guns.common.persistence.model.User;
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
import com.stylefeng.guns.modular.post.model.DeptPost;
import com.stylefeng.guns.modular.post.service.IDeptPostService;
import com.stylefeng.guns.modular.post.decorator.DeptPostDecorator;

/**
 * 机构职务配置控制器
 *
 * @author 
 * @Date 2021-02-18 15:45:15
 */
@Controller
@RequestMapping("${guns.admin-prefix}/deptPost")
public class DeptPostController extends BaseController {

    private String PREFIX = "/post/deptPost/";

    @Autowired
    private IDeptPostService deptPostService;

    /**
     * 跳转到机构职务配置首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/deptPost/list"})
    public String index() {
        return PREFIX + "deptPost.html";
    }

    /**
     * 跳转到添加机构职务配置
     */
    @RequestMapping("/deptPost_add")
    @RequiresPermissions(value = {"/deptPost/add"})
    public String deptPostAdd() {
        return PREFIX + "deptPost_add.html";
    }

    /**
     * 跳转到修改机构职务配置
     */
    @RequestMapping("/deptPost_update/{deptPostId}")
    @RequiresPermissions(value = {"/deptPost/update"})
    public String deptPostUpdate(@PathVariable String deptPostId, Model model) {
        DeptPost deptPost = deptPostService.selectById(deptPostId);
        model.addAttribute("item",deptPost);
        LogObjectHolder.me().set(deptPost);
        return PREFIX + "deptPost_edit.html";
    }

    /**
     * 获取机构职务配置列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/deptPost/list"})
    @ResponseBody
    public Object list(DeptPost deptPost) {
        Page<DeptPost> page = new PageFactory<DeptPost>().defaultPage();
        EntityWrapper< DeptPost> wrapper = new EntityWrapper<>();
        deptPostService.selectPage(page,wrapper);
        page.setRecords(new DeptPostDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增机构职务配置
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/deptPost/add"})
    @ResponseBody
    public Object add(DeptPost deptPost) {
        deptPostService.insert(deptPost);
        return SUCCESS_TIP;
    }

    /**
     * 删除机构职务配置
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/deptPost/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long deptPostId) {
        deptPostService.deleteById(deptPostId);
        return SUCCESS_TIP;
    }

    /**
     * 修改机构职务配置
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/deptPost/update"})
    @ResponseBody
    public Object update(DeptPost deptPost) {
        deptPostService.updateById(deptPost);
        return SUCCESS_TIP;
    }

    /**
     * 机构职务配置详情
     */
    @RequestMapping(value = "/detail/{deptPostId}")
    @ResponseBody
    public Object detail(@PathVariable("deptPostId") String deptPostId) {
        return deptPostService.selectById(deptPostId);
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/open_import")
    public String openImport() {
        return PREFIX + "deptPost_import.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/import")
    @ResponseBody
    public Object importUser(DeptPost deptPost) {
        deptPostService.importSetting(deptPost);
        return SUCCESS_TIP;
    }
}
