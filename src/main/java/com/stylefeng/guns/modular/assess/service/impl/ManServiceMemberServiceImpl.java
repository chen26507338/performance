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
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.assess.dao.ManServiceMemberMapper;
import com.stylefeng.guns.modular.assess.service.IManServiceMemberService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理服务成员服务实现类
 *
 * @author
 * @Date 2020-09-17 18:39:27
 */
@Service
public class ManServiceMemberServiceImpl extends ServiceImpl<ManServiceMemberMapper, ManServiceMember> implements IManServiceMemberService {

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
    public void importAssess(ManServiceMember manServiceMember) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + manServiceMember.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("项目名称", "projectName");
        reader.addHeaderAlias("积分归属年份", "year");
        reader.addHeaderAlias("教师工号", "account");
        List<ManServiceMember> normalAssesses = reader.readAll(ManServiceMember.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_GLFW);
        for (ManServiceMember assess : normalAssesses) {
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }
            assess.setUserId(user.getId());
            assess.setStatus(YesNo.YES.getCode());
            assess.setCoePoint(coefficient.getCoefficient());

            ManServiceMember param = new ManServiceMember();
            param.setUserId(user.getId());
            param.setProjectName(assess.getProjectName());
            param.setAssessName(assess.getAssessName());
            if (this.selectCount(new EntityWrapper<>(param)) > 0) {
                throw new GunsException(StrUtil.format("职工编号：{},项目名称：{} 已存在",assess.getAccount(),
                        assess.getProjectName()));
            }

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getGlfwMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setGlfwMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setGlfwMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);

    }
}