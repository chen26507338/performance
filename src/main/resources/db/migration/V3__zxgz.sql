INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318401', 'specialAssess', 'emp_money', '[0],[emp_money],', '专项工作奖项目列表', '', '/specialAssess', '80', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318402', 'specialAssess_list', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖项目列表列表', '', '/specialAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318403', 'specialAssess_add', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖项目列表添加', '', '/specialAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318404', 'specialAssess_update', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖项目列表更新', '', '/specialAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318405', 'specialAssess_delete', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖项目列表删除', '', '/specialAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318406', 'specialAssess_detail', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖项目列表详情', '', '/specialAssess/detail', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364877699977318407', 'specialAssess_importAssess', 'specialAssess', '[0],[emp_money],[specialAssess],', '专项工作奖考核导入', '', '/specialAssess/importAssess', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318401,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318402,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318403,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318404,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318405,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318406,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318407,1);
insert into sys_relation (`menuid`,`roleid`) values (1364877699977318407,12);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640130', 'specialAssessMember', 'assess_norm', '[0],[assess_norm],', '专项工作考核', '', '/specialAssessMember', '230', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640131', 'specialAssessMember_list', 'specialAssessMember', '[0],[assess_norm],[specialAssessMember],', '专项工作考核列表', '', '/specialAssessMember/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640132', 'specialAssessMember_add', 'specialAssessMember', '[0],[assess_norm],[specialAssessMember],', '专项工作考核添加', '', '/specialAssessMember/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640133', 'specialAssessMember_update', 'specialAssessMember', '[0],[assess_norm],[specialAssessMember],', '专项工作考核更新', '', '/specialAssessMember/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640134', 'specialAssessMember_delete', 'specialAssessMember', '[0],[assess_norm],[specialAssessMember],', '专项工作考核删除', '', '/specialAssessMember/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1364882147646640135', 'specialAssessMember_detail', 'specialAssessMember', '[0],[assess_norm],[specialAssessMember],', '专项工作考核详情', '', '/specialAssessMember/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640130,1);
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640131,1);
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640132,1);
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640133,1);
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640134,1);
insert into sys_relation (`menuid`,`roleid`) values (1364882147646640135,1);
delete from sys_menu where id = 1226328464463880219;
alter table special_assess change user_id dept_id bigint null comment '部门ID';

alter table special_assess change point apply_point double default 0 null comment '申请积分';

alter table special_assess change money add_point double default 0 null comment '增加积分';

alter table special_assess
	add reference_point double default 0 null comment '申请参考分';

alter table special_assess
	add is_jr tinyint null comment '是否计入';

alter table special_assess
	add is_yjkh tinyint null comment '是否记入部门优绩考核';
alter table special_assess
    add is_import tinyint default 0 null comment '是否导入';

alter table special_assess
	add project_content varchar(255) null comment '申请项目内容';
alter table special_assess
	add zx_no varchar(255) null comment '编号';
alter table special_assess
    add type varchar(255) null comment '项目分类';

alter table assess_norm_point
    add zxgz_main double default 0 null comment '专项工作校积分';

alter table assess_norm_point
    add zxgz_college double default 0 null comment '专项工作院级分';
insert into assess_coefficient value ('zxgz', '专项工作', 1);

create table special_assess_member
(
	id bigint not null,
	user_id bigint null comment '用户id',
	sa_id bigint null comment '专项项目id',
	point double null comment '积分',
	money double null comment '薪酬',
	coe_point double null comment '考核系数',
	year varchar(50) null comment '年度',
	constraint special_assess_member_pk
		primary key (id)
)
comment '专项工作积分分配';



