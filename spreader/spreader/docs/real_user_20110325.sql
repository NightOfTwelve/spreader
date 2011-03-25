use ebuy;

drop table if exists tb_real_user;

/*==============================================================*/
/* Table: tb_real_user                                          */
/*==============================================================*/
create table tb_real_user
(
   uid                  bigint not null,
   website_uid          bigint,
   website_id           int,
   user_name            varchar(30),
   email                varchar(30),
   nationality          varchar(30),
   province             varchar(20),
   city                 varchar(30),
   district             varchar(30),
   sex                  char(10),
   qq                   varchar(30),
   msn                  varchar(50),
   nick_name            varchar(50),
   real_name            varchar(30),
   id_card              varchar(30),
   birthday_year        int,
   birthday_month       int,
   company_name         varchar(50),
   company_address      varchar(100),
   home_address         varchar(100),
   university           varchar(100),
   junior_middle_school varchar(100),
   senior_middle_school varchar(100),
   interest             varchar(100),
   skill                varchar(100),
   create_time          timestamp,
   profession           varchar(50),
   tags                 varchar(200),
   home_site            varchar(300),
   intro                varchar(300),
   signature            varchar(100),
   fans_count           int,
   follow_count         int,
   blog_count           int,
   photo_count          int,
   video_count          int,
   voice_count          int,
   primary key (uid)
);
