CREATE TABLE `banner`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BANNER_NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BANNER_IMGURL` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CONTENT` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `BANNER_ORDER` int(11) NULL DEFAULT NULL,
  `IS_GO` int(1) NULL DEFAULT NULL COMMENT '是否跳转',
  `STATUS` int(1) NULL DEFAULT NULL COMMENT '0创建 1正常（申请通过',
  `BANNER_PLACE` int(1) NULL DEFAULT NULL COMMENT '1app 2网站首页',
  `CREATE_TIME` datetime NULL DEFAULT NULL,
  `URL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `banner_name_en` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称（英文）',
  `banner_imgurl_en` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片（英文）',
  `content_en` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容（英文）',
  `banner_name_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称（印尼）',
  `banner_imgurl_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片（印尼）',
  `content_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容（印尼）',
  `banner_name_my` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称（马来）',
  `banner_imgurl_my` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片（马来）',
  `content_my` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容（马来）',
  `banner_name_vi` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称（越南）',
  `banner_imgurl_vi` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片（越南）',
  `content_vi` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容（越南）',
  `banner_name_tc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称（繁体）',
  `banner_imgurl_tc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片（繁体）',
  `content_tc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容（繁体）',
  PRIMARY KEY (`ID`) USING BTREE
) ;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769601, 'banner', 'system', '[0],[system],', '广告资讯管理', '', '/banner', 2, 2, 1, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769602, 'banner_list', 'banner', '[0],[system],[banner],', '广告管理列表', '', '/banner/list', 99, 3, 0, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769603, 'banner_add', 'banner', '[0],[system],[banner],', '广告管理添加', '', '/banner/add', 99, 3, 0, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769604, 'banner_update', 'banner', '[0],[system],[banner],', '广告管理更新', '', '/banner/update', 99, 3, 0, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769605, 'banner_delete', 'banner', '[0],[system],[banner],', '广告管理删除', '', '/banner/delete', 99, 3, 0, NULL, 1, 0);
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES (974223582223769606, 'banner_detail', 'banner', '[0],[system],[banner],', '广告管理详情', '', '/banner/detail', 99, 3, 0, NULL, 1, 0);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769601,1);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769602,1);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769603,1);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769604,1);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769605,1);
insert into sys_relation (`menuid`,`roleid`) values (974223582223769606,1);

INSERT INTO `sys_dict` (`id`, `num`, `pid`, `name`, `tips`) VALUES (50, 0, 0, '广告资讯类型', NULL);
INSERT INTO `sys_dict` ( `num`, `pid`, `name`, `tips`) VALUES ( 1, 50, '广告', NULL);
INSERT INTO `sys_dict` ( `num`, `pid`, `name`, `tips`) VALUES ( 2, 50, '资讯', NULL);
INSERT INTO `sys_dict` (`id`, `num`, `pid`, `name`, `tips`) VALUES (60, 0, 0, '广告状态', NULL);
INSERT INTO `sys_dict` ( `num`, `pid`, `name`, `tips`) VALUES ( 0, 60, '禁用', NULL);
INSERT INTO `sys_dict` ( `num`, `pid`, `name`, `tips`) VALUES ( 1, 60, '正常', NULL);
create table sign_in_log
(
    id bigint not null,
    user_id bigint null comment '用户ID',
    location varchar(1000) null comment '位置信息',
    longitude double(14,11) null comment '经度',
    latitude double(14,11) null comment '纬度',
    create_time datetime null comment '打卡时间',
    type tinyint default 1 null comment '打卡类型',
    constraint sign_in_log_pk
        primary key (id)
)
    comment '签到';
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379891355649', 'signInLog', 'info_query', '[0],[info_query],', '打卡签到管理', '', '/signInLog', '99', '2', '1', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379895549953', 'signInLog_list', 'signInLog', '[0],[info_query],[signInLog],', '打卡签到管理列表', '', '/signInLog/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379895549954', 'signInLog_add', 'signInLog', '[0],[info_query],[signInLog],', '打卡签到管理添加', '', '/signInLog/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379895549955', 'signInLog_update', 'signInLog', '[0],[info_query],[signInLog],', '打卡签到管理更新', '', '/signInLog/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379895549956', 'signInLog_delete', 'signInLog', '[0],[info_query],[signInLog],', '打卡签到管理删除', '', '/signInLog/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('1369613379895549957', 'signInLog_detail', 'signInLog', '[0],[info_query],[signInLog],', '打卡签到管理详情', '', '/signInLog/detail', '99', '3', '0', NULL, '1', '0');
insert into sys_relation (`menuid`,`roleid`) values (1369613379891355649,1);
insert into sys_relation (`menuid`,`roleid`) values (1369613379895549953,1);
insert into sys_relation (`menuid`,`roleid`) values (1369613379895549954,1);
insert into sys_relation (`menuid`,`roleid`) values (1369613379895549955,1);
insert into sys_relation (`menuid`,`roleid`) values (1369613379895549956,1);
insert into sys_relation (`menuid`,`roleid`) values (1369613379895549957,1);

INSERT INTO `sys_dict` (`id`, `num`, `pid`, `name`, `tips`) VALUES (70, 0, 0, '打卡类型', NULL);
INSERT INTO `sys_dict` ( `num`, `pid`, `name`, `tips`) VALUES ( 1, 70, '考勤', NULL);
alter table job_task
    add user_point double default 0 null comment '经办得分';
