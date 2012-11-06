update spreader.tb_user_group
set prop_exp = null
where gtype = 2

update spreader.tb_user_group
set prop_val = null


create table spreader.tb_user_group_exclude
(
   id             bigint not null auto_increment,
   gid            bigint not null comment '分组ID',
   exclude_gid    bigint not null comment '排除的分组ID',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_user_group_exclude comment '用户分组中需要排除的分组';

create index idx_gid_exclude on spreader.tb_user_group_exclude
(
   gid,exclude_gid
);