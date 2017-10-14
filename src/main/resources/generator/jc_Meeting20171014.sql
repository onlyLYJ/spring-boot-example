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

 Date: 14/10/2017 16:03:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id`               INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `activity_name`    VARCHAR(100)              DEFAULT NULL
  COMMENT '活动名',
  `start_time`       DATETIME                  DEFAULT NULL
  COMMENT '开始时间',
  `apply_begin_time` DATETIME                  DEFAULT NULL
  COMMENT '报名开始时间',
  `apply_end_time`   DATETIME                  DEFAULT NULL
  COMMENT '报名结束时间',
  `remark`           VARCHAR(500)              DEFAULT NULL
  COMMENT '备注',
  `apply_num`        INT(11)                   DEFAULT '0'
  COMMENT '报名人数',
  `status`           VARCHAR(2)                DEFAULT NULL
  COMMENT '0 正常 1 关闭',
  `create_time`      DATETIME                  DEFAULT NULL,
  `update_time`      DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8
  COMMENT ='活动';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES
  (1, '加班餐（晚餐）', '2017-07-17 18:00:01', '2017-07-17 09:00:01', '2017-07-17 16:00:01', '加班餐，未报名但加班用餐者扣十元！', 0, '0',
   '2017-07-17 00:40:02', NULL);
INSERT INTO `activity` VALUES
  (2, '123', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '123', 0, '0', '2017-09-21 11:19:46',
   NULL);
INSERT INTO `activity` VALUES
  (3, '加班餐（晚餐）', '2017-09-21 18:00:00', '2017-09-21 09:00:00', '2017-09-21 16:00:00', '加班餐，未报名但加班用餐者扣十元！', 0, '0',
   '2017-09-21 11:33:12', NULL);
INSERT INTO `activity` VALUES
  (4, '1234', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '1234', 0, '0',
   '2017-09-21 11:48:11', NULL);
INSERT INTO `activity` VALUES
  (5, '44', '2017-09-21 09:00:00', '2017-09-21 09:00:00', '2017-09-21 18:00:00', '444', 0, '0', '2017-09-21 11:56:51',
   NULL);
INSERT INTO `activity` VALUES
  (6, '加班餐（晚餐）', '2017-09-23 18:00:00', '2017-09-23 09:00:00', '2017-09-23 16:00:00', '加班餐，未报名但加班用餐者扣十元！', 0, '0',
   '2017-09-23 18:43:55', NULL);
INSERT INTO `activity` VALUES
  (7, '加班餐（晚餐）', '2017-09-24 18:00:00', '2017-09-24 09:00:00', '2017-09-24 16:00:00', '加班餐，未报名但加班用餐者扣十元！', 0, '0',
   '2017-09-24 22:29:00', NULL);
COMMIT;

-- ----------------------------
-- Table structure for activity_apply
-- ----------------------------
DROP TABLE IF EXISTS `activity_apply`;
CREATE TABLE `activity_apply` (
  `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `activity_id`   INT(11)                   DEFAULT NULL
  COMMENT '活动id',
  `user_id`       INT(11)                   DEFAULT NULL
  COMMENT '员工id',
  `activity_name` VARCHAR(200)              DEFAULT NULL
  COMMENT '活动名',
  `real_name`     VARCHAR(50)               DEFAULT NULL
  COMMENT '真是姓名',
  `remark`        VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
  `status`        VARCHAR(2)                DEFAULT NULL
  COMMENT '0 报名 1 取消报名',
  `create_time`   DATETIME                  DEFAULT NULL,
  `update_time`   DATETIME                  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='活动报名表';

