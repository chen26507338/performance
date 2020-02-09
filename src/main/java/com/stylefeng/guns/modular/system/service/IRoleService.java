package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Role;

/**
 * 角色相关业务
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:11:57
 */
public interface IRoleService extends IService<Role> {

    String CACHE_ENTITY = "role_entity_";

    String CACHE_LIST = "role_list_";
    /**
     * 角色类型：校级指标编辑者
     */
    long TYPE_MAIN_ASSESS_HANDLE = 10;

    /**
     * 角色类型：部门领导
     */
    long TYPE_DEPT_LEADER = 12;

    /**
     * 角色类型：人事经办
     */
    long TYPE_HR_HANDLER = 13;

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param ids    权限的id
     * @date 2017年2月13日 下午8:26:53
     */
    void setAuthority(Long roleId, String ids);

    /**
     * 删除角色
     *
     * @author stylefeng
     * @Date 2017/5/5 22:24
     */
    void delRoleById(Long roleId);


}
