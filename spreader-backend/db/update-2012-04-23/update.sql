create table tb_keyword
(
   id                   bigint not null auto_increment,
   name                 varchar(50),
   category_id          bigint,
   tag                  bit(0) not null default 0 comment '是否是手工建的关键字',
   create_time          timestamp,
   executable			bit(0) not null default true comment '执行状态，false表示被锁定',
   allowtag				bit(0) default true comment '是否可做标签关键字，true可以做标签',
   primary key (id)
)
auto_increment = 800000
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_keyword comment '关键字表';

create unique index uk_keyword_name on tb_keyword
(
   name
);

insert into tb_keyword
select id,name,id,false,current_timestamp(),true,true from tb_category;



DROP INDEX uk_category_user ON tb_user_tag;

alter table tb_user_tag drop column category_id;

ALTER TABLE tb_user_tag ADD tag_id bigint;

ALTER TABLE tb_user_tag ADD INDEX idx_tag_user (uid,tag_id);
