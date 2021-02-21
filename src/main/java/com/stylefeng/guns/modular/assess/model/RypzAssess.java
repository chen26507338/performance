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
 * 人员配置考核实体类
 *
 * @author 
 * @Date 2021-02-21 17:45:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rypz_assess")
public class RypzAssess extends BaseModel<RypzAssess> {

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 考核项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
    * 校积分
    */
    @TableField("main_point")
    private Double mainPoint;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

}