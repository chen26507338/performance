/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stylefeng.guns.core.base.BaseModel;
import com.stylefeng.guns.modular.act.entity.Act;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Activiti Entity类
 * @author ThinkGem
 * @version 2013-05-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseActEntity<T extends Model> extends BaseModel<T>{

	private static final long serialVersionUID = 1L;

	protected Act act; 		// 流程任务对象

	public BaseActEntity() {
		super();
	}

	
	@JsonIgnore
	public Act getAct() {
		if (act == null){
			act = new Act();
		}
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	/**
	 * 获取流程实例ID
	 * @return
	 */
	public String getProcInsId() {
		return this.getAct().getProcInsId();
	}

	/**
	 * 设置流程实例ID
	 * @param procInsId
	 */
	public void setProcInsId(String procInsId) {
		this.getAct().setProcInsId(procInsId);
	}
}
