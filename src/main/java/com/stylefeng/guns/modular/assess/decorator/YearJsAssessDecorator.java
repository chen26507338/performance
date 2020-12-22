package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.sun.org.apache.bcel.internal.generic.IUSHR;

import java.util.List;

public class YearJsAssessDecorator extends BaseListDecorator<YearJsAssess> {

    private IUserService userService;

    public YearJsAssessDecorator(List<YearJsAssess> list) {
        super(list);
        userService = SpringContextHolder.getBean(IUserService.class);
    }

    @Override
    protected void decorateTheEntity(YearJsAssess yearJsAssess) {
        yearJsAssess.setKygz(
                yearJsAssess.getKygz().replaceFirst(",","课题名称、级别：")
                .replaceFirst(",","<br>本人承担任务：")
                .replaceFirst(",","<br>完成任务情况及成果：")
        );
        yearJsAssess.setSysgz(
                yearJsAssess.getSysgz().replaceFirst(",","工作内容：")
                .replaceFirst(",","<br>本人承担任务：")
                .replaceFirst(",","<br>完成任务情况及成果：")
        );
        yearJsAssess.putExpand("statusDict", ConstantFactory.me().getDictsByName("年度考核状态", yearJsAssess.getStatus()));
        yearJsAssess.putExpand("user", userService.selectIgnorePointById(yearJsAssess.getUserId()));
    }
}
