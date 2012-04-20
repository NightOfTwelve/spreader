drop table if exists spreader.tb_ctrl_group_conflict;

/*==============================================================*/
/* Table: tb_ctrl_group_conflict                                */
/*==============================================================*/
create table spreader.tb_ctrl_group_conflict
(
   id                   bigint not null auto_increment,
   uid                  bigint not null comment '冲突的用户',
   old_gid              bigint not null comment '老分组',
   new_gid              bigint not null comment '当前分组',
   create_time          Date not null comment '创建时间',
   last_modified_time   Date not null comment '最后修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_ctrl_group_conflict comment '用户分组表更新后冲突';

/*==============================================================*/
/* Index: idx_status_uid                                        */
/*==============================================================*/
create index idx_uid on spreader.tb_ctrl_group_conflict
(
   uid
);

/*==============================================================*/
/* Index: idx_status_ngid                                       */
/*==============================================================*/
create index idx_ngid on spreader.tb_ctrl_group_conflict
(
   new_gid
);

/*==============================================================*/
/* Index: idx_modtime                                           */
/*==============================================================*/
create index idx_modtime on spreader.tb_ctrl_group_conflict
(
   last_modified_time
);
