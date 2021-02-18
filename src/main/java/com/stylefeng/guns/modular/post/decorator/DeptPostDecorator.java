package com.stylefeng.guns.modular.post.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.modular.post.model.DeptPost;

import java.util.List;

public class DeptPostDecorator extends BaseListDecorator<DeptPost> {

    public DeptPostDecorator(List<DeptPost> list) {
        super(list);
    }

    @Override
    protected void decorateTheEntity(DeptPost deptPost) {
        deptPost.putExpand("isDbDict", ConstantFactory.me().getDictsByName("是否",deptPost.getIsDb()));
        deptPost.putExpand("isStarDict", ConstantFactory.me().getDictsByName("是否",deptPost.getIsStar()));
    }
}
