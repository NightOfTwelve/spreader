create table spreader.tb_ip_record
(
   id                   bigint not null auto_increment,
   client_id            bigint not null comment '客户端编号',
   ip            		varchar(20) not null comment 'IP',
   create_time 			timestamp not null comment 'IP开始登记时间',
   record_time          timestamp not null comment '登记时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_ip_record comment '客户端IP记录';

alter table spreader.tb_ip_record add is_cleared boolean default false not null comment '过期清理';

create index idx_client_id_create_time on spreader.tb_ip_record
(
   client_id,create_time
);