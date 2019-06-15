/*
Navicat MySQL Data Transfer

Source Server         : dice新
Source Server Version : 50724
Source Host           : 11.82.36.227:3306
Source Database       : db

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-05-20 10:56:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `db_data_sources`
-- ----------------------------
DROP TABLE IF EXISTS `db_data_sources`;
CREATE TABLE `db_data_sources` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_key` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库组标志',
  `name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库名字',
  `driver_class_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库驱动类名',
  `url` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库地址',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `extra_json` blob COMMENT '额外配置',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库连接管理表';

-- ----------------------------
-- Records of db_data_sources
-- ----------------------------
INSERT INTO `db_data_sources` VALUES ('1', 'drp', '1234', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://192.168.219.129:1433;database=drp_wh2', 'sa', 'Very_Strong123', null, '2018-11-11 00:00:00', '2018-11-11 00:00:00');

