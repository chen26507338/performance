package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.StuWorkMemberMapper;
import com.stylefeng.guns.modular.assess.service.IStuWorkMemberService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 学生工作成员服务实现类
 *
 * @author cp
 * @Date 2020-09-16 15:26:17
 */
@Service
public class StuWorkMemberServiceImpl extends ServiceImpl<StuWorkMemberMapper, StuWorkMember> implements IStuWorkMemberService {


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
    public boolean updateById(StuWorkMember entity) {
        StuWorkMember oldAssess = this.selectById(entity.getId());
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setXsgzMain(point.getXsgzMain() - oldAssess.getMainNormPoint());
        point.setXsgzMain(point.getXsgzMain() + entity.getMainNormPoint());
        point.updateById();
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        StuWorkMember oldAssess = this.selectById(id);
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setXsgzMain(point.getXsgzMain() - oldAssess.getMainNormPoint());
        point.updateById();
        return super.deleteById(id);
    }
    
    @Override
    @Transactional
    public void importAssess(StuWorkMember stuWorkMember) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + stuWorkMember.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("人数/次数", "result");
        reader.addHeaderAlias("参赛项目名称/团队名称/类别/百分率", "mixture");
        reader.addHeaderAlias("教师工号", "account");
        reader.addHeaderAlias("积分归属年份", "year");
        List<StuWorkMember> normalAssesses = reader.readAll(StuWorkMember.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_XSGZ);
        for (StuWorkMember assess : normalAssesses) {
            if (assess.getMainNormPoint() == null) {
                throw new GunsException(StrUtil.format("职工编号{} 考核项目{}校级积分不能为空"
                        , assess.getAccount(), assess.getAssessName()));
            }
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }
            assess.setUserId(user.getId());
            assess.setStatus(YesNo.YES.getCode());
            assess.setCoePoint(coefficient.getCoefficient());

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getXsgzMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setXsgzMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setXsgzMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }
}