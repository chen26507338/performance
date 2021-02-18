package com.stylefeng.guns.modular.post.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.post.model.PostSetting;

import java.util.List;

public class PostSettingDecorator extends BaseListDecorator<PostSetting> {

    public PostSettingDecorator(List<PostSetting> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(PostSetting postSetting) {
    }
}
