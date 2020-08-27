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
 * 专业建设考核实体类
 *
 * @author 
 * @Date 2020-08-19 16:34:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("major_build")
public class MajorBuild extends BaseActEntity<MajorBuild> {

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
    * 负责人ID
    */
    @TableField("principal_id")
    private Long principalId;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 立项时间
    */
    @TableField("time")
    private String time;

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
    * 科技处专员ID
    */
    @TableField("major_commissioner")
    private Long majorCommissioner;

    /**
    * 指标ID
    */
    @TableField("norm_id")
    private Long normId;
    /**
    * 指标ID
    */
    @TableField(exist = false)
    private String normCode;

    /**
    * 考核结果
    */
    @TableField("result")
    private Integer result;

    /**
    * 立项年度
    */
    @TableField("approval_year")
    private String approvalYear;

    /**
    * 教务处处长ID
    */
    @TableField("dean_office_leader_id")
    private Long deanOfficeLeaderId;

    /**
    * 验收年度
    */
    @TableField("check_year")
    private String checkYear;

}