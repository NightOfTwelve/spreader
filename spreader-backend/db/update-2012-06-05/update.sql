ALTER TABLE tb_content ADD pic_url VARCHAR(200) comment '图片地址';

ALTER TABLE tb_content ADD video_url VARCHAR(200) comment '视频地址';

ALTER TABLE tb_content ADD audio_url VARCHAR(200) comment '音频地址';

CREATE TABLE tb_content_keyword
(
id bigint not null auto_increment,
content_id bigint comment '内容ID',
keyword_id bigint comment '关键字ID', 
create_time timestamp comment '创建时间',
primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create unique index uk_content_keyword on tb_content_keyword
(
   content_id,keyword_id
);

alter table tb_content_keyword comment '微博内容与关键字绑定关系表';