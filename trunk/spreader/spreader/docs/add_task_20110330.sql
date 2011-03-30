/*==============================================================*/
/* Table: tb_task                                               */
/*==============================================================*/
drop table if exists spreader.tb_task;
create table spreader.tb_task
(
   id                   bigint not null,
   status               int not null,
   client_id            int not null,
   error_reason         varchar(2000),
   user_id              bigint not null,
   created_time         timestamp not null,
   check_url            varchar(200),
   website_id           int not null,
   use_cookie           tinyint,
   flow_id              bigint,
   content_id           bigint,
   has_sub_task         bit,
   content              varchar(1000),
   task_type            int,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_task_assign                                        */
/*==============================================================*/
drop table if exists spreader.tb_task_assign;
create table spreader.tb_task_assign
(
   id                   bigint not null,
   from_id              bigint,
   to_id                bigint,
   admin_id             bigint,
   from_date            timestamp,
   to_date              timestamp,
   task_type            int,
   status               int,
   website_id           int,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;