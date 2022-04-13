--
-- DELIMITER $$
-- DROP FUNCTION IF EXISTS everytalk.uid $$
-- CREATE FUNCTION everytalk.uid (id binary(16)) returns char(32)
-- BEGIN
--   DECLARE uidStr char(32);
--   SET uidStr = substr(hex(id), 1,32);
--   RETURN uidStr;
-- END $$
-- DELIMITER ;

-- #==== member =================================================================================================
SET FOREIGN_KEY_CHECKS=0;
drop table if exists tb_member ;
create table tb_member
(
    id char(36) not null primary key,
    te_id char(36) not null comment 'tenant id',
    mb_email_hash char(64) not null comment 'member sha256(email)',
    mb_mobile_hash char(64) not null comment 'member sha256(mobile)',
    mb_password varchar(255) not null comment 'member password',
    mb_name varchar(64) not null comment 'member name',
    mb_level int default 0 not null comment 'member level(0:guest, 1:user, 10:company, 11:company user, 30:agent, 31:agent user, 1000:admin',
    mb_point bigint default 0 not null comment 'member point value',
    mb_status int default 0 not null comment 'member email or mobile certification flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait for certification, 1:email-only, 2:mobile-only, 3:both)',
    reg_datetime datetime not null,
    mod_datetime datetime not null,
    unique key(mb_email_hash),
    unique key(mb_email_hash),
    key(mb_status),
    key(mb_level),
    key(reg_datetime),
    key(mod_datetime),
    foreign key (te_id) references tb_tenant (id) on delete cascade
);


-- #==== member_detail =================================================================================================

drop table if exists tb_member_detail ;
create table tb_member_detail
(
    mb_id char(36) default null comment 'member join id(uuid)',
    mb_mobile varchar(32) not null,
    mb_email varchar(64) not null,
    mb_homepage varchar(255) default null,
    mb_image varchar(255) default null,
    mb_gender char(1) default null,
    mb_married bit default 0 comment 'member IsMarried flag(0:not married, 1:married)',
    mb_birthday date default null,
    mb_adult bit default null,
    mb_zip char(8) default null,
    mb_addr1 varchar(255) default null,
    mb_addr2 varchar(255) default null,
    mb_addr3 varchar(255) default null,
    mb_addr_jibeon varchar(255) default null,
    mb_latitude double default null,
    mb_longitude double default null,
    mb_signature text,
    mb_recommend varchar(255) default null,
    mb_today_login datetime not null,
    mb_login_ip varchar(255) default null,
    mb_ip varchar(255) default null,
    mb_leave_date date default null,
    mb_intercept_date date default null,
    mb_email_certify datetime not null,
    mb_email_certify2 varchar(255) default null,
    mb_mobile_certify datetime not null,
    mb_mobile_certify2 varchar(255) default null,
    mb_memo text,
    mb_lost_certify varchar(255) NOT NULL,
    mb_mailling bit default null,
    mb_sms bit default null,
    mb_open bit default null,
    mb_open_date date default null,
    mb_greeting text,
    mb_memo_call varchar(255) default null,
    mb_memo_cnt int default null,
    mb_scrap_cnt int default null,
    mb_1 varchar(255) default null,
    mb_2 varchar(255) default null,
    mb_3 varchar(255) default null,
    mb_4 varchar(255) default null,
    mb_5 varchar(255) default null,
    mb_6 varchar(255) default null,
    mb_7 varchar(255) default null,
    mb_8 varchar(255) default null,
    mb_9 varchar(255) default null,
    mb_10 varchar(255) default null,
    foreign key (mb_id) references tb_member (id) on delete cascade,
    unique key(mb_id),
    unique key(mb_email),
    unique key(mb_mobile)

);

-- #=== admin member ======================================================

