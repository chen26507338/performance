package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 其他设置实体类
 *
 * @author 
 * @Date 2018-03-15 11:49:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("other_info")
public class OtherInfo extends BaseModel<OtherInfo> {
    /**
    * 
    */
    @TableId("ID")
    private Long id;

    /**
    * 键名
    */
    @TableField("OTHER_KEY")
    private String otherKey;

    /**
    * 值
    */
    @TableField("OTHER_VALUE")
    private String otherValue;

    /**
    * 备注
    */
    @TableField("BEIZHU")
    private String beizhu;

    /**
    * 排序
    */
    @TableField("sorts")
    private String sorts;

}