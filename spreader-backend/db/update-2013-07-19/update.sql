alter table tb_client_report drop column app_id; 
alter table tb_client_report Add column app_name varchar(500);
DROP INDEX idx ON tb_client_report;
ALTER TABLE tb_client_report ADD INDEX idx_client_report (task_date,client_id,client_seq,task_type,action_id,app_name);