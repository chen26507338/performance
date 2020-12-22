package com.stylefeng.guns.modular.assess.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.common.persistence.model.BaseActEntity;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 教师考核实体类
 *
 * @author 
 * @Date 2020-12-21 22:58:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("year_js_assess")
public class YearJsAssess extends BaseActEntity<YearJsAssess> {

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
    * 流程实例ID
    */
    @TableField("proc_ins_id")
    private String procInsId;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 年份
    */
    @TableField("year")
    private String year;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 备注
    */
    @TableField("remark")
    private String remark;

    /**
    * 考核等次
    */
    @TableField("level")
    private String level;

    /**
    * 教研室主任
    */
    @TableField("jyszr_user")
    private Long jyszrUser;

    /**
    * 支部书记
    */
    @TableField("zbsj_user")
    private Long zbsjUser;

    /**
    * 院长
    */
    @TableField("dean_user")
    private Long deanUser;

    /**
    * 综办主任
    */
    @TableField("zbzr_user")
    private Long zbzrUser;

    /**
    * 科研工作
    */
    @TableField("kygz")
    private String kygz;

    /**
    * 评语
    */
    @TableField("comments")
    private String comments;

    /**
    * 实验室工作
    */
    @TableField("sysgz")
    private String sysgz;

}