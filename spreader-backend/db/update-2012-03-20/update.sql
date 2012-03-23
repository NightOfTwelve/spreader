alter table tb_task add column result_id bigint;
alter table tb_task add column trace_link varchar(2000);
create index idx_result_status on tb_task
(
   result_id,
   status
);
create table tb_regular_job_result
(
   id                   bigint not null auto_increment,
   job_id               bigint,
   start_time           datetime,
   end_time             datetime,
   result               longtext,
   status               int comment '0：执行中，1：完成，2：异常中断',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_regular_job_result comment '定时任务结果表';

/*==============================================================*/
/* Index: idx_job_id                                            */
/*==============================================================*/
create index idx_job_id on tb_regular_job_result
(
   job_id
);

create index idx_exptime_hc on tb_captcha(expire_time,handle_client);
create index idx_exec_time on tb_client_task_log(executed_time);
/*
insert into tb_user select * from tb_user_delete;
update tb_robot_user set account_state=1 where account_state=2;
truncate table tb_user_delete;
 */
