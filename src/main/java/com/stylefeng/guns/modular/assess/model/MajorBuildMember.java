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
@TableName("major_build_member")
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
    private String status;

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

    /**
    * 考核项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
    * 分类
    */
    @TableField("type")
    private String type;

    /**
    * 名称
    */
    @TableField("build_name")
    private String buildName;

    /**
    * 排名
    */
    @TableField("rank")
    private String rank;

    /**
    * 立项时间
    */
    @TableField("start_time")
    private String startTime;

    /**
    * 建设时间
    */
    @TableField("build_time")
    private String buildTime;

    /**
    * 备注
    */
    @TableField("remark")
    private String remark;

    /**
    * 姓名
    */
    @TableField(exist = false)
    private String name;

    /**
     * 职工编号
     */
    @TableField(exist = false)
    private String account;


}