drop table if exists "tb_admin-��̨����Ա";

drop table if exists tb_client;

drop table if exists tb_content;

drop table if exists tb_content_cookies;

drop table if exists tb_content_headers;

drop table if exists tb_content_parameters;

drop table if exists tb_data_type;

drop table if exists tb_flow_template_relation;

drop table if exists tb_parameters;

drop table if exists tb_profile;

drop table if exists "tb_real_user-�罻��վ����ʵ�û�";

drop table if exists tb_relation;

drop table if exists tb_robot_user;

drop table if exists tb_sub_task;

drop table if exists tb_target_cookies;

drop table if exists tb_target_headers;

drop table if exists tb_target_param_values;

drop table if exists tb_target_template;

drop table if exists tb_target_template_parameters;

drop table if exists tb_target_web_flow;

drop table if exists tb_task;

drop table if exists tb_template;

drop table if exists tb_web_content;

drop table if exists tb_web_content_parameters;

drop table if exists tb_web_cookies;

drop table if exists tb_web_headers;

drop table if exists tb_websites;

drop table if exists tb_world_relation;

/*==============================================================*/
/* Table: "tb_admin-��̨����Ա"                                      */
/*==============================================================*/
create table "tb_admin-��̨����Ա"
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
);

/*==============================================================*/
/* Table: tb_client                                             */
/*==============================================================*/
create table tb_client
(
   id                   int not null,
   user_name            varchar(30) not null comment '�û���¼��',
   pwd                  varchar(20) not null comment '��¼����',
   real_name            varchar(20) not null comment '�û�ʵ��',
   id_card              varchar() not null comment '���֤��',
   sex                  int,
   cellphone            varchar(15) not null comment '�ֻ���',
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
);

alter table tb_client comment '�����ƹ�ͻ���ʹ�õ��û��ʺ������';

/*==============================================================*/
/* Table: tb_content                                            */
/*==============================================================*/
create table tb_content
(
   template_id          bigint,
   content_id           bigint not null,
   quality              tinyint not null,
   creater_id           bigint,
   creater_time         timestamp,
   last_modified_time   timestamp,
   use_count            int not null,
   primary key (content_id)
);

alter table tb_content comment 'ͨ�����ݿ⡣';

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
   id                   bigint,
   primary key (content_id, header_id)
);

/*==============================================================*/
/* Table: tb_content_parameters                                 */
/*==============================================================*/
create table tb_content_parameters
(
   content_id           bigint,
   id                   bigint,
   param_id             bigint,
   value                varchar(2000)
);

alter table tb_content_parameters comment 'ͨ�����ݵĲ����б�';

/*==============================================================*/
/* Table: tb_data_type                                          */
/*==============================================================*/
create table tb_data_type
(
   id                   int not null,
   name                 varchar(30) not null,
   primary key (id)
);

alter table tb_data_type comment '�����������ͱ�����У��';

/*==============================================================*/
/* Table: tb_flow_template_relation                             */
/*==============================================================*/
create table tb_flow_template_relation
(
   flow_id              bigint not null,
   template_id          bigint not null,
   "order"              int
);

/*==============================================================*/
/* Table: tb_parameters                                         */
/*==============================================================*/
create table tb_parameters
(
   tempate_id           int not null,
   param_id             bigint not null,
   name                 varchar(50),
   primary key (param_id)
);

/*==============================================================*/
/* Table: tb_profile                                            */
/*==============================================================*/
create table tb_profile
(
   id                   int not null,
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
   msn                  varcgar(40),
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
);

alter table tb_profile comment '�����˵���Ϣ������ְҵ���Ա���Ȥ����Ϣ';

/*==============================================================*/
/* Table: "tb_real_user-�罻��վ����ʵ�û�"                              */
/*==============================================================*/
create table "tb_real_user-�罻��վ����ʵ�û�"
(
   uid                  bigint not null,
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
);

/*==============================================================*/
/* Table: tb_relation                                           */
/*==============================================================*/
create table tb_relation
(
   relation_id          int,
   relation_name        varchar(30),
   relation_level       int
);

/*==============================================================*/
/* Table: tb_robot_user                                         */
/*==============================================================*/
create table tb_robot_user
(
   id                   bigint,
   website_id           int not null comment '��վID',
   profile_id           bigint not null,
   admin_id             int,
   level                int not null,
   register_time        datetime not null comment 'ע��ʱ��',
   home_url             varchar(300) not null,
   score                int,
   fans_count           int,
   followed_count       int
);

