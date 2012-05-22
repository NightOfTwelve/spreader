create temporary table tt as select * from tb_robot_user where account_state=1 limit 3000,300;
insert into tb_user_export select u.* from tb_user u where u.id in (select uid from tt);
update tb_robot_user set account_state=3 where uid in (select uid from tt);
delete from tb_user where id in (select uid from tt);

select u.nick_name,r.login_name,r.login_pwd from tb_user_export u left join tb_robot_user r on u.id=r.uid order by nick_name;
