INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
VALUES (1360783475540607977, 'gongzi', 'emp_money', '[0],[emp_money],', '工资', '', '#', 99, 2, 1, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540607977,1);
create table zbry_gz
(
    id bigint null,
    user_id bigint null comment '用户id',
    in_time datetime null comment '时间',
    gwgz double default 0 null comment '岗位工资',
    xjgz double default 0 null comment '薪级工资',
    gwjt double default 0 null comment '岗位津贴',
    shbt double default 0 null comment '生活补贴',
    tsbt double default 0 null comment '特殊补贴',
    tz double default 0 null comment '提租',
    bf double default 0 null comment '补发',
    yfs double default 0 null comment '应发数',
    kk double default 0 null comment '扣款',
    tzglbxj double default 0 null comment '调整各类保险金',
    gjj double default 0 null comment '公积金',
    yb double default 0 null comment '医保',
    ylj double default 0 null comment '养老金',
    zynj double default 0 null comment '职业年金',
    syj double default 0 null comment '失业金',
    hyf double default 0 null comment '会员费',
    fsf double default 0 null comment '房水费',
    sds double default 0 null comment '所得税',
    sfs double default 0 null comment '实发数',
    constraint zbry_gz_pk
        primary key (id)
)
    comment '在编人员工资';

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042935681026', 'zbryGz', 'gongzi', '[0],[emp_money],[gongzi],', '在编人员', '', '/zbryGz', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042939875330', 'zbryGz_list', 'zbryGz', '[0],[emp_money],[gongzi],[zbryGz],', '在编人员列表', '', '/zbryGz/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042939875331', 'zbryGz_add', 'zbryGz', '[0],[emp_money],[gongzi],[zbryGz],', '在编人员添加', '', '/zbryGz/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042939875332', 'zbryGz_update', 'zbryGz', '[0],[emp_money],[gongzi],[zbryGz],', '在编人员更新', '', '/zbryGz/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042939875333', 'zbryGz_delete', 'zbryGz', '[0],[emp_money],[gongzi],[zbryGz],', '在编人员删除', '', '/zbryGz/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365537042939875334', 'zbryGz_detail', 'zbryGz', '[0],[emp_money],[gongzi],[zbryGz],', '在编人员详情', '', '/zbryGz/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1365537042935681026,1);
insert into sys_relation (`menuid`,`roleid`) values (1365537042939875330,1);
insert into sys_relation (`menuid`,`roleid`) values (1365537042939875331,1);
insert into sys_relation (`menuid`,`roleid`) values (1365537042939875332,1);
insert into sys_relation (`menuid`,`roleid`) values (1365537042939875333,1);
insert into sys_relation (`menuid`,`roleid`) values (1365537042939875334,1);

create table pqry_gz
(
    id bigint null,
    user_id bigint null comment '用户id',
    in_time datetime null comment '时间',
    gz double default 0 null comment '工资',
    bfgzce double default 0 null comment '补发工资差额',
    kcj double default 0 null comment '扣产假',
    yfgz double default 0 null comment '应发工资',
    ylsyjs double default 0 null comment '养老失业基数',
    ybsyjs double default 0 null comment '医保生育基数',
    gjjjs double default 0 null comment '公积金基数',
    gsjs double default 0 null comment '工伤基数',
    yl double default 0 null comment '养老',
    sy double default 0 null comment '失业',
    yb double default 0 null comment '医保',
    gjj double default 0 null comment '公积金',
    hj double default 0 null comment '合计',
    sfgz double default 0 null comment '实发工资',
    constraint pqry_gz_pk
        primary key (id)
)
    comment '派遣人员工资';

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139265', 'pqryGz', 'gongzi', '[0],[emp_money],[gongzi],', '派遣人员', '', '/pqryGz', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139266', 'pqryGz_list', 'pqryGz', '[0],[emp_money],[gongzi],[pqryGz],', '派遣人员列表', '', '/pqryGz/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139267', 'pqryGz_add', 'pqryGz', '[0],[emp_money],[gongzi],[pqryGz],', '派遣人员添加', '', '/pqryGz/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139268', 'pqryGz_update', 'pqryGz', '[0],[emp_money],[gongzi],[pqryGz],', '派遣人员更新', '', '/pqryGz/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139269', 'pqryGz_delete', 'pqryGz', '[0],[emp_money],[gongzi],[pqryGz],', '派遣人员删除', '', '/pqryGz/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365646782848139270', 'pqryGz_detail', 'pqryGz', '[0],[emp_money],[gongzi],[pqryGz],', '派遣人员详情', '', '/pqryGz/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139265,1);
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139266,1);
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139267,1);
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139268,1);
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139269,1);
insert into sys_relation (`menuid`,`roleid`) values (1365646782848139270,1);