drop table if exists tb_admin ;
create table tb_admin
(
    id char(36) not null primary key comment '관리자 고유 ID' ,
    te_id char(36) not null comment 'tenant id',
    am_email_hash char(64) not null comment 'admin sha256(email)',
    am_password varchar(255) not null comment 'admin password',
    am_name varchar(64) not null comment 'admin name',
    am_mobile varchar(64) not null comment 'admin mobile',
    am_reg_no varchar(64) null comment '사번',
    am_otp varchar(32) null comment 'OTP 암호',
    am_level int default 1000 comment '레벨(1000:관리자)',
    am_status int default 0 comment '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:ceritified)',
    am_memo varchar(256) null comment '메모',
    am_registrant_id char(36) null comment '등록자 admin ID',
    am_modifier_id char(36) null comment '수정자 admin ID',
    am_detail varchar(255) default 'Bory Inc.' comment '관리자 상세정보',
    reg_datetime datetime not null comment '등록 일시',
    mod_datetime datetime not null comment '수정 일시',
    unique key(am_email_hash),
    key(reg_datetime),
    key(mod_datetime),
    key(am_status),
    key(am_level),
    foreign key (te_id) references tb_tenant (id) on delete cascade
) comment '관리자';

-- #=== agent member ======================================================

drop table if exists tb_agent ;
create table tb_agent
(
    id char(36) not null primary key comment '대행사 고유 ID',
    te_id char(36) not null comment 'tenant id',
    ag_email_hash char(64) not null comment 'agent sha256(email)',
    ag_mobile_hash char(64) not null comment 'agent sha256(email)',
    ag_password varchar(255) not null comment 'agent password',
    ag_otp varchar(32) null comment 'OTP 암호',
    ag_code varchar(32) not null,
    ag_status int default 0 not null comment '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:agent-otp-ceritified)',
    ag_level int default 100 not null comment '레벨 flag(100:agent user, 101:agent admin',
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    unique key(ag_email_hash),
    unique key(ag_mobile_hash),
    key(reg_datetime),
    key(mod_datetime),
    key(ag_status),
    key(ag_level),
    foreign key (te_id) references tb_tenant (id) on delete cascade
) comment '대행사';

drop table if exists tb_agent_detail ;
create table tb_agent_detail
(
    ag_id char(36) null comment '대행사 고유 ID (join id)',
    te_id char(36) not null comment 'tenant id',
    ag_mobile varchar(64) not null,
    ag_email varchar(64) not null,
    ag_contract_start date null,
    ag_contract_end date null,
    ag_phone varchar(64) null,
    ag_fax varchar(64) null,
    ag_zip char(8) default null,
    ag_addr1 varchar(255) default null,
    ag_addr2 varchar(255) default null,
    ag_latitude double default null,
    ag_longitude double default null,
    ag_homepage varchar(64) null comment '대행사 홈페이지',
    ag_charge_name varchar(64) null comment '과금 부과 기관 사용자 명',
    ag_charge_org varchar(64) null comment '과금 부과 기관',
    ag_charge_id char(36) null comment '과금 부과 member ID',
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    foreign key (ag_id) references tb_agent (id) on delete set null,
    foreign key (te_id) references tb_tenant (id) on delete cascade,
    unique key(ag_id),
    key(ag_charge_id),
    key(reg_datetime),
    key(mod_datetime)
) comment '대행사 상세';

-- #===== tb_company ==================================================
drop table if exists tb_company ;
create table tb_company
(
    id char(36) not null primary key comment '가입회사 고유 ID' ,
    te_id char(36) not null comment 'tenant id',
    ag_id char(36) default null  comment '가입회사 대행사 고유 ID',
    mb_id char(36) default null  comment '가입회사 등록 member 고유 ID',
    co_code varchar(32) not null comment '가입회사 코드',
    co_name varchar(64) not null comment '가입회사명',
    co_type int default 0 not null comment '0:개인, 1:법인, 2:협동조합...',
    co_reg_no varchar(32) not null comment '가입회사 사업자번호',
    co_contract_start date null,
    co_contract_end date null,
    co_status int default 0 not null comment '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)',
    co_prop varchar(8192) null comment '상세정보 JSON',
    co_phone varchar(64) null,
    co_fax varchar(64) null,
    co_zip char(8) default null,
    co_addr1 varchar(255) default null,
    co_addr2 varchar(255) default null,
    co_latitude double default null,
    co_longitude double default null,
    co_homepage varchar(64) null comment '대행사 홈페이지',
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    foreign key (ag_id) references tb_agent (id) on delete set null,
    foreign key (mb_id) references tb_member (id) on delete set null,
    foreign key (te_id) references tb_tenant (id) on delete cascade,
    key(reg_datetime),
    key(mod_datetime),
    key(co_status)
);


drop table if exists tb_company_member ;
create table tb_company_member
(
    id char(36) not null primary key comment '가입회사 사용자 고유 ID' ,
    te_id char(36) not null comment 'tenant id',
    mb_id char(36) null comment '사용자 ID',
    co_id char(36) default null  comment '가입회사 대행사 고유 ID',
    cm_otp varchar(32) null comment 'OTP 암호',
    cm_status int default 0 not null comment '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)',
    cm_level int default 10 not null comment '레벨 flag(0:guest, 10:co member, 11:co manager, 12:co admin)',
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    foreign key (co_id) references tb_company (id) on delete set null,
    foreign key (mb_id) references tb_member (id) on delete set null,
    foreign key (te_id) references tb_tenant (id) on delete cascade,
    unique key(co_id, mb_id),
    key(reg_datetime),
    key(mod_datetime),
    key(cm_status),
    key(cm_level)
);

-- #===== tb_board =====================================================
drop table if exists tb_board ;
CREATE TABLE tb_board (
    id char(36) not null primary key comment '게시판 고유 ID' ,
    te_id char(36) not null comment 'tenant id',
    bo_table VARCHAR(20) NOT NULL  comment '게시판 테이블명',
    gr_id char(36) NOT NULL comment '게시판 그룹 고유 ID',
    bo_subject VARCHAR(255) NOT NULL,
    bo_mobile_subject VARCHAR(255) NOT NULL,
    bo_device INT  NOT NULL DEFAULT 2 comment '0:pc, 1:mobile, 2:both',
    bo_admin VARCHAR(255) DEFAULT NULL,
    bo_list_level INT NOT NULL DEFAULT 1,
    bo_read_level INT NOT NULL DEFAULT 1,
    bo_write_level INT NOT NULL DEFAULT 1,
    bo_reply_level INT NOT NULL DEFAULT 1,
    bo_comment_level INT NOT NULL DEFAULT 1,
    bo_upload_level INT NOT NULL DEFAULT 1,
    bo_download_level INT NOT NULL DEFAULT 1,
    bo_html_level INT NOT NULL DEFAULT 1,
    bo_link_level INT NOT NULL DEFAULT 1,
    bo_count_delete INT NOT NULL DEFAULT 1,
    bo_count_modify INT NOT NULL DEFAULT 1,
    bo_read_point INT NOT NULL DEFAULT -1,
    bo_write_point INT NOT NULL DEFAULT 5,
    bo_comment_point INT NOT NULL DEFAULT 1,
    bo_download_point INT NOT NULL DEFAULT -20,
    bo_use_category INT NOT NULL DEFAULT 0,
    bo_category_list TEXT,
    bo_use_sideview INT NOT NULL DEFAULT 0,
    bo_use_file_content INT NOT NULL DEFAULT 0,
    bo_use_secret INT NOT NULL DEFAULT 0,
    bo_use_dhtml_editor INT NOT NULL DEFAULT 0,
    bo_select_editor VARCHAR(50) NULL,
    bo_use_rss_view INT NOT NULL DEFAULT 0,
    bo_use_good INT NOT NULL DEFAULT 0,
    bo_use_nogood INT NOT NULL DEFAULT 0,
    bo_use_name INT NOT NULL DEFAULT 0,
    bo_use_signature INT NOT NULL DEFAULT 0,
    bo_use_ip_view INT NOT NULL DEFAULT 0,
    bo_use_list_view INT NOT NULL DEFAULT 0,
    bo_use_list_file INT NOT NULL DEFAULT 0,
    bo_use_list_content INT NOT NULL DEFAULT 0,
    bo_table_width INT NOT NULL DEFAULT 128,
    bo_subject_len INT NOT NULL DEFAULT 70,
    bo_mobile_subject_len INT NOT NULL DEFAULT 30,
    bo_page_rows INT NOT NULL DEFAULT 15,
    bo_mobile_page_rows INT NOT NULL DEFAULT 15,
    bo_new INT NOT NULL DEFAULT 24,
    bo_hot INT NOT NULL DEFAULT 100,
    bo_image_width INT NOT NULL DEFAULT 835,
    bo_skin VARCHAR(255) NOT NULL DEFAULT 'basic',
    bo_mobile_skin VARCHAR(255) NOT NULL DEFAULT 'basic',
    bo_include_head VARCHAR(255) NULL,
    bo_include_tail VARCHAR(255) NULL,
    bo_content_head TEXT,
    bo_mobile_content_head TEXT,
    bo_content_tail TEXT,
    bo_mobile_content_tail TEXT,
    bo_insert_content TEXT,
    bo_gallery_cols INT NOT NULL DEFAULT 4,
    bo_gallery_width INT NOT NULL DEFAULT 128,
    bo_gallery_height INT NOT NULL DEFAULT 128,
    bo_mobile_gallery_width INT NOT NULL DEFAULT 100,
    bo_mobile_gallery_height INT NOT NULL DEFAULT 100,
    bo_upload_size INT NOT NULL DEFAULT 1048576,
    bo_reply_order INT NOT NULL DEFAULT 1,
    bo_use_search INT NOT NULL DEFAULT 0,
    bo_order INT NOT NULL DEFAULT 0,
    bo_count_write INT NOT NULL DEFAULT 0,
    bo_count_comment INT NOT NULL DEFAULT 0,
    bo_write_min INT NOT NULL DEFAULT 0,
    bo_write_max INT NOT NULL DEFAULT 0,
    bo_comment_min INT NOT NULL DEFAULT 0,
    bo_comment_max INT NOT NULL DEFAULT 0,
    bo_notice TEXT,
    bo_upload_count INT NOT NULL DEFAULT 0,
    bo_use_email INT NOT NULL DEFAULT 0,
    bo_use_cert INT NOT NULL DEFAULT 0 comment 'general:0, cert:1, adult:2, hp-cert:3, hp-adult:4',
    bo_use_sns INT NOT NULL DEFAULT 0,
    bo_use_captcha INT NOT NULL DEFAULT 0,
    bo_sort_field VARCHAR(255) NULL,
    bo_1_subj VARCHAR(255) NULL,
    bo_2_subj VARCHAR(255) NULL,
    bo_3_subj VARCHAR(255) NULL,
    bo_4_subj VARCHAR(255) NULL,
    bo_5_subj VARCHAR(255) NULL,
    bo_6_subj VARCHAR(255) NULL,
    bo_7_subj VARCHAR(255) NULL,
    bo_8_subj VARCHAR(255) NULL,
    bo_9_subj VARCHAR(255) NULL,
    bo_10_subj VARCHAR(255) NULL,
    bo_1 VARCHAR(255) NULL,
    bo_2 VARCHAR(255) NULL,
    bo_3 VARCHAR(255) NULL,
    bo_4 VARCHAR(255) NULL,
    bo_5 VARCHAR(255) NULL,
    bo_6 VARCHAR(255) NULL,
    bo_7 VARCHAR(255) NULL,
    bo_8 VARCHAR(255) NULL,
    bo_9 VARCHAR(255) NULL,
    bo_10 VARCHAR(255) NULL,
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    unique key(bo_table),
    key(reg_datetime),
    key(mod_datetime),
    key(te_id)
);


