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
 * 管理服务实体类
 *
 * @author 
 * @Date 2020-09-17 16:39:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("man_service")
public class ManService extends BaseActEntity<ManService> {

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
    * 部门长ID
    */
    @TableField("dept_leader_id")
    private Long deptLeaderId;

    /**
    * 人事领导ID
    */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
    * 综办ID
    */
    @TableField("general_man_id")
    private Long generalManId;

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
     * 指标代码
     */
    @TableField(exist = false)
    private String normCode;

}