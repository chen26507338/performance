/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.act.entity.Act;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程工具
 * @author ThinkGem
 * @version 2013-11-03
 */
public class ActUtils {

//	private static Logger logger = LoggerFactory.getLogger(ActUtils.class);
	
	/**
	 * 定义流程定义KEY，必须以“PD_”开头
	 * 组成结构：string[]{"流程标识","业务主表表名"}
	 */
	public static final String[] PD_JOB_TASK = new String[]{"task_appoint", "job_task"};
	public static final String[] PD_NORMAL_ASSESS = new String[]{"normal_assess", "normal_assess"};

//	/**
//	 * 流程定义Map（自动初始化）
//	 */
//	private static Map<String, String> procDefMap = new HashMap<String, String>() {
//		private static final long serialVersionUID = 1L;
//		{
//			for (Field field : ActUtils.class.getFields()){
//				if(StringUtils.startsWith(field.getName(), "PD_")){
//					try{
//						String[] ss = (String[])field.get(null);
//						put(ss[0], ss[1]);
//					}catch (Exception e) {
//						logger.debug("load pd error: {}", field.getName());
//					}
//				}
//			}
//		}
//	};
//
//	/**
//	 * 获取流程执行（办理）URL
//	 * @param procId
//	 * @return
//	 */
//	public static String getProcExeUrl(String procId) {
//		String url = procDefMap.get(StringUtils.split(procId, ":")[0]);
//		if (StringUtils.isBlank(url)){
//			return "404";
//		}
//		return url;
//	}

//	@SuppressWarnings({ "unused" })
//	public static Map<String, Object> getMobileEntity(Object entity,String spiltType){
//		if(spiltType==null){
//			spiltType="@";
//		}
//		Map<String, Object> map = Maps.newHashMap();
//
//		List<String> field = Lists.newArrayList();
//		List<String> value = Lists.newArrayList();
//		List<String> chinesName = Lists.newArrayList();
//
//		try{
//			for (Method m : entity.getClass().getMethods()){
//				if (m.getAnnotation(JsonIgnore.class) == null && m.getAnnotation(JsonBackReference.class) == null && m.getName().startsWith("get")){
//					if (m.isAnnotationPresent(FieldName.class)) {
//						Annotation p = m.getAnnotation(FieldName.class);
//						FieldName fieldName=(FieldName) p;
//						chinesName.add(fieldName.value());
//					}else{
//						chinesName.add("");
//					}
//					if (m.getName().equals("getAct")){
//						Object act = m.invoke(entity, new Object[]{});
//						Method actMet = act.getClass().getMethod("getTaskId");
//						map.put("taskId", ObjectUtils.toString(m.invoke(act, new Object[]{}), ""));
//					}else{
//						field.add(StringUtils.uncapitalize(m.getName().substring(3)));
//						value.add(ObjectUtils.toString(m.invoke(entity, new Object[]{}), ""));
//					}
//				}
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		map.put("beanTitles", StringUtils.join(field, spiltType));
//		map.put("beanInfos", StringUtils.join(value, spiltType));
//		map.put("chineseNames", StringUtils.join(chinesName, spiltType));
//
//		return map;
//	}

	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, Act act){

        GunsProperties champagneProperties = SpringContextHolder.getBean(GunsProperties.class);
		StringBuilder formUrl = new StringBuilder();


        formUrl.append(champagneProperties.getAdminPrefix());
        formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("act.taskId=").append(act.getTaskId() != null ? act.getTaskId() : "");
		formUrl.append("&act.taskName=").append(act.getTaskName() != null ? URLUtil.encode(act.getTaskName()) : "");
		formUrl.append("&act.taskDefKey=").append(act.getTaskDefKey() != null ? act.getTaskDefKey() : "");
		formUrl.append("&act.procInsId=").append(act.getProcInsId() != null ? act.getProcInsId() : "");
		formUrl.append("&act.procDefId=").append(act.getProcDefId() != null ? act.getProcDefId() : "");
        if (act.getStatus() != null) {
            formUrl.append("&act.status=").append(act.getStatus() != null ? act.getStatus() : "");
        }
        if (StrUtil.isNotBlank(act.getBusinessId()) && !"null".equals(act.getBusinessId())) {
            formUrl.append("&id=").append(act.getBusinessId() != null ? act.getBusinessId() : "");
        }
		return formUrl.toString();
	}

	/**
	 * 转换流程节点类型为中文说明
	 * @param type 英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<>();
		types.put("userTask", "用户任务");
		types.put("serviceTask", "系统任务");
		types.put("startEvent", "开始节点");
		types.put("endEvent", "结束节点");
		types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
		types.put("inclusiveGateway", "并行处理任务");
		types.put("callActivity", "子流程");
		return types.get(type) == null ? type : types.get(type);
	}

	public static UserEntity toActivitiUser(User user){
		if (user == null){
			return null;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getAccount());
		userEntity.setFirstName(user.getName());
		userEntity.setLastName(StringUtils.EMPTY);
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}
	
	public static GroupEntity toActivitiGroup(Role role){
		if (role == null){
			return null;
		}
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getName());
		groupEntity.setName(role.getName());
//        groupEntity.setType(role.getDeptid() + "");
		groupEntity.setRevision(1);
		return groupEntity;
	}
	
}
