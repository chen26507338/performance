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
 * 思政工作实体类
 *
 * @author 
 * @Date 2020-09-30 13:31:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("szgz_assess")
public class SzgzAssess extends BaseActEntity<SzgzAssess> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
    * id
    */
    @TableId("id")
    private Long id;

    /**
    * 职工ID
    */
    @TableField("user_id")
    private Long userId;

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
    * 院长ID
    */
    @TableField("dean_id")
    private Long deanId;

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

    /**
    * 类型
    */
    @TableField("type")
    private String type;

}