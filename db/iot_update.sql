-- ----------------------------
-- Table modification for monitor_factor_template
-- ----------------------------

ALTER TABLE monitor_factor_template ADD machine_id VARCHAR(32);
ALTER TABLE monitor_factor_template CHANGE  condensing_device_num machine_type varchar(50);
ALTER TABLE monitor_factor_template ADD UNIQUE KEY(factor_code, machine_id);
ALTER TABLE monitor_factor_template ADD `lower_limit_text` varchar(100) DEFAULT '' COMMENT '阈值下限警报信息';
ALTER TABLE monitor_factor_template ADD `upper_limit_text` varchar(100) DEFAULT '' COMMENT '阈值上限警报信息';
ALTER TABLE monitor_factor_template ADD `start_switch` tinyint(1) DEFAULT '0', COMMENT '是否是设备启动开关';

-- ----------------------------
-- Table structure for control_machine
-- ----------------------------
DROP TABLE IF EXISTS `control_machine`;
CREATE TABLE `control_machine` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  `serial_no` varchar(200) DEFAULT NULL,
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '所属终端',
  `enterprise_id` varchar(32) NOT NULL DEFAULT '' COMMENT '企业ID',
  `org_id` varchar(32) DEFAULT NULL,
  `template_id` varchar(32) DEFAULT '' COMMENT '模板ID',
  `machine_no` varchar(16) DEFAULT '' COMMENT '设备编号',
  `machine_name` varchar(32) DEFAULT '' COMMENT '设备名称',
  `runtime` bigint(16) DEFAULT '0' COMMENT '运行时间',
  `machine_type` varchar(32) NOT NULL DEFAULT '' COMMENT '设备类型',
  `machine_model` varchar(32) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT '' COMMENT '图片路径',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `purchasing_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `manufacotry` varchar(255) DEFAULT NULL,
  `machine_usage` varchar(255) DEFAULT NULL,
  `rated_power` float DEFAULT NULL,
  `online_status` tinyint(1) DEFAULT '0',
  `power_status` smallint(1) DEFAULT '0' COMMENT '运行状态，0：停机，1：运行',
  `alarm_status` smallint(1) DEFAULT '0' COMMENT '报警状态，0:停机，1：运行，2：预警，3：报警',
  `status` smallint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `device_code` (`device_code`,`machine_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE control_machine ADD `running_status` smallint(1) DEFAULT '0' COMMENT '设备开关状态，0：停机，1：启动';
ALTER TABLE control_machine ADD `fault_status` smallint(1) DEFAULT '0' COMMENT '故障状态，0：无故障，1：有故障';
ALTER TABLE control_machine Add `early_alarm_status` bigint(16) DEFAULT '0' COMMENT '预警的数量';
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

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table modification for monitor_factor
-- ----------------------------

ALTER TABLE monitor_factor ADD machine_id VARCHAR(32);
ALTER TABLE monitor_factor CHANGE condensing_device_num machine_type varchar(50);
ALTER TABLE monitor_factor ADD `lower_limit_text` varchar(100) DEFAULT '' COMMENT '阈值下限警报信息';
ALTER TABLE monitor_factor ADD `upper_limit_text` varchar(100) DEFAULT '' COMMENT '阈值上限警报信息';
ALTER TABLE monitor_factor ADD `start_switch` tinyint(1) DEFAULT '0', COMMENT '是否是设备启动开关';

INSERT INTO `sys_dictionary`(`id`, `dict_code`, `dict_name`, `is_delete_enable`, `remark`, `create_time`, `modify_time`) VALUES ('3156079fd0b743f89fea90f20c5c30c1', 'machineType', '设备类型', 0, '', '2020-09-03 20:34:53', NULL);

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

UPDATE sys_dictionary_item SET layout = '{row1:{col1:{chart_type:\"line\",factor_tag:\"chart1\",unit:\"mA\"},col2:{chart_type:\"line\",factor_tag:\"chart2\",unit:\"mA\"}},row2:{col1:{chart_type:\"line\",factor_tag:\"chart1\",unit:\"mA\"}}'