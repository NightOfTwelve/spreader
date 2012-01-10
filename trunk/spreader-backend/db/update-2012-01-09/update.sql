alter table spreader.tb_robot_register add column (
   county               varchar(50) comment '县',
   person_id            varchar(18) comment '身份证',
   student_id           varchar(18) comment '学生证'
);
