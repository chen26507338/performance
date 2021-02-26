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
 * 专项工作考核实体类
 *
 * @author 
 * @Date 2021-02-25 18:46:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("special_assess_member")
public class SpecialAssessMember extends BaseModel<SpecialAssessMember> {

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
    * 专项项目id
    */
    @TableField("sa_id")
    private Long saId;

    /**
    * 积分
    */
    @TableField("point")
    private Double point;

    /**
    * 薪酬
    */
    @TableField("money")
    private Double money;

}