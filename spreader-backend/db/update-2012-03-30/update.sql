alter table tb_client_error
modify column error_code           varchar(200) comment '出错信息',
modify column error_desc           text comment '错误描述'
;
alter table tb_client_task_log
modify column error_code           varchar(200) comment '出错信息',
modify column error_desc           text comment '错误描述'
;