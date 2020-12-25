-- MySQL dump 10.13  Distrib 8.0.18, for osx10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: iot_test
-- ------------------------------------------------------
-- Server version	5.7.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_authority`
--

DROP TABLE IF EXISTS `sys_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_authority`
--

LOCK TABLES `sys_authority` WRITE;
/*!40000 ALTER TABLE `sys_authority` DISABLE KEYS */;
INSERT INTO `sys_authority` VALUES ('081e7b5c8aab475799d40b30dc7e4086','编辑','data_template_edit',2,643,'197455a39e5540f2b9c89169734bbc0f','','',NULL,NULL),('08937f6426f34e6196acfb748eb38e7a','删除','sys_role_del',2,293,'8d9088321c8e445a8026daad1754f930','','','2017-11-13 10:47:14','2018-09-29 10:57:28'),('0fa17e753aa14373baf89b1ef8bb5d63','删除','sys_dict_del',2,289,'2b4e247c9a4e4c748e269c1915e23a2c','','',NULL,NULL),('1545b681213945edab428d0945d566d0','机组列表','condensing_device_monitor',1,120,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/condensingDeviceMonitor/index.do','system_icon_sysOrg',NULL,'2020-03-26 15:08:44'),('197455a39e5540f2b9c89169734bbc0f','监测因子模板管理','monitor_factor_template',1,650,'e97ab8fca0a544e98cc13fa846f3292e','dataDesources/monitorFactorTemplate/index.do','base_icon_baseCabinetType',NULL,'2020-03-26 15:31:25'),('1d0f7a96d58449bcb7a713eb5ede7086','删除','condensing_device_del',2,122,'a4bd186b06d844e2bdba171639d43e39','','',NULL,NULL),('1e01d677e77344f2913b95de37295304','添加','sys_authority_add',2,341,'6323cc55d1764646b35f6ac8d798cdaf','','',NULL,NULL),('1f36e7a57e5d4845a898a90c694d54f0','编辑','sys_dict_edit',2,288,'2b4e247c9a4e4c748e269c1915e23a2c','','',NULL,NULL),('1fdd4fed150a4502b629974d79447bbc','企业信息','enterprise_info',1,654,'e97ab8fca0a544e98cc13fa846f3292e','dataDesources/enterpriseInfo/index.do','sysOrg',NULL,'2020-03-26 15:32:14'),('23c69d19ace540b2a82b49196fb4f322','详细状态','detailed_state',1,123,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/detailedState/detailedState.do','wrWaterReport',NULL,'2020-03-26 15:03:35'),('2b4e247c9a4e4c748e269c1915e23a2c','数据字典管理','sys_dict',1,286,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysDictionary/index.do','wrBase',NULL,'2020-03-26 15:36:15'),('2be8f142cab243e9b1cc8c682d014c90','添加','condensing_device_add',2,120,'a4bd186b06d844e2bdba171639d43e39','','',NULL,NULL),('2cf171f7bf4144ff812b5e7b37ffcfde','系统登录日志','sys_login_log',1,255,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysLoginLog/index.do','log_icon_logCabinetMaintain',NULL,'2020-03-26 15:35:38'),('3354d1d835c943b78ee5069513c69e78','编辑','condensing_device_edit',2,121,'a4bd186b06d844e2bdba171639d43e39','','',NULL,NULL),('4147b9bc302c4d1bac6ee3e881b5172b','删除','device_info_del',2,112,'d7efc4ecf020422b8e33bf3e480d7c8e','','',NULL,'2020-02-26 15:57:34'),('4ee8b0d4e56f4efda913752ab0748e3b','系统管理','sys',1,700,'0','system/index.do','environment_events_manage','2017-11-12 17:46:45','2020-03-26 15:27:10'),('504e004379c74b028ea0a5978a0cc6b9','监测因子管理','monitor_factor',1,645,'e97ab8fca0a544e98cc13fa846f3292e','dataDesources/monitorFactor/index.do','wrBase',NULL,'2020-03-26 15:30:52'),('50562029eea04ccc8f6024a478c8c11d','终端管理','device',1,555,'0','deviceManage/index.do','environment_monitor',NULL,'2020-03-26 15:26:34'),('529574782561435d9b45e06d228a0ce9','添加','enterprise_info_add',2,110,'1fdd4fed150a4502b629974d79447bbc','','',NULL,NULL),('55f97a4e813d485fb3ca6cfc8b8b4267','删除','sys_authority_del',2,343,'6323cc55d1764646b35f6ac8d798cdaf','','',NULL,NULL),('5a8f6aa21aba4628a0ad17a58266e803','添加','sys_user_add',2,241,'8d9088321c8e445a8026daad1754f931','','','2017-11-13 10:47:19','2018-09-29 10:55:54'),('615c8292838c43cea442f8427f7e2591','添加','maintenance_record_add',2,126,'f5686f0eb59a4d41a1075641c4ee83fa','','',NULL,NULL),('6323cc55d1764646b35f6ac8d798cdaf','菜单配置','sys_authority',1,340,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysAuthority/index.do','system_icon_sysSmsSendTpl','2018-12-03 15:14:15','2020-03-26 15:38:59'),('700be0e95da44127904cdae0a707c978','删除','enterprise_info_del',2,112,'1fdd4fed150a4502b629974d79447bbc','','',NULL,NULL),('714e94e4d2374ce68391aee13ebc18f0','删除','sys_org_del',2,238,'7266bc58a7a94854aab576ce97a2acd0','','','2017-11-13 10:47:08','2018-09-29 10:55:53'),('71cf6b38640c48339d0560b8407869a5','查看','sys_user_view',2,242,'8d9088321c8e445a8026daad1754f931','','','2017-11-13 10:47:19','2018-09-30 09:30:54'),('7266bc58a7a94854aab576ce97a2acd0','组织管理','sys_org',1,235,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysOrg/index.do','system_icon_sysOrg','2017-11-13 10:47:08','2020-03-26 15:34:55'),('75e6bbf575404277addcfaf8d723c1c4','企业信息','enterprise_info_monitor',1,105,'cfd251e73b8846c89346a57fa8b6ca3e','remoteMonitor/enterpriseInfoMonitor/enterpriseInfoMonitor.do','sysOrg',NULL,'2020-03-26 15:01:20'),('78c421238e544d7d8282229d961c061b','删除','monitor_factor_del',2,651,'504e004379c74b028ea0a5978a0cc6b9','','',NULL,NULL),('7a0abd659a98405b97b72777f2aa669c','机组信息','condensing_device_info',1,113,'cfd251e73b8846c89346a57fa8b6ca3e','','system_icon_sysUserRank',NULL,'2020-03-26 15:40:04'),('7aeabd6073f646849518b97c570de596','删除','sys_user_del',2,244,'8d9088321c8e445a8026daad1754f931','','','2017-11-13 10:47:19','2018-09-30 09:30:59'),('83607560eb91455da4e509b34e5cd4ff','添加','monitor_factor_add',2,655,'504e004379c74b028ea0a5978a0cc6b9','','',NULL,NULL),('8d9088321c8e445a8026daad1754f930','角色管理','sys_role',1,231,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysRole/index.do','fdDuty','2017-11-13 10:47:14','2020-03-26 15:37:14'),('8d9088321c8e445a8026daad1754f931','用户管理','sys_user',1,230,'4ee8b0d4e56f4efda913752ab0748e3b','system/sysUser/index.do','sysUser','2017-11-13 10:47:19','2020-03-26 15:33:48'),('8e9e4cfff6444d2ca0f14784c11d300e','按天趋势','day_operation_trend',1,124,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/dayOperationTrend/dayOperationTrend.do','-wuranyuanjiance',NULL,'2020-03-26 15:05:50'),('9a276bcbda7341558074b9284f314cd7','编辑','monitor_factor_edit',2,652,'504e004379c74b028ea0a5978a0cc6b9','','',NULL,NULL),('a4bd186b06d844e2bdba171639d43e39','机组管理','condensing_device',1,660,'e97ab8fca0a544e98cc13fa846f3292e','dataDesources/condensingDevice/index.do','system_icon_sysOrg',NULL,'2020-02-24 09:54:43'),('a7211afa34db4fe08d27fca228c459f3','编辑','sys_user_edit',2,243,'8d9088321c8e445a8026daad1754f931','','','2017-11-13 10:47:19','2018-09-30 09:30:56'),('a7744a544f4a4d0496732aa087a06730','编辑','device_info_edit',2,111,'d7efc4ecf020422b8e33bf3e480d7c8e','','',NULL,'2020-02-26 15:57:27'),('a78d006a9b10414682973a94151c23e1','报警状态','alarm_info',1,122,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/alarmInfo/alarmInfo.do','wlAlarm',NULL,'2020-03-26 14:58:51'),('aaf5b626da0c4ebeb94481697f512202','GIS地图','enterprise_map',1,107,'cfd251e73b8846c89346a57fa8b6ca3e','remoteMonitor/enterpriseInfoMonitor/enterpriseMap.do','wlMap',NULL,'2020-03-26 15:01:29'),('b59c0529f2194249b7571efe37922295','删除','maintenance_record_del',2,128,'f5686f0eb59a4d41a1075641c4ee83fa','','',NULL,NULL),('b655691792b24426950ccc38a507bb86','导入','sys_user_import',2,245,'8d9088321c8e445a8026daad1754f931','','','2017-11-13 10:47:19','2018-09-30 09:31:01'),('ba687061ac164663a6dcee495335a5fb','编辑','maintenance_record_edit',2,127,'f5686f0eb59a4d41a1075641c4ee83fa','','',NULL,NULL),('bfc3b3da008047d7acd88dcb13af6cfe','企业主看板','enterprise_main_panel',1,111,'cfd251e73b8846c89346a57fa8b6ca3e','remoteMonitor/enterpriseMainPanel/enterpriseMainPanel.do','base_icon_baseMission',NULL,'2020-03-26 15:38:29'),('c5c7712f965842429d1a687b6a8d0246','运行趋势','operation_trend',1,121,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/operationTrend/operationTrend.do','shijiantongji',NULL,'2020-03-26 14:57:16'),('c7dfe1c459c64835b9480319717836a5','添加','sys_dict_add',2,287,'2b4e247c9a4e4c748e269c1915e23a2c','','',NULL,NULL),('cfd251e73b8846c89346a57fa8b6ca3e','远程监控','remote_monitor',1,100,'0','remoteMonitor/index.do','nav_icon_szjc',NULL,'2020-03-26 15:25:52'),('d0ca2dfd27ef4dbe9337f1b39c7f5ef3','添加','sys_role_add',2,291,'8d9088321c8e445a8026daad1754f930','','','2017-11-13 10:47:14','2018-09-29 10:57:24'),('d3bc649678434976a87133e7a832c925','编辑','enterprise_info_edit',2,111,'1fdd4fed150a4502b629974d79447bbc','','',NULL,NULL),('d45524e724b74d00adc203790e7e0af7','编辑','sys_authority_edit',2,342,'6323cc55d1764646b35f6ac8d798cdaf','','',NULL,NULL),('d7efc4ecf020422b8e33bf3e480d7c8e','终端列表','device_info',1,110,'50562029eea04ccc8f6024a478c8c11d','device/deviceInfo/deviceInfo.do','wrPumpStation',NULL,'2020-03-26 15:30:32'),('d98a8c4ce2c84e15ad727ae10c94a92a','添加','data_template_add',2,641,'197455a39e5540f2b9c89169734bbc0f','','',NULL,NULL),('e0befcd3c6a64f9196395a98c881d8ff','添加','device_info_add',2,110,'d7efc4ecf020422b8e33bf3e480d7c8e','','',NULL,'2020-02-26 15:57:20'),('e5b5a1b8552b449d99e36efcbfdd478c','编辑','sys_org_edit',2,237,'7266bc58a7a94854aab576ce97a2acd0','','','2017-11-13 10:47:08','2018-09-29 14:42:16'),('e8932c5108054b9bba7dd069220a3b97','编辑','sys_role_edit',2,292,'8d9088321c8e445a8026daad1754f930','','','2017-11-13 10:47:14','2019-07-07 16:50:59'),('e97ab8fca0a544e98cc13fa846f3292e','基础信息管理','data_resources',1,600,'0','dataDesources/index.do','nav_icon_tjfx',NULL,'2020-03-26 15:26:08'),('eb11c37cc346489ab16a495122b76402','查看','sys_log_view',2,256,'2cf171f7bf4144ff812b5e7b37ffcfde','','',NULL,NULL),('f07818d60aa64e5e850117664c7bd2d1','删除','data_template_del',2,642,'197455a39e5540f2b9c89169734bbc0f','','',NULL,NULL),('f5686f0eb59a4d41a1075641c4ee83fa','维护保养','maintenance_record',1,125,'7a0abd659a98405b97b72777f2aa669c','remoteMonitor/maintenanceRecord/maintenanceRecord.do','ziyuan',NULL,'2020-04-03 06:10:27'),('f7ec77c5994f420b884a4550b09865c8','添加','sys_org_add',2,236,'7266bc58a7a94854aab576ce97a2acd0','','','2017-11-13 10:47:08','2018-09-29 10:55:53');
/*!40000 ALTER TABLE `sys_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary`
--

DROP TABLE IF EXISTS `sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary`
--

LOCK TABLES `sys_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` VALUES ('1f6a1a8d94994e1d98531e4ba4331d2d','industry_category','行业类别',0,'','2019-09-05 11:09:56',NULL),('818f744118ce4361a78b82cb83448086','deviceType','设备类型',0,'','2019-09-06 14:57:13',NULL),('91879397926e40d0aa6e31073d662a45','enterprise_control_level','客户类型',0,'','2019-09-05 11:14:30','2020-02-26 16:36:26'),('bbbe43d11e114d2eb908ad2971f844f1','enterprise_park','维护分公司',0,'','2019-09-05 11:04:57','2020-02-26 16:35:49');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary_item`
--