create table dlxpry_gz
(
    id bigint null,
    user_id bigint null comment '用户id',
    in_time datetime null comment '时间',
    jbgz double default 0 null comment '基本工资',
    jcxjx double default 0 null comment '基础性绩效',
    bfgzd double default 0 null comment '补发工资等',
    yfgz double default 0 null comment '应发工资',
    ylbx double default 0 null comment '养老保险',
    yl double default 0 null comment '医疗保险',
    sybx double default 0 null comment '失业保险',
    gjj double default 0 null comment '公积金',
    ghf double default 0 null comment '工会费',
    qtkk double default 0 null comment '其他扣款',
    sfs double default 0 null comment '实发数',
    constraint dlxpry_gz_pk
        primary key (id)
)
    comment '代理校聘人员工资';

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888125607938', 'dlxpryGz', 'gongzi', '[0],[emp_money],[gongzi],', '代理校聘人员', '', '/dlxpryGz', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888125607939', 'dlxpryGz_list', 'dlxpryGz', '[0],[emp_money],[gongzi],[dlxpryGz],', '代理校聘人员列表', '', '/dlxpryGz/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888125607940', 'dlxpryGz_add', 'dlxpryGz', '[0],[emp_money],[gongzi],[dlxpryGz],', '代理校聘人员添加', '', '/dlxpryGz/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888125607941', 'dlxpryGz_update', 'dlxpryGz', '[0],[emp_money],[gongzi],[dlxpryGz],', '代理校聘人员更新', '', '/dlxpryGz/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888129802241', 'dlxpryGz_delete', 'dlxpryGz', '[0],[emp_money],[gongzi],[dlxpryGz],', '代理校聘人员删除', '', '/dlxpryGz/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1365651888129802242', 'dlxpryGz_detail', 'dlxpryGz', '[0],[emp_money],[gongzi],[dlxpryGz],', '代理校聘人员详情', '', '/dlxpryGz/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1365651888125607938,1);
insert into sys_relation (`menuid`,`roleid`) values (1365651888125607939,1);
insert into sys_relation (`menuid`,`roleid`) values (1365651888125607940,1);
insert into sys_relation (`menuid`,`roleid`) values (1365651888125607941,1);
insert into sys_relation (`menuid`,`roleid`) values (1365651888129802241,1);
insert into sys_relation (`menuid`,`roleid`) values (1365651888129802242,1);

alter table normal_assess
    add jb varchar(255) null comment '级别';

alter table normal_assess
    add dc varchar(255) null comment '等次';
alter table teaching_load_assess
    add course_type varchar(255) null comment '课程类型';

alter table teaching_load_assess
    add course_times int default 0 null comment '课时数';

INSERT INTO `per`.`sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
 VALUES (1307583248381219820, 'teachingLoadAssess_import', 'teachingLoadAssess', '[0],[assess_norm],[teachingLoadAssess],', '教学考核导入', '', '/teachingLoadAssess/import', 99, 3, 0, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1307583248381219820,1);
INSERT INTO `per`.`sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
VALUES (1308993140152364481, 'dzbWorkAssess_import', 'dzbWorkAssess', '[0],[assess_norm],[dzbWorkAssess],', '党支部工作考核导入', '', '/dzbWorkAssess/import', 99, 3, 0, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1308993140152364481,1);
alter table dzb_work_assess
    add name varchar(255) null comment '名称';

alter table dzb_work_assess
    add zbdf double default 0 null comment '支部得分';
alter table dzb_work_assess
    add user_id bigint null comment '用户id';
alter table dzb_work_assess
    add coe_point double default 0 null comment '考核系数';

create table shpxgz_assess
(
    id bigint null,
    user_id bigint null comment '用户id',
    assess_name varchar(255) null comment '考核项目',
    name varchar(255) null comment '名称',
    num int default 0 null comment '数量',
    main_norm_point double null comment '校积分',
    coe_point double null comment '考核系数',
    year varchar(255) null comment '年度',
    constraint shpxgz_assess_pk
        primary key (id)
)
    comment '社会培训工作考核';

insert into assess_coefficient value ('shpxgz', '社会培训工作', 1);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161358848002', 'shpxgzAssess', 'assess_norm', '[0],[assess_norm],', '社会培训工作考核', '', '/shpxgzAssess', '110', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042305', 'shpxgzAssess_list', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核列表', '', '/shpxgzAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042306', 'shpxgzAssess_add', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核添加', '', '/shpxgzAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042307', 'shpxgzAssess_update', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核更新', '', '/shpxgzAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042308', 'shpxgzAssess_delete', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核删除', '', '/shpxgzAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042309', 'shpxgzAssess_detail', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核详情', '', '/shpxgzAssess/detail', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366412161363042310', 'shpxgzAssess_import', 'shpxgzAssess', '[0],[assess_norm],[shpxgzAssess],', '社会培训工作考核导入', '', '/shpxgzAssess/import', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1366412161358848002,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042305,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042306,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042307,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042308,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042309,1);
insert into sys_relation (`menuid`,`roleid`) values (1366412161363042310,1);
delete from sys_menu where id = 1226328464463880212;

alter table assess_norm_point
    add shpxgz_main double default 0 null comment '社会培训工作校积分';

alter table assess_norm_point
    add shpxgz_college double default 0 null comment '社会培训工作院级分';
