package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.decorator.OtherInfoDecorator;
import com.stylefeng.guns.modular.system.dict.OtherInfoDict;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 其他设置控制器
 *
 * @author 
 * @Date 2018-03-15 11:49:31
 */
@Controller
@RequestMapping("${guns.admin-prefix}/otherInfo")
public class OtherInfoController extends BaseController {

    private final String PREFIX = "/system/otherInfo/";

    @Autowired
    private IOtherInfoService otherInfoService;


    /**
     * 跳转到其他设置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "otherInfo.html";
    }

    /**
     * 跳转到添加其他设置
     */
    @RequestMapping("/otherInfo_add")
    public String otherInfoAdd() {
        return PREFIX + "otherInfo_add.html";
    }

    /**
     * 跳转到修改其他设置
     */
    @RequestMapping("/otherInfo_update/{otherInfoId}")
    public String otherInfoUpdate(@PathVariable String otherInfoId, Model model) {
        OtherInfo otherInfo = otherInfoService.selectById(otherInfoId);
        model.addAttribute("item",otherInfo);
        LogObjectHolder.me().set(otherInfo);
        return PREFIX + "otherInfo_edit.html";
    }

    /**
     * 跳转到修改其他设置
     */
    @RequestMapping("/otherInfo_file/{otherInfoId}")
    public String otherInfoFile(@PathVariable String otherInfoId, Model model) {
        OtherInfo otherInfo = otherInfoService.selectById(otherInfoId);
        model.addAttribute("item",otherInfo);
        LogObjectHolder.me().set(otherInfo);
        return PREFIX + "otherInfo_file.html";
    }

    /**
     * 获取其他设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(OtherInfo otherInfo) {
        Page<OtherInfo> page = new PageFactory<OtherInfo>().defaultPage();
        EntityWrapper< OtherInfo> wrapper = new EntityWrapper<>();
        otherInfoService.selectPage(page,wrapper);
        page.setRecords(new OtherInfoDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增其他设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OtherInfo otherInfo) {
        otherInfoService.insert(otherInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除其他设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除其他设置", key = "beizhu,otherKey,otherValue", dict = OtherInfoDict.class)
    public Object delete(@RequestParam Long otherInfoId) {
        otherInfoService.deleteById(otherInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 清空缓存
     * @return
     */
    @RequestMapping(value = "/clean/cache")
    @ResponseBody
    public Object cleanCache() {
        otherInfoService.cleanCache();
        return SUCCESS_TIP;
    }

    /**
     * 清空用户锁缓存
     * @return
     */
    @RequestMapping(value = "/clean/lock")
    @ResponseBody
    public Object cleanLock() {
        otherInfoService.cleanCache();
        return SUCCESS_TIP;
    }

    /**
     * 修改其他设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改其他设置", key = "otherKey,otherValue,beizhu", dict = OtherInfoDict.class)
    public Object update(OtherInfo otherInfo) {
        otherInfoService.updateById(otherInfo);
        return SUCCESS_TIP;
    }

    /**
     * 其他设置详情
     */
    @RequestMapping(value = "/detail/{otherInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("otherInfoId") String otherInfoId) {
        return otherInfoService.selectById(otherInfoId);
    }
}
