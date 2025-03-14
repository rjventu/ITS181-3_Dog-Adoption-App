-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: doggoforeverdb
-- ------------------------------------------------------
-- Server version	8.0.39

USE doggoforeverdb;

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
-- Table structure for table `adoption`
--

DROP TABLE IF EXISTS `adoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adoption` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `datetime` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `dog_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2e1f5ji66qofvm0qt2nwjdhn` (`dog_id`),
  KEY `FKtgw61inca2jefokjrthlq9bxa` (`user_id`),
  CONSTRAINT `FKh2e1f5ji66qofvm0qt2nwjdhn` FOREIGN KEY (`dog_id`) REFERENCES `dog` (`id`),
  CONSTRAINT `FKtgw61inca2jefokjrthlq9bxa` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoption`
--

LOCK TABLES `adoption` WRITE;
/*!40000 ALTER TABLE `adoption` DISABLE KEYS */;
INSERT INTO `adoption` VALUES (5,'2024-11-10 04:45:06.682699','Pending',1,2);
/*!40000 ALTER TABLE `adoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dog`
--

DROP TABLE IF EXISTS `dog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int NOT NULL,
  `description` text,
  `gender` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ster` bit(1) NOT NULL,
  `vacc` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dog`
--

LOCK TABLES `dog` WRITE;
/*!40000 ALTER TABLE `dog` DISABLE KEYS */;
INSERT INTO `dog` VALUES (1,5,'Angel is a gentle soul who brings warmth and joy to everyone.','Female','51046756-f287-4837-91bc-0e70e2faa5f2.jpg','Angel',_binary '\0',_binary ''),(2,3,'Big Whitey is a loyal and loving companion who always stays by your side.','Male','fae593ac-26db-447d-8871-ba4bec6eaa46.jpg','Big Whitey',_binary '',_binary '\0'),(3,2,'Bravo is a spirited and brave pup who’s full of life and excitement.','Male','d06c24c7-6381-40f0-bc80-8b08cfde6500.jpg','Bravo',_binary '\0',_binary '\0'),(4,3,'Sara is a quiet and affectionate friend who loves peaceful moments.','Female','7f84f1be-9cca-466c-bb80-8360cdfa8724.png','Sara',_binary '\0',_binary ''),(5,1,'Harry is curious and playful pup who’s always ready to explore.','Male','641a1511-6ecc-4666-a560-03953c990e96.jpg','Harry',_binary '',_binary '\0'),(6,2,'Charlotte is sweet and charming dog who wins hearts effortlessly.','Female','d3154194-e171-420e-a0d8-1e6ea9dd41fd.jpg','Charlotte',_binary '',_binary '\0');
/*!40000 ALTER TABLE `dog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'319 Cherry Avenue','09228765432','Jane Doe','$2a$10$Aqup902WonCzZq9XDfT81eKskANYquWqan01mhm9fRElcXzEdFKcC','ADMIN','admin@gmail.com'),(2,'100 Cecile Hall','09221234567','John Johnson','$2a$10$hMtGM/pdueM6bGii1cJcgefNPe45Ym2eip/lNnmVkul14VtEROVU6','USER','user@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-14 20:40:11
