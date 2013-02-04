update tb_user set robot_fans = 0  where robot_fans is null;
update tb_user set fans = 0  where fans is null;
update tb_user set attentions = 0  where attentions is null;
update tb_user set articles = 0  where articles is null;

ALTER TABLE tb_user MODIFY COLUMN articles bigint  not null default 0;

ALTER TABLE tb_user MODIFY COLUMN attentions bigint  not null default 0;

ALTER TABLE tb_user MODIFY COLUMN robot_fans bigint  not null default 0;

ALTER TABLE tb_user MODIFY COLUMN fans bigint not null default 0;