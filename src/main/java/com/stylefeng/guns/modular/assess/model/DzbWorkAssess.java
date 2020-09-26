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
 * 党支部工作考核实体类
 *
 * @author 
 * @Date 2020-09-24 13:06:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dzb_work_assess")
public class DzbWorkAssess extends BaseActEntity<DzbWorkAssess> {

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
    * 指标ID
    */
    @TableField("norm_id")
    private Long normId;

    /**
    * 考核结果
    */
    @TableField("result")
    private Integer result;

    /**
    * 考核年度
    */
    @TableField("year")
    private String year;

    /**
    * 组织部部长ID
    */
    @TableField("zzb_leader_id")
    private Long zzbLeaderId;

    /**
    * 党支部工作考核专员ID
    */
    @TableField("dzb_commissioner")
    private Long dzbCommissioner;

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
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;


    /**
     * 人事领导ID
     */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

}