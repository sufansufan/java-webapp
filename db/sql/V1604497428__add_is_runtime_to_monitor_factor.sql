ALTER TABLE monitor_factor_template ADD `is_runtime` tinyint(1) DEFAULT '0', COMMENT '是否是设备运转时间';
ALTER TABLE monitor_factor ADD `is_runtime` tinyint(1) DEFAULT '0', COMMENT '是否是设备运转时间';
