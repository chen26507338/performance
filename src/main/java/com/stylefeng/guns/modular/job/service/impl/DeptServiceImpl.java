package com.stylefeng.guns.modular.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.state.YesNo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.dao.DeptMapper;
import com.stylefeng.guns.modular.job.service.IDeptService;

import java.io.Serializable;
import java.util.List;

/**
 * 部门管理服务实现类
 *
 * @author cp
 * @Date 2020-01-17 09:34:46
 */
 @Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {



    @Override
    @Cacheable(value = Cache.DEPT,key = "'"+CACHE_ENTITY+"'+#id")
    public Dept selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @CacheEvict(value = Cache.DEPT,allEntries = true)
    public boolean insert(Dept entity) {
        return super.insert(entity);
    }

    @Override
    @Cacheable(value = Cache.DEPT,key = "'"+CACHE_LIST+"'")
    public List<Dept> selectAllOn() {
        Dept param = new Dept();
        param.setStatus(YesNo.YES.getCode());
        return this.selectList(new EntityWrapper<>(param));
    }

    @Override
    @Cacheable(value = Cache.DEPT,key = "'"+CACHE_ENTITY+"'+#name")
    public Dept getByName(String name) {
        Dept params = new Dept();
        params.setName(name);
        return this.selectOne(new EntityWrapper<>(params));
    }

    @Override
    @CacheEvict(value = Cache.DEPT,allEntries = true)
    public boolean updateById(Dept entity) {
        return super.updateById(entity);
    }
}