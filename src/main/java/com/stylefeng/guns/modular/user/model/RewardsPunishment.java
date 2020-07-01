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
 * 考核奖惩实体类
 *
 * @author cp
 * @Date 2020-07-01 15:23:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rewards_punishment")
public class RewardsPunishment extends BaseActEntity<RewardsPunishment> {

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
    * 奖或惩
    */
    @TableField("content")
    private String content;

    /**
    * 时间
    */
    @TableField("time")
    private String time;

    /**
    * 种类
    */
    @TableField("type")
    private Integer type;
    /**
    * 种类
    */
    @TableField(exist = false)
    private String typeDict;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 批准依据（文号）
    */
    @TableField("reference_number")
    private String referenceNumber;

    /**
    * 批准单位
    */
    @TableField("approved_by")
    private String approvedBy;

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

}