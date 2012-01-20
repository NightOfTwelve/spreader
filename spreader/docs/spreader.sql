/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011-3-9 19:52:42                            */
/*==============================================================*/
create database spreader;

use spreader;
drop table if exists tb_user;

drop table if exists tb_admin;

drop table if exists tb_client;

drop table if exists tb_content;

drop table if exists tb_content_parameters;

drop table if exists tb_data_type;

drop table if exists tb_parameters;

drop table if exists tb_profile;

drop table if exists tb_target_param_values;

drop table if exists tb_target_template;

drop table if exists tb_target_template_parameters;

drop table if exists tb_task;

drop table if exists tb_template;

drop table if exists tb_robot_user;

drop table if exists tb_web_content;

drop table if exists tb_web_content_parameters;

drop table if exists tb_websites;

drop table if exists tb_real_user;

drop table if exists tb_world_relation;

drop table if exists tb_relation;

/*==============================================================*/
/* Table: tb_admin                                              */
/*==============================================================*/
create table tb_admin
(
   id                   int not null auto_increment,
   user_name            varchar(30) not null comment '��¼�û���',
   pwd                  varchar(20) not null comment '��¼����',
   role                 int not null comment '��ɫ',
   employee_id          int comment 'Ա����',
   real_name            varchar(30),
   create_time          datetime not null,
   last_login_time      datetime not null,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_client                                             */
/*==============================================================*/
create table tb_client
(
   id                   int not null auto_increment,
   user_name            varchar(30) not null comment '�û���¼��',
   pwd                  varchar(20) not null comment '��¼����',
   real_name            varchar(20) not null comment '�û�ʵ��',
   id_card              varchar(20) not null comment '���֤��',
   sex                  int,
   cellphone            varchar(15) not null comment '�ֻ��',
   tel                  varchar(15),
   address              varchar(100),
   post_code            int,
   qq                   int,
   msn                  varchar(40),
   latest_login_time    datetime not null,
   latest_login_ip      varchar(20),
   register_time        datetime not null,
   version              varchar(20),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_client comment '�����ƹ�ͻ���ʹ�õ��û��ʺ������';

/*==============================================================*/
/* Table: tb_content                                            */
/*==============================================================*/
create table tb_content
(
   template_id          bigint not null,
   content_id           bigint not null auto_increment,
   quality              tinyint not null,
   creater_id           bigint,
   creater_time         timestamp,
   last_modified_time   timestamp,
   use_count            int not null,
   primary key (content_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_content comment 'ͨ�����ݿ⡣';

/*==============================================================*/
/* Table: tb_content_parameters                                 */
/*==============================================================*/
create table tb_content_parameters
(
   content_id           bigint,
   id                   bigint not null auto_increment,
   param_id             bigint,
   value                varchar(2000),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_content_parameters comment 'ͨ�����ݵĲ����б�';

/*==============================================================*/
/* Table: tb_data_type                                          */
/*==============================================================*/
create table tb_data_type
(
   id                   int not null,
   name                 varchar(30) not null,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_data_type comment '����������ͱ?����У��';

/*==============================================================*/
/* Table: tb_parameters                                         */
/*==============================================================*/
create table tb_parameters
(
   tempate_id           int not null,
   param_id             bigint not null auto_increment,
   name                 varchar(50),
   primary key (param_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_profile                                            */
/*==============================================================*/
create table tb_profile
(
   id                   int not null auto_increment,
   user_name            varchar(30) not null comment '��¼�û���',
   pwd                  varchar(20) not null comment '��¼����',
   email                varchar(30) not null comment 'ע��ʱ�õ�email',
   nationality          varchar(100),
   province             varchar(15),
   city                 varchar(30),
   district             varchar(50),
   sex                  int,
   introduction         varchar(500),
   nick_name            varchar(30),
   qq                   varchar(30),
   msn                  varchar(40),
   real_name            varchar(30),
   id_card              varchar(30),
   birthday_year        int,
   birthday_month       int,
   birthday_day         int,
   company              varchar(50),
   university           varchar(50),
   junior_middle_school varchar(50),
   senior_middle_school varchar(50),
   interest             varchar(50),
   skill                varchar(50),
   profession           varchar(20),
   create_time          datetime not null comment '�û�����ʱ��',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_profile comment '�����˵���Ϣ������ְҵ���Ա���Ȥ����Ϣ';

/*==============================================================*/
/* Table: tb_target_param_values                                */
/*==============================================================*/
create table tb_target_param_values
(
   param_id             bigint not null auto_increment,
   value                varchar(2000),
   primary key (param_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_target_param_values comment 'Ŀ����վ�̶������ֵ';

/*==============================================================*/
/* Table: tb_target_template                                    */
/*==============================================================*/
create table tb_target_template
(
   id                   bigint not null  auto_increment comment '����',
   name                 varchar(50) not null comment 'ģ�����',
   description          varchar(200) comment 'ģ������',
   url                  varchar(200) not null,
   created_time         timestamp not null,
   website_id           int not null,
   has_problem          tinyint(1) not null comment '�Ƿ�������',
   template_type        int comment 'ģ�����ͣ����ӣ�΢������Ƶ����Ƭ�ȵ�',
   last_modified_time   timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_target_template comment '������վ�?ģ�壬����Ҫ����ģ�嶼��ͨ��ģ��ӳ���ϣ������������û��ӳ���ϵġ�';

/*==============================================================*/
/* Table: tb_target_template_parameters                         */
/*==============================================================*/
create table tb_target_template_parameters
(
   template_id          bigint,
   id                   bigint not null auto_increment,
   param_id             bigint,
   name                 varchar(200) not null,
   mandatory            tinyint(1) not null default 1,
   has_values           tinyint(1) not null default 0,
   data_type            int,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_target_template_parameters comment 'Ŀ����վ�?ģ��Ĳ����б�';

/*==============================================================*/
/* Table: tb_task                                               */
/*==============================================================*/
create table tb_task
(
   content_id           bigint,
   user_id              bigint not null,
   status               int not null,
   client_id            int not null,
   error_reason         longtext,
   created_time         timestamp not null,
   id                   bigint not null auto_increment,
   check_url            varchar(200),
   target_url           varchar(200) not null,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_task comment '��վ�����б�';

/*==============================================================*/
/* Table: tb_template                                           */
/*==============================================================*/
create table tb_template
(
   template_id          int not null auto_increment,
   name                 varchar(50) not null,
   description          varchar(200) not null,
   ceated_time          timestamp not null,
   last_modified_time   timestamp,
   primary key (template_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_template comment 'ͨ��ģ�壬������';

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_robot_user
(
   uid                  bigint not null auto_increment,
   website_id           int not null comment '��վID',
   profile_id           bigint not null,
   admin_id             int,
   level                int not null,
   register_time        datetime not null comment 'ע��ʱ��',
   home_url             varchar(300) not null,
   score                int,
   fans_count           int,
   followed_count       int,
   primary key (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_robot_user comment '�˻��?�û���¼������վ';

/*==============================================================*/
/* Table: tb_web_content                                        */
/*==============================================================*/
create table tb_web_content
(
   template_id          bigint,
   id                   bigint not null auto_increment,
   content_id           bigint,
   creater_id           bigint,
   quality              tinyint not null,
   website_id           int not null,
   ceated_time          timestamp not null,
   url                  varchar(200) not null,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_web_content comment 'ĳ��վ�ض������ݿ�';

/*==============================================================*/
/* Table: tb_web_content_parameters                             */
/*==============================================================*/
create table tb_web_content_parameters
(
   content_id           bigint,
   id                   bigint not null auto_increment,
   target_param_id      bigint not null,
   name                 varchar(50) not null,
   value                varchar(2000),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_web_content_parameters comment 'ĳ��վ�ض����ݵĲ����б�';

/*==============================================================*/
/* Table: tb_websites                                           */
/*==============================================================*/
create table tb_websites
(
   id                   int not null auto_increment,
   url                  varchar(200) not null,
   name                 varchar(50) not null,
   charger_id           bigint,
   type                 tinyint not null,
   description          varchar(1000),
   logo                 varchar(200),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tb_websites comment '�����ƹ��Ŀ����վ�б�';




/*==============================================================*/
/* Table: tb_real_user                                          */
/*==============================================================*/
create table tb_real_user
(
   uid                  bigint not null auto_increment,
   website_uid          bigint,
   website_id           int,
   user_name            varchar(30),
   email                varchar(30),
   nationality          varchar(30),
   province             varchar(20),
   city                 varchar(30),
   district             varchar(30),
   sex                  tinyint,
   qq                   varchar(30),
   msn                  varchar(50),
   nick_name            varchar(50),
   real_name            varchar(30),
   id_card              varchar(30),
   birthday_year        int,
   birthday_month       int,
   company_name         varchar(50),
   company_address      varchar(100),
   home_address         varchar(100),
   university           varchar(100),
   junior_middle_school varchar(100),
   senior_middle_school varchar(100),
   interest             varchar(100),
   skill                varchar(100),
   create_time          timestamp,
   profession           varchar(50),
   tags                 varchar(200),
   home_site            varchar(300),
   intro                varchar(300),
   primary key (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_world_relation                                     */
/*==============================================================*/
create table tb_world_relation
(
   from_uid             bigint,
   from_uid_type        tinyint,
   to_uid               bigint,
   to_uid_type          tinyint,
   relation_type        int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: tb_relation                                           */
/*==============================================================*/
create table tb_relation
(
   relation_id          int,
   relation_name        varchar(30),
   relation_level       int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
