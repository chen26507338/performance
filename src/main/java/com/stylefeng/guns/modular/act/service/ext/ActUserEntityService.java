/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.stylefeng.guns.modular.act.service.ext;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.google.common.collect.Lists;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Activiti User Entity Service
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
public class ActUserEntityService extends UserEntityManager {

    @Autowired
	private IUserService userService;
    @Autowired
    private IRoleService roleService;

	@Override
    public User createNewUser(String userId) {
		return new UserEntity(userId);
	}

	@Override
    public void insertUser(User user) {
		throw new RuntimeException("not implement method.");
	}

	public void updateUser(UserEntity updatedUser) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public UserEntity findUserById(String userId) {
		return ActUtils.toActivitiUser(userService.selectById(userId));
	}

	@Override
    public void deleteUser(String userId) {
		User user = findUserById(userId);
		if (user != null) {
			userService.deleteById(userId);
		}
	}

	@Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<Group> findGroupsByUser(String userId) {
        List<Group> list = Lists.newArrayList();
        com.stylefeng.guns.common.persistence.model.User user = userService.selectById(userId);
        if (user != null && user.getRoleId() != null){
            EntityWrapper<Role> queryWrapper = new EntityWrapper<>();
            queryWrapper.in("id", user.getRoleId().split(","));
            List<Role> roles = roleService.selectList(queryWrapper);
            for (Role role : roles){
                list.add(ActUtils.toActivitiGroup(role));
            }
        }
        return list;
	}

	@Override
    public UserQuery createNewUserQuery() {
//		return new UserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
		throw new RuntimeException("not implement method.");
	}

	@Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
//		Map<String, String> parameters = new HashMap<String, String>();
//		parameters.put("userId", userId);
//		parameters.put("key", key);
//		return (IdentityInfoEntity) getDbSqlSession().selectOne("selectIdentityInfoByUserIdAndKey", parameters);
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
//		Map<String, String> parameters = new HashMap<String, String>();
//		parameters.put("userId", userId);
//		parameters.put("type", type);
//		return (List) getDbSqlSession().getSqlSession().selectList("selectIdentityInfoKeysByUserIdAndType", parameters);
		throw new RuntimeException("not implement method.");
	}

	@Override
    public Boolean checkPassword(String userId, String password) {
//		User user = findUserById(userId);
//		if ((user != null) && (password != null) && (password.equals(user.getPassword()))) {
//			return true;
//		}
//		return false;
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
//		Map<String, String> parameters = new HashMap<String, String>();
//		parameters.put("procDefId", proceDefId);
//		return (List<User>) getDbSqlSession().selectOne("selectUserByQueryCriteria", parameters);
		throw new RuntimeException("not implement method.");

	}

	@Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
//		return getDbSqlSession().selectListWithRawParameter("selectUserByNativeQuery", parameterMap, firstResult, maxResults);
		throw new RuntimeException("not implement method.");
	}

	@Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
//		return (Long) getDbSqlSession().selectOne("selectUserCountByNativeQuery", parameterMap);
		throw new RuntimeException("not implement method.");
	}
	
}
