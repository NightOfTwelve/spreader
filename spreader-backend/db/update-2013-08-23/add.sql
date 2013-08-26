CREATE TABLE `tb_yyb_packet` (
  `id` bigint(20) NOT NULL auto_increment,
  `machine_id` varchar(100) collate utf8_bin default NULL COMMENT '机器ID',
  `phone` varchar(100) collate utf8_bin default NULL COMMENT '手机型号',
  `guid` int(11) default '0' COMMENT 'guid',
  `mac` varchar(100) collate utf8_bin default NULL COMMENT '网卡地址',
  `imsi` varchar(100) collate utf8_bin default NULL COMMENT 'IMSI',
  `android_version` varchar(50) collate utf8_bin default NULL COMMENT '安卓版本',
  `product_id` int(11) default '0' COMMENT '产品编号',
  `file_id` int(11) default '0' COMMENT '文件编号',
  `apk_url` varchar(300) collate utf8_bin default NULL COMMENT 'apk下载地址',
  `client_ip` varchar(100) collate utf8_bin default NULL COMMENT '客户端IP',
  `apk_pack` varchar(100) collate utf8_bin default NULL COMMENT 'apk',
  `post_time` datetime default NULL COMMENT '发送时间',
  `post_date` varchar(100) collate utf8_bin default NULL COMMENT '发送日期',
  `client_id` bigint(20) default NULL COMMENT '客户端ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应用宝发送数据表';

ALTER TABLE tb_yyb_packet ADD INDEX idx_yyb_packet (product_id,post_date,client_id,post_time);

CREATE TABLE `tb_phone_info` (
  `id` bigint(20) NOT NULL auto_increment,
  `phone_name` varchar(150) collate utf8_bin NOT NULL,
  `resolution_x` int(11) NOT NULL,
  `resolution_y` int(11) NOT NULL,
  `dpi` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uk_tb_phone_info_name` (`phone_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='手机参数表';

ALTER TABLE tb_user ADD INDEX idx_user_robot_website (is_robot,website_id);

alter table `tb_client_config` 
Add column create_time timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP;

alter table `tb_client_config` 
Add column update_time timestamp ;