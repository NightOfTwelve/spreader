create table spreader.tb_weibo_appeal
(
   uid                  bigint not null,
   start_time           datetime comment '开始日期',
   status               int not null default 0 comment '0:生成，1：确认开始,2:失败',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_weibo_appeal comment '微博申诉表';

create index idx_status_start_time on spreader.tb_weibo_appeal
(
   status, start_time
);