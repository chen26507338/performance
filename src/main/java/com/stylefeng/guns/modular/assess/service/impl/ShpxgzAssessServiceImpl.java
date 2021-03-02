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
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.DzbWorkAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.ShpxgzAssess;
import com.stylefeng.guns.modular.assess.dao.ShpxgzAssessMapper;
import com.stylefeng.guns.modular.assess.service.IShpxgzAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 社会培训工作考核服务实现类
 *
 * @author
 * @Date 2021-03-01 23:40:34
 */
@Service
public class ShpxgzAssessServiceImpl extends ServiceImpl<ShpxgzAssessMapper, ShpxgzAssess> implements IShpxgzAssessService {

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
    public void importAssess(ShpxgzAssess shpxgzAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + shpxgzAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainNormPoint");
        reader.addHeaderAlias("名称", "name");
        reader.addHeaderAlias("数量", "num");
        reader.addHeaderAlias("教师工号", "account");
        reader.addHeaderAlias("积分归属年份", "year");
        List<ShpxgzAssess> normalAssesses = reader.readAll(ShpxgzAssess.class);
        AssessCoefficient coefficient = assessCoefficientService.selectById(IAssessCoefficientService.TYPE_SHPXGZ);
        for (ShpxgzAssess assess : normalAssesses) {
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
                Double mainPoint = assessNormPoint.getShpxgzMain();
                mainPoint += assess.getMainNormPoint();
                assessNormPoint.setShpxgzMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainNormPoint();
                assessNormPoint.setShpxgzMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }
}