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
 * 教学考核实体类
 *
 * @author 
 * @Date 2020-09-20 15:34:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teaching_load_assess")
public class TeachingLoadAssess extends BaseActEntity<TeachingLoadAssess> {

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
    * 教务处处长ID
    */
    @TableField("dean_office_leader_id")
    private Long deanOfficeLeaderId;

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
    * 院长D
    */
    @TableField("dean_user_id")
    private Long deanUserId;

    /**
    * 二级教学干事ID
    */
    @TableField("dept_teaching_id")
    private Long deptTeachingId;

    /**
    * 教学考核专员ID
    */
    @TableField("dean_office_commissioner")
    private Long deanOfficeCommissioner;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

}