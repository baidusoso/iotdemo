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

 Date: 01/06/2020 01:59:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(64) DEFAULT NULL,
  `gender` char(2) DEFAULT NULL,
  `no` varchar(64) DEFAULT NULL COMMENT '工号',
  `certificate_num` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `org_id` varchar(64) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `usergroup` varchar(32) DEFAULT NULL,
  `face_id` varchar(64) DEFAULT NULL,
  `face_pic` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '1有效0无效',
  `update_date` varchar(32) DEFAULT NULL,
  `sync_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('30d221c1e7bb72cac883a4ab3dc39a05', '郝毓文', '0', '12024582', '220284198511017011', '15124473470', '2060', 'haoyuwen', '厂内人员', 'b2f42465-4d4c-4a8a-9fe4-0efb8d3c91ca', '30d221c1e7bb72cac883a4ab3dc39a05.jpg', 1, '2019-11-06 09:56:51', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('41154b528e3345d4b95c3288e98d2a2a', '甄宝', '0', '', '140225200005081811', '12000000082', '2051', 'zhenb', '厂内人员', '5141248c-8e9a-404e-942a-aa593d93e344', '41154b528e3345d4b95c3288e98d2a2a.jpg', 1, '2019-07-29 09:37:26', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('43e7229489cc46aa8232dbf1ff6ed8b2', '张心然', '0', '12089778', '131102198804174415', '15103167788', '2060', 'zhangxinran', '厂内人员', 'b183688c-d8b2-49bb-bbc9-f24d5b794ae2', '43e7229489cc46aa8232dbf1ff6ed8b2.jpg', 1, '2019-10-16 19:43:11', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('49f5cc2dc56ff7e77c634dd282c1b6d5', '张秀斌', '0', '', '140104197307280013', '12000000671', '2040', 'zhangxb2', '厂内人员', 'd1802237-bf3b-4ebb-920c-aaa61d26ab02', '49f5cc2dc56ff7e77c634dd282c1b6d5.jpg', 1, '2019-11-19 11:05:14', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('4aa7bdedd2534e65bb2a5669eaea9c75', '赵有', '0', '', '132527197205188538', '12000000093', '2044', 'zhaoyou', '厂内人员', '0c600db9-6875-4e1d-95d5-266e1cc1558e', '4aa7bdedd2534e65bb2a5669eaea9c75.jpg', 1, '2019-07-09 08:44:14', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('4b3cc9e8a3d143ed93201f1ae4ffeb5e', '杨继龙', '0', '', '131002199311072014', '12000000660', '2039', 'yangjl2', '厂内人员', '14691533-6967-4976-8f40-2dc88c258c35', '4b3cc9e8a3d143ed93201f1ae4ffeb5e.jpg', 1, '2019-11-04 11:01:56', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('51bf32e3506b456b987fd3ce1cc88b70', '张禄华', '0', '12092306', '130183199301180030', '18503161568', '2031', 'zhangluhua', '厂内人员', 'a9ca35e3-ef94-4560-8061-97d9d6c3e632', '51bf32e3506b456b987fd3ce1cc88b70.jpg', 1, '2019-07-15 10:23:18', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('52ab19eb400349948c801110210c066d', '李科', '0', '12047879', '140104197606040036', '18503168338', '2025', 'like', '厂内人员', 'c0a83a82-e70b-4668-8d47-0960528357d9', '52ab19eb400349948c801110210c066d.jpg', 1, '2019-05-27 17:29:39', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('5c30709c1f8e2f0b05df4900c7569108', '许铠明', '0', '', '130322200102032610', '15631625170', '2077', 'xukm', '厂内人员', '6177d37a-a9d3-48b1-8b2a-c08b27116322', '5c30709c1f8e2f0b05df4900c7569108.jpg', 1, '2019-11-14 16:49:10', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('5db662706d5d4708843a4745d0a9c910', '王欣', '0', '90000607', '220104196809210045', '12000000603', '2070', 'wangxin', '厂内人员', '030f2872-a18c-48f5-9ab1-1df2acd5e7d9', '5db662706d5d4708843a4745d0a9c910.jpg', 1, '2019-11-04 09:14:57', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('64e5ae71b9f842f098f506036a650580', '孙晓宾', '0', '', '140225198911111832', '12000000131', '2049', 'sunxb', '厂内人员', '16a8dd7c-9273-4ae7-87b7-77ba4a5ab906', '64e5ae71b9f842f098f506036a650580.jpg', 1, '2019-05-27 17:32:16', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('684ffc3fac664b52491d26c8bb87616d', '陈建', '0', '', '132801198207282234', '15369666663', '2077', 'chenj', '厂内人员', 'f2fd0367-b4ed-41aa-9b00-58ad4c28a2ff', '684ffc3fac664b52491d26c8bb87616d.jpg', 1, '2019-11-14 16:58:34', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('6f32f527c5fc4b96bfd8214cdbe50857', '史巧凤', '0', '12069283', '110108197112156047', '13082139121', '2011', 'shiqiaofeng', '厂内人员', 'd9f8a6f9-13ac-4e21-8fc0-b21dd4d439b1', '6f32f527c5fc4b96bfd8214cdbe50857.jpg', 1, '2019-11-05 09:54:10', '2020-05-30 02:34:10');
INSERT INTO `account` VALUES ('7d84c4fa94f14c078643bbf12a7c38b7', '陈海旺', '0', '12075881', '130229199204261813', '15531650025', '2021', 'chenhaiwang', '厂内人员', '056130f8-5f39-4949-ad5e-a313d2a127bc', '7d84c4fa94f14c078643bbf12a7c38b7.jpg', 1, '2019-05-27 17:23:46', '2020-05-30 02:34:10');
COMMIT;

-- ----------------------------
-- Table structure for gateway
-- ----------------------------
DROP TABLE IF EXISTS `gateway`;
CREATE TABLE `gateway` (
  `index_code` varchar(64) NOT NULL COMMENT '设备唯一标识',
  `name` varchar(64) DEFAULT NULL COMMENT '设备名称',
  `region_code` varchar(64) DEFAULT NULL COMMENT '所属区域唯一标识',
  `treaty_type` varchar(32) DEFAULT NULL COMMENT '接入协议',
  `firm` varchar(32) DEFAULT NULL COMMENT '厂商',
  `type_code` varchar(32) DEFAULT NULL COMMENT '设备类型编号',
  `type_name` varchar(32) DEFAULT NULL COMMENT '设备类型名称',
  `type_desc` varchar(32) DEFAULT NULL COMMENT '设备类型描述',
  `ip` varchar(32) DEFAULT NULL COMMENT '设备IP',
  `port` int(11) DEFAULT NULL COMMENT '设备port',
  `code` varchar(32) DEFAULT NULL COMMENT '设备编号',
  `status` tinyint(4) DEFAULT '1',
  `create_time` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `sync_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`index_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gateway
-- ----------------------------
BEGIN;
INSERT INTO `gateway` VALUES ('122cc79e81c849998da75ad792a0f052', '1号炉等离子整流柜配电柜间北一', '379ed963-42fd-461c-b7fd-0190156101c3', 'hiksdk_net', NULL, '201933568', NULL, 'DS-K1T60HZ-DC', '192.168.13.20', 8000, NULL, 1, '2019-10-30T16:32:35.822+08:00', '2019-11-09T08:14:44.093+08:00', '2020-05-30 04:13:38');
INSERT INTO `gateway` VALUES ('36dff7cf0ddf42fe897aa760416e2a55', '1号机一次风机变频间', '31deda20-635a-4557-8f90-74837672b64e', 'hiksdk_net', NULL, '201933568', NULL, 'DS-K1T60HZ-DC', '192.168.13.19', 8000, NULL, 1, '2019-10-30T16:29:10.630+08:00', '2019-11-05T17:12:30.509+08:00', '2020-05-30 04:13:37');
INSERT INTO `gateway` VALUES ('6f0642c437cc4b89a932645ab4df8c4d', '1号炉等离子整流柜配电柜间中二', '1608faa6-831e-44d6-a876-fe11300f2adc', 'hiksdk_net', NULL, '201933568', NULL, 'DS-K1T60HZ-DC', '192.168.13.21', 8000, NULL, 1, '2019-10-30T16:34:04.828+08:00', '2019-11-10T15:44:11.292+08:00', '2020-05-30 04:13:38');
INSERT INTO `gateway` VALUES ('a3a2daf4b5dc449096ba5844e29c865a', '1号炉等离子整流柜配电柜间南三', '7b708648-18eb-46dd-9ab0-fceec9f7fbf5', 'hiksdk_net', NULL, '201933568', NULL, 'DS-K1T60HZ-DC', '192.168.13.22', 8000, NULL, 1, '2019-10-30T16:36:39.575+08:00', '2019-11-05T18:22:31.913+08:00', '2020-05-30 04:13:38');
COMMIT;

-- ----------------------------
-- Table structure for gateway_door
-- ----------------------------
DROP TABLE IF EXISTS `gateway_door`;
CREATE TABLE `gateway_door` (
  `index_code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `gateway_index_code` varchar(64) NOT NULL,
  `channel_no` tinyint(4) NOT NULL,
  `channel_type` varchar(16) DEFAULT NULL,
  `no` tinyint(4) DEFAULT NULL,
  `install_location` varchar(256) DEFAULT NULL,
  `create_time` varchar(32) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `update_time` varchar(32) DEFAULT NULL,
  `sync_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`index_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gateway_door
