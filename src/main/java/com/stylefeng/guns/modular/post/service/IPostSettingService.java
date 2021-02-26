package com.stylefeng.guns.modular.post.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.post.model.PostSetting;
import javafx.geometry.Pos;

import java.util.List;

/**
 * 职务设置服务类
 *
 * @author 
 * @Date 2021-02-18 15:39:51
 */
public interface IPostSettingService extends IService<PostSetting> {
    String CACHE_NAME = "post_setting";
    String CACHE_ENTITY = "post_setting_entity";

    PostSetting getByCache(PostSetting postSetting);
}