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
    * 科研工作校级分
    */
    @TableField("kygz_main")
    private Double kygzMain;

    /**
    * 科研工作院级分
    */
    @TableField("kygz_college")
    private Double kygzCollege;

    /**
    * 专业建设校级分
    */
    @TableField("zyjs_main")
    private Double zyjsMain;

    /**
    * 专业建设院级分
    */
    @TableField("zyjs_college")
    private Double zyjsCollege;

    /**
    * 学生工作校级分
    */
    @TableField("xsgz_main")
    private Double xsgzMain;

    /**
    * 学生工作院级分
    */
    @TableField("xsgz_college")
    private Double xsgzCollege;

    /**
    * 管理服务校级分
    */
    @TableField("glfw_main")
    private Double glfwMain;

    /**
    * 管理服务院级分
    */
    @TableField("glfw_college")
    private Double glfwCollege;
    /**
    * 教学工作校级分
    */
    @TableField("jxgz_main")
    private Double jxgzMain;

    /**
    * 教学工作院级分
    */
    @TableField("jxgz_college")
    private Double jxgzCollege;

    /**
    * 党支部工作校级分
    */
    @TableField("dzbgz_main")
    private Double dzbgzMain;

    /**
    * 党支部工作院级分
    */
    @TableField("dzbgz_college")
    private Double dzbgzCollege;
    /**
    * 辅导员思政工作校级分
    */
    @TableField("fdyszgz_main")
    private Double fdyszgzMain;

    /**
    * 辅导员思政工作院级分
    */
    @TableField("fdyszgz_college")
    private Double fdyszgzCollege;

    /**
    * 思政教师思政工作校级分
    */
    @TableField("szjsszgz_main")
    private Double szjsszgzMain;

    /**
    * 思政教师思政工作院级分
    */
    @TableField("szjsszgz_college")
    private Double szjsszgzCollege;


    /**
    * 表彰荣誉思政工作校级分
    */
    @TableField("bzry_main")
    private Double bzryMain;

    /**
    * 表彰荣誉思政工作院级分
    */
    @TableField("bzry_college")
    private Double bzryCollege;
    /**
    * 辅导员日常工作校级分
    */
    @TableField("fdyrcgz_main")
    private Double fdyrcgzMain;

    /**
    * 辅导员日常工作院级分
    */
    @TableField("fdyrcgz_college")
    private Double fdyrcgzCollege;
    /**
    * 辅导员日常工作校级分
    */
    @TableField("sxjx_main")
    private Double sxjxMain;

    /**
    * 辅导员日常工作院级分
    */
    @TableField("sxjx_college")
    private Double sxjxCollege;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

}