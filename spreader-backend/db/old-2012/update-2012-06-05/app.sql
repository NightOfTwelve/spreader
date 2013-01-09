use spreader;

/*==============================================================*/
/* Table: tb_reg_address                                        */
/*==============================================================*/
create table tb_reg_address
(
   register_id          bigint not null,
   nationality          varchar(100) comment '国籍',
   province             varchar(30),
   city                 varchar(50),
   street               varchar(100),
   suite                varchar(100),
   post_code            varchar(20),
   phone_area_code      varchar(10),
   phone_code           varchar(10),
   primary key (register_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_reg_address comment '注册地址';

/*==============================================================*/
/* Table: tb_app_udid                                           */
/*==============================================================*/
create table tb_app_udid
(
   register_id          bigint not null,
   pwd                  varchar(20),
   udid                 varchar(40),
   primary key (register_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_app_udid comment 'apple udid';

/*==============================================================*/
/* Index: uk_udid                                               */
/*==============================================================*/
create unique index uk_udid on tb_app_udid
(
   udid
);

create index idx_update_time_id on tb_robot_register
(
   update_time,
   id
);
