-- ----------------------------
-- Table structure for monitor_factor_tag
-- ----------------------------
DROP TABLE IF EXISTS `monitor_factor_tag`;
CREATE TABLE `monitor_factor_tag` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `tag_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '监测因子标签名',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
