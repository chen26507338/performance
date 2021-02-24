package com.stylefeng.guns.modular.assess.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 竞赛获奖实体类
 *
 * @author 
 * @Date 2021-02-24 13:44:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("jshj_assess")
public class JshjAssess extends BaseModel<JshjAssess> {

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
    * 竞赛类别
    */
    @TableField("type")
    private String type;

    /**
    * 竞赛名称
    */
    @TableField("js_name")
    private String jsName;

    /**
    * 赛项名称
    */
    @TableField("sx_name")
    private String sxName;

    /**
    * 竞赛级别
    */
    @TableField("js_level")
    private String jsLevel;

    /**
    * 获奖等级
    */
    @TableField("hj_level")
    private String hjLevel;

    /**
    * 校级积分
    */
    @TableField("main_norm_point")
    private Double mainNormPoint;

    /**
    * 参赛/指导/管理
    */
    @TableField("js_type")
    private String jsType;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 用户id
    */
    @TableField("user_id")
    private Long userId;


    /**
     * 职工编号
     */
    @TableField(exist = false)
    private String account;

    /**
     * 考核系数
     */
    @TableField("coe_point")
    private Double coePoint;
}