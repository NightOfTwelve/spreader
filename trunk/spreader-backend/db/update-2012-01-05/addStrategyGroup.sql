create table spreader.tb_strategy_group
(
   id                   bigint not null auto_increment,
   group_name           varchar(100),
   group_type           int not null,
   note                 varchar(200),
   create_time          timestamp,
   primary key (id)
);

alter table spreader.tb_strategy_group comment '策略分组表';