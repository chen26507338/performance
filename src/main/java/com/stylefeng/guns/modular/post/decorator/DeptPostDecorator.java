package com.stylefeng.guns.modular.post.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.post.model.DeptPost;
import com.stylefeng.guns.modular.post.service.IDeptPostService;
import com.stylefeng.guns.modular.post.service.IPostSettingService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificProject;

import java.util.List;

public class DeptPostDecorator extends BaseListDecorator<DeptPost> {

    private final IUserService userService;
    private final IPostSettingService postSettingService;
    private final IDeptService deptService;

    public DeptPostDecorator(List<DeptPost> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        postSettingService = SpringContextHolder.getBean(IPostSettingService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
    }

    @Override
    protected void decorateTheEntity(DeptPost deptPost) {
        deptPost.putExpand("isDbDict", ConstantFactory.me().getDictsByName("是否",deptPost.getIsDb()));
        deptPost.putExpand("isStarDict", ConstantFactory.me().getDictsByName("是否",deptPost.getIsStar()));
        deptPost.putExpand("user", userService.selectIgnorePointById(deptPost.getUserId()));
        deptPost.putExpand("postS", postSettingService.selectById(deptPost.getPostId()));
        deptPost.putExpand("dept", deptService.selectById(deptPost.getDeptId()));
    }
}
