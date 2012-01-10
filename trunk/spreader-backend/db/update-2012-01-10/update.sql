/**
 * 增加分组表，分组类型的默认值为1，简单分组
 */
alter table tb_strategy_group drop column group_type;
alter table tb_strategy_group  add column group_type int default 1;