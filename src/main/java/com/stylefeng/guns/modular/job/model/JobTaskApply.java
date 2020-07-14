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
 * 经办协作人办理结果实体类
 *
 * @author 
 * @Date 2020-07-14 18:57:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_task_apply")
public class JobTaskApply extends BaseModel<JobTaskApply> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
    * 
    */
    @TableId("id")
    private Long id;

    /**
    * 办理结果
    */
    @TableField("des")
    private String des;

    /**
    * 任务ID
    */
    @TableField("task_id")
    private Long taskId;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 办理时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 分数
    */
    @TableField("point")
    private Double point;

}