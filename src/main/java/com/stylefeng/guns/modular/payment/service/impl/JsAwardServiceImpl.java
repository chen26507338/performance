package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.JshjAssess;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.JsAward;
import com.stylefeng.guns.modular.payment.dao.JsAwardMapper;
import com.stylefeng.guns.modular.payment.service.IJsAwardService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 竞赛奖励服务实现类
 *
 * @author
 * @Date 2021-03-04 18:48:16
 */
@Service
public class JsAwardServiceImpl extends ServiceImpl<JsAwardMapper, JsAward> implements IJsAwardService {
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importData(JsAward jsAward) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + jsAward.getExpand().get("fileName"));
        reader.addHeaderAlias("用人形式", "yrxs");
        reader.addHeaderAlias("项目", "project");
        reader.addHeaderAlias("获奖类别", "hjlb");
        reader.addHeaderAlias("获奖等级", "hjdj");
        reader.addHeaderAlias("金额", "money");
        reader.addHeaderAlias("归属年份", "year");
        reader.addHeaderAlias("人员代码", "account");
        List<JsAward> datas = reader.readAll(JsAward.class);
        for (JsAward data : datas) {
            User user = userService.getByAccount(data.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", data.getAccount()));
            }
            data.setUserId(user.getId());
            data.setType(jsAward.getType());
        }
        this.insertBatch(datas);
    }
}