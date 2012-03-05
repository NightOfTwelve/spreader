alter table tb_client_task add column (
   status               int
);
alter table tb_client_task modify column batch_id bigint null default null;