drop table if exists spreader.tb_user_group;
/*==============================================================*/
/* Table: tb_user_group                                         */
/*==============================================================*/
create table spreader.tb_user_group
(
   gid                  bigint not null auto_increment comment '分组id',
   gname                varchar(60) not null comment '分组的名字',
   description          varchar(300) not null comment '分组描述',
   gtype                int not null comment '分组类型',
   prop_exp             text comment '分组属性表达式',
   create_time          datetime not null comment '创建时间',
   last_modified_time   datetime not null comment '最后修改时间',
   website_id           int not null comment '分组所在网站',
   prop_val             int comment '采用了哪些属性作为分组',
   primary key (gid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_user_group comment '用户分组表';

/*==============================================================*/
/* Index: uidx_gname                                            */
/*==============================================================*/
create unique index uidx_gname on spreader.tb_user_group
(
   gname
);

/*==============================================================*/
/* Index: idx_ctime_gtype                                       */
/*==============================================================*/
create index idx_ctime_gtype on spreader.tb_user_group
(
   create_time,
   gtype
);

alter table spreader.tb_user add column (score decimal(2,2) not null default 0, ctrl_gid bigint not null default -1);

insert into spreader.tb_identity (app_name,id,last_modify_time) VALUES ('spreader.user.group',0,{ts '2012-01-16 09:53:47.000'});