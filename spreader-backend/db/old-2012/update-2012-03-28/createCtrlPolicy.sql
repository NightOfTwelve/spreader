drop index uidx_cgid_tcode_tunit on spreader.tb_ctrl_policy;

drop table if exists spreader.tb_ctrl_policy;

create table spreader.tb_ctrl_policy
(
   id                   bigint not null,
   ctrl_gid             bigint not null,
   task_code            varchar(60) not null,
   count                int not null,
   timeunit             int not null default 1,
   create_time          date not null,
   last_modified_time   date not null,
   unit_count           int not null default 1,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_ctrl_policy comment '控制策略表';

create unique index uidx_cgid_tcode_tunit on spreader.tb_ctrl_policy
(
   ctrl_gid,
   task_code,
   timeunit
);