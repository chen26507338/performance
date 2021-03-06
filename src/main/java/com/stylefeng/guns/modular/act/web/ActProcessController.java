/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.util.JsonMapper;
import com.stylefeng.guns.modular.act.service.ActProcessService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${guns.admin-prefix}/act/process")
public class ActProcessController extends BaseController {
    private static String PREFIX = "/act/";

	@Autowired
	private ActProcessService actProcessService;

	/**
	 * 流程定义列表
	 */
	@RequiresPermissions("/act/process")
	@RequestMapping(value = "")
	public String processList() {
        return PREFIX + "actProcess.html";
	}

	/**
	 * 流程定义列表
	 */
	@RequiresPermissions("/act/process")
	@RequestMapping(value = "list")
    @ResponseBody
	public Object list(String category) {
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();

        //保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
	    actProcessService.processList(page, category);
		return packForBT(page);
	}

	/**
	 * 运行中的实例列表
	 */
	@RequiresPermissions("/act/process/running")
	@RequestMapping(value = "running")
	public String running() {
		return "/act/actProcessRunningList.html";
	}

	/**
	 * 运行中的实例列表
	 */
	@RequiresPermissions("/act/process/running")
	@RequestMapping(value = "running/list")
    @ResponseBody
	public Object runningList(String procInsId, String procDefKey) {
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        actProcessService.runningList(page, procInsId, procDefKey);
        return packForBT(page);
	}

	/**
	 * 读取资源，通过部署ID
	 * @param response
	 * @throws Exception
	 */
//	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
//	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "/deploy", method= RequestMethod.GET)
	public String deploy(Model model) {
		return "modules/act/actProcessDeploy";
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
//	@RequiresPermissions("act:process:edit")
//	@RequestMapping(value = "/deploy", method= RequestMethod.POST)
//	public String deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir,
//                         String category, MultipartFile file, RedirectAttributes redirectAttributes) {
//
//		String fileName = file.getOriginalFilename();
//
//		if (StringUtils.isBlank(fileName)){
//			redirectAttributes.addFlashAttribute("message", "请选择要部署的流程文件");
//		}else{
//			String message = actProcessService.deploy(exportDir, category, file);
//			redirectAttributes.addFlashAttribute("message", message);
//		}
//
//		return "redirect:" + adminPath + "/act/process";
//	}

	/**
	 * 挂起、激活流程实例
	 */
//	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "update/{state}")
    @ResponseBody
	public Object updateState(@PathVariable("state") String state, String procDefId) {
		String message = actProcessService.updateState(state, procDefId);
		return new SuccessTip(message);
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
//	@RequiresPermissions("act:process:edit")
//	@RequestMapping(value = "convert/toModel")
//	public String convertToModel(String procDefId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException {
//		org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
//		redirectAttributes.addFlashAttribute("message", "转换模型成功，模型ID="+modelData.getId());
//		return "redirect:" + adminPath + "/act/model";
//	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);;
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
//	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "delete")
    @ResponseBody
	public Object delete(String deploymentId) {
		actProcessService.deleteDeployment(deploymentId);
		return SUCCESS_TIP;
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
//	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteProcIns")
    @ResponseBody
	public Object deleteProcIns(String procInsId, String reason) {
        actProcessService.deleteProcIns(procInsId, "reason");
		return SUCCESS_TIP;
	}
	
}
