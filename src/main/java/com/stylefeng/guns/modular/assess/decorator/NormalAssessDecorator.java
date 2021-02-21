package com.stylefeng.guns.modular.assess.decorator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;

import java.util.List;

public class NormalAssessDecorator extends BaseListDecorator<NormalAssess> {

    private IUserService userService;
    private IDeptService deptService;
    private IAssessNormService assessNormService;

    public NormalAssessDecorator(List<NormalAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(NormalAssess normalAssess) {
        normalAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("考核状态",normalAssess.getStatus()));
        User user = userService.selectById(normalAssess.getUserId());
        if (user != null) {
            normalAssess.putExpand("userName", user.getName());
            normalAssess.putExpand("account", user.getAccount());
        }
        Dept dept = deptService.selectById(normalAssess.getDeptId());
        if (dept != null) {
            normalAssess.putExpand("deptName", dept.getName());
        }
        AssessNorm assessNorm = assessNormService.selectById(normalAssess.getNormId());
        if (assessNorm != null) {
            normalAssess.putExpand("normCode", assessNorm.getCode());
            normalAssess.putExpand("normName", assessNorm.getContent());
        }else {
            normalAssess.putExpand("normName", normalAssess.getAssessName());
        }

        if (normalAssess.getResult() != null) {
            double mainPoint = normalAssess.getMainNormPoint() * normalAssess.getResult() * normalAssess.getCoePoint();
            normalAssess.setMainPoint(mainPoint);
            normalAssess.putExpand("collegePoint", NumberUtil.roundStr(mainPoint * (1 + normalAssess.getCollegeNormPoint()), 2));
        }

    }
}