alter table tb_robot_user comment '�˻����û���¼������վ';

/*==============================================================*/
/* Table: tb_sub_task                                           */
/*==============================================================*/
create table tb_sub_task
(
   status               int not null,
   error_reason         longtext,
   created_time         timestamp not null,
   id                   bigint not null,
   target_id            bigint not null,
   content_id           bigint,
   primary key (id)
);

alter table tb_sub_task comment '��վ�����б�';

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
   tb__template_id      int,
   template_Id          int,
   name                 varchar(30),
   value                varchar(200),
   primary key (id)
);

/*==============================================================*/
/* Table: tb_target_param_values                                */
/*==============================================================*/
create table tb_target_param_values
(
   param_id             bigint,
   value                varchar(2000)
);

alter table tb_target_param_values comment 'Ŀ����վ�̶�������ֵ';

/*==============================================================*/
/* Table: tb_target_template                                    */
/*==============================================================*/
create table tb_target_template
(
   id                   bigint not null comment '����',
   name                 varchar(50) not null comment 'ģ������',
   description          varchar(200) comment 'ģ������',
   url                  varchar(200) not null,
   created_time         timestamp not null,
   website_id           int not null,
   has_problem          tinyint(1) not null comment '�Ƿ�������',
   template_type        int comment 'ģ�����ͣ����ӣ�΢������Ƶ����Ƭ�ȵ�',
   last_modified_time   timestamp,
   use_cookie           int,
   redirect_type        int,
   content_encoding     varchar(10),
   primary key (id)
);

alter table tb_target_template comment '������վ��ģ�壬����Ҫ����ģ�嶼��ͨ��ģ��ӳ���ϣ������������û��ӳ���ϵġ�';

/*==============================================================*/
/* Table: tb_target_template_parameters                         */
/*==============================================================*/
create table tb_target_template_parameters
(
   template_id          bigint,
   id                   bigint not null,
   param_id             bigint,
   name                 varchar(200) not null,
   mandatory            tinyint(1) not null default 1,
   has_values           tinyint(1) not null default 0,
   data_type            int,
   primary key (id)
);

alter table tb_target_template_parameters comment 'Ŀ����վ��ģ��Ĳ����б�';

/*==============================================================*/
/* Table: tb_target_web_flow                                    */
/*==============================================================*/
create table tb_target_web_flow
(
   flow_id              bigint not null,
   use_cookie           bit not null,
   name                 varchar(100),
   primary key (flow_id)
);

/*==============================================================*/
/* Table: tb_task                                               */
/*==============================================================*/
create table tb_task
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
   content_id           bigint,
   has_sub_task         bit,
   primary key (id)
);

/*==============================================================*/
/* Table: tb_template                                           */
/*==============================================================*/
create table tb_template
(
   template_id          bigint not null,
   name                 varchar(50) not null,
   description          varchar(200) not null,
   ceated_time          timestamp not null,
   last_modified_time   timestamp,
   content_type         varchar(20),
   method_type          int,
   primary key (template_id)
);

alter table tb_template comment 'ͨ��ģ�壬������';

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

alter table tb_web_content comment 'ĳ��վ�ض������ݿ�';

/*==============================================================*/
/* Table: tb_web_content_parameters                             */
/*==============================================================*/
create table tb_web_content_parameters
(
   content_id           bigint,
   id                   bigint not null,
   target_param_id      bigint not null,
   name                 varchar(50) not null,
   value                varchar(2000),
   primary key (id)
);

alter table tb_web_content_parameters comment 'ĳ��վ�ض����ݵĲ����б�';

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
);

/*==============================================================*/
/* Table: tb_web_headers                                        */
/*==============================================================*/
create table tb_web_headers
(
   id                   bigint not null,
   name                 varchar(50),
   value                varchar(200),
   primary key (id)
);

/*==============================================================*/
/* Table: tb_websites                                           */
/*==============================================================*/
create table tb_websites
(
   "id--����"             int not null,
   url                  varchar(200) not null,
   name                 varchar(50) not null,
   charger_id           bigint,
   type                 tinyint not null,
   description          varchar(1000),
   logo                 varchar(200),
   primary key ("id--����")
);

alter table tb_websites comment '�����ƹ��Ŀ����վ�б�';

