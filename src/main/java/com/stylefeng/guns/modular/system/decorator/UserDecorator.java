package com.stylefeng.guns.modular.system.decorator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.system.service.IRoleService;

import java.util.ArrayList;
import java.util.List;

public class UserDecorator extends BaseListDecorator<User> {
    private IDeptService deptService;
    private IJobService jobService;
    private IRoleService roleService;

    public UserDecorator(List<User> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        jobService = SpringContextHolder.getBean(IJobService.class);
        roleService = SpringContextHolder.getBean(IRoleService.class);
    }

    @Override
    protected void decorateTheEntity(User user) {
        Dept dept = deptService.selectById(user.getDeptId());
        if (dept != null) {
            user.putExpand("deptName", dept.getName());
        }
        Job job = jobService.selectById(user.getJobId());
        if (job != null) {
            user.putExpand("jobName", job.getName());
        }
        user.putExpand("statusName", ConstantFactory.me().getDictsByName("账号状态",user.getStatus()));
        user.putExpand("sexName", ConstantFactory.me().getDictsByName("性别",user.getSex()));
        user.setAvatar(KaptchaUtil.formatFileUrl(user.getAvatar()));
        if (StrUtil.isNotBlank(user.getRoleId())) {
            String[] roleIds = user.getRoleId().split(",");
            List<String> roleNames = new ArrayList<>();

            for (String roleId : roleIds) {
                Role role = roleService.selectById(roleId);
                if (role != null) {
                    roleNames.add(role.getName());
                }
            }
            user.putExpand("userType", CollUtil.isEmpty(roleNames)?"会员": CollUtil.join(roleNames.iterator(), ","));
        }
    }
}
