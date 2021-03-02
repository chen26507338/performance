package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.ShpxgzAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.JfwcqkAssess;
import com.stylefeng.guns.modular.assess.dao.JfwcqkAssessMapper;
import com.stylefeng.guns.modular.assess.service.IJfwcqkAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 经费完成情况考核服务实现类
 *
 * @author
 * @Date 2021-03-02 15:27:56
 */
@Service
public class JfwcqkAssessServiceImpl extends ServiceImpl<JfwcqkAssessMapper, JfwcqkAssess> implements IJfwcqkAssessService {


    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormService assessNormService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;

    @Override
    @Transactional
    public void importAssess(JfwcqkAssess jfwcqkAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + jfwcqkAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("经费项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("经费完成费", "jfwcf");
        reader.addHeaderAlias("教师工号", "account");
        reader.addHeaderAlias("积分归属年份", "year");
        List<JfwcqkAssess> normalAssesses = reader.readAll(JfwcqkAssess.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_JFWCQK);
        for (JfwcqkAssess assess : normalAssesses) {
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }
            assess.setUserId(user.getId());
//            assess.setStatus(YesNo.YES.getCode());
            assess.setCoePoint(coefficient.getCoefficient());

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getJfwcqkMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setJfwcqkMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setJfwcqkMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }
}