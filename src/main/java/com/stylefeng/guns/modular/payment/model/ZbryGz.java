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
 * 在编人员实体类
 *
 * @author 
 * @Date 2021-02-27 14:33:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zbry_gz")
public class ZbryGz extends BaseModel<ZbryGz> {

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
    * 岗位工资
    */
    @TableField("gwgz")
    private Double gwgz;

    /**
    * 薪级工资
    */
    @TableField("xjgz")
    private Double xjgz;

    /**
    * 岗位津贴
    */
    @TableField("gwjt")
    private Double gwjt;

    /**
    * 生活补贴
    */
    @TableField("shbt")
    private Double shbt;

    /**
    * 特殊补贴
    */
    @TableField("tsbt")
    private Double tsbt;

    /**
    * 提租
    */
    @TableField("tz")
    private Double tz;

    /**
    * 补发
    */
    @TableField("bf")
    private Double bf;

    /**
    * 应发数
    */
    @TableField("yfs")
    private Double yfs;

    /**
    * 扣款
    */
    @TableField("kk")
    private Double kk;

    /**
    * 调整各类保险金
    */
    @TableField("tzglbxj")
    private Double tzglbxj;

    /**
    * 公积金
    */
    @TableField("gjj")
    private Double gjj;

    /**
    * 医保
    */
    @TableField("yb")
    private Double yb;

    /**
    * 养老金
    */
    @TableField("ylj")
    private Double ylj;

    /**
    * 职业年金
    */
    @TableField("zynj")
    private Double zynj;

    /**
    * 失业金
    */
    @TableField("syj")
    private Double syj;

    /**
    * 会员费
    */
    @TableField("hyf")
    private Double hyf;

    /**
    * 房水费
    */
    @TableField("fsf")
    private Double fsf;

    /**
    * 所得税
    */
    @TableField("sds")
    private Double sds;

    /**
    * 实发数
    */
    @TableField("sfs")
    private Double sfs;

}