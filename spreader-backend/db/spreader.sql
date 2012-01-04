/*==============================================================*/
/* Database name:  spreader                                     */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011-12-7 16:20:24                           */
/*==============================================================*/


drop database if exists spreader;

/*==============================================================*/
/* Database: spreader                                           */
/*==============================================================*/
create database spreader;

use spreader;

/*==============================================================*/
/* Table: tb_action_step                                        */
/*==============================================================*/
create table tb_action_step
(
   id                   bigint not null auto_increment,
   action_id            bigint not null,
   type                 varchar(10),
   post_config          text,
   fetch_config         text,
   error_config         text,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_action_step comment 'action����';

/*==============================================================*/
/* Table: tb_captcha                                            */
/*==============================================================*/
create table tb_captcha
(
   id                   bigint not null auto_increment,
   expire_time          datetime comment '����ʱ��',
   data                 blob comment 'ͼ������',
   type                 int comment '0:�ѷ��룬1�������У�2�����˹�ʶ���������Զ�����',
   result               varchar(50),
   post_client          bigint comment '�ύ�ͻ���',
   handle_client        bigint comment '����ͻ��ˣ�0Ϊϵͳ����',
   handle_time          datetime comment '����ʱ��',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

/*==============================================================*/
/* Index: idx_type_expire                                       */
/*==============================================================*/
create index idx_type_expire on tb_captcha
(
   type,
   expire_time
);

/*==============================================================*/
/* Table: tb_career                                             */
/*==============================================================*/
create table tb_career
(
   id                   bigint not null auto_increment,
   uid                  bigint not null,
   company              varchar(200),
   province             varchar(50),
   city                 varchar(50),
   start_date           date,
   end_date             date,
   position             varchar(100),
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_career comment 'ְҵ��Ϣ';

/*==============================================================*/
/* Table: tb_category                                           */
/*==============================================================*/
create table tb_category
(
   id                   bigint not null auto_increment,
   name                 varchar(200) not null,
   description          varchar(2000),
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

/*==============================================================*/
/* Index: uk_name                                               */
/*==============================================================*/
create unique index uk_name on tb_category
(
   name
);

/*==============================================================*/
/* Table: tb_client_action                                      */
/*==============================================================*/
create table tb_client_action
(
   id                   bigint not null auto_increment,
   logic_type           int not null default 0,
   return_config        text,
   cool_down_seconds    bigint,
   weight               int,
   procedure_config     varchar(2000),
   description          varchar(2000),
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_client_action comment '�ͻ�����Ϊ';

/*==============================================================*/
/* Table: tb_client_task                                        */
/*==============================================================*/
create table tb_client_task
(
   id                   bigint not null auto_increment,
   contents             text,
   action_id            bigint,
   uid                  bigint not null,
   task_code            varchar(100),
   batch_id             bigint not null default 0,
   task_type            int not null default 1 comment '��������',
   expire_time          datetime,
   hash                 char(10) comment 'hashֵ�������������',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_client_task comment '�ͻ�������';

/*==============================================================*/
/* Index: idx_user_action                                       */
/*==============================================================*/
create index idx_user_action on tb_client_task
(
   batch_id,
   task_type,
   uid,
   action_id
);

/*==============================================================*/
/* Table: tb_content                                            */
/*==============================================================*/
create table tb_content
(
   id                   bigint not null auto_increment,
   type                 int comment '1:΢��',
   website_id           int comment '��վid',
   website_content_id   bigint comment '��վ������id',
   website_ref_id       bigint comment '������վ���������ݵ�id',
   website_uid          bigint comment '��վ�û�id',
   uid                  bigint comment '�û�id',
   title                varchar(1000),
   content              longtext,
   pub_date             datetime comment '��������',
   sync_date            datetime comment '��ȡ����',
   ref_count            int comment 'ת����',
   reply_count          int comment '�ظ���',
   entry                varchar(1000) comment 'ҳ�����',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

/*==============================================================*/
/* Index: user_index                                            */
/*==============================================================*/
create index user_index on tb_content
(
   uid
);

/*==============================================================*/
/* Table: tb_content_category                                   */
/*==============================================================*/
create table tb_content_category
(
   content_id           bigint not null,
   category_id          bigint not null,
   primary key (content_id, category_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_content_category comment '���ݷ���';

/*==============================================================*/
/* Table: tb_education                                          */
/*==============================================================*/
create table tb_education
(
   id                   bigint not null auto_increment,
   uid                  bigint not null,
   school               varchar(100),
   type                 varchar(30),
   college              varchar(100),
   year                 int comment '��ѧʱ��',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_education comment '������Ϣ';

/*==============================================================*/
/* Table: tb_login_config                                       */
/*==============================================================*/
create table tb_login_config
(
   uid                  bigint not null,
   contents             text,
   action_id            bigint,
   primary key (uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_login_config comment '��½����';

/*==============================================================*/
/* Table: tb_login_fail_info                                    */
/*==============================================================*/
create table tb_login_fail_info
(
   id                   bigint not null auto_increment,
   uid                  bigint,
   client_id            bigint,
   error                text,
   created_time         datetime,
   client_detail        text,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_login_fail_info comment '���񷵻�';

/*==============================================================*/
/* Table: tb_photo                                              */
/*==============================================================*/
create table tb_photo
(
   id                   bigint not null auto_increment,
   pic                  blob comment 'ͼ������',
   gender               int comment '�Ա�',
   uid                  bigint default 0 comment '�û�id',
   pic_type             varchar(10) comment 'ͼƬ����',
   pic_url              varchar(200) comment 'ͼƬurl���ⲿͼƬ��',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_photo comment '�û�ͷ��';

/*==============================================================*/
/* Table: tb_regular_job                                        */
/*==============================================================*/
create table tb_regular_job
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null comment '����',
   config               text comment '���ò���',
   trigger_type         int not null comment '1:simple,2:cron',
   trigger_info         varchar(4000) not null comment '����������',
   description          varchar(2000) comment '����',
   job_type             int default 1 comment '�������ͣ�0��ϵͳ��1����ͨ',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_regular_job comment '��ʱ�����';

/*==============================================================*/
/* Index: idx_name                                              */
/*==============================================================*/
create index idx_name on tb_regular_job
(
   name
);

/*==============================================================*/
/* Index: idx_job_type                                          */
/*==============================================================*/
create index idx_job_type on tb_regular_job
(
   job_type,
   id
);

/*==============================================================*/
/* Table: tb_robot_register                                     */
/*==============================================================*/
create table tb_robot_register
(
   id                   bigint not null auto_increment,
   first_name           varchar(10) comment '��',
   last_name            varchar(20) comment '��',
   gender               int comment '�Ա�',
   first_name_pinyin    varchar(50) comment 'ƴ������',
   last_name_pinyin     varchar(50) comment 'ƴ������',
   en_name              varchar(50) comment 'Ӣ����',
   nick_name            varchar(100) comment '�����ǳ�',
   province             varchar(30) comment 'ʡ',
   city                 varchar(50) comment '��',
   birthday_year        int comment '������',
   birthday_month       int comment '������',
   birthday_day         int comment '������',
   constellation        int comment '����������-˫�㣺1-12',
   school               varchar(100) comment 'ѧУ',
   career               varchar(100) comment 'ְҵ',
   pwd                  varchar(100) comment '��������',
   email                varchar(100) comment 'ʹ������',
   introduction         varchar(1000) comment '���ҽ���',
   update_time          datetime comment '������ʱ��',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_robot_register comment '������ע���
������ṩע���õĸ�����Ϣ�����ǲ�һ����������ע�����Ϣ�������ǳ�ʲô�Ŀ�����Ϊ�������ı�';

/*==============================================================*/
/* Table: tb_robot_user                                         */
/*==============================================================*/
create table tb_robot_user
(
   uid                  bigint not null,
   website_id           int not null comment '��վID',
   website_uid          bigint,
   login_name           varchar(200),
   login_pwd            varchar(100),
   robot_register_id    bigint not null comment '��������Ϣid',
   account_state        int not null comment '�ʺ�״̬-0��ע���У�1��������2��ͣ��',
   primary key (uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_robot_user comment '�˻����û���¼������վ';

/*==============================================================*/
/* Table: tb_task_batch                                         */
/*==============================================================*/
create table tb_task_batch
(
   id                   bigint not null auto_increment,
   client_id            bigint,
   expire_time          datetime,
   assign_time          datetime,
   task_type            int not null comment '��������',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_task_batch comment '��������';

/*==============================================================*/
/* Index: expire_time                                           */
/*==============================================================*/
create index expire_time on tb_task_batch
(
   task_type,
   expire_time
);

/*==============================================================*/
/* Table: tb_task_result                                        */
/*==============================================================*/
create table tb_task_result
(
   task_id              bigint not null auto_increment,
   client_id            bigint,
   result               text,
   task_code            varchar(100),
   status               int not null comment '0���ɹ���1��ʧ�ܣ�2��������3������',
   executed_time        datetime,
   primary key (task_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_task_result comment '���񷵻�';

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_user
(
   id                   bigint not null auto_increment,
   website_id           int not null,
   website_uid          bigint not null,
   is_robot             boolean not null,
   create_time          datetime not null comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   nick_name            varchar(100) comment '�ǳ�',
   gender               int comment '��1��Ů2',
   space_entry          varchar(200) comment '������ҳ',
   real_name            varchar(30) comment '����',
   email                varchar(100) comment 'ע��ʱ�õ�email',
   nationality          varchar(100) comment '����',
   province             varchar(30),
   city                 varchar(50),
   introduction         varchar(1000) comment '���ҽ���',
   birthday_year        int,
   birthday_month       int,
   birthday_day         int,
   constellation        int comment '����������-˫�㣺1-12',
   qq                   varchar(30),
   msn                  varchar(50),
   blog                 varchar(200),
   v_type               int default 0 comment '1ʵ����֤��2��ҵ��֤',
   v_info               varchar(200) comment 'ʵ����Ϣ',
   attentions           bigint comment '��ע����',
   fans                 bigint comment '��˿��',
   articles             bigint comment '������',
   attentions_relation_update_time datetime,
   robot_fans           bigint default 0 comment '�����˷�˿��',
   primary key (id),
   key uk_tb_user_website_user (website_id, website_uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_user comment '�û���Ϣ������ְҵ���Ա���Ȥ����Ϣ';

/*==============================================================*/
/* Table: tb_user_relation                                      */
/*==============================================================*/
create table tb_user_relation
(
   uid                  bigint not null,
   to_uid               bigint not null,
   type                 int not null,
   website_id           int,
   website_uid          bigint,
   to_website_uid       bigint,
   is_robot_user        boolean default false comment '�Ƿ������',
   primary key (uid, to_uid, type)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

/*==============================================================*/
/* Table: tb_user_tag                                           */
/*==============================================================*/
create table tb_user_tag
(
   id                   bigint not null auto_increment,
   uid                  bigint not null,
   tag                  varchar(100),
   category_id          bigint,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_user_tag comment '�û���ǩ';

/*==============================================================*/
/* Index: category_user                                         */
/*==============================================================*/
create index category_user on tb_user_tag
(
   uid,
   category_id
);

/*==============================================================*/
/* Table: tb_websites                                           */
/*==============================================================*/
create table tb_websites
(
   id                   int not null auto_increment,
   name                 varchar(50) not null,
   type                 int not null,
   url                  varchar(200) not null,
   description          varchar(1000),
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_websites comment '�����ƹ��Ŀ����վ�б�';

