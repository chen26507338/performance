package com.stylefeng.guns.modular.assess.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 考核系数实体类
 *
 * @author 
 * @Date 2020-02-25 10:45:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("assess_coefficient")
public class AssessCoefficient extends BaseModel<AssessCoefficient> {

    @Override
    protected Serializable pkVal() {
        return this.type;
    }

    /**
    * 类型
    */
    @TableId(value = "type",type = IdType.INPUT)
    private String type;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 系数
    */
    @TableField("coefficient")
    private Double coefficient;

}