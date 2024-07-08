-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: student_registrarion
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `dob` date NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `hobby` varbinary(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `nrc` varchar(255) NOT NULL,
  `phonenumber` int DEFAULT NULL,
  `state` enum('AYEYARWADDY','CHIN','KACHIN','KAYAH','KAYIN','MAGWAY','MANDALAY','MON','NAYPYIDAW','RAKHINE','SAGAING','SHAN','TANINTHARYI','YANGON') DEFAULT NULL,
  `studentid` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `student_chk_1` CHECK ((`phonenumber` >= 9))
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Address 1 (MGMG)','2024-06-14 09:24:43.452665','2010-01-14','mgmg@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0	VideoGamex','MgMg','2/LKT(N)123456',987654321,'SHAN','#0001',NULL,NULL),(2,'Address 2(ZawZaw)','2024-06-14 09:38:51.347531','2014-06-19','zaw.zaw@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0Readingx','ZawZaq','1/BMT(N)876543',959698989,'SHAN','#0002',NULL,NULL),(3,'Address 3','2024-06-14 09:42:07.033912','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','MaSapal','3/BMT(N)987654',123456789,'RAKHINE','#0003',NULL,NULL),(4,'Address (MawMaw)','2024-06-14 09:44:37.632973','2017-02-14','maw@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Readingt\0Paintingx','MawMaw','5/SKT(N)098765',345678912,'SAGAING','#0004',NULL,NULL),(5,'Address 5','2024-06-14 09:46:38.421567','2015-06-15','nyi@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	VideoGamet\0Fishingx','NyiNyi','4/BNT(N)987654',987654321,'TANINTHARYI','#0005','2024-06-14 11:36:52.141783',NULL),(6,'Address 6','2024-06-14 09:48:16.618125','2018-03-14','r@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Readingt\0Fishingx','Rio','6/BLT(N)123456',98765432,'AYEYARWADDY','#0006',NULL,NULL),(8,'Address 7','2024-06-14 10:04:21.116915','2016-01-08','t@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0	VideoGamet\0Bakingx','TunTun','5/KLT(N)123456',987654321,'SAGAING','#0007',NULL,NULL),(9,'Address 8','2024-06-14 10:14:51.690131','2009-12-29','Y@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0Readingx','Yazar','5/KLT(N)123456',987654321,'SAGAING','#0008',NULL,NULL),(10,'Address','2024-06-14 11:18:01.692047','2019-02-14','yanlay@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Bakingt\0Fishingt\0Paintingx','Yanlay','5/KLT(n)123456',987654321,'KAYAH','#0009','2024-06-14 11:23:59.899826',NULL),(11,'Address 1 (MGMG)','2024-06-16 20:38:20.021929','2010-01-14','TayZar@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0	VideoGamex','TayZar','2/LKT(N)123456',987654321,'SHAN','#00010',NULL,NULL),(12,'Address 3','2024-06-16 21:45:51.998248','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','LaMin','3/BMT(N)987654',123456789,'RAKHINE','#00011',NULL,NULL),(14,'Address 3','2024-06-16 21:58:35.424865','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','Sapal','3/BMT(N)987654',123456789,'RAKHINE','#00012',NULL,NULL),(15,'Address 3','2024-06-16 22:04:37.546752','2019-02-07','ma.sapal@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','BoBo','3/BMT(N)987654',123456789,'RAKHINE','#00013',NULL,NULL),(16,'Address 3','2024-06-16 22:04:37.549751','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','AyeAye','3/BMT(N)987654',123456789,'RAKHINE','#00014',NULL,NULL),(17,'Address 3','2024-06-16 22:04:37.552749','2019-02-07','ma.sapal@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','KyawSyar','3/BMT(N)987654',123456789,'RAKHINE','#00015',NULL,NULL),(18,'Address 3','2024-06-16 22:04:37.560744','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','Lawoon','3/BMT(N)987654',123456789,'RAKHINE','#00016',NULL,NULL),(19,'Address 3','2024-06-16 22:04:37.563741','2019-02-07','ma.sapal@mail.com','FEMALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Paintingt\0Bakingx','Nadi','3/BMT(N)987654',123456789,'RAKHINE','#00017',NULL,NULL),(20,'Address 1 (MGMG)','2024-06-16 22:19:58.119328','2010-01-14','TayZar@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0	VideoGamex','NayNay','2/LKT(N)123456',987654321,'SHAN','#00018',NULL,NULL),(21,'Address 1 (MGMG)','2024-06-16 22:24:34.009632','2010-01-14','TayZar@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0	VideoGamex','SaiThuAung','2/LKT(N)123456',987654321,'SHAN','#00019',NULL,NULL),(22,'Address 20','2024-06-17 09:34:59.356784','2009-02-17','min@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0Bakingt\0	VideoGamet\0Fishingx','MinAung','5/KLT(N)123456',987654321,'SAGAING','#0020','2024-06-17 09:35:35.102471',NULL),(67,'Address 27 john','2024-06-21 16:23:44.709882','2004-02-16','joh.n@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Footballt\0Readingx','John','1/BMT(N)999888',959698989,'SHAN','#0028',NULL,NULL),(70,'d','2024-06-24 17:05:05.792850','2024-06-08','e@mail.com','MALE',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	VideoGamex','SampleStudent updated','5/1234',999988888,'YANGON','#0033','2024-06-25 09:16:45.100829','Image');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-27 14:42:56
