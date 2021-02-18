package com.stylefeng.guns.modular.pay.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.pay.model.PaySetting;
import com.stylefeng.guns.modular.pay.dao.PaySettingMapper;
import com.stylefeng.guns.modular.pay.service.IPaySettingService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 薪酬设置服务实现类
 *
 * @author
 * @Date 2021-02-14 11:12:00
 */
@Service
public class PaySettingServiceImpl extends ServiceImpl<PaySettingMapper, PaySetting> implements IPaySettingService {
    @Resource
    private GunsProperties gunsProperties;

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
            User u = BeanUtil.mapToBean(map, User.class,true);
//            u.setJobId(job.getId());
//            u.setDeptId(dept.getId());
            u.setBirthday(DateUtils.parseDate(map.get("birthday")));
            u.setCreateTime(new Date());
            u.setSalt(ShiroKit.getRandomSalt(5));
            u.setPassword(ShiroKit.md5("123456", u.getSalt()));
            u.setRoleId(IRoleService.TYPE_TEACHER + "");

        }
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'" + CACHE_ENTITY + "'+#name")
    public PaySetting getByName(String name) {
        PaySetting params = new PaySetting();
        params.setName(name);
        return this.selectOne(new EntityWrapper<>(params));
    }


    @Override
    @CacheEvict(value = CACHE_NAME, key = "'" + CACHE_ENTITY + "'+#entity.name")
    public boolean insert(PaySetting entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public boolean updateById(PaySetting entity) {
        return super.updateById(entity);
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'" + CACHE_ENTITY + "'+#id")
    public PaySetting selectById(Serializable id) {
        return super.selectById(id);
    }
}