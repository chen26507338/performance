delete from sys_menu where pcode = 'majorBuild';
delete from sys_menu where id = 1296001749282439169;
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815681', 'majorBuildMember', 'assess_norm', '[0],[assess_norm],', '专业建设考核', '', '/majorBuildMember', '20', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815682', 'majorBuildMember_list', 'majorBuildMember', '[0],[assess_norm],[majorBuildMember],', '专业建设考核列表', '', '/majorBuildMember/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815683', 'majorBuildMember_add', 'majorBuildMember', '[0],[assess_norm],[majorBuildMember],', '专业建设考核添加', '', '/majorBuildMember/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815684', 'majorBuildMember_update', 'majorBuildMember', '[0],[assess_norm],[majorBuildMember],', '专业建设考核更新', '', '/majorBuildMember/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815685', 'majorBuildMember_delete', 'majorBuildMember', '[0],[assess_norm],[majorBuildMember],', '专业建设考核删除', '', '/majorBuildMember/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370657584063815686', 'majorBuildMember_detail', 'majorBuildMember', '[0],[assess_norm],[majorBuildMember],', '专业建设考核详情', '', '/majorBuildMember/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815681,1);
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815682,1);
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815683,1);
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815684,1);
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815685,1);
insert into sys_relation (`menuid`,`roleid`) values (1370657584063815686,1);

delete from sys_menu where id = 1226328464463880204;
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632840433665', 'rypzAssess', 'assess_norm', '[0],[assess_norm],', '人员配置考核', '', '/rypzAssess', '40', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632844627970', 'rypzAssess_list', 'rypzAssess', '[0],[assess_norm],[rypzAssess],', '人员配置考核列表', '', '/rypzAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632844627971', 'rypzAssess_add', 'rypzAssess', '[0],[assess_norm],[rypzAssess],', '人员配置考核添加', '', '/rypzAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632844627972', 'rypzAssess_update', 'rypzAssess', '[0],[assess_norm],[rypzAssess],', '人员配置考核更新', '', '/rypzAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632844627973', 'rypzAssess_delete', 'rypzAssess', '[0],[assess_norm],[rypzAssess],', '人员配置考核删除', '', '/rypzAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370736632844627974', 'rypzAssess_detail', 'rypzAssess', '[0],[assess_norm],[rypzAssess],', '人员配置考核详情', '', '/rypzAssess/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1370736632840433665,1);
insert into sys_relation (`menuid`,`roleid`) values (1370736632844627970,1);
insert into sys_relation (`menuid`,`roleid`) values (1370736632844627971,1);
insert into sys_relation (`menuid`,`roleid`) values (1370736632844627972,1);
insert into sys_relation (`menuid`,`roleid`) values (1370736632844627973,1);
insert into sys_relation (`menuid`,`roleid`) values (1370736632844627974,1);

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403712004097', 'manServiceMember', 'assess_norm', '[0],[assess_norm],', '管理服务成员', '', '/manServiceMember', '60', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403720392705', 'manServiceMember_list', 'manServiceMember', '[0],[assess_norm],[manServiceMember],', '管理服务成员列表', '', '/manServiceMember/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403720392706', 'manServiceMember_add', 'manServiceMember', '[0],[assess_norm],[manServiceMember],', '管理服务成员添加', '', '/manServiceMember/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403720392707', 'manServiceMember_update', 'manServiceMember', '[0],[assess_norm],[manServiceMember],', '管理服务成员更新', '', '/manServiceMember/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403720392708', 'manServiceMember_delete', 'manServiceMember', '[0],[assess_norm],[manServiceMember],', '管理服务成员删除', '', '/manServiceMember/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370742403720392709', 'manServiceMember_detail', 'manServiceMember', '[0],[assess_norm],[manServiceMember],', '管理服务成员详情', '', '/manServiceMember/detail', '99', '3', '0', NULL, '1', '0');
delete from sys_menu where pcode = 'manService';
delete from sys_menu where id = 1306513075398238210;
insert into sys_relation (`menuid`,`roleid`) values (1370742403712004097,1);
insert into sys_relation (`menuid`,`roleid`) values (1370742403720392705,1);
insert into sys_relation (`menuid`,`roleid`) values (1370742403720392706,1);
insert into sys_relation (`menuid`,`roleid`) values (1370742403720392707,1);
insert into sys_relation (`menuid`,`roleid`) values (1370742403720392708,1);
insert into sys_relation (`menuid`,`roleid`) values (1370742403720392709,1);

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370752502773952515', 'normalAssess_list', 'normalAssess', '[0],[assess_norm],[normalAssess],', '通用考核列表', '', '/normalAssess/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370752502773952516', 'normalAssess_add', 'normalAssess', '[0],[assess_norm],[normalAssess],', '通用考核添加', '', '/normalAssess/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370752502773952517', 'normalAssess_update', 'normalAssess', '[0],[assess_norm],[normalAssess],', '通用考核更新', '', '/normalAssess/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370752502773952518', 'normalAssess_delete', 'normalAssess', '[0],[assess_norm],[normalAssess],', '通用考核删除', '', '/normalAssess/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1370752502773952519', 'normalAssess_detail', 'normalAssess', '[0],[assess_norm],[normalAssess],', '通用考核详情', '', '/normalAssess/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1370752502773952515,1);
insert into sys_relation (`menuid`,`roleid`) values (1370752502773952516,1);
insert into sys_relation (`menuid`,`roleid`) values (1370752502773952517,1);
insert into sys_relation (`menuid`,`roleid`) values (1370752502773952518,1);
insert into sys_relation (`menuid`,`roleid`) values (1370752502773952519,1);
