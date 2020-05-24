package com.stylefeng.guns.modular.assess.decorator;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.BaseListDecorator;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.sun.mail.imap.protocol.ID;

import java.util.List;

public class AssessNormDecorator extends BaseListDecorator<AssessNorm> {

    private IDeptService deptService;
    private IAssessNormService assessNormService;

    public AssessNormDecorator(List<AssessNorm> list) {
        super(list);
        deptService = SpringContextHolder.getBean(IDeptService.class);
        assessNormService = SpringContextHolder.getBean(IAssessNormService.class);
    }

    @Override
    protected void decorateTheEntity(AssessNorm assessNorm) {
        assessNorm.putExpand("typeDict", ConstantFactory.me().getDictsByName("考核项目",assessNorm.getType()));
        Dept dept = deptService.selectById(assessNorm.getDeptId());
        if (dept == null) {
            assessNorm.putExpand("deptDict", "校级");
            assessNorm.putExpand("mainPoint", assessNorm.getPoint());
            assessNorm.setPoint(null);
        } else {
            assessNorm.putExpand("deptDict",  dept.getName());
            AssessNorm param = new AssessNorm();
            param.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
            param.setCode(assessNorm.getCode());
            param.setType(assessNorm.getType());
            AssessNorm mainNorm = assessNormService.getByCode(param);
            if (mainNorm != null) {
                assessNorm.putExpand("mainPoint", mainNorm.getPoint());
            }
        }
    }
}
