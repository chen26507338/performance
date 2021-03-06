create table pay_setting
(
	id bigint not null,
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
	id bigint not null,
	ldks varchar(255) null comment '领导科室',
	zj varchar(255) null comment '职级',
	zw varchar(255) null comment '职务',
	constraint post_setting_pk
		primary key (id)
)
comment '职务设置';

create table dept_post
(
	id bigint not null,
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
	id bigint not null,
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
alter table assess_norm_point
    add jshj_main double default 0 null comment '竞赛获奖校积分';

alter table assess_norm_point
    add jshj_college double default 0 null comment '竞赛获奖院级分';

insert into assess_coefficient value ('rypz', '人员配置', 1);
insert into assess_coefficient value ('jshj', '竞赛获奖', 1);

alter table scientific_project
    add assess_name varchar(255) null comment '考核项目';

INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
VALUES (1226328464463880319, 'kykh', 'assess_norm', '[0],[assess_norm],', '科研考核', '', '/scientificProject/index', 230, 2, 1, NULL, 1, NULL);
insert into sys_relation (`menuid`,`roleid`) values (1226328464463880319,1);

alter table man_service_member
    add assess_name varchar(255) null comment '考核项目';

alter table man_service_member
    add project_name varchar(255) null comment '项目名称';

update sys_menu set url = '/manServiceMember' where id = 1306513075398238210;

alter table major_build_member
    add assess_name varchar(255) null comment '考核项目';

alter table major_build_member
    add type varchar(255) null comment '分类';

alter table major_build_member
    add build_name varchar(255) null comment '名称';

alter table major_build_member
    add `rank` varchar(255) null comment '排名';

alter table major_build_member
    add start_time varchar(255) null comment '立项时间';

alter table major_build_member
    add build_time varchar(255) null comment '建设时间';

alter table major_build_member modify
    status varchar(255) default 0 null comment '状态';

alter table major_build_member
    add remark varchar(255) null comment '备注';

update sys_menu set url = '/MajorBuildMember' where id = 1296001749282439169;

create table jshj_assess
(
    id bigint not null,
    type varchar(255) null comment '竞赛类别',
    js_name varchar(255) null comment '竞赛名称',
    sx_name varchar(255) null comment '赛项名称',
    js_level varchar(255) null comment '竞赛级别',
    hj_level varchar(255) null comment '获奖等级',
    main_norm_point double null comment '校级积分',
    js_type varchar(255) null comment '参赛/指导/管理',
    year varchar(255) null comment '年度',
    user_id bigint null comment '用户id',
    coe_point double null comment '考核系数',
    constraint jshj_assess_pk
        primary key (id)
)
    comment '竞赛获奖';

delete from sys_menu where id = 1226328464463880207;

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255553', 'jshjAssess', 'assess_norm', '[0],[assess_norm],', '竞赛获奖考核', '', '/jshjAssess', '70', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255554', 'jshjAssess_list', 'jshjAssess', '[0],[assess_norm],[jshjAssess],', '竞赛获奖列表', '', '/jshjAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255555', 'jshjAssess_add', 'jshjAssess', '[0],[assess_norm],[jshjAssess],', '竞赛获奖添加', '', '/jshjAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255556', 'jshjAssess_update', 'jshjAssess', '[0],[assess_norm],[jshjAssess],', '竞赛获奖更新', '', '/jshjAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255557', 'jshjAssess_delete', 'jshjAssess', '[0],[assess_norm],[jshjAssess],', '竞赛获奖删除', '', '/jshjAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364450798255255558', 'jshjAssess_detail', 'jshjAssess', '[0],[assess_norm],[jshjAssess],', '竞赛获奖详情', '', '/jshjAssess/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255553,1);
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255554,1);
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255555,1);
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255556,1);
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255557,1);
insert into sys_relation (`menuid`,`roleid`) values (1364450798255255558,1);
