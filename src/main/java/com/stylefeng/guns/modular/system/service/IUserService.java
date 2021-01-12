package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.Token;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public interface IUserService extends IService<User>{

    String CACHE_ACCOUNT = "user_account_";

    /**
     * 用户状态:正常
     */
    int STATUS_NORMAL = 1;

    /**
     * 用户状态:拉黑
     */
    int STATUS_DISABLE = 0;

    /**
     * 获取用户信息不计余额
     * @param id
     * @return
     */
    User selectIgnorePointById(Serializable id);

    /**
     * 搜索部门下用户，排除某个用户
     * @param user
     * @return
     */
    List<User> selectByDeptWithOutUser(User user);

    /**
     * 通过账号获取用户
     * @param account
     * @return
     */
    User getByAccount(String account);

    void checkUser(User user,User check);

    /**
     * 设置用户角色
     * @param user
     * @param roleId
     * @return
     */
    User setRole(User user, Serializable roleId);

    /**
     * 删除用户角色
     * @param user
     * @param roleId
     * @return
     */
    User delRole(User user, Serializable roleId);

    User updateUser(User user);

    /**
     * 导入用户
     * @param user
     */
    void importUser(User user);

    /**
     *
     * @return
     */
    User fuzzyFind(String userInfo);

}
