/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011-3-13 15:15:15                           */
/*==============================================================*/


drop table if exists tb_composite_task;

drop table if exists tb_content_cookies;

drop table if exists tb_content_headers;

drop table if exists tb_flow_template_relation;

drop table if exists tb_target_cookies;

drop table if exists tb_target_headers;

drop table if exists tb_target_template;

drop table if exists tb_target_web_flow;

drop table if exists tb_task;

drop table if exists tb_template;

drop table if exists tb_web_content;

drop table if exists tb_web_cookies;

drop table if exists tb_web_headers;

/*==============================================================*/
/* Table: tb_composite_task                                     */
/*==============================================================*/
create table tb_composite_task
(
   id                   bigint not null,
   status               int not null,
   client_id            int not null,
   error_reason         varchar(2000),
   user_id              bigint not null,
   created_time         timestamp not null,
   check_url            varchar(200),
   website_id           int not null,
   use_cookie           tinyint,
   flow_id              bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: tb_content_cookies                                    */
/*==============================================================*/
create table tb_content_cookies
(
   content_id           bigint not null,
   cookie_id            bigint not null,
   primary key (content_id, cookie_id)
);

/*==============================================================*/
/* Table: tb_content_headers                                    */
/*==============================================================*/
create table tb_content_headers
(
   content_id           bigint not null,
   header_id            bigint not null,
   primary key (content_id, header_id)
);

/*==============================================================*/
/* Table: tb_flow_template_relation                             */
/*==============================================================*/
create table tb_flow_template_relation
(
   flow_id              bigint not null,
   template_id          int not null
);

/*==============================================================*/
/* Table: tb_target_cookies                                     */
/*==============================================================*/
create table tb_target_cookies
(
   id                   bigint not null,
   domain               varchar(30) not null,
   name                 varchar(30) not null,
   value                varchar(200) not null,
   path                 varchar(100),
   flow_id              bigint,
   template_id          bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: tb_target_headers                                     */
/*==============================================================*/
create table tb_target_headers
(
   id                   bigint not null,
   template_Id          int,
   name                 varchar(30),
   value                varchar(200),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_target_template                                    */
/*==============================================================*/
create table tb_target_template
(
   id                   bigint not null comment '主键',
   name                 varchar(50) not null comment '模板名称',
   description          varchar(200) comment '模板描述',
   url                  varchar(200) not null,
   created_time         timestamp not null,
   website_id           int not null,
   has_problem          tinyint(1) not null comment '是否有问题',
   template_type        int comment '模板类型，帖子，微薄，视频，照片等等',
   last_modified_time   timestamp,
   use_cookie           int,
   redirect_type        int,
   content_encoding     varchar(10),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_target_template comment '各大网站表单模板，不需要所有模板都和通用模板映射上，可以有特殊的没有映射上的。';

/*==============================================================*/
/* Table: tb_target_web_flow                                    */
/*==============================================================*/
create table tb_target_web_flow
(
   flow_id              bigint not null,
   use_cookie           char(10) not null,
   primary key (flow_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_task                                               */
/*==============================================================*/
create table tb_task
(
   status               int not null,
   error_reason         longtext,
   created_time         timestamp not null,
   id                   bigint not null,
   target_url           varchar(200) not null,
   composite_task_id    bigint,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_task comment '网站任务列表';

/*==============================================================*/
/* Table: tb_template                                           */
/*==============================================================*/
create table tb_template
(
   template_id          int not null,
   name                 varchar(50) not null,
   description          varchar(200) not null,
   ceated_time          timestamp not null,
   last_modified_time   timestamp,
   content_type         varchar(20),
   method_type          int,
   primary key (template_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_template comment '通用模板，抽象定义';

/*==============================================================*/
/* Table: tb_web_content                                        */
/*==============================================================*/
create table tb_web_content
(
   template_id          bigint,
   id                   bigint not null,
   content_id           bigint,
   creater_id           bigint,
   quality              tinyint not null,
   website_id           int not null,
   ceated_time          timestamp not null,
   url                  varchar(200) not null,
   primary key (id)
);

alter table tb_web_content comment '某网站特定的内容库';

/*==============================================================*/
/* Table: tb_web_cookies                                        */
/*==============================================================*/
create table tb_web_cookies
(
   name                 varchar(50),
   value                varchar(500),
   domain               varchar(50),
   path                 varchar(100),
   content_id           bigint,
   id                   bigint not null,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_web_headers                                        */
/*==============================================================*/
create table tb_web_headers
(
   id                   bigint not null,
   name                 varchar(50),
   value                varchar(200),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
