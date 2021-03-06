/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stylefeng.guns.core.util.JsonMapper;
import com.stylefeng.guns.modular.act.dao.ActMapper;
import com.stylefeng.guns.modular.act.entity.Act;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActProcessService  {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
    @Autowired
    private ActTaskService actTaskService;
    @Resource
    private ActMapper actMapper;

    private Logger logger = LoggerFactory.getLogger(ActProcessService.class);

	/**
	 * 流程定义列表
	 */
	public Page<Map<String,Object>> processList(Page<Map<String,Object>> page, String category) {

	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().orderByProcessDefinitionKey().asc();
	    
	    if (StrUtil.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setTotal(processDefinitionQuery.count());

        List<Map<String, Object>> mapList = new ArrayList<>();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getCurrent() - 1, page.getSize());
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> process = BeanUtil.beanToMap(processDefinition);
            process.remove("processDefinition");
            map.put("process", process);
            map.put("deployment", BeanUtil.beanToMap(deployment));
	      mapList.add(map);
	    }
        page.setRecords(mapList);
		return page;
	}

	/**
	 * 流程定义列表
	 */
	public Page<Map<String, Object>> runningList(Page<Map<String, Object>> page, String procInsId, String procDefKey) {

	    ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

	    if (StrUtil.isNotBlank(procInsId)){
		    processInstanceQuery.processInstanceId(procInsId);
	    }
	    
	    if (StrUtil.isNotBlank(procDefKey)){
		    processInstanceQuery.processDefinitionKey(procDefKey);
	    }
	    
	    page.setTotal(processInstanceQuery.count());
        List<ProcessInstance> list = processInstanceQuery.listPage(page.getCurrent() - 1, page.getSize());
        List<Map<String, Object>> result = new ArrayList<>();

        for (ProcessInstance processInstance : list) {
            Map<String, Object> temp = BeanUtil.beanToMap(processInstance);
            result.add(temp);
        }
        page.setRecords(result);
		return page;
	}
	
	/**
	 * 读取资源，通过部署ID
	 */
	public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception {
		
		if (StrUtil.isBlank(procDefId)){
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
			procDefId = processInstance.getProcessDefinitionId();
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		
		String resourceName = "";
		if ("image".equals(resType)) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if ("xml".equals(resType)) {
			resourceName = processDefinition.getResourceName();
		}
		
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		return resourceAsStream;
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@Transactional(readOnly = false)
	public String deploy(String exportDir, String category, MultipartFile file) {

		StringBuilder message = new StringBuilder();
		
		String fileName = file.getOriginalFilename();
		
		try {
			InputStream fileInputStream = file.getInputStream();
			Deployment deployment;
			String extension = FilenameUtils.getExtension(fileName);
			if ("zip".equals(extension) || "bar".equals(extension)) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
			} else if ("png".equals(extension)) {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			} else if (fileName.contains("bpmn20.xml")) {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			} else if ("bpmn".equals(extension)) { // bpmn扩展名特殊处理，转换为bpmn20.xml
				String baseName = FilenameUtils.getBaseName(fileName);
				deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
			} else {
				message = new StringBuilder("不支持的文件类型：" + extension);
				return message.toString();
			}
			
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

			// 设置流程分类
			for (ProcessDefinition processDefinition : list) {
//					ActUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
				message.append("部署成功，流程ID=").append(processDefinition.getId()).append("<br/>");
			}
			
			if (list.size() == 0){
				message = new StringBuilder("部署失败，没有流程。");
			}
			
		} catch (Exception e) {
			throw new ActivitiException("部署失败！", e);
		}
		return message.toString();
	}
	
	/**
	 * 设置流程分类
	 */
	@Transactional(readOnly = false)
	public void updateCategory(String procDefId, String category) {
		repositoryService.setProcessDefinitionCategory(procDefId, category);
	}

	/**
	 * 挂起、激活流程实例
	 */
	@Transactional
	public String updateState(String state, String procDefId) {
		if ("active".equals(state)) {
			repositoryService.activateProcessDefinitionById(procDefId, true, null);
			return "已激活ID为[" + procDefId + "]的流程定义。";
		} else if ("suspend".equals(state)) {
			repositoryService.suspendProcessDefinitionById(procDefId, true, null);
			return "已挂起ID为[" + procDefId + "]的流程定义。";
		}
		return "无操作";
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@Transactional(readOnly = false)
	public org.activiti.engine.repository.Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException {
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
		processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, StandardCharsets.UTF_8);
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	
		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		org.activiti.engine.repository.Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getCategory());
		modelData.setDeploymentId(processDefinition.getDeploymentId());
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
	
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());
	
		repositoryService.saveModel(modelData);
	
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	
		return modelData;
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	public List<String> exportDiagrams(String exportDir) throws IOException {
		List<String> files = new ArrayList<String>();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
		
		for (ProcessDefinition processDefinition : list) {
			String diagramResourceName = processDefinition.getDiagramResourceName();
			String key = processDefinition.getKey();
			int version = processDefinition.getVersion();
			String diagramPath = "";

			InputStream resourceAsStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(), diagramResourceName);
			byte[] b = new byte[resourceAsStream.available()];

			@SuppressWarnings("unused")
			int len = -1;
			resourceAsStream.read(b, 0, b.length);

			// create file if not exist
			String diagramDir = exportDir + "/" + key + "/" + version;
			File diagramDirFile = new File(diagramDir);
			if (!diagramDirFile.exists()) {
				diagramDirFile.mkdirs();
			}
			diagramPath = diagramDir + "/" + diagramResourceName;
			File file = new File(diagramPath);

			// 文件存在退出
			if (file.exists()) {
				// 文件大小相同时直接返回否则重新创建文件(可能损坏)
				logger.debug("diagram exist, ignore... : {}", diagramPath);
				
				files.add(diagramPath);
			} else {
				file.createNewFile();
				logger.debug("export diagram to : {}", diagramPath);

				// wirte bytes to file
				FileUtils.writeByteArrayToFile(file, b, true);
				
				files.add(diagramPath);
			}
			
		}
		
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	@Transactional(readOnly = false)
	public void deleteDeployment(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**
	 * 删除部署的流程实例
	 * @param procInsId 流程实例ID
	 * @param deleteReason 删除原因，可为空
	 */
	@Transactional(readOnly = false)
	public void deleteProcIns(String procInsId, String deleteReason) {
        ProcessInstance processInstance = actTaskService.getProcIns(procInsId);
        String[] businessKey = processInstance.getBusinessKey().split(":");
        Act act = new Act();
        act.setBusinessTable(businessKey[0]);
        act.setProcInsId(procInsId);
        actMapper.deleteBusinessDataByProInsId(act);
		runtimeService.deleteProcessInstance(procInsId, deleteReason);
	}
	
}
