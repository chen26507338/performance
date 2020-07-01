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
 * 亲属关系实体类
 *
 * @author cp
 * @Date 2020-06-30 17:27:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kinship")
public class Kinship extends BaseActEntity<Kinship> {

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
    * 关系
    */
    @TableField("relationship")
    private String relationship;

    /**
    * 关系
    */
    @TableField(exist = false)
    private String relationshipDict;

    /**
    * 姓名
    */
    @TableField("name")
    private String name;

    /**
    * 生日
    */
    @TableField("birthday")
    private String birthday;

    /**
    * 年龄
    */
    @TableField("age")
    private Integer age;

    /**
    * 政治面貌
    */
    @TableField("politics_status")
    private Integer politicsStatus;
    /**
    * 政治面貌
    */
    @TableField(exist = false)
    private String politicsStatusDict;

    /**
    * 工作单位及职务
    */
    @TableField("company")
    private String company;

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
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;

}