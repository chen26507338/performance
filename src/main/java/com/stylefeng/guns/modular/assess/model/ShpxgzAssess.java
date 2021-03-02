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
 * 社会培训工作考核实体类
 *
 * @author 
 * @Date 2021-03-01 23:40:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shpxgz_assess")
public class ShpxgzAssess extends BaseModel<ShpxgzAssess> {

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
    * 考核项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
     * 考核系数
     */
    @TableField("coe_point")
    private Double coePoint;


    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 数量
    */
    @TableField("num")
    private Integer num;

    /**
    * 校积分
    */
    @TableField("main_norm_point")
    private Double mainNormPoint;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

}