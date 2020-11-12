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
 * 岗位管理实体类
 *
 * @author 
 * @Date 2020-01-17 09:37:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job")
public class Job extends BaseModel<Job> {

    /**
    * 
    */
    @TableField("id")
    private Long id;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 描述
    */
    @TableField("des")
    private String des;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

}