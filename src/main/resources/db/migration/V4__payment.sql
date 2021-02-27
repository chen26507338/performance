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

