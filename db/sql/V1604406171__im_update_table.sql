ALTER TABLE inspection_maintain_item ADD `is_available` smallint(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)';

ALTER TABLE inspection_maintain_item drop column status;

DROP TABLE IF EXISTS inspection_maintain_result;

ALTER TABLE inspection_maintain_item_result  drop column exception_type;

DROP TABLE IF EXISTS inspection_maintain_fix;

DROP TABLE IF EXISTS exception_fix;
CREATE TABLE `exception_fix` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养异常处理id',
  `exception_id` varchar(32) NOT NULL DEFAULT '' COMMENT '异常id',
  `describe` varchar(400) NOT NULL DEFAULT '' COMMENT '异常处理描述',
  `fix_img_urls` varchar(400) DEFAULT '' COMMENT '维修图片链接',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `exception`;
CREATE TABLE `exception` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养异常处理id',
  `create_time` datetime NOT NULL,
  `source_id` varchar(32) NOT NULL DEFAULT '' COMMENT '异常来源id(如果是突发异常，则为空)',
  `describe` varchar(400) NOT NULL DEFAULT '' COMMENT '描述',
  `exception_type` varchar(32) NOT NULL DEFAULT '' COMMENT 'IM(点检保养)/BO(break out突发异常)',
  `exception_img_urls` varchar(400) DEFAULT '' COMMENT '异常图片',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '点检保养异常处理id',
  `start_time` datetime NOT NULL,
  `status` varchar(32) NOT NULL DEFAULT '' COMMENT '状态（1. 待处理，2. 被驳回， 3.待确认，4.待承认， 5.已完成）',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT 'IM(点检保养)/BO(break out突发异常)',
  `last_modified_time` datetime DEFAULT NULL COMMENT '异常最后修改时间',
  `source_id` varchar(32) NOT NULL DEFAULT '' COMMENT '来源id（exception_id或schedule_id）',
  `is_available` smallint(1) NOT NULL DEFAULT '1' COMMENT '1(可用)/0(删除)',
  `operator_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '处理人id',
  `operate_date` datetime DEFAULT NULL COMMENT '处理时间',
  `confirm_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '确认人id',
  `confirm_date` datetime DEFAULT NULL COMMENT '确认时间',
  `admit_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '承认人id',
  `admit_date` datetime DEFAULT NULL COMMENT '承认时间',
  `reject_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '驳回人id',
  `reject_date` datetime DEFAULT NULL COMMENT '驳回时间',
  `before_task_id` varchar(32) DEFAULT '' COMMENT '被驳回之前taskid',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;