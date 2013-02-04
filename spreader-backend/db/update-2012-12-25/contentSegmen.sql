create table spreader.tb_content_segmen
(
   id             			bigint not null  auto_increment,
   name     				VARCHAR(50) not null  comment '分词名称',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_content_segmen comment '内容库分词对应表';

create unique index uk_content_segmen_name on spreader.tb_content_segmen
(
   name
);