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
 * 管理服务成员实体类
 *
 * @author 
 * @Date 2020-09-17 18:39:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("man_service_member")
public class ManServiceMember extends BaseModel<ManServiceMember> {

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
    * 校级指标分
    */
    @TableField("main_norm_point")
    private Double mainNormPoint;

    /**
    * 院级浮动值
    */
    @TableField("college_norm_point")
    private Double collegeNormPoint;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

    /**
    * 年份
    */
    @TableField("year")
    private String year;

    /**
    * 管理服务ID
    */
    @TableField("m_service_id")
    private Long mServiceId;

    /**
     * 姓名
     */
    @TableField(exist = false)
    private String name;
}