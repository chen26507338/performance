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
 * 自然信息实体类
 *
 * @author cp
 * @Date 2020-07-06 10:38:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("personal_info")
public class PersonalInfo extends BaseActEntity<PersonalInfo> {

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
    * qq
    */
    @TableField("qq")
    private String qq;

    /**
    * 微信
    */
    @TableField("wechat")
    private String wechat;

    /**
    * 身份证号码
    */
    @TableField("id_card")
    private String idCard;

    /**
    * 年龄
    */
    @TableField("age")
    private Integer age;

    /**
    * 现居住地
    */
    @TableField("address")
    private String address;

    /**
    * 籍贯
    */
    @TableField("native_place")
    private String nativePlace;

    /**
    * 出生地
    */
    @TableField("birthplace")
    private String birthplace;

    /**
    * 退休日期
    */
    @TableField("retire_date")
    private Date retireDate;

    /**
    * 党派
    */
    @TableField("party_groupings")
    private String partyGroupings;

    /**
    * 用工类型
    */
    @TableField("job_type")
    private Integer jobType;

    /**
    * 联系电话1
    */
    @TableField("mobile")
    private String mobile;

    /**
    * 紧急联系人
    */
    @TableField("emergency_contact")
    private String emergencyContact;

    /**
    * 紧急联系人关系
    */
    @TableField("emergency_relation")
    private Integer emergencyRelation;

    /**
    * 紧急联系人联系电话
    */
    @TableField("emergency_mobile")
    private String emergencyMobile;

    /**
    * 电子邮件
    */
    @TableField("email")
    private String email;

    /**
    * 名字
    */
    @TableField("name")
    private String name;

    /**
    * 出生年月
    */
    @TableField("birthday")
    private Date birthday;

    /**
    * 性别
    */
    @TableField("sex")
    private Integer sex;

    /**
    * 联系电话2
    */
    @TableField("phone")
    private String phone;

    /**
    * 人员状态
    */
    @TableField("personal_state")
    private Integer personalState;

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