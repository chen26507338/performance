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
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606977,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606978,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606979,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606980,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606981,1);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540606982,1);

delete from sys_menu where id = 1226328464463880225;

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773634', 'postSetting', 'college', '[0],[college],', '职务设置', '', '/postSetting', '90', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773635', 'postSetting_list', 'postSetting', '[0],[college],[postSetting],', '职务设置列表', '', '/postSetting/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773636', 'postSetting_add', 'postSetting', '[0],[college],[postSetting],', '职务设置添加', '', '/postSetting/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773637', 'postSetting_update', 'postSetting', '[0],[college],[postSetting],', '职务设置更新', '', '/postSetting/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773638', 'postSetting_delete', 'postSetting', '[0],[college],[postSetting],', '职务设置删除', '', '/postSetting/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305336648773639', 'postSetting_detail', 'postSetting', '[0],[college],[postSetting],', '职务设置详情', '', '/postSetting/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773634,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773635,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773636,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773637,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773638,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305336648773639,1);

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534977', 'deptPost', 'college', '[0],[college],', '机构职务配置', '', '/deptPost', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534978', 'deptPost_list', 'deptPost', '[0],[college],[deptPost],', '机构职务配置列表', '', '/deptPost/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534979', 'deptPost_add', 'deptPost', '[0],[college],[deptPost],', '机构职务配置添加', '', '/deptPost/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534980', 'deptPost_update', 'deptPost', '[0],[college],[deptPost],', '机构职务配置更新', '', '/deptPost/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534981', 'deptPost_delete', 'deptPost', '[0],[college],[deptPost],', '机构职务配置删除', '', '/deptPost/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1362305938577534982', 'deptPost_detail', 'deptPost', '[0],[college],[deptPost],', '机构职务配置详情', '', '/deptPost/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534977,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534978,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534979,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534980,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534981,1);
insert into sys_relation (`menuid`,`roleid`) values (1362305938577534982,1);

alter table normal_assess
	add type varchar(100) null comment '类型';

alter table normal_assess
	add assess_name varchar(100) null comment '考核项目';

alter table normal_assess
    add main_point double null comment '校积分';

create table rypz_assess
(
	id bigint null,
	user_id bigint null comment '用户ID',
	assess_name varchar(100) null comment '考核项目',
	main_point double null comment '校积分',
	year varchar(100) null comment '年度',
	constraint rypz_assess_pk
		primary key (id)
)
comment '人员配置考核';

update sys_menu set url = '/rypzAssess' where id = 1226328464463880204;

alter table assess_norm_point
    add rypz_main double default 0 null comment '人员配置校级分';

alter table assess_norm_point
    add rypz_college double default 0 null comment '人员配置院级分';

insert into assess_coefficient value ('rypz', '人员配置', 1);

alter table scientific_project
    add assess_name varchar(255) null comment '考核项目';

INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
VALUES (1226328464463880319, 'kykh', 'assess_norm', '[0],[assess_norm],', '科研考核', '', '/scientificProject/index', 230, 2, 1, NULL, 1, NULL);
insert into sys_relation (`menuid`,`roleid`) values (1226328464463880319,1);
