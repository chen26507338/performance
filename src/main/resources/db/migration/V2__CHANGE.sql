create table pay_setting
(
	id bigint null,
	name varchar(255) null comment '名称',
	money double null comment '薪酬',
	constraint pay_setting_pk
		primary key (id)
)
comment '薪酬设置';

alter table sys_user
	add pays_id bigint null comment '薪酬设置id';

create table post_setting
(
	id bigint null,
	ldks varchar(255) null comment '领导科室',
	zj varchar(255) null comment '职级',
	zw varchar(255) null comment '职务',
	constraint post_setting_pk
		primary key (id)
)
comment '职务设置';

create table dept_post
(
	id bigint null,
	dept_id bigint null comment '部门id',
	post_id bigint null comment '职务id',
	user_id bigint null comment '用户id',
	is_db tinyint null comment '是否定编',
	is_star tinyint null comment '是否星号',
	constraint dept_post_pk
		primary key (id)
)
comment '部门职务';

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606977', 'paySetting', 'emp_money', '[0],[emp_money],', '薪酬设置', '', '/paySetting', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606978', 'paySetting_list', 'paySetting', '[0],[emp_money],[paySetting],', '薪酬设置列表', '', '/paySetting/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606979', 'paySetting_add', 'paySetting', '[0],[emp_money],[paySetting],', '薪酬设置添加', '', '/paySetting/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606980', 'paySetting_update', 'paySetting', '[0],[emp_money],[paySetting],', '薪酬设置更新', '', '/paySetting/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606981', 'paySetting_delete', 'paySetting', '[0],[emp_money],[paySetting],', '薪酬设置删除', '', '/paySetting/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1360783475540606982', 'paySetting_detail', 'paySetting', '[0],[emp_money],[paySetting],', '薪酬设置详情', '', '/paySetting/detail', '99', '3', '0', NULL, '1', '0');

delete from sys_menu where id = 1226328464463880225;

insert into sys_relation (`menuid`,`roleid`) values (1360783475540606977,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606978,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606979,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606980,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606981,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606982,1);