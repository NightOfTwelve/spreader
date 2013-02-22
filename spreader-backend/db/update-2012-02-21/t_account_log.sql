CREATE TABLE `tb_account_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `op_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '操作名称，一般为注解自定义的值',
  `url` varchar(100) NOT NULL COLLATE utf8_bin COMMENT '请求的路径',
  `params` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '方法参数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `account_id` varchar(50) NOT NULL COMMENT '操作人员的用户名',
  PRIMARY KEY (`id`),
  KEY `IDX_LOG_OP_NAME_USER` (`op_name`,`url`,`create_time`,`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin