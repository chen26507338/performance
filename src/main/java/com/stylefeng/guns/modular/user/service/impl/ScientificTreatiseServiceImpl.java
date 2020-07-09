package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.dao.ScientificTreatiseMapper;
import com.stylefeng.guns.modular.user.model.ScientificTreatise;
import com.stylefeng.guns.modular.user.service.IScientificTreatiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 科研论著服务实现类
 *
 * @author cp
 * @Date 2020-07-02 10:13:49
 */
@Service
public class ScientificTreatiseServiceImpl extends ServiceImpl<ScientificTreatiseMapper, ScientificTreatise> implements IScientificTreatiseService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public void audit(ScientificTreatise scientificTreatise) {
        String pass = (String) scientificTreatise.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (scientificTreatise.getExpand().get("comment") != null) {
            comment.append(scientificTreatise.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) scientificTreatise.getExpand().get("data");
        List<ScientificTreatise> auditDatas = JSON.parseArray(dataJson, ScientificTreatise.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas);
            this.updateBatchById(auditDatas);
        }

        if (scientificTreatise.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            ScientificTreatise param = new ScientificTreatise();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(scientificTreatise.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            ScientificTreatise newEntity = new ScientificTreatise();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(scientificTreatise.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(scientificTreatise.getAct().getTaskId(), scientificTreatise.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void addApply(List<ScientificTreatise> scientificTreatises) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_SCIENTIFIC_TREATISE_HR + "");
        User eduExpHr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", eduExpHr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/scientificTreatise/scientificTreatise_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "scientific_treatise", ShiroKit.getUser().name + " 科研论著审核", vars);
        for (ScientificTreatise scientificTreatise : scientificTreatises) {
            scientificTreatise.setProcInsId(proIncId);
            scientificTreatise.setUserId(ShiroKit.getUser().id);
        }
        this.handList(scientificTreatises);
        this.insertBatch(scientificTreatises);
    }

    private void handList(List<ScientificTreatise> scientificTreatises) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (ScientificTreatise scientificTreatise : scientificTreatises) {
            if (!p.matcher(scientificTreatise.getPublishDate()).find()) {
                throw new GunsException("发布时间格式不正确，正确格式xxxx-xx-xx");
            }
        }
    }

}