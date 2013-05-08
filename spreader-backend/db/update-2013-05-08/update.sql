
drop table if exists tb_client_report;

/*==============================================================*/
/* Table: tb_client_report                                      */
/*==============================================================*/
create table tb_client_report
(
   client_id            bigint,
   client_seq			bigint,
   task_date            datetime comment '任务日期',
   task_type            int,
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime,
   expect_count         int comment '计划执行数量',
   actual_count         int comment '实际执行数量'
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table tb_client_report comment '客户端自动执行任务报告';
alter table tb_client_report add index (task_date, task_type, client_id, client_seq);