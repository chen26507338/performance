package com.stylefeng.guns.modular.user.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 学历培训实体类
 *
 * @author cp
 * @Date 2020-06-18 16:23:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("education_experience")
public class EducationExperience extends BaseModel<EducationExperience> {

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
    private Date enrollmentTime;

    /**
    * 毕业时间
    */
    @TableField("graduate_time")
    private Date graduateTime;

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
    * 学位
    */
    @TableField("degree")
    private Integer degree;

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