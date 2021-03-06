package com.stylefeng.guns.modular.assess.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.JsonMapper;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.assess.model.*;
import com.stylefeng.guns.modular.assess.service.IAssessCoefficientService;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import com.stylefeng.guns.modular.assess.service.IAssessNormService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.model.DeptAssess;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.dao.NormalAssessMapper;
import com.stylefeng.guns.modular.assess.service.INormalAssessService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 考核指标库服务实现类
 *
 * @author
 * @Date 2020-02-02 13:18:03
 */
@Service
public class NormalAssessServiceImpl extends ServiceImpl<NormalAssessMapper, NormalAssess> implements INormalAssessService {
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IRoleService roleService;
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
    public boolean updateById(NormalAssess entity) {
        NormalAssess oldAssess = this.selectById(entity.getId());
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        double mainPoint = (double) ReflectUtil.getFieldValue(point, entity.getType() + "Main") - oldAssess.getMainPoint();
        ReflectUtil.setFieldValue(point, entity.getType() + "Main", mainPoint+entity.getMainPoint());
        point.updateById();
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        NormalAssess oldAssess = this.selectById(id);
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        double mainPoint = (double) ReflectUtil.getFieldValue(point, oldAssess.getType() + "Main") - oldAssess.getMainPoint();
        ReflectUtil.setFieldValue(point, oldAssess.getType() + "Main", mainPoint);
        point.updateById();
        return super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(NormalAssess entity) {
        List<Map> normalAssesses;
        if (entity.getExpand().get("fileName") != null) {
            ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + entity.getExpand().get("fileName"));
            reader.addHeaderAlias("分院名称", "deptName");
            reader.addHeaderAlias("考核项目", "assessName");
            reader.addHeaderAlias("考核指标代码", "normCode");
            reader.addHeaderAlias("考核结果", "result");
            reader.addHeaderAlias("职工姓名", "userName");
            reader.addHeaderAlias("职工代码", "account");
            normalAssesses = reader.readAll(Map.class);
        } else {
            normalAssesses = (List<Map>) entity.getExpand().get("data");
        }

        this.insertBatch(handleMap(normalAssesses, entity.getType(), true));
        return true;
    }

    private List<NormalAssess> handleMap(List<Map> normalAssesses, String type, boolean isImport) {
        User leader = null;
        User hrHandle = null;
        User commissioner = null;
        String procInsId = null;

        if (isImport) {
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
            wrapper.eq("dept_id", DeptAssess.typeOf(type));
            leader = userService.selectOne(wrapper);

            //人事经办
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", IRoleService.TYPE_HR_HANDLER + "");
            wrapper.eq("dept_id", IDeptService.HR);
            hrHandle = userService.selectOne(wrapper);

            //考核专员
            Role role = roleService.getByTips(type + "_hr");
            wrapper = new EntityWrapper<>();
            wrapper.like("role_id", role.getId() + "");
            commissioner = userService.selectOne(wrapper);

            Map<String, Object> vars = new HashMap<>();
            vars.put("user", ShiroKit.getUser().id);
            vars.put("leader", leader.getId());
            vars.put("hr_handle", hrHandle.getId());
            vars.put("commissioner", commissioner.getId());
            procInsId = actTaskService.startProcessOnly(ActUtils.PD_NORMAL_ASSESS[0], ActUtils.PD_NORMAL_ASSESS[1], "考核", vars);
        }
        List<NormalAssess> datas = new ArrayList<>();
        for (Map excelMap : normalAssesses) {
            NormalAssess assess = new NormalAssess();

            String no = String.valueOf(excelMap.get("account"));
            User employee = userService.getByAccount(no);
            if (employee == null) {
                throw new GunsException("职工" + no + "不存在");
            }

            if (isImport) {
                assess.setDeptId(employee.getDeptId());
                assess.setHrHandleId(hrHandle.getId());
                assess.setHrLeaderId(commissioner.getId());
                assess.setDeptLeaderId(leader.getId());
                assess.setInputUserId(ShiroKit.getUser().getId());
                assess.setCreateTime(new Date());
            }

            assess.setUserId(employee.getId());
            assess.setResult(Integer.parseInt(String.valueOf(excelMap.get("result"))));

            String normCode = (String) excelMap.get("normCode");
            if (StrUtil.isNotBlank(normCode)) {
                //校级标准分
                AssessNorm mainNorm = new AssessNorm();
                mainNorm.setDeptId(IAssessNormService.TYPE_MAIN_DEPT);
                mainNorm.setCode(normCode);
                mainNorm.setType(type);
                mainNorm = assessNormService.getByCode(mainNorm);
                assess.setMainNormPoint(mainNorm.getPoint());
                assess.setNormId(mainNorm.getId());
                //院级浮动值
                AssessNorm collegeNorm = new AssessNorm();
                collegeNorm.setDeptId(employee.getDeptId());
                collegeNorm.setCode(normCode);
                collegeNorm.setType(type);
                collegeNorm = assessNormService.getByCode(collegeNorm);
                assess.setCollegeNormPoint(collegeNorm.getPoint());
                assess.setProcInsId(procInsId);
                //考核系数
                AssessCoefficient coefficient = assessCoefficientService.selectById(type);
                assess.setCoePoint(coefficient.getCoefficient());
            }

            String id = (String) excelMap.get("id");
            if (StrUtil.isNotBlank(id)) {
                assess.setId(Long.valueOf(id));
            }
            datas.add(assess);
        }
        return datas;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(NormalAssess normalAssess) {
        String pass = (String) normalAssess.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【拒绝】");
        if (normalAssess.getExpand().get("comment") != null) {
            comment.append(normalAssess.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);
        String dataJson = (String) normalAssess.getExpand().get("data");
        List<Map> normalAssesses = JSON.parseArray(dataJson, Map.class);
        if (CollUtil.isNotEmpty(normalAssesses)) {
            List<NormalAssess> updates = handleMap(normalAssesses, normalAssess.getType(), false);
            this.updateBatchById(updates);
        }

        if (pass.equals(YesNo.YES.getCode() + "")) {
            EntityWrapper<NormalAssess> wrapper = new EntityWrapper<>();
            wrapper.eq("proc_ins_id", normalAssess.getAct().getProcInsId());
            NormalAssess update = new NormalAssess();
            switch (normalAssess.getAct().getTaskDefKey()) {
                //人事经办
                case "hr_handle_audit":
                    if (StrUtil.isBlank(normalAssess.getYear())) {
                        throw new GunsException("请设置年度");
                    }
                    update.setYear(normalAssess.getYear());
                    this.update(update, wrapper);
                    break;
                //人事领导审核
                case "hr_leader_audit":
                    //计算考核分入库
                    NormalAssess assessParams = new NormalAssess();
                    assessParams.setProcInsId(normalAssess.getProcInsId());
                    List<NormalAssess> assessList = this.selectList(new EntityWrapper<>(assessParams));
                    for (NormalAssess assess : assessList) {
                        AssessNormPoint assessNormPoint = new AssessNormPoint();
                        assessNormPoint.setUserId(assess.getUserId());
                        assessNormPoint.setYear(assess.getYear());
                        assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

                        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(normalAssess.getType());
                        if (assessNormPoint != null) {
                            Double mainPoint = (Double) ReflectUtil.getFieldValue(assessNormPoint, normalAssess.getType() + "Main");
                            mainPoint += assess.getMainNormPoint() * assess.getResult() * assessCoefficient.getCoefficient();
                            ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "Main", mainPoint);
                            Double collegePoint = (Double) ReflectUtil.getFieldValue(assessNormPoint, normalAssess.getType() + "College");
                            collegePoint += (1 + assess.getCollegeNormPoint()) * mainPoint;
                            ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "College", collegePoint);
                        } else {
                            assessNormPoint = new AssessNormPoint();
                            double mainPoint = assess.getMainNormPoint() * assess.getResult() * assessCoefficient.getCoefficient();
                            ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "Main", mainPoint);
                            ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "College", mainPoint * (1 + assess.getCollegeNormPoint()));
                        }
                        assessNormPoint.setYear(assess.getYear());
                        assessNormPoint.setDeptId(assess.getDeptId());
                        assessNormPoint.setUserId(assess.getUserId());
                        assessNormPointService.insertOrUpdate(assessNormPoint);
                    }
                    update.setStatus(YesNo.YES.getCode());
                    this.update(update, wrapper);
                    break;
            }
        }
        actTaskService.complete(normalAssess.getAct().getTaskId(), normalAssess.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void importAssess(NormalAssess normalAssess) {
        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + normalAssess.getExpand().get("fileName"));
        reader.addHeaderAlias("考核项目", "assessName");
        reader.addHeaderAlias("校级积分", "mainPoint");
        reader.addHeaderAlias("时间", "inTime");
        reader.addHeaderAlias("级别", "jb");
        reader.addHeaderAlias("等次", "dc");
        reader.addHeaderAlias("次数", "result");
        reader.addHeaderAlias("积分归属年份", "year");
        reader.addHeaderAlias("教师工号", "account");
        List<NormalAssess> normalAssesses = reader.readAll(NormalAssess.class);
        AssessCoefficient assessCoefficient = assessCoefficientService.selectById(normalAssess.getType());
        for (NormalAssess assess : normalAssesses) {
            assess.setCoePoint(assessCoefficient.getCoefficient());
            User user = userService.getByAccount(assess.getAccount());
            if (user == null) {
                throw new GunsException(StrUtil.format("职工编号 {} 不存在", assess.getAccount()));
            }

            assess.setUserId(user.getId());
            assess.setStatus(YesNo.YES.getCode());
            assess.setCreateTime(new Date());
            assess.setDeptId(user.getDeptId());
            assess.setType(normalAssess.getType());

            AssessNormPoint assessNormPoint = new AssessNormPoint();
            assessNormPoint.setUserId(assess.getUserId());
            assessNormPoint.setYear(assess.getYear());
            assessNormPoint = assessNormPointService.selectOne(new EntityWrapper<>(assessNormPoint));

            if (assessNormPoint != null) {
                Double mainPoint = (Double) ReflectUtil.getFieldValue(assessNormPoint, normalAssess.getType() + "Main");
                mainPoint += assess.getMainPoint();
                ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "Main", mainPoint);
            } else {
                assessNormPoint = new AssessNormPoint();
                double mainPoint = assess.getMainPoint();
                ReflectUtil.setFieldValue(assessNormPoint, normalAssess.getType() + "Main", mainPoint);
                assessNormPoint.setYear(assess.getYear());
                assessNormPoint.setUserId(assess.getUserId());
                assessNormPoint.setDeptId(user.getDeptId());
            }
            assessNormPointService.insertOrUpdate(assessNormPoint);
        }
        this.insertBatch(normalAssesses);
    }

    @Override
    public Page<NormalAssess> selectPage(Page<NormalAssess> page, Wrapper<NormalAssess> wrapper) {
        page.setRecords(baseMapper.selectPage(page, wrapper.getEntity()));
        return page;
    }
}