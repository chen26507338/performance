package com.stylefeng.guns.modular.pay.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 薪酬设置实体类
 *
 * @author 
 * @Date 2021-02-14 11:12:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_setting")
public class PaySetting extends BaseModel<PaySetting> {

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
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 薪酬
    */
    @TableField("money")
    private Double money;

}