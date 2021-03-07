INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (1360783475540607977, 'gongzi', 'emp_money', '[0],[emp_money],', '工资', '', '#', 99, 2, 1, NULL, 1, 0);
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

INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
 VALUES (1307583248381219820, 'teachingLoadAssess_import', 'teachingLoadAssess', '[0],[assess_norm],[teachingLoadAssess],', '教学考核导入', '', '/teachingLoadAssess/import', 99, 3, 0, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1307583248381219820,1);
INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
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
insert into assess_coefficient value ('jfwcqk', '经费完成情况', 1);
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
alter table assess_norm_point
    add jfwcqk_main double default 0 null comment '经费完成情况校积分';
alter table assess_norm_point
    add jfwcqk_college double default 0 null comment '经费完成情况院级分';

create table jfwcqk_assess
(
    id bigint null,
    user_id bigint null comment '用户id',
    assess_name varchar(255) null comment '经费项目',
    jfwcf varchar(255) null comment '经费完成费',
    main_norm_point double null comment '校级分',
    year varchar(255) null comment '年度',
    coe_point double default 1 null comment '考核系数',
    constraint jfwcqk_assess_pk
        primary key (id)
)
    comment '经费完成情况考核';
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738561', 'jfwcqkAssess', 'assess_norm', '[0],[assess_norm],', '经费完成情况考核', '', '/jfwcqkAssess', '100', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738562', 'jfwcqkAssess_list', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核列表', '', '/jfwcqkAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738563', 'jfwcqkAssess_add', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核添加', '', '/jfwcqkAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738564', 'jfwcqkAssess_update', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核更新', '', '/jfwcqkAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738565', 'jfwcqkAssess_delete', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核删除', '', '/jfwcqkAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738566', 'jfwcqkAssess_detail', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核详情', '', '/jfwcqkAssess/detail', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366650837900738567', 'jfwcqkAssess_import', 'jfwcqkAssess', '[0],[assess_norm],[jfwcqkAssess],', '经费完成情况考核详情', '', '/jfwcqkAssess/import', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738561,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738562,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738563,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738564,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738565,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738566,1);
insert into sys_relation (`menuid`,`roleid`) values (1366650837900738567,1);
delete from sys_menu where id = 1226328464463880214;
alter table sxjx_assess
    add zxmc varchar(255) null comment '中心名称';
INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (1314774586021253127, 'sxjxAssess_import', 'sxjxAssess', '[0],[assess_norm],[sxjxAssess],', '实训绩效考核导入', '', '/sxjxAssess/import', 99, 3, 0, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1314774586021253127,1);
alter table sxjx_assess
    add user_id bigint null comment '用户id';
alter table stu_work_member
    add assess_name varchar(255) null comment '考核项目';

alter table stu_work_member
    add result int null comment '人数/次数';

alter table stu_work_member
    add mixture varchar(255) null comment '参赛项目名称/团队名称/类别/百分率';
delete from sys_relation where id = 14450;
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068409487362', 'stuWorkMember', 'assess_norm', '[0],[assess_norm],', '学生工作考核', '', '/stuWorkMember', '50', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681666', 'stuWorkMember_list', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员列表', '', '/stuWorkMember/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681667', 'stuWorkMember_add', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员添加', '', '/stuWorkMember/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681668', 'stuWorkMember_update', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员更新', '', '/stuWorkMember/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681669', 'stuWorkMember_delete', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员删除', '', '/stuWorkMember/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681670', 'stuWorkMember_detail', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员详情', '', '/stuWorkMember/detail', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1366707068413681671', 'stuWorkMember_import', 'stuWorkMember', '[0],[assess_norm],[stuWorkMember],', '学生工作成员导入', '', '/stuWorkMember/import', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1366707068409487362,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681666,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681667,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681668,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681669,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681670,1);
insert into sys_relation (`menuid`,`roleid`) values (1366707068413681671,1);
INSERT INTO `sys_menu`(`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (1360783475540607978, 'ryjsj', 'emp_money', '[0],[emp_money],', '荣誉（竞赛）奖', '', '#', 98, 2, 1, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (1360783475540607978,1);
create table js_award
(
    id bigint null,
    user_id bigint null comment '用户id',
    yrxs varchar(255) null comment '用人形式',
    project varchar(255) null comment '项目',
    hjlb varchar(255) null comment '获奖类别',
    hjdj varchar(255) null comment '获奖等级',
    money varchar(255) null comment '金额',
    year varchar(255) null comment '年度',
    type varchar(255) null comment '类型',
    constraint js_award_pk
        primary key (id)
)
    comment '竞赛奖励';
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109954', 'jsAward', 'ryjsj', '[0],[emp_money],[ryjsj],', '竞赛奖励', '', '/jsAward?type=jsjl', '1', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109955', 'ryjl', 'ryjsj', '[0],[emp_money],[ryjsj],', '荣誉奖励', '', '/jsAward?type=ryjl', '2', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109956', 'tcgxjl', 'ryjsj', '[0],[emp_money],[ryjsj],', '突出贡献奖励', '', '/jsAward?type=tcgxjl', '3', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109957', 'shfwj', 'emp_money', '[0],[emp_money],', '社会服务奖', '', '/jsAward?type=shfwj', 100, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109958', 'zxgzj', 'emp_money', '[0],[emp_money],', '专项工作奖', '', '/jsAward?type=zxgzj', 101, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109959', 'ndyjkhj', 'emp_money', '[0],[emp_money],', '年度优绩考核奖', '', '#', 102, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109960', 'gryjkhj', 'ndyjkhj', '[0],[emp_money],', '个人优绩考核奖', '', '/jsAward?type=gryjkhj', 1, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109961', 'bmyjkhj', 'ndyjkhj', '[0],[emp_money],', '部门优绩考核奖', '', '/jsAward?type=bmyjkhj', 2, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109962', 'tsjl', 'emp_money', '[0],[emp_money],', '特殊奖励', '', '#', 103, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109963', 'zzszjsbt', 'tsjl', '[0],[emp_money],', '专职思政教师补贴', '', '/jsAward?type=zzszjsbt', 1, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109964', 'zzfdybt', 'tsjl', '[0],[emp_money],', '专职辅导员补贴', '', '/jsAward?type=zzfdybt', 2, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210234109965', 'jxwxc', 'emp_money', '[0],[emp_money],', '绩效外薪酬', '', '/jsAward?type=jxwxc', 104, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210238304257', 'jsAward_list', 'jsAward', '[0],[emp_money],[ryjsj],[jsAward],', '竞赛奖励列表', '', '/jsAward/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210238304258', 'jsAward_add', 'jsAward', '[0],[emp_money],[ryjsj],[jsAward],', '竞赛奖励添加', '', '/jsAward/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210238304259', 'jsAward_update', 'jsAward', '[0],[emp_money],[ryjsj],[jsAward],', '竞赛奖励更新', '', '/jsAward/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210238304260', 'jsAward_delete', 'jsAward', '[0],[emp_money],[ryjsj],[jsAward],', '竞赛奖励删除', '', '/jsAward/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1367426210238304261', 'jsAward_detail', 'jsAward', '[0],[emp_money],[ryjsj],[jsAward],', '竞赛奖励详情', '', '/jsAward/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109954,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109955,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109956,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109957,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109958,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109959,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109960,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109961,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109962,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109963,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109964,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210234109965,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210238304257,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210238304258,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210238304259,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210238304260,1);
insert into sys_relation (`menuid`,`roleid`) values (1367426210238304261,1);

