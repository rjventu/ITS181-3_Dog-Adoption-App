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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dog`
--

LOCK TABLES `dog` WRITE;
/*!40000 ALTER TABLE `dog` DISABLE KEYS */;
INSERT INTO `dog` VALUES (1,5,'Bond is a friendly and affectionate Great Pyrenees, known for his calm demeanor and loyalty. He loves being around his family and is especially protective. While he enjoys outdoor time, he\'s equally content snuggling with his loved ones. Bond is ideal for a family or individual who appreciates a loyal, gentle companion with a need for both affection and a little space to roam. If you’re looking for a loving, dependable dog, Bond may be the perfect match!','Male','7596eb8c-9f42-4614-abc9-a18b38492d6f.jpg','Bond',_binary '',_binary '\0'),(2,4,'Nala is a cheerful and affectionate female Golden Retriever with a heart full of love. Known for her friendly nature, she enjoys spending time with her family and is always up for a game or a cuddle. Nala’s playful spirit makes her great with children and other pets, and her gentle demeanor ensures she’s a perfect companion for any home. Whether it\'s playing outdoors or snuggling up for some downtime, Nala will bring joy and love to your family. If you\'re looking for a sweet, energetic, and loyal dog, Nala could be the one for you!','Female','fccf80d5-1e6b-49e9-b75d-23184785974b.png','Nala Stomps',_binary '',_binary ''),(3,3,'Bear is a lovable, brown male Newfoundland with a calm and easygoing nature. Known for their gentle giant temperament, Newfoundlands are incredibly loyal and protective, and Bear is no exception. He enjoys being with his family, whether it’s lounging around or going on leisurely walks. His friendly and patient demeanor makes him great with kids and other pets. Bear’s majestic size is matched by a heart full of affection, and he thrives in a home where he can receive plenty of love and attention. If you’re looking for a devoted and loving companion, Bear may be the perfect fit for your family!','Male','6ab85868-ba42-4296-a907-92a3a4365891.jpg','Bear',_binary '',_binary '\0');
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
  `img` varchar(255) DEFAULT NULL,
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
INSERT INTO `user` VALUES (1,'319 Cherry Avenue','09221234567','e584e814-76f6-4590-a1d6-2c6920a13524.png','Beth Smith','$2a$10$Aqup902WonCzZq9XDfT81eKskANYquWqan01mhm9fRElcXzEdFKcC','ADMIN','admin@gmail.com'),(2,'100 Cecile Hall','09221234567','d69d8a62-aba2-437f-976b-3f9a93190618.jpg','Anya Forger','$2a$10$hMtGM/pdueM6bGii1cJcgefNPe45Ym2eip/lNnmVkul14VtEROVU6','USER','user@gmail.com');
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

-- Dump completed on 2024-11-11 15:10:17
