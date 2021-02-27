package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.modular.assess.model.AssessCoefficient;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.ManServiceMember;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.ZbryGz;
import com.stylefeng.guns.modular.payment.dao.ZbryGzMapper;
import com.stylefeng.guns.modular.payment.service.IZbryGzService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在编人员服务实现类
 *
 * @author
 * @Date 2021-02-27 14:33:44
 */
@Service
public class ZbryGzServiceImpl extends ServiceImpl<ZbryGzMapper, ZbryGz> implements IZbryGzService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void importData(ZbryGz zbryGz) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + zbryGz.getExpand().get("fileName"));
        reader.addHeaderAlias("时间", "time");
        reader.addHeaderAlias("岗位工资", "gwgz");
        reader.addHeaderAlias("薪级工资", "xjgz");
        reader.addHeaderAlias("岗位津贴", "gwjt");
        reader.addHeaderAlias("生活补贴", "shbt");
        reader.addHeaderAlias("特殊补贴", "tsbt");
        reader.addHeaderAlias("提租", "tz");
        reader.addHeaderAlias("补发", "bf");
        reader.addHeaderAlias("应发数", "yfs");
        reader.addHeaderAlias("扣款", "kk");
        reader.addHeaderAlias("调整各类保险金", "tzglbxj");
        reader.addHeaderAlias("公积金", "gjj");
        reader.addHeaderAlias("医保", "yb");
        reader.addHeaderAlias("养老金", "ylj");
        reader.addHeaderAlias("职业年金", "zynj");
        reader.addHeaderAlias("失业金", "syj");
        reader.addHeaderAlias("会员费", "hyf");
        reader.addHeaderAlias("房水费", "fsf");
        reader.addHeaderAlias("所得税", "sds");
        reader.addHeaderAlias("实发数", "sfs");
        reader.addHeaderAlias("人员代码", "account");
        List<Map> maps = reader.readAll(Map.class);
        List<ZbryGz> zbryGzs = new ArrayList<>();
        for (Map map : maps) {
            User u = userService.getByAccount(map.get("account") + "");
            if (u == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", map.get("account")));
            }
            Date date = DateUtils.parse(map.get("time") + "", "yyyy.MM");
            ZbryGz gz = BeanUtil.mapToBean(map, ZbryGz.class, true);
            gz.setInTime(date);
            gz.setUserId(u.getId());
            zbryGzs.add(gz);
        }
        this.insertBatch(zbryGzs);
    }
}