package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
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
     * 电子邮件
     */
	private String email;
    /**
     * 电话
     */
	private String phone;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