-- ----------------------------
BEGIN;
INSERT INTO `gateway_door` VALUES ('0c3518c8f15b4b9da7242d32f7034dc7', '1号炉等离子整流柜配电柜间 南三_门1', 'a3a2daf4b5dc449096ba5844e29c865a', 1, 'door', 1, NULL, '2019-10-30T16:36:55.129+08:00', 1, '2019-10-30T16:36:55.196+08:00', '2020-05-30 04:13:39');
INSERT INTO `gateway_door` VALUES ('0e0a7aa376454873a227e8f8f6f97d1e', '1号机一次风机变频间_门1', '36dff7cf0ddf42fe897aa760416e2a55', 1, 'door', 1, NULL, '2019-10-30T16:29:28.528+08:00', 1, '2019-10-30T16:29:28.562+08:00', '2020-05-30 04:13:39');
INSERT INTO `gateway_door` VALUES ('5a629ae9579d427aac31b442fdd87022', '1号炉DCS电子间南三_门1', 'c392c1d82aa44fd58ebdebffccd04648', 1, 'door', 1, NULL, '2019-10-31T10:45:53.659+08:00', 1, '2019-10-31T10:45:53.700+08:00', '2020-05-30 04:13:39');
INSERT INTO `gateway_door` VALUES ('5aa3e82b3991452aa126a9e05c2c12e8', '1号炉等离子整流柜配电柜间 中二_门1', '6f0642c437cc4b89a932645ab4df8c4d', 1, 'door', 1, NULL, '2019-10-30T16:34:24.272+08:00', 1, '2019-10-30T16:34:24.307+08:00', '2020-05-30 04:13:39');
INSERT INTO `gateway_door` VALUES ('61a327b86ee94545a91b394e75a2953f', '1号炉等离子整流柜配电柜间 北一_门1', '122cc79e81c849998da75ad792a0f052', 1, 'door', 1, NULL, '2019-10-30T16:32:47.992+08:00', 1, '2019-10-30T16:32:48.050+08:00', '2020-05-30 04:13:39');
INSERT INTO `gateway_door` VALUES ('8ed6148ec0aa442aa1d435478e9afb0d', '水汽取样分析仪表间_门1', '457958abeef34a5c8433c36b6f9560d9', 1, 'door', 1, NULL, '2019-10-30T17:39:01.154+08:00', 1, '2019-10-30T17:39:01.177+08:00', '2020-05-30 04:13:39');
COMMIT;

