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
 *  考核指标库实体类
 *
 * @author 
 * @Date 2020-02-02 13:38:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("assess_norm")
public class AssessNorm extends BaseModel<AssessNorm> {

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
    * 内容
    */
    @TableField("content")
    private String content;

    /**
    * 代码
    */
    @TableField("code")
    private String code;

    /**
    * 考核项目
    */
    @TableField("type")
    private String type;

    /**
    * 部门ID
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 校级指标/院级浮动值
    */
    @TableField("point")
    private Double point;

    /**
    * 描述
    */
    @TableField("des")
    private String des;

}