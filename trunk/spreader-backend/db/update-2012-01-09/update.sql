alter table spreader.tb_robot_register add column (
   county               varchar(50) comment '县',
   person_id            varchar(18) comment '身份证',
   student_id           varchar(18) comment '学生证'
);

create table spreader.tb_client_task_log
(
   task_id              bigint not null auto_increment,
   client_id            bigint,
   error_code           text comment '出错信息，成功则为空',
   task_code            varchar(100),
   status               int not null comment '0：成功，1：失败，2：放弃，3：过期',
   executed_time        datetime,
   primary key (task_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_client_task_log comment '任务返回';