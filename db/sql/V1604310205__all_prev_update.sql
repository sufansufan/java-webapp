ALTER TABLE monitor_factor_template ADD machine_id VARCHAR(32);
ALTER TABLE monitor_factor_template CHANGE  condensing_device_num machine_type varchar(50);
ALTER TABLE monitor_factor_template ADD UNIQUE KEY(factor_code, machine_id);
ALTER TABLE monitor_factor_template ADD `lower_limit_text` varchar(100) DEFAULT '' COMMENT '阈值下限警报信息';
ALTER TABLE monitor_factor_template ADD `upper_limit_text` varchar(100) DEFAULT '' COMMENT '阈值上限警报信息';
ALTER TABLE monitor_factor_template ADD `start_switch` tinyint(1) DEFAULT '0', COMMENT '是否是设备启动开关';
INSERT INTO `sys_dictionary`(`id`, `dict_code`, `dict_name`, `is_delete_enable`, `remark`, `create_time`, `modify_time`) VALUES ('3156079fd0b743f89fea90f20c5c30c1', 'machineType', '设备类型', 0, '', '2020-09-03 20:34:53', NULL);

-- ----------------------------
-- Table structure for abnormal
-- ----------------------------
DROP TABLE IF EXISTS `abnormal`;
CREATE TABLE `abnormal`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `machine_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备编号',
  `machine_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `scheduling_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排班id',
  `template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  `processor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业执行人id（管理部门下的userId）',
  `process_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理内容描述',
  `process_urls` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业图片url列表，半角逗号分隔',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：0-待处理  1-待确认 2-已完成',
  `acknowledgement_status` int(1) NULL DEFAULT NULL COMMENT '是否承认：0否 1是',
  `acknowledgement_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承认人（管理部门下的userId）',
  `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回理由',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `abnormal_source` int(1) NULL DEFAULT NULL COMMENT '异常来源(dic_item表value值)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异常表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for execute_result
-- ----------------------------
DROP TABLE IF EXISTS `execute_result`;
CREATE TABLE `execute_result`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scheduling_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排班id',
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点检项目',
  `result` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0-通过 1-不通过 2-跳过',
  `result_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `result_urls` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片url列表，逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '点检/保养结果表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for scheduling
-- ----------------------------
DROP TABLE IF EXISTS `scheduling`;
CREATE TABLE `scheduling`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `machine_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备编号',
  `template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
  `execute_date` datetime(0) NULL DEFAULT NULL COMMENT '执行日期',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：0-未完成 1-已完成 2-待确认',
  `executor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行点检/保养人id，与用户表user_id一致',
  `rejector_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回人id',
  `rejector_reason` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '排班表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for scheduling_gen_rules
-- ----------------------------
DROP TABLE IF EXISTS `scheduling_gen_rules`;
CREATE TABLE `scheduling_gen_rules`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `machine_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备编号',
  `begin_date` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `count` int(20) NULL DEFAULT NULL COMMENT '点检/保养次数',
  `frequency_class` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率类别：2-按周 3-按月',
  `frequency` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率值，如按月对应值：1,20（数字表示某月的某一天，半角逗号分隔），按周对应值：0，5（数字0-6对应周日到周一）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '排班生成规则表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_team
-- ----------------------------
DROP TABLE IF EXISTS `sys_team`;
CREATE TABLE `sys_team`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班组名称',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点id',
  `team_id_path` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属班组路径',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_team
-- ----------------------------
INSERT INTO `sys_team` VALUES ('01c6d377439a4b4f86a50d73536ca2a8', '', '保养班组', NULL, '01c6d377439a4b4f86a50d73536ca2a8', '2020-10-15 17:49:39', NULL);
INSERT INTO `sys_team` VALUES ('efde2e8bbcab44ff82a32896a014f981', '037d39246b5c49888603499d89647ccd', '保养一班', '01c6d377439a4b4f86a50d73536ca2a8', '01c6d377439a4b4f86a50d73536ca2a8:efde2e8bbcab44ff82a32896a014f981', '2020-10-16 17:51:03', '2020-10-18 23:04:14');

