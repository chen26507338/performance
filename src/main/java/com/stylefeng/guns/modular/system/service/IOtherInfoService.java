package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.OtherInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-02
 */
public interface IOtherInfoService extends IService<OtherInfo> {
    OtherInfo getOtherInfoByKey(String key);

    void cleanCache();

    void cleanLock();
}
