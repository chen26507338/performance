package com.stylefeng.guns.modular.assess.model;

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
 * 通用考核实体类
 *
 * @author 
 * @Date 2020-02-02 16:40:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("normal_assess")
public class NormalAssess extends BaseActEntity<NormalAssess> {

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
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

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
    * 指标ID
    */
    @TableField("norm_id")
    private Long normId;

    /**
    * 类型
    */
    @TableField(exist = false)
    private String type;

    /**
    * 创建时间
    */
    @TableField("create_time")
    private Date createTime;

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
    * 录入人员ID
    */
    @TableField("input_user_id")
    private Long inputUserId;

}