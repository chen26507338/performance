package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.assess.dao.RypzAssessMapper;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IRypzAssessService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员配置考核服务实现类
 *
 * @author
 * @Date 2021-02-21 17:45:46
 */
@Service
public class RypzAssessServiceImpl extends ServiceImpl<RypzAssessMapper, RypzAssess> implements IRypzAssessService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAssessNormPointService assessNormPointService;


    @Override
    @Transactional
    public boolean updateById(RypzAssess entity) {
        RypzAssess oldAssess = this.selectById(entity.getId());
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setRypzMain(point.getRypzMain() - oldAssess.getMainPoint());
        point.setRypzMain(point.getRypzMain() + entity.getMainPoint());
        point.updateById();
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        RypzAssess oldAssess = this.selectById(id);
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setRypzMain(point.getRypzMain() - oldAssess.getMainPoint());
        point.updateById();
        return super.deleteById(id);
    }
    
    @Override
    @Transactional
    public void importAssess(RypzAssess rypzAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + rypzAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainPoint");
        reader.addHeaderAlias("积分归属年份", "year");
        reader.addHeaderAlias("教师工号", "account");
        List<RypzAssess> normalAssesses = reader.readAll(RypzAssess.class);
        for (RypzAssess assess : normalAssesses) {
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }

            assess.setUserId(user.getId());
            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = assessNormPoint.getRypzMain();
                mainPoint += assess.getMainPoint();
                assessNormPoint.setRypzMain(mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainPoint();
                assessNormPoint.setRypzMain(mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }
}