package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNorm;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.TeachingLoad;
import com.stylefeng.guns.modular.assess.dao.TeachingLoadMapper;
import com.stylefeng.guns.modular.assess.service.ITeachingLoadService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 教学工作量服务实现类
 *
 * @author
 * @Date 2020-09-20 09:20:09
 */
@Service
public class TeachingLoadServiceImpl extends ServiceImpl<TeachingLoadMapper, TeachingLoad> implements ITeachingLoadService {
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
    @Autowired
    private IOtherInfoService otherInfoService;

    @Override
    @Transactional
    public void add(TeachingLoad entity) {
        List<Map> list;
        if (entity.getExpand().get("fileName") != null) {
            ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + entity.getExpand().get("fileName"));
            reader.addHeaderAlias("分院名称", "deptName");
            reader.addHeaderAlias("考核项目", "assessName");
            reader.addHeaderAlias("考核指标代码", "normCode");
            reader.addHeaderAlias("考核结果", "result");
            reader.addHeaderAlias("职工姓名", "userName");
            reader.addHeaderAlias("职工代码", "account");
            list = reader.readAll(Map.class);
        } else {
            list = JSON.parseArray((String)entity.getExpand().get("data"), Map.class) ;
        }

        this.insertBatch(handleMap(list, true));
    }

    private List<TeachingLoad> handleMap(List<Map> maps, boolean isImport) {
        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        List<TeachingLoad> datas = new ArrayList<>();
        for (Map excelMap : maps) {
            TeachingLoad teachingLoad = BeanUtil.mapToBean(excelMap, TeachingLoad.class, true);

            String no = String.valueOf(excelMap.get("account"));
            User employee = userService.getByAccount(no);
            if (employee == null) {
                throw new GunsException("职工" + no + "不存在");
            }

            teachingLoad.setUserId(employee.getId());
            teachingLoad.setYear(currentYears.getOtherValue());
            teachingLoad.setDeptId(ShiroKit.getUser().deptId);
            datas.add(teachingLoad);
        }
        return datas;
    }

}