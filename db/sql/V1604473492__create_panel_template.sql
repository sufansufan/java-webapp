-- ----------------------------
-- Table structure for panel_template
-- ----------------------------
DROP TABLE IF EXISTS `panel_template`;
CREATE TABLE `panel_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'id',
  `template_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` smallint(1) NULL DEFAULT 0 COMMENT '分级',
  `layout` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布局',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `template_code`(`template_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `panel_template` VALUES ('23f30acdc64d4a8cdd0b9d2ecb856af0', 'location', 0, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('aea3ee7a55f15e6bedccace58e2551f4', 'location_1', 2, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('7845bc5bd41320fb177b987c2d23fb14', 'location_2', 2, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('bea9842065125b63790d914e4af8d2a3', 'machine', 0, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('c519aa1699767f9aab8222019998b6c9', 'machine_1', 1, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('267583801a8fa7a7f3c3fe6e59857fdd', 'machine_2', 1, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');
INSERT INTO `panel_template` VALUES ('67e19e9876f9903ac5f47c6d0bb9b54e', 'machine_9d658bc35bba4d5ea05fa0712bd882a6', 2, '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}');

UPDATE panel_template SET layout = '{"rows":[{"cols":[{"type":"chart_line","title":"压力","unit":"Pa","factor_tag":"chart_1"},{"type":"chart_bar","title":"温度","unit":"C","factor_tag":"chart_2"}]},{"cols":[{"type":"chart_dot","title":"湿度","unit":"A","factor_tag":"chart_3"}]}]}' where id = 'aea3ee7a55f15e6bedccace58e2551f4' or id = '23f30acdc64d4a8cdd0b9d2ecb856af0' or id = '7845bc5bd41320fb177b987c2d23fb14';
UPDATE panel_template SET layout = '{"rows":[{"cols":[{"type":"chart_line","title":"排气压力","unit":"MPa","factor_tag":"chart_m_1"},{"type":"chart_line","title":"油回收器内压力","unit":"MPa","factor_tag":"chart_m_2"}]},{"cols":[{"type":"chart_line","title":"环境温度","unit":"℃","factor_tag":"chart_m_3"},{"type":"chart_line","title":"电机线圈温度","unit":"℃","factor_tag":"chart_m_4"}]},{"cols":[{"type":"chart_line","title":"排气温度","unit":"℃","factor_tag":"chart_m_5"},{"type":"chart_dot","title":"油分芯后温度","unit":"℃","factor_tag":"chart_m_6"}]},{"cols":[{"type":"chart_bar","title":"电流","unit":"A","factor_tag":"chart_m_7"}]}]}' where id = 'bea9842065125b63790d914e4af8d2a3' or id = '67e19e9876f9903ac5f47c6d0bb9b54e' or id = '267583801a8fa7a7f3c3fe6e59857fdd' or id = 'c519aa1699767f9aab8222019998b6c9';
