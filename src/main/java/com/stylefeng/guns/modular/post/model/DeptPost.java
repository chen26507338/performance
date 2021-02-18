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
 * 机构职务配置实体类
 *
 * @author 
 * @Date 2021-02-18 15:45:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dept_post")
public class DeptPost extends BaseModel<DeptPost> {

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
    * 部门id
    */
    @TableField("dept_id")
    private Long deptId;

    /**
    * 职务id
    */
    @TableField("post_id")
    private Long postId;

    /**
    * 用户id
    */
    @TableField("user_id")
    private Long userId;

    /**
    * 是否定编
    */
    @TableField("is_db")
    private Integer isDb;

    /**
    * 是否星号
    */
    @TableField("is_star")
    private Integer isStar;

}