package com.stylefeng.guns.modular.user.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 打卡签到管理实体类
 *
 * @author 
 * @Date 2021-03-10 19:38:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sign_in_log")
public class SignInLog extends BaseModel<SignInLog> {

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
    * 位置信息
    */
    @TableField("location")
    private String location;

    /**
    * 经度
    */
    @TableField("longitude")
    private Double longitude;

    /**
    * 纬度
    */
    @TableField("latitude")
    private Double latitude;

    /**
    * 打卡时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 打卡类型
    */
    @TableField("type")
    private Integer type;

}