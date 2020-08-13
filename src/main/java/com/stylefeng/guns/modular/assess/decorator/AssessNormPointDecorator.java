package com.stylefeng.guns.modular.assess.decorator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.sun.mail.imap.protocol.ID;

import javax.swing.*;
import java.util.List;

public class AssessNormPointDecorator extends BaseListDecorator<AssessNormPoint> {

    private IUserService userService;
    private IDeptService deptService;
    private String type;

    public AssessNormPointDecorator(List<AssessNormPoint> list,String type) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        this.type = type;
    }

    @Override
    protected void decorateTheEntity(AssessNormPoint assessNormPoint) {
        User user = userService.selectIgnorePointById(assessNormPoint.getUserId());
        if (user != null) {
            assessNormPoint.putExpand("name", user.getName());
            assessNormPoint.putExpand("account", user.getAccount());
        }
        Dept dept = deptService.selectById(assessNormPoint.getDeptId());
        if (dept != null) {
            assessNormPoint.putExpand("deptName", dept.getName());
        }

        if (StrUtil.isNotBlank(type)) {
            Object mainPoint = ReflectUtil.getFieldValue(assessNormPoint, type + "Main");
            if (mainPoint != null) {
                assessNormPoint.putExpand("main", NumberUtil.roundStr((double)mainPoint,2));
            }
            Object collegePoint = ReflectUtil.getFieldValue(assessNormPoint, type + "College");
            if (collegePoint != null) {
                assessNormPoint.putExpand("college", NumberUtil.roundStr((double)collegePoint,2));
            }
        }
    }
}
