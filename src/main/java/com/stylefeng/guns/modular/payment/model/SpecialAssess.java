package com.stylefeng.guns.modular.payment.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 专项工作奖项目列表实体类
 *
 * @author 
 * @Date 2021-02-25 18:10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("special_assess")
public class SpecialAssess extends BaseModel<SpecialAssess> {

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
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 积分百分比
    */
    @TableField("point_percent")
    private Double pointPercent;

    /**
    * 申请积分
    */
    @TableField("apply_point")
    private Double applyPoint;

    /**
    * 增加积分
    */
    @TableField("add_point")
    private Double addPoint;

    /**
    * 备注
    */
    @TableField("remark")
    private String remark;

    /**
    * 申请时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 是否全年
    */
    @TableField("is_all_point")
    private Integer isAllPoint;

    /**
    * 状态
    */
    @TableField("status")
    private Integer status;

    /**
    * 申请参考分
    */
    @TableField("reference_point")
    private Double referencePoint;

    /**
    * 是否计入
    */
    @TableField("is_jr")
    private Integer isJr;

    /**
    * 是否记入部门优绩考核
    */
    @TableField("is_yjkh")
    private Integer isYjkh;

    /**
    * 是否导入
    */
    @TableField("is_import")
    private Integer isImport;

    /**
    * 申请项目内容
    */
    @TableField("project_content")
    private String projectContent;

    /**
    * 编号
    */
    @TableField("zx_no")
    private String zxNo;
    /**
    * 项目分类
    */
    @TableField("type")
    private String type;

}