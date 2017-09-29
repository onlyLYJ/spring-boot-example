/*
 Navicat Premium Data Transfer

 Source Server         : sampledb
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : jc_apply

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 29/09/2017 10:11:18
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
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES (1, '财务部', '2017-09-28 16:47:03', NULL, '0', NULL);
INSERT INTO `department` VALUES (2, '市场部', '2017-09-28 16:47:09', NULL, '0', NULL);
INSERT INTO `department` VALUES (3, '技术部', '2017-09-28 16:47:11', NULL, '0', NULL);
INSERT INTO `department` VALUES (4, '销售部', '2017-09-28 16:47:12', NULL, '0', NULL);
INSERT INTO `department` VALUES (5, '人事部', '2017-09-28 16:47:13', NULL, '0', NULL);
INSERT INTO `department` VALUES (6, '行政部', '2017-09-28 16:47:59', NULL, '0', NULL);
INSERT INTO `department` VALUES (7, '总经办', '2017-09-28 16:48:01', NULL, '0', NULL);
INSERT INTO `department` VALUES (8, '其他', '2017-09-28 16:49:50', NULL, '1', NULL);
COMMIT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `department`   VARCHAR(50)               DEFAULT '未知'
  COMMENT '部门',
  `real_name`    VARCHAR(50)               DEFAULT NULL
  COMMENT '姓名',
  `english_name` VARCHAR(50)               DEFAULT NULL
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
  AUTO_INCREMENT = 12
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
INSERT INTO `employee` VALUES (3, '技术部', '架构师A', 'jason', '2017-09-28 10:53:55', '2017-07-15 00:17:36',
                               '$2a$10$W/C4rQo9DWYy3Pz0RuJXdO5Wh0yO31UlU8lblh8SiSQBBEk62ADA6', NULL, '0');
INSERT INTO `employee` VALUES (4, '技术部', '架构师B', 'admin', '2017-09-28 10:53:55', NULL,
                               '$2a$10$W/C4rQo9DWYy3Pz0RuJXdO5Wh0yO31UlU8lblh8SiSQBBEk62ADA6', NULL, '0');
INSERT INTO `employee` VALUES (5, '技术部', '真的好~', 'steven3', '2017-09-28 14:04:47', NULL,
                               '$2a$10$yWvG/UDOEdm/GVn/9CC/m.opkw7xWZxxNyUZeX.nFj72mbvRYcfPy', NULL, '0');
INSERT INTO `employee` VALUES (6, '技术部', '杨广', 'yangguang', '2017-09-28 14:07:38', NULL,
                               '$2a$10$m9KSil6QtxdQNC9iOFy7DeGEnI4nvZWJltlgLFz/A03k8GO0dxFFe', NULL, '0');
INSERT INTO `employee` VALUES (7, '技术部', '刁林', 'diaotree', '2017-09-28 15:44:49', NULL,
                               '$2a$10$9sRQs48aytri5dX9GN30Suh.99WL4cGiwUS1Fd/N0Ss0tarFToy7i', NULL, '0');
INSERT INTO `employee` VALUES (8, '技术部', '丁家星', 'jackDing', '2017-09-28 17:23:46', NULL,
                               '$2a$10$gt16YRtH9rM/YiqidoJKGuqoq579srIfiURppjznmJDHXGjzhONx.', NULL, '0');
INSERT INTO `employee` VALUES (9, '技术部', '李', 'steven35', '2017-09-28 18:39:47', NULL,
                               '$2a$10$0d3cTjVtUU0x12F2jH0giennrhqEZ5YymNAflM6U7xGzta0YZ.Qhm', NULL, '0');
INSERT INTO `employee` VALUES (10, '技术部', '123', 'steven2', '2017-09-28 18:58:01', NULL,
                               '$2a$10$aX/r4wgmSGCo9Ad5k0BbdetE/U4DJOlAgkuvb52A0XMHo.gn5lXZa', NULL, '0');
INSERT INTO `employee` VALUES (11, '技术部', 'sssdf', 'steven6', '2017-09-28 18:58:11', NULL,
                               '$2a$10$l.Z7sGkwtv3KxZHrRjrPFeHFJPc3MZHNctVNFu3kDHHs15TeSistW', NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for meetingroom
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom`;
CREATE TABLE `meetingroom` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roomname`    VARCHAR(50)               DEFAULT NULL
  COMMENT '会议室名',
  `status`      VARCHAR(2)                DEFAULT '0'
  COMMENT '使用状态 0正常 1关闭',
  `capacity`    INT(11) UNSIGNED          DEFAULT '10'
  COMMENT '可容纳人数',
  `remark`      VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
  `create_time` DATETIME                  DEFAULT NULL
  COMMENT '创建时间',
  `update_time` DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 42
  DEFAULT CHARSET = utf8
  COMMENT ='会议室';

-- ----------------------------
-- Records of meetingroom
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom` VALUES (1, '1号会议室', '0', 35, '豪华装修，配置全息投影!!', '2017-09-13 06:30:53', '2017-09-28 17:22:19');
INSERT INTO `meetingroom` VALUES (3, '3号会议室', '0', 10, '接见领导人专用', '2017-09-26 10:03:22', '2017-09-27 13:28:27');
INSERT INTO `meetingroom` VALUES (4, '2号会议室', '0', 100, '请不要随便删除信息好嘛~', '2017-09-27 13:27:43', '2017-09-27 13:27:43');
INSERT INTO `meetingroom` VALUES (40, '5号会议室', '0', 88, '这次好了~', '2017-09-28 09:13:11', '2017-09-28 09:13:11');
INSERT INTO `meetingroom` VALUES (41, '4号会议室', '0', 25, '最后来的！', '2017-09-28 17:22:39', '2017-09-28 17:22:39');
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
  `create_time`                DATETIME                  DEFAULT NULL
  COMMENT '创建时间',
  `update_time`                DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
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
INSERT INTO `meetingroom_book_change` VALUES (1, 1, '时间冲突', '1', '2017-09-26 15:03:42', '2017-09-26 15:03:42');
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
  `create_time`        DATETIME                  DEFAULT NULL
  COMMENT '申请提交时间',
  `update_time`        DATETIME                  DEFAULT NULL
  COMMENT '更新时间',
  `status`             VARCHAR(2)                DEFAULT NULL
  COMMENT '预定过期状态，0未过期，1过期',
  `dept_id`            INT(11)                   DEFAULT NULL
  COMMENT '预定部门id',
  PRIMARY KEY (`id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 232
  DEFAULT CHARSET = utf8
  COMMENT ='会议室预定详情';

-- ----------------------------
-- Records of meetingroom_book_detail
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_detail` VALUES
  (1, 2, 2, '2017-09-24 10:32:05', '2017-09-24 13:32:15', '架构选型说明', 12, '1', '重要！！', '2017-08-29 08:58:51',
      '2017-09-28 09:12:25', '1', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (180, 1, 1, '2017-09-30 01:00:01', '2017-09-30 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 13:01:59', '1', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (181, 1, 1, '2017-10-07 01:00:01', '2017-10-07 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 17:22:52', '1', 2);
INSERT INTO `meetingroom_book_detail` VALUES
  (182, 2, 1, '2017-10-14 01:00:01', '2017-10-14 14:00:02', '讨论中秋去哪儿玩', 101, '1', '123', '2017-09-28 10:07:41',
        '2017-09-28 18:44:05', '1', 2);
INSERT INTO `meetingroom_book_detail` VALUES
  (183, 2, 1, '2017-10-21 01:00:01', '2017-10-21 14:00:02', '讨论中秋去哪儿玩', 101, '1', '123', '2017-09-28 10:07:41',
        '2017-09-28 18:44:37', '0', 3);
INSERT INTO `meetingroom_book_detail` VALUES
  (184, 3, 1, '2017-10-28 01:00:01', '2017-10-28 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (185, 3, 1, '2017-11-04 01:00:01', '2017-11-04 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 3);
INSERT INTO `meetingroom_book_detail` VALUES
  (186, 4, 1, '2017-11-11 01:00:01', '2017-11-11 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 4);
INSERT INTO `meetingroom_book_detail` VALUES
  (187, 4, 1, '2017-11-18 01:00:01', '2017-11-18 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 5);
INSERT INTO `meetingroom_book_detail` VALUES
  (188, 1, 1, '2017-11-25 01:00:01', '2017-11-25 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 6);
INSERT INTO `meetingroom_book_detail` VALUES
  (189, 2, 1, '2017-12-02 01:00:01', '2017-12-02 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 6);
INSERT INTO `meetingroom_book_detail` VALUES
  (190, 2, 1, '2017-12-09 01:00:01', '2017-12-09 14:00:02', '讨论中秋去哪儿玩', 101, '0', '123', '2017-09-28 10:07:41',
        '2017-09-28 10:07:41', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (191, 1, 3, '2017-12-31 09:00:10', '2017-12-31 13:00:01', '讨论年终发10薪还是15薪', 15, '0', '无', '2017-09-28 17:25:04',
        '2017-09-28 17:25:04', '0', 7);
INSERT INTO `meetingroom_book_detail` VALUES
  (192, NULL, 1, '2017-09-29 05:00:05', '2017-09-29 13:00:01', '买什么福利品？', 1, '0', '哈哈', NULL, '2017-09-28 18:43:53',
   '0', 6);
INSERT INTO `meetingroom_book_detail` VALUES
  (218, 1, 1, '2017-12-27 08:00:05', '2017-12-27 17:00:05', '124', 41, '0', '2', '2017-09-28 17:32:48',
        '2017-09-28 17:32:48', '0', 6);
INSERT INTO `meetingroom_book_detail` VALUES
  (219, NULL, 1, '2017-09-29 01:00:01', '2017-09-29 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (220, NULL, 1, '2017-10-06 01:00:01', '2017-10-06 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (221, NULL, 1, '2017-10-13 01:00:01', '2017-10-13 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (222, NULL, 1, '2017-10-20 01:00:01', '2017-10-20 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (223, NULL, 1, '2017-10-27 01:00:01', '2017-10-27 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (224, NULL, 1, '2017-11-03 01:00:01', '2017-11-03 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (225, NULL, 1, '2017-11-10 01:00:01', '2017-11-10 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (226, NULL, 1, '2017-11-17 01:00:01', '2017-11-17 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (227, NULL, 1, '2017-11-24 01:00:01', '2017-11-24 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (228, NULL, 1, '2017-12-01 01:00:01', '2017-12-01 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (229, NULL, 1, '2017-12-08 01:00:01', '2017-12-08 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (230, NULL, 1, '2017-12-15 01:00:01', '2017-12-15 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
INSERT INTO `meetingroom_book_detail` VALUES
  (231, NULL, 1, '2017-12-22 01:00:01', '2017-12-22 02:00:02', '这次国庆为什么有8天？', 15, '0', '', '2017-09-28 18:42:39',
        '2017-09-28 18:42:39', '0', 1);
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
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8
  COMMENT ='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'P_A', '测试', '/test', 'ALL');
INSERT INTO `permission` VALUES (2, 'ROLE_ADMIN', '管理', '/admin/index', 'ALL');
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
  AUTO_INCREMENT = 5
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
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = utf8
  COMMENT ='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 2, 1);
INSERT INTO `role_permission` VALUES (6, 2, 2);
INSERT INTO `role_permission` VALUES (7, 2, 3);
INSERT INTO `role_permission` VALUES (8, 2, 4);
INSERT INTO `role_permission` VALUES (9, 3, 1);
INSERT INTO `role_permission` VALUES (10, 3, 2);
INSERT INTO `role_permission` VALUES (11, 3, 3);
INSERT INTO `role_permission` VALUES (12, 3, 4);
INSERT INTO `role_permission` VALUES (13, 3, 5);
INSERT INTO `role_permission` VALUES (14, 3, 6);
INSERT INTO `role_permission` VALUES (15, 3, 7);
INSERT INTO `role_permission` VALUES (16, 1, 6);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(50)               DEFAULT NULL
  COMMENT '用户名',
  `password`    VARCHAR(260)              DEFAULT NULL
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
  AUTO_INCREMENT = 12
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
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8
  COMMENT ='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (3, 2, 1);
INSERT INTO `user_role` VALUES (4, 2, 2);
INSERT INTO `user_role` VALUES (5, 3, 1);
INSERT INTO `user_role` VALUES (6, 3, 2);
INSERT INTO `user_role` VALUES (7, 4, 1);
INSERT INTO `user_role` VALUES (8, 4, 2);
INSERT INTO `user_role` VALUES (9, 5, 1);
INSERT INTO `user_role` VALUES (10, 5, 2);
INSERT INTO `user_role` VALUES (11, 6, 1);
INSERT INTO `user_role` VALUES (12, 6, 2);
INSERT INTO `user_role` VALUES (13, 1, 3);
INSERT INTO `user_role` VALUES (14, 2, 3);
INSERT INTO `user_role` VALUES (15, 4, 3);
INSERT INTO `user_role` VALUES (16, 5, 3);
INSERT INTO `user_role` VALUES (17, 6, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
