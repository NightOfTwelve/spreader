alter table spreader.tb_client_task modify column id bigint not null;
alter table spreader.tb_client_task drop column hash;
alter table spreader.tb_client_task add column (
   base_priority        int comment '基本优先级',
   start_time           datetime comment '开始时间',
   priority             bigint comment '优先级'
);


/*==============================================================*/
/* Table: tb_task                                               */
/*==============================================================*/
create table spreader.tb_task
(
   id                   bigint not null auto_increment,
   task_code            varchar(100) comment '任务代码',
   bid                  bigint comment '业务id,负数表示直接引用id，正数需要从中间表查多个id',
   start_time           datetime comment '开始时间',
   handle_time          datetime comment '处理时间',
   status               int not null default 0 comment '状态，0-n：已分配，成功，失败，放弃，过期',
   uid                  bigint,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_task comment '任务表';

/*==============================================================*/
/* Index: task_code_bid                                         */
/*==============================================================*/
create index task_code_bid on spreader.tb_task
(
   task_code,
   bid
);




