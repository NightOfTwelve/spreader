/*==============================================================*/
/* Table: tb_account_logon                                      */
/*==============================================================*/
create table tb_account_logon
(
   id                   bigint not null auto_increment,
   account_id           varchar(50),
   password             varchar(50),
   create_time          timestamp,
   remove_tag     bit(0) not null default 0,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_account_logon comment '用户账户信息表';

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create unique index idx_account_id on tb_account_logon
(
   account_id
);

INSERT INTO tb_account_logon (account_id,password) VALUES ('test','123456');