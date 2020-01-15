/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.service.ext;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Activiti Group Entity Factory
 * @author ThinkGem
 * @version 2013-11-03
 */
@Component
public class ActGroupEntityServiceFactory implements SessionFactory {
	
	@Autowired
	private ActGroupEntityService actGroupEntityService;
	
	@Override
    public Class<?> getSessionType() {
		// 返回原始的GroupIdentityManager类型
		return GroupIdentityManager.class;
	}

	@Override
    public Session openSession() {
		// 返回自定义的GroupEntityManager实例
		return actGroupEntityService;
	}

}
