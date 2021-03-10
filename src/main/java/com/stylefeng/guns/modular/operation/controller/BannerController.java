package com.stylefeng.guns.modular.operation.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.operation.decorator.BannerDecorator;
import com.stylefeng.guns.modular.operation.model.Banner;
import com.stylefeng.guns.modular.operation.service.IBannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 广告管理控制器
 *
 * @author 
 * @Date 2018-03-15 17:58:55
 */
@Controller
@RequestMapping("${guns.admin-prefix}/banner")
public class BannerController extends BaseController {

    private final String PREFIX = "/operation/banner/";

    @Autowired
    private IBannerService bannerService;

    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "banner.html";
    }

    /**
     * 跳转到添加广告管理
     */
    @RequestMapping("/banner_add")
    public String bannerAdd() {
        return PREFIX + "banner_add.html";
    }

    /**
     * 跳转到修改广告管理
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable String bannerId, Model model) {
        Banner banner = bannerService.selectById(bannerId);
        model.addAttribute("item",banner);
        LogObjectHolder.me().set(banner);
        return PREFIX + "banner_edit.html";
    }

    /**
     * 获取广告管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Banner banner) {
        Page<Banner> page = new PageFactory<Banner>().defaultPage();
        EntityWrapper< Banner> wrapper = new EntityWrapper<>();
         if(StringUtils.isNotBlank(banner.getBannerName()))
            wrapper.like("BANNER_NAME",banner.getBannerName());
        bannerService.selectPage(page,wrapper);
        page.setRecords(new BannerDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增广告管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Banner banner) {
        banner.setCreateTime(new Date());
        banner.setContent(ToolUtil.unEscapeHtml4(banner.getContent()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentEN(ToolUtil.unEscapeHtml4(banner.getContentEN()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentID(ToolUtil.unEscapeHtml4(banner.getContentID()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentMY(ToolUtil.unEscapeHtml4(banner.getContentMY()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentVI(ToolUtil.unEscapeHtml4(banner.getContentVI()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentTC(ToolUtil.unEscapeHtml4(banner.getContentTC()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setStatus(YesNo.YES.getCode());
        if (banner.getIsGo() == null) {
            banner.setIsGo(YesNo.YES.getCode());
        }
        bannerService.insert(banner);
        return SUCCESS_TIP;
    }

    /**
     * 删除广告管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bannerId) {
        bannerService.deleteById(bannerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改广告管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Banner banner) {
        banner.setContent(ToolUtil.unEscapeHtml4(banner.getContent()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentEN(ToolUtil.unEscapeHtml4(banner.getContentEN()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentMY(ToolUtil.unEscapeHtml4(banner.getContentMY()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentID(ToolUtil.unEscapeHtml4(banner.getContentID()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentVI(ToolUtil.unEscapeHtml4(banner.getContentVI()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        banner.setContentTC(ToolUtil.unEscapeHtml4(banner.getContentTC()).replace("img src", "img style=\"display: block;height: auto; max-width: 100%;\" src"));
        bannerService.updateById(banner);
        return SUCCESS_TIP;
    }

    /**
     * 广告管理详情
     */
    @RequestMapping(value = "/detail/{bannerId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerId") String bannerId) {
        return bannerService.selectById(bannerId);
    }
}
