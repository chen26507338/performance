package com.stylefeng.guns.modular.job.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.common.persistence.model.BaseActEntity;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 工作任务实体类
 *
 * @author cp
 * @Date 2020-01-17 09:42:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_task")
public class JobTask extends BaseActEntity<JobTask> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
    * 
    */
    @TableField("id")
    private Long id;

    /**
    * 职责
    */
    @TableField("duties")
    private String duties;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 经办人ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 委派协助人ID
    */
    @TableField("appoint_user_id")
    private Long appointUserId;

    /**
    * 任务发起人ID
    */
    @TableField("start_user_id")
    private Long startUserId;

    /**
    * 经办协作人
    */
    @TableField("apply_user_id")
    private Long applyUserId;

    /**
    * 任务分
    */
    @TableField("point")
    private Double point;

    /**
    * 任务描述
    */
    @TableField("des")
    private String des;

    /**
    * 经办人处理结果
    */
    @TableField("user_des")
    private String userDes;

    /**
    * 委派协助人办理结果
    */
    @TableField("appoint_user_des")
    private String appointUserDes;

    /**
    * 汇总工作
    */
    @TableField("summary")
    private String summary;

    /**
    * 经办协作人办理结果
    */
    @TableField("apply_user_des")
    private String applyUserDes;

    /**
    * 提交时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 开始提交时间
    */
    @TableField(exist = false)
    private Date startCreateTime;
    /**
    * 结束提交时间
    */
    @TableField(exist = false)
    private Date endCreateTime;
    /**
    * 处理结束时间
    */
    @TableField("end_time")
    private Date endTime;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 类型
    */
    @TableField("type")
    private Integer type;

    /**
    * 开始处理结束时间
    */
    @TableField(exist = false)
    private Date startEndTime;
    /**
    * 结束处理结束时间
    */
    @TableField(exist = false)
    private Date endEndTime;
}