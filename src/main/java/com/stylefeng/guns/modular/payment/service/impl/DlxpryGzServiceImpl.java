package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.modular.payment.model.PqryGz;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.DlxpryGz;
import com.stylefeng.guns.modular.payment.dao.DlxpryGzMapper;
import com.stylefeng.guns.modular.payment.service.IDlxpryGzService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代理校聘人员服务实现类
 *
 * @author
 * @Date 2021-02-27 21:17:01
 */
@Service
public class DlxpryGzServiceImpl extends ServiceImpl<DlxpryGzMapper, DlxpryGz> implements IDlxpryGzService {
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importData(DlxpryGz dlxpryGz) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + dlxpryGz.getExpand().get("fileName"));
        reader.addHeaderAlias("时间", "time");
        reader.addHeaderAlias("基本工资", "jbgz");
        reader.addHeaderAlias("基础性绩效", "jcxjx");
        reader.addHeaderAlias("补发工资等", "bfgzd");
        reader.addHeaderAlias("应发工资", "yfgz");
        reader.addHeaderAlias("养老保险", "ylbx");
        reader.addHeaderAlias("医疗保险", "yl");
        reader.addHeaderAlias("失业保险", "sybx");
        reader.addHeaderAlias("工会费", "ghf");
        reader.addHeaderAlias("其他扣款", "qtkk");
        reader.addHeaderAlias("实发数", "sfs");
        reader.addHeaderAlias("公积金", "gjj");
        reader.addHeaderAlias("代码", "account");
        List<Map> maps = reader.readAll(Map.class);
        List<DlxpryGz> gzs = new ArrayList<>();
        for (Map map : maps) {
            User u = userService.getByAccount(map.get("account") + "");
            if (u == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", map.get("account")));
            }
            Date date = DateUtils.parse(map.get("time") + "", "yyyy.MM");
            DlxpryGz gz = BeanUtil.mapToBean(map, DlxpryGz.class, true);
            gz.setInTime(date);
            gz.setUserId(u.getId());
            gzs.add(gz);
        }
        this.insertBatch(gzs);

    }
}