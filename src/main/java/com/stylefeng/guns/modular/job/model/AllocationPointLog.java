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
 * 分配分数记录实体类
 *
 * @author 
 * @Date 2020-09-25 23:04:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("allocation_point_log")
public class AllocationPointLog extends BaseModel<AllocationPointLog> {

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
    * 考核类型
    */
    @TableField("type")
    private String type;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

}