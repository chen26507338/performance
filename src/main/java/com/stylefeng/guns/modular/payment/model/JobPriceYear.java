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
 * 年度岗位责任奖实体类
 *
 * @author 
 * @Date 2020-10-19 22:04:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_price_year")
public class JobPriceYear extends BaseModel<JobPriceYear> {

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 年度
    */
    @TableField("year")
    private String year;

    /**
    * 一月
    */
    @TableField("month_1")
    private Double month1;

    /**
    * 二月
    */
    @TableField("month_2")
    private Double month2;

    /**
    * 三月
    */
    @TableField("month_3")
    private Double month3;

    /**
    * 四月
    */
    @TableField("month_4")
    private Double month4;

    /**
    * 五月
    */
    @TableField("month_5")
    private Double month5;

    /**
    * 六月
    */
    @TableField("month_6")
    private Double month6;

    /**
    * 七月
    */
    @TableField("month_7")
    private Double month7;

    /**
    * 八月
    */
    @TableField("month_8")
    private Double month8;

    /**
    * 九月
    */
    @TableField("month_9")
    private Double month9;

    /**
    * 十月
    */
    @TableField("month_10")
    private Double month10;

    /**
    * 十一月
    */
    @TableField("month_11")
    private Double month11;

    /**
    * 十二月
    */
    @TableField("month_12")
    private Double month12;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

}