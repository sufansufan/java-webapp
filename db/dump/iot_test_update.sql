/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : iot_test

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 08/09/2020 13:53:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for monitor_factor_tag
-- ----------------------------
DROP TABLE IF EXISTS `monitor_factor_tag`;
CREATE TABLE `monitor_factor_tag` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `tag_name` varchar(50) NOT NULL DEFAULT '' COMMENT '监测因子标签名',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监测因子标签表';

-- ----------------------------
-- Records of monitor_factor_tag
-- ----------------------------
BEGIN;
INSERT INTO `monitor_factor_tag` VALUES ('1', 'tagtest', '2020-02-01 00:00:00', NULL);
INSERT INTO `monitor_factor_tag` VALUES ('b856009d84da4e809fbe75871f2220a4', 'test', '2020-09-08 01:26:43', '2020-09-08 01:26:43');
INSERT INTO `monitor_factor_tag` VALUES ('ee6a0cef61dd4d70a0559ed8ecd3bd09', 'esix', '2020-09-08 01:33:07', '2020-09-08 01:33:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` varchar(32) NOT NULL COMMENT 'id，guid',
  `dict_code` varchar(32) NOT NULL DEFAULT '' COMMENT '字典编码，唯一索引',
  `dict_name` varchar(255) NOT NULL DEFAULT '' COMMENT '字典名称',
  `is_delete_enable` tinyint(4) DEFAULT '0' COMMENT '是否可删除 0:不可删除 1:可删除',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary` VALUES ('1f6a1a8d94994e1d98531e4ba4331d2d', 'industry_category', '行业类别', 0, '', '2019-09-05 11:09:56', NULL);
INSERT INTO `sys_dictionary` VALUES ('3156079fd0b743f89fea90f20c5c30c1', 'machineType', '设备类型', 0, '', '2020-09-03 20:34:53', NULL);
INSERT INTO `sys_dictionary` VALUES ('818f744118ce4361a78b82cb83448086', 'deviceType', '监控终端类型', 0, '', '2019-09-06 14:57:13', '2020-09-03 20:34:29');
INSERT INTO `sys_dictionary` VALUES ('91879397926e40d0aa6e31073d662a45', 'enterprise_control_level', '客户类型', 0, '', '2019-09-05 11:14:30', '2020-02-26 16:36:26');
INSERT INTO `sys_dictionary` VALUES ('bbbe43d11e114d2eb908ad2971f844f1', 'enterprise_park', '维护分公司', 0, '', '2019-09-05 11:04:57', '2020-02-26 16:35:49');
COMMIT;

-- ----------------------------
-- Table structure for sys_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_item`;
CREATE TABLE `sys_dictionary_item` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id，guid',
  `dict_id` varchar(32) NOT NULL DEFAULT '' COMMENT '所属字典表id',
  `item_name` varchar(32) DEFAULT '' COMMENT '数据项名称',
  `item_value` int(4) DEFAULT NULL COMMENT '数据项值，同一字典表值不可重复',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，0：禁用；1：启用；',
  `sort_idx` int(11) DEFAULT '0' COMMENT '排序，越小排在前面',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典项表';

-- ----------------------------
-- Records of sys_dictionary_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary_item` VALUES ('04acfa7abbd54168b943e377fcec0f8e', '3156079fd0b743f89fea90f20c5c30c1', '123123', 321321, 1, 0, '', '2020-09-07 12:34:55', NULL);
INSERT INTO `sys_dictionary_item` VALUES ('0b074bd2587c4e3ea74007b8020ac240', '91879397926e40d0aa6e31073d662a45', '民企', 3, 1, 3, '', '2019-09-05 11:15:33', '2020-02-26 16:37:14');
INSERT INTO `sys_dictionary_item` VALUES ('0d262ce9ddc743928abe3e19a5590f1f', '91879397926e40d0aa6e31073d662a45', '国企', 1, 1, 1, '', '2019-09-05 11:14:55', '2020-02-26 16:36:53');
INSERT INTO `sys_dictionary_item` VALUES ('44ca218418b343cd801673fea807f630', '1f6a1a8d94994e1d98531e4ba4331d2d', '其他行业', 99, 1, 99, '', '2019-09-05 11:13:13', NULL);
INSERT INTO `sys_dictionary_item` VALUES ('6d3fefb6bdf84b269c4d7a06e90fe7a4', '3156079fd0b743f89fea90f20c5c30c1', 'esix', 777, 1, 0, '', '2020-09-07 12:36:00', NULL);
INSERT INTO `sys_dictionary_item` VALUES ('8d921a598e064538b5e6eb87d43f92de', '91879397926e40d0aa6e31073d662a45', '日企', 2, 1, 2, '', '2019-09-05 11:15:10', '2020-02-26 16:36:59');
INSERT INTO `sys_dictionary_item` VALUES ('ac7acee5228541b289bab302a557ff27', '818f744118ce4361a78b82cb83448086', '冰水机', 4, 1, 2, '', '2020-03-25 14:46:34', '2020-03-25 16:02:46');
INSERT INTO `sys_dictionary_item` VALUES ('bb3d5bd301044a6da3951deed602b3d4', '818f744118ce4361a78b82cb83448086', '螺杆式压缩机', 2, 1, 0, '', '2020-02-26 11:29:38', '2020-03-25 16:02:20');
INSERT INTO `sys_dictionary_item` VALUES ('bb4b010952db4e2cb02e835a6123f7e2', '1f6a1a8d94994e1d98531e4ba4331d2d', '工业制造行业', 2, 1, 2, '', '2019-09-05 11:10:54', '2020-02-26 16:34:20');
INSERT INTO `sys_dictionary_item` VALUES ('c5be3af1f65644d5bef18cda4ab84700', '1f6a1a8d94994e1d98531e4ba4331d2d', '食品加工行业', 1, 1, 1, '', '2019-09-05 11:10:40', '2020-02-26 16:34:05');
INSERT INTO `sys_dictionary_item` VALUES ('ee60966c05ef4b46bf21635625856aba', '3156079fd0b743f89fea90f20c5c30c1', '114514', 123456, 1, 0, '', '2020-09-05 18:24:49', NULL);
INSERT INTO `sys_dictionary_item` VALUES ('f4b77990e71f4d2fbc9a6699f23a987d', 'bbbe43d11e114d2eb908ad2971f844f1', '前川迈坤国际贸易(上海)有限公司', 1, 1, 1, '', '2019-09-05 11:08:09', '2020-02-26 16:35:39');
INSERT INTO `sys_dictionary_item` VALUES ('f6cc7bd17d454c7e9d98b07bf2a65080', '818f744118ce4361a78b82cb83448086', '活塞式压缩机', 1111, 1, 0, '', '2020-03-25 14:49:14', '2020-03-25 16:01:41');
INSERT INTO `sys_dictionary_item` VALUES ('ffac1fbf056e4b869d23016134cfdab3', '1f6a1a8d94994e1d98531e4ba4331d2d', '物流运输行业', 3, 1, 3, '', '2019-09-05 11:11:08', '2020-02-26 16:34:32');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
