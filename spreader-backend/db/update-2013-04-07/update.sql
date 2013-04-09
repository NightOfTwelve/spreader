
alter table tb_app_udid add column (
   q1        varchar(50),
   q2        varchar(50),
   q3		 varchar(50),
   a1		 varchar(50),
   a2		 varchar(50),
   a3	     varchar(50)
);

create table tb_user_pay
(
   uid          bigint not null,
   pay_type		int not null comment '支付类型：1,信用卡，2,预付费',
   status       int not null comment '0：注册完毕，1：基本付费帐号，2-9预留，其他状态根据pay_type解释',
   pay_money	int not null default 0 comment '支付过的金额',
   primary key (uid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table tb_apple_super_user
(
   id           bigint not null auto_increment,
   uid          bigint not null,
   status       int not null default 1 comment '1：可用，2：被封',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table tb_app_purchase
(
   appid         bigint not null,
   info			 blob comment '购买信息摘要',
   info_type 	 int default 1 comment '摘要类型:1,bitset',
   assign_count	 int comment '分配数量',
   confirm_count int comment '返回数量',
   primary key (appid)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;