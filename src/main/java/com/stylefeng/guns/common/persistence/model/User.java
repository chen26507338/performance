package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseModel<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id")
	private Long id;
    /**
     * 头像
     */
	private String avatar;
    @TableField(exist = false)
    private String pwdCheck;
    /**
     * 账号
     */
	private String account;
    /**
     * 密码
     */
	private String password;
    /**
     * md5密码盐
     */
	private String salt;
    /**
     * 名字
     */
	private String name;
    /**
     * 生日
     */
	private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
	private Integer sex;
    /**
     * 年龄
     */
	private Integer age;
    /**
     * 电子邮件
     */
	private String email;
    /**
     * 电话
     */
	private String phone;
    /**
     * qq
     */
	private String qq;
    /**
     * 微信
     */
	private String wechat;
    /**
     * 身份证号码
     */
	private String idCard;
    /**
     * 现居住地
     */
	private String address;
    /**
     * 籍贯
     */
	private String nativePlace;
    /**
     * 出生地
     */
	private String birthplace;
    /**
     * 联系电话
     */
	private String mobile;
    /**
     * 紧急联系人
     */
	private String emergencyContact;
    /**
     * 紧急联系人联系电话
     */
	private String emergencyMobile;
    /**
     * 紧急联系人关系
     */
	private Integer emergencyRelation;
    /**
     * 退休日期
     */
	private Date retireDate;
    /**
     * 用工类型
     */
	private Integer jobType;
    /**
     * 党派
     */
	private String partyGroupings;
    /**
     * 角色id
     */
	private String roleId;

    /**
     * 部门id
     */
	private Long deptId;
    /**
     * 岗位id
     */
	private Long jobId;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
	private Integer status;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 保留字段
     */
	private Integer version;
	/**
     * 人员状态
     */
	private Integer personalState;

    /**
     * 民族
     */
    @TableField("nation")
    private String nation;
    /**
     * 健康状况
     */
    @TableField("health_condition")
    private String healthCondition;
    /**
     * 专业技术职务
     */
    @TableField("pro_posts")
    private String proPosts;
    /**
     * 熟悉专业有何专长
     */
    @TableField("major_speciality")
    private String majorSpeciality;

    /**
     * 入党时间
     */
    @TableField("join_party_time")
    private Date joinPartyTime;

    /**
     * 参加工作时间
     */
    @TableField("first_work_time")
    private Date firstWorkTime;
    /**
     * 学历
     */
    @TableField("education")
    private String education;
    /**
     * 学位
     */
    @TableField("degree")
    private String degree;
    /**
     * 职务
     */
    @TableField("post")
    private String post;
    /**
     * 职级
     */
    @TableField("zhi_ji")
    private String zhiJi;
    /**
     * 职称
     */
    @TableField("zhi_chen")
    private String zhiChen;
    /**
     * 职称等级
     */
    @TableField("zc_level")
    private String zcLevel;
    /**
     * 专业岗位
     */
    @TableField("zy_job")
    private String zyJob;
    /**
     * 毕业院校
     */
    @TableField("college")
    private String college;
    /**
     * 专业
     */
    @TableField("major")
    private String major;
    /**
     * 工龄起算时间
     */
    @TableField("gl_sj")
    private String glSj;
    /**
     * 岗位类别
     */
    @TableField("gw_type")
    private String gwType;
    /**
     * 到校工作时间
     */
    @TableField("dx_sj")
    private String dxSj;
    /**
     * 参保时间
     */
    @TableField("cb_sj")
    private String cbSj;
    /**
     * 工资职级
     */
    @TableField("gzzj")
    private String gzzj;
    /**
     * 聘期起始时间
     */
    @TableField("pqqs")
    private String pqqs;
    /**
     * 薪酬设置id
     */
    @TableField("pays_id")
    private Long paysId;

    /**
     * 个人身份
     */
    @TableField("grsf")
    private String grsf;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
