
CREATE TABLE spreader.`tb_identity` (
  `app_id` int(11) NOT NULL auto_increment,
  `app_name` varchar(50) default NULL,
  `id` int(11) NOT NULL,
  `last_modify_time` datetime default NULL,
  PRIMARY KEY  (`app_id`),
  UNIQUE KEY `uidx_appName` (`app_name`),
  KEY `idx_appId_appName` USING BTREE (`app_id`,`app_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop index uidx_mn_pn_spn on spreader.tb_properties;

drop table if exists spreader.tb_properties;

/*==============================================================*/
/* Table: tb_properties                                         */
/*==============================================================*/
create table spreader.tb_properties
(
   id                   bigint not null auto_increment,
   mod_name             varchar(60) not null,
   property_name        varchar(60) not null,
   sub_property_name    varchar(60),
   property_value       varchar(2000) not null,
   property_value_type  varchar(500) not null,
   property_type        int not null,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_properties comment '属性表。加载各种模式的配置信息。';

/*==============================================================*/
/* Index: uidx_mn_pn_spn                                        */
/*==============================================================*/
create unique index uidx_mn_pn_spn on spreader.tb_properties
(
   mod_name,
   property_name,
   sub_property_name
);
