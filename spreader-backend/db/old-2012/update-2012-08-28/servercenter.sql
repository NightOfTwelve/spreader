/**
 * maxid = select max(id) from tb_content
 */
INSERT INTO tb_identity (app_id,app_name,id) VALUES (2,'spreader.content',maxid+10000);
/**
 * 清理数据
 */
delete from spreader.tb_content where website_id = 1 and type = 1 and (entry is null or website_id is null or website_uid is null);