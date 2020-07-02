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

}