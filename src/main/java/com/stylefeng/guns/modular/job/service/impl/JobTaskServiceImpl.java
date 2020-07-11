package com.stylefeng.guns.modular.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.job.model.JobTaskPoint;
import com.stylefeng.guns.modular.job.service.IJobTaskPointService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.JobTask;
import com.stylefeng.guns.modular.job.dao.JobTaskMapper;
import com.stylefeng.guns.modular.job.service.IJobTaskService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作任务服务实现类
 *
 * @author cp
 * @Date 2020-01-17 09:42:16
 */
 @Service
public class JobTaskServiceImpl extends ServiceImpl<JobTaskMapper, JobTask> implements IJobTaskService {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(JobTask entity) {
        entity.setType(TYPE_APPOINT);
        entity.insert();
        Map<String, Object> vars = new HashMap<>();
        vars.put("start_user", entity.getStartUserId());
        vars.put("user", entity.getUserId());
        if (entity.getAppointUserId() != null) {
            vars.put("hasAppoint", 1);
            vars.put("appoint_user", entity.getAppointUserId());
        } else {
            vars.put("hasAppoint", 0);
        }
        actTaskService.startProcess(ActUtils.PD_TASK_APPOINT[ActUtils.PD_TASK_ID], ActUtils.PD_TASK_APPOINT[ActUtils.PD_TASK_TABLE],
                entity.getId() + "", entity.getDes(), vars);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(JobTask entity) {
        Map<String, Object> vars = new HashMap<>();
        switch (entity.getAct().getTaskDefKey()) {
            //经办确认
            case "user_confirm":
            //重新申请协助人
            case "re_nominate_apply":
                if (entity.getExpand().get("pass").equals(YesNo.NO.getCode() + "")) {
                    vars.put("pass", YesNo.NO.getCode());
                    JobTask temp = selectById(entity.getId());
                    temp.setApplyUserId(null);
                    temp.updateAllColumnById();
                } else {
                    vars.put("pass", YesNo.YES.getCode());
                    if (entity.getApplyUserId() != null) {
                        actTaskService.getTaskService().setVariable(entity.getAct().getTaskId(),
                                "apply_user", entity.getApplyUserId());
                        vars.put("hasApply", YesNo.YES.getCode());
                        entity.updateById();
                    }else{
                        vars.put("hasApply", YesNo.NO.getCode());
                    }
                }
                break;
                case "re_nominate_appoint":
                if (entity.getExpand().get("pass").equals(YesNo.NO.getCode() + "")) {
                    vars.put("pass", YesNo.NO.getCode());
                    JobTask temp = selectById(entity.getId());
                    temp.setAppointUserId(null);
                    temp.updateAllColumnById();
                } else {
                    vars.put("pass", YesNo.YES.getCode());
                    if (entity.getAppointUserId() != null) {
                        actTaskService.getTaskService().setVariable(entity.getAct().getTaskId(),
                                "apply_user", entity.getAppointUserId());
                        vars.put("hasApply", YesNo.YES.getCode());
                        entity.updateById();
                    }else{
                        vars.put("hasApply", YesNo.NO.getCode());
                    }
                }
                break;
            case "apply_user_confirm":
            case "appoint_handle_confirm":
            case "appoint_user_confirm":
            case "summary_confirm":
                vars.put("pass", entity.getExpand().get("pass"));
                break;
            //重新指派经办人
            case "re_nominate_user":
                actTaskService.getTaskService().setVariable(entity.getAct().getTaskId(), "user", entity.getUserId());
                entity.updateById();
                break;
            //评分
            case "score":
                double userPoint = Double.parseDouble(entity.getExpand().get("userPoint")+"");
                double applyUserPoint = Double.parseDouble(entity.getExpand().get("applyUserPoint")+"");
                double appointUserPoint = Double.parseDouble(entity.getExpand().get("appointUserPoint")+"");
                if (userPoint + applyUserPoint + appointUserPoint > entity.getPoint()) {
                    throw new GunsException("总得分不得大于任务总分");
                }

                JobTask jobTask = this.selectById(entity.getId());
                JobTaskPoint userTaskPoint = new JobTaskPoint();
                userTaskPoint.setUserId(jobTask.getUserId());
                userTaskPoint.setCreateTime(new Date());
                userTaskPoint.setPoint(userPoint);
                userTaskPoint.setTaskId(entity.getId());
                userTaskPoint.setType(IJobTaskPointService.TYPE_MAIN_HANDLE);
                userTaskPoint.insert();

                if (applyUserPoint > 0) {
                    JobTaskPoint applyTaskPoint = new JobTaskPoint();
                    applyTaskPoint.setUserId(jobTask.getApplyUserId());
                    applyTaskPoint.setCreateTime(new Date());
                    applyTaskPoint.setPoint(applyUserPoint);
                    applyTaskPoint.setTaskId(entity.getId());
                    applyTaskPoint.setType(IJobTaskPointService.TYPE_ASSIST_HANDLE);
                    applyTaskPoint.insert();
                }
                if (appointUserPoint > 0) {
                    JobTaskPoint appointTaskPoint = new JobTaskPoint();
                    appointTaskPoint.setUserId(jobTask.getAppointUserId());
                    appointTaskPoint.setCreateTime(new Date());
                    appointTaskPoint.setPoint(appointUserPoint);
                    appointTaskPoint.setTaskId(entity.getId());
                    appointTaskPoint.setType(IJobTaskPointService.TYPE_ASSIST_HANDLE);
                    appointTaskPoint.insert();
                }
                entity.setEndTime(new Date());
            default:
                entity.updateById();
        }
        actTaskService.complete(entity.getAct().getTaskId(), entity.getAct().getProcInsId(), "", vars);
        return true;
    }

