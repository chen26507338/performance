/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.modular.act.service.ActModelService;
import org.activiti.engine.repository.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程模型相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${guns.admin-prefix}/act/model")
public class ActModelController extends BaseController {
    private static String PREFIX = "/act/";


    @Autowired
	private ActModelService actModelService;

    private Logger logger = LoggerFactory.getLogger(ActModelController.class);

	/**
	 * 流程模型列表
	 */
	@RequiresPermissions("/act/model/list")
	@RequestMapping(value = "")
	public String index() {
        return PREFIX + "actModel.html";
	}

    /**
     * 查询模型列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("/act/model/list")
    public Object list() {
        Page<Model> page = new PageFactory<Model>().defaultPage();
        page = actModelService.modelList(page, null);
        return packForBT(page);
    }

    @RequestMapping(value = "add")
    public String add() {
        return PREFIX + "actModel_add.html";
    }

	/**
	 * 创建模型
	 */
	@RequiresPermissions("/act/model/add")
	@RequestMapping(value = "create")
	public void create(String name, String key, String description, String category,
                       HttpServletRequest request, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			response.sendRedirect(request.getContextPath() + "/static/act/process-editor/modeler.html?modelId=" + modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建模型失败：", e);
		}
	}

	/**
	 * 根据Model部署流程
	 */
    @RequiresPermissions("/act/model/modify")
    @RequestMapping(value = "deploy")
    @ResponseBody
	public Object deploy(String id) {
		String message = actModelService.deploy(id);
        return new SuccessTip(message);
	}
	
	/**
	 * 导出model的xml文件
	 */
    @RequiresPermissions("/act/model/modify")
    @RequestMapping(value = "export")
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

//	/**
//	 * 更新Model分类
//	 */
//	@RequiresPermissions("act:model:edit")
//	@RequestMapping(value = "updateCategory")
//	public String updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
//		actModelService.updateCategory(id, category);
//		redirectAttributes.addFlashAttribute("message", "设置成功，模块ID=" + id);
//		return "redirect:" + adminPath + "/act/model";
//	}
	
	/**
	 * 删除Model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("/act/model/modify")
	@RequestMapping(value = "delete")
    @ResponseBody
	public Object delete(String id) {
		actModelService.delete(id);
		return SUCCESS_TIP;
	}
}
