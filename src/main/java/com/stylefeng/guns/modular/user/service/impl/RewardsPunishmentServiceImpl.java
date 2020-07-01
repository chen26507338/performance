package com.stylefeng.guns.modular.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.dao.RewardsPunishmentMapper;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;
import com.stylefeng.guns.modular.user.service.IRewardsPunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 考核奖惩服务实现类
 *
 * @author cp
 * @Date 2020-07-01 15:23:57
 */
@Service
public class RewardsPunishmentServiceImpl extends ServiceImpl<RewardsPunishmentMapper, RewardsPunishment> implements IRewardsPunishmentService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;
    
    @Override
    @Transactional
    public void audit(RewardsPunishment rewardsPunishment) {
        String pass = (String) rewardsPunishment.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (rewardsPunishment.getExpand().get("comment") != null) {
            comment.append(rewardsPunishment.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) rewardsPunishment.getExpand().get("data");
        List<RewardsPunishment> auditDatas = JSON.parseArray(dataJson, RewardsPunishment.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas,false);
            this.updateBatchById(auditDatas);
        }

        if (rewardsPunishment.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            RewardsPunishment param = new RewardsPunishment();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(rewardsPunishment.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            RewardsPunishment newEntity = new RewardsPunishment();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(rewardsPunishment.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(rewardsPunishment.getAct().getTaskId(), rewardsPunishment.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void addApply(List<RewardsPunishment> rewardsPunishments) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_RP_HR + "");
        User eduExpHr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", eduExpHr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/rewardsPunishment/rewardsPunishment_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "rewards_punishment", ShiroKit.getUser().name + "考核奖惩审核", vars);
        for (RewardsPunishment rewardsPunishment : rewardsPunishments) {
            rewardsPunishment.setProcInsId(proIncId);
            rewardsPunishment.setUserId(ShiroKit.getUser().id);
        }
        this.handList(rewardsPunishments, true);
        this.insertBatch(rewardsPunishments);
    }


    private void handList(List<RewardsPunishment> rewardsPunishments, boolean isAdd) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (RewardsPunishment rewardsPunishment : rewardsPunishments) {
            if (!p.matcher(rewardsPunishment.getTime()).find()) {
                throw new GunsException("时间格式不正确，正确格式xxxx-xx-xx");
            }

            if (!isAdd) {
                String typeDict = ConstantFactory.me().getDictValueByName("奖惩种类", rewardsPunishment.getTypeDict());
                if (StrUtil.isBlank(typeDict)) {
                    throw new GunsException("种类填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("奖惩种类").iterator(), ","));
                }
                rewardsPunishment.setType(Integer.parseInt(typeDict));

            }
        }
    }
}