create table spreader.tb_email_using
(
   email                varchar(100) comment '使用邮箱',
   primary key (email)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table spreader.tb_user_group_exclude comment '使用的邮箱';

insert ignore into tb_email_using select login_name from tb_robot_user where website_id=1;
insert ignore into tb_email_using select email from tb_robot_user ru join tb_robot_register r on r.id=ru.robot_register_id where website_id=2;