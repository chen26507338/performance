package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.SpecialAssess;
import com.stylefeng.guns.modular.payment.dao.SpecialAssessMapper;
import com.stylefeng.guns.modular.payment.service.ISpecialAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 专项工作奖项目列表服务实现类
 *
 * @author
 * @Date 2021-02-25 18:10:30
 */
@Service
public class SpecialAssessServiceImpl extends ServiceImpl<SpecialAssessMapper, SpecialAssess> implements ISpecialAssessService {

    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importProject(SpecialAssess specialAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + specialAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("项目分类", "type");
        reader.addHeaderAlias("申报项目内容", "projectContent");
        reader.addHeaderAlias("编号", "zxNo");
        reader.addHeaderAlias("申报部门", "dept");
        reader.addHeaderAlias("申请参考分", "referencePoint");
        reader.addHeaderAlias("增加积分", "addPoint");
        reader.addHeaderAlias("申请积分", "applyPoint");
        reader.addHeaderAlias("计入与否", "isJr");
        reader.addHeaderAlias("是否记入部门优绩考核", "isYjkh");
        List<Map> normalAssesses = reader.readAll(Map.class);
        List<SpecialAssess> assesses = new ArrayList<>();
//        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_GLFW);
        for (Map assess : normalAssesses) {
            assess.put("isJr",assess.get("isJr").equals("是") ? YesNo.YES.getCode() : YesNo.NO.getCode());
            assess.put("isYjkh",assess.get("isYjkh").equals("是") ? YesNo.YES.getCode() : YesNo.NO.getCode());
            SpecialAssess sAssess = BeanUtil.mapToBean(assess, SpecialAssess.class, true);
            Dept dept = deptService.getByName(assess.get("dept") + "");
            sAssess.setDeptId(dept.getId());
            if (dept == null) {
                throw new GunsException("部门不存在");
            }
            assesses.add(sAssess);
        }
        this.insertBatch(assesses);
    }
}