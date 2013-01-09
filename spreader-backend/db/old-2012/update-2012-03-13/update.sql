alter table tb_content modify column entry varchar(200) comment '页面入口';
alter table tb_content drop index url_idx;
create unique index url_idx on tb_content
(
   type,
   website_id,
   website_uid,
   entry
);

drop table if exists tb_robot_content;

/*==============================================================*/
/* Table: tb_robot_content                                      */
/*==============================================================*/
create table tb_robot_content
(
   id                   bigint not null auto_increment,
   uid                  bigint not null,
   content_id           bigint not null,
   author_id            bigint comment '原作者id',
   type                 int not null comment '1:发过，2：转过，3：回复过',
   update_time          datetime,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_robot_content comment '机器人内容';

/*==============================================================*/
/* Index: idx_robot_content                                     */
/*==============================================================*/
create index idx_robot_content on tb_robot_content
(
   uid,
   content_id,
   type
);

/*==============================================================*/
/* Index: idx_robot_author                                      */
/*==============================================================*/
create index idx_robot_author on tb_robot_content
(
   uid,
   author_id,
   type
);
