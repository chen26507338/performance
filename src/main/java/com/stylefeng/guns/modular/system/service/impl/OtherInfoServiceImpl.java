package com.stylefeng.guns.modular.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.cache.CacheKey;
import com.stylefeng.guns.modular.system.dao.OtherInfoMapper;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-02
 */
@Service
public class OtherInfoServiceImpl extends ServiceImpl<OtherInfoMapper, OtherInfo> implements IOtherInfoService {

    private final Logger logger = LoggerFactory.getLogger(com.stylefeng.guns.modular.system.service.impl.OtherInfoServiceImpl.class);

    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.OTHER_INFO + "'+#key")
    public OtherInfo getOtherInfoByKey(String key) {
        EntityWrapper<OtherInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("other_key", key);
        return this.selectOne(entityWrapper);
    }

    @Override
    @CacheEvict(value = Cache.CONSTANT,key = "'" + CacheKey.OTHER_INFO + "'+#entity.otherKey")
    public boolean updateById(OtherInfo entity) {
        if (StrUtil.isBlank(entity.getSorts())) {
            entity.setSorts(null);
        }
        return super.updateAllColumnById(entity);
    }

    @Override
    @CacheEvict(value = Cache.CONSTANT,key = "'" + CacheKey.OTHER_INFO + "'+#entity.otherKey")
    public boolean insert(OtherInfo entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(value = Cache.CONSTANT,allEntries = true)
    public void cleanCache() {
        logger.info("清空缓存");
    }

    @Override
    @CacheEvict(value = Cache.USER_CHOOSE_LOCK_CACHE,allEntries = true)
    public void cleanLock() {
        logger.info("清空用户锁");
    }
}