DROP TABLE IF EXISTS `sys_dictionary_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary_item`
--

LOCK TABLES `sys_dictionary_item` WRITE;
/*!40000 ALTER TABLE `sys_dictionary_item` DISABLE KEYS */;
INSERT INTO `sys_dictionary_item` VALUES ('0b074bd2587c4e3ea74007b8020ac240','91879397926e40d0aa6e31073d662a45','民企',3,1,3,'','2019-09-05 11:15:33','2020-02-26 16:37:14'),('0d262ce9ddc743928abe3e19a5590f1f','91879397926e40d0aa6e31073d662a45','国企',1,1,1,'','2019-09-05 11:14:55','2020-02-26 16:36:53'),('44ca218418b343cd801673fea807f630','1f6a1a8d94994e1d98531e4ba4331d2d','其他行业',99,1,99,'','2019-09-05 11:13:13',NULL),('8d921a598e064538b5e6eb87d43f92de','91879397926e40d0aa6e31073d662a45','日企',2,1,2,'','2019-09-05 11:15:10','2020-02-26 16:36:59'),('ac7acee5228541b289bab302a557ff27','818f744118ce4361a78b82cb83448086','冰水机',4,1,2,'','2020-03-25 14:46:34','2020-03-25 16:02:46'),('bb3d5bd301044a6da3951deed602b3d4','818f744118ce4361a78b82cb83448086','螺杆式压缩机',2,1,0,'','2020-02-26 11:29:38','2020-03-25 16:02:20'),('bb4b010952db4e2cb02e835a6123f7e2','1f6a1a8d94994e1d98531e4ba4331d2d','工业制造行业',2,1,2,'','2019-09-05 11:10:54','2020-02-26 16:34:20'),('c5be3af1f65644d5bef18cda4ab84700','1f6a1a8d94994e1d98531e4ba4331d2d','食品加工行业',1,1,1,'','2019-09-05 11:10:40','2020-02-26 16:34:05'),('f4b77990e71f4d2fbc9a6699f23a987d','bbbe43d11e114d2eb908ad2971f844f1','前川迈坤国际贸易(上海)有限公司',1,1,1,'','2019-09-05 11:08:09','2020-02-26 16:35:39'),('f6cc7bd17d454c7e9d98b07bf2a65080','818f744118ce4361a78b82cb83448086','活塞式压缩机',1111,1,0,'','2020-03-25 14:49:14','2020-03-25 16:01:41'),('ffac1fbf056e4b869d23016134cfdab3','1f6a1a8d94994e1d98531e4ba4331d2d','物流运输行业',3,1,3,'','2019-09-05 11:11:08','2020-02-26 16:34:32');
/*!40000 ALTER TABLE `sys_dictionary_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_org`
--

DROP TABLE IF EXISTS `sys_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_org`
--

LOCK TABLES `sys_org` WRITE;
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` VALUES ('01c6d377439a4b4f86a50d73536ca2a8','011','SHYLD','f395cdb383154a73b00459f42f127fec','37790373a8f14f01a19297fe2d733681:f395cdb383154a73b00459f42f127fec:01c6d377439a4b4f86a50d73536ca2a8',1,'','2020-03-23 14:35:47','2020-03-23 14:38:55'),('022a0b2e51a74893863ecb8d42fc51eb','004','ZDLK','65ec9a72a8a448c091161d68c4f3b396','37790373a8f14f01a19297fe2d733681:65ec9a72a8a448c091161d68c4f3b396:022a0b2e51a74893863ecb8d42fc51eb',3,'','2020-03-19 14:28:09','2020-03-23 14:43:12'),('0a270960912a4df6b811d91ba66913f3','021','GZGBRZY','ecc933f63c67428f8c99629b5403ebce','37790373a8f14f01a19297fe2d733681:ecc933f63c67428f8c99629b5403ebce:0a270960912a4df6b811d91ba66913f3',2,'','2020-03-23 14:40:31','2020-03-23 14:41:51'),('352564a3c44a4430877e309cbec468e4','030','AQFH','37790373a8f14f01a19297fe2d733681','37790373a8f14f01a19297fe2d733681:352564a3c44a4430877e309cbec468e4',100,'','2020-03-23 14:34:34',NULL),('37790373a8f14f01a19297fe2d733681','001','上海前川迈坤机械设备','','37790373a8f14f01a19297fe2d733681',100,'','2019-04-12 20:51:08','2020-03-19 14:23:56'),('51f8361d52b740a5957da4e0e9ed42ff','014','GZYLD3C','f395cdb383154a73b00459f42f127fec','37790373a8f14f01a19297fe2d733681:f395cdb383154a73b00459f42f127fec:51f8361d52b740a5957da4e0e9ed42ff',4,'','2020-03-23 14:38:06','2020-03-23 14:39:14'),('5cd0ba60d43f4b6cbb1a34bb0884bad8','121','ZDCD','37790373a8f14f01a19297fe2d733681','5cd0ba60d43f4b6cbb1a34bb0884bad8:37790373a8f14f01a19297fe2d733681',1,'','2019-07-09 15:29:46','2020-03-23 14:45:10'),('65ec9a72a8a448c091161d68c4f3b396','00','ZD','37790373a8f14f01a19297fe2d733681','37790373a8f14f01a19297fe2d733681:65ec9a72a8a448c091161d68c4f3b396',2,'','2020-03-19 14:25:01','2020-03-23 14:45:18'),('6d69ec57e15f428788cce4d6c3af870a','012','GZYLD1C','f395cdb383154a73b00459f42f127fec','37790373a8f14f01a19297fe2d733681:f395cdb383154a73b00459f42f127fec:6d69ec57e15f428788cce4d6c3af870a',2,'','2020-03-23 14:36:39','2020-03-23 14:38:47'),('7fa017b9403c4cb68a10f25bcaec83ed','022','ZJGBRZY','ecc933f63c67428f8c99629b5403ebce','37790373a8f14f01a19297fe2d733681:ecc933f63c67428f8c99629b5403ebce:7fa017b9403c4cb68a10f25bcaec83ed',1,'','2020-03-23 14:41:29','2020-03-23 14:41:43'),('b331eb5bbf1b44c686122a9b9c4ea480','003','ZDHB','65ec9a72a8a448c091161d68c4f3b396','37790373a8f14f01a19297fe2d733681:65ec9a72a8a448c091161d68c4f3b396:b331eb5bbf1b44c686122a9b9c4ea480',4,'','2020-03-19 14:27:36','2020-03-23 14:43:21'),('e1e370581f3a421fa28b732245e7fdb7','013','GZYLD2C','f395cdb383154a73b00459f42f127fec','37790373a8f14f01a19297fe2d733681:f395cdb383154a73b00459f42f127fec:e1e370581f3a421fa28b732245e7fdb7',3,'','2020-03-23 14:37:25','2020-03-23 14:39:05'),('ea616225dea341058ad40a0efe4c9e73','002','ZDYW','65ec9a72a8a448c091161d68c4f3b396','37790373a8f14f01a19297fe2d733681:65ec9a72a8a448c091161d68c4f3b396:ea616225dea341058ad40a0efe4c9e73',2,'','2020-03-19 14:27:00','2020-03-23 14:56:16'),('ecc933f63c67428f8c99629b5403ebce','020','BRZY','37790373a8f14f01a19297fe2d733681','37790373a8f14f01a19297fe2d733681:ecc933f63c67428f8c99629b5403ebce',4,'','2020-03-23 14:33:20','2020-03-23 14:45:36'),('f395cdb383154a73b00459f42f127fec','010','YLD','37790373a8f14f01a19297fe2d733681','37790373a8f14f01a19297fe2d733681:f395cdb383154a73b00459f42f127fec',3,'','2020-03-23 14:32:39','2020-03-23 14:45:25');
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('00000000000000000000000000000001','','默认角色','default','workbench,workbench_view,workbench_export','工作台','2018-03-21 11:43:46','2018-03-21 14:00:50'),('1dfcaadbb77249c28c2a9ff5a9356644','6d69ec57e15f428788cce4d6c3af870a','GZYLD01','022','remote_monitor,enterprise_info_monitor,enterprise_map,enterprise_main_panel,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,GIS地图,企业主看板,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-23 15:44:38','2020-04-03 15:06:15'),('2efb6627251c40fcb0b4f737c8ea4854','022a0b2e51a74893863ecb8d42fc51eb','ZDLK','11','remote_monitor,enterprise_info_monitor,enterprise_main_panel,enterprise_map,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,企业主看板,GIS地图,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-19 14:38:30','2020-03-23 14:59:00'),('317e1ae4cd0640cebb07d5d501d9a1b5','b331eb5bbf1b44c686122a9b9c4ea480','ZDHB','23','remote_monitor,enterprise_info_monitor,enterprise_main_panel,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,企业主看板,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-02 23:31:00','2020-03-23 14:59:23'),('3a74fac64c9c4f02ae6f0868f2647ad2','65ec9a72a8a448c091161d68c4f3b396','ZD','010','remote_monitor,enterprise_info_monitor,enterprise_main_panel,enterprise_map,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,企业主看板,GIS地图,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-19 16:55:22',NULL),('6cdc432a58994036995bda7f1ab04d7e','ea616225dea341058ad40a0efe4c9e73','ZDYW','5','remote_monitor,enterprise_info_monitor,enterprise_main_panel,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,企业主看板,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-13 09:57:13','2020-03-23 15:16:00'),('bb7341f937a543489763cfa2be5dfb6f','01c6d377439a4b4f86a50d73536ca2a8','SHYLD','021','remote_monitor,enterprise_info_monitor,enterprise_main_panel,enterprise_map,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend','远程监控,企业信息,企业主看板,GIS地图,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势','2020-03-23 15:32:45','2020-03-23 15:33:22'),('c327b542e7cc41169e725c12104e8b2c','5cd0ba60d43f4b6cbb1a34bb0884bad8','ZDCD','22','remote_monitor,enterprise_main_panel,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend,maintenance_record,maintenance_record_add,maintenance_record_edit,maintenance_record_del','远程监控,企业主看板,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势,维护保养','2020-01-20 17:02:04','2020-04-07 11:50:19'),('ec28c85fb03048bd9144cdd9d6b6415f','37790373a8f14f01a19297fe2d733681','管理员','1','remote_monitor,enterprise_info_monitor,enterprise_main_panel,enterprise_map,condensing_device_info,condensing_device_monitor,operation_trend,alarm_info,detailed_state,day_operation_trend,device,device_info,device_info_add,device_info_edit,device_info_del,data_resources,monitor_factor,monitor_factor_del,monitor_factor_edit,monitor_factor_add,monitor_factor_template,data_template_add,data_template_del,data_template_edit,enterprise_info,enterprise_info_add,enterprise_info_edit,enterprise_info_del,condensing_device,condensing_device_add,condensing_device_edit,condensing_device_del,sys,sys_user,sys_user_add,sys_user_view,sys_user_edit,sys_user_del,sys_user_import,sys_org,sys_org_add,sys_org_edit,sys_org_del,sys_login_log,sys_log_view,sys_dict,sys_dict_add,sys_dict_edit,sys_dict_del,sys_role,sys_role_add,sys_role_edit,sys_role_del,sys_authority,sys_authority_add,sys_authority_edit,sys_authority_del','远程监控,企业信息,企业主看板,GIS地图,机组信息,机组列表,运行趋势,报警状态,详细状态,按天趋势,终端管理,终端列表,基础信息管理,监测因子管理,监测因子模板管理,企业信息,机组管理,系统管理,用户管理,据点管理,系统登录管理,数据字典管理,角色管理,菜单配置','2020-03-09 09:36:33','2020-03-19 16:52:48');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-02  0:21:10
