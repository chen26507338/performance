package com.stylefeng.guns.modular.operation.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.operation.model.Banner;

import java.util.List;

public class BannerDecorator extends BaseListDecorator<Banner> {


    public BannerDecorator(List<Banner> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(Banner banner) {
        banner.setBannerImgurl(KaptchaUtil.formatFileUrl(banner.getBannerImgurl()));
        banner.setBannerImgurlEN(KaptchaUtil.formatFileUrl(banner.getBannerImgurlEN()));
        banner.putExpand("statusDict", ConstantFactory.me().getDictsByName("广告状态", banner.getStatus()));
        banner.putExpand("bannerPlaceDict", ConstantFactory.me().getDictsByName("广告资讯类型", banner.getBannerPlace()));
    }
}