    @Override
    @Transactional
    public void addReport(JobTask entity) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_DEPT_LEADER + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        User deptLeader = userService.selectOne(wrapper);
        entity.setStartUserId(deptLeader.getId());
        entity.setType(TYPE_REPORT);
        entity.insert();
        Map<String, Object> vars = new HashMap<>();
        vars.put("user", entity.getUserId());
        vars.put("leader_user", entity.getStartUserId());
        actTaskService.startProcess(ActUtils.PD_TASK_REPORT[ActUtils.PD_TASK_ID], ActUtils.PD_TASK_APPOINT[ActUtils.PD_TASK_TABLE],
                entity.getId() + "", entity.getDes(), vars);
    }

    @Override
    @Transactional
    public void handleReport(JobTask entity) {
        String pass = (String) entity.getExpand().get("pass");
        StringBuilder comment = new StringBuilder(pass.equals(YesNo.YES.getCode() + "") ? "【通过】" : "【驳回】");
        if (entity.getExpand().get("comment") != null) {
            comment.append(entity.getExpand().get("comment"));
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", pass);

        //新数据
        JobTask newData = new JobTask();
        newData.setId(entity.getId());

        switch (entity.getAct().getTaskDefKey()) {
            //重新汇报
            case "re_report":
                if (pass.equals(YesNo.NO.getCode()+"")) {
                    entity.setStatus(YesNo.YES.getCode());
                }
                entity.updateById();
                break;
            //经办人处理
            case "user_handle":
                comment.setLength(0);
                if (entity.getExpand().get("comment") != null) {
                    comment.append("意见：").append(entity.getExpand().get("comment")).append("\n处理结果：");
                }
                comment.append(entity.getUserDes());
                newData.setUserDes(entity.getUserDes());
                newData.updateById();
                break;
            //部门长设置积分
            case "set_point":
                if (pass.equals(YesNo.YES.getCode()+"")) {
                    JobTask jobTask = this.selectById(entity.getId());
                    if (entity.getPoint() > jobTask.getPoint()) {
                        throw new GunsException("得分不得大于任务分");
                    }

                    JobTaskPoint userTaskPoint = new JobTaskPoint();
                    userTaskPoint.setUserId(jobTask.getUserId());
                    userTaskPoint.setCreateTime(new Date());
                    userTaskPoint.setPoint(entity.getPoint());
                    userTaskPoint.setTaskId(entity.getId());
                    userTaskPoint.setType(IJobTaskPointService.TYPE_MAIN_HANDLE);
                    userTaskPoint.insert();
                    newData.setStatus(YesNo.YES.getCode());
                    newData.setEndTime(new Date());
                    newData.updateById();
                }
                break;
        }
        actTaskService.complete(entity.getAct().getTaskId(), entity.getAct().getProcInsId(), comment.toString(), vars);
    }
}