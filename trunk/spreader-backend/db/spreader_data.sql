
INSERT INTO spreader.tb_client_action (id,cool_down_seconds,weight,description,return_config) VALUES (1,600,1000,'爬取明星排行榜','class=List<Long>');
INSERT INTO spreader.tb_client_action (id,cool_down_seconds,weight,description,return_config) VALUES (2,60,50,'爬取个人主页','class=com.nali.spreader.data.User');
--INSERT INTO spreader.tb_robot_user (uid,website_id,website_uid,login_name,login_pwd) VALUES (1,1,2285193757,'ninethere@sina.com','nalibar');
--INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd) VALUES (2,1,2304565795,'8there@sina.cn','nalibar');

/*
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (1,1,2283050077,'damogu10001@163.com','111111#',1,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (2,1,2283589845,'damogu10002@163.com','111111#',2,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (3,1,2283058703,'damogu10003@163.com','111111#',3,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (4,1,2283060443,'damogu10004@163.com','111111#',4,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (5,1,2283063533,'damogu10005@163.com','111111#',5,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (6,1,2283065173,'xiaotudou10001@163.com','111111#',6,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (7,1,2283066743,'xiaotudou10002@163.com','111111#',7,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (8,1,2283604895,'xiaotudou10003@163.com','111111#',8,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (9,1,2283070107,'xiaotudou10004@163.com','111111#',9,1);
INSERT INTO tb_robot_user (uid,website_id,website_uid,login_name,login_pwd,robot_register_id,account_state) VALUES (10,1,2283072707,'xiaotudou10005@163.com','111111#',10,1);
*/

insert into spreader.tb_user(id,website_id,website_uid,create_time,is_robot)
select uid,website_id,website_uid,now(),true
from spreader.tb_robot_user;

INSERT INTO spreader.tb_robot_register (id,pwd,email,update_time)
select robot_register_id,login_pwd,login_name,now()
from spreader.tb_robot_user;


insert into spreader.tb_login_config(uid,contents,action_id)
select uid, concat('{"name":"', login_name, '","password":"', login_pwd,'"}'), 3
from spreader.tb_robot_user;
