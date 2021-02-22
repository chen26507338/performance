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
 * 科研项目实体类
 *
 * @author cp
 * @Date 2020-07-02 12:39:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scientific_project")
public class ScientificProject extends BaseActEntity<ScientificProject> {

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
    * 课题名称
    */
    @TableField("name")
    private String name;

    /**
    * 课题性质
    */
    @TableField("nature")
    private String nature;

    /**
    * 课题项目类别
    */
    @TableField("type")
    private String type;

    /**
    * 开题时间
    */
    @TableField("start_time")
    private String startTime;

    /**
    * 考核项目
    */
    @TableField("assess_name")
    private String assessName;

    /**
     * 职工编号
     */
    @TableField(exist = false)
    private String account;


    /**
    * 结题时间
    */
    @TableField("end_time")
    private String endTime;

    /**
    * 到账经费
    */
    @TableField("expenditure")
    private String expenditure;

    /**
    * 排名
    */
    @TableField("rank")
    private String rank;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 
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

    @TableField(exist = false)
    private String normName;

}