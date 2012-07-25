create table tb_notice
(
   id                   bigint not null auto_increment,
   website_id 			int not null comment '网站ID',
   notice_type          int not null comment '消息类型  1.评论 2.新增粉丝 3.私信 4.@到微博 5.@到评论 6.群组消息 7.相册消息',
   to_uid               bigint not null comment '消息接收者的Uid',
   from_uid             bigint not null comment '消息来源的Uid',
   from_website_uid     bigint not null comment '消息来源的website_uid',
   replay_id           	bigint comment '引用的tb_content_replay ID',
   content_id 			bigint comment '引用的tb_content ID',
   is_from_robot		boolean comment '是否来自机器人',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_notice comment '消息通知';

create index idx_to_uid on tb_notice
(
   website_id,notice_type,to_uid,is_from_robot
);

ALTER TABLE tb_content ADD ref_id BIGINT comment '引用的contentId';


create table tb_content_replay
(
   id                   bigint not null auto_increment,
   from_uid             bigint not null comment '作者Uid',
   to_uid               bigint not null comment '被评论的微博作者Uid',
   content_id           bigint comment '被评论的微博ID',
   ref_id 				bigint comment '引用的replay ID',
   content              varchar(500) comment '评论内容',
   pub_date             date comment '评论发布时间',
   website_id           int not null,
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_content_replay comment '内容回复表';

create index idx_content_id on tb_content_replay
(
   website_id,content_id,from_uid
);

ALTER TABLE spreader.tb_regular_job ADD ref_id bigint comment '关联消息ID';
