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

alter table tb_action_step comment 'action步骤';

/*==============================================================*/
/* Table: tb_captcha                                            */
/*==============================================================*/
create table tb_captcha
(
   id                   bigint not null auto_increment,
   expire_time          datetime comment '过期时间',
   data                 blob comment '图像数据',
   type                 int comment '0:已翻译，1：处理中，2：需人工识别，其他：自动处理',
   result               varchar(50),
   post_client          bigint comment '提交客户端',
   handle_client        bigint comment '处理客户端，0为系统处理',
   handle_time          datetime comment '处理时间',
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

alter table tb_career comment '职业信息';

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

alter table tb_client_action comment '客户端行为';

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
   task_type            int not null default 1 comment '任务类型',
   expire_time          datetime,
   hash                 char(10) comment 'hash值，用于任务分配',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_client_task comment '客户端任务';

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
   type                 int comment '1:微博',
   website_id           int comment '网站id',
   website_content_id   bigint comment '网站的内容id',
   website_ref_id       bigint comment '引用网站的其他内容的id',
   website_uid          bigint comment '网站用户id',
   uid                  bigint comment '用户id',
   title                varchar(1000),
   content              longtext,
   pub_date             datetime comment '发布日期',
   sync_date            datetime comment '爬取日期',
   ref_count            int comment '转发数',
   reply_count          int comment '回复数',
   entry                varchar(1000) comment '页面入口',
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

alter table tb_content_category comment '内容分类';

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
   year                 int comment '入学时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_education comment '教育信息';

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

alter table tb_login_config comment '登陆配置';

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

alter table tb_login_fail_info comment '任务返回';

/*==============================================================*/
/* Table: tb_photo                                              */
/*==============================================================*/
create table tb_photo
(
   id                   bigint not null auto_increment,
   pic                  blob comment '图像数据',
   gender               int comment '性别',
   uid                  bigint default 0 comment '用户id',
   pic_type             varchar(10) comment '图片类型',
   pic_url              varchar(200) comment '图片url（外部图片）',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_photo comment '用户头像';

/*==============================================================*/
/* Table: tb_regular_job                                        */
/*==============================================================*/
create table tb_regular_job
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null comment '名称',
   config               text comment '配置参数',
   trigger_type         int not null comment '1:simple,2:cron',
   trigger_info         varchar(4000) not null comment '触发器配置',
   description          varchar(2000) comment '描述',
   job_type             int default 1 comment '任务类型，0：系统，1：普通',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_regular_job comment '定时任务表';

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
   first_name           varchar(10) comment '姓',
   last_name            varchar(20) comment '名',
   gender               int comment '性别',
   first_name_pinyin    varchar(50) comment '拼音，姓',
   last_name_pinyin     varchar(50) comment '拼音，名',
   en_name              varchar(50) comment '英文名',
   nick_name            varchar(100) comment '网上昵称',
   province             varchar(30) comment '省',
   city                 varchar(50) comment '市',
   birthday_year        int comment '出生年',
   birthday_month       int comment '出生月',
   birthday_day         int comment '出生日',
   constellation        int comment '星座：白羊-双鱼：1-12',
   school               varchar(100) comment '学校',
   career               varchar(100) comment '职业',
   pwd                  varchar(100) comment '常用密码',
   email                varchar(100) comment '使用邮箱',
   introduction         varchar(1000) comment '自我介绍',
   update_time          datetime comment '最后更新时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_robot_register comment '机器人注册表
这个表提供注册用的个人信息，但是不一定就是真正注册的信息，比如昵称什么的可能因为重名而改变';

/*==============================================================*/
/* Table: tb_robot_user                                         */
/*==============================================================*/
create table tb_robot_user
(
   uid                  bigint not null,
   website_id           int not null comment '网站ID',
   website_uid          bigint,
   login_name           varchar(200),
   login_pwd            varchar(100),
   robot_register_id    bigint not null comment '机器人信息id',
   account_state        int not null comment '帐号状态-0：注册中，1：正常，2：停用',
   primary key (uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_robot_user comment '账户表，用户登录各大网站';

/*==============================================================*/
/* Table: tb_task_batch                                         */
/*==============================================================*/
create table tb_task_batch
(
   id                   bigint not null auto_increment,
   client_id            bigint,
   expire_time          datetime,
   assign_time          datetime,
   task_type            int not null comment '任务类型',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_task_batch comment '任务批次';

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
   status               int not null comment '0：成功，1：失败，2：放弃，3：过期',
   executed_time        datetime,
   primary key (task_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_task_result comment '任务返回';

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_user
(
   id                   bigint not null auto_increment,
   website_id           int not null,
   website_uid          bigint not null,
   is_robot             boolean not null,
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '更新时间',
   nick_name            varchar(100) comment '昵称',
   gender               int comment '男1，女2',
   space_entry          varchar(200) comment '个人首页',
   real_name            varchar(30) comment '真名',
   email                varchar(100) comment '注册时用的email',
   nationality          varchar(100) comment '国籍',
   province             varchar(30),
   city                 varchar(50),
   introduction         varchar(1000) comment '自我介绍',
   birthday_year        int,
   birthday_month       int,
   birthday_day         int,
   constellation        int comment '星座：白羊-双鱼：1-12',
   qq                   varchar(30),
   msn                  varchar(50),
   blog                 varchar(200),
   v_type               int default 0 comment '1实名认证，2企业认证',
   v_info               varchar(200) comment '实名信息',
   attentions           bigint comment '关注人数',
   fans                 bigint comment '粉丝数',
   articles             bigint comment '文章数',
   attentions_relation_update_time datetime,
   robot_fans           bigint default 0 comment '机器人粉丝数',
   primary key (id),
   key uk_tb_user_website_user (website_id, website_uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_user comment '用户信息。包括职业，性别，兴趣等信息';

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
   is_robot_user        boolean default false comment '是否机器人',
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

alter table tb_user_tag comment '用户标签';

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

alter table tb_websites comment '那里推广的目标网站列表';

