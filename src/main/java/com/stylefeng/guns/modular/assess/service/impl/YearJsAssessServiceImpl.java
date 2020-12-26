package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
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
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.assess.dao.YearJsAssessMapper;
import com.stylefeng.guns.modular.assess.service.IYearJsAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 教师考核服务实现类
 *
 * @author
 * @Date 2020-12-21 22:58:22
 */
@Service
public class YearJsAssessServiceImpl extends ServiceImpl<YearJsAssessMapper, YearJsAssess> implements IYearJsAssessService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IOtherInfoService otherInfoService;
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
    public void apply(YearJsAssess yearJsAssess) {
        String procInsId;
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if (yearJsAssess.getType() == TYPE_JS) {
            //院长
            wrapper.like("role_id", IRoleService.TYPE_DEAN + "");
            wrapper.eq("dept_id", ShiroKit.getUser().deptId);
            User dean = userService.selectOne(wrapper);


            //支部书记
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_SECRETARY + "");
            wrapper.eq("dept_id", ShiroKit.getUser().deptId);
            User sjUser = userService.selectOne(wrapper);

            //综办主任
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_ZBZR + "");
            wrapper.eq("dept_id", ShiroKit.getUser().deptId);
            User zbUser = userService.selectOne(wrapper);

            Map<String, Object> vars = new HashMap<>();
            vars.put("sj_user", sjUser.getId());
            vars.put("zbzr_user", zbUser.getId());
            vars.put("dean_user", dean.getId());
            vars.put("jys_user", yearJsAssess.getJyszrUser());
            vars.put("user", ShiroKit.getUser().id);
            procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_YEAR_JS_ASSESS[0], ActUtils.PD_TASK_YEAR_JS_ASSESS[1], "教师年度考核", vars);

            yearJsAssess.setDeanUser(dean.getId());
            yearJsAssess.setUserId(ShiroKit.getUser().id);
            yearJsAssess.setZbzrUser(zbUser.getId());
            yearJsAssess.setZbsjUser(sjUser.getId());
        } else {
            //部门领导
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", ShiroKit.getUser().deptId);
            User deptLeader = userService.selectOne(wrapper);


            //综办主任
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_ZBZR + "");
            wrapper.eq("dept_id", ShiroKit.getUser().deptId);
            User zbUser = userService.selectOne(wrapper);

            Map<String, Object> vars = new HashMap<>();
            vars.put("zbzr_user", zbUser.getId());
            vars.put("dept_leader", deptLeader.getId());
            vars.put("user", ShiroKit.getUser().id);
            procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_YEAR_OTHER_ASSESS[0], ActUtils.PD_TASK_YEAR_OTHER_ASSESS[1],
                    ConstantFactory.me().getDictsByName("模板名称", yearJsAssess.getType()), vars);

            yearJsAssess.setUserId(ShiroKit.getUser().id);
            yearJsAssess.setDeptLeader(deptLeader.getId());
            yearJsAssess.setZbzrUser(zbUser.getId());
        }

        yearJsAssess.setProcInsId(procInsId);
        yearJsAssess.insert();
    }

    @Override
    @Transactional
    public void audit(YearJsAssess yearJsAssess) {
        String pass = (String) yearJsAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (yearJsAssess.getExpand().get("comment") != null) {
            comment.append(yearJsAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        YearJsAssess params = new YearJsAssess();
        params.setProcInsId(yearJsAssess.getAct().getProcInsId());
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (yearJsAssess.getAct().getTaskDefKey()) {
                case "dean_audit":
                case "re_submit":
                case "zbzr_audit":
                    if ("zbzr_audit".equals(yearJsAssess.getAct().getTaskDefKey())) {
                        yearJsAssess.setStatus(STATUS_WAIT_AUDIT);
                        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
                        yearJsAssess.setYear(currentYears.getOtherValue());
                    }
                    this.update(yearJsAssess, new EntityWrapper<>(params));
                    break;
            }
        }
        actTaskService.complete(yearJsAssess.getAct().getTaskId(), yearJsAssess.getAct().getProcInsId(), comment.toString(), vars);
    }
}