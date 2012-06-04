create table tb_user_export like tb_user;
alter table tb_user add index (nick_name);