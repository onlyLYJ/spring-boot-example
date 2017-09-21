/*
 Navicat Premium Data Transfer

 Source Server         : jc_apply
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : jc_apply

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 21/09/2017 11:58:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `apply_begin_time` datetime DEFAULT NULL COMMENT '报名开始时间',
  `apply_end_time` datetime DEFAULT NULL COMMENT '报名结束时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `apply_num` int(11) DEFAULT '0' COMMENT '报名人数',
  `status` varchar(2) DEFAULT NULL COMMENT '0 正常 1 关闭',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='活动';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES (1, '加班餐（晚餐）', '2017-07-17 18:00:01', '2017-07-17 09:00:01', '2017-07-17 16:00:01', '加班餐，未报名但加班用餐者扣十元！', 0, '0', '2017-07-17 00:40:02', NULL);
INSERT INTO `activity` VALUES (2, '123', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '123', 0, '0', '2017-09-21 11:19:46', NULL);
INSERT INTO `activity` VALUES (3, '加班餐（晚餐）', '2017-09-21 18:00:00', '2017-09-21 09:00:00', '2017-09-21 16:00:00', '加班餐，未报名但加班用餐者扣十元！', 0, '0', '2017-09-21 11:33:12', NULL);
INSERT INTO `activity` VALUES (4, '1234', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '1234', 0, '0', '2017-09-21 11:48:11', NULL);
INSERT INTO `activity` VALUES (5, '44', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '444', 0, '0', '2017-09-21 11:56:51', NULL);
COMMIT;

-- ----------------------------
-- Table structure for activity_apply
-- ----------------------------
DROP TABLE IF EXISTS `activity_apply`;
CREATE TABLE `activity_apply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL COMMENT '活动id',
  `employee_id` int(11) DEFAULT NULL COMMENT '员工id',
  `activity_name` varchar(200) DEFAULT NULL COMMENT '活动名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真是姓名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` varchar(2) DEFAULT NULL COMMENT '0 报名 1 取消报名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='活动报名表';

-- ----------------------------
-- Records of activity_apply
-- ----------------------------
BEGIN;
INSERT INTO `activity_apply` VALUES (1, 1, 1, '午餐', '朱圣然', '报名报名', '1', '2017-07-15 09:15:00', '2017-07-15 09:23:40');
COMMIT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `department` varchar(50) DEFAULT '未知' COMMENT '部门',
  `real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `english_name` varchar(50) DEFAULT NULL COMMENT '英文名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='员工';

-- ----------------------------
-- Records of employee
-- ----------------------------
BEGIN;
INSERT INTO `employee` VALUES (1, '技术部', '朱圣然', 'jasonzhu', '2017-07-15 00:17:36', NULL);
INSERT INTO `employee` VALUES (2, '技术部', '李一俊', 'Steven', '2017-09-16 07:02:31', NULL);
INSERT INTO `employee` VALUES (4, '市场运营部', '陈世美', 'JsonBeauty', '2017-09-20 07:02:16', NULL);
INSERT INTO `employee` VALUES (5, '财务部', '钱太多', 'MoneyMore', '2017-09-20 07:07:49', NULL);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom`;
CREATE TABLE `meetingroom` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `roomname` varchar(50) DEFAULT NULL COMMENT '会议室名',
  `status` varchar(2) DEFAULT '0' COMMENT '使用状态 0正常 1关闭',
  `capacity` int(11) unsigned DEFAULT '10' COMMENT '可容纳人数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='会议室';

-- ----------------------------
-- Records of meetingroom
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom` VALUES (1, '1号会议室', '0', 30, '豪华配置', '2017-09-13 06:30:53', '2017-09-21 06:31:03');
INSERT INTO `meetingroom` VALUES (2, '2号会议室', '0', 10, '适合大场面会谈', '2017-09-25 06:30:56', '2017-09-21 06:31:05');
INSERT INTO `meetingroom` VALUES (3, '3号会议室', '1', 50, '装修中', '2017-09-21 06:31:00', '2017-09-21 06:31:08');
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_change
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_change`;
CREATE TABLE `meetingroom_book_change` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `meetingroom_book_details_id` int(11) unsigned DEFAULT NULL COMMENT '会议室预定详情id',
  `change_reason` varchar(200) DEFAULT NULL COMMENT '预定变化原因',
  `audit_status` varchar(2) DEFAULT NULL COMMENT '状态 0待审核，1审核通过，2审核不通过, 3取消',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='会议室预定变更请求';

-- ----------------------------
-- Records of meetingroom_book_change
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_change` VALUES (1, 1, '时间冲突', '1', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_detail
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_detail`;
CREATE TABLE `meetingroom_book_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) unsigned DEFAULT NULL COMMENT '员工id',
  `meetingroom_id` int(11) unsigned DEFAULT NULL COMMENT '会议室id',
  `book_reason` varchar(200) DEFAULT NULL COMMENT '申请原因',
  `attend_num` int(5) unsigned DEFAULT NULL COMMENT '与会人数',
  `audit_status` varchar(2) DEFAULT NULL COMMENT '状态 0待审核，1审核通过，2审核不通过，3取消',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '申请提交时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='会议室预定详情';

-- ----------------------------
-- Records of meetingroom_book_detail
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_detail` VALUES (1, 1, 1, '架构选型说明', 12, '1', '重要！！', '2017-08-29 08:58:51', '2017-09-21 06:31:22');
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '权限名',
  `description` varchar(200) DEFAULT NULL COMMENT '介绍',
  `url` varchar(500) DEFAULT NULL COMMENT '链接',
  `method` varchar(11) DEFAULT NULL COMMENT '调用方法',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'P_A', '测试', '/test', 'ALL');
INSERT INTO `permission` VALUES (2, 'ROLE_ADMIN', '管理', '/admin/index', 'ALL');
INSERT INTO `permission` VALUES (3, 'ROLE_ADMIN', '管理', '/meetingroom', 'ALL');
INSERT INTO `permission` VALUES (4, 'ROLE_ADMIN', '管理', '/meetingroom/add', 'ALL');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'ROLE_USER');
INSERT INTO `role` VALUES (2, 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'jasonzhu', '$2a$10$lsdNtJi36afQ87NfaN2fvuk25s35.HzSdItDSGD11x7tb7mTwbLGO');
INSERT INTO `user` VALUES (2, 'admin', '$2a$10$BRp7lCwoSZzV7K4httDRvOwHxuJvOSbHqZwSk17nf0pnVV/xtTJZa');
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (3, 2, 1);
INSERT INTO `user_role` VALUES (4, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
