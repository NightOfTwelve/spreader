
alter table tb_app_udid add column (
   version              bigint,
   update_time			timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   ipad_serial          varchar(20),
   iphone_serial        varchar(20),
   imei			        varchar(20),
   imsi			        varchar(20),
   iccid		        varchar(20)
);