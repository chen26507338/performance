package com.stylefeng.guns.modular.payment.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.common.persistence.model.BaseActEntity;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 月度岗位责任奖实体类
 *
 * @author 
 * @Date 2020-10-18 14:48:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_price_month")
public class JobPriceMonth extends BaseActEntity<JobPriceMonth> {

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 基本岗位责任奖
    */
    @TableField("base_price")
    private Double basePrice;

    /**
    * 管理服务工作奖
    */
    @TableField("mgr_price")
    private Double mgrPrice;

    /**
    * 补发
    */
    @TableField("retroactive_price")
    private Double retroactivePrice;

    /**
    * 扣发
    */
    @TableField("garnished_price")
    private Double garnishedPrice;

    /**
    *  应发数
    */
    @TableField("should_price")
    private Double shouldPrice;

    /**
    *  实发数
    */
    @TableField("result_price")
    private Double resultPrice;

    /**
    * 备注
    */
    @TableField("remark")
    private String remark;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    *  月度
    */
    @TableField("month")
    private String month;

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
    * 考核专员ID
    */
    @TableField("commissioner_id")
    private Long commissionerId;

    /**
    * 书记ID
    */
    @TableField("sj_id")
    private Long sjId;

    /**
    * 部门领导ID
    */
    @TableField("dept_leader_id")
    private Long deptLeaderId;
}