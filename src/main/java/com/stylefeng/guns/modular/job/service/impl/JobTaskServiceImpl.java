package com.stylefeng.guns.modular.job.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.act.service.ActTaskService;
import com.stylefeng.guns.modular.act.utils.ActUtils;
import com.stylefeng.guns.modular.job.dao.JobTaskPointMapper;
import com.stylefeng.guns.modular.job.model.JobDuties;
import com.stylefeng.guns.modular.job.model.JobTaskApply;
import com.stylefeng.guns.modular.job.model.JobTaskPoint;
import com.stylefeng.guns.modular.job.service.IJobTaskPointService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.job.model.JobTask;
import com.stylefeng.guns.modular.job.dao.JobTaskMapper;
import com.stylefeng.guns.modular.job.service.IJobTaskService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    @Autowired
    private IJobTaskPointService jobTaskPointService;

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
        User user = userService.selectById(entity.getUserId());
        JobDuties dutiesParams = new JobDuties();
        dutiesParams.setJobId(user.getJobId());
        dutiesParams.setDes(entity.getDuties());
        if (dutiesParams.selectCount(new EntityWrapper<>(dutiesParams)) == 0) {
            dutiesParams.setPoint(entity.getPoint());
            dutiesParams.insert();
        }
        actTaskService.startProcess(ActUtils.PD_TASK_APPOINT[ActUtils.PD_TASK_ID], ActUtils.PD_TASK_APPOINT[ActUtils.PD_TASK_TABLE],
                entity.getId() + "", entity.getDes(), vars);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(JobTask entity) {
        String comment = "";
        Map<String, Object> vars = new HashMap<>();
        switch (entity.getAct().getTaskDefKey()) {
            //经办确认
            case "user_confirm":
                if (entity.getExpand().get("pass").equals(YesNo.NO.getCode() + "")) {
                    vars.put("pass", YesNo.NO.getCode());
                } else {
                    vars.put("pass", YesNo.YES.getCode());
                    //是否申请协助人
                    if (ToolUtil.isNotEmpty(entity.getExpand().get("applyUserList"))) {
                        String[] ids = ((String)entity.getExpand().get("applyUserList")).split(",");
                        if (ids.length != ArrayUtil.distinct(ids).length) {
                            throw new GunsException("经派协助人不能重复");
                        }
                        actTaskService.getTaskService().setVariable(entity.getAct().getTaskId(),
                                "applyUserList", Arrays.asList(ids));
                        vars.put("hasApply", YesNo.YES.getCode());
                        entity.updateById();
                    }else{
                        vars.put("hasApply", YesNo.NO.getCode());
                    }
                }
                break;
            //重新申请协助人
            case "re_nominate_apply":
                if (entity.getExpand().get("pass").equals(YesNo.NO.getCode() + "")) {
                    vars.put("pass", YesNo.NO.getCode());
                    comment = "不申请";
                } else {
                    vars.put("pass", YesNo.YES.getCode());
                    comment = "重新申请";
                    actTaskService.getTaskService().setVariable(entity.getAct().getTaskId(),
                            "apply_user", entity.getApplyUserId()+"");
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
            //经办协作人办理
            case "user_handle":
                comment = entity.getUserDes();
                break;
            //经办人办理
            case "apply_handle":
                JobTaskApply jobTaskApply = new JobTaskApply();
                jobTaskApply.setUserId(ShiroKit.getUser().id);
                jobTaskApply.setTaskId(entity.getId());
                jobTaskApply.setCreateTime(new Date());
                jobTaskApply.setDes(entity.getApplyUserDes());
                jobTaskApply.insert();
                comment = entity.getApplyUserDes();
                break;
            case "apply_user_confirm":
            case "appoint_handle_confirm":
            case "appoint_user_confirm":
                comment = entity.getExpand().get("pass").equals(YesNo.YES.getCode() + "") ? "【通过】" : "【拒绝】";
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
                double applyUserPoint = 0;
                double appointUserPoint = Double.parseDouble(entity.getExpand().get("appointUserPoint")+"");

                JobTaskApply applyParams = new JobTaskApply();
                applyParams.setTaskId(entity.getId());
                List<JobTaskApply> jobTaskApplies = applyParams.selectList(new EntityWrapper<>(applyParams));
                List<JobTaskPoint> applyJobTaskPoints = new ArrayList<>();
                if (CollUtil.isNotEmpty(jobTaskApplies)) {
                    for (JobTaskApply taskApply : jobTaskApplies) {
                        double point = Double.parseDouble(entity.getExpand().get(taskApply.getId() + "") + "");
                        applyUserPoint += point;
                        JobTaskPoint jobTaskPoint = new JobTaskPoint();
                        jobTaskPoint.setUserId(taskApply.getUserId());
                        jobTaskPoint.setTaskId(entity.getId());
                        jobTaskPoint.setCreateTime(new Date());
                        jobTaskPoint.setType(IJobTaskPointService.TYPE_ASSIST_HANDLE);
                        applyJobTaskPoints.add(jobTaskPoint);
                    }
                }

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

                jobTaskPointService.insertBatch(applyJobTaskPoints);
            default:
                entity.updateById();
        }
        comment = StrUtil.isBlank(comment) ? "同意" : comment;
        actTaskService.complete(entity.getAct().getTaskId(), entity.getAct().getProcInsId(), comment, vars);
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