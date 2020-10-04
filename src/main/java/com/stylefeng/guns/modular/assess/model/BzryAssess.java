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
 * 表彰荣誉考核实体类
 *
 * @author 
 * @Date 2020-10-04 12:11:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bzry_assess")
public class BzryAssess extends BaseActEntity<BzryAssess> {

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
    * 职工ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 荣誉级别
    */
    @TableField("level")
    private String level;

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
    * 人事领导ID
    */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
    * 荣誉类型
    */
    @TableField("type")
    private String type;

    /**
    * 考核年度
    */
    @TableField("year")
    private String year;

    /**
    * 考核专员ID
    */
    @TableField("commissioner_id")
    private Long commissionerId;

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
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

}