package com.stylefeng.guns.modular.assess.service.impl;

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
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.JshjAssess;
import com.stylefeng.guns.modular.assess.dao.JshjAssessMapper;
import com.stylefeng.guns.modular.assess.service.IJshjAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 竞赛获奖服务实现类
 *
 * @author
 * @Date 2021-02-24 13:44:31
 */
@Service
public class JshjAssessServiceImpl extends ServiceImpl<JshjAssessMapper, JshjAssess> implements IJshjAssessService {
    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importAssess(JshjAssess jshjAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + jshjAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("竞赛名称", "jsName");
        reader.addHeaderAlias("赛项名称", "sxName");
        reader.addHeaderAlias("竞赛级别", "jsLevel");
        reader.addHeaderAlias("获奖等级", "hjLevel");
        reader.addHeaderAlias("参赛/指导/管理", "jsType");
        reader.addHeaderAlias("积分归属年份", "year");
        reader.addHeaderAlias("教师工号", "account");
        reader.addHeaderAlias("竞赛类别", "type");
        List<JshjAssess> normalAssesses = reader.readAll(JshjAssess.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_JSHJ);
        for (JshjAssess assess : normalAssesses) {
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
                Double mainPoint = assessNormPoint.getJshjMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setJshjMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setJshjMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);

    }
}