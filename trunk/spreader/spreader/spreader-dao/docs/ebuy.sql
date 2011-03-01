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
   id                   int not null comment '品牌Id',
   brand_zh_name        varchar(30) not null comment '品牌的中文名',
   description          varchar(200) comment '品牌描述',
   site                 varchar(50) not null comment '品牌官方地址',
   brand_en_name        varchar(30) not null comment '品牌英文名，没有的用拼音替代',
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

alter table tb_brand_catalog comment '品牌分类关系表';

alter table tb_brand_catalog
   add primary key (id);

/*==============================================================*/
/* Table: tb_brand_option                                       */
/*==============================================================*/
create table tb_brand_option
(
   brand_id             int comment '品牌Id',
   alias                varchar(500)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_brand_option comment '品牌选项表，品牌别名等';

/*==============================================================*/
/* Table: tb_castalog_option                                    */
/*==============================================================*/
create table tb_catalog_option
(
   catalog_id           int not null,
   properties           varchar(600),
   alias                varchar(600)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_catalog_option comment '商品分类别名，特征属性描述，有后台运维手动添加';

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

alter table tb_crawler_logs comment '商品爬取日志记录';

alter table tb_crawler_logs
   add primary key (id);

/*==============================================================*/
/* Table: tb_emarket                                            */
/*==============================================================*/
create table tb_emarket
(
   id                   int not null,
   name                 varchar(20) not null comment '商城名称',
   description          varchar(200) not null,
   pay_channel          int not null comment '支持的支付方式',
   dispatch_channel     int not null comment '支持的运送方式',
   site                 varchar(50) not null comment '商城主页网址',
   freight_pay_policy   varchar(100) not null comment '运费策略',
   create_time          date
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_emarket comment '各商城信息';

alter table tb_emarket
   add primary key (id);

/*==============================================================*/
/* Table: tb_emarket_catalog                                    */
/*==============================================================*/
create table tb_emarket_catalog
(
   id                   int not null,
   level                int not null comment '分类的级别',
   name                 varchar(30) not null comment '父分类的名称',
   parent_id            int comment '父分类的Id',
   emarket_id           int not null,
   create_time          date not null,
   last_modified_time   date not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_emarket_catalog comment '其他网上商城的分类列表';

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
   list_date            date comment '上架时间',
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
   suttle               char(20) comment '净重',
   gross_weight         char(20) comment '毛重',
   standard_number      char(30) comment '产品标准号',
   hygiene_liscence     varchar(40) comment '卫生许可证',
   material             varchar(200) comment '材质/配料/原料',
   standard             varchar(30) comment '箱规/规格',
   color                char(20)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_baby comment '母婴用品';

alter table tb_goods_baby
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_bag                                          */
/*==============================================================*/
create table tb_goods_bag
(
   goods_id             bigint not null,
   material             varchar(20) comment '材料',
   color                varchar(10) comment '颜色',
   size                 varchar(30) comment '尺寸',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_bag comment '箱包产品特征属性表';

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
   material             char(50) comment '质地/成分'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_beauty comment '化妆用品';

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
   publication_time     timestamp comment '出版时间',
   edition              varchar(10) comment '版次',
   make_up              varchar(20) comment '装帧',
   pages                int comment '页数',
   format               varchar(20) comment '开本',
   introduction         varchar(2000) comment '内容简介'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_book comment '图书';

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
   size                 varchar(50) comment '尺寸',
   suttle               float,
   gross_weight         float comment '毛重',
   ram                  varchar(30) comment '内存',
   screen               varchar(50) comment '显示屏',
   battery              varchar(50) comment '电池',
   electric             varchar(50) comment '电源',
   material             varchar(30) comment '材质',
   standard             varchar(80) comment '规格',
   bar_code             varchar(100) comment '条形码',
   sphere               varchar(500) comment '适用范围',
   shelf_life           varchar(20) comment '保持期',
   power                varchar(20) comment '功率'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_car comment '汽车用品';

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
   pattern_number       char(50) comment '款号',
   washing              char(50) comment '洗涤',
   weight               float
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_clothing comment '服装鞋帽';

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
   create_time          timestamp not null comment '创建时间',
   last_modify_time     timestamp not null,
   model_number         varchar(80) comment '型号',
   image                varchar(250) comment 'imageUrl',
   firm                 varchar(100) comment '厂商',
   product_area         varchar(20) comment '产地',
   weight               varchar(30) comment '商品毛重'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_common comment '商品通用属性表';

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
   dist                 char(20) comment '硬盘',
   weight               float,
   size                 char(30),
   sphere               varchar(200),
   isbn                 char(20),
   name                 char(30),
   type                 varchar(20) comment '类型'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_computer comment '电脑办公';

alter table tb_goods_computer
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_digital                                      */
/*==============================================================*/
create table tb_goods_digital
(
   goods_id             bigint not null,
   color                char(20),
   design               char(10) comment '外观设计',
   os                   char(20) comment '操作系统',
   weight               float,
   size                 char(30),
   pixel                char(20) comment '像素',
   electric             char(20) comment '电池类型',
   screen               varchar(30) comment '屏幕'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_digital comment '手机数码';

alter table tb_goods_digital
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_electronic                                   */
/*==============================================================*/
create table tb_goods_electronic
(
   goods_id             bigint not null,
   series               char(20) comment '系列',
   color                char(20),
   suttle               char(20) comment '净重',
   gross_weight         char(20) comment '毛重',
   size                 char(30) comment '外型尺寸',
   power                varchar(30),
   electric             char(20) comment '电源',
   material             varchar(50) comment '材质',
   capacity             char(20),
   process_ability      varchar(30) comment '处理能力'
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_electronic comment '家用电器';

alter table tb_goods_electronic
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_food                                         */
/*==============================================================*/
create table tb_goods_food
(
   goods_id             bigint not null,
   weight               varchar(20) comment '质量',
   count                int comment '个数',
   guarantee_period     varchar(20) comment '保质期',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_food comment '食品';

alter table tb_goods_food
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_furniture                                    */
/*==============================================================*/
create table tb_goods_furniture
(
   goods_id             bigint not null,
   size                 varchar(30) comment '尺寸',
   support_weight       varchar(20) comment '承重',
   material             varchar(20) comment '商品材质',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_furniture comment '家具装饰';

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

alter table tb_goods_sports comment '运动户外';

alter table tb_goods_sports
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_toy                                          */
/*==============================================================*/
create table tb_goods_toy
(
   goods_id             bigint not null,
   material             varchar(20),
   size                 varchar(30) comment '规格',
   application_scope    varchar(30) comment '适用范围',
   color                varchar(20) comment '颜色',
   weight               varchar(20) comment '重量',
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_toy comment '玩具文具';

alter table tb_goods_toy
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_goods_watch                                        */
/*==============================================================*/
create table tb_goods_watch
(
   goods_id             bigint not null,
   matrial              varchar(20),
   warter_protect       tinyint comment '防水',
   watch_band           varchar(20) comment '表带',
   dial_plate           varchar(20) comment '表盘',
   watch_surface        varchar(20) comment '表面',
   size                 varchar(30) comment '尺寸',
   mechanism            varchar(20) comment '机芯',
   function             varchar(500),
   color                varchar(20),
   attributes           varchar(6000)
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_goods_watch comment '手表饰品';

alter table tb_goods_watch
   add primary key (goods_id);

/*==============================================================*/
/* Table: tb_nali_catalog                                       */
/*==============================================================*/
create table tb_nali_catalog
(
   id                   int not null comment '分类id',
   level                int not null comment '分类级别',
   name                 varchar(200) not null comment '分类名称',
   parent_id            int comment '父级分类Id',
   create_time          date not null,
   last_modified_time   date not null
) type = InnoDB DEFAULT Charset = utf8;

alter table tb_nali_catalog comment '那里的商品目录表';

alter table tb_nali_catalog
   add primary key (id);
