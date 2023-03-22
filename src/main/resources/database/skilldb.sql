-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: skillbase
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `action_1`
--

DROP TABLE IF EXISTS `action_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action_1` (
  `DAY` varchar(255) DEFAULT NULL,
  `TIME` varchar(255) DEFAULT NULL,
  `Action` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_1`
--

LOCK TABLES `action_1` WRITE;
/*!40000 ALTER TABLE `action_1` DISABLE KEYS */;
INSERT INTO `action_1` VALUES ('Monday','11','Monday we have Theoretical Computer Science at 11'),('Tuesday',NULL,NULL),('Tuesday',NULL,NULL),('Wednesday',NULL,'It\'s Project Time'),('Wednesday',NULL,'It\'s Project Time');
/*!40000 ALTER TABLE `action_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_2`
--

DROP TABLE IF EXISTS `action_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action_2` (
  `DAY` varchar(255) DEFAULT NULL,
  `Action` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_2`
--

LOCK TABLES `action_2` WRITE;
/*!40000 ALTER TABLE `action_2` DISABLE KEYS */;
INSERT INTO `action_2` VALUES ('Wednesday','The weather is extremely bad'),('Friday','The weather is insane'),(NULL,'I have no idea'),('Tuesday','rainy all day'),('Saturday','The weather is good'),('Monday','fuck monday'),('Thursday','The weather is bad'),('Sunday','The weather is average');
/*!40000 ALTER TABLE `action_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_3`
--

DROP TABLE IF EXISTS `action_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action_3` (
  `LOCATION` varchar(255) DEFAULT NULL,
  `COMPANY` varchar(255) DEFAULT NULL,
  `Action` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_3`
--

LOCK TABLES `action_3` WRITE;
/*!40000 ALTER TABLE `action_3` DISABLE KEYS */;
INSERT INTO `action_3` VALUES (NULL,NULL,'I have no idea'),('Maastricht','QPark','Qpark is located in Maastricht');
/*!40000 ALTER TABLE `action_3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot_1`
--

DROP TABLE IF EXISTS `slot_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot_1` (
  `SlotType` varchar(255) DEFAULT NULL,
  `SlotValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot_1`
--

LOCK TABLES `slot_1` WRITE;
/*!40000 ALTER TABLE `slot_1` DISABLE KEYS */;
INSERT INTO `slot_1` VALUES ('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday'),('TIME','11'),('TIME','13'),('DAY','Monday'),('DAY','Thursday'),('TIME','15'),('DAY','Friday'),('DAY','Wednesday'),('TIME','9'),('DAY','Tuesday'),('DAY','Saturday');
/*!40000 ALTER TABLE `slot_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot_2`
--

DROP TABLE IF EXISTS `slot_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot_2` (
  `SlotType` varchar(255) DEFAULT NULL,
  `SlotValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot_2`
--

LOCK TABLES `slot_2` WRITE;
/*!40000 ALTER TABLE `slot_2` DISABLE KEYS */;
INSERT INTO `slot_2` VALUES ('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday'),('DAY','Monday'),('DAY','Thursday'),('DAY','Friday'),('DAY','Sunday'),('DAY','Wednesday'),('DAY','Tuesday'),('DAY','Saturday');
/*!40000 ALTER TABLE `slot_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot_3`
--

DROP TABLE IF EXISTS `slot_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot_3` (
  `SlotType` varchar(255) DEFAULT NULL,
  `SlotValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot_3`
--

LOCK TABLES `slot_3` WRITE;
/*!40000 ALTER TABLE `slot_3` DISABLE KEYS */;
INSERT INTO `slot_3` VALUES ('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht'),('COMPANY','QPark'),('LOCATION','Maastricht');
/*!40000 ALTER TABLE `slot_3` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-16 12:40:19
