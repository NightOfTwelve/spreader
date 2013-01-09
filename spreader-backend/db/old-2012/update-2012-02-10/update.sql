alter table tb_client_task add column (
   status               int
);
alter table tb_client_task modify column batch_id bigint null default null;
update tb_client_task set expire_time=curdate();

create table tb_user_delete like tb_user;


alter table tb_client_task drop index idx_user_action;
alter table tb_client_task add index idx_expire (status,expire_time);
alter table tb_client_task add index idx_active (expire_time,start_time);
alter table tb_client_task add index idx_task_summary (task_type,status,priority,uid,action_id);
alter table tb_client_task add index idx_uid_action_task (status,task_type,uid,action_id,priority);

create table tb_client_error
(
   task_id              bigint not null auto_increment,
   client_id            bigint,
   task_code            varchar(100),
   error_code           text comment '出错信息',
   error_desc           varchar(200) comment '错误描述',
   error_time           datetime,
   website_id           int,
   uid                  bigint,
   website_error_code   varchar(200),
   website_error_desc   varchar(4000),
   referer_id           bigint,
   primary key (task_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_client_error comment '任务错误';