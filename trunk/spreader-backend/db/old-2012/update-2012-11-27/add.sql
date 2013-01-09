create table spreader.tb_robot_weibo_ximalaya
(
   id             			bigint not null  auto_increment,
   uid     					bigint not null  comment 'User表ID',
   weibo_website_uid    	bigint not null  comment '微博的websiteUid',
   ximalaya_website_uid 	bigint not null  comment '喜马拉雅ID',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_robot_weibo_ximalaya comment '喜马拉雅的账户与微博账户的对应关系表';

create unique index uk_weibo_ximalaya_website_uid on spreader.tb_robot_weibo_ximalaya
(
   weibo_website_uid,ximalaya_website_uid
);