-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: serenity
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_admin`
--
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_admin` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '관리자 고유 ID',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `am_email_hash` char(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'admin sha256(email)',
  `am_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'admin password',
  `am_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'admin name',
  `am_mobile` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'admin mobile',
  `am_reg_no` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '사번',
  `am_otp` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'OTP 암호',
  `am_level` int DEFAULT '1000' COMMENT '레벨(1000:관리자)',
  `am_status` int DEFAULT '0' COMMENT '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:ceritified)',
  `am_memo` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '메모',
  `am_registrant_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '등록자 admin ID',
  `am_modifier_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '수정자 admin ID',
  `am_detail` varchar(255) COLLATE utf8mb4_general_ci DEFAULT 'Bory Inc.' COMMENT '관리자 상세정보',
  `reg_datetime` datetime NOT NULL COMMENT '등록 일시',
  `mod_datetime` datetime NOT NULL COMMENT '수정 일시',
  PRIMARY KEY (`id`),
  UNIQUE KEY `am_email_hash` (`am_email_hash`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `am_status` (`am_status`),
  KEY `am_level` (`am_level`),
  KEY `te_id` (`te_id`),
  CONSTRAINT `tb_admin_ibfk_1` FOREIGN KEY (`te_id`) REFERENCES `tb_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='관리자';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_admin`
--


--
-- Table structure for table `tb_agent`
--



DROP TABLE IF EXISTS `tb_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_agent` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '대행사 고유 ID',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_email_hash` char(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'agent sha256(email)',
  `ag_mobile_hash` char(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'agent sha256(email)',
  `ag_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'agent password',
  `ag_otp` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'OTP 암호',
  `ag_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_status` int NOT NULL DEFAULT '0' COMMENT '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:agent-otp-ceritified)',
  `ag_level` int NOT NULL DEFAULT '100' COMMENT '레벨 flag(100:agent user, 101:agent admin',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ag_email_hash` (`ag_email_hash`),
  UNIQUE KEY `ag_mobile_hash` (`ag_mobile_hash`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `ag_status` (`ag_status`),
  KEY `ag_level` (`ag_level`),
  KEY `te_id` (`te_id`),
  CONSTRAINT `tb_agent_ibfk_1` FOREIGN KEY (`te_id`) REFERENCES `tb_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='대행사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_agent`
--


--
-- Table structure for table `tb_agent_detail`
--

DROP TABLE IF EXISTS `tb_agent_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_agent_detail` (
  `ag_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '대행사 고유 ID (join id)',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_mobile` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_email` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_contract_start` date DEFAULT NULL,
  `ag_contract_end` date DEFAULT NULL,
  `ag_phone` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ag_fax` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ag_zip` char(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ag_addr1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ag_addr2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ag_latitude` double DEFAULT NULL,
  `ag_longitude` double DEFAULT NULL,
  `ag_homepage` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '대행사 홈페이지',
  `ag_charge_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '과금 부과 기관 사용자 명',
  `ag_charge_org` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '과금 부과 기관',
  `ag_charge_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '과금 부과 member ID',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  UNIQUE KEY `ag_id` (`ag_id`),
  KEY `te_id` (`te_id`),
  KEY `ag_charge_id` (`ag_charge_id`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  CONSTRAINT `tb_agent_detail_ibfk_1` FOREIGN KEY (`ag_id`) REFERENCES `tb_agent` (`id`) ON DELETE SET NULL,
  CONSTRAINT `tb_agent_detail_ibfk_2` FOREIGN KEY (`te_id`) REFERENCES `tb_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='대행사 상세';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_agent_detail`
--


--
-- Table structure for table `tb_board`
--

DROP TABLE IF EXISTS `tb_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 고유 ID',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_table` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 테이블명',
  `gr_id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 그룹 고유 ID',
  `bo_subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_mobile_subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_device` int NOT NULL DEFAULT '2' COMMENT '0:pc, 1:mobile, 2:both',
  `bo_admin` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_list_level` int NOT NULL DEFAULT '1',
  `bo_read_level` int NOT NULL DEFAULT '1',
  `bo_write_level` int NOT NULL DEFAULT '1',
  `bo_reply_level` int NOT NULL DEFAULT '1',
  `bo_comment_level` int NOT NULL DEFAULT '1',
  `bo_upload_level` int NOT NULL DEFAULT '1',
  `bo_download_level` int NOT NULL DEFAULT '1',
  `bo_html_level` int NOT NULL DEFAULT '1',
  `bo_link_level` int NOT NULL DEFAULT '1',
  `bo_count_delete` int NOT NULL DEFAULT '1',
  `bo_count_modify` int NOT NULL DEFAULT '1',
  `bo_read_point` int NOT NULL DEFAULT '-1',
  `bo_write_point` int NOT NULL DEFAULT '5',
  `bo_comment_point` int NOT NULL DEFAULT '1',
  `bo_download_point` int NOT NULL DEFAULT '-20',
  `bo_use_category` int NOT NULL DEFAULT '0',
  `bo_category_list` text COLLATE utf8mb4_general_ci,
  `bo_use_sideview` int NOT NULL DEFAULT '0',
  `bo_use_file_content` int NOT NULL DEFAULT '0',
  `bo_use_secret` int NOT NULL DEFAULT '0',
  `bo_use_dhtml_editor` int NOT NULL DEFAULT '0',
  `bo_select_editor` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_use_rss_view` int NOT NULL DEFAULT '0',
  `bo_use_good` int NOT NULL DEFAULT '0',
  `bo_use_nogood` int NOT NULL DEFAULT '0',
  `bo_use_name` int NOT NULL DEFAULT '0',
  `bo_use_signature` int NOT NULL DEFAULT '0',
  `bo_use_ip_view` int NOT NULL DEFAULT '0',
  `bo_use_list_view` int NOT NULL DEFAULT '0',
  `bo_use_list_file` int NOT NULL DEFAULT '0',
  `bo_use_list_content` int NOT NULL DEFAULT '0',
  `bo_table_width` int NOT NULL DEFAULT '128',
  `bo_subject_len` int NOT NULL DEFAULT '70',
  `bo_mobile_subject_len` int NOT NULL DEFAULT '30',
  `bo_page_rows` int NOT NULL DEFAULT '15',
  `bo_mobile_page_rows` int NOT NULL DEFAULT '15',
  `bo_new` int NOT NULL DEFAULT '24',
  `bo_hot` int NOT NULL DEFAULT '100',
  `bo_image_width` int NOT NULL DEFAULT '835',
  `bo_skin` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'basic',
  `bo_mobile_skin` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'basic',
  `bo_include_head` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_include_tail` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_content_head` text COLLATE utf8mb4_general_ci,
  `bo_mobile_content_head` text COLLATE utf8mb4_general_ci,
  `bo_content_tail` text COLLATE utf8mb4_general_ci,
  `bo_mobile_content_tail` text COLLATE utf8mb4_general_ci,
  `bo_insert_content` text COLLATE utf8mb4_general_ci,
  `bo_gallery_cols` int NOT NULL DEFAULT '4',
  `bo_gallery_width` int NOT NULL DEFAULT '128',
  `bo_gallery_height` int NOT NULL DEFAULT '128',
  `bo_mobile_gallery_width` int NOT NULL DEFAULT '100',
  `bo_mobile_gallery_height` int NOT NULL DEFAULT '100',
  `bo_upload_size` int NOT NULL DEFAULT '1048576',
  `bo_reply_order` int NOT NULL DEFAULT '1',
  `bo_use_search` int NOT NULL DEFAULT '0',
  `bo_order` int NOT NULL DEFAULT '0',
  `bo_count_write` int NOT NULL DEFAULT '0',
  `bo_count_comment` int NOT NULL DEFAULT '0',
  `bo_write_min` int NOT NULL DEFAULT '0',
  `bo_write_max` int NOT NULL DEFAULT '0',
  `bo_comment_min` int NOT NULL DEFAULT '0',
  `bo_comment_max` int NOT NULL DEFAULT '0',
  `bo_notice` text COLLATE utf8mb4_general_ci,
  `bo_upload_count` int NOT NULL DEFAULT '0',
  `bo_use_email` int NOT NULL DEFAULT '0',
  `bo_use_cert` int NOT NULL DEFAULT '0' COMMENT 'general:0, cert:1, adult:2, hp-cert:3, hp-adult:4',
  `bo_use_sns` int NOT NULL DEFAULT '0',
  `bo_use_captcha` int NOT NULL DEFAULT '0',
  `bo_sort_field` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_1_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_2_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_3_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_4_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_5_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_6_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_7_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_8_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_9_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_10_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_6` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_7` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_8` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_9` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bo_10` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bo_table` (`bo_table`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board`
--


--
-- Table structure for table `tb_board_file`
--

DROP TABLE IF EXISTS `tb_board_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board_file` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 첨부파일 고유 ID',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 정보 테이블 Join ID',
  `wr_id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 작성(write) 글 고유 ID',
  `bf_no` int NOT NULL DEFAULT '0' COMMENT '게시판 1개 글에 첨부된 파일 순번',
  `bf_source` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '첨부파일 원래 이름',
  `bf_fname` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '첨부파일 upload 후 생성된 파일명',
  `bf_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '첨부파일 위치 or url',
  `bf_download` int NOT NULL DEFAULT '0' COMMENT '다운로드된 수',
  `bf_content` text COLLATE utf8mb4_general_ci COMMENT '메타 정보(필요한경우)',
  `bf_filesize` int NOT NULL,
  `bf_width` int NOT NULL,
  `bf_height` int NOT NULL,
  `bf_status` int NOT NULL DEFAULT '1' COMMENT '0:deleted, 1:use',
  `bf_ext` varchar(4) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'jpg, git, png, mp3, mp4, pdf...',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bo_id` (`bo_id`,`wr_id`,`bf_no`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board_file`
--


--
-- Table structure for table `tb_board_good`
--

DROP TABLE IF EXISTS `tb_board_good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board_good` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bg_flag` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bo_id` (`bo_id`,`wr_id`,`mb_id`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board_good`
--


--
-- Table structure for table `tb_board_new`
--

DROP TABLE IF EXISTS `tb_board_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board_new` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_parent` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  KEY `mb_id` (`mb_id`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board_new`
--


--
-- Table structure for table `tb_board_write`
--

DROP TABLE IF EXISTS `tb_board_write`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board_write` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `bo_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_num` int NOT NULL DEFAULT '0',
  `wr_reply` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_parent` int NOT NULL DEFAULT '0',
  `wr_is_comment` int NOT NULL DEFAULT '0',
  `wr_comment` int NOT NULL DEFAULT '0',
  `wr_comment_reply` varchar(5) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_category` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '게시판 내 서브 카테고리 명',
  `wr_option` int NOT NULL DEFAULT '0' COMMENT 'html1:0, html2:1, secret:2, mail:3',
  `wr_hit` int NOT NULL DEFAULT '0',
  `wr_good` int NOT NULL DEFAULT '0',
  `wr_nogood` int NOT NULL DEFAULT '0',
  `wr_file` int NOT NULL DEFAULT '0',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  KEY `bo_id` (`bo_id`,`wr_num`,`wr_reply`,`wr_parent`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board_write`
--


--
-- Table structure for table `tb_board_write_detail`
--

DROP TABLE IF EXISTS `tb_board_write_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_board_write_detail` (
  `wr_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `wr_content` text COLLATE utf8mb4_general_ci NOT NULL,
  `wr_link1` text COLLATE utf8mb4_general_ci,
  `wr_link2` text COLLATE utf8mb4_general_ci,
  `wr_link1_hit` int DEFAULT NULL,
  `wr_link2_hit` int DEFAULT NULL,
  `wr_password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_homepage` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_facebook_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_twitter_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_6` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_7` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_8` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_9` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wr_10` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  KEY `wr_id` (`wr_id`),
  CONSTRAINT `tb_board_write_detail_ibfk_1` FOREIGN KEY (`wr_id`) REFERENCES `tb_board_write` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board_write_detail`
--


--
-- Table structure for table `tb_call`
--

DROP TABLE IF EXISTS `tb_call`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_call` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `ca_request_id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `ca_uri` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `ca_request_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `ca_method` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `ca_request_body` text COLLATE utf8mb4_general_ci,
  `ca_response_body` text COLLATE utf8mb4_general_ci,
  `reg_datetime` datetime NOT NULL,
  `mod_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_call_mod_datetime_index` (`mod_datetime`),
  KEY `tb_call_request_id_uri_index` (`ca_request_id`,`ca_uri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='call log table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_call`
--


--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_company` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '가입회사 고유 ID',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `ag_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '가입회사 대행사 고유 ID',
  `mb_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '가입회사 등록 member 고유 ID',
  `co_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '가입회사 코드',
  `co_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '가입회사명',
  `co_type` int NOT NULL DEFAULT '0' COMMENT '0:개인, 1:법인, 2:협동조합...',
  `co_reg_no` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '가입회사 사업자번호',
  `co_contract_start` date DEFAULT NULL,
  `co_contract_end` date DEFAULT NULL,
  `co_status` int NOT NULL DEFAULT '0' COMMENT '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)',
  `co_prop` varchar(8192) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '상세정보 JSON',
  `co_phone` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `co_fax` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `co_zip` char(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `co_addr1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `co_addr2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `co_latitude` double DEFAULT NULL,
  `co_longitude` double DEFAULT NULL,
  `co_homepage` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '대행사 홈페이지',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  KEY `ag_id` (`ag_id`),
  KEY `mb_id` (`mb_id`),
  KEY `te_id` (`te_id`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `co_status` (`co_status`),
  CONSTRAINT `tb_company_ibfk_1` FOREIGN KEY (`ag_id`) REFERENCES `tb_agent` (`id`) ON DELETE SET NULL,
  CONSTRAINT `tb_company_ibfk_2` FOREIGN KEY (`mb_id`) REFERENCES `tb_member` (`id`) ON DELETE SET NULL,
  CONSTRAINT `tb_company_ibfk_3` FOREIGN KEY (`te_id`) REFERENCES `tb_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--


--
-- Table structure for table `tb_company_member`
--

DROP TABLE IF EXISTS `tb_company_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_company_member` (
  `id` char(36) NOT NULL COMMENT '가입회사 사용자 고유 ID',
  `te_id` varchar(36) NOT NULL,
  `mb_id` char(36) NOT NULL COMMENT '사용자 ID',
  `co_id` char(36) NOT NULL COMMENT '가입회사 고유 ID',
  `cm_otp` varchar(32) DEFAULT NULL COMMENT 'OTP 암호',
  `cm_status` int NOT NULL DEFAULT '0' COMMENT '상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)',
  `cm_level` int NOT NULL DEFAULT '10' COMMENT '레벨 flag(0:guest, 10:co member, 11:co manager, 12:co admin)',
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `co_mb_uidx` (`co_id`,`mb_id`),
  KEY `co_id` (`co_id`),
  KEY `mb_id` (`mb_id`),
  KEY `te_id` (`te_id`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `cm_status` (`cm_status`),
  KEY `cm_level` (`cm_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_member`
--


--
-- Table structure for table `tb_continent`
--

DROP TABLE IF EXISTS `tb_continent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_continent` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='continent table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_continent`
--

INSERT INTO `tb_continent` VALUES ('AF','Africa'),('AN','Antarctica'),('AS','Asia'),('EU','Europe'),('NA','North America'),('OC','Oceania'),('SA','South America');

--
-- Table structure for table `tb_counter`
--

DROP TABLE IF EXISTS `tb_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_counter` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Tenant Id',
  `tr_id` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Terminal Id',
  `co_version` int NOT NULL DEFAULT '3' COMMENT 'Counter Version',
  `co_status` int NOT NULL DEFAULT '0' COMMENT 'Counter Status',
  PRIMARY KEY (`id`),
  KEY `te_id` (`te_id`),
  KEY `tr_id` (`tr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='eMoldino IoT Counter device table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_counter`
--


--
-- Table structure for table `tb_country`
--

DROP TABLE IF EXISTS `tb_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_country` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `continent_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='country table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_country`
--

INSERT INTO `tb_country` VALUES ('AD','Andorra, Principality of','EU'),('AE','United Arab Emirates','AS'),('AF','Afghanistan, Islamic Republic of','AS'),('AG','Antigua and Barbuda','NA'),('AI','Anguilla','NA'),('AL','Albania, Republic of','EU'),('AM','Armenia, Republic of','EU'),('AN','Netherlands Antilles','NA'),('AO','Angola, Republic of','AF'),('AQ','Antarctica (the territory South of 60 deg S)','AN'),('AR','Argentina, Argentine Republic','SA'),('AS','American Samoa','OC'),('AT','Austria, Republic of','EU'),('AU','Australia, Commonwealth of','OC'),('AW','Aruba','NA'),('AX','?land Islands','EU'),('AZ','Azerbaijan, Republic of','EU'),('BA','Bosnia and Herzegovina','EU'),('BB','Barbados','NA'),('BD','Bangladesh, People\'s Republic of','AS'),('BE','Belgium, Kingdom of','EU'),('BF','Burkina Faso','AF'),('BG','Bulgaria, Republic of','EU'),('BH','Bahrain, Kingdom of','AS'),('BI','Burundi, Republic of','AF'),('BJ','Benin, Republic of','AF'),('BL','Saint Barthelemy','NA'),('BM','Bermuda','NA'),('BN','Brunei Darussalam','AS'),('BO','Bolivia, Republic of','SA'),('BQ','Bonaire, Sint Eustatius and Saba','NA'),('BR','Brazil, Federative Republic of','SA'),('BS','Bahamas, Commonwealth of the','NA'),('BT','Bhutan, Kingdom of','AS'),('BV','Bouvet Island (Bouvetoya)','AN'),('BW','Botswana, Republic of','AF'),('BY','Belarus, Republic of','EU'),('BZ','Belize','NA'),('CA','Canada','NA'),('CC','Cocos (Keeling) Islands','AS'),('CD','Congo, Democratic Republic of the','AF'),('CF','Central African Republic','AF'),('CG','Congo, Republic of the','AF'),('CH','Switzerland, Swiss Confederation','EU'),('CI','Cote d\'Ivoire, Republic of','AF'),('CK','Cook Islands','OC'),('CL','Chile, Republic of','SA'),('CM','Cameroon, Republic of','AF'),('CN','China, People\'s Republic of','AS'),('CO','Colombia, Republic of','SA'),('CR','Costa Rica, Republic of','NA'),('CU','Cuba, Republic of','NA'),('CV','Cape Verde, Republic of','AF'),('CW','Cura?ao','NA'),('CX','Christmas Island','AS'),('CY','Cyprus, Republic of','EU'),('CZ','Czech Republic','EU'),('DE','Germany, Federal Republic of','EU'),('DJ','Djibouti, Republic of','AF'),('DK','Denmark, Kingdom of','EU'),('DM','Dominica, Commonwealth of','NA'),('DO','Dominican Republic','NA'),('DZ','Algeria, People\'s Democratic Republic of','AF'),('EC','Ecuador, Republic of','SA'),('EE','Estonia, Republic of','EU'),('EG','Egypt, Arab Republic of','AF'),('EH','Western Sahara','AF'),('ER','Eritrea, State of','AF'),('ES','Spain, Kingdom of','EU'),('ET','Ethiopia, Federal Democratic Republic of','AF'),('FI','Finland, Republic of','EU'),('FJ','Fiji, Republic of the Fiji Islands','OC'),('FK','Falkland Islands (Malvinas)','SA'),('FM','Micronesia, Federated States of','OC'),('FO','Faroe Islands','EU'),('FR','France, French Republic','EU'),('GA','Gabon, Gabonese Republic','AF'),('GB','United Kingdom of Great Britain & Northern Ireland','EU'),('GD','Grenada','NA'),('GE','Georgia','EU'),('GF','French Guiana','SA'),('GG','Guernsey, Bailiwick of','EU'),('GH','Ghana, Republic of','AF'),('GI','Gibraltar','EU'),('GL','Greenland','NA'),('GM','Gambia, Republic of the','AF'),('GN','Guinea, Republic of','AF'),('GP','Guadeloupe','NA'),('GQ','Equatorial Guinea, Republic of','AF'),('GR','Greece, Hellenic Republic','EU'),('GS','South Georgia and the South Sandwich Islands','AN'),('GT','Guatemala, Republic of','NA'),('GU','Guam','OC'),('GW','Guinea-Bissau, Republic of','AF'),('GY','Guyana, Co-operative Republic of','SA'),('HK','Hong Kong, Special Administrative Region of China','AS'),('HM','Heard Island and McDonald Islands','AN'),('HN','Honduras, Republic of','NA'),('HR','Croatia, Republic of','EU'),('HT','Haiti, Republic of','NA'),('HU','Hungary, Republic of','EU'),('ID','Indonesia, Republic of','AS'),('IE','Ireland','EU'),('IL','Israel, State of','AS'),('IM','Isle of Man','EU'),('IN','India, Republic of','AS'),('IO','British Indian Ocean Territory (Chagos Archipelago)','AS'),('IQ','Iraq, Republic of','AS'),('IR','Iran, Islamic Republic of','AS'),('IS','Iceland, Republic of','EU'),('IT','Italy, Italian Republic','EU'),('JE','Jersey, Bailiwick of','EU'),('JM','Jamaica','NA'),('JO','Jordan, Hashemite Kingdom of','AS'),('JP','Japan','AS'),('KE','Kenya, Republic of','AF'),('KG','Kyrgyz Republic','AS'),('KH','Cambodia, Kingdom of','AS'),('KI','Kiribati, Republic of','OC'),('KM','Comoros, Union of the','AF'),('KN','Saint Kitts and Nevis, Federation of','NA'),('KP','Korea, Democratic People\'s Republic of','AS'),('KR','Korea, Republic of','AS'),('KW','Kuwait, State of','AS'),('KY','Cayman Islands','NA'),('KZ','Kazakhstan, Republic of','EU'),('LA','Lao People\'s Democratic Republic','AS'),('LB','Lebanon, Lebanese Republic','AS'),('LC','Saint Lucia','NA'),('LI','Liechtenstein, Principality of','EU'),('LK','Sri Lanka, Democratic Socialist Republic of','AS'),('LR','Liberia, Republic of','AF'),('LS','Lesotho, Kingdom of','AF'),('LT','Lithuania, Republic of','EU'),('LU','Luxembourg, Grand Duchy of','EU'),('LV','Latvia, Republic of','EU'),('LY','Libyan Arab Jamahiriya','AF'),('MA','Morocco, Kingdom of','AF'),('MC','Monaco, Principality of','EU'),('MD','Moldova, Republic of','EU'),('ME','Montenegro, Republic of','EU'),('MF','Saint Martin','NA'),('MG','Madagascar, Republic of','AF'),('MH','Marshall Islands, Republic of the','OC'),('MK','Macedonia, The Former Yugoslav Republic of','EU'),('ML','Mali, Republic of','AF'),('MM','Myanmar, Union of','AS'),('MN','Mongolia','AS'),('MO','Macao, Special Administrative Region of China','AS'),('MP','Northern Mariana Islands, Commonwealth of the','OC'),('MQ','Martinique','NA'),('MR','Mauritania, Islamic Republic of','AF'),('MS','Montserrat','NA'),('MT','Malta, Republic of','EU'),('MU','Mauritius, Republic of','AF'),('MV','Maldives, Republic of','AS'),('MW','Malawi, Republic of','AF'),('MX','Mexico, United Mexican States','NA'),('MY','Malaysia','AS'),('MZ','Mozambique, Republic of','AF'),('NA','Namibia, Republic of','AF'),('NC','New Caledonia','OC'),('NE','Niger, Republic of','AF'),('NF','Norfolk Island','OC'),('NG','Nigeria, Federal Republic of','AF'),('NI','Nicaragua, Republic of','NA'),('NL','Netherlands, Kingdom of the','EU'),('NO','Norway, Kingdom of','EU'),('NP','Nepal, State of','AS'),('NR','Nauru, Republic of','OC'),('NU','Niue','OC'),('NZ','New Zealand','OC'),('OM','Oman, Sultanate of','AS'),('PA','Panama, Republic of','NA'),('PE','Peru, Republic of','SA'),('PF','French Polynesia','OC'),('PG','Papua New Guinea, Independent State of','OC'),('PH','Philippines, Republic of the','AS'),('PK','Pakistan, Islamic Republic of','AS'),('PL','Poland, Republic of','EU'),('PM','Saint Pierre and Miquelon','NA'),('PN','Pitcairn Islands','OC'),('PR','Puerto Rico, Commonwealth of','NA'),('PS','Palestinian Territory, Occupied','AS'),('PT','Portugal, Portuguese Republic','EU'),('PW','Palau, Republic of','OC'),('PY','Paraguay, Republic of','SA'),('QA','Qatar, State of','AS'),('RE','Reunion','AF'),('RO','Romania','EU'),('RS','Serbia, Republic of','EU'),('RU','Russian Federation','EU'),('RW','Rwanda, Republic of','AF'),('SA','Saudi Arabia, Kingdom of','AS'),('SB','Solomon Islands','OC'),('SC','Seychelles, Republic of','AF'),('SD','Sudan, Republic of','AF'),('SE','Sweden, Kingdom of','EU'),('SG','Singapore, Republic of','AS'),('SH','Saint Helena','AF'),('SI','Slovenia, Republic of','EU'),('SJ','Svalbard & Jan Mayen Islands','EU'),('SK','Slovakia (Slovak Republic)','EU'),('SL','Sierra Leone, Republic of','AF'),('SM','San Marino, Republic of','EU'),('SN','Senegal, Republic of','AF'),('SO','Somalia, Somali Republic','AF'),('SR','Suriname, Republic of','SA'),('SS','South Sudan','AF'),('ST','Sao Tome and Principe, Democratic Republic of','AF'),('SV','El Salvador, Republic of','NA'),('SX','Sint Maarten (Netherlands)','NA'),('SY','Syrian Arab Republic','AS'),('SZ','Swaziland, Kingdom of','AF'),('TC','Turks and Caicos Islands','NA'),('TD','Chad, Republic of','AF'),('TF','French Southern Territories','AN'),('TG','Togo, Togolese Republic','AF'),('TH','Thailand, Kingdom of','AS'),('TJ','Tajikistan, Republic of','AS'),('TK','Tokelau','OC'),('TL','Timor-Leste, Democratic Republic of','AS'),('TM','Turkmenistan','AS'),('TN','Tunisia, Tunisian Republic','AF'),('TO','Tonga, Kingdom of','OC'),('TR','Turkey, Republic of','EU'),('TT','Trinidad and Tobago, Republic of','NA'),('TV','Tuvalu','OC'),('TW','Taiwan','AS'),('TZ','Tanzania, United Republic of','AF'),('UA','Ukraine','EU'),('UG','Uganda, Republic of','AF'),('UM','United States Minor Outlying Islands','OC'),('US','United States of America','NA'),('UY','Uruguay, Eastern Republic of','SA'),('UZ','Uzbekistan, Republic of','AS'),('VA','Holy See (Vatican City State)','EU'),('VC','Saint Vincent and the Grenadines','NA'),('VE','Venezuela, Bolivarian Republic of','SA'),('VG','British Virgin Islands','NA'),('VI','United States Virgin Islands','NA'),('VN','Vietnam, Socialist Republic of','AS'),('VU','Vanuatu, Republic of','OC'),('WF','Wallis and Futuna','OC'),('WS','Samoa, Independent State of','OC'),('YE','Yemen','AS'),('YT','Mayotte','AF'),('ZA','South Africa, Republic of','AF'),('ZM','Zambia, Republic of','AF'),('ZW','Zimbabwe, Republic of','AF');

--
-- Table structure for table `tb_group`
--

DROP TABLE IF EXISTS `tb_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `gr_name` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `gr_subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `gr_device` int NOT NULL DEFAULT '2' COMMENT '0:pc, 1:mobile, 2:both',
  `gr_admin` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_use_access` int NOT NULL DEFAULT '0',
  `gr_order` int NOT NULL DEFAULT '0',
  `gr_1_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_2_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_3_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_4_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_5_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_6_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_7_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_8_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_9_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_10_subj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_6` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_7` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_8` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_9` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gr_10` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reg_datetime` datetime NOT NULL COMMENT '등록시각',
  `mod_datetime` datetime NOT NULL COMMENT '변경시각',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gr_name` (`gr_name`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group`
--


--
-- Table structure for table `tb_member`
--

DROP TABLE IF EXISTS `tb_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_member` (
  `id` char(36) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_email_hash` char(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'member sha256(email)',
  `mb_mobile_hash` char(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'member sha256(mobile)',
  `mb_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'member password',
  `mb_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'member name',
  `mb_level` int NOT NULL DEFAULT '0' COMMENT 'member level(0:guest, 1:user, 10:company, 11:company user, 30:agent, 31:agent user, 1000:admin',
  `mb_point` bigint NOT NULL DEFAULT '0' COMMENT 'member point value',
  `mb_status` int NOT NULL DEFAULT '0' COMMENT 'member email or mobile certification flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait for certification, 1:email-only, 2:mobile-only, 3:both)',
  `reg_datetime` datetime NOT NULL,
  `mod_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mb_email_hash` (`mb_email_hash`),
  UNIQUE KEY `mb_email_hash_2` (`mb_email_hash`),
  KEY `mb_status` (`mb_status`),
  KEY `mb_level` (`mb_level`),
  KEY `reg_datetime` (`reg_datetime`),
  KEY `mod_datetime` (`mod_datetime`),
  KEY `te_id` (`te_id`),
  CONSTRAINT `tb_member_ibfk_1` FOREIGN KEY (`te_id`) REFERENCES `tb_tenant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member`
--

INSERT INTO `tb_member` VALUES ('b6f7a14f-3131-4957-93c4-6462f328de9f','emoldino-own-kr','bb0eed9f1269b20c9150d9f195ecd290f5b13f09dce95dc7aaf604e5f30e89e2','b57944c183598bb995c674271cf3cf7b71835ef6eb91f1b09208582de5d06f64','$2a$10$r7DmEv5V95EkfVEDDVq/pOncZDZ9NStbKt/fz2CPldiKM2AScWrcu','Kepha Khang',1000,0,0,'2022-05-13 05:14:35','2022-05-13 05:14:35');

--
-- Table structure for table `tb_member_detail`
--

DROP TABLE IF EXISTS `tb_member_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_member_detail` (
  `mb_id` char(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'member join id(uuid)',
  `te_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_mobile` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_email` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `mb_homepage` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_gender` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_married` bit(1) DEFAULT b'0' COMMENT 'member IsMarried flag(0:not married, 1:married)',
  `mb_birthday` date DEFAULT NULL,
  `mb_adult` bit(1) DEFAULT NULL,
  `mb_zip` char(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_addr1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_addr2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_addr3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_addr_jibeon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_latitude` double DEFAULT NULL,
  `mb_longitude` double DEFAULT NULL,
  `mb_signature` text COLLATE utf8mb4_general_ci,
  `mb_recommend` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_today_login` datetime NOT NULL,
  `mb_login_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_leave_date` date DEFAULT NULL,
  `mb_intercept_date` date DEFAULT NULL,
  `mb_email_certify` datetime DEFAULT NULL,
  `mb_email_certify2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_mobile_certify` datetime DEFAULT NULL,
  `mb_mobile_certify2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_memo` text COLLATE utf8mb4_general_ci,
  `mb_lost_certify` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_mailling` bit(1) DEFAULT NULL,
  `mb_sms` bit(1) DEFAULT NULL,
  `mb_open` bit(1) DEFAULT NULL,
  `mb_open_date` date DEFAULT NULL,
  `mb_greeting` text COLLATE utf8mb4_general_ci,
  `mb_memo_call` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_memo_cnt` int DEFAULT NULL,
  `mb_scrap_cnt` int DEFAULT NULL,
  `mb_1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_6` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_7` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_8` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_9` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mb_10` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  UNIQUE KEY `mb_email` (`mb_email`),
  UNIQUE KEY `mb_mobile` (`mb_mobile`),
  UNIQUE KEY `mb_id` (`mb_id`),
  CONSTRAINT `tb_member_detail_ibfk_1` FOREIGN KEY (`mb_id`) REFERENCES `tb_member` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member_detail`
--

INSERT INTO `tb_member_detail` VALUES ('b6f7a14f-3131-4957-93c4-6462f328de9f','','01099902251','kepha.khang@emoldino.com',NULL,NULL,NULL,_binary '\0',NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-05-12 20:14:35',NULL,NULL,NULL,NULL,'2022-05-12 20:14:35','1783641',NULL,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

--
-- Table structure for table `tb_tenant`
--

DROP TABLE IF EXISTS `tb_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tenant` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `type` int NOT NULL DEFAULT '1',
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jdbc_host` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jdbc_user` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jdbc_pass` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `country_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `host_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `enable` bit(1) NOT NULL DEFAULT b'1',
  `reg_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mod_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `prefix` varchar(12) COLLATE utf8mb4_general_ci NOT NULL,
  `hostname` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Auto generated hostname for each OEM',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_name_country_uindex` (`name`,`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='tenant table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tenant`
--

INSERT INTO `tb_tenant` VALUES ('abb-oem-cn','ABB',1,'ABB China',NULL,NULL,NULL,'CN','http://47.98.59.87/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('abb-oem-de','ABB',1,'ABB Germany',NULL,NULL,NULL,'DE','http://ag0513.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ag0513'),('abb-oem-it','ABB',1,'ABB Italy',NULL,NULL,NULL,'IT','http://18.221.152.61/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('bd-oem-mx','BD',1,'BD Mexico',NULL,NULL,NULL,'MX','http://3.18.124.254/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('beiersdorf-oem-de','Beiersdorf',1,'Beiersdorf Germany',NULL,NULL,NULL,'DE','https://bg0824.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','bg0824'),('boticario-oem-br','Boticario',1,'Boticario Brazil',NULL,NULL,NULL,'BR','https://bb0703.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','bb0703'),('continental-oem-br','Continental',1,'Continental Brazil',NULL,NULL,NULL,'BR','https://cb0413.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','cb0413'),('dana-oem-us','Dana',1,'Dana',NULL,NULL,NULL,'US','http://49.247.200.147:82/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('delonghi-oem-it','DeLonghi',1,'De\'Longhi Italy',NULL,NULL,NULL,'IT','https://di0402.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','di0402'),('denso-oem-it','Denso',1,'Denso Italy',NULL,NULL,NULL,'IT','https://fc0616.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','fc0616'),('dyson-oem-sg','Dyson',1,'Dyson',NULL,NULL,NULL,'SG','https://ds0124.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ds0124'),('eaton-oem-pr','Eaton',1,'Eaton Puerto Rico',NULL,NULL,NULL,'PR','https://ep1111.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ep1111'),('electrolux-oem-br','Electrolux',1,'Electrolux Brazil',NULL,NULL,NULL,'BR','https://eb0702.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','eb0702'),('em-local','eMoldino-localhost',1,'eMoldino localhost',NULL,NULL,NULL,'KR','http://localhost:8080/',_binary '','2022-05-26 09:39:53','2022-05-26 09:39:53','',''),('emoldino-own-kr','eMoldino',0,'eMoldino Members',NULL,NULL,NULL,'KR','https://serentity.emoldino.com',_binary '','2022-05-11 20:42:38','2022-05-11 20:42:38','','serent'),('emoldinoDemo-own-kr','eMoldinoDemo',0,'eMoldino-demo test server',NULL,NULL,NULL,'KR','https://demo.emoldino.com/',_binary '','2022-04-25 12:01:39','2022-04-25 12:01:39','','demo.e'),('emoldinoDev-own-kr','eMoldinoDev',0,'eMoldino-dev test server',NULL,NULL,NULL,'KR','https://dev.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53','','dev.em'),('emoldinoDevFeature-own-kr','eMoldinoDevFeature',0,'eMoldino-dev-feature test server',NULL,NULL,NULL,'KR','https://dev-feature.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53','','dev-fe'),('emoldinoNew-own-kr','eMoldinoNew',0,'eMoldino-new sensor test server',NULL,NULL,NULL,'KR','https://new.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53','','new.em'),('foehl-oem-cn','Foehl',1,'Foehl China',NULL,NULL,NULL,'CN','http://47.111.178.125/l',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('icee-oem-us','ICEE',1,'ICEE',NULL,NULL,NULL,'US','http://49.247.200.147:81/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('loreal-oem-fr','Loreal',1,'L\'Oreal France',NULL,NULL,NULL,'FR','https://lf0408.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','lf0408'),('mabe-oem-mx','Mabe',1,'Mabe Mexico',NULL,NULL,NULL,'MX','https://mm0427.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','mm0427'),('megatechIndustries-oem-cz','MegatechIndustries',1,'Megatech-industries Cezch',NULL,NULL,NULL,'CZ','https://ml0421.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ml0421'),('nestle-oem-ch','Nestle',1,'Nestle Switerland',NULL,NULL,NULL,'CH','https://ns0407.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ns0407'),('philips-oem-id','Philips',1,'Philips Indonisia',NULL,NULL,NULL,'ID','http://18.189.137.58/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('philips-oem-ro','Philips',1,'Philips Romania',NULL,NULL,NULL,'RO','https://pr0202.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','pr0202'),('png-oem-ch','png',1,'P&G Switzerland',NULL,NULL,NULL,'CH','https://ps0611.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','ps0611'),('sbd-oem-in','SBD',1,'SBD India',NULL,NULL,NULL,'IN','https://su0901.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','su0901'),('schaffler-oem-de','Schaeffler',1,'Schaeffler Germany',NULL,NULL,NULL,'DE','http://sg0527.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','sg0527'),('unilever-oem-gb','Unilever',1,'Unilever',NULL,NULL,NULL,'GB','http://49.247.200.147:85/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','',''),('volvo-oem-fr','Volvo',1,'Volvo France',NULL,NULL,NULL,'FR','https://vf0616.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','vf0616'),('zebra-oem-cn','Zebra',1,'Zebra China',NULL,NULL,NULL,'CN','http://114.55.66.143/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39','','');

--
-- Table structure for table `tb_terminal`
--

DROP TABLE IF EXISTS `tb_terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_terminal` (
  `id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `te_id` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Tenant Id',
  `tr_version` int NOT NULL DEFAULT '3' COMMENT 'Terminal Version',
  `tr_status` int NOT NULL DEFAULT '0' COMMENT 'Terminal Status',
  PRIMARY KEY (`id`),
  KEY `te_id` (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='eMoldino IoT gateway terminal table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_terminal`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-15 10:13:54
SET FOREIGN_KEY_CHECKS=1;