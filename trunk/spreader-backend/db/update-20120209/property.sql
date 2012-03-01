drop table if exists spreader.tb_properties;

/*==============================================================*/
/* Table: tb_properties                                         */
/*==============================================================*/
create table spreader.tb_properties
(
   id                   bigint not null auto_increment,
   mod_name             varchar(30) comment '模块名',
   property_name        varchar(30) comment '属性名',
   property_query_type  int comment '属性类型',
   property_query_value varchar(30) comment '查询值',
   property_value       varchar(200) comment '属性值',
   property_value_type  int comment '属性值的类型',
   primary key (id)
);
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_properties comment '自定义属性表';
/*==============================================================*/
/* Index: uidx_mname_pname                                      */
/*==============================================================*/
create unique index uidx_mname_pname on spreader.tb_properties
(
   mod_name,
   property_name
);
