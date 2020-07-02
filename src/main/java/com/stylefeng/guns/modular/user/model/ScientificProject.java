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

}