DROP TABLE IF EXISTS tb_board_file ;
CREATE TABLE tb_board_file (
    id char(36) not null primary key comment '게시판 첨부파일 고유 ID',
    te_id char(36) not null comment 'tenant id',
    bo_id char(36) not null comment '게시판 정보 테이블 Join ID',
    wr_id char(36) NOT NULL comment '게시판 작성(write) 글 고유 ID',
    bf_no INT NOT NULL DEFAULT 0 comment '게시판 1개 글에 첨부된 파일 순번',
    bf_source VARCHAR(64) NOT NULL comment '첨부파일 원래 이름',
    bf_fname VARCHAR(64) NOT NULL comment '첨부파일 upload 후 생성된 파일명',
    bf_path VARCHAR(255) NOT NULL comment '첨부파일 위치 or url',
    bf_download INT NOT NULL DEFAULT 0 comment '다운로드된 수',
    bf_content TEXT comment '메타 정보(필요한경우)',
    bf_filesize INT NOT NULL,
    bf_width INT NOT NULL,
    bf_height INT NOT NULL,
    bf_status INT NOT NULL DEFAULT 1 comment '0:deleted, 1:use',
    bf_ext VARCHAR(4) NOT NULL comment 'jpg, git, png, mp3, mp4, pdf...',
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    UNIQUE KEY (bo_id, wr_id, bf_no),
    key(reg_datetime),
    key(mod_datetime),
    key(te_id)
);

