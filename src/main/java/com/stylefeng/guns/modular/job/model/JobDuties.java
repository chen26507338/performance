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
 * 岗位职责管理实体类
 *
 * @author cp
 * @Date 2020-01-17 09:39:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_duties")
public class JobDuties extends BaseModel<JobDuties> {

    /**
    * 
    */
    @TableField("id")
    private Long id;

    /**
    * 描述
    */
    @TableField("des")
    private String des;

    /**
    * 得分
    */
    @TableField("point")
    private Double point;

    /**
    * 岗位ID
    */
    @TableField("job_id")
    private Long jobId;

}