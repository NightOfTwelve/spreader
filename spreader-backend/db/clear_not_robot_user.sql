drop procedure if exists clear_no_robot_user;
drop table if exists spreader.tmp;
CREATE TEMPORARY TABLE spreader.tmp SELECT id as uid,website_uid FROM spreader.tb_user where is_robot=false;
delimiter //
create procedure spreader.clear_not_robot_user()
begin
  DECLARE finished int DEFAULT 0;
  DECLARE v_uid bigint DEFAULT 0;
  declare cr_tmp cursor for select uid from spreader.tmp;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished=1;
  open cr_tmp;
  loop_user:loop
    fetch cr_tmp into v_uid;
    if finished=1 then leave loop_user; end if;
    delete from spreader.tb_user_relation where uid = v_uid;
    delete from spreader.tb_user_tag where uid = v_uid;
    delete from spreader.tb_user where id = v_uid;
  end loop loop_user;
  close cr_tmp;
end
//
call spreader.clear_not_robot_user
//