package com.stylefeng.guns.modular.post.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 职务设置实体类
 *
 * @author 
 * @Date 2021-02-18 15:39:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("post_setting")
public class PostSetting extends BaseModel<PostSetting> {

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
    * 领导科室
    */
    @TableField("ldks")
    private String ldks;

    /**
    * 职级
    */
    @TableField("zj")
    private String zj;

    /**
    * 职务
    */
    @TableField("zw")
    private String zw;

}