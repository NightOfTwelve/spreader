
/* 增加是否为头像字段*/
alter table spreader.tb_photo add avatarflg bit(0);
/* 增加是否为相册字段 */
alter table spreader.tb_photo add photolibflg bit(0);

alter table spreader.tb_photo add createtime timestamp;

/* 增加图片ID字段*/
alter table spreader.tb_user Add column pid bigint;