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
 * 教学工作量实体类
 *
 * @author 
 * @Date 2020-09-20 09:20:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teaching_load")
public class TeachingLoad extends BaseModel<TeachingLoad> {

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
    * 课程名称
    */
    @TableField("course_name")
    private String courseName;

    /**
    * 周次
    */
    @TableField("weeks")
    private String weeks;

    /**
    * 课程类型
    */
    @TableField("course_type")
    private String courseType;

    /**
    * 课时时数
    */
    @TableField("course_times")
    private Integer courseTimes;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

}