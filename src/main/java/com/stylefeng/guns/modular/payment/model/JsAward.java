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
 * 竞赛奖励实体类
 *
 * @author 
 * @Date 2021-03-04 18:48:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("js_award")
public class JsAward extends BaseModel<JsAward> {

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
    * 用人形式
    */
    @TableField("yrxs")
    private String yrxs;

    /**
    * 项目
    */
    @TableField("project")
    private String project;

    /**
    * 获奖类别
    */
    @TableField("hjlb")
    private String hjlb;

    /**
    * 获奖等级
    */
    @TableField("hjdj")
    private String hjdj;

    /**
     * 职工编号
     */
    @TableField(exist = false)
    private String account;

    /**
    * 金额
    */
    @TableField("money")
    private String money;

    /**
    * 类型
    */
    @TableField("type")
    private String type;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

}