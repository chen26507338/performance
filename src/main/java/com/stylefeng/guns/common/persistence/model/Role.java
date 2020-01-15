package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
@Data
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 序号
     */
	private Integer num;
    /**
     * 父角色id
     */
	private Long pid;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 提示
     */
	private String tips;
    /**
     * 保留字段(暂时没用）
     */
	private Integer version;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
