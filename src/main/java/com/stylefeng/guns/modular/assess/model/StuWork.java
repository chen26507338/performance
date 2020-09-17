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
 * 学生工作考核实体类
 *
 * @author cp
 * @Date 2020-09-16 15:25:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stu_work")
public class StuWork extends BaseActEntity<StuWork> {

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
    * 院长D
    */
    @TableField("dean_user_id")
    private Long deanUserId;

    /**
    * 人事领导ID
    */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
    * 学生工作考核专员ID
    */
    @TableField("stu_work_commissioner")
    private Long stuWorkCommissioner;

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
    * 学生处处长ID
    */
    @TableField("students_office_leader_id")
    private Long studentsOfficeLeaderId;

    /**
    * 书记ID
    */
    @TableField("secretary_id")
    private Long secretaryId;

    /**
    * 团委书记ID
    */
    @TableField("committee_secretary_id")
    private Long committeeSecretaryId;

    /**
     * 指标代码
     */
    @TableField(exist = false)
    private String normCode;

}