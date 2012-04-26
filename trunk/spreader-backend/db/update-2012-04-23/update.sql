create table tb_keyword
(
   id                   bigint not null auto_increment,
   name                 varchar(50),
   category_id          bigint,
   tag                  bit(0) not null default 0,
   create_time          timestamp,
   primary key (id)
)
auto_increment = 800000
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_keyword comment '关键字表';

create unique index idx_keyword_name on tb_keyword
(
   name
);

insert into tb_keyword
select id,name,id,false,current_timestamp() From tb_category;

ALTER TABLE tb_user_tag ADD tag_id bigint;

update tb_user_tag
set tag_id = category_id;