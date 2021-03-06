CREATE TABLE `tb_client_config` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_id` bigint(20) NOT NULL COMMENT '客户端ID',
  `config_name` varchar(200) collate utf8_bin NOT NULL COMMENT '配置名称',
  `config_type` int(11) NOT NULL COMMENT '配置类型',
  `client_config` varchar(1500) collate utf8_bin default NULL COMMENT '客户端配置信息',
  `config_md5` varchar(100) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_client_config_id_name` (`client_id`,`config_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户端配置表';

CREATE TABLE `tb_account_log` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `op_name` varchar(100) collate utf8_bin default NULL COMMENT '操作名称，一般为注解自定义的值',
  `url` varchar(100) collate utf8_bin NOT NULL COMMENT '请求的路径',
  `params` varchar(1500) collate utf8_bin default NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP COMMENT '创建时间',
  `account_id` varchar(50) collate utf8_bin NOT NULL COMMENT '操作人员的用户名',
  PRIMARY KEY  (`id`),
  KEY `IDX_LOG_OP_NAME_USER` (`op_name`,`url`,`create_time`,`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table spreader.tb_account_logon modify column password varchar(150)  comment '密码';

alter table tb_client_config Add column type int not null
CREATE UNIQUE INDEX uk_client_config_id_name_type ON tb_client_config (client_id,config_name,type);