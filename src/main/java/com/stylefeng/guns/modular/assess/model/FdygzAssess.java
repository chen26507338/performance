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
 * 辅导员工作考核实体类
 *
 * @author 
 * @Date 2020-10-07 11:53:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fdygz_assess")
public class FdygzAssess extends BaseActEntity<FdygzAssess> {

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
    * 考核内容
    */
    @TableField("content")
    private String content;

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
    * 考核结果
    */
    @TableField("result")
    private String result;

    /**
    * 考核年度
    */
    @TableField("year")
    private String year;

    /**
    * 学生处处长ID
    */
    @TableField("students_office_leader_id")
    private Long studentsOfficeLeaderId;

    /**
    * 分值
    */
    @TableField("point")
    private Double point;

    /**
    * 考核专员ID
    */
    @TableField("commissioner_id")
    private Long commissionerId;

    /**
    * 考核分值
    */
    @TableField("norm_point")
    private Double normPoint;

    /**
    * 书记ID
    */
    @TableField("sj_id")
    private Long sjId;

    /**
    * 团委书记ID
    */
    @TableField("twsj_id")
    private Long twsjId;

}