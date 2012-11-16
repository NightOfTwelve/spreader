create table spreader.tb_help_enum_info
(
   id             	bigint not null auto_increment,
   enum_name     	varchar(100) not null comment '分类名称',
   enum_values    	varchar(100) not null  comment '分类值说明',
   sort_id 			int comment '排序号',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_help_enum_info comment '枚举信息帮助表';

create index idx_help_enum_name on spreader.tb_help_enum_info
(
   enum_name
);