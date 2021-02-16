package com.stylefeng.guns.modular.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.druid.util.Base64;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.cache.CacheKey;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
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
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.pay.model.PaySetting;
import com.stylefeng.guns.modular.pay.service.IPaySettingService;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
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

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IJobService jobService;
    @Autowired
    private IPaySettingService paySettingService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Cacheable(value = Cache.USER_IGNORE_POINT, key = "''+#id")
    public User selectIgnorePointById(Serializable id) {
        return selectById(id);
    }

    @Override
    public List<User> selectByDeptWithOutUser(User user) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("dept_id", user.getDeptId());
        wrapper.ne("id", user.getId());
        return this.selectList(wrapper);
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
    @Transactional
    public void importUser(User user) {
        if (user.getExpand().get("fileName") == null) {
            throw new GunsException("请上传导入文件");
        }

        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + user.getExpand().get("fileName"));
        reader.addHeaderAlias("姓名", "name");
        reader.addHeaderAlias("职工号", "account");
        reader.addHeaderAlias("所在部门", "dept");
        reader.addHeaderAlias("类别", "type");
        reader.addHeaderAlias("岗位", "job");
        reader.addHeaderAlias("性别", "sex");
        reader.addHeaderAlias("出生年月", "birthday");
        reader.addHeaderAlias("年龄", "age");
        reader.addHeaderAlias("最高学历", "college");
        reader.addHeaderAlias("行政职务", "post");
        reader.addHeaderAlias("学位", "degree");
        reader.addHeaderAlias("职级", "zhiJi");
        reader.addHeaderAlias("职称（聘任职称）", "zhiChen");
        reader.addHeaderAlias("职称等级", "zcLevel");
        reader.addHeaderAlias("专业岗位", "zyJob");
        reader.addHeaderAlias("毕业院校", "college");
        reader.addHeaderAlias("专业", "major");
        reader.addHeaderAlias("身份证号码", "idCard");
        reader.addHeaderAlias("籍贯", "nativePlace");
        reader.addHeaderAlias("民族", "nation");
        reader.addHeaderAlias("聘期起始时间", "pqqs");
        reader.addHeaderAlias("到校工作时间", "dxSj");
        reader.addHeaderAlias("工龄起算时间", "glSj");
        reader.addHeaderAlias("个人身份", "grsf");
        reader.addHeaderAlias("岗位类别", "gwType");
        reader.addHeaderAlias("工资职级专业岗位", "gzzygw");
        reader.addHeaderAlias("参保时间", "cbSj");
        reader.addHeaderAlias("工资职级", "gzzj");
        reader.addHeaderAlias("实际岗位描述", "sjgwms");
        List<Map> users = reader.readAll(Map.class);
        for (Map map : users) {
            String deptName = (String) map.get("dept");
            Dept dept = deptService.getByName(deptName);
            if (dept == null) {
                dept = new Dept();
                dept.setName(deptName);
                deptService.insert(dept);
            }
            user.setDeptId(dept.getId());
            String jobName = (String) map.get("job");
            Job params = new Job();
            params.setDeptId(dept.getId());
            params.setName(jobName);
            Job job = jobService.getDeptName(params);
            if (job == null) {
                job = new Job();
                job.setName(jobName);
                job.setDeptId(dept.getId());
                job.setDes((String) map.get("sjgwms"));
                jobService.insert(job);
            }
            map.put("sex",Integer.parseInt(ConstantFactory.me().getDictValueByName("性别", String.valueOf(map.get("sex")))));
            map.put("jobType",Integer.parseInt(ConstantFactory.me().getDictValueByName("用工类型", String.valueOf(map.get("type")))));
            User u = BeanUtil.mapToBean(map, User.class,true);
            u.setJobId(job.getId());
            u.setDeptId(dept.getId());
            u.setBirthday(DateUtils.parseDate(map.get("birthday")));
            u.setCreateTime(new Date());
            u.setSalt(ShiroKit.getRandomSalt(5));
            u.setPassword(ShiroKit.md5("123456", u.getSalt()));
            u.setRoleId(IRoleService.TYPE_TEACHER + "");

            try {
                this.insert(u);
            } catch (DuplicateKeyException e) {
                throw new GunsException("工号 " + u.getAccount() + " 已存在");
            } catch (Exception e) {
                logger.error("", e);
                throw new GunsException("工号 " + u.getAccount() + " 导入异常");
            }
        }
    }

    @Override
    public User fuzzyFind(String userInfo) {
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper
                .eq("account", userInfo).or()
//                .eq("id", userInfo).or()
                .eq("name", userInfo);
        userEntityWrapper.last("limit 1");
        return this.selectOne(userEntityWrapper);
    }

    @Override
    @Transactional
    public void importSetting(PaySetting paySetting) {
        if (paySetting.getExpand().get("fileName") == null) {
            throw new GunsException("请上传导入文件");
        }

        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + paySetting.getExpand().get("fileName"));
        reader.addHeaderAlias("岗位责任奖参考标准", "name");
        reader.addHeaderAlias("代码", "account");
        reader.addHeaderAlias("原标准值（180元）", "money");
        List<Map> users = reader.readAll(Map.class);
        for (Map map : users) {
            User u = this.getByAccount(map.get("account") + "");
            if (u == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", (String) map.get("account")));
            }
            PaySetting setting = paySettingService.getByName((String) map.get("name"));
            if (setting == null) {
                setting = new PaySetting();
                setting.setName((String) map.get("name"));
                setting.setMoney(Double.parseDouble(map.get("money") + ""));
                paySettingService.insert(setting);
            }
            u.setPaysId(setting.getId());
            u.updateById();
        }
    }

    @Override
    @CacheEvict(value = Cache.USER_IGNORE_POINT, key = "''+#entity.id")
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }
}