DROP TABLE IF EXISTS tb_board_good ;
CREATE TABLE tb_board_good (
    id char(36) NOT NULL PRIMARY KEY,
    te_id char(36) not null comment 'tenant id',
    bo_id char(36) NOT NULL,
    wr_id char(36) NOT NULL,
    mb_id char(36) NOT NULL,
    bg_flag VARCHAR(255) NULL,
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    UNIQUE KEY (bo_id, wr_id, mb_id),
    key(reg_datetime),
    key(mod_datetime),
    key(te_id)
);


DROP TABLE IF EXISTS tb_board_new ;
CREATE TABLE tb_board_new (
  id char(36) NOT NULL PRIMARY KEY,
  te_id char(36) not null comment 'tenant id',
  bo_id char(36) NOT NULL,
  wr_id char(36) NOT NULL,
  wr_parent char(36) NOT NULL,
  mb_id char(36) NULL,
  reg_datetime datetime not null comment '등록시각',
  mod_datetime datetime not null comment '변경시각',
  key(mb_id),
  key(reg_datetime),
  key(mod_datetime),
  key(te_id)
);

DROP TABLE IF EXISTS tb_board_write ;
CREATE TABLE tb_board_write (
    id char(36) NOT NULL PRIMARY KEY,
    te_id char(36) not null comment 'tenant id',
    bo_id char(36) NOT NULL,
    mb_id char(36) NOT NULL,
    wr_subject VARCHAR(255) NOT NULL,
    wr_name varchar(64) NOT NULL,
    wr_num INT NOT NULL DEFAULT 0,
    wr_reply VARCHAR(10) NOT NULL,
    wr_parent INT NOT NULL DEFAULT 0,
    wr_is_comment INT NOT NULL DEFAULT 0,
    wr_comment INT NOT NULL DEFAULT 0,
    wr_comment_reply VARCHAR(5) NOT NULL,
    wr_category VARCHAR(64) NOT NULL comment '게시판 내 서브 카테고리 명',
    wr_option INT NOT NULL DEFAULT 0 comment 'html1:0, html2:1, secret:2, mail:3',
    wr_hit INT NOT NULL DEFAULT 0,
    wr_good INT NOT NULL DEFAULT 0,
    wr_nogood INT NOT NULL DEFAULT 0,
    wr_file INT NOT NULL DEFAULT 0,
    reg_datetime datetime not null comment '등록시각',
    mod_datetime datetime not null comment '변경시각',
    KEY (bo_id, wr_num, wr_reply, wr_parent),
    key(reg_datetime),
    key(mod_datetime),
    key(te_id)
);

