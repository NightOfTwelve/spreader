alter table spreader.tb_client_task_log add error_desc varchar(200) comment '错误描述';
alter table spreader.tb_user drop index uk_tb_user_website_user;
alter table spreader.tb_user add unique index uk_tb_user_website_user (website_id, website_uid);