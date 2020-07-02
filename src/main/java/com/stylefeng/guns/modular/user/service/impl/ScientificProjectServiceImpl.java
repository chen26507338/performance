package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.ScientificProject;
import com.stylefeng.guns.modular.user.dao.ScientificProjectMapper;
import com.stylefeng.guns.modular.user.service.IScientificProjectService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 科研项目服务实现类
 *
 * @author cp
 * @Date 2020-07-02 12:39:00
 */
@Service
public class ScientificProjectServiceImpl extends ServiceImpl<ScientificProjectMapper, ScientificProject> implements IScientificProjectService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;
    @Override
    @Transactional
    public void addApply(List<ScientificProject> scientificProjects) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_SCIENTIFIC_TREATISE_HR + "");
        User eduExpHr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", eduExpHr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/scientificProject/scientificProject_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "scientific_treatise", ShiroKit.getUser().name + "科研论著审核", vars);
        for (ScientificProject scientificProject : scientificProjects) {
            scientificProject.setProcInsId(proIncId);
            scientificProject.setUserId(ShiroKit.getUser().id);
        }
        this.handList(scientificProjects);
        this.insertBatch(scientificProjects);
    }

    @Override
    @Transactional
    public void audit(ScientificProject scientificProject) {
        String pass = (String) scientificProject.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (scientificProject.getExpand().get("comment") != null) {
            comment.append(scientificProject.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) scientificProject.getExpand().get("data");
        List<ScientificProject> auditDatas = JSON.parseArray(dataJson, ScientificProject.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas);
            this.updateBatchById(auditDatas);
        }

        if (scientificProject.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            ScientificProject param = new ScientificProject();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(scientificProject.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            ScientificProject newEntity = new ScientificProject();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(scientificProject.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(scientificProject.getAct().getTaskId(), scientificProject.getAct().getProcInsId(), comment.toString(), vars);

    }

    private void handList(List<ScientificProject> scientificProjects) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (ScientificProject scientificProject : scientificProjects) {
            if (!p.matcher(scientificProject.getStartTime()).find()) {
                throw new GunsException("开题时间格式不正确，正确格式xxxx-xx-xx");
            }
            if (!p.matcher(scientificProject.getEndTime()).find()) {
                throw new GunsException("结题时间格式不正确，正确格式xxxx-xx-xx");
            }
        }
    }
}