-- ----------------------------
-- Records of activity_apply
-- ----------------------------
BEGIN;
INSERT INTO `activity_apply` VALUES (1, 1, 1, '午餐', '朱圣然', '报名报名', '1', '2017-07-15 09:15:00', '2017-07-15 09:23:40');
COMMIT;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_name`   VARCHAR(50)               DEFAULT NULL
  COMMENT '部门名称',
  `create_time` DATETIME                  DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  `enable`      VARCHAR(2)                DEFAULT NULL
  COMMENT '可用情况 0可用 1不可用',
  `extra`       VARCHAR(50)               DEFAULT NULL
  COMMENT '备用字段',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES (1, '总经办', '2017-09-28 16:47:03', NULL, '0', NULL);
INSERT INTO `department` VALUES (2, '市场销售部', '2017-09-28 16:47:09', NULL, '0', NULL);
INSERT INTO `department` VALUES (3, '运营部', '2017-09-28 16:47:11', NULL, '0', NULL);
INSERT INTO `department` VALUES (4, '技术部', '2017-09-28 16:47:12', NULL, '0', NULL);
INSERT INTO `department` VALUES (5, '行政部', '2017-09-28 16:47:13', NULL, '0', NULL);
INSERT INTO `department` VALUES (6, '财务部', '2017-09-28 16:47:59', NULL, '0', NULL);
INSERT INTO `department` VALUES (7, '产品部', '2017-09-28 16:48:01', NULL, '0', NULL);
INSERT INTO `department` VALUES (8, '人力资源部', '2017-09-28 16:49:50', NULL, '0', NULL);
INSERT INTO `department` VALUES (9, '供应链管理部', '2017-09-28 16:49:50', NULL, '0', NULL);
INSERT INTO `department` VALUES (10, '设计部', '2017-09-28 16:49:50', NULL, '0', NULL);
COMMIT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `department`   VARCHAR(25)               DEFAULT '未知'
  COMMENT '部门',
  `real_name`    VARCHAR(25)               DEFAULT NULL
  COMMENT '姓名',
  `english_name` VARCHAR(25)               DEFAULT NULL
  COMMENT '英文名',
  `create_time`  DATETIME                  DEFAULT NULL,
  `update_time`  DATETIME                  DEFAULT NULL,
  `password`     VARCHAR(66)               DEFAULT NULL
  COMMENT '密码',
  `extra`        VARCHAR(10)               DEFAULT NULL
  COMMENT '其他',
  `enable`       VARCHAR(2)                DEFAULT NULL
  COMMENT '可用状态 0可用 1不可用',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8
  COMMENT ='员工';

-- ----------------------------
-- Records of employee
-- ----------------------------
BEGIN;
INSERT INTO `employee` VALUES (1, '技术部', '朱圣然', 'jasonzhu', '2017-07-15 00:17:36', '2017-07-15 00:17:36',
                               '$2a$10$W/C4rQo9DWYy3Pz0RuJXdO5Wh0yO31UlU8lblh8SiSQBBEk62ADA6', NULL, '0');
INSERT INTO `employee` VALUES (2, '技术部', '李一俊', 'steven', '2017-07-15 00:17:36', '2017-07-15 00:17:36',
                               '$2a$10$W/C4rQo9DWYy3Pz0RuJXdO5Wh0yO31UlU8lblh8SiSQBBEk62ADA6', NULL, '0');
INSERT INTO `employee` VALUES (6, '技术部', '杨广', 'yangguang', '2017-09-28 14:07:38', '2017-09-28 14:07:38',
                               '$2a$10$m9KSil6QtxdQNC9iOFy7DeGEnI4nvZWJltlgLFz/A03k8GO0dxFFe', NULL, '0');
INSERT INTO `employee` VALUES (7, '技术部', '刁林', 'diaolin', '2017-09-28 15:44:49', '2017-09-28 15:44:49',
                               '$2a$10$9sRQs48aytri5dX9GN30Suh.99WL4cGiwUS1Fd/N0Ss0tarFToy7i', NULL, '0');
INSERT INTO `employee` VALUES (8, '技术部', '丁家星', 'jackDing', '2017-09-28 17:23:46', '2017-09-28 17:23:46',
                               '$2a$10$gt16YRtH9rM/YiqidoJKGuqoq579srIfiURppjznmJDHXGjzhONx.', NULL, '0');
INSERT INTO `employee` VALUES (9, '技术部', '123', 'steven123', '2017-10-14 15:51:41', NULL,
                               '$2a$10$y4LonMoevOCIEmvvzA4RpekmRuJ8Dyr9KNFPBeIJtH52I17ocstpi', NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for meetingroom
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom`;
CREATE TABLE `meetingroom` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_name`   VARCHAR(50)               DEFAULT NULL
  COMMENT '会议室名',
  `status`      VARCHAR(2)                DEFAULT '0'
  COMMENT '使用状态 0正常 1关闭',
  `capacity`    INT(11) UNSIGNED          DEFAULT '10'
  COMMENT '可容纳人数',
  `remark`      VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
  `create_time` DATETIME                  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8
  COMMENT ='会议室';

-- ----------------------------
-- Records of meetingroom
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom` VALUES (1, '1号会议室', '0', 35, '豪华，大气，上档次！！！', '2017-10-09 08:09:48', '2017-10-09 08:09:48');
INSERT INTO `meetingroom` VALUES (2, '2号会议室', '0', 100, '接见领导人专用', '2017-10-09 08:08:26', '2017-10-14 15:24:25');
INSERT INTO `meetingroom` VALUES (3, '3号会议室', '0', 25, '装修中', '2017-10-09 08:09:25', '2017-10-09 08:09:25');
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_change
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_change`;
CREATE TABLE `meetingroom_book_change` (
  `id`                         INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `meetingroom_book_detail_id` INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '会议室预定详情id',
  `change_reason`              VARCHAR(200)              DEFAULT NULL
  COMMENT '预定变化原因',
  `audit_status`               VARCHAR(2)                DEFAULT NULL
  COMMENT '状态 0待审核，1审核通过，2审核不通过, 3取消',
  `create_time`                DATETIME                  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`                DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  `employee_id`                INT(11)                   DEFAULT NULL
  COMMENT '申请人id',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='会议室预定变更请求';