/*==============================================================*/
/* Table: tb_world_relation                                     */
/*==============================================================*/
create table tb_world_relation
(
   from_uid             bigint,
   from_uid_type        tinyint,
   to_uid               bigint,
   to_uid_type          tinyint,
   relation_id          int
);

alter table tb_content add constraint FK_Reference_20 foreign key (template_id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_content_headers add constraint FK_Reference_24 foreign key (id)
      references tb_web_content (id) on delete restrict on update restrict;

alter table tb_content_headers add constraint FK_Reference_25 foreign key (content_id)
      references tb_web_headers (id) on delete restrict on update restrict;

alter table tb_content_parameters add constraint FK_Reference_21 foreign key (param_id)
      references tb_parameters (param_id) on delete restrict on update restrict;

alter table tb_content_parameters add constraint FK_Reference_7 foreign key (content_id)
      references tb_content (content_id) on delete restrict on update restrict;

alter table tb_flow_template_relation add constraint FK_Reference_30 foreign key (template_id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_flow_template_relation add constraint FK_Reference_31 foreign key (flow_id)
      references tb_target_web_flow (flow_id) on delete restrict on update restrict;

alter table tb_parameters add constraint FK_Reference_16 foreign key (tempate_id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_robot_user add constraint FK_Reference_13 foreign key (id)
      references tb_websites ("id--����") on delete restrict on update restrict;

alter table tb_robot_user add constraint FK_Reference_2 foreign key (website_id)
      references tb_profile (id) on delete restrict on update restrict;

alter table tb_robot_user add constraint FK_Reference_23 foreign key (profile_id)
      references "tb_admin-��̨����Ա" (id) on delete restrict on update restrict;

alter table tb_sub_task add constraint FK_Reference_22 foreign key (id)
      references tb_client (id) on delete restrict on update restrict;

alter table tb_sub_task add constraint FK_Reference_27 foreign key (content_id)
      references tb_task (id) on delete restrict on update restrict;

alter table tb_sub_task add constraint FK_Reference_35 foreign key ()
      references tb_web_content (id) on delete restrict on update restrict;

alter table tb_target_cookies add constraint FK_Reference_33 foreign key (template_id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_target_headers add constraint FK_Reference_32 foreign key (tb__template_id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_target_param_values add constraint FK_Reference_15 foreign key (param_id)
      references tb_target_template_parameters (id) on delete restrict on update restrict;

alter table tb_target_template add constraint FK_Reference_11 foreign key (id)
      references tb_websites ("id--����") on delete restrict on update restrict;

alter table tb_target_template add constraint FK_Reference_14 foreign key (id)
      references tb_template (template_id) on delete restrict on update restrict;

alter table tb_target_template_parameters add constraint FK_Reference_17 foreign key (param_id)
      references tb_parameters (param_id) on delete restrict on update restrict;

alter table tb_target_template_parameters add constraint FK_Reference_18 foreign key (data_type)
      references tb_data_type (id) on delete restrict on update restrict;

alter table tb_target_template_parameters add constraint FK_Reference_3 foreign key (template_id)
      references tb_target_template (id) on delete restrict on update restrict;

alter table tb_task add constraint FK_Reference_26 foreign key (id)
      references tb_client (id) on delete restrict on update restrict;

alter table tb_task add constraint FK_Reference_29 foreign key ()
      references tb_robot_user on delete restrict on update restrict;

alter table tb_task add constraint FK_Reference_36 foreign key ()
      references tb_web_content (id) on delete restrict on update restrict;

alter table tb_web_content add constraint FK_Reference_12 foreign key (template_id)
      references tb_websites ("id--����") on delete restrict on update restrict;

alter table tb_web_content add constraint FK_Reference_4 foreign key (template_id)
      references tb_target_template (id) on delete restrict on update restrict;

alter table tb_web_content add constraint FK_Reference_6 foreign key (content_id)
      references tb_content (content_id) on delete restrict on update restrict;

alter table tb_web_content_parameters add constraint FK_Reference_19 foreign key (content_id)
      references tb_target_template_parameters (id) on delete restrict on update restrict;

alter table tb_web_content_parameters add constraint FK_Reference_5 foreign key (content_id)
      references tb_web_content (id) on delete restrict on update restrict;

alter table tb_web_cookies add constraint FK_Reference_28 foreign key (id)
      references tb_web_content (id) on delete restrict on update restrict;

alter table tb_world_relation add constraint FK_Reference_34 foreign key ()
      references tb_relation on delete restrict on update restrict;
