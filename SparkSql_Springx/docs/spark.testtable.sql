/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.202
Source Server Version : 50636
Source Host           : 192.168.0.202:3306
Source Database       : spark

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-09-07 15:50:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `testtable`
-- ----------------------------
DROP TABLE IF EXISTS `testtable`;
CREATE TABLE `testtable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of testtable
-- ----------------------------
INSERT INTO `testtable` VALUES ('1', 'zhangsan', '123456');
INSERT INTO `testtable` VALUES ('2', 'wuchuan', '321654');
INSERT INTO `testtable` VALUES ('3', 'lisi', '452323');
INSERT INTO `testtable` VALUES ('4', 'zhaoliu', '563445');
INSERT INTO `testtable` VALUES ('5', 'zhaoliujj', '2323');
