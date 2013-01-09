create table spreader.tb_real_man
(
   id                   bigint not null auto_increment,
   real_id              varchar(20) not null comment '身份证号',
   real_name            varchar(20) not null comment '姓名',
   is_real              boolean comment '是否真实身份证信息',
   is_forbid_by_sina    boolean comment '是否被新浪禁用',
   sina_use_count       int not null default 0 comment '新浪已使用的次数',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_real_man comment '真实身份证库';

create index idx_real_forbid_count on spreader.tb_real_man
(
   is_real,is_forbid_by_sina,sina_use_count
);

alter table spreader.tb_real_man drop index idx_real_id_name;

alter ignore table spreader.tb_real_man add unique index uk_real_id_name (real_id,real_name);

alter table spreader.tb_robot_register add real_man_id BIGINT comment '引用的真实身份证库ID';
