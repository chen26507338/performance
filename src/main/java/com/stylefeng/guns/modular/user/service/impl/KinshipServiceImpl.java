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
import com.stylefeng.guns.modular.user.dao.KinshipMapper;
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.service.IKinshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 亲属关系服务实现类
 *
 * @author cp
 * @Date 2020-06-30 17:27:31
 */
@Service
public class KinshipServiceImpl extends ServiceImpl<KinshipMapper, Kinship> implements IKinshipService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;


    @Override
    @Transactional
    public void audit(Kinship kinship) {
        String pass = (String) kinship.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (kinship.getExpand().get("comment") != null) {
            comment.append(kinship.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //修改数据
        String dataJson = (String) kinship.getExpand().get("data");
        List<Kinship> auditDatas = JSON.parseArray(dataJson, Kinship.class);
        if (CollUtil.isNotEmpty(auditDatas)) {
            this.handList(auditDatas,false);
            this.updateBatchById(auditDatas);
        }

        if (kinship.getAct().getTaskDefKey().equals("audit") && pass.equals(YesNo.YES.getCode() + "")) {
            Kinship param = new Kinship();
            param.setUserId((Long) actTaskService.getTaskService().getVariable(kinship.getAct().getTaskId(), "user"));

            //将所有状态标识为拒绝
            Kinship newEntity = new Kinship();
            newEntity.setStatus(YesNo.NO.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
            //本次审核的数据标识为已通过
            param.setProcInsId(kinship.getAct().getProcInsId());
            newEntity.setStatus(YesNo.YES.getCode());
            this.update(newEntity, new EntityWrapper<>(param));
        }

        actTaskService.complete(kinship.getAct().getTaskId(), kinship.getAct().getProcInsId(), comment.toString(), vars);
    }

    @Override
    @Transactional
    public void addApply(List<Kinship> kinships) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_KINSHIP_HR + "");
        User eduExpHr = userService.selectOne(wrapper);
        Map<String, Object> vars = new HashMap<>();
        vars.put("audit_user", eduExpHr.getId());
        vars.put("user", ShiroKit.getUser().id);
        vars.put("act_path", "/kinship/kinship_act");
        String proIncId = actTaskService.startProcessOnly(ActUtils.PD_PERSONAL_INFO, "kinship", ShiroKit.getUser().name + "亲属关系审核", vars);
        for (Kinship kinship : kinships) {
            kinship.setProcInsId(proIncId);
            kinship.setUserId(ShiroKit.getUser().id);
        }
        this.handList(kinships, true);
        this.insertBatch(kinships);
    }

    private void handList(List<Kinship> kinships, boolean isAdd) {
        //验证时间格式
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        for (Kinship kinship : kinships) {
            if (!p.matcher(kinship.getBirthday()).find()) {
                throw new GunsException("生日格式不正确，正确格式xxxx-xx-xx");
            }

            if (!isAdd) {
                String relationshipDict = ConstantFactory.me().getDictValueByName("亲属关系", kinship.getRelationshipDict());
                if (StrUtil.isBlank(relationshipDict)) {
                    throw new GunsException("关系填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("亲属关系").iterator(), ","));
                }
                kinship.setRelationshipDict(relationshipDict);

                String politicsStatusDict = ConstantFactory.me().getDictValueByName("政治面貌", kinship.getPoliticsStatusDict());
                if (StrUtil.isBlank(politicsStatusDict)) {
                    throw new GunsException("政治面貌填写错误，请填写 " + CollUtil.join(ConstantFactory.me().getDictsByName("政治面貌").iterator(), ","));
                }
                kinship.setPoliticsStatus(Integer.parseInt(politicsStatusDict));

            }
        }
    }

}