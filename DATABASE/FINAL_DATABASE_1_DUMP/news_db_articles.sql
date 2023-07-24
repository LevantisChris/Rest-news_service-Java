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
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(250) NOT NULL,
  `CONTENT` varchar(2500) NOT NULL,
  `DATE_CREATION` date NOT NULL,
  `TOPIC_ID` int NOT NULL,
  `STATE_ID` int NOT NULL,
  `CREATOR_USERNAME` varchar(100) NOT NULL,
  `alert` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `TITLE` (`TITLE`),
  KEY `TOPIC_ID` (`TOPIC_ID`),
  KEY `STATE_ID` (`STATE_ID`),
  KEY `CREATOR_USERNAME` (`CREATOR_USERNAME`),
  CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`TOPIC_ID`) REFERENCES `topic` (`ID`),
  CONSTRAINT `articles_ibfk_2` FOREIGN KEY (`STATE_ID`) REFERENCES `states` (`ID`),
  CONSTRAINT `articles_ibfk_3` FOREIGN KEY (`CREATOR_USERNAME`) REFERENCES `users` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'This is the NEW2 Title','This is the NEW2 Content, the new ','2023-06-29',1,1,'AlexAlexakis',0),(2,'Toyota Yaris GR review','The Toyota Yaris is the best car ever. Is the best. Buy it now.','2023-06-29',2,4,'DimitraAlexa',0),(3,'Nissan NEW2 Almera review','The NEW2 NEW2 Almera is one of the of the best cars in the world. Is the best, buy it now!!!!!.','2023-06-29',2,4,'DimitraAlexa',0),(5,'Suzuki hayabusa 2023 review','The Suzuki Hayabusa has \nfirmly established its status as motorcycling’s Ultimate Sportbike. \nThe 2023 version of Suzuki’s flagship sportbike is propelled by a muscular, \nrefined inline four-cylinder engine housed in a proven and thoroughly updated \nchassis with incomparable manners, managed by an unequaled suite of electronic rider \naids within stunning aerodynamic bodywork that is distinctly Hayabusa.','2023-06-30',3,3,'AlexAlexakis',0),(6,'Toyota Corolla specs review','The Toyota Corolla isn\'t here to wow or excite. Instead, it offers a safe predictable ride no matter how far the trip is. Performance isn\'t at the forefront of the Corolla\'s intentions (except for the GR Corolla, reviewed separately), and its most powerful engine packs a stable of 169�lethargic�horses. When compared to the Honda Civic or Mazda 3, the Corolla feels much like a tortoise between two hares. The Corolla is available as either a four-door sedan or five-door hatchback. The former is even available with an incredibly fuel-efficient hybrid powertrain.','2023-06-30',2,3,'DimitraAlexa',0),(7,'Yamaha tmax','In most The respects, the MT-07 is a conventional middleweight naked bike.[11] It uses world a best compact tubular backbone frame.[12] Its rear monoshock unit is placed horizontally car within the subframe to give in a shorter wheelbase, to save weight[13] and to lower the centre of gravity.[14] The world front forks are conventional telescopic items, whereas its 3-cylinder sibling, the MT-09, has inverted forks. The anti-lock braking system is available as an option on 2015�2017 models, but became standard equipment in 2018.\r\n\r\nBoth the MT-07 and the MT-09 are base models from which a range of derivative bikes was intended to follow. Yamaha commissioned designer Shinya Kimura to create a caf� racer special based on the MT-07. In June 2015, Kimura revealed the machine, which he called \"Faster Son\". Motorcycle News said that they expected Yamaha to announce a production version based on \"Faster Son\" in late 2015.[15] The Yamaha XSR700 was launched for the 2016 model year based on the MT-07. The Yamaha T�n�r� 700, which shares the CP2 engine with the MT-07, was launched for the 2019 model year. ','2023-06-30',3,3,'AlexAlexakis',0),(8,'Ferrari Change alert California review','The NEW Ferrari California T bears the burden of being the entry-level member of this most exclusive club. It�ll get you in the door, but that�s about it. It is the low man on the totem pole, and in this world of elitist snobbery, many enjoy looking down on that. But you know what? If we can cast aside the country club mentality for a minute, the California T was, in many ways, the best real-world package Ferrari made. And now that it�s being replaced by the Portofino, the California T is one of the best value grand touring cars you can buy.','2023-06-30',2,1,'AlexAlexakis',0),(9,'BMW new M5 2020 review','Don\'t call it a comeback. The BMW M5 has been here for years. However, the latest generation is rocking its peers and putting fear in the hearts of other sports sedans. Uncorking 600-plus horsepower from its twin-turbo V-8, the 2020 M5 accelerates with unbridled ferocity. Its standard all-wheel-drive system can even send all that power to the rear wheels for drivers who like to hang out the tail. Indeed, BMW has reinvented an icon that had become almost unrecognizable. While the M5 is still a big luxury sedan loaded with high-tech gadgetry, it now has a maniacal side that lately has been missing from the company\'s storied M division. The best car in the word','2023-06-30',2,2,'DimitraAlexa',0),(11,'This the NEW TEST title','This is the NEW MODIFIED content TEST ','2023-07-02',1,4,'GeorgeGEO',0),(12,'Test the search kakoko kpop','kakoko this is the content kpop','2023-07-03',1,1,'AlexAlexakis',0),(13,'TEST TITLE','This is a test to see!!!','2023-07-06',1,4,'DimitraAlexa',0),(14,'This is the Test title','This is the content of the test.','2023-07-20',1,1,'DimitraAlexa',1),(15,'Toyota Corolla new model 2023','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2023-07-21',2,1,'DimitraAlexa',0),(16,'Test title testing','This is a test that contains, some words that the test method will be relied on. One word is the SportsCar and another word is the Music.','2023-07-21',1,1,'DimitraAlexa',0);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
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