-- ----------------------------
-- Table structure for sys_team_member
-- ----------------------------
DROP TABLE IF EXISTS `sys_team_member`;
CREATE TABLE `sys_team_member`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `team_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班组id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班组成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_team_member
-- ----------------------------
INSERT INTO `sys_team_member` VALUES ('3c5e64a297274829b98aef90827990f9', 'efde2e8bbcab44ff82a32896a014f981', '3184714fd4ad4cb3919d4b53b393f12f');
INSERT INTO `sys_team_member` VALUES ('6e3ae4aedd6c48f59d01d3475b97e797', 'efde2e8bbcab44ff82a32896a014f981', '4407cfa6468e4d9599fd544cd13ea27f');


-- ----------------------------
-- Table structure for inspection_maintain_template
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_template`;
CREATE TABLE `inspection_maintain_template` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  `machine_id` varchar(32) NOT NULL DEFAULT '' COMMENT '设备id',
  `template_type` varchar(32) NOT NULL DEFAULT '' COMMENT '模板类型（定期D、日常R、保养B）',
  `is_available` int(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_class
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_class`;
CREATE TABLE `inspection_maintain_class` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检分类id',
  `template_id` varchar(32) NOT NULL DEFAULT '' COMMENT '模板id',
  `class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '点检分类名称',
  `is_available` smallint(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)',
  `machine_id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_item
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_item`;
CREATE TABLE `inspection_maintain_item` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检项id',
  `class_id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养分类id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '点检保养项名称',
  `status` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养项状态',
  `index_num` smallint(1) NOT NULL DEFAULT '-1' COMMENT '排序',
  `method` varchar(400) DEFAULT '' COMMENT '点检保养方法',
  `standard` varchar(400) DEFAULT '' COMMENT '点检保养标准',
  `no_check` int(1) NOT NULL DEFAULT '0' COMMENT '0不检查，1检查',
  `step_img_urls` varchar(400) DEFAULT NULL COMMENT '操作规程相关图片存放地址',
  `template_id` varchar(32) NOT NULL COMMENT '模版id',
  `machine_id` varchar(32) NOT NULL COMMENT '设备id',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_schedule
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_schedule`;
CREATE TABLE `inspection_maintain_schedule` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养排班id',
  `date` datetime NOT NULL COMMENT '排班日期',
  `machine_id` varchar(32) NOT NULL COMMENT '设备id',
  `template_id` varchar(32) NOT NULL COMMENT '模版id',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_set_schedule
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_set_schedule`;
CREATE TABLE `inspection_maintain_set_schedule` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `template_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '模版id',
  `machine_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '设备id',
  `start_date` date DEFAULT NULL COMMENT '排班设定的开始日期',
  `times` int(4) DEFAULT NULL COMMENT '次数',
  `frequency` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '频率 DAY天 WEEK周 MONTH月',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for inspection_maintain_result
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_result`;
CREATE TABLE `inspection_maintain_result` (
  `id` varchar(32) NOT NULL COMMENT '点检保养结果id',
  `schedule_id` varchar(32) NOT NULL COMMENT '对应排班id',
  `confirm_user_id` varchar(32) DEFAULT NULL COMMENT '班长确认人',
  `confirm_date` datetime DEFAULT NULL COMMENT '班长确认时间',
  `admit_user_id` varchar(32) DEFAULT '' COMMENT 'manager确认人',
  `admit_date` datetime DEFAULT NULL COMMENT 'manager确认时间',
  `status` varchar(32) NOT NULL DEFAULT '' COMMENT '当前状态',
  `reject_user_id` varchar(32) DEFAULT NULL COMMENT '驳回人',
  `reject_date` datetime DEFAULT NULL COMMENT '驳回日期',
  `is_available` int(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)',
  `machine_id` varchar(32) NOT NULL COMMENT '设备id',
  `item_id` varchar(32) NOT NULL COMMENT '点检保养项id',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `option_user_id` varchar(32) DEFAULT '' COMMENT '点检保养执行人',
  `option_date` datetime DEFAULT NULL COMMENT '执行人执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_item_result
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_item_result`;
CREATE TABLE `inspection_maintain_item_result` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养结果id',
  `execute_date` datetime DEFAULT NULL COMMENT '班长确认时间',
  `result` varchar(32) NOT NULL DEFAULT '' COMMENT '结果(通过/不通过/不检查)',
  `desc` varchar(400) DEFAULT '' COMMENT '描述',
  `result_img_urls` varchar(400) DEFAULT '' COMMENT '检查图片相关图片链接',
  `machine_id` varchar(0) NOT NULL COMMENT '设备id',
  `machine_item_id` varchar(0) NOT NULL COMMENT '点检保养项id',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `exception_type` varchar(10) DEFAULT 'T' COMMENT '异常类型 点检D，保养B，突发异常：T',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inspection_maintain_fix
-- ----------------------------
DROP TABLE IF EXISTS `inspection_maintain_fix`;
CREATE TABLE `inspection_maintain_fix` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养异常处理id',
  `item_result_id` varchar(32) NOT NULL DEFAULT '' COMMENT '异常处理项id',
  `fix_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '异常处理人id',
  `desc` varchar(400) NOT NULL DEFAULT '' COMMENT '异常处理描述',
  `fix_img_urls` varchar(400) DEFAULT '' COMMENT '维修图片链接',
  `fix_date` datetime DEFAULT NULL COMMENT '维修处理时间',
  `admit_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '承认人id',
  `admit_date` datetime DEFAULT NULL COMMENT '承认时间',
  `reject_user_id` varchar(32) DEFAULT NULL COMMENT '驳回人',
  `reject_date` datetime DEFAULT NULL COMMENT '驳回时间',
  `is_available` int(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)',
  `result` varchar(100) DEFAULT NULL COMMENT '处理结果',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `sys_dictionary_item` ADD layout varchar(1024) default '';

UPDATE sys_dictionary_item SET layout = '{row1:{col1:{chart_type:\"line\",factor_tag:\"chart1\",unit:\"mA\"},col2:{chart_type:\"line\",factor_tag:\"chart2\",unit:\"mA\"}},row2:{col1:{chart_type:\"line\",factor_tag:\"chart1\",unit:\"mA\"}}';


-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `machine_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备编号',
  `temp_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板类型(日常点检、定期点检、保养、远程监控、突发异常)',
  `item_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类',
  `item_number` int(20) NULL DEFAULT NULL COMMENT '项目编号',
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点检/保养项目',
  `item_method` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点检/保养方法',
  `judgment_criteria` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '判断标准',
  `results` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果集合：0-通过 1-不通过 2-跳过，半角逗号分隔',
  `oper_proc_urls` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作规程图片url列表，半角逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模板表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_authority` VALUES ('ae33366cb1984736aad76175f1a65dbf', '设备管理', 'machine_manager', 1, 664, 'e97ab8fca0a544e98cc13fa846f3292e', 'dataDesources/machineManager/index.do', 'system_icon_sysOrg', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('dab692886ecf45daa2081c3c61c2ddc6', '删除', 'machine_del', 2, 554, 'ae33366cb1984736aad76175f1a65dbf', '', '', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('1519800488bd4381b92859cf2fa21315', '添加', 'machine_add', 2, 552, 'ae33366cb1984736aad76175f1a65dbf', '', '', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('a57d52bde1da433f84ee7b9393bfaeeb', '编辑', 'machine_edit', 2, 553, 'ae33366cb1984736aad76175f1a65dbf', '', '', NULL, NULL);

INSERT INTO `sys_authority` VALUES ('6f94c8bb61c44ff8bd857aedb7c9c8e6', '点检保养', 'inspectionMaintenance', 1, 556, '0', 'inspectionMaintenance/index.do', 'nav_icon_szjc', NULL, '2020-10-20 14:34:11');
INSERT INTO `sys_authority` VALUES ('0e6294b9f1794c52a73b7b2f4bace6c0', '异常管理', 'abnormal', 1, 700, '6f94c8bb61c44ff8bd857aedb7c9c8e6', 'inspectionMaintenance/abnormal/index.do', 'wlAlarm', NULL, '2020-10-20 15:11:14');
ALTER TABLE monitor_factor CHANGE condensing_device_num machine_type varchar(50);
ALTER TABLE monitor_factor ADD `lower_limit_text` varchar(100) DEFAULT '' COMMENT '阈值下限警报信息';
ALTER TABLE monitor_factor ADD `upper_limit_text` varchar(100) DEFAULT '' COMMENT '阈值上限警报信息';
ALTER TABLE monitor_factor ADD `start_switch` tinyint(1) DEFAULT '0', COMMENT '是否是设备启动开关';

INSERT INTO `sys_authority` VALUES ('8889ce7ec50247f799605d753a86075d', '班组管理', 'sys_team', 1, 236, '4ee8b0d4e56f4efda913752ab0748e3b', 'system/sysTeam/index.do', 'sysUser', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('fc84a3461ef94d79a22f5fdfcaacd330', '编辑', 'sys_team_edit', 2, 241, '8889ce7ec50247f799605d753a86075d', '', '', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('4e23cc9e5dc84f139b82cd744a07fd3e', '添加', 'sys_team_add', 2, 239, '8889ce7ec50247f799605d753a86075d', '', '', NULL, NULL);
INSERT INTO `sys_authority` VALUES ('61783a9b1e6b4e3babdebfd660c0bc9a', '删除', 'sys_team_del', 2, 240, '8889ce7ec50247f799605d753a86075d', '', '', NULL, NULL);

INSERT INTO `sys_authority` VALUES ('396c0cee0a68406dbf5996b41290c9bd', '监测因子标签管理', 'monitor_factor_tag', 1, 646, 'e97ab8fca0a544e98cc13fa846f3292e', 'dataDesources/monitorFactorTag/index.do', 'wrBase', NULL, '2020-03-26 15:31:25');
INSERT INTO `sys_authority` VALUES ('cf5802c4b2424477889a0d59eab6ad5f', '编辑', 'monitor_factor_tag_edit', 2, 648, '396c0cee0a68406dbf5996b41290c9bd', '', '', NULL, '2020-03-26 15:31:25');
INSERT INTO `sys_authority` VALUES ('e0c3136aa8f74c9fb1ece2c9a817873e', '添加', 'monitor_factor_tag_add', 2, 649, '396c0cee0a68406dbf5996b41290c9bd', '', '', NULL, '2020-03-26 15:31:25');
INSERT INTO `sys_authority` VALUES ('0ea47bd58c984477a68a6cc396e7b31d', '删除', 'monitor_factor_tag_del', 2, 657, '396c0cee0a68406dbf5996b41290c9bd', '', '', NULL, '2020-03-26 15:31:25');
INSERT INTO `sys_authority` VALUES ('bc6f258324b349e49b53befcdf7a4ecf', '车间看板', 'department_panel', 1, 114, 'cfd251e73b8846c89346a57fa8b6ca3e', 'remoteMonitor/enterpriseMainPanel/departmentMainPanel.do', 'base_icon_department', NULL, '2020-10-29 12:45:41');
INSERT INTO `sys_authority` VALUES ('5c8eb3a84676468aae99da779131103f', '设备看板', 'device_panel', 1, 115, 'cfd251e73b8846c89346a57fa8b6ca3e', 'remoteMonitor/enterpriseMainPanel/deviceMainPanel.do', 'base_icon_department', NULL, '2020-10-30 05:35:32');
