/*
Navicat MySQL Data Transfer

Source Server         : iot_test
Source Server Version : 50712

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2020-08-23 12:46:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alarm_info
-- ----------------------------
DROP TABLE IF EXISTS `alarm_info`;
CREATE TABLE `alarm_info` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  `dynamic_id` varchar(32) NOT NULL DEFAULT '' COMMENT '实时ID',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(16) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(16) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `alarm_type` int(1) NOT NULL DEFAULT '0' COMMENT '报警类型，0：预警，1：报警',
  `alarm_content` varchar(32) NOT NULL DEFAULT '' COMMENT '预警/报警内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `recovery_time` datetime DEFAULT NULL COMMENT '报警恢复时间',
  PRIMARY KEY (`id`),
  KEY `dynamic_id` (`dynamic_id`) USING BTREE,
  KEY `device_code` (`device_code`) USING BTREE,
  KEY `condensing_device_num` (`condensing_device_num`) USING BTREE,
  KEY `factor_code` (`factor_code`) USING BTREE,
  KEY `alarm_type` (`alarm_type`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `recovery_time` (`recovery_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for condensing_device
-- ----------------------------
DROP TABLE IF EXISTS `condensing_device`;
CREATE TABLE `condensing_device` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '所属终端',
  `enterprise_id` varchar(32) NOT NULL DEFAULT '' COMMENT '企业ID',
  `template_id` varchar(32) NOT NULL DEFAULT '' COMMENT '模板ID',
  `condensing_device_num` varchar(16) NOT NULL DEFAULT '' COMMENT '冷凝机设备编号',
  `condensing_device_name` varchar(32) NOT NULL DEFAULT '' COMMENT '冷凝机设备名称',
  `alarm_state` int(1) NOT NULL DEFAULT '0' COMMENT '报警状态，0:停机，1：运行，2：预警，3：报警',
  `switch_state` int(1) NOT NULL DEFAULT '0' COMMENT '运行状态，0：停机，1：运行',
  `runtime` int(16) NOT NULL DEFAULT '0' COMMENT '运行时间',
  `device_type` varchar(32) NOT NULL DEFAULT '' COMMENT '设备类型',
  `device_model` varchar(32) NOT NULL,
  `photo_path` varchar(255) NOT NULL DEFAULT '' COMMENT '图片路径',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '设备标识，guid',
  `dynamic_id` varchar(32) NOT NULL DEFAULT '' COMMENT '实时ID',
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '设备编号',
  `device_name` varchar(32) NOT NULL DEFAULT '' COMMENT '设备名称',
  `org_id` varchar(32) NOT NULL DEFAULT '' COMMENT '所属机构Id',
  `enterprise_id` varchar(32) NOT NULL DEFAULT '' COMMENT '所属企业',
  `template_id` varchar(32) NOT NULL DEFAULT '' COMMENT '数据模板',
  `condensing_device_num_str` varchar(100) NOT NULL DEFAULT '' COMMENT '绑定机组',
  `net_state` int(1) NOT NULL DEFAULT '0' COMMENT '在线状态，0：离线，1：在线',
  `alarm_state` int(1) NOT NULL DEFAULT '0' COMMENT '报警状态，0：正常，1：报警',
  `sn` varchar(32) NOT NULL DEFAULT '' COMMENT '终端序列ID',
  `ip_addr` varchar(32) NOT NULL DEFAULT '' COMMENT '当前连接IP',
  `signal_strength` varchar(16) NOT NULL DEFAULT '' COMMENT '信号强度',
  `online_time` varchar(16) NOT NULL DEFAULT '' COMMENT '在线时长',
  `start_time` varchar(16) NOT NULL DEFAULT '' COMMENT '启动时长',
  `operators` varchar(16) NOT NULL DEFAULT '' COMMENT '运营商',
  `lbs_locating` varchar(32) NOT NULL DEFAULT '' COMMENT 'LBS定位信息',
  `network_type` varchar(16) NOT NULL DEFAULT '' COMMENT '网络制式',
  `firmware_version` varchar(64) NOT NULL DEFAULT '' COMMENT '固件版本',
  `cpu_load` varchar(16) NOT NULL DEFAULT '' COMMENT 'CPU负荷',
  `memory_surplus` varchar(32) NOT NULL DEFAULT '' COMMENT '内存剩余容量',
  `memory_percent` varchar(16) NOT NULL DEFAULT '' COMMENT '内存使用百分比',
  `flash_surplus` varchar(16) NOT NULL DEFAULT '' COMMENT 'Flash剩余容量',
  `flash_percent` varchar(16) NOT NULL DEFAULT '' COMMENT ' Flash使用百分比',
  `device_model` varchar(32) NOT NULL DEFAULT '' COMMENT '设备型号',
  `device_type` varchar(32) NOT NULL DEFAULT '' COMMENT '设备类型',
  `device_version` varchar(32) NOT NULL DEFAULT '' COMMENT '设备版本',
  `monitor_type` int(2) NOT NULL DEFAULT '0' COMMENT '监测类型，1:污染源，2:水质，3:噪声，4:大气，5:扬尘，6:地质灾害点',
  `sim_card` varchar(32) NOT NULL DEFAULT '' COMMENT 'SIM卡号',
  `sim_operator` varchar(16) NOT NULL DEFAULT '' COMMENT '手机卡运营商',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '安装地址',
  `install_time` datetime DEFAULT NULL COMMENT '安装时间',
  `longitude` decimal(16,10) NOT NULL DEFAULT '0.0000000000' COMMENT '经度',
  `latitude` decimal(16,10) NOT NULL DEFAULT '0.0000000000' COMMENT '纬度',
  `owner_name` varchar(32) NOT NULL DEFAULT '' COMMENT '业主姓名',
  `owner_phone` varchar(32) NOT NULL DEFAULT '' COMMENT '业主电话',
  `photo_path` varchar(255) NOT NULL DEFAULT '' COMMENT '图片路径',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device_code` (`device_code`) USING BTREE,
  KEY `dynamic_id` (`dynamic_id`) USING BTREE,
  KEY `org_id` (`org_id`) USING BTREE,
  KEY `enterprise_id` (`enterprise_id`) USING BTREE,
  KEY `template_id` (`template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Table structure for device_info_history
-- ----------------------------
DROP TABLE IF EXISTS `device_info_history`;
CREATE TABLE `device_info_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '' COMMENT '设备标识，guid',
  `net_state` int(1) NOT NULL DEFAULT '0' COMMENT '在线状态，0：离线，1：在线',
  `alarm_state` int(1) NOT NULL DEFAULT '0' COMMENT '报警状态，0：正常，1：报警',
  `sn` varchar(32) NOT NULL DEFAULT '' COMMENT '终端序列ID',
  `ip_addr` varchar(32) NOT NULL DEFAULT '' COMMENT '当前连接IP',
  `signal_strength` varchar(16) NOT NULL DEFAULT '' COMMENT '信号强度',
  `online_time` varchar(16) NOT NULL DEFAULT '' COMMENT '在线时长',
  `start_time` varchar(16) NOT NULL DEFAULT '' COMMENT '启动时长',
  `lbs_locating` varchar(32) NOT NULL DEFAULT '' COMMENT 'LBS定位信息',
  `firmware_version` varchar(64) NOT NULL DEFAULT '' COMMENT '固件版本',
  `cpu_load` varchar(16) NOT NULL DEFAULT '' COMMENT 'CPU负荷',
  `memory_surplus` varchar(32) NOT NULL DEFAULT '' COMMENT '内存剩余容量',
  `memory_percent` varchar(16) NOT NULL DEFAULT '' COMMENT '内存使用百分比',
  `flash_surplus` varchar(16) NOT NULL DEFAULT '' COMMENT 'Flash剩余容量',
  `flash_percent` varchar(16) NOT NULL DEFAULT '' COMMENT ' Flash使用百分比',
  `longitude` decimal(16,10) NOT NULL DEFAULT '0.0000000000' COMMENT '经度',
  `latitude` decimal(16,10) NOT NULL DEFAULT '0.0000000000' COMMENT '纬度',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5858 DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Table structure for enterprise_info
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_info`;
CREATE TABLE `enterprise_info` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '企业标识，guid',
  `enterprise_name` varchar(32) NOT NULL DEFAULT '' COMMENT '企业名称',
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '绑定终端',
  `abbreviation` varchar(32) NOT NULL DEFAULT '' COMMENT '企业简称',
  `org_id` varchar(32) NOT NULL DEFAULT '' COMMENT '所属区域Id',
  `basin_id` varchar(32) NOT NULL DEFAULT '' COMMENT '所在流域id',
  `concern_degree` varchar(16) NOT NULL DEFAULT '' COMMENT '关注程度',
  `control_level` varchar(16) NOT NULL DEFAULT '' COMMENT '控制级别',
  `drainage_type` varchar(16) NOT NULL DEFAULT '' COMMENT '排水类型',
  `outlet_position` varchar(32) NOT NULL DEFAULT '' COMMENT '排口位置',
  `drainage_basin` varchar(32) NOT NULL DEFAULT '' COMMENT '排向流域',
  `drainage_river` varchar(32) NOT NULL DEFAULT '' COMMENT '排向河道',
  `enterprise_type` varchar(32) NOT NULL DEFAULT '' COMMENT '企业类型',
  `enterprise_scale` varchar(32) NOT NULL DEFAULT '' COMMENT '企业规模',
  `affiliated_park` varchar(32) NOT NULL DEFAULT '' COMMENT '所属园区',
  `industry_category` varchar(32) NOT NULL DEFAULT '' COMMENT '行业类别',
  `enterprise_credit_code` varchar(32) NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  `legal_representative` varchar(10) NOT NULL DEFAULT '' COMMENT '法人代表',
  `enterprise_product` varchar(1024) NOT NULL DEFAULT '' COMMENT '企业生产的产品',
  `enterprise_overview` varchar(1024) NOT NULL DEFAULT '' COMMENT '企业概况',
  `photo_path` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `longitude` decimal(18,10) NOT NULL DEFAULT '0.0000000000' COMMENT '经度',
  `latitude` decimal(18,10) NOT NULL DEFAULT '0.0000000000' COMMENT '纬度',
  `contact_name` varchar(10) NOT NULL DEFAULT '' COMMENT '联系人',
  `contact_phone` varchar(16) NOT NULL DEFAULT '' COMMENT '联系人电话',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '地址',
  `mail_push_state` int(1) NOT NULL DEFAULT '0' COMMENT '邮箱推送状态，0：不推送，1：推送',
  `mail_address` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱推送地址',
  `wechat_push_state` int(1) NOT NULL DEFAULT '0' COMMENT '企业微信推送状态，0：不推送，1：推送',
  `wechat_address` varchar(100) NOT NULL DEFAULT '' COMMENT '企业微信推送名称',
  `agent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '企业微信推送ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `enterprise_name` (`enterprise_name`) USING BTREE,
  KEY `device_code` (`device_code`) USING BTREE,
  KEY `org_id` (`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for maintenance_record
-- ----------------------------
DROP TABLE IF EXISTS `maintenance_record`;
CREATE TABLE `maintenance_record` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '终端编码',
  `condensing_device_num` varchar(16) NOT NULL DEFAULT '' COMMENT '机组编号',
  `maintenance_time` datetime DEFAULT NULL COMMENT '维修时间',
  `maintenance_duration` varchar(64) NOT NULL DEFAULT '' COMMENT '维修时长',
  `maintenance_content` varchar(500) NOT NULL DEFAULT '' COMMENT '维修保养内容',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mk_wechat_app
-- ----------------------------
DROP TABLE IF EXISTS `mk_wechat_app`;
CREATE TABLE `mk_wechat_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `corp_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `secret` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for mk_wechat_token
-- ----------------------------
DROP TABLE IF EXISTS `mk_wechat_token`;
CREATE TABLE `mk_wechat_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `corp_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `secret` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `expired_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for monitor_factor
-- ----------------------------
DROP TABLE IF EXISTS `monitor_factor`;
CREATE TABLE `monitor_factor` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `condensing_device_num` int(11) NOT NULL,
  `factor_code` varchar(100) DEFAULT '',
  `factor_name` varchar(100) DEFAULT '',
  `factor_tag` varchar(50) DEFAULT '' COMMENT '因子标签',
  `factor_unit` varchar(100) DEFAULT '',
  `data_accuracy` decimal(32,10) NOT NULL DEFAULT '1.0000000000' COMMENT '数据精度，当前值乘以数据精度',
  `decimal_digits` int(11) NOT NULL DEFAULT '2' COMMENT '保留的小数位',
  `protocol` varchar(100) DEFAULT '',
  `type_id` int(11) NOT NULL DEFAULT '1',
  `alarm_state` int(1) NOT NULL DEFAULT '0' COMMENT '是否报警，0：不报警：1：报警',
  `lower_limit` varchar(16) NOT NULL DEFAULT '0' COMMENT '阈值下限',
  `upper_limit` varchar(16) NOT NULL DEFAULT '0' COMMENT '阈值上限',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`condensing_device_num`,`factor_code`,`type_id`,`alarm_state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for monitor_factor_template
-- ----------------------------
DROP TABLE IF EXISTS `monitor_factor_template`;
CREATE TABLE `monitor_factor_template` (
  `id` varchar(32) NOT NULL,
  `device_code` varchar(32) NOT NULL DEFAULT '' COMMENT '绑定终端',
  `condensing_device_num` int(11) NOT NULL,
  `factor_code` varchar(100) DEFAULT '',
  `factor_name` varchar(100) DEFAULT '',
  `factor_tag` varchar(50) DEFAULT '' COMMENT '因子标签',
  `factor_unit` varchar(100) DEFAULT '',
  `data_accuracy` decimal(32,10) NOT NULL DEFAULT '1.0000000000' COMMENT '数据精度，当前值乘以数据精度',
  `decimal_digits` int(11) NOT NULL DEFAULT '2' COMMENT '保留的小数位',
  `protocol` varchar(100) DEFAULT '',
  `type_id` int(11) NOT NULL DEFAULT '1',
  `alarm_state` int(1) NOT NULL DEFAULT '0' COMMENT '是否报警，0：不报警：1：报警',
  `lower_limit` varchar(16) NOT NULL DEFAULT '0' COMMENT '阈值下限',
  `upper_limit` varchar(16) NOT NULL DEFAULT '0' COMMENT '阈值上限',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`device_code`,`condensing_device_num`,`factor_code`) USING BTREE,
  KEY `device_code` (`device_code`) USING BTREE,
  KEY `condensing_device_num` (`condensing_device_num`) USING BTREE,
  KEY `factor_code` (`factor_code`) USING BTREE,
  KEY `protocol` (`protocol`) USING BTREE,
  KEY `type_id` (`type_id`) USING BTREE,
  KEY `alarm_state` (`alarm_state`) USING BTREE,
  KEY `factor_tag` (`device_code`,`factor_tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rtd_history
-- ----------------------------
DROP TABLE IF EXISTS `rtd_history`;
CREATE TABLE `rtd_history` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '标识，guid',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(8) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(10) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`),
  KEY `device_code` (`device_code`,`collect_time`),
  KEY `device_code_2` (`device_code`,`condensing_device_num`,`collect_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rtd_history_dummy
-- ----------------------------
DROP TABLE IF EXISTS `rtd_history_dummy`;
CREATE TABLE `rtd_history_dummy` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '标识，guid',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(8) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(10) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rtd_history_latestday
-- ----------------------------
DROP TABLE IF EXISTS `rtd_history_latestday`;
CREATE TABLE `rtd_history_latestday` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '标识，guid',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(8) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(10) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`),
  KEY `device_code` (`device_code`,`collect_time`),
  KEY `factor_code` (`device_code`,`condensing_device_num`,`factor_code`,`collect_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rtd_history_latestmonth
-- ----------------------------
DROP TABLE IF EXISTS `rtd_history_latestmonth`;
CREATE TABLE `rtd_history_latestmonth` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '标识，guid',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(8) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(10) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`),
  KEY `device_code` (`device_code`,`collect_time`),
  KEY `factor_code` (`device_code`,`condensing_device_num`,`factor_code`,`collect_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rtd_history_latestweek
-- ----------------------------
DROP TABLE IF EXISTS `rtd_history_latestweek`;
CREATE TABLE `rtd_history_latestweek` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '标识，guid',
  `device_code` varchar(16) NOT NULL DEFAULT '' COMMENT '所属终端',
  `condensing_device_num` varchar(2) NOT NULL DEFAULT '' COMMENT '设备编号',
  `factor_code` varchar(8) NOT NULL DEFAULT '' COMMENT '监测因子编码',
  `factor_value` varchar(10) NOT NULL DEFAULT '' COMMENT '监测因子数值',
  `collect_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`),
  KEY `device_code` (`device_code`,`collect_time`),
  KEY `factor_code` (`device_code`,`condensing_device_num`,`factor_code`,`collect_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '权限id，guid',
  `authority_name` varchar(64) NOT NULL DEFAULT '' COMMENT '权限名称',
  `authority_code` varchar(64) NOT NULL DEFAULT '' COMMENT '权限编码，唯一',
  `authority_type` tinyint(4) DEFAULT NULL COMMENT '资源类型：1菜单；2功能',
  `authority_order` int(11) NOT NULL DEFAULT '0' COMMENT '权限顺序，数值越小排在前面',
  `authority_parent_id` varchar(32) DEFAULT NULL COMMENT '该权限所属的父权限 0为顶级权限',
  `authority_url` varchar(256) DEFAULT NULL COMMENT '该权限对应的url',
  `authority_image` varchar(256) DEFAULT NULL COMMENT '该权限对应的image',
  `create_time` datetime DEFAULT NULL COMMENT ' 创建时间',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

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
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(32) NOT NULL,
  `log_type` int(11) DEFAULT '0' COMMENT '日志类型',
  `log_detail` varchar(1024) DEFAULT NULL,
  `user_ip` varchar(128) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '机构id，guid',
  `org_code` varchar(32) NOT NULL DEFAULT '' COMMENT '机构代码，唯一索引',
  `org_name` varchar(64) NOT NULL DEFAULT '' COMMENT '机构名称',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父机构id，如果为根节点，则为‘’',
  `org_id_path` varchar(320) DEFAULT '' COMMENT '所属机构路径',
  `sort_idx` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_org_code` (`org_code`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `sort_idx` (`sort_idx`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色id，guid',
  `org_id` varchar(32) NOT NULL DEFAULT '' COMMENT '据点id',
  `role_name` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(64) DEFAULT '' COMMENT '角色编码，唯一',
  `authority_code` varchar(7600) DEFAULT '' COMMENT '权限编码值，一串权限编码的组合，由权限编码组成，以:分隔',
  `authority_desc` varchar(5600) DEFAULT '' COMMENT '权限名称值，一串权限名称的组合，由权限名称组成，以,分隔',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id，guid',
  `user_code` varchar(32) NOT NULL DEFAULT '' COMMENT '用户编号，唯一索引',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_password` varchar(128) NOT NULL DEFAULT '' COMMENT '平台登录密码SM3',
  `terminal_user_password` varchar(128) DEFAULT '' COMMENT '终端操作密码SM3',
  `id_card` varchar(32) DEFAULT '' COMMENT '身份证号',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别，1：男；2：女',
  `telephone` varchar(32) DEFAULT '' COMMENT '电话',
  `cellphone` varchar(32) DEFAULT '' COMMENT '手机',
  `photo_path` varchar(256) DEFAULT '' COMMENT '照片路径',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，1：正常；2：禁止登录平台；3：禁止操作终端',
  `remark` varchar(1024) DEFAULT '' COMMENT ' 备注',
  `org_id` varchar(32) DEFAULT '' COMMENT '所属机构id，最基层的机构',
  `org_id_path` varchar(320) DEFAULT '' COMMENT '所属机构路径，从一级到最基层，最多10级，各个机构id之间用:（英文冒号）分隔',
  `role_id` varchar(32) DEFAULT '' COMMENT '角色id',
  `identity_idx` tinyint(4) DEFAULT '0' COMMENT '用户身份序号，数值越小级别越高',
  `identity_name` varchar(32) DEFAULT '' COMMENT '用户身份名称',
  `title_name` varchar(32) DEFAULT '' COMMENT '用户职称',
  `rank_name` varchar(32) DEFAULT '' COMMENT '用户职级',
  `reg_from` tinyint(4) DEFAULT '0' COMMENT '注册来源，0平台；1终端',
  `deadline` datetime DEFAULT NULL COMMENT '账号最后期限',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表，用于登录管理平台和终端操作';

-- ----------------------------
-- View structure for v_rtd_history
-- ----------------------------
DROP VIEW IF EXISTS `v_rtd_history`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dcmcadmin`@`%` SQL SECURITY DEFINER VIEW `v_rtd_history` AS select `rtd_history`.`id` AS `id`,`rtd_history`.`device_code` AS `device_code`,`rtd_history`.`condensing_device_num` AS `condensing_device_num`,`rtd_history`.`factor_code` AS `factor_code`,`rtd_history`.`factor_value` AS `factor_value_o`,(`rtd_history`.`factor_value` * `monitor_factor_template`.`data_accuracy`) AS `factor_value`,`rtd_history`.`collect_time` AS `collect_time`,`monitor_factor_template`.`factor_unit` AS `factor_unit`,`monitor_factor_template`.`data_accuracy` AS `data_accuracy`,`monitor_factor_template`.`decimal_digits` AS `decimal_digits`,`monitor_factor_template`.`protocol` AS `protocol`,`monitor_factor_template`.`type_id` AS `type_id`,`monitor_factor_template`.`alarm_state` AS `alarm_state`,`monitor_factor_template`.`lower_limit` AS `lower_limit`,`monitor_factor_template`.`upper_limit` AS `upper_limit`,`monitor_factor_template`.`factor_tag` AS `factor_tag`,`monitor_factor_template`.`factor_name` AS `factor_name` from (`rtd_history` join `monitor_factor_template` on(((`rtd_history`.`condensing_device_num` = `monitor_factor_template`.`condensing_device_num`) and (`rtd_history`.`factor_code` = `monitor_factor_template`.`factor_code`)))) where ((`monitor_factor_template`.`decimal_digits` > 0) and (`monitor_factor_template`.`type_id` = 1)) ;

-- ----------------------------
-- View structure for v_rtd_history_aggr
-- ----------------------------
DROP VIEW IF EXISTS `v_rtd_history_aggr`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dcmcadmin`@`%` SQL SECURITY DEFINER VIEW `v_rtd_history_aggr` AS select `r`.`device_code` AS `device_code`,date_format(`r`.`collect_time`,'%Y-%m-%d %H:%i') AS `ctime`,count(0) AS `count(*)` from `rtd_history` `r` group by `r`.`device_code`,`ctime` order by `r`.`device_code`,`ctime` desc ;

-- ----------------------------
-- View structure for v_rtd_history_aggr2
-- ----------------------------
DROP VIEW IF EXISTS `v_rtd_history_aggr2`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dcmcadmin`@`%` SQL SECURITY DEFINER VIEW `v_rtd_history_aggr2` AS select `r`.`device_code` AS `device_code`,`r`.`collect_time` AS `collect_time`,count(0) AS `count(*)` from `rtd_history` `r` group by `r`.`device_code`,`r`.`collect_time` order by `r`.`device_code`,`r`.`collect_time` desc ;

-- ----------------------------
-- View structure for v_rtd_history_latestday
-- ----------------------------
DROP VIEW IF EXISTS `v_rtd_history_latestday`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dcmcadmin`@`%` SQL SECURITY DEFINER VIEW `v_rtd_history_latestday` AS select `rtd_history`.`id` AS `id`,`rtd_history`.`device_code` AS `device_code`,`rtd_history`.`condensing_device_num` AS `condensing_device_num`,`rtd_history`.`factor_code` AS `factor_code`,`rtd_history`.`factor_value` AS `factor_value_o`,(`rtd_history`.`factor_value` * `monitor_factor_template`.`data_accuracy`) AS `factor_value`,`rtd_history`.`collect_time` AS `collect_time`,`monitor_factor_template`.`factor_unit` AS `factor_unit`,`monitor_factor_template`.`data_accuracy` AS `data_accuracy`,`monitor_factor_template`.`decimal_digits` AS `decimal_digits`,`monitor_factor_template`.`protocol` AS `protocol`,`monitor_factor_template`.`type_id` AS `type_id`,`monitor_factor_template`.`alarm_state` AS `alarm_state`,`monitor_factor_template`.`lower_limit` AS `lower_limit`,`monitor_factor_template`.`upper_limit` AS `upper_limit`,`monitor_factor_template`.`factor_tag` AS `factor_tag`,`monitor_factor_template`.`factor_name` AS `factor_name` from (`rtd_history_latestday` `rtd_history` join `monitor_factor_template` on(((`rtd_history`.`condensing_device_num` = `monitor_factor_template`.`condensing_device_num`) and (`rtd_history`.`factor_code` = `monitor_factor_template`.`factor_code`)))) where ((`monitor_factor_template`.`decimal_digits` > 0) and (`monitor_factor_template`.`type_id` = 1)) ;

-- ----------------------------
-- Procedure structure for batch_job_daily
-- ----------------------------
DROP PROCEDURE IF EXISTS `batch_job_daily`;
DELIMITER ;;

;;
DELIMITER ;

-- ----------------------------
-- Event structure for batch_job_daily
-- ----------------------------
DROP EVENT IF EXISTS `batch_job_daily`;
DELIMITER ;;
CREATE DEFINER=`dcmcadmin`@`%` EVENT `batch_job_daily` ON SCHEDULE EVERY 1 DAY STARTS '2020-06-22 02:05:00' ON COMPLETION NOT PRESERVE ENABLE DO call batch_job_daily()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `after_insert`;
DELIMITER ;;
CREATE TRIGGER `after_insert` AFTER INSERT ON `rtd_history` FOR EACH ROW begin
insert into rtd_history_latestday(
id,
device_code,
condensing_device_num,
factor_code,
factor_value,
collect_time
)values(
NEW.id,
NEW.device_code,
NEW.condensing_device_num,
NEW.factor_code,
NEW.factor_value,
NEW.collect_time);

insert into rtd_history_latestweek(
id,
device_code,
condensing_device_num,
factor_code,
factor_value,
collect_time
)values(
NEW.id,
NEW.device_code,
NEW.condensing_device_num,
NEW.factor_code,
NEW.factor_value,
NEW.collect_time);

insert into rtd_history_latestmonth(
id,
device_code,
condensing_device_num,
factor_code,
factor_value,
collect_time
)values(
NEW.id,
NEW.device_code,
NEW.condensing_device_num,
NEW.factor_code,
NEW.factor_value,
NEW.collect_time);
 end
;;
DELIMITER ;
