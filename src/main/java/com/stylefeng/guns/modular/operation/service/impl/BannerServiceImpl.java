package com.stylefeng.guns.modular.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.operation.decorator.BannerDecorator;
import com.stylefeng.guns.modular.operation.model.Banner;
import com.stylefeng.guns.modular.operation.dao.BannerMapper;
import com.stylefeng.guns.modular.operation.service.IBannerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-01
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    @Cacheable(value = CACHE_NAME, key = "'" + LIST + "'+#place")
    public List<Banner> selectByPlace(Integer place) {
        EntityWrapper<Banner> bannerEntityWrapper = new EntityWrapper<>();
        Banner banner = new Banner();
        banner.setBannerPlace(place);
        banner.setStatus(IBannerService.BANNER_STATUS_NORMAL);
        bannerEntityWrapper.setEntity(banner);
        bannerEntityWrapper.orderBy("createTime");
        return new BannerDecorator(this.selectList(bannerEntityWrapper)).decorate();
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'" + ENTITY + "'+#id")
    public Banner selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @CacheEvict(value = CACHE_NAME,allEntries = true)
    public boolean updateById(Banner entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(value = CACHE_NAME,allEntries = true)
    public boolean insert(Banner entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(value = CACHE_NAME,allEntries = true)
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }
}
