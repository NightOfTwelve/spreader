create table tb_notice
(
   id                   bigint not null auto_increment,
   website_id 			int not null comment '网站ID',
   notice_type          int not null comment '消息类型  1.评论 2.新增粉丝 3.私信 4.@到微博 5.@到评论 6.群组消息 7.相册消息',
   to_uid               bigint not null comment '消息接收者的Uid',
   from_uid             bigint not null comment '消息来源的Uid',
   from_website_uid     bigint not null comment '消息来源的website_uid',
   content_id           bigint comment '对应content表ID',
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

ALTER TABLE tb_content ADD ref_id BIGINT comment '引用的contentId',
