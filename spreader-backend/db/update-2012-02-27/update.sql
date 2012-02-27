//添加默认值
alter table tb_photo drop column avatarflg;

alter table tb_photo add column avatarflg bit default false;

alter table tb_photo drop column photolibflg;

alter table tb_photo add column photolibflg bit default false;

//添加索引
ALTER TABLE tb_photo ADD INDEX IDX_PHOTO_GENDER(gender,avatarflg)