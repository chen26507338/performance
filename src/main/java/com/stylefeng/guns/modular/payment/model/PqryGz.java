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
 * 派遣人员实体类
 *
 * @author 
 * @Date 2021-02-27 20:56:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pqry_gz")
public class PqryGz extends BaseModel<PqryGz> {

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
    * 用户id
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 时间
    */
    @TableField("in_time")
    private Date inTime;

    /**
    * 工资
    */
    @TableField("gz")
    private Double gz;

    /**
    * 补发工资差额
    */
    @TableField("bfgzce")
    private Double bfgzce;

    /**
    * 扣产假
    */
    @TableField("kcj")
    private Double kcj;

    /**
    * 应发工资
    */
    @TableField("yfgz")
    private Double yfgz;

    /**
    * 养老失业基数
    */
    @TableField("ylsyjs")
    private Double ylsyjs;

    /**
    * 医保生育基数
    */
    @TableField("ybsyjs")
    private Double ybsyjs;

    /**
    * 公积金基数
    */
    @TableField("gjjjs")
    private Double gjjjs;

    /**
    * 工伤基数
    */
    @TableField("gsjs")
    private Double gsjs;

    /**
    * 养老
    */
    @TableField("yl")
    private Double yl;

    /**
    * 失业
    */
    @TableField("sy")
    private Double sy;

    /**
    * 医保
    */
    @TableField("yb")
    private Double yb;

    /**
    * 公积金
    */
    @TableField("gjj")
    private Double gjj;

    /**
    * 合计
    */
    @TableField("hj")
    private Double hj;

    /**
    * 实发工资
    */
    @TableField("sfgz")
    private Double sfgz;

}