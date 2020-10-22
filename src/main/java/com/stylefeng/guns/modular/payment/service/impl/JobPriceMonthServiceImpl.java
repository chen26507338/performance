package com.stylefeng.guns.modular.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.payment.model.JobPriceYear;
import com.stylefeng.guns.modular.payment.service.IJobPriceYearService;
import com.stylefeng.guns.modular.system.model.DeptAssess;
import com.stylefeng.guns.modular.system.model.OtherInfo;
import com.stylefeng.guns.modular.system.service.IOtherInfoService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.payment.model.JobPriceMonth;
import com.stylefeng.guns.modular.payment.dao.JobPriceMonthMapper;
import com.stylefeng.guns.modular.payment.service.IJobPriceMonthService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.*;
import javax.xml.ws.Action;
import java.util.*;

/**
 * 月度岗位责任奖服务实现类
 *
 * @author
 * @Date 2020-10-18 11:22:30
 */
@Service
public class JobPriceMonthServiceImpl extends ServiceImpl<JobPriceMonthMapper, JobPriceMonth> implements IJobPriceMonthService {

    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IAssessNormPointService assessNormPointService;
    @Autowired
    private IAssessCoefficientService assessCoefficientService;
    @Autowired
    private IOtherInfoService otherInfoService;
    @Autowired
    private IJobPriceYearService jobPriceYearService;

