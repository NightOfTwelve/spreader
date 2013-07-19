alter table tb_client_report drop column app_id; 
alter table tb_client_report Add column app_name varchar(500);
DROP INDEX idx ON tb_client_report;
CREATE INDEX idx_client_report ON tb_client_report (task_date,client_id,action_id,client_seq,task_type,update_time,create_time,app_name);