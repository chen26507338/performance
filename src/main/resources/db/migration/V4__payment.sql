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
