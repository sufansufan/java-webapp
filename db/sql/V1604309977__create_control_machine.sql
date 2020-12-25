-- ----------------------------
-- Table structure for control_machine
-- ----------------------------
DROP TABLE IF EXISTS `control_machine`;
CREATE TABLE `control_machine`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'id',
  `serial_no` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属终端',
  `enterprise_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '企业ID',
  `org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织id',
  `team_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班组id',
  `template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模板ID',
  `machine_no` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '设备编号',
  `machine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '设备名称',
  `runtime` bigint(16) NULL DEFAULT 0 COMMENT '运行时间',
  `machine_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '设备类型',
  `machine_model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备型号',
  `photo_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片路径',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `purchasing_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `manufacotry` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `machine_usage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rated_power` float NULL DEFAULT NULL,
  `online_status` tinyint(1) NULL DEFAULT 0,
  `power_status` smallint(1) NULL DEFAULT 0 COMMENT '运行状态，0：停机，1：运行',
  `alarm_status` smallint(1) NULL DEFAULT 0 COMMENT '报警状态，0:停机，1：运行，2：预警，3：报警',
  `status` smallint(4) NULL DEFAULT 1,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `running_status` smallint(1) NULL DEFAULT 0 COMMENT '设备开关状态，0：停机，1：启动',
  `fault_status` smallint(1) NULL DEFAULT 0 COMMENT '故障状态，0：无故障，1：有故障',
  `early_alarm_status` bigint(16) NULL DEFAULT 0 COMMENT '预警的数量',
  `remote_monitor_flag` smallint(1) NULL DEFAULT NULL COMMENT '是否远程监视 0：否  1：是',
  `check_flag` smallint(1) NULL DEFAULT NULL COMMENT '是否点检 0：否  1：是',
  `maintain_flag` smallint(1) NULL DEFAULT NULL COMMENT '是否保养 0：否  1：是',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属位置',
  `layout` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布局',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `device_code`(`device_code`, `machine_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of control_machine
-- ----------------------------
INSERT INTO `control_machine` VALUES ('09a0cc9ae9d945849778c08d0f4249a6', '111', 'TR01', '8f5f7fbe2b9f47879e676f9c0ed907e7', '51f8361d52b740a5957da4e0e9ed42ff', '1', '1', '1', 'test1', 0, '777', 'adsffff', '1', '1', '2020-10-20 09:35:09', '1', '1', 1, 1, 0, 0, 0, '2020-10-15 15:03:27', '2020-10-17 17:53:30', 0, 0, 0, 1, 1, 0, 0, '车间A');
INSERT INTO `control_machine` VALUES ('702049f3e9f246fabd744091d0f7a0f3', NULL, 'doremo_proxy_01', 'ab7f242d941c4c34a691ed478722adc5', '51f8361d52b740a5957da4e0e9ed42ff', '1', '', '1', 'test11', 0, '123456', 'FFFFF', '2020101918354615357409.png', 'test', '2020-10-20 09:35:12', NULL, NULL, NULL, 0, 0, 0, 0, '2020-10-19 09:48:56', '2020-10-19 10:36:06', 0, 0, 0, 1, 1, 1, 0, '车间B');
INSERT INTO `control_machine` VALUES ('f29ab9e9630145e79651b5d19649e7f4', NULL, '100005', '61ce767d9166484d964270fd55ed35d4', '65ec9a72a8a448c091161d68c4f3b396', '2', '', '2', 'test2', 0, '777', 'ffssx', 'images/2020101918435504358051.png', 'tt', '2020-10-20 09:35:15', NULL, NULL, NULL, 1, 0, 0, 0, '2020-10-17 17:54:59', '2020-10-19 12:01:20', 0, 0, 0, 0, 0, 1, 0, '车间C');
