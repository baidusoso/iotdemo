/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50648
 Source Host           : localhost:3306
 Source Schema         : iot

 Target Server Type    : MySQL
 Target Server Version : 50648
 File Encoding         : 65001

 Date: 28/05/2020 09:45:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(64) NOT NULL DEFAULT '0',
  `name` varchar(256) NOT NULL,
  `parent_org_code` varchar(64) DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '1有效 0无效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES (11, '11', '总公司', '0', NULL, 1, '2020-05-25 15:29:00', '2020-05-27 16:18:47');
INSERT INTO `sys_org` VALUES (13, '13', '北京分公司', '11', NULL, 1, '2020-05-25 15:34:22', '2020-05-27 16:18:52');
INSERT INTO `sys_org` VALUES (14, '14', '上海分公司', '11', NULL, 1, '2020-05-25 15:34:31', '2020-05-27 16:18:54');
INSERT INTO `sys_org` VALUES (15, '15', '海淀分部', '13', NULL, 1, '2020-05-25 15:34:48', '2020-05-27 16:18:55');
INSERT INTO `sys_org` VALUES (16, '16', '研发部', '11', NULL, 1, '2020-05-25 15:58:40', '2020-05-27 16:18:58');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 'system', '系统管理', 'system:role', '角色管理', 1);
INSERT INTO `sys_permission` VALUES (2, 'system', '系统管理', 'system:user', '用户管理', 2);
INSERT INTO `sys_permission` VALUES (3, 'system', '系统管理', 'system:org', '部门管理', 2);
INSERT INTO `sys_permission` VALUES (4, 'system', '系统管理', 'system:region', '区域管理', 2);
INSERT INTO `sys_permission` VALUES (5, 'system', '系统管理', 'system:map', '地图管理', 2);
INSERT INTO `sys_permission` VALUES (101, 'recognization', '人脸识别', 'recognization:account', '人员管理', 1);
INSERT INTO `sys_permission` VALUES (102, 'recognization', '人脸识别', 'recognization:acs', '门禁管理', 2);
INSERT INTO `sys_permission` VALUES (103, 'recognization', '人脸识别', 'recognization:auth', '权限管理', 2);
INSERT INTO `sys_permission` VALUES (104, 'recognization', '人脸识别', 'recognization:visitor', '来访人员管理', 2);
INSERT INTO `sys_permission` VALUES (105, 'recognization', '人脸识别', 'recognization:visitorhistory', '访问记录', 2);
INSERT INTO `sys_permission` VALUES (301, 'vehicle', '车辆识别', 'vehicle:whitelist', '车辆白名单管理', 1);
INSERT INTO `sys_permission` VALUES (302, 'vehicle', '车辆识别', 'vehicle:acs', '车辆白闸机管理', 2);
INSERT INTO `sys_permission` VALUES (303, 'vehicle', '车辆识别', 'vehicle: accessrecords', '车辆进出记录管理', 2);
INSERT INTO `sys_permission` VALUES (401, 'algorithm', '算法管理', 'algorithm:camera', '摄像机管理', 1);
INSERT INTO `sys_permission` VALUES (402, 'algorithm', '算法管理', 'algorithm:algorithm', '算法管理', 2);
INSERT INTO `sys_permission` VALUES (403, 'algorithm', '算法管理', 'algorithm:joinup', '算法接入', 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `note` varchar(256) DEFAULT NULL COMMENT '描述',
  `enable` tinyint(4) DEFAULT '1' COMMENT '是否启用：1启用 0禁用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 1, '2017-11-22 16:24:34', '2020-05-27 16:03:25', '1');
INSERT INTO `sys_role` VALUES (2, '门卫', '门卫', 1, '2017-11-22 16:24:34', '2020-05-24 08:59:16', '1');
INSERT INTO `sys_role` VALUES (3, '人脸识别管理员', '人脸识别管理员', 1, '2017-11-22 16:28:47', '2020-05-24 06:21:10', '1');
INSERT INTO `sys_role` VALUES (4, '门禁管理员', '门禁管理员', 1, '2020-05-24 08:41:18', '2020-05-24 08:44:21', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `org_code` varchar(64) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8 COMMENT='运营后台用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (10003, 'admin', '123456', 'admin', '13', '13800138000', '2017-10-30 11:52:38', '2020-05-26 14:57:24', '1');
INSERT INTO `sys_user` VALUES (10004, 'user', '123456', '莎士比亚', '16', '13800138002', '2017-10-30 16:13:02', '2020-05-26 15:31:53', '1');
INSERT INTO `sys_user` VALUES (10005, 'aaa', '123456', 'abba', '14', '13800138001', '2017-11-15 14:02:56', '2020-05-26 15:32:08', '0');
INSERT INTO `sys_user` VALUES (10007, 'test', '123456', '就看看列表', NULL, NULL, '2017-11-22 16:29:41', '2020-05-26 15:22:48', '0');
INSERT INTO `sys_user` VALUES (10008, 'abcde', '123456', 'abcde', '15', '13800138000', '2020-05-26 15:24:29', '2020-05-26 15:24:29', '1');
INSERT INTO `sys_user` VALUES (10009, 'test', '123456', 'test', '14', 'fs', '2020-05-27 17:11:31', '2020-05-27 17:12:01', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (12, 10003, 1, '2020-05-27 16:38:02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
