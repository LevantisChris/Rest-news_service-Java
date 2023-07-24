CREATE DATABASE  IF NOT EXISTS `news_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `news_db`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: news_db
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CONTENT` varchar(500) NOT NULL,
  `DATE_CREATION` date NOT NULL,
  `ARTICLE_ID` int NOT NULL,
  `STATE_ID` int NOT NULL,
  `CREATOR_USERNAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ARTICLE_ID` (`ARTICLE_ID`),
  KEY `STATE_ID` (`STATE_ID`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `articles` (`ID`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`STATE_ID`) REFERENCES `states` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (14,'I dont like this article is very bad!','2023-07-10',2,3,'GeorgeGEO'),(15,'This is a comment.','2023-07-10',3,3,NULL),(16,'I have owned the car in the past!!!!','2023-07-17',3,3,NULL),(17,'The best car ever build!!!!!','2023-07-17',3,3,'Hraklis1234'),(18,'I dont like this car very bad build quality!!!','2023-07-17',3,3,NULL),(19,'This a final test','2023-07-17',13,3,'NameUser12364'),(20,'This another final Test','2023-07-17',13,3,NULL),(21,'The Yaris is the best car ever build by Toyota','2023-07-17',2,1,'DimitraAlexa'),(22,'Testing modify comment','2023-07-22',13,1,NULL);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-24 18:38:50