-- ----------------------------
-- Records of meetingroom_book_change
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_change` VALUES (1, 342, '123', NULL, '2017-10-14 15:25:16', NULL, 2);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_detail
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_detail`;
CREATE TABLE `meetingroom_book_detail` (
  `id`                 INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_id`        INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '用户id',
  `meetingroom_id`     INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '会议室id',
  `meeting_begin_time` DATETIME                  DEFAULT NULL
  COMMENT '会议开始时间',
  `meeting_end_time`   DATETIME                  DEFAULT NULL
  COMMENT '会议结束时间',
  `book_reason`        VARCHAR(200)              DEFAULT NULL
  COMMENT '申请原因',
  `attend_num`         INT(5) UNSIGNED           DEFAULT NULL
  COMMENT '与会人数',
  `audit_status`       VARCHAR(2)                DEFAULT NULL
  COMMENT '状态 0待审核，1审核通过，2审核不通过，3取消',
  `remark`             VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
  `create_time`        DATETIME                  DEFAULT CURRENT_TIMESTAMP
  COMMENT '申请提交时间,使用mysql自动生成的时间',
  `update_time`        DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  `status`             VARCHAR(2)                DEFAULT NULL
  COMMENT '预定过期状态，0未过期，1过期',
  `dept_id`            INT(11)                   DEFAULT NULL
  COMMENT '预定部门id',
  PRIMARY KEY (`id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 344
  DEFAULT CHARSET = utf8
  COMMENT ='会议室预定详情';

-- ----------------------------
-- Records of meetingroom_book_detail
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_detail` VALUES
  (1, 1, 2, '2017-09-24 10:32:05', '2017-09-24 13:32:15', '架构选型说明', 12, '1', '重要！！', '2017-08-29 08:58:51',
      '2017-09-28 09:12:25', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (325, 2, 1, '2017-10-09 11:24:18', '2017-10-09 11:54:18', '极速预定', 10, '0', NULL, '2017-10-09 11:14:17', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (326, 2, 1, '2017-10-10 19:19:58', '2017-10-10 19:49:58', '极速预定', 10, '0', NULL, '2017-10-10 19:09:58', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (327, 2, 1, '2017-10-10 19:51:46', '2017-10-10 20:21:46', '极速预定', 10, '0', NULL, '2017-10-10 19:11:45', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (328, 2, 1, '2017-10-10 21:21:37', '2017-10-10 21:51:37', '极速预定', 10, '0', NULL, '2017-10-10 19:21:36', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (329, 2, 1, '2017-10-10 21:56:15', '2017-10-10 22:26:15', '极速预定', 10, '0', NULL, '2017-10-10 19:26:15', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (330, 2, 1, '2017-10-16 09:30:13', '2017-10-16 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (331, 2, 1, '2017-10-23 09:30:13', '2017-10-23 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (332, 2, 1, '2017-10-30 09:30:13', '2017-10-30 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (333, 2, 1, '2017-11-06 09:30:13', '2017-11-06 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (334, 2, 1, '2017-11-13 09:30:13', '2017-11-13 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (335, 2, 1, '2017-11-20 09:30:13', '2017-11-20 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (336, 2, 1, '2017-11-27 09:30:13', '2017-11-27 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (337, 2, 1, '2017-12-04 09:30:13', '2017-12-04 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (338, 2, 1, '2017-12-11 09:30:13', '2017-12-11 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (339, 2, 1, '2017-12-18 09:30:13', '2017-12-18 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (340, 2, 1, '2017-12-25 09:30:13', '2017-12-25 10:00:13', '周会', 25, '0', '', '2017-10-10 19:28:22',
        '2017-10-10 19:28:22', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (341, 2, 1, '2017-10-14 15:33:15', '2017-10-14 16:03:15', '极速预定', 10, '0', NULL, '2017-10-14 15:23:14', NULL, '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (342, 2, 2, '2017-10-14 15:30:35', '2017-10-14 15:50:35', '开会', 10, '0', '', '2017-10-14 15:24:00',
        '2017-10-14 15:25:16', '1', 5);
INSERT INTO `meetingroom_book_detail` VALUES
  (343, 2, 3, '2017-10-14 16:00:04', '2017-10-14 16:05:14', '讨论明年会上市么？', 10, '0', '', '2017-10-14 15:58:26', NULL, '0',
   4);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50)               DEFAULT NULL
  COMMENT '权限名',
  `description` VARCHAR(200)              DEFAULT NULL
  COMMENT '介绍',
  `url`         VARCHAR(500)              DEFAULT NULL
  COMMENT '链接',
  `method`      VARCHAR(11)               DEFAULT NULL
  COMMENT '调用方法',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8
  COMMENT ='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'P_A', '测试', '/test', 'ALL');
INSERT INTO `permission` VALUES (2, 'ROLE_ADMIN', '管理', '/admin/index', 'ALL');
INSERT INTO `permission` VALUES (3, 'ROLE_ADMIN', '管理', '/meetingroom/**', 'ALL');
INSERT INTO `permission` VALUES (4, 'ROLE_ADMIN', '管理', '/employee/**', 'ALL');
INSERT INTO `permission` VALUES (5, 'ROLE_USER', '普通用户', '/book/**', 'ALL');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id`   INT(11) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '角色ID',
  `name` VARCHAR(50)               DEFAULT NULL
  COMMENT '角色名',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  COMMENT ='角色表';

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
  `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id`       INT(11)                   DEFAULT NULL
  COMMENT '角色ID',
  `permission_id` INT(11)                   DEFAULT NULL
  COMMENT '权限ID',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8
  COMMENT ='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (2, 2, 1);
INSERT INTO `role_permission` VALUES (3, 2, 2);
INSERT INTO `role_permission` VALUES (4, 2, 3);
INSERT INTO `role_permission` VALUES (5, 2, 4);
INSERT INTO `role_permission` VALUES (8, 1, 5);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(50)               DEFAULT NULL
  COMMENT '用户名',
  `password`    VARCHAR(64)               DEFAULT NULL
  COMMENT '密码',
  `real_name`   VARCHAR(50)               DEFAULT NULL
  COMMENT '中文名',
  `department`  VARCHAR(50)               DEFAULT '未知'
  COMMENT '部门',
  `create_time` DATETIME                  DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  `extra`       VARCHAR(0)                DEFAULT NULL
  COMMENT '其他',
  `enable`      VARCHAR(2)                DEFAULT NULL
  COMMENT '可用状态 0可用 1不可用',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='员工';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id`      INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11)                   DEFAULT NULL
  COMMENT '用户ID',
  `role_id` INT(11)                   DEFAULT NULL
  COMMENT '角色ID',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8
  COMMENT ='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 2, 1);
INSERT INTO `user_role` VALUES (2, 6, 1);
INSERT INTO `user_role` VALUES (3, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
