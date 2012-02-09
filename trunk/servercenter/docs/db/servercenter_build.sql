
CREATE TABLE spreader.`tb_identity` (
  `app_id` int(11) NOT NULL auto_increment,
  `app_name` varchar(50) default NULL,
  `id` int(11) NOT NULL,
  `last_modify_time` datetime default NULL,
  PRIMARY KEY  (`app_id`),
  UNIQUE KEY `uidx_appName` (`app_name`),
  KEY `idx_appId_appName` USING BTREE (`app_id`,`app_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;