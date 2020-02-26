package com.stylefeng.guns.modular.assess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.dao.AssessCoefficientMapper;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;

import java.io.Serializable;
import java.util.List;

/**
 * 考核系数服务实现类
 *
 * @author 
 * @Date 2020-02-25 10:45:56
 */
 @Service
public class AssessCoefficientServiceImpl extends ServiceImpl<AssessCoefficientMapper, AssessCoefficient> implements IAssessCoefficientService {

    @Override
    @Cacheable(value = Cache.NORMAL_CONFIG, key = "'" + CACHE_LIST + "'")
    public List<AssessCoefficient> selectAll() {
        return this.selectList(new EntityWrapper<>());
    }

    @Override
    @Cacheable(value = Cache.NORMAL_CONFIG, key = "'" + CACHE_ENTITY + "'+#id")
    public AssessCoefficient selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @CacheEvict(value = Cache.NORMAL_CONFIG, allEntries = true)
    public boolean insert(AssessCoefficient entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(value = Cache.NORMAL_CONFIG, allEntries = true)
    public boolean updateById(AssessCoefficient entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(value = Cache.NORMAL_CONFIG, allEntries = true)
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }
}