package com.stylefeng.guns.modular.assess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.dao.AssessNormMapper;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;

import java.io.Serializable;

/**
 *  考核指标库服务实现类
 *
 * @author 
 * @Date 2020-02-02 13:38:54
 */
 @Service
public class AssessNormServiceImpl extends ServiceImpl<AssessNormMapper, AssessNorm> implements IAssessNormService {

    @Override
    @Cacheable(value = Cache.NORMAL_CONFIG,key = "'"+Cache.NORMAL_CONFIG+"'+#assessNorm.type+#assessNorm.code+#assessNorm.deptId")
    public AssessNorm getByCode(AssessNorm assessNorm) {
        EntityWrapper<AssessNorm> wrapper = new EntityWrapper<>(assessNorm);
        return selectOne(wrapper);
    }

    @Override
    @Cacheable(value = Cache.NORMAL_CONFIG,key = "'"+Cache.NORMAL_CONFIG+"'+#id")
    public AssessNorm selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @CacheEvict(value = Cache.NORMAL_CONFIG, allEntries = true)
    public boolean updateById(AssessNorm entity) {
        return super.updateById(entity);
    }
}