-- ----------------------------
-- Table structure for gateway_policy
-- ----------------------------
DROP TABLE IF EXISTS `gateway_policy`;
CREATE TABLE `gateway_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(64) DEFAULT NULL,
  `gateway_id` varchar(64) DEFAULT NULL,
  `begin_time` varchar(32) DEFAULT NULL,
  `end_time` varchar(32) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '1有效0无效',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gateway_policy
-- ----------------------------
BEGIN;
INSERT INTO `gateway_policy` VALUES (1, '30d221c1e7bb72cac883a4ab3dc39a05', '122cc79e81c849998da75ad792a0f052', '2019-12-19 00:00:00', '2029-01-30 00:00:00', 0, '2020-05-31 01:31:04');
INSERT INTO `gateway_policy` VALUES (2, '30d221c1e7bb72cac883a4ab3dc39a05', '36dff7cf0ddf42fe897aa760416e2a55', '2019-12-19 00:00:00', '2029-01-30 00:00:00', 0, '2020-05-31 01:31:04');
INSERT INTO `gateway_policy` VALUES (3, '30d221c1e7bb72cac883a4ab3dc39a05', 'fa82a6d88fd84bf1a9b0d6610fa2275c', '2019-12-19 00:00:00', '2029-01-30 00:00:00', 1, '2020-05-31 08:49:08');
INSERT INTO `gateway_policy` VALUES (4, '30d221c1e7bb72cac883a4ab3dc39a05', '18ff52a65c2149df880822a409dca686', '2019-12-19 00:00:00', '2029-01-30 00:00:00', 1, '2020-05-31 08:49:08');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=985 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES (892, '2039', '太一项目部', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (893, '2000', '国家能源集团华北电力有限公司', '660', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (894, '2028', '锅炉专业', '2012', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (895, '2027', '综合专业', '2012', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (896, '2013', '燃料管理部', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (897, '2006', '财务部', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (898, '2087', '安徽省和县第一建筑安装工程公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (899, '2083', '河北雪龙建筑园林工程有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (900, '2079', '廊坊市顺电机械租赁有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (901, '2077', '湖南电力物流', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (902, '2088', '临沂箭科清洗有限责任公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (903, '2063', '河南新科起重机股份有限公司廊坊项目部', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (904, '2003', '厂长办公室(党委办公室)', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (905, '2045', '项目部除尘班', '2039', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (906, '2021', '集控四值', '2011', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (907, '2035', '采制样组', '2013', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (908, '2037', '汽车衡组', '2013', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (909, '2015', '安健环部', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (910, '2036', '化验组', '2013', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (911, '2002', '厂领导', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (912, '2070', '退二线', '2004', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (913, '2095', '国电科学技术研究院有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (914, '2098', '国电科学技术研究院有限公司太原分公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (915, '2100', '河北景易公路工程有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (916, '2001', '国家能源集团华北电力有限公司廊坊热电厂', '2000', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (917, '2052', '河北建筑消防技术服务中心廊坊项目部', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (918, '2053', '湖北华维斯博环保工程有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (919, '2004', '人力资源部(党委组织部)', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (920, '2049', '项目部电气班', '2039', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (921, '2041', '脱硫运行', '2039', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (922, '2029', '汽机专业', '2012', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (923, '2018', '集控一值', '2011', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (924, '2005', '计划营销部', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (925, '2010', '工程部', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (926, '2064', '廊坊市优力电梯有限公司廊坊项目部', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (927, '2054', '翻车斗轮维护班', '2061', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (928, '2055', '起重维护班', '2063', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (929, '2066', '物业', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (930, '2065', '司机', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (931, '2074', '荏原冷热系统（中国）有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (932, '2082', '河北东润建工有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (933, '2091', '石家庄建工集团', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (934, '2090', '河北联亚电力工程有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (935, '2092', '华升富士达电梯有限公司', '2069', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (936, '660', '国家能源投资集团有限责任公司', '0', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (937, '2007', '党建工作部(党委宣传部)(工会办公室)', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (938, '2008', '监察审计部(纪委办公室)', '2001', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (939, '2040', '输煤运行', '2039', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (940, '2032', '热控炉控', '2012', NULL, 1, '2020-05-29 08:38:17', '2020-05-29 08:38:17');
INSERT INTO `sys_org` VALUES (941, '2019', '集控二值', '2011', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (942, '2020', '集控三值', '2011', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (943, '2030', '电气一次', '2012', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (944, '2033', '继电保护', '2012', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (945, '2023', '集控五值', '2011', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (946, '2061', '大连奇重机电成套设备有限公司廊坊项目部', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (947, '2044', '项目部制粉班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (948, '2042', '项目部汽机班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (949, '2057', '项目部电控班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (950, '2060', '智慧企业办公室', '2001', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (951, '2069', '外委单位', '2001', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (952, '2085', '河北景易有限公司公路工程', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (953, '2081', '廊坊金盾建工有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (954, '2071', '厂外借调', '2004', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (955, '2089', '河北德普环境监测有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (956, '2097', '皓庭新风系统售后', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (957, '2101', '北京格林保险公估有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (958, '2043', '项目部锅炉班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (959, '2047', '项目部输煤班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (960, '2048', '项目部综合班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (961, '2046', '项目部脱硫班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (962, '2031', '热控机控', '2012', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (963, '2025', '化学班', '2011', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (964, '2038', '厂内借调', '2013', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (965, '2012', '设备管理部', '2001', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (966, '2014', '华北公司本部', '2000', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (967, '2011', '运行部', '2001', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (968, '2062', '重庆泷郡电力科技有限公司廊坊项目部', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (969, '2072', '廊坊汇佳物业服务有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (970, '2051', '项目部热控班', '2039', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (971, '2058', '吹灰器维护班', '2062', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (972, '2056', '消防维护班', '2052', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (973, '2059', '电梯维护班', '2064', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (974, '2076', '天津诚瑞达物业集团有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (975, '2075', '邯郸建工集团有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (976, '2067', '副总师、总助', '2002', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (977, '2084', '九江中船长安消防设备有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (978, '2078', '扬州花木盆景有限责任有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (979, '2080', '廊坊市盛安机械租赁有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (980, '2099', '廊坊市朗汇机电设备有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (981, '2096', '冀北电力有限公司检修分公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (982, '2093', '山西誉骋电力工程有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (983, '2094', '德州明奥电力科技有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
INSERT INTO `sys_org` VALUES (984, '2086', '河南鼎苑建设有限公司', '2069', NULL, 1, '2020-05-29 08:38:18', '2020-05-29 08:38:18');
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
INSERT INTO `sys_permission` VALUES (101, 'recognization', '人脸识别', 'recognization:person', '人员管理', 1);
INSERT INTO `sys_permission` VALUES (102, 'recognization', '人脸识别', 'recognization:gateway', '门禁管理', 2);
INSERT INTO `sys_permission` VALUES (103, 'recognization', '人脸识别', 'recognization:policy', '权限管理', 2);
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
-- Table structure for sys_region
-- ----------------------------
DROP TABLE IF EXISTS `sys_region`;
CREATE TABLE `sys_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_code` varchar(64) NOT NULL,
  `region_name` varchar(256) NOT NULL,
  `parent_region_code` varchar(64) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '1有效0无效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=288 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_region
-- ----------------------------
BEGIN;
INSERT INTO `sys_region` VALUES (1, 'root000000', '廊坊热电厂', '-1', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (2, '5fcd4ef5-4647-4708-9ef8-759c785d6eb2', '锅炉工业电视', 'root000000', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (3, '6d0d162b-00a2-46d8-b52d-da57a441dca0', '水汽取样分析仪表间', 'b31700a6-ae75-4e5d-a3ad-0f05d0bd1178', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (4, '0a85296a-b6d9-48c6-bb0c-36053c78ea1d', '1号炉等离子整流柜配电柜间', '0b251a04-c3e1-4ba3-8f65-4f5268b4f353', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (5, '379ed963-42fd-461c-b7fd-0190156101c3', '北一', '0a85296a-b6d9-48c6-bb0c-36053c78ea1d', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (6, '9511749a-1fcc-4101-aaea-5d50c8ed04d6', '南三', 'f7ed973e-44dc-4cb6-a558-ddd041b062d1', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (7, 'd8ec25b8-b05b-4f78-8cec-cde12f9623ca', '1号脱硝A侧CEMS小间', '4a84ba11-1184-4f4f-9cd1-100903a3c680', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (8, '6569db9d-2606-4878-8688-1ba10e00a480', '2号炉0米门禁', 'aaae135f-2e7a-4c4b-85cd-bc554f6e1b79', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (9, '845b3662-3d5f-4a52-b4eb-9c189ac69c96', '2号机一次风机变频间', '6569db9d-2606-4878-8688-1ba10e00a480', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (10, '9f81b65a-b39e-4b56-81b9-5af7e386f58f', '2号炉等离子整流柜配电间', '598f3378-fdf0-4503-bcc5-22f65e5478dd', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (11, '7cb77c17-0cc9-41c5-909e-cb23c5ee499e', '南一', '9f81b65a-b39e-4b56-81b9-5af7e386f58f', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (12, 'a0861a42-d3ea-4fa0-a96c-39a38f3d3628', '北三', '46432dc1-4134-46ad-9b6a-37c5961b0418', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (13, 'eb6c4950-d7f9-4728-8ab9-4d4ff8af7e16', '2号炉脱硝A侧CEMS小间', 'b71e2cc3-3678-47bf-9980-b7eac5e008e9', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (14, '58ef9b3c-3842-469c-a756-8e16210b77eb', '2号炉远程DCS电子间', 'c9fcd2d0-f9d4-4086-8260-2bda06859ddd', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (15, '9b64662a-a2f0-483b-83ee-9d9f90bf50ba', '1号升压站 电子间', '66ebd902-afd4-4ff6-9b6f-bc9a1c945f0f', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (16, '029e22a7-f20b-4de7-abc8-9141d9a92b35', '东北门', '9b64662a-a2f0-483b-83ee-9d9f90bf50ba', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (17, 'cc8b7597-2104-4167-a275-89006a13211b', '循环水泵房380V MCC配电间', 'bd5bc131-67dd-46a0-bc0e-41b43ca49b39', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (18, '28f3e4e1-a03c-44af-914f-b3296af0ea4d', '东北门', 'cc8b7597-2104-4167-a275-89006a13211b', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (19, '03f834f7-93cc-4f99-a5d8-983bbd210fc0', '东南门', 'f3233f2f-084d-4488-a84a-c48a57670de7', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (20, 'b6d35fd9-a1a0-45e5-a989-69f256d9b6d3', '1号汽机工业电视', '6bb4b0fa-76f7-4ddd-afcf-18c4e2c3d14e', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (21, 'a5d5275f-21d2-4f6a-86a3-af183be3315d', '1号汽机门禁', 'cd183457-5fbb-407b-ba8f-ab9be687c4c7', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (22, '6a238436-0373-4895-8d51-c7da4c3e95eb', '1号汽机0米门禁', 'a5d5275f-21d2-4f6a-86a3-af183be3315d', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (23, '68e2d51d-7c63-40f2-af64-15a56fbe18b5', '2号汽机0米门禁', 'c9bf8ded-9217-4eaa-93de-fa9e1a499585', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (24, '16b8dffe-f3c1-4a68-bc26-5258c2a8a03a', '1号机38V保安段配电室门禁', '6a238436-0373-4895-8d51-c7da4c3e95eb', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (25, '0bcd6d78-1680-42cc-ae1c-fc4d42ff7cf3', '1号机38V保安段配电室北门', '16b8dffe-f3c1-4a68-bc26-5258c2a8a03a', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (26, '150c6f0b-4c40-40b8-8be4-824160e358c2', '1号汽机6KV门禁', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (27, '94015897-ba3d-4a1e-b1a3-f0c72f4f558e', '1号汽机6KV 南一门禁', '150c6f0b-4c40-40b8-8be4-824160e358c2', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (28, '1f2d2f13-040a-48e9-991c-bb0e64b9dcaa', '1号机 380V 西二门禁', '27f912c5-2444-414c-83f3-7a26d3fad4f9', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (29, '24891ff7-8491-4746-9159-9e11db4df884', '1A1B热网水泵变频间东门禁', '0b3e8936-24ca-4c76-8168-ec9821ccd545', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (30, 'd5e5655f-722b-4942-861a-a5a0936ca9d9', '2号机 380V 保安段配电室 南 门禁', 'd016ad16-44c1-4f18-bb21-c8b0adb92d59', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (31, '9beed5b1-9850-48b9-a119-7d634ad01fa2', '2号汽机主厂房 220V~110V 直流配电间北门', 'e6b9adf4-607a-4416-abae-08a9dc754837', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (32, '77cc282c-5b58-45b5-8c86-8f560741e8c9', '2号汽机 机组蓄电池 东门禁', 'e4bdceb1-441a-4e85-a35c-16a642ab9511', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (33, '60a00eda-1028-4eed-9e98-a17b11450eda', '2号汽机6.3米 电气电子间 门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (34, 'f987d6a1-406e-4557-be72-f4c382f152f3', '2号汽机6.3米 电气电子间 西门 门禁', '60a00eda-1028-4eed-9e98-a17b11450eda', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (35, '1f890953-819b-4172-b722-9820d2064f90', '2号机 6KV 南三 门禁', '9e01b717-f0bf-4ee3-8503-11dc5af04e54', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (36, 'c91dcf81-d98d-480f-9708-edda6fbe65e4', '2号机公用热网变频间 西一 门禁', 'dd36b4d2-d125-4bb7-9969-fb275328b8eb', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (37, 'd497b082-1956-46d7-84e1-3e27d57dd4c1', '2#机炉 380V PC配电间 南一  门禁', '4b08c92c-df24-4f20-9ae8-e23942e4f153', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (38, 'c662ae7d-8993-4c08-8692-c1bcf0707720', '生活污水处理间 南 门禁', '1d64a4ae-6c30-405b-a3b9-4b0caf5826ca', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (39, 'ca849b05-9af6-4fa2-a043-321e9b9018a3', '2A2B热网疏水泵变频间', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (40, '4ccff583-4011-4bf1-8b40-2d907167d2b5', '输煤区域门禁', '1ea60dd3-7136-4c6a-955b-01be4feb7275', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (41, '57b67c8e-2f2f-41a2-a1be-c830b03bf0aa', 'DCS 输煤远程站', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (42, 'a1de8df9-d55f-4650-aa0d-025eb5b3398c', '6号重锤等离子380V PC配电间 东 门禁', '760bb865-79ba-447a-b387-39aa08890d4b', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (43, '534b36c3-a430-46cf-8bb9-162d01b9c4d1', '碎煤机室 380V MCC配电间 北门禁', 'a96f53f2-2a4b-4c30-a4ac-8be8c0742801', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (44, 'f9eed14b-e74c-4539-a685-daf8bb0a11da', '6号重锤 等离子 380V PC配电间 ', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (45, '0c5a8332-f806-4c09-96d6-fdd485b30cf4', '翻车机 380V MCC配电间', '22957c8e-1c5c-4370-b892-daffe6c77b8d', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (46, 'e83ccef5-cb3c-444a-96b4-f3f101f990fe', '翻车机 PC 配电间 北门', '75f47652-7e23-4a24-99f8-bea2932abe24', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (47, '8e693353-a099-4c2a-870c-430729572596', '输煤 380V 配电间 门禁 东南', 'e61b39f5-833e-4efa-9a83-19b0517a5023', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (48, 'e1a67aeb-8d2f-47e1-8d52-268129253404', '1号转运站 380V MCC配电间 北 门禁', '247cfbe0-5155-42de-acd3-4ce1b6909fd5', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (49, 'f3612577-2200-4d26-b0aa-a59617763bb4', '燃料智能化DCS 工程师站 南 门禁', '584d4abe-523f-437d-a14c-ec1360bafad4', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (50, 'b455f1af-4d44-4107-a1d0-e134256f66d2', '脱硫控制室', 'cd90ccd0-3e8f-4f0c-a5a0-61b081ec7767', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (51, '2d6f0283-02d2-4da0-a268-2bf97b54ec48', '脱硫公用380V MCC配电间 北 门禁', '61d1483b-21a8-4405-9746-607e8d8661f4', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (52, 'b8847d60-6e2e-4c60-a5c1-c027e10fdfc2', '公用脱硫DCS电子间 东 门禁', 'a119271d-0778-4504-88ce-840c4f72bdac', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (53, 'b863fc66-0e68-4506-8f24-a17557b0a937', '1层 1号炉原烟气CEMS小间门禁', '05cc4836-c388-42a0-ac63-bafd04077738', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (54, 'e5cbb93f-63e9-4678-b77f-2022117f5e8b', '三层 #1脱硫6KV 配电间 北一 门禁', 'f6c038c4-2179-4ea4-addb-88420b66659e', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (55, 'a4029376-7940-41a8-9230-bc2cdda2f01d', '#1脱硫UPS配电间 南 门禁', 'd67d1bb9-4c9b-4f5e-b387-f4c11ef6cf46', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (56, 'c7613340-a9ff-46f1-9773-91ba2fd1bc4f', '五层 1-2号湿除 380V PC配电间 北一 门禁', '2ed2a2f2-170a-4b0d-89c1-027a79e03c89', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (57, '5a0a8772-dafb-4cf3-89c8-afa2692292ab', '湿除 DCS 工程师站 南 门禁', 'e4ea6d01-15e2-4efc-a282-b20f736a70d7', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (58, '1f097e8e-872b-4207-8e79-ad629bae87dd', '一层 2号炉原烟气CEMS小间门禁', 'e472c6a3-7c80-4126-af17-a409faee215e', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (59, '8fded9e3-6ab2-455f-b505-cb34f18c32e6', '三层 2号脱硫 6KV 配电间 南三 门禁', '6914c2f4-bb00-4337-b45e-1e16819d4242', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (60, 'b095034c-7afb-494f-b933-b2cb0a4842ae', '尿素 DCS 电子间 门禁', '550fb231-3e87-4d52-b469-4e090f3e4d44', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (61, 'd2a45868-163b-4824-bd45-db8e3a9e5e10', '尿素DCS 电子间 东 门禁', 'b095034c-7afb-494f-b933-b2cb0a4842ae', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (62, '0aede14b-3d22-46b2-a6b7-b3085551f4a5', '尿素车间 380V MCC 配电间 东 门禁', '78041817-793f-4dd1-b056-43d445540288', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (63, '6ba5912d-486c-405e-80b7-95683137192c', '脱硫区域工业电视', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (64, '32bbc889-20cd-4cdf-98c8-14cb91a78de2', '化水 380V PC 配电间 西门禁', 'd8c333e4-cd6d-47d6-a052-38ae161db131', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (65, 'ba90f276-2d8e-47ee-9537-46bc61343a5d', '化水药品库 西 门禁', '2ca69399-d948-4e8d-85d6-11f3d5e5843d', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (66, '6f64f5af-608f-45e2-8f57-eda0e7378de2', '化学储存间 西 门禁', 'cf52f2dc-b354-453d-be1e-b79873f2f58b', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (67, 'fd8c4094-53a3-4677-a160-b66a6b5ead0f', '脱硫综合楼工业电视', '6ba5912d-486c-405e-80b7-95683137192c', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (68, '8eaf3545-d06f-4ddf-a088-97b731c33905', '脱硫综合楼一层', 'fd8c4094-53a3-4677-a160-b66a6b5ead0f', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (69, 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', '化学区域工业电视', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (70, '6b3a9ead-b370-4ba8-9c26-8f88a7d5c0e9', '化学石灰石存储计量间', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (71, 'dd123288-43cc-4d37-9b30-f900a043103a', '除灰DCS工程师站门禁', 'd5351782-1382-49df-b400-8c1f5256bc28', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (72, '04be5609-f24e-478f-894b-9f25c3e503b1', '除灰DCS电子间 北一 门禁', 'a30bd8bc-e1c7-4b19-bae5-01acf83e2249', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (73, '29509594-136b-43f5-905d-1b3b043c6598', '除灰380V MCC配电间 北一 门禁', 'd5c72f1d-58bb-4542-a23a-416e4fd48a9e', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (74, '69319f52-a8c0-4eb5-9ba0-7db3b51acdc6', '除灰综合楼二层6KV公用段配电间 北一门禁', '3b22a76d-fb8f-47a8-bc99-95440ac4b62e', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (75, '4467b462-3602-471c-a804-7252be01e1cf', 'A斗轮机高压配电室门禁', '98fa5a45-73bb-4d5a-b31b-0c4788dc7e94', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (76, 'dbc6b906-be52-497b-ad35-99cd61c1f695', '1号机励磁小间门禁', '39bd4e3c-4b80-427b-9f2c-678c1e682490', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (77, 'bd021742-eb54-4cc2-a82e-e511371c2ad3', '2号机励磁小间门禁', 'a644242e-fc35-462d-9770-011cd9ef3af5', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (78, '0452759a-7d03-4880-8461-5abc24a490ed', '1#电汽电子间 西 门禁', 'e91fcfdc-841b-4dcf-b7e6-e1ba6338c554', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (79, '5cbd08bd-661a-433f-824f-760d6dc47d0a', '1#机组蓄电池 西 门禁 ', 'a50cb713-3ad3-43d0-abf3-e68bc3428215', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (80, '7aab0cad-7638-4185-ab0e-07b43628a913', '集控NVR-1', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (81, 'b31700a6-ae75-4e5d-a3ad-0f05d0bd1178', '1号锅炉0米门禁', '34c36c37-ed4a-4b27-9d35-5a5e478e6abe', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (82, '31deda20-635a-4557-8f90-74837672b64e', '1号机一次风机变频间', 'b31700a6-ae75-4e5d-a3ad-0f05d0bd1178', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (83, '1608faa6-831e-44d6-a876-fe11300f2adc', '中二', '0a85296a-b6d9-48c6-bb0c-36053c78ea1d', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (84, 'f7ed973e-44dc-4cb6-a558-ddd041b062d1', '1号炉DCS电子间', '0b251a04-c3e1-4ba3-8f65-4f5268b4f353', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (85, 'f12a2ae2-23c9-4a32-8665-3d7daf0c2746', '中二', 'f7ed973e-44dc-4cb6-a558-ddd041b062d1', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (86, 'b0d350d6-d099-4b10-baab-d197f0268e91', '1号炉脱硝B侧CEMS小间', '4a84ba11-1184-4f4f-9cd1-100903a3c680', 1, '2020-05-28 14:52:00', '2020-05-28 14:52:00');
INSERT INTO `sys_region` VALUES (87, '598f3378-fdf0-4503-bcc5-22f65e5478dd', '2号锅炉12.6米门禁', 'aaae135f-2e7a-4c4b-85cd-bc554f6e1b79', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (88, 'f28ad7b3-79a3-48d9-97e5-ed939c1b3ce0', '中二', '9f81b65a-b39e-4b56-81b9-5af7e386f58f', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (89, '46432dc1-4134-46ad-9b6a-37c5961b0418', '2号炉DCS电子间', '598f3378-fdf0-4503-bcc5-22f65e5478dd', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (90, '9ac07e55-e5f6-4319-af9f-66333ca92b39', '中二', '46432dc1-4134-46ad-9b6a-37c5961b0418', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (91, '9c4fdd7d-a99b-4ff2-81e9-fe28f981036c', '2号炉B侧CEMS小间', 'b71e2cc3-3678-47bf-9980-b7eac5e008e9', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (92, '70d0eefb-49b7-4702-b5a4-d6114ef47fa4', '东南门', '9b64662a-a2f0-483b-83ee-9d9f90bf50ba', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (93, 'f3233f2f-084d-4488-a84a-c48a57670de7', '循环水变频间', 'cc8b7597-2104-4167-a275-89006a13211b', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (94, '1314276e-6f2d-45e7-ade7-5e42245083ee', '南门', 'f3233f2f-084d-4488-a84a-c48a57670de7', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (95, '9bcc6a9f-48ed-493c-8469-a888d8cfc92c', '2号汽机工业电视', '6bb4b0fa-76f7-4ddd-afcf-18c4e2c3d14e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (96, 'c9bf8ded-9217-4eaa-93de-fa9e1a499585', '2号汽机门禁', 'cd183457-5fbb-407b-ba8f-ab9be687c4c7', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (97, 'd6fb3f6a-f093-49a2-955f-d2fd22250707', '1号汽机6.3米门禁', 'a5d5275f-21d2-4f6a-86a3-af183be3315d', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (98, '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', '2号汽机6.3米门禁', 'c9bf8ded-9217-4eaa-93de-fa9e1a499585', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (99, 'b2372bab-b595-4d4f-bea3-78c8ecfb3f02', '1号机38V保安段配电室东门', '16b8dffe-f3c1-4a68-bc26-5258c2a8a03a', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (100, '81a07e13-6f58-4982-b577-bf4ebb99403a', '1号机凝结水泵变频间', '6a238436-0373-4895-8d51-c7da4c3e95eb', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (101, '61a1f134-5874-4852-ada6-37f84b0762ba', '1机炉380V PC配电间', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (102, '8f512dff-9fa1-4565-954d-052ba7d9b94f', '1号汽机6KV中二门禁', '150c6f0b-4c40-40b8-8be4-824160e358c2', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (103, '80cb5bac-9dc9-4ef9-b4bf-57f4a9ff892b', '1号机 380V 北一 门禁', '27f912c5-2444-414c-83f3-7a26d3fad4f9', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (104, '0b3e8936-24ca-4c76-8168-ec9821ccd545', '1A1B热网水泵变频间', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (105, 'bd8f68e0-bca4-430e-81f6-27067c69804d', '1A1B热网水泵变频间西门禁', '0b3e8936-24ca-4c76-8168-ec9821ccd545', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (106, '049a6172-765e-4221-992b-c6cede5ef22a', '2号机 380V 保安段配电室 北 门禁', 'd016ad16-44c1-4f18-bb21-c8b0adb92d59', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (107, 'ef216384-dfa9-48d1-aa2a-0627a6b6ba95', '2号汽机主厂房 220V~110V 直流配电间东南门', 'e6b9adf4-607a-4416-abae-08a9dc754837', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (108, '5716cc19-bd90-42d8-8675-4d7473ef674c', '2号汽机 机组蓄电池 西门禁', 'e4bdceb1-441a-4e85-a35c-16a642ab9511', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (109, '70c45d94-7310-49bd-8e37-4ca5bf1b8c93', '2号汽机6.3米 电气电子间 南门 门禁', '60a00eda-1028-4eed-9e98-a17b11450eda', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (110, '6cecad3c-19cd-4581-b028-f1fc922b1448', '2号汽机 汽机及公用DCS电子间门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (111, '93455fa7-d424-4767-ae56-7141a3f37f66', '2号机 6KV 中二 门禁', '9e01b717-f0bf-4ee3-8503-11dc5af04e54', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (112, 'e59d0f99-fb5b-4e36-a149-d03fb685786f', '2号机 公用热网变频间 中二 门禁', 'dd36b4d2-d125-4bb7-9969-fb275328b8eb', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (113, '3fa690e4-45c5-4dfb-8c2f-9cbdea0fb458', '2#机炉 380V PC配电间 中二 门禁', '4b08c92c-df24-4f20-9ae8-e23942e4f153', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (114, 'b6fceaf0-9b2a-470f-8532-5e1ca309c690', '生活污水处理间 北 门禁', '1d64a4ae-6c30-405b-a3b9-4b0caf5826ca', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (115, '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', '输煤区域工业电视', '1ea60dd3-7136-4c6a-955b-01be4feb7275', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (116, '760bb865-79ba-447a-b387-39aa08890d4b', '6号重锤 等离子 380V PC配电间', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (117, '5243a682-8f2c-4b64-a54b-b0a43536b7cc', '6号重锤 等离子 380V PC配电间 西门禁', '760bb865-79ba-447a-b387-39aa08890d4b', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (118, '06c8b4cf-dac9-4f33-b007-2a8ac0e4bfc4', '碎煤机室 380V MCC配电间南门禁', 'a96f53f2-2a4b-4c30-a4ac-8be8c0742801', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (119, '308d54f0-cf41-4a76-8317-9f2e93b46548', '碎煤机室 380V MCC配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (120, '1b50a441-f453-41e1-ab2d-d78d093e922e', '翻车机控制室', '22957c8e-1c5c-4370-b892-daffe6c77b8d', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (121, 'e8f22c80-ef25-4602-ba40-e1127704a4dc', '翻车机 PC 配电间 东门', '75f47652-7e23-4a24-99f8-bea2932abe24', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (122, 'ab570fc4-3d22-43ea-8f4d-b524bdd098cb', '输煤 380V 配电间 门禁 西南', 'e61b39f5-833e-4efa-9a83-19b0517a5023', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (123, '6dfa4c21-e96b-4094-9a10-4356542a4e0e', '1号转运站 380V MCC配电间 东 门禁', '247cfbe0-5155-42de-acd3-4ce1b6909fd5', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (124, 'd4935102-5cb7-4444-8958-678287fd9c93', '燃料智能化DCS 工程师站 北 门禁', '584d4abe-523f-437d-a14c-ec1360bafad4', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (125, 'cd90ccd0-3e8f-4f0c-a5a0-61b081ec7767', '脱硫综合楼门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (126, 'b5c26817-6365-4dc7-9093-9497fb139737', '脱硫交班室', 'cd90ccd0-3e8f-4f0c-a5a0-61b081ec7767', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (127, '7d8b9e31-f273-40fd-9af7-c3f0d27c045b', '脱硫公用380V MCC配电间 西 门禁', '61d1483b-21a8-4405-9746-607e8d8661f4', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (128, '34e34567-446c-46ba-80a7-170a1f327159', '公用脱硫DCS电子间 西 门禁', 'a119271d-0778-4504-88ce-840c4f72bdac', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (129, '747d4e2a-945c-4d78-83e4-aca6065857a3', '二层 1号机脱硫电缆夹层门禁', '05cc4836-c388-42a0-ac63-bafd04077738', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (130, 'ef4b5787-217f-4203-970a-c134b7d656ee', '三层 #1脱硫6KV 配电间 中二 门禁', 'f6c038c4-2179-4ea4-addb-88420b66659e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (131, 'd74c9a61-2ccb-4b10-a2b7-b8bab257699d', '#1脱硫UPS配电间 北 门禁', 'd67d1bb9-4c9b-4f5e-b387-f4c11ef6cf46', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (132, '6d3a8c1d-0249-4223-8546-9254e5ea3965', '五层 1-2号湿除 380V PC配电间 中二 门禁', '2ed2a2f2-170a-4b0d-89c1-027a79e03c89', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (133, '6ba7fdc5-2455-44c5-b926-65666585583b', '湿除 DCS 工程师站 北 门禁', 'e4ea6d01-15e2-4efc-a282-b20f736a70d7', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (134, 'd0f091fc-0d6c-4c04-b820-bda5252b5a74', '二层 2号机脱硫电缆夹层门禁', 'e472c6a3-7c80-4126-af17-a409faee215e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (135, 'af4e13fa-24a6-425d-9f20-79505094ed80', '三层 2号脱硫 6KV 配电间 中二 门禁', '6914c2f4-bb00-4337-b45e-1e16819d4242', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (136, 'a49a3823-cdea-4d1b-a073-981f131ea1bc', '尿素 DCS 电子间  西 门禁', 'b095034c-7afb-494f-b933-b2cb0a4842ae', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (137, '78041817-793f-4dd1-b056-43d445540288', '尿素车间 380V MCC 配电间 门禁', '550fb231-3e87-4d52-b469-4e090f3e4d44', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (138, '89398ffd-1935-4332-bd01-9bfdd3c42eb9', '尿素车间 380V MCC 配电间 西 门禁', '78041817-793f-4dd1-b056-43d445540288', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (139, 'd8c333e4-cd6d-47d6-a052-38ae161db131', '化水 380V PC 配电间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (140, '3c0f6f8e-643a-4b19-9966-a0f5e89ebc13', '化水 380V PC 配电间 北 门禁', 'd8c333e4-cd6d-47d6-a052-38ae161db131', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (141, '421ad202-41f2-4bf5-989f-e5c09eaf6348', '化水药品库 北 门禁', '2ca69399-d948-4e8d-85d6-11f3d5e5843d', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (142, 'd68f18be-4b63-428c-9c40-29eff83bc9f8', '化学储存间 东北 门禁', 'cf52f2dc-b354-453d-be1e-b79873f2f58b', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (143, '960cdec6-cfc8-48e7-a7ed-aa1e4053ed82', '脱硫综合楼二层', 'fd8c4094-53a3-4677-a160-b66a6b5ead0f', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (144, 'e37394f1-17dc-40be-9ab6-0693184eeff2', '脱硫废水楼工业电视', '6ba5912d-486c-405e-80b7-95683137192c', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (145, 'd8ccfd24-978d-4117-8e4c-104abdf9f552', '澄清池', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (146, 'a30bd8bc-e1c7-4b19-bae5-01acf83e2249', '除灰DCS电子间门禁', 'd5351782-1382-49df-b400-8c1f5256bc28', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (147, '7ea47065-78d7-40c1-a37b-fac75aabd86c', '除灰DCS电子间 南二 门禁', 'a30bd8bc-e1c7-4b19-bae5-01acf83e2249', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (148, '3d807e7f-f6ca-4e46-b02f-2dd7a5081469', '除灰380V MCC配电间 南二 门禁', 'd5c72f1d-58bb-4542-a23a-416e4fd48a9e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (149, '426b9b92-ce83-43de-889b-4a8b8abf0a02', '除灰综合楼二层6KV公用段配电间 中二 门禁', '3b22a76d-fb8f-47a8-bc99-95440ac4b62e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (150, '1e926c02-9e94-435e-901b-f6cdf5c28805', 'A斗轮机低压配电室门禁', '98fa5a45-73bb-4d5a-b31b-0c4788dc7e94', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (151, '2db45df0-afcc-4382-9286-d0e3b9b80ad0', '1#电汽电子间 南 门禁', 'e91fcfdc-841b-4dcf-b7e6-e1ba6338c554', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (152, '1c73c374-7ae3-453c-b84e-a71c83123458', ' 1#机组蓄电池 东 门禁', 'a50cb713-3ad3-43d0-abf3-e68bc3428215', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (153, 'af846b35-555e-453f-9550-ed9b63653279', '集控NVR-2', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (154, '0b251a04-c3e1-4ba3-8f65-4f5268b4f353', '1号锅炉12.6米门禁', '34c36c37-ed4a-4b27-9d35-5a5e478e6abe', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (155, '7b708648-18eb-46dd-9ab0-fceec9f7fbf5', '南三', '0a85296a-b6d9-48c6-bb0c-36053c78ea1d', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (156, 'dc6fa23c-e12e-4827-aaf1-a740572aa7c5', '北一', 'f7ed973e-44dc-4cb6-a558-ddd041b062d1', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (157, '2925d0f6-f814-4f87-84aa-ecfebcf25bb5', '1号炉脱硝MCC配电间', '4a84ba11-1184-4f4f-9cd1-100903a3c680', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (158, '842d16a7-3356-4314-bdce-db572ceb8d5f', '北三', '9f81b65a-b39e-4b56-81b9-5af7e386f58f', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (159, 'b23739ac-fb5d-4349-8991-6621cb7871a6', '南一', '46432dc1-4134-46ad-9b6a-37c5961b0418', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (160, 'b71e2cc3-3678-47bf-9980-b7eac5e008e9', '2号锅炉5层半门禁', 'aaae135f-2e7a-4c4b-85cd-bc554f6e1b79', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (161, '00ebfd23-9f94-4bc8-91bb-cf0298c38410', '2号炉脱硝MCC配电间', 'b71e2cc3-3678-47bf-9980-b7eac5e008e9', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (162, '7f4d4511-8d24-47cd-ae9c-db12455dcd0e', '通讯机房北门', '9b64662a-a2f0-483b-83ee-9d9f90bf50ba', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (163, '39bd4e3c-4b80-427b-9f2c-678c1e682490', '1号汽机12.6米门禁', 'a5d5275f-21d2-4f6a-86a3-af183be3315d', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (164, 'a644242e-fc35-462d-9770-011cd9ef3af5', '2号汽机12.6米门禁', 'c9bf8ded-9217-4eaa-93de-fa9e1a499585', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (165, '73c1dcf4-b9a3-425d-a16b-e86159d0b74b', '1号机38V保安段配电室南门', '16b8dffe-f3c1-4a68-bc26-5258c2a8a03a', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (166, '7f91f068-58eb-48ee-8467-9da15e5a9cbf', '1号机热网循环水泵变频间', '6a238436-0373-4895-8d51-c7da4c3e95eb', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (167, '27f912c5-2444-414c-83f3-7a26d3fad4f9', '1号机 380V 门禁', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (168, '51eb742b-8726-43c9-93db-499c3ff1dbc7', '1号汽机6KV北三门禁', '150c6f0b-4c40-40b8-8be4-824160e358c2', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (169, '8719fa42-1c3a-4b9f-b8c4-4fec2a4d3502', '2号机 380V 保安段配电室 东 门禁', 'd016ad16-44c1-4f18-bb21-c8b0adb92d59', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (170, 'e65e3834-9bcb-46d5-bc70-b7ef59e1e522', '2号汽机 公用电子间门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (171, 'e4fa47ec-292b-4115-bebc-2126605ee890', '2号机 6KV 北一 门禁', '9e01b717-f0bf-4ee3-8503-11dc5af04e54', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (172, 'f3d012d7-9de8-472c-ad91-4c1b88d01d1c', '2#机炉 380V PC配电间 北三 门禁', '4b08c92c-df24-4f20-9ae8-e23942e4f153', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (173, 'd209cf83-5a97-43ab-81e7-53edd338b227', '2号机凝泵变频间', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (174, 'f3895adb-cc55-46e5-a4ed-caad4cf0cca4', '循环水工业电视', '6bb4b0fa-76f7-4ddd-afcf-18c4e2c3d14e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (175, 'a96f53f2-2a4b-4c30-a4ac-8be8c0742801', '碎煤机室 380V MCC配电间门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (176, 'cb64653f-86a2-4a08-94d5-8aba248f0125', '翻车机 PC 配电间 南门', '75f47652-7e23-4a24-99f8-bea2932abe24', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (177, '73764444-2a2d-4598-900a-61e2a64c34ca', '输煤 380V 配电间 门禁 西', 'e61b39f5-833e-4efa-9a83-19b0517a5023', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (178, '7b78de46-a982-4ffe-afe5-42a70dbda807', '燃料智能化 380V MCC配电间', '584d4abe-523f-437d-a14c-ec1360bafad4', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (179, '61d1483b-21a8-4405-9746-607e8d8661f4', '脱硫公用380V MCC配电间门禁', 'cd90ccd0-3e8f-4f0c-a5a0-61b081ec7767', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (180, 'fb26349a-a45d-4c25-8cb3-d6b62816b6e4', '脱硫盐酸间 门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (181, 'f6c038c4-2179-4ea4-addb-88420b66659e', '三层 #1脱硫6KV 配电间门禁', '05cc4836-c388-42a0-ac63-bafd04077738', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (182, '2ac9f04c-6677-424f-8212-1a1b56afd270', '三层 #1脱硫6KV 配电间 南三 门禁', 'f6c038c4-2179-4ea4-addb-88420b66659e', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (183, '688d5c00-9868-4aee-a4e4-91c8be579785', '五层 1-2号湿除 380V PC配电间 南三 门禁', '2ed2a2f2-170a-4b0d-89c1-027a79e03c89', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (184, 'd1291e29-5e74-42c1-b3c0-0d1e0b3309d5', '三层 2号脱硫 6KV 配电间 北一 门禁', '6914c2f4-bb00-4337-b45e-1e16819d4242', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (185, '68e38ec2-7af1-4332-8747-3676242cd771', '尿素水解反应区 门禁', '550fb231-3e87-4d52-b469-4e090f3e4d44', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (186, 'bb4a78e9-3d55-4678-99c2-acad98380f47', '化水 380V PC 配电间 东北 门禁', 'd8c333e4-cd6d-47d6-a052-38ae161db131', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (187, '2ca69399-d948-4e8d-85d6-11f3d5e5843d', '化水药品库门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (188, 'e302c702-2952-41d3-8f0f-2d90ec50bde3', '化学储存间 东南 门禁', 'cf52f2dc-b354-453d-be1e-b79873f2f58b', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (189, '71d2b13c-d6c6-4e20-981f-f043f3f9e735', '1号氧化风机房', '6ba5912d-486c-405e-80b7-95683137192c', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (190, '43895d87-b15f-4773-a19d-96ece6e86d4e', '拉泥间', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:01', '2020-05-28 14:52:01');
INSERT INTO `sys_region` VALUES (191, 'd5c72f1d-58bb-4542-a23a-416e4fd48a9e', '除灰380V MCC配电间门禁', 'd5351782-1382-49df-b400-8c1f5256bc28', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (192, '2cdc6292-7498-49f5-85de-54d3445b1f0f', '除灰综合楼二层6KV公用段配电间 南三 门禁', '3b22a76d-fb8f-47a8-bc99-95440ac4b62e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (193, 'd579766a-a54e-4bfa-bdbc-c272de5cd38f', 'B斗轮机高压配电室门禁', '98fa5a45-73bb-4d5a-b31b-0c4788dc7e94', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (194, '2c58e81b-456f-484a-8c84-136f67b9c517', '集控NVR-3', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (195, '4a84ba11-1184-4f4f-9cd1-100903a3c680', '1号炉5层半', '34c36c37-ed4a-4b27-9d35-5a5e478e6abe', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (196, 'c9fcd2d0-f9d4-4086-8260-2bda06859ddd', '2号锅炉9层门禁', 'aaae135f-2e7a-4c4b-85cd-bc554f6e1b79', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (197, 'ad1974f4-e379-4e33-96b4-5cd973217371', '蓄电池南门', '9b64662a-a2f0-483b-83ee-9d9f90bf50ba', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (198, 'ab5fa60e-c802-406d-9377-1986a0b317dd', '1号锅炉工业电视', '5fcd4ef5-4647-4708-9ef8-759c785d6eb2', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (199, '9e01b717-f0bf-4ee3-8503-11dc5af04e54', '2号机 6KV 门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (200, 'f35e169a-23d7-4b6b-94f4-627eeef65036', '2号机继电器室 380V MCC配电室', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (201, '44e71bb6-745b-47f2-bbb7-4f9463d0451c', '生活污水工业电视', '6bb4b0fa-76f7-4ddd-afcf-18c4e2c3d14e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (202, 'af4020e8-098f-4af8-a0f8-88b825792264', '2号转运站 380V MCC配电间', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (203, 'a119271d-0778-4504-88ce-840c4f72bdac', '公用脱硫DCS电子间', 'cd90ccd0-3e8f-4f0c-a5a0-61b081ec7767', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (204, '87ce04ad-79e7-4592-9f2e-b89865d525bb', '脱硫废水间门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (205, 'd67d1bb9-4c9b-4f5e-b387-f4c11ef6cf46', '#1脱硫UPS配电间门禁', 'f6c038c4-2179-4ea4-addb-88420b66659e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (206, '2ed2a2f2-170a-4b0d-89c1-027a79e03c89', '五层 1-2号湿除 380V PC配电间门禁', '05cc4836-c388-42a0-ac63-bafd04077738', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (207, 'e4ea6d01-15e2-4efc-a282-b20f736a70d7', '湿除 DCS 工程师站', '2ed2a2f2-170a-4b0d-89c1-027a79e03c89', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (208, '9b191954-b9da-4a33-a70a-5c7eb3390c7b', '三层 2号脱硫DCS电子间门禁', 'e472c6a3-7c80-4126-af17-a409faee215e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (209, '98bc0986-dc06-4d81-b1b4-96ddeeded334', '2号氧化风机房', '6ba5912d-486c-405e-80b7-95683137192c', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (210, '8584df56-a5fb-4e41-9c6e-4808ba750a65', '变空隙滤池间', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (211, '3b22a76d-fb8f-47a8-bc99-95440ac4b62e', '除灰综合楼二层6KV公用段配电间门禁', 'd5351782-1382-49df-b400-8c1f5256bc28', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (212, '2a32dc2d-b92e-4691-a222-db131dc88b41', 'B斗轮机低压配电室门禁', '98fa5a45-73bb-4d5a-b31b-0c4788dc7e94', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (213, 'e0c2df56-2655-406f-b02c-7c631f1cb46c', '1号机精处理DCS电子间门禁', '6a238436-0373-4895-8d51-c7da4c3e95eb', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (214, 'e91fcfdc-841b-4dcf-b7e6-e1ba6338c554', '1#电汽电子间门禁', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (215, '2bddfd42-024b-4309-bee2-e31c8a3527b3', '集控NVR-4', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (216, '5c9fbf8a-3e6e-4e47-b920-42805a4ef3a5', '燃料智能化二层中间过道门禁', '584d4abe-523f-437d-a14c-ec1360bafad4', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (217, 'c1d12a2f-b13c-440a-96d8-94586d86d933', '1号锅炉9层门禁', '34c36c37-ed4a-4b27-9d35-5a5e478e6abe', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (218, 'd016ad16-44c1-4f18-bb21-c8b0adb92d59', '2号机 380V 保安段配电室门禁', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (219, 'dd36b4d2-d125-4bb7-9969-fb275328b8eb', '2号机公用热网变频间门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (220, '22957c8e-1c5c-4370-b892-daffe6c77b8d', '输煤翻车机控制楼', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (221, '05cc4836-c388-42a0-ac63-bafd04077738', '1号氧化风机房门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (222, 'be3197a2-b14a-4ec8-bd67-73b1be40e927', '顶层 1号炉净烟气CEMS小间门禁', '05cc4836-c388-42a0-ac63-bafd04077738', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (223, '6914c2f4-bb00-4337-b45e-1e16819d4242', '三层 2号脱硫 6KV 配电间 门禁', 'e472c6a3-7c80-4126-af17-a409faee215e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (224, 'cf52f2dc-b354-453d-be1e-b79873f2f58b', '化学储存间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (225, 'acec648b-0783-406a-b551-578e556b05eb', '输煤燃料智能化 380V MCC配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (226, '3822d519-b1a7-42c5-bcfa-17e946848e24', '再生酸碱罐间', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (227, '2f4779bd-829a-4e6f-b0cb-b8a64b3c5b68', '1#汽机及公用DCS电子间门禁', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (228, 'a50cb713-3ad3-43d0-abf3-e68bc3428215', '1#机组蓄电池门禁', '6a238436-0373-4895-8d51-c7da4c3e95eb', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (229, '59205acf-d3e8-408c-b18b-1ec81263d59f', '集控NVR-5', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (230, 'e6b9adf4-607a-4416-abae-08a9dc754837', '2号汽机主厂房 220V~110V 直流配电间', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (231, 'd4dbc82d-1e79-4ad1-9398-06dab50ae0d7', '2号机热网DCS电子间 门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (232, '247cfbe0-5155-42de-acd3-4ce1b6909fd5', '1号转运站 380V MCC配电间', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (233, 'e472c6a3-7c80-4126-af17-a409faee215e', '2号氧化风机房门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (234, '1ea5c5a9-f574-4b21-a0ba-a66aa7854b37', '顶层 2号炉净烟气 CEMS 小间 门禁', 'e472c6a3-7c80-4126-af17-a409faee215e', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (235, 'eb16a7f2-ef8c-44e6-b5c5-58808f392ec8', '再生水深度处理DCS间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (236, '114bc3ec-4f40-4d42-84d8-dbaddc0f46f5', '翻车机控制楼(MCC)', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (237, '9bf57d2a-1a28-4b05-9eb0-20d1b9443036', '再生MCC', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (238, 'ae4b8704-6d56-4ee0-9e0b-ef151478b571', '1#汽机6.3m汽机执行机构电源间', 'd6fb3f6a-f093-49a2-955f-d2fd22250707', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (239, '57d74014-48e2-4979-8007-2e842049c454', '集控NVR-6', 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (240, '6bb4b0fa-76f7-4ddd-afcf-18c4e2c3d14e', '汽机-循环水-生活污水工业电视', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (241, 'e4bdceb1-441a-4e85-a35c-16a642ab9511', '2号汽机 机组蓄电池 门禁', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (242, '4b08c92c-df24-4f20-9ae8-e23942e4f153', '2#机炉 380V PC配电间 门禁', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (243, '75f47652-7e23-4a24-99f8-bea2932abe24', '翻车机 PC 配电间 门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (244, 'a72c594a-38e8-477f-b390-1b010f4dd7cd', '再生水深度处理380V MCC配电间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (245, '8f9bad79-d1f4-43b8-928e-471807a0f5bc', '输煤1号转运站380B MCC配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (246, '40b5cc9b-59ea-46fa-aa03-481c306af6eb', '磨细控制设备间门禁', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (247, '6a1c7079-71b0-4f6e-9f14-3831c6563dc6', '精处理再生间', 'fb6baa94-04f2-4e86-8215-fb5735e2ad3f', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (248, '7a3af0a0-0538-4b80-bdf3-405579b1e01f', '2号锅炉工业电视', '5fcd4ef5-4647-4708-9ef8-759c785d6eb2', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (249, '78c7515b-7825-44ce-8f27-8957f65bc707', '2# 汽机执行机构电源间', '1f9d6cfc-7633-47a3-a26d-09d04f248ea0', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (250, 'e61b39f5-833e-4efa-9a83-19b0517a5023', '输煤 380V 配电间 门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (251, '2b292701-a0a8-4e37-8194-8fbfb59efe36', '再生水深度处理MCC配电间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (252, '54f5d64b-440d-4e19-9c64-5959cb50766d', '输煤2号转运站 380V MCC配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (253, 'a6f4301d-874c-4f25-9ebd-b3a5920c90a9', '细灰库二层除灰远程DCS电子间', '02d2a173-16b7-4cca-99df-1f8dac0039fa', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (254, '6227b6f5-cd38-4c1d-aab9-d0b9abcbf6d7', '2号机精处理DCS电子间门禁', '68e2d51d-7c63-40f2-af64-15a56fbe18b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (255, 'cd183457-5fbb-407b-ba8f-ab9be687c4c7', '汽机门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (256, 'd21ad13d-9e66-4e08-8d80-6a79d8f993bf', '煤泥水 处理间 东 门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (257, '159a3e38-2f04-4d7d-a258-e9fa110bc626', '翻车机PC配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (258, '864853ad-c869-4126-9f27-fa7064d08124', '再生酸碱间门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (259, '34c36c37-ed4a-4b27-9d35-5a5e478e6abe', '1号锅炉门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (260, '93df6678-b288-43e3-9558-9213a9f5a65f', '煤泥间 药品库 门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (261, '7c7f6542-6866-43b5-b57a-4686c17cca80', '煤泥水间配电室', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (262, 'e2e60fb8-2bca-496e-931b-e867fff6a767', '再生MCC配电室门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (263, 'aaae135f-2e7a-4c4b-85cd-bc554f6e1b79', '2号锅炉门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (264, '584d4abe-523f-437d-a14c-ec1360bafad4', '燃料智能化区域 门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (265, '9a764a7f-d1e5-4e0b-a194-30d4e529c77a', '输煤380V 配电间', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (266, '70582ff3-b1e1-4edb-bf5e-e3f42fb50f43', '化学控制室门禁', '9a234464-a76e-4f0d-9716-fa09e9a45456', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (267, '66ebd902-afd4-4ff6-9b6f-bc9a1c945f0f', '1号升压站门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (268, '3765f9b4-32f8-419f-9b75-8a44720a6546', '碎煤机室外侧防爆枪机', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (269, '98fa5a45-73bb-4d5a-b31b-0c4788dc7e94', '输煤 煤仓门禁', '4ccff583-4011-4bf1-8b40-2d907167d2b5', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (270, 'bd5bc131-67dd-46a0-bc0e-41b43ca49b39', '循环水泵房门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (271, 'cf66b86f-5bba-448d-94eb-eb1edc20f6a1', '输煤 煤仓 A-B斗轮机高低压配电室监控', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (272, '1d64a4ae-6c30-405b-a3b9-4b0caf5826ca', '生活污水处理间门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (273, '4d17d035-3ccd-4248-8750-35158e085869', '输煤 煤仓西侧2A-2B输送机', '041ff13d-9283-4e3b-acb8-59e7bea9f5b8', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (274, 'a567669b-bfc5-4931-8049-ae6fb250c075', '东门门口', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (275, '1ea60dd3-7136-4c6a-955b-01be4feb7275', '输煤区域', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (276, '02d2a173-16b7-4cca-99df-1f8dac0039fa', '脱硫区域', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (277, '550fb231-3e87-4d52-b469-4e090f3e4d44', '尿素区域门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (278, '9a234464-a76e-4f0d-9716-fa09e9a45456', '化学区域', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (279, 'e97028e8-c8b0-4324-9430-2b1679ece082', '南门门口', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (280, 'd5351782-1382-49df-b400-8c1f5256bc28', '除灰综合楼门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (281, 'ebb930b2-c620-45a3-b1eb-be4c85954b7b', '空压机房工业电视', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (282, 'b4c51a09-cc57-470b-a2e9-ed0266c2373d', '厂区前PC门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (283, 'e6e9043d-4cc2-41cc-8d30-730334cdcb52', '旧工业电视', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (284, '8860bcac-efad-4418-b6a9-20990b4a309d', '管控室门禁', 'root000000', 1, '2020-05-28 14:52:02', '2020-05-28 14:52:02');
INSERT INTO `sys_region` VALUES (285, 'a2b6e037-a2dc-4ae0-913f-cd7adb451f4d', '管控室监控', 'root000000', 1, '2020-05-28 14:52:03', '2020-05-28 14:52:03');
INSERT INTO `sys_region` VALUES (286, '61920d93-31fe-4059-8f2e-ca6408e7a51f', '集控室门禁', 'root000000', 1, '2020-05-28 14:52:03', '2020-05-28 14:52:03');
INSERT INTO `sys_region` VALUES (287, '7ff69156-8c23-4fab-88c5-d1d4e0caef34', '办票室门禁', 'root000000', 1, '2020-05-28 14:52:03', '2020-05-28 14:52:03');
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
INSERT INTO `sys_role` VALUES (2, '门卫', '门卫', 1, '2017-11-22 16:24:34', '2020-05-28 13:45:05', '1');
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
