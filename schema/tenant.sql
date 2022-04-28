-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: serenity
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `tb_tenant`
--

DROP TABLE IF EXISTS `tb_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tenant` (
  `id` varchar(36) NOT NULL,
  `name` varchar(64) NOT NULL,
  `type` int NOT NULL DEFAULT '1',
  `description` varchar(255) DEFAULT NULL,
  `jdbc_host` varchar(255) DEFAULT NULL,
  `jdbc_user` varchar(32) DEFAULT NULL,
  `jdbc_pass` varchar(32) DEFAULT NULL,
  `country_id` varchar(32) NOT NULL,
  `host_url` varchar(255) DEFAULT NULL,
  `ENABLE` bit(1) NOT NULL DEFAULT b'1',
  `reg_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mod_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_id_uindex` (`id`),
  UNIQUE KEY `tenant_name_country_uindex` (`name`,`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='tenant table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tenant`
--

LOCK TABLES `tb_tenant` WRITE;
/*!40000 ALTER TABLE `tb_tenant` DISABLE KEYS */;
INSERT INTO `tb_tenant` VALUES ('abb-oem-cn','ABB',1,'ABB China',NULL,NULL,NULL,'CN','http://47.98.59.87/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('abb-oem-de','ABB',1,'ABB Germany',NULL,NULL,NULL,'DE','http://ag0513.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('abb-oem-it','ABB',1,'ABB Italy',NULL,NULL,NULL,'IT','http://18.221.152.61/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39'),('bd-oem-mx','BD',1,'BD Mexico',NULL,NULL,NULL,'MX','http://3.18.124.254/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39'),('beiersdorf-oem-de','Beiersdorf',1,'Beiersdorf Germany',NULL,NULL,NULL,'DE','https://bg0824.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('boticario-oem-br','Boticario',1,'Boticario Brazil',NULL,NULL,NULL,'BR','https://bb0703.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('continental-oem-br','Continental',1,'Continental Brazil',NULL,NULL,NULL,'BR','https://cb0413.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('dana-oem-us','Dana',1,'Dana',NULL,NULL,NULL,'US','http://49.247.200.147:82/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('delonghi-oem-it','DeLonghi',1,'De\'Longhi Italy',NULL,NULL,NULL,'IT','https://di0402.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('denso-oem-it','Denso',1,'Denso Italy',NULL,NULL,NULL,'IT','https://fc0616.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('dyson-oem-sg','Dyson',1,'Dyson',NULL,NULL,NULL,'SG','https://ds0124.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('eaton-oem-pr','Eaton',1,'Eaton Puerto Rico',NULL,NULL,NULL,'PR','https://ep1111.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('electrolux-oem-br','Electrolux',1,'Electrolux Brazil',NULL,NULL,NULL,'BR','https://eb0702.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('emoldinoDemo-own-kr','eMoldinoDemo',0,'eMoldino-demo test server',NULL,NULL,NULL,'KR','https://demo.emoldino.com/',_binary '','2022-04-25 12:01:39','2022-04-25 12:01:39'),('emoldinoDev-own-kr','eMoldinoDev',0,'eMoldino-dev test server',NULL,NULL,NULL,'KR','https://dev.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53'),('emoldinoDevFeature-own-kr','eMoldinoDevFeature',0,'eMoldino-dev-feature test server',NULL,NULL,NULL,'KR','https://dev-feature.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53'),('emoldinoNew-own-kr','eMoldinoNew',0,'eMoldino-new sensor test server',NULL,NULL,NULL,'KR','https://new.emoldino.com/',_binary '','2022-04-25 11:59:53','2022-04-25 11:59:53'),('foehl-oem-cn','Foehl',1,'Foehl China',NULL,NULL,NULL,'CN','http://47.111.178.125/l',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39'),('icee-oem-us','ICEE',1,'ICEE',NULL,NULL,NULL,'US','http://49.247.200.147:81/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('loreal-oem-fr','Loreal',1,'L\'Oreal France',NULL,NULL,NULL,'FR','https://lf0408.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('mabe-oem-mx','Mabe',1,'Mabe Mexico',NULL,NULL,NULL,'MX','https://mm0427.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('megatechIndustries-oem-cz','MegatechIndustries',1,'Megatech-industries Cezch',NULL,NULL,NULL,'CZ','https://ml0421.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('nestle-oem-ch','Nestle',1,'Nestle Switerland',NULL,NULL,NULL,'CH','https://ns0407.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('philips-oem-id','Philips',1,'Philips Indonisia',NULL,NULL,NULL,'ID','http://18.189.137.58/',_binary '\0','2022-03-31 17:45:07','2022-03-31 17:45:39'),('philips-oem-ro','Philips',1,'Philips Romania',NULL,NULL,NULL,'RO','https://pr0202.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('png-oem-ch','png',1,'P&G Switzerland',NULL,NULL,NULL,'CH','https://ps0611.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('sbd-oem-in','SBD',1,'SBD India',NULL,NULL,NULL,'IN','https://su0901.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('schaffler-oem-de','Schaeffler',1,'Schaeffler Germany',NULL,NULL,NULL,'DE','http://sg0527.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('unilever-oem-gb','Unilever',1,'Unilever',NULL,NULL,NULL,'GB','http://49.247.200.147:85/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('volvo-oem-fr','Volvo',1,'Volvo France',NULL,NULL,NULL,'FR','https://vf0616.emoldino.com/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39'),('zebra-oem-cn','Zebra',1,'Zebra China',NULL,NULL,NULL,'CN','http://114.55.66.143/',_binary '','2022-03-31 17:45:07','2022-03-31 17:45:39');
/*!40000 ALTER TABLE `tb_tenant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28  9:42:22