    @Override
    @Transactional
    public void audit(JobPriceMonth jobPriceMonth) {
        String pass = (String) jobPriceMonth.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (jobPriceMonth.getExpand().get("comment") != null) {
            comment.append(jobPriceMonth.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        if (pass.equals(YesNo.YES.getCode() + "")) {
            switch (jobPriceMonth.getAct().getTaskDefKey()) {
                case "commissioner_audit":
                    JobPriceMonth params = new JobPriceMonth();
                    params.setProcInsId(jobPriceMonth.getAct().getProcInsId());
                    List<JobPriceMonth> jobPriceMonths = this.selectList(new EntityWrapper<>(params));
                    for (JobPriceMonth priceMonth : jobPriceMonths) {
                        JobPriceYear jobPriceYear = new JobPriceYear();
                        jobPriceYear.setUserId(priceMonth.getUserId());
                        jobPriceYear.setYear(priceMonth.getYear());
                        jobPriceYear = jobPriceYearService.selectOne(new EntityWrapper<>(jobPriceYear));
                        if (jobPriceYear == null) {
                            jobPriceYear = new JobPriceYear();
                            jobPriceYear.setUserId(priceMonth.getUserId());
                            jobPriceYear.setYear(priceMonth.getYear());
                        }

                        params = new JobPriceMonth();
                        params.setDeptId(priceMonth.getDeptId());
                        params.setStatus(YesNo.YES.getCode());
                        params.setMonth(priceMonth.getMonth());
                        params.setUserId(priceMonth.getUserId());
                        JobPriceMonth oldJobPrice = this.selectOne(new EntityWrapper<>(params));
                        String monthFieldName = "month" + priceMonth.getMonth();
                        if (oldJobPrice != null) {
                            double price = (double) ReflectUtil.getFieldValue(jobPriceYear, monthFieldName);
                            price = price - oldJobPrice.getResultPrice();
                            ReflectUtil.setFieldValue(jobPriceYear, monthFieldName, price + priceMonth.getResultPrice());
                            oldJobPrice.deleteById();
                        } else {
                            Object price = ReflectUtil.getFieldValue(jobPriceYear, monthFieldName);
                            double newPrice = (price != null ? (double) price : 0) + priceMonth.getResultPrice();
                            ReflectUtil.setFieldValue(jobPriceYear, monthFieldName, newPrice);
                        }
                        jobPriceYearService.insertOrUpdate(jobPriceYear);
                    }

                    params = new JobPriceMonth();
                    params.setProcInsId(jobPriceMonth.getAct().getProcInsId());
                    JobPriceMonth newJobPrice = new JobPriceMonth();
                    newJobPrice.setStatus(YesNo.YES.getCode());
                    this.update(newJobPrice, new EntityWrapper<>(params));
                    break;
                case "dept_leader_audit":
                    vars.put("leader_pass", pass);
                    int hasSj = (int) actTaskService.getTaskService().getVariable(jobPriceMonth.getAct().getTaskId(), "has_sj");
                    if (hasSj == YesNo.NO.getCode()) {
                        vars.put("sj_pass", pass);
                    }
                    break;
                case "sj_audit":
                    vars.put("sj_pass", pass);
                    break;
            }
        } else {
            JobPriceMonth params = new JobPriceMonth();
            params.setProcInsId(jobPriceMonth.getAct().getProcInsId());
            this.delete(new EntityWrapper<>(params));
        }
        actTaskService.complete(jobPriceMonth.getAct().getTaskId(), jobPriceMonth.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void apply(JobPriceMonth jobPriceMonth) {
        if (StrUtil.isBlank(jobPriceMonth.getMonth())) {
            throw new GunsException("月份不能为空");
        }
        try {
            int month = Integer.parseInt(jobPriceMonth.getMonth());
            if (month < 1 || month > 12) {
                throw new GunsException("请输入1-12数字");
            }
        } catch (NumberFormatException e) {
            throw new GunsException("请输入数字");
        }

        if (StrUtil.isBlank(jobPriceMonth.getExpand().get("fileName").toString())) {
            throw new GunsException("请导入数据");
        }
        OtherInfo currentYears = otherInfoService.getOtherInfoByKey("current_years");
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + jobPriceMonth.getExpand().get("fileName"));
        reader.addHeaderAlias("基本岗位责任奖", "basePrice");
        reader.addHeaderAlias("管理服务工作奖", "mgrPrice");
        reader.addHeaderAlias("补发", "retroactivePrice");
        reader.addHeaderAlias("扣发", "garnishedPrice");
        reader.addHeaderAlias("备注", "remark");
        reader.addHeaderAlias("姓名", "userName");
        reader.addHeaderAlias("代码", "account");

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User leader = userService.selectOne(wrapper);

        //人事经办
        wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_RSJX_HR + "");
        User commissioner = userService.selectOne(wrapper);


        Map<String, Object> vars = new HashMap<>();
        vars.put("user", ShiroKit.getUser().id);
        vars.put("dept_leader", leader.getId());
        vars.put("commissioner", commissioner.getId());
        vars.put("has_sj", YesNo.NO.getCode());
        String procInsId = actTaskService.startProcessOnly(ActUtils.PD_TASK_JOB_PRICE_MONTH[0], ActUtils.PD_TASK_JOB_PRICE_MONTH[1], "岗位责任奖", vars);

        List<Map> jobPriceMonths = reader.readAll(Map.class);
        if (CollUtil.isEmpty(jobPriceMonths)) {
            throw new GunsException("请导入数据");
        }

        List<JobPriceMonth> datas = new ArrayList<>();
        for (Map priceMonth : jobPriceMonths) {
            String no = String.valueOf(priceMonth.get("account").toString());
            User employee = userService.getByAccount(no);
            if (employee == null) {
                throw new GunsException("职工" + no + "不存在");
            }

            if (!employee.getName().equals(priceMonth.get("userName"))) {
                throw new GunsException(StrUtil.format("代码 {} 和人员姓名不一致", employee.getAccount()));
            }

            JobPriceMonth jobPrice = BeanUtil.mapToBean(priceMonth, JobPriceMonth.class, true);
            double basePrice = jobPrice.getBasePrice() == null ? 0 : jobPrice.getBasePrice();
            double mgrPrice = jobPrice.getMgrPrice() == null ? 0 : jobPrice.getMgrPrice();
            double retroactivePrice = jobPrice.getRetroactivePrice() == null ? 0 : jobPrice.getRetroactivePrice();
            double garnishedPrice = jobPrice.getGarnishedPrice() == null ? 0 : jobPrice.getGarnishedPrice();

            jobPrice.setCommissionerId(commissioner.getId());
            jobPrice.setDeptLeaderId(leader.getId());
            jobPrice.setUserId(employee.getId());
            jobPrice.setProcInsId(procInsId);
            jobPrice.setDeptId(ShiroKit.getUser().deptId);
            jobPrice.setYear(currentYears.getOtherValue());
            jobPrice.setMonth(jobPriceMonth.getMonth());
            jobPrice.setShouldPrice(basePrice+mgrPrice+retroactivePrice);
            jobPrice.setResultPrice(jobPrice.getShouldPrice() - garnishedPrice);
            datas.add(jobPrice);
        }
        this.insertBatch(datas);
    }

    @Override
    @Transactional
    public void importData(JobPriceMonth jobPriceMonth) {


//        ShiroKit.getSession().setAttribute("jobPriceMonthData", jobPriceMonths);
    }
}