package com.stylefeng.guns.modular.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.modular.job.model.Job;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.dao.JobMapper;
import com.stylefeng.guns.modular.job.service.IJobService;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位管理服务实现类
 *
 * @author 
 * @Date 2020-01-17 09:37:54
 */
 @Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    @Override
    @Cacheable(value = Cache.JOB,key = "'"+CACHE_ENTITY+"'+#id")
    public Job selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @CacheEvict(value = Cache.JOB,allEntries = true)
    public boolean insert(Job entity) {
        return super.insert(entity);
    }

    @Override
    @Cacheable(value = Cache.JOB,key = "'"+CACHE_LIST+"'")
    public List<Job> selectAllOn() {
        Job param = new Job();
        param.setStatus(YesNo.YES.getCode());
        return this.selectList(new EntityWrapper<>(param));
    }

    @Override
    @Cacheable(value = Cache.JOB,key = "'"+CACHE_ENTITY+"'+#job.deptId+#job.name")
    public Job getDeptName(Job job) {
        return this.selectOne(new EntityWrapper<>(job));
    }

    @Override
    @CacheEvict(value = Cache.JOB,allEntries = true)
    public boolean updateById(Job entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(value = Cache.JOB,allEntries = true)
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }
}