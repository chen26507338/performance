/*
Navicat MySQL Data Transfer

Source Server         : shero2
Source Server Version : 50556
Source Host           : dev.hishero.cn:3306
Source Database       : guns

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-05-11 11:11:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) DEFAULT NULL COMMENT '关联父表外键',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `biz_name` varchar(255) DEFAULT NULL COMMENT '业务名称',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名',
  `parent_menu_name` varchar(255) DEFAULT NULL COMMENT '父级菜单名称',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`) USING BTREE,
  KEY `gen_table_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `gen_table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000) DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`) USING BTREE,
  KEY `gen_table_column_name` (`name`) USING BTREE,
  KEY `gen_table_column_sort` (`sort`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(20) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('25', '2', '24', '[0],[24],', '开发部', '开发部', '', null);
INSERT INTO `sys_dept` VALUES ('26', '3', '24', '[0],[24],', '运营部', '运营部', '', null);
INSERT INTO `sys_dept` VALUES ('27', '4', '24', '[0],[24],', '战略部', '战略部', '', null);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(20) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('29', '0', '0', '性别', null);
INSERT INTO `sys_dict` VALUES ('30', '1', '29', '男', null);
INSERT INTO `sys_dict` VALUES ('31', '2', '29', '女', null);
INSERT INTO `sys_dict` VALUES ('35', '0', '0', '账号状态', null);
INSERT INTO `sys_dict` VALUES ('36', '1', '35', '启用', null);
INSERT INTO `sys_dict` VALUES ('37', '2', '35', '冻结', null);
INSERT INTO `sys_dict` VALUES ('38', '3', '35', '已删除', null);
INSERT INTO `sys_dict` VALUES ('39', '0', '0', '是否', null);
INSERT INTO `sys_dict` VALUES ('40', '0', '39', '否', null);
INSERT INTO `sys_dict` VALUES ('41', '1', '39', '是', null);
INSERT INTO `sys_dict` VALUES ('62', '0', '0', '通用状态', null);
INSERT INTO `sys_dict` VALUES ('63', '1', '62', '正常', null);
INSERT INTO `sys_dict` VALUES ('64', '0', '62', '禁止', null);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(20) DEFAULT NULL COMMENT '管理员id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=748 DEFAULT CHARSET=utf8 COMMENT='登录记录';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('743', '登录日志', '2520', '2018-04-24 16:53:51', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES ('744', '登录日志', '2528', '2018-04-25 10:16:31', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES ('745', '登录日志', '2520', '2018-04-25 10:45:37', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES ('746', '登录日志', '2520', '2018-04-25 14:19:52', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES ('747', '登录失败日志', null, '2018-05-11 11:09:31', '成功', '账号:admin,账号密码错误', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=987863016767684616 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '#', '4', '1', '1', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('106', 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', '1', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('131', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('148', 'code', '0', '[0],', '代码生成', 'fa-code', '#', '3', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('149', 'api_mgr', '0', '[0],', '接口文档', 'fa-leaf', '/swagger-ui.html', '2', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('952122912841572354', 'genTable', 'code', '[0],[code],', '业务表', '', '/genTable', '99', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('952122912841572355', 'genTable_list', 'genTable', '[0],[code],[genTable],', '业务表列表', '', '/genTable/list', '99', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('952122912841572356', 'genTable_add', 'genTable', '[0],[code],[genTable],', '业务表添加', '', '/genTable/add', '99', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('952122912841572357', 'genTable_update', 'genTable', '[0],[code],[genTable],', '业务表更新', '', '/genTable/update', '99', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('952122912841572358', 'genTable_delete', 'genTable', '[0],[code],[genTable],', '业务表删除', '', '/genTable/delete', '99', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('952122912841572359', 'genTable_detail', 'genTable', '[0],[code],[genTable],', '业务表详情', '', '/genTable/detail', '99', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('968000431414784008', 'interface', 'code', '[0],[code],', '接口', '', '/code/interface', '2', '2', '1', null, '1', null);


-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8668 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES ('8349', '105', '5');
INSERT INTO `sys_relation` VALUES ('8350', '106', '5');
INSERT INTO `sys_relation` VALUES ('8351', '108', '5');
INSERT INTO `sys_relation` VALUES ('8352', '109', '5');
INSERT INTO `sys_relation` VALUES ('8353', '110', '5');
INSERT INTO `sys_relation` VALUES ('8354', '111', '5');
INSERT INTO `sys_relation` VALUES ('8355', '112', '5');
INSERT INTO `sys_relation` VALUES ('8356', '113', '5');
INSERT INTO `sys_relation` VALUES ('8357', '167', '5');
INSERT INTO `sys_relation` VALUES ('8358', '133', '5');
INSERT INTO `sys_relation` VALUES ('8359', '160', '5');
INSERT INTO `sys_relation` VALUES ('8360', '161', '5');
INSERT INTO `sys_relation` VALUES ('8361', '964657341694111746', '5');
INSERT INTO `sys_relation` VALUES ('8362', '964657341694111747', '5');
INSERT INTO `sys_relation` VALUES ('8363', '964657341694111748', '5');
INSERT INTO `sys_relation` VALUES ('8364', '964657341694111749', '5');
INSERT INTO `sys_relation` VALUES ('8365', '964657341694111750', '5');
INSERT INTO `sys_relation` VALUES ('8366', '964657341694111751', '5');
INSERT INTO `sys_relation` VALUES ('8412', '974131216599015427', '5');
INSERT INTO `sys_relation` VALUES ('8413', '974131216599015428', '5');
INSERT INTO `sys_relation` VALUES ('8414', '974131216599015429', '5');
INSERT INTO `sys_relation` VALUES ('8415', '974131216599015430', '5');
INSERT INTO `sys_relation` VALUES ('8416', '974131216599015431', '5');
INSERT INTO `sys_relation` VALUES ('8442', '982810317195124739', '5');
INSERT INTO `sys_relation` VALUES ('8443', '982810317195124740', '5');
INSERT INTO `sys_relation` VALUES ('8444', '982810317195124741', '5');
INSERT INTO `sys_relation` VALUES ('8445', '982810317195124742', '5');
INSERT INTO `sys_relation` VALUES ('8446', '982810317195124743', '5');
INSERT INTO `sys_relation` VALUES ('8469', '105', '1');
INSERT INTO `sys_relation` VALUES ('8470', '106', '1');
INSERT INTO `sys_relation` VALUES ('8471', '107', '1');
INSERT INTO `sys_relation` VALUES ('8472', '108', '1');
INSERT INTO `sys_relation` VALUES ('8473', '109', '1');
INSERT INTO `sys_relation` VALUES ('8474', '110', '1');
INSERT INTO `sys_relation` VALUES ('8475', '111', '1');
INSERT INTO `sys_relation` VALUES ('8476', '112', '1');
INSERT INTO `sys_relation` VALUES ('8477', '113', '1');
INSERT INTO `sys_relation` VALUES ('8478', '165', '1');
INSERT INTO `sys_relation` VALUES ('8479', '166', '1');
INSERT INTO `sys_relation` VALUES ('8480', '167', '1');
INSERT INTO `sys_relation` VALUES ('8481', '114', '1');
INSERT INTO `sys_relation` VALUES ('8482', '115', '1');
INSERT INTO `sys_relation` VALUES ('8483', '116', '1');
INSERT INTO `sys_relation` VALUES ('8484', '117', '1');
INSERT INTO `sys_relation` VALUES ('8485', '118', '1');
INSERT INTO `sys_relation` VALUES ('8486', '162', '1');
INSERT INTO `sys_relation` VALUES ('8487', '163', '1');
INSERT INTO `sys_relation` VALUES ('8488', '164', '1');
INSERT INTO `sys_relation` VALUES ('8489', '119', '1');
INSERT INTO `sys_relation` VALUES ('8490', '120', '1');
INSERT INTO `sys_relation` VALUES ('8491', '121', '1');
INSERT INTO `sys_relation` VALUES ('8492', '122', '1');
INSERT INTO `sys_relation` VALUES ('8493', '150', '1');
INSERT INTO `sys_relation` VALUES ('8494', '151', '1');
INSERT INTO `sys_relation` VALUES ('8495', '128', '1');
INSERT INTO `sys_relation` VALUES ('8496', '134', '1');
INSERT INTO `sys_relation` VALUES ('8497', '158', '1');
INSERT INTO `sys_relation` VALUES ('8498', '159', '1');
INSERT INTO `sys_relation` VALUES ('8499', '130', '1');
INSERT INTO `sys_relation` VALUES ('8500', '131', '1');
INSERT INTO `sys_relation` VALUES ('8501', '135', '1');
INSERT INTO `sys_relation` VALUES ('8502', '136', '1');
INSERT INTO `sys_relation` VALUES ('8503', '137', '1');
INSERT INTO `sys_relation` VALUES ('8504', '152', '1');
INSERT INTO `sys_relation` VALUES ('8505', '153', '1');
INSERT INTO `sys_relation` VALUES ('8506', '154', '1');
INSERT INTO `sys_relation` VALUES ('8507', '132', '1');
INSERT INTO `sys_relation` VALUES ('8508', '138', '1');
INSERT INTO `sys_relation` VALUES ('8509', '139', '1');
INSERT INTO `sys_relation` VALUES ('8510', '140', '1');
INSERT INTO `sys_relation` VALUES ('8511', '155', '1');
INSERT INTO `sys_relation` VALUES ('8512', '156', '1');
INSERT INTO `sys_relation` VALUES ('8513', '157', '1');
INSERT INTO `sys_relation` VALUES ('8514', '133', '1');
INSERT INTO `sys_relation` VALUES ('8515', '160', '1');
INSERT INTO `sys_relation` VALUES ('8516', '161', '1');
INSERT INTO `sys_relation` VALUES ('8517', '141', '1');
INSERT INTO `sys_relation` VALUES ('8518', '142', '1');
INSERT INTO `sys_relation` VALUES ('8519', '143', '1');
INSERT INTO `sys_relation` VALUES ('8520', '144', '1');
INSERT INTO `sys_relation` VALUES ('8521', '964657341694111746', '1');
INSERT INTO `sys_relation` VALUES ('8522', '964657341694111747', '1');
INSERT INTO `sys_relation` VALUES ('8523', '964657341694111748', '1');
INSERT INTO `sys_relation` VALUES ('8524', '964657341694111749', '1');
INSERT INTO `sys_relation` VALUES ('8525', '964657341694111750', '1');
INSERT INTO `sys_relation` VALUES ('8526', '964657341694111751', '1');
INSERT INTO `sys_relation` VALUES ('8527', '145', '1');
INSERT INTO `sys_relation` VALUES ('8528', '148', '1');
INSERT INTO `sys_relation` VALUES ('8529', '952122912841572354', '1');
INSERT INTO `sys_relation` VALUES ('8530', '952122912841572355', '1');
INSERT INTO `sys_relation` VALUES ('8531', '952122912841572356', '1');
INSERT INTO `sys_relation` VALUES ('8532', '952122912841572357', '1');
INSERT INTO `sys_relation` VALUES ('8533', '952122912841572358', '1');
INSERT INTO `sys_relation` VALUES ('8534', '952122912841572359', '1');
INSERT INTO `sys_relation` VALUES ('8535', '968000431414784008', '1');
INSERT INTO `sys_relation` VALUES ('8536', '149', '1');
INSERT INTO `sys_relation` VALUES ('8603', '974131216599015427', '1');
INSERT INTO `sys_relation` VALUES ('8604', '974131216599015428', '1');
INSERT INTO `sys_relation` VALUES ('8605', '974131216599015429', '1');
INSERT INTO `sys_relation` VALUES ('8606', '974131216599015430', '1');
INSERT INTO `sys_relation` VALUES ('8607', '974131216599015431', '1');
INSERT INTO `sys_relation` VALUES ('8633', '982810317195124739', '1');
INSERT INTO `sys_relation` VALUES ('8634', '982810317195124740', '1');
INSERT INTO `sys_relation` VALUES ('8635', '982810317195124741', '1');
INSERT INTO `sys_relation` VALUES ('8636', '982810317195124742', '1');
INSERT INTO `sys_relation` VALUES ('8637', '982810317195124743', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` bigint(20) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` bigint(20) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '0', '超级管理员', '24', 'administrator', '1');
INSERT INTO `sys_role` VALUES ('5', '2', '1', '管理员', '26', 'temp', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` bigint(20) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  `no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'girl.gif', 'admin', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '超级管理员', '2017-05-05 00:00:00', '2', 'sn93@qq.com', '18200000000', '1', '27', '1', '2016-01-29 08:49:53', '25', null);
INSERT INTO `sys_user` VALUES ('45', null, 'boss', '71887a5ad666a18f709e1d4e693d5a35', '1f7bf', '管理员', '2017-12-04 00:00:00', '1', '', '', '1', '24', '1', '2017-12-04 22:24:02', null, null);
