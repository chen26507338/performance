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

