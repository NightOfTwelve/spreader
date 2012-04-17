create TEMPORARY table ttt as
  select distinct(substring(ce.website_error_desc, 23)) as uid 
  from tb_client_error ce 
  where ce.error_code=10008;
insert into tb_user select u.* from tb_user_delete u join ttt t on u.id=t.uid where u.is_robot=true;
update tb_robot_user set account_state=1 where account_state=2 and uid in (select uid from ttt);
delete from tb_user_delete where is_robot=true and id in (select uid from ttt);