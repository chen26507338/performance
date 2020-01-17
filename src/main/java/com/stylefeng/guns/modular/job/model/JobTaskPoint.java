package com.stylefeng.guns.modular.job.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 工作得分实体类
 *
 * @author cp
 * @Date 2020-01-17 09:44:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_task_point")
public class JobTaskPoint extends BaseModel<JobTaskPoint> {

    /**
    * 
    */
    @TableField("id")
    private Long id;

    /**
    * 任务ID
    */
    @TableField("task_id")
    private Long taskId;

    /**
    * 职员ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 得分
    */
    @TableField("point")
    private Double point;

    /**
    * 类型
    */
    @TableField("type")
    private Integer type;

    /**
    * 创建时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 开始创建时间
    */
    @TableField(exist = false)
    private Date startCreateTime;
    /**
    * 结束创建时间
    */
    @TableField(exist = false)
    private Date endCreateTime;
}