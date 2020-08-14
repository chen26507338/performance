package com.stylefeng.guns.modular.user.model;

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
 * 科研成果实体类
 *
 * @author cp
 * @Date 2020-08-14 15:55:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scientific_achievement")
public class ScientificAchievement extends BaseActEntity<ScientificAchievement> {

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
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 简要描述
    */
    @TableField("des")
    private String des;

    /**
    * 时间
    */
    @TableField("time")
    private String time;

    /**
    * 类型
    */
    @TableField("type")
    private String type;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 流程实例ID
    */
    @TableField("proc_ins_id")
    private String procInsId;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 人事经办ID
    */
    @TableField("hr_handle_id")
    private Long hrHandleId;

    /**
    * 部门领导ID
    */
    @TableField("dept_leader_id")
    private Long deptLeaderId;

    /**
    * 人事领导ID
    */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
    * 科技处专员ID
    */
    @TableField("sci_commissioner")
    private Long sciCommissioner;

    /**
    * 指标ID
    */
    @TableField("norm_id")
    private Long normId;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

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
    * 考核结果
    */
    @TableField("result")
    private Integer result;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 科技处领导ID
    */
    @TableField("sci_leader_id")
    private Long sciLeaderId;


    @TableField(exist = false)
    private String normCode;

    @TableField(exist = false)
    private String normName;
}