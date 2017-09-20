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

 Date: 20/09/2017 11:22:50
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
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='活动';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES
  (1, '加班餐（晚餐）', '2017-07-17 18:00:01', '2017-07-17 09:00:01', '2017-07-17 16:00:01', '加班餐，未报名但加班用餐者扣十元！', 0, '0',
   '2017-07-17 00:40:02', NULL);
COMMIT;

-- ----------------------------
-- Table structure for activity_apply
-- ----------------------------
DROP TABLE IF EXISTS `activity_apply`;
CREATE TABLE `activity_apply` (
  `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `activity_id`   INT(11)                   DEFAULT NULL
  COMMENT '活动id',
  `employee_id`   INT(11)                   DEFAULT NULL
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
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8
  COMMENT ='员工';

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
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roomname` VARCHAR(50)               DEFAULT NULL
  COMMENT '会议室名',
  `status`   VARCHAR(2)                DEFAULT '0'
  COMMENT '使用状态 0正常 1关闭',
  `capacity` INT(11) UNSIGNED          DEFAULT '10'
  COMMENT '可容纳人数',
  `remark`   VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
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
INSERT INTO `meetingroom` VALUES (1, '1号会议室', '0', 30, NULL);
INSERT INTO `meetingroom` VALUES (2, '2号会议室', '0', 10, NULL);
INSERT INTO `meetingroom` VALUES (3, '3号会议室', '1', 50, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_change
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_change`;
CREATE TABLE `meetingroom_book_change` (
  `id`                          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `meetingroom_book_details_id` INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '会议室预定详情id',
  `change_reason`               VARCHAR(200)              DEFAULT NULL
  COMMENT '预定变化原因',
  `create_time`                 DATETIME                  DEFAULT NULL
  COMMENT '提交变化的时间',
  `audit_status`                VARCHAR(2)                DEFAULT NULL
  COMMENT '审核状态 0待审核，1审核通过，2审核不通过',
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
INSERT INTO `meetingroom_book_change` VALUES (1, 1, '时间冲突', '2017-09-08 08:59:55', '1');
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_book_detail
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_book_detail`;
CREATE TABLE `meetingroom_book_detail` (
  `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_id`    INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '员工id',
  `meetingroom_id` INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '会议室id',
  `book_reason`    VARCHAR(200)              DEFAULT NULL
  COMMENT '申请原因',
  `attend_num`     INT(11) UNSIGNED          DEFAULT NULL
  COMMENT '与会人数',
  `create_time`    DATETIME                  DEFAULT NULL
  COMMENT '申请提交时间',
  `audit_status`   VARCHAR(2)                DEFAULT NULL
  COMMENT '0待审核，1审核通过，2审核不通过',
  `remark`         VARCHAR(200)              DEFAULT NULL
  COMMENT '备注',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='会议室预定详情';

-- ----------------------------
-- Records of meetingroom_book_detail
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_book_detail` VALUES (1, 1, 1, '架构选型说明', 12, '2017-08-29 08:58:51', '1', NULL);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_daily_book_schedule
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_daily_book_schedule`;
CREATE TABLE `meetingroom_daily_book_schedule` (
  `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `meetingroom_id` INT(11)                   DEFAULT NULL
  COMMENT '会议室id',
  `book_date`      DATE                      DEFAULT NULL
  COMMENT '会议的日期',
  `version`        INT(10)                   DEFAULT NULL
  COMMENT '版本控制字段',
  `_0900_0930`     INT(11)                   DEFAULT NULL
  COMMENT '9点到9点30预定情况',
  `_0930_1000`     INT(11)                   DEFAULT NULL,
  `_1000_1030`     INT(11)                   DEFAULT NULL,
  `_1030_1100`     INT(11)                   DEFAULT NULL,
  `_1100_1130`     INT(11)                   DEFAULT NULL,
  `_1130_1200`     INT(11)                   DEFAULT NULL,
  `_1200_1230`     INT(11)                   DEFAULT NULL,
  `_1230_1300`     INT(11)                   DEFAULT NULL,
  `_1300_1330`     INT(11)                   DEFAULT NULL,
  `_1330_1400`     INT(11)                   DEFAULT NULL,
  `_1400_1430`     INT(11)                   DEFAULT NULL,
  `_1430_1500`     INT(11)                   DEFAULT NULL,
  `_1500_1530`     INT(11)                   DEFAULT NULL,
  `_1530_1600`     INT(11)                   DEFAULT NULL,
  `_1600_1630`     INT(11)                   DEFAULT NULL,
  `_1630_1700`     INT(11)                   DEFAULT NULL,
  `_1700_1730`     INT(11)                   DEFAULT NULL,
  `_1730_1800`     INT(11)                   DEFAULT NULL,
  `_1800_1830`     INT(11)                   DEFAULT NULL,
  `_1830_1900`     INT(11)                   DEFAULT NULL,
  `_1900_1930`     INT(11)                   DEFAULT NULL,
  `_1930_2000`     INT(11)                   DEFAULT NULL,
  `_2000_2030`     INT(11)                   DEFAULT NULL,
  `_2030_2100`     INT(11)                   DEFAULT NULL,
  `other_time`     VARCHAR(50)               DEFAULT NULL
  COMMENT '非常规时段',
  PRIMARY KEY (`id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='会议室每日预定档期';

-- ----------------------------
-- Records of meetingroom_daily_book_schedule
-- ----------------------------
BEGIN;
INSERT INTO `meetingroom_daily_book_schedule` VALUES
  (1, 1, '2017-08-29', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 1, 1, NULL, NULL, NULL,
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meetingroom_monthly_book_schedule
-- ----------------------------
DROP TABLE IF EXISTS `meetingroom_monthly_book_schedule`;
CREATE TABLE `meetingroom_monthly_book_schedule` (
  `id`                    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date`            DATE             NOT NULL
  COMMENT '记录会议室预定月份信息，设定为当月的第一天',
  `meetingroom_id`        INT(11) UNSIGNED NOT NULL
  COMMENT '会议室id',
  `monthly_book_schedule` TINYBLOB         NOT NULL
  COMMENT 'start_date月份预定详情信息，使用位图法结合字节数组记录，每一位代表30分钟的预定情况 0 未预定 1已预定',
  `updated_time`          DATETIME                  DEFAULT NULL
  COMMENT 'monthly_book_schedule字段最后一次更新的时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='会议室预定月计划';

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
  AUTO_INCREMENT = 3
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
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  COMMENT ='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50)               DEFAULT NULL
  COMMENT '用户名',
  `password` VARCHAR(100)              DEFAULT NULL
  COMMENT '密码',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  COMMENT ='用户表';

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
  `id`      INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11)                   DEFAULT NULL
  COMMENT '用户ID',
  `role_id` INT(11)                   DEFAULT NULL
  COMMENT '角色ID',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
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
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
