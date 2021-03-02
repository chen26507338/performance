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
 * 经费完成情况考核实体类
 *
 * @author 
 * @Date 2021-03-02 15:27:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("jfwcqk_assess")
public class JfwcqkAssess extends BaseModel<JfwcqkAssess> {

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
     * 职工编号
     */
    @TableField(exist = false)
    private String account;

    /**
    * 用户id
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 经费项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
    * 经费完成费
    */
    @TableField("jfwcf")
    private String jfwcf;

    /**
    * 校级分
    */
    @TableField("main_norm_point")
    private Double mainNormPoint;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

}