DROP TABLE IF EXISTS tb_board_write_detail ;
CREATE TABLE tb_board_write_detail (
    wr_id char(36) NULL,
    wr_content TEXT NOT NULL,
    wr_link1 TEXT NULL,
    wr_link2 TEXT NULL,
    wr_link1_hit INT NULL,
    wr_link2_hit INT NULL,
    wr_password VARCHAR(255) NULL,
    wr_email VARCHAR(255) NULL,
    wr_homepage VARCHAR(255) NULL,
    wr_ip VARCHAR(255) NULL,
    wr_facebook_user VARCHAR(255) NULL,
    wr_twitter_user VARCHAR(255) NULL,
    wr_1 VARCHAR(255) NULL,
    wr_2 VARCHAR(255) NULL,
    wr_3 VARCHAR(255) NULL,
    wr_4 VARCHAR(255) NULL,
    wr_5 VARCHAR(255) NULL,
    wr_6 VARCHAR(255) NULL,
    wr_7 VARCHAR(255) NULL,
    wr_8 VARCHAR(255) NULL,
    wr_9 VARCHAR(255) NULL,
    wr_10 VARCHAR(255) NULL,
    foreign key (wr_id) references tb_board_write (id) on delete cascade
);

-- #====== tb_group ================================================

DROP TABLE IF EXISTS tb_group ;
CREATE TABLE tb_group (
   id char(36) NOT NULL PRIMARY KEY,
   te_id char(36) not null comment 'tenant id',
   gr_name VARCHAR(36) NOT NULL,
   gr_subject VARCHAR(255) NOT NULL,
   gr_device INT  NOT NULL DEFAULT 2 comment '0:pc, 1:mobile, 2:both',
   gr_admin VARCHAR(255) NULL ,
   gr_use_access INT NOT NULL DEFAULT 0,
   gr_order INT NOT NULL DEFAULT 0,
   gr_1_subj VARCHAR(255) NULL,
   gr_2_subj VARCHAR(255) NULL,
   gr_3_subj VARCHAR(255) NULL,
   gr_4_subj VARCHAR(255) NULL,
   gr_5_subj VARCHAR(255) NULL,
   gr_6_subj VARCHAR(255) NULL,
   gr_7_subj VARCHAR(255) NULL,
   gr_8_subj VARCHAR(255) NULL,
   gr_9_subj VARCHAR(255) NULL,
   gr_10_subj VARCHAR(255) NULL,
   gr_1 VARCHAR(255) NULL,
   gr_2 VARCHAR(255) NULL,
   gr_3 VARCHAR(255) NULL,
   gr_4 VARCHAR(255) NULL,
   gr_5 VARCHAR(255) NULL,
   gr_6 VARCHAR(255) NULL,
   gr_7 VARCHAR(255) NULL,
   gr_8 VARCHAR(255) NULL,
   gr_9 VARCHAR(255) NULL,
   gr_10 VARCHAR(255) NULL,
   reg_datetime datetime not null comment '등록시각',
   mod_datetime datetime not null comment '변경시각',
   UNIQUE KEY (gr_name),
   key(reg_datetime),
   key(mod_datetime),
   key(te_id)
);


DROP TABLE IF EXISTS `tb_continent`;
CREATE TABLE IF NOT EXISTS `tb_continent` (
    `id` varchar(36) NOT NULL PRIMARY KEY,
    `name` varchar(255) NOT NULL
);

DROP TABLE IF EXISTS `tb_country`;
CREATE TABLE IF NOT EXISTS `tb_country` (
    `id` varchar(36) NOT NULL PRIMARY KEY ,
    `name` varchar(255) NOT NULL,
    `continent_id` varchar(32) NOT NULL
);

DROP TABLE IF EXISTS `tb_tenant`;
CREATE TABLE IF NOT EXISTS `tb_tenant` (
    `id` varchar(36) NOT NULL PRIMARY KEY,
    `name` varchar(64) NOT NULL,
    `type` int NOT NULL DEFAULT '1',
    `description` varchar(255) DEFAULT NULL,
    `jdbc_host` varchar(255) DEFAULT NULL,
    `jdbc_user` varchar(32) DEFAULT NULL,
    `jdbc_pass` varchar(32) DEFAULT NULL,
    `country_id` varchar(32) NOT NULL,
    `host_url` varchar(255) DEFAULT NULL,
    `enable` tinyint(1) NOT NULL DEFAULT '1',
    `reg_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `mod_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `tenant_id_uindex` (`id`),
    UNIQUE KEY `tenant_name_country_uindex` (`name`,`country_id`)
)
