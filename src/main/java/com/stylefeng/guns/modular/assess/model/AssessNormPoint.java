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
 * 考核分实体类
 *
 * @author 
 * @Date 2020-02-09 10:15:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("assess_norm_point")
public class AssessNormPoint extends BaseModel<AssessNormPoint> {

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
    * 教学督导校级分
    */
    @TableField("jxdd_main")
    private Double jxddMain;

    /**
    * 教学督导院级分
    */
    @TableField("jxdd_college")
    private Double jxddCollege;

    /**
    * 综治平安校级分
    */
    @TableField("zzpa_main")
    private Double zzpaMain;

    /**
    * 综治平安院级分
    */
    @TableField("zzpa_colleage")
    private Double zzpaColleage;

    /**
    * 师德师风校级分
    */
    @TableField("sdsf_main")
    private Double sdsfMain;

    /**
    * 师德师风院级分
    */
    @TableField("sdsf_college")
    private Double sdsfCollege;

    /**
    * 组织纪律校级分
    */
    @TableField("zzjl_main")
    private Double zzjlMain;

    /**
    * 组织纪律院级分
    */
    @TableField("zzjl_colleage")
    private Double zzjlColleage;

    /**
    * 党风党纪校级分
    */
    @TableField("dfdj_main")
    private Double dfdjMain;

    /**
    * 党风党纪院级分
    */
    @TableField("dfdj_college")
    private Double dfdjCollege;

    /**
    * 意识形态校级分
    */
    @TableField("ysxt_main")
    private Double ysxtMain;

    /**
    * 意识形态院级分
    */
    @TableField("ysxt_college")
    private Double ysxtCollege;

    /**
    * 劳动纪律校级分
    */
    @TableField("ldjl_main")
    private Double ldjlMain;

    /**
    * 劳动纪律院级分
    */
    @TableField("ldjl_college")
    private Double ldjlCollege;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

}