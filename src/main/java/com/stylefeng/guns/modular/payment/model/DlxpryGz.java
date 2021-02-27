package com.stylefeng.guns.modular.payment.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 代理校聘人员实体类
 *
 * @author 
 * @Date 2021-02-27 21:17:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dlxpry_gz")
public class DlxpryGz extends BaseModel<DlxpryGz> {

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
     * 时间
     */
    @TableField("in_time")
    private Date inTime;


    /**
    * 用户id
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 基本工资
    */
    @TableField("jbgz")
    private Double jbgz;

    /**
    * 基础性绩效
    */
    @TableField("jcxjx")
    private Double jcxjx;

    /**
    * 补发工资等
    */
    @TableField("bfgzd")
    private Double bfgzd;

    /**
    * 应发工资
    */
    @TableField("yfgz")
    private Double yfgz;

    /**
    * 养老保险
    */
    @TableField("ylbx")
    private Double ylbx;

    /**
    * 医疗保险
    */
    @TableField("yl")
    private Double yl;

    /**
    * 失业保险
    */
    @TableField("sybx")
    private Double sybx;

    /**
    * 公积金
    */
    @TableField("gjj")
    private Double gjj;

    /**
    * 工会费
    */
    @TableField("ghf")
    private Double ghf;

    /**
    * 其他扣款
    */
    @TableField("qtkk")
    private Double qtkk;

    /**
    * 实发数
    */
    @TableField("sfs")
    private Double sfs;

}