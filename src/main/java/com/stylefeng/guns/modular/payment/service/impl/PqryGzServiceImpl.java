package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.modular.payment.model.ZbryGz;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.PqryGz;
import com.stylefeng.guns.modular.payment.dao.PqryGzMapper;
import com.stylefeng.guns.modular.payment.service.IPqryGzService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 派遣人员服务实现类
 *
 * @author
 * @Date 2021-02-27 20:56:41
 */
@Service
public class PqryGzServiceImpl extends ServiceImpl<PqryGzMapper, PqryGz> implements IPqryGzService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importData(PqryGz pqryGz) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + pqryGz.getExpand().get("fileName"));
        reader.addHeaderAlias("时间", "time");
        reader.addHeaderAlias("工资", "gz");
        reader.addHeaderAlias("补发工资差额", "bfgzce");
        reader.addHeaderAlias("扣产假", "kcj");
        reader.addHeaderAlias("应发工资", "yfgz");
        reader.addHeaderAlias("养老失业基数", "ylsyjs");
        reader.addHeaderAlias("医保生育基数", "ybsyjs");
        reader.addHeaderAlias("公积金基数", "gjjjs");
        reader.addHeaderAlias("工伤基数", "gsjs");
        reader.addHeaderAlias("养老", "yl");
        reader.addHeaderAlias("失业", "sy");
        reader.addHeaderAlias("公积金", "gjj");
        reader.addHeaderAlias("医保", "yb");
        reader.addHeaderAlias("合计", "hj");
        reader.addHeaderAlias("实发工资", "sfgz");
        reader.addHeaderAlias("人员代码", "account");
        List<Map> maps = reader.readAll(Map.class);
        List<PqryGz> gzs = new ArrayList<>();
        for (Map map : maps) {
            User u = userService.getByAccount(map.get("account") + "");
            if (u == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", map.get("account")));
            }
            Date date = DateUtils.parse(map.get("time") + "", "yyyy.MM");
            PqryGz gz = BeanUtil.mapToBean(map, PqryGz.class, true);
            gz.setInTime(date);
            gz.setUserId(u.getId());
            gzs.add(gz);
        }
        this.insertBatch(gzs);

    }
}