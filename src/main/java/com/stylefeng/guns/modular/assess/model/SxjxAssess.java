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
 * 实训绩效考核实体类
 *
 * @author 
 * @Date 2020-10-10 11:53:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sxjx_assess")
public class SxjxAssess extends BaseActEntity<SxjxAssess> {

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
     * 考核年度
     */
    @TableField("year")
    private String year;

    /**
    * 流程实例ID
    */
    @TableField("proc_ins_id")
    private String procInsId;


    /**
     * 职工编号
     */
    @TableField(exist = false)
    private String account;


    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 人事经办ID
    */
    @TableField("hr_handle_id")
    private Long hrHandleId;

    /**
    * 考核内容
    */
    @TableField("content")
    private String content;

    /**
    * 考核问题图片
    */
    @TableField("problem_url")
    private String problemUrl;

    /**
    * 整改内容
    */
    @TableField("result")
    private String result;

    /**
    * 院长ID
    */
    @TableField("dean_id")
    private Long deanId;

    /**
    * 实训绩效考核专员ID
    */
    @TableField("sxjx_commissioner")
    private Long sxjxCommissioner;

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
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 人事领导ID
    */
    @TableField("hr_leader_id")
    private Long hrLeaderId;

    /**
    * 实训室管理人员ID
    */
    @TableField("sxsgl_user")
    private Long sxsglUser;

    /**
    * 整改结果图片
    */
    @TableField("result_url")
    private String resultUrl;

    /**
    * 中心名称
    */
    @TableField("zxmc")
    private String zxmc;

    /**
    * 考核系数
    */
    @TableField("coe_point")
    private Double coePoint;

}