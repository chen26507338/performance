package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.EducationExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.WorkResume;
import com.stylefeng.guns.modular.user.dao.WorkResumeMapper;
import com.stylefeng.guns.modular.user.service.IWorkResumeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 工作简历服务实现类
 *
 * @author cp
 * @Date 2020-06-30 09:54:17
 */
@Service
public class WorkResumeServiceImpl extends ServiceImpl<WorkResumeMapper, WorkResume> implements IWorkResumeService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void addApply(List<WorkResume> workResumes) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_WORK_RESUME_HR + "");
        User hr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", hr.getId());
        vars.put("user", ShiroKit.getUser().id);
        //审核路径
        vars.put("act_path", "/workResume/workResume_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "work_resume", ShiroKit.getUser().name + "工作简历审核", vars);
        for (WorkResume workResume : workResumes) {
            workResume.setProcInsId(proIncId);
            workResume.setUserId(ShiroKit.getUser().id);
        }
        this.handList(workResumes);
        this.insertBatch(workResumes);
    }

    @Override
    @Transactional
    public void audit(WorkResume workResume) {
        String pass = (String) workResume.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (workResume.getExpand().get("comment") != null) {
            comment.append(workResume.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) workResume.getExpand().get("data");
        List<WorkResume> auditDatas = JSON.parseArray(dataJson, WorkResume.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas);
            this.updateBatchById(auditDatas);
        }

        if (workResume.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            WorkResume param = new WorkResume();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(workResume.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            WorkResume newEntity = new WorkResume();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(workResume.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(workResume.getAct().getTaskId(), workResume.getAct().getProcInsId(), comment.toString(), vars);
    }

    private void handList(List<WorkResume> workResumes) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (WorkResume workResume : workResumes) {
            if (!p.matcher(workResume.getStartDate()).find()) {
                throw new GunsException("开始时间格式不正确，正确格式xxxx-xx-xx");
            }
            if (!p.matcher(workResume.getEndDate()).find()) {
                throw new GunsException("结束时间格式不正确，正确格式xxxx-xx-xx");
            }

        }
    }
}