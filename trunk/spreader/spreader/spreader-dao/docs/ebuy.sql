/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2010-12-8 15:20:43                           */
/*==============================================================*/


alter table tb_brand
   drop primary key;

drop table if exists tb_brand;

alter table tb_brand_catalog
   drop primary key;

drop table if exists tb_brand_catalog;

drop table if exists tb_brand_option;

alter table tb_catalog_option
   drop primary key;

drop table if exists tb_catalog_option;

alter table tb_catalog_relation
   drop primary key;

drop table if exists tb_catalog_relation;

alter table tb_crawler_logs
   drop primary key;

drop table if exists tb_crawler_logs;

alter table tb_emarket
   drop primary key;

drop table if exists tb_emarket;

alter table tb_emarket_catalog
   drop primary key;

drop table if exists tb_emarket_catalog;

drop table if exists tb_emarket_comparasion;

alter table tb_goods_baby
   drop primary key;

drop table if exists tb_goods_baby;

alter table tb_goods_bag
   drop primary key;

drop table if exists tb_goods_bag;

alter table tb_goods_beauty
   drop primary key;

drop table if exists tb_goods_beauty;

alter table tb_goods_book
   drop primary key;

drop table if exists tb_goods_book;

alter table tb_goods_car
   drop primary key;

drop table if exists tb_goods_car;

alter table tb_goods_clothing
   drop primary key;

drop table if exists tb_goods_clothing;

alter table tb_goods_common
   drop primary key;

drop table if exists tb_goods_common;

alter table tb_goods_computer
   drop primary key;

drop table if exists tb_goods_computer;

alter table tb_goods_digital
   drop primary key;

drop table if exists tb_goods_digital;

alter table tb_goods_electronic
   drop primary key;

drop table if exists tb_goods_electronic;

alter table tb_goods_food
   drop primary key;

drop table if exists tb_goods_food;

alter table tb_goods_furniture
   drop primary key;

drop table if exists tb_goods_furniture;

alter table tb_goods_sports
   drop primary key;

drop table if exists tb_goods_sports;

alter table tb_goods_toy
   drop primary key;

drop table if exists tb_goods_toy;

alter table tb_goods_watch
   drop primary key;

drop table if exists tb_goods_watch;

alter table tb_nali_catalog
   drop primary key;

drop table if exists tb_nali_catalog;

/*==============================================================*/
/* Table: tb_brand                                              */
/*==============================================================*/
create table tb_brand
(
   id                   int not null comment 'Ʒ��Id',
   brand_zh_name        varchar(30) not null comment 'Ʒ�Ƶ�������',
   description          varchar(200) comment 'Ʒ������',
   site                 varchar(50) not null comment 'Ʒ�ƹٷ���ַ',
   brand_en_name        varchar(30) not null comment 'Ʒ��Ӣ������û�е���ƴ�����',
   create_time          date not null,
   last_modifed_time    date not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_brand
   add primary key (id);

/*==============================================================*/
/* Table: tb_brand_catalog                                      */
/*==============================================================*/
create table tb_brand_catalog
(
   id                   bigint not null,
   brand_id             int not null,
   catalog_id           int not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_brand_catalog comment 'Ʒ�Ʒ����ϵ��';

alter table tb_brand_catalog
   add primary key (id);

/*==============================================================*/
/* Table: tb_brand_option                                       */
/*==============================================================*/
create table tb_brand_option
(
   brand_id             int comment 'Ʒ��Id',
   alias                varchar(500)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_brand_option comment 'Ʒ��ѡ���Ʒ�Ʊ�����';

/*==============================================================*/
/* Table: tb_castalog_option                                    */
/*==============================================================*/
create table tb_catalog_option
(
   catalog_id           int not null,
   properties           varchar(600),
   alias                varchar(600)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_catalog_option comment '��Ʒ������������������������к�̨��ά�ֶ����';

alter table tb_catalog_option
   add primary key (catalog_id);

/*==============================================================*/
/* Table: tb_catalog_relation                                   */
/*==============================================================*/
create table tb_catalog_relation
(
   nali_catalog_id      int,
   emarket_catalog_id   int,
   id                   int not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_catalog_relation
   add primary key (id);

/*==============================================================*/
/* Table: tb_crawler_logs                                       */
/*==============================================================*/
create table tb_crawler_logs
(
   id                   bigint not null,
   create_time          date not null,
   emarket_id           bigint not null,
   title                varchar(200),
   catalog_name         bigint,
   brand_name           varchar(30),
   goods_url            varchar(200),
   file                 varchar(20),
   attributes           varchar(6000),
   firm                 varchar(100),
   product_area         varchar(20),
   weight               varchar(30)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_crawler_logs comment '��Ʒ��ȡ��־��¼';

alter table tb_crawler_logs
   add primary key (id);

/*==============================================================*/
/* Table: tb_emarket                                            */
/*==============================================================*/
create table tb_emarket
(
   id                   int not null,
   name                 varchar(20) not null comment '�̳�����',
   description          varchar(200) not null,
   pay_channel          int not null comment '֧�ֵ�֧����ʽ',
   dispatch_channel     int not null comment '֧�ֵ����ͷ�ʽ',
   site                 varchar(50) not null comment '�̳���ҳ��ַ',
   freight_pay_policy   varchar(100) not null comment '�˷Ѳ���',
   create_time          date
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_emarket comment '���̳���Ϣ';

alter table tb_emarket
   add primary key (id);

/*==============================================================*/
/* Table: tb_emarket_catalog                                    */
/*==============================================================*/
create table tb_emarket_catalog
(
   id                   int not null,
   level                int not null comment '����ļ���',
   name                 varchar(30) not null comment '�����������',
   parent_id            int comment '�������Id',
   emarket_id           int not null,
   create_time          date not null,
   last_modified_time   date not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_emarket_catalog comment '���������̳ǵķ����б�';

alter table tb_emarket_catalog
   add primary key (id);

/*==============================================================*/
/* Table: tb_emarket_comparasion                                */
/*==============================================================*/
create table tb_emarket_comparasion
(
   emarket_id           int not null,
   goods_id             bigint not null,
   price                decimal(16,2),
   comments_count       int,
   score                int,
   list_date            date comment '�ϼ�ʱ��',
   create_time          date,
   last_modified_time   date not null
) type = InnoDB DEFAULT Charset = utf8;

/*==============================================================*/
/* Table: tb_goods_baby                                         */
/*==============================================================*/
create table tb_goods_baby
(
   goods_id             bigint not null,
   size                 char(30),
   shelf_life           char(20),
   suttle               char(20) comment '����',
   gross_weight         char(20) comment 'ë��',
   standard_number      char(30) comment '��Ʒ��׼��',
   hygiene_liscence     varchar(40) comment '�������֤',
   material             varchar(200) comment '����/����/ԭ��',
   standard             varchar(30) comment '���/���',
   color                char(20)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_baby comment 'ĸӤ��Ʒ';

alter table tb_goods_baby
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_bag                                          */
/*==============================================================*/
create table tb_goods_bag
(
   goods_id             bigint not null,
   material             varchar(20) comment '����',
   color                varchar(10) comment '��ɫ',
   size                 varchar(30) comment '�ߴ�',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_bag comment '�����Ʒ�������Ա�';

alter table tb_goods_bag
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_beauty                                       */
/*==============================================================*/
create table tb_goods_beauty
(
   goods_id             bigint not null,
   suttle               char(20),
   shelf_life           char(30),
   capacity             char(30),
   size                 char(40),
   color                char(20),
   material             char(50) comment '�ʵ�/�ɷ�'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_beauty comment '��ױ��Ʒ';

alter table tb_goods_beauty
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_book                                         */
/*==============================================================*/
create table tb_goods_book
(
   goods_id             bigint not null,
   author               varchar(100),
   isbn                 varchar(50),
   publication_time     timestamp comment '����ʱ��',
   edition              varchar(10) comment '���',
   make_up              varchar(20) comment 'װ֡',
   pages                int comment 'ҳ��',
   format               varchar(20) comment '����',
   introduction         varchar(2000) comment '���ݼ��'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_book comment 'ͼ��';

alter table tb_goods_book
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_car                                          */
/*==============================================================*/
create table tb_goods_car
(
   goods_id             bigint not null,
   id                   int,
   color                varchar(30),
   size                 varchar(50) comment '�ߴ�',
   suttle               float,
   gross_weight         float comment 'ë��',
   ram                  varchar(30) comment '�ڴ�',
   screen               varchar(50) comment '��ʾ��',
   battery              varchar(50) comment '���',
   electric             varchar(50) comment '��Դ',
   material             varchar(30) comment '����',
   standard             varchar(80) comment '���',
   bar_code             varchar(100) comment '������',
   sphere               varchar(500) comment '���÷�Χ',
   shelf_life           varchar(20) comment '������',
   power                varchar(20) comment '����'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_car comment '������Ʒ';

alter table tb_goods_car
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_clothing                                     */
/*==============================================================*/
create table tb_goods_clothing
(
   goods_id             int not null,
   id                   int,
   color                char(30),
   material             char(30),
   pattern_number       char(50) comment '���',
   washing              char(50) comment 'ϴ��',
   weight               float
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_clothing comment '��װЬñ';

alter table tb_goods_clothing
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_common                                       */
/*==============================================================*/
create table tb_goods_common
(
   id                   bigint not null,
   name                 varchar(200) not null,
   catalog_id           int not null,
   brand_id             int not null,
   description          varchar(6000),
   create_time          timestamp not null comment '����ʱ��',
   last_modify_time     timestamp not null,
   model_number         varchar(80) comment '�ͺ�',
   image                varchar(250) comment 'imageUrl',
   firm                 varchar(100) comment '����',
   product_area         varchar(20) comment '����',
   weight               varchar(30) comment '��Ʒë��'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_common comment '��Ʒͨ�����Ա�';

alter table tb_goods_common
   add primary key (id);

/*==============================================================*/
/* Table: tb_goods_computer                                     */
/*==============================================================*/
create table tb_goods_computer
(
   goods_id             bigint not null,
   color                char(20),
   cpu                  char(20),
   ram                  char(20),
   dist                 char(20) comment 'Ӳ��',
   weight               float,
   size                 char(30),
   sphere               varchar(200),
   isbn                 char(20),
   name                 char(30),
   type                 varchar(20) comment '����'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_computer comment '���԰칫';

alter table tb_goods_computer
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_digital                                      */
/*==============================================================*/
create table tb_goods_digital
(
   goods_id             bigint not null,
   color                char(20),
   design               char(10) comment '������',
   os                   char(20) comment '����ϵͳ',
   weight               float,
   size                 char(30),
   pixel                char(20) comment '����',
   electric             char(20) comment '�������',
   screen               varchar(30) comment '��Ļ'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_digital comment '�ֻ�����';

alter table tb_goods_digital
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_electronic                                   */
/*==============================================================*/
create table tb_goods_electronic
(
   goods_id             bigint not null,
   series               char(20) comment 'ϵ��',
   color                char(20),
   suttle               char(20) comment '����',
   gross_weight         char(20) comment 'ë��',
   size                 char(30) comment '���ͳߴ�',
   power                varchar(30),
   electric             char(20) comment '��Դ',
   material             varchar(50) comment '����',
   capacity             char(20),
   process_ability      varchar(30) comment '��������'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_electronic comment '���õ���';

alter table tb_goods_electronic
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_food                                         */
/*==============================================================*/
create table tb_goods_food
(
   goods_id             bigint not null,
   weight               varchar(20) comment '����',
   count                int comment '����',
   guarantee_period     varchar(20) comment '������',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_food comment 'ʳƷ';

alter table tb_goods_food
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_furniture                                    */
/*==============================================================*/
create table tb_goods_furniture
(
   goods_id             bigint not null,
   size                 varchar(30) comment '�ߴ�',
   support_weight       varchar(20) comment '����',
   material             varchar(20) comment '��Ʒ����',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_furniture comment '�Ҿ�װ��';

alter table tb_goods_furniture
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_sports                                       */
/*==============================================================*/
create table tb_goods_sports
(
   goods_id             bigint not null,
   weight               varchar(30),
   material             varchar(30),
   color                varchar(20),
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_sports comment '�˶�����';

alter table tb_goods_sports
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_toy                                          */
/*==============================================================*/
create table tb_goods_toy
(
   goods_id             bigint not null,
   material             varchar(20),
   size                 varchar(30) comment '���',
   application_scope    varchar(30) comment '���÷�Χ',
   color                varchar(20) comment '��ɫ',
   weight               varchar(20) comment '����',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_toy comment '����ľ�';

alter table tb_goods_toy
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_watch                                        */
/*==============================================================*/
create table tb_goods_watch
(
   goods_id             bigint not null,
   matrial              varchar(20),
   warter_protect       tinyint comment '��ˮ',
   watch_band           varchar(20) comment '���',
   dial_plate           varchar(20) comment '����',
   watch_surface        varchar(20) comment '����',
   size                 varchar(30) comment '�ߴ�',
   mechanism            varchar(20) comment '��о',
   function             varchar(500),
   color                varchar(20),
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_watch comment '�ֱ���Ʒ';

alter table tb_goods_watch
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_nali_catalog                                       */
/*==============================================================*/
create table tb_nali_catalog
(
   id                   int not null comment '����id',
   level                int not null comment '���༶��',
   name                 varchar(200) not null comment '��������',
   parent_id            int comment '��������Id',
   create_time          date not null,
   last_modified_time   date not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_nali_catalog comment '�������ƷĿ¼��';

alter table tb_nali_catalog
   add primary key (id);
