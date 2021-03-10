package com.stylefeng.guns.modular.operation.service;

import com.stylefeng.guns.modular.operation.model.Banner;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-01
 */
public interface IBannerService extends IService<Banner> {

    String CACHE_NAME = "banner_cache";
    String LIST = "list_";
    String ENTITY = "entity_";

    /**
     * 广告状态：正常
     */
    int BANNER_STATUS_NORMAL = 1;

    List<Banner> selectByPlace(Integer place);
}
