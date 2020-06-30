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
 * 工作简历实体类
 *
 * @author cp
 * @Date 2020-06-30 09:54:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_resume")
public class WorkResume extends BaseActEntity<WorkResume> {

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
    *
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 开始时间
    */
    @TableField("start_date")
    private String startDate;

    /**
    * 结束时间
    */
    @TableField("end_date")
    private String endDate;

    /**
    * 工作单位
    */
    @TableField("company")
    private String company;

    /**
    * 所在部门
    */
    @TableField("department")
    private String department;

    /**
    * 职务
    */
    @TableField("job")
    private String job;

    /**
    * 职称
    */
    @TableField("title")
    private String title;

    /**
    * 养老缴交月数 
    */
    @TableField("pension_month")
    private String pensionMonth;

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

}