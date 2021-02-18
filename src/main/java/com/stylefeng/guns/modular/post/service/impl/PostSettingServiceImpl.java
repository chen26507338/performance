package com.stylefeng.guns.modular.post.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.post.model.PostSetting;
import com.stylefeng.guns.modular.post.dao.PostSettingMapper;
import com.stylefeng.guns.modular.post.service.IPostSettingService;

/**
 * 职务设置服务实现类
 *
 * @author
 * @Date 2021-02-18 15:39:51
 */
@Service
public class PostSettingServiceImpl extends ServiceImpl<PostSettingMapper, PostSetting> implements IPostSettingService {

    @Override
    @Cacheable(value = CACHE_NAME,key = "'"+CACHE_ENTITY+"'+#postSetting.ldks+#postSetting.zj+#postSetting.zw")
    public PostSetting getByCache(PostSetting postSetting) {
        return this.selectOne(new EntityWrapper<>(postSetting));
    }

    @Override
    @CacheEvict(value = CACHE_NAME,key = "'"+CACHE_ENTITY+"'+#entity.ldks+#entity.zj+#entity.zw")
    public boolean insert(PostSetting entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public boolean updateById(PostSetting entity) {
        return super.updateById(entity);
    }
}