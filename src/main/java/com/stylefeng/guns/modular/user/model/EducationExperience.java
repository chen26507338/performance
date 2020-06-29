package com.stylefeng.guns.modular.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.common.persistence.model.BaseActEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 学历培训实体类
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("education_experience")
public class EducationExperience extends BaseActEntity<EducationExperience> {

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
    * 入学时间
    */
    @TableField("enrollment_time")
    private String enrollmentTime;

    /**
    * 毕业时间
    */
    @TableField("graduate_time")
    private String graduateTime;

    /**
    * 毕业学校
    */
    @TableField("school")
    private String school;

    /**
     * 所学专业
     */
    @TableField("major")
    private String major;

    /**
    * 学历
    */
    @TableField("education_background")
    private Integer educationBackground;

    /**
    * 学历
    */
    @TableField(exist = false)
    private String educationBackgroundDict;
    /**
    * 学位
    */
    @TableField(exist = false)
    private String degreeDict;
    /**
    * 学习方式
    */
    @TableField(exist = false)
    private String learnStyleDict;

    /**
    * 学位
    */
    @TableField("degree")
    private Integer degree;
    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
     * 流程实例ID
     */
    @TableField("proc_ins_id")
    private String procInsId;


    /**
    * 学习方式
    */
    @TableField("learn_style")
    private Integer learnStyle;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

}