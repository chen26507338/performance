package com.stylefeng.guns.modular.user.model;

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
 * 科研论著实体类
 *
 * @author cp
 * @Date 2020-07-02 10:13:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scientific_treatise")
public class ScientificTreatise extends BaseActEntity<ScientificTreatise> {

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
     * 发表时间
     */
    @TableField("publish_date")
    private String publishDate;

    /**
     * 论文题目
     */
    @TableField("title")
    private String title;

    /**
     * 刊物名称
     */
    @TableField("periodical_name")
    private String periodicalName;

    /**
     * 作者顺序
     */
    @TableField("author_order")
    private String authorOrder;

    /**
     * 刊物级别
     */
    @TableField("periodical_level")
    private String periodicalLevel;

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
     * 校级指标分
     */
    @TableField("main_norm_point")
    private Double mainNormPoint;


    /**
     * 考核系数
     */
    @TableField("coe_point")
    private Double coePoint;

    /**
     * 院级浮动值
     */
    @TableField("college_norm_point")
    private Double collegeNormPoint;

    /**
     * 考核结果
     */
    @TableField("result")
    private Integer result;

    /**
     * 年度
     */
    @TableField("year")
    private String year;

    /**
     * 指标ID
     */
    @TableField("norm_id")
    private Long normId;

    /**
     * 人事经办ID
     */
    @TableField("hr_handle_id")
    private Long hrHandleId;

    /**
     * 部门领导ID
     */
    @TableField("dept_leader_id")
    private Long deptLeaderId;

    /**
     * 人事领导ID
     */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
     * 科技处专员ID
     */
    @TableField("sci_commissioner")
    private Long sciCommissioner;

    /**
     * 科技处领导ID
     */
    @TableField("sci_leader_id")
    private Long sciLeaderId;

    @TableField(exist = false)
    private String normCode;
}