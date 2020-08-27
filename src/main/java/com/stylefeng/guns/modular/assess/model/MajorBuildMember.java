package com.stylefeng.guns.modular.assess.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 专业建设项目成员实体类
 *
 * @author 
 * @Date 2020-08-19 16:35:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("major_build_mamber")
public class MajorBuildMember extends BaseModel<MajorBuildMember> {

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
    * 专业建设ID
    */
    @TableField("build_id")
    private Long buildId;

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
    * 考核系数
    */
    @TableField("year")
    private String year;

}