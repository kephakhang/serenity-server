SET FOREIGN_KEY_CHECKS=0;

alter table tb_admin modify column id varchar(36) not null;

alter table tb_agent modify column id varchar(36) not null;
alter table tb_agent_detail modify column ag_id varchar(36) not null;
alter table tb_agent_detail modify column ag_zip varchar(8) default null;


alter table tb_board modify column id varchar(36) not null;
alter table tb_board modify column gr_id varchar(36) not null;

alter table tb_board_file modify column id varchar(36) not null;
alter table tb_board_file modify column bo_id varchar(36) not null;
alter table tb_board_file modify column wr_id varchar(36) not null;

alter table tb_board_good modify column id varchar(36) not null;
alter table tb_board_good modify column bo_id varchar(36) not null;
alter table tb_board_good modify column wr_id varchar(36) not null;
alter table tb_board_good modify column mb_id varchar(36) not null;

alter table tb_board_new modify column id varchar(36) not null;
alter table tb_board_new modify column bo_id varchar(36) not null;
alter table tb_board_new modify column wr_id varchar(36) not null;
alter table tb_board_new modify column wr_parent varchar(36) not null;
alter table tb_board_new modify column mb_id varchar(36) not null;

alter table tb_board_write modify column id varchar(36) not null;
alter table tb_board_write modify column bo_id varchar(36) not null;
alter table tb_board_write modify column mb_id varchar(36) not null;

alter table tb_board_write_detail modify column id varchar(36) not null;

alter table tb_call modify column id varchar(36) not null;
alter table tb_call modify column ca_request_id varchar(36) not null;

alter table tb_company modify column id varchar(36) not null;
alter table tb_company modify column ag_id varchar(36) default null;
alter table tb_company modify column mb_id varchar(36) not null;
alter table tb_company modify column co_zip varchar(8) default null;

alter table tb_company_member modify column id varchar(36) not null;
alter table tb_company_member modify column mb_id varchar(36) not null;
alter table tb_company_member modify column co_id varchar(36) not null;

alter table tb_counter modify column id varchar(13) not null;
alter table tb_counter modify column te_id varchar(36) not null;

alter table tb_group modify column id varchar(36) not null;

alter table tb_member modify column id varchar(36) not null;
alter table tb_member modify column mb_email_hash varchar(64) not null;
alter table tb_member modify column mb_mobile_hash varchar(64) not null;
alter table tb_member modify column mb_zip varchar(8) default null;

alter table tb_terminal modify column id varchar(16) not null;
alter table tb_terminal modify column te_id varchar(36) not null;
alter table tb_terminal add column te_ip varchar(16) default null;

alter table tb_call add column ca_ip varchar(16) default null;

SET FOREIGN_KEY_CHECKS=1;