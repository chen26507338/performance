package com.stylefeng.guns.modular.assess.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 辅导员日常工作考核成员实体类
 *
 * @author 
 * @Date 2020-10-07 11:56:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fdygz_assess_member")
public class FdygzAssessMember extends BaseModel<FdygzAssessMember> {

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 校级指标分
    */
    @TableField("main_norm_point")
    private Double mainNormPoint;

    /**
    * 院级浮动值
    */
    @TableField("college_norm_point")
    private Double collegeNormPoint;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

    /**
    * 年份
    */
    @TableField("year")
    private String year;

    /**
    * 辅导员工作ID
    */
    @TableField("f_work_id")
    private Long fWorkId;

}