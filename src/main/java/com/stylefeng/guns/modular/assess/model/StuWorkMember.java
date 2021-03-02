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
 * 学生工作成员实体类
 *
 * @author cp
 * @Date 2020-09-16 15:26:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stu_work_member")
public class StuWorkMember extends BaseModel<StuWorkMember> {

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
    * 考核项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
    * 参赛项目名称/团队名称/类别/百分率
    */
    @TableField("mixture")
    private String mixture;
    /**
    * 人数/次数
    */
    @TableField("result")
    private Integer result;

    /**
    * 学生工作ID
    */
    @TableField("s_work_id")
    private Long sWorkId;

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