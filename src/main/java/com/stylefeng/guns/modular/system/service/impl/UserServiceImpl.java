package com.stylefeng.guns.modular.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.Base64;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.cache.CacheKey;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.common.persistence.model.WechatAccessToken;
import com.stylefeng.guns.common.persistence.model.WechatReqParams;
import com.stylefeng.guns.common.persistence.model.WechatUserInfo;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.Token;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.*;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Cacheable(value = Cache.USER_IGNORE_POINT, key = "''+#id")
    public User selectIgnorePointById(Serializable id) {
        return selectById(id);
    }

    @Override
    @Cacheable(value = Cache.USER_IGNORE_POINT, key = "'" + CACHE_ACCOUNT + "'+#account")
    public User getByAccount(String account) {
        User params = new User();
        params.setAccount(account);
        return this.selectOne(new EntityWrapper<>(params));
    }

    @Override
    public void checkUser(User user,User check) {
        if (user == null) {
            throw new GunsException("账号或密码错误");
        }

        if (StrUtil.isBlank(check.getPassword())) {
            throw new GunsException("账号或密码错误");
        }

        String checkPwd = ShiroKit.md5(check.getPassword(), user.getSalt());
        if (!checkPwd.equals(user.getPassword())) {
            throw new GunsException("账号或密码错误");
        }
    }

    @Override
    @CachePut(value = Cache.USER_IGNORE_POINT, key = "''+#user.id")
    public User setRole(User user, Serializable roleId) {
        String roleIds = StringUtils.isNotBlank(user.getRoleId()) ? "," + user.getRoleId() + "," : ",";
        if (!roleIds.contains("," + roleId + ",")) {
            user.setRoleId(roleIds.substring(1) + roleId);
            user.updateById();
        } else {
            throw new GunsException("权限已存在");
        }
        return user;
    }

    @Override
    @CachePut(value = Cache.USER_IGNORE_POINT, key = "''+#user.id")
    public User delRole(User user, Serializable roleId) {
        String roleIds = StringUtils.isNotBlank(user.getRoleId()) ? "," + user.getRoleId() + "," : ",";
        if (roleIds.contains("," + String.valueOf(roleId) + ",")) {
            String temp = roleIds.replace("," + String.valueOf(roleId) + ",", "");

            //去掉收尾逗号
            if (temp.indexOf(",") == 0) {
                temp = temp.substring(1);
            }
            if (temp.length() > 0 && temp.lastIndexOf(",") == temp.length() - 1) {
                temp = temp.substring(temp.length() - 1);
            }

            user.setRoleId(temp);
            user.updateById();
        } else {
            throw new GunsException("权限不存在");
        }
        return user;
    }

    @Override
    @CachePut(value = Cache.USER_IGNORE_POINT, key = "''+#user.id")
    public User updateUser(User user) {
        if (!user.updateById()) {
            throw new GunsException("用户更新异常");
        }
        user.setVersion(user.getVersion() + 1);
        return user;
    }

    @Override
    @CacheEvict(value = Cache.USER_IGNORE_POINT, key = "''+#entity.id")
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }
}
