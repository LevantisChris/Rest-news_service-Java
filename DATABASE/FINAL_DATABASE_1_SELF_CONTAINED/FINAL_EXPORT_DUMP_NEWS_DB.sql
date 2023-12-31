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
-- Table structure for table `alerts_causes`
--

DROP TABLE IF EXISTS `alerts_causes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alerts_causes` (
  `ARTICLE_ID` int NOT NULL,
  `CAUSE` varchar(2500) NOT NULL,
  PRIMARY KEY (`ARTICLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerts_causes`
--

LOCK TABLES `alerts_causes` WRITE;
/*!40000 ALTER TABLE `alerts_causes` DISABLE KEYS */;
INSERT INTO `alerts_causes` VALUES (6,'Not appropriate language'),(19,'Fake news about basketball'),(20,'I dont like the content');
/*!40000 ALTER TABLE `alerts_causes` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'This is the NEW2 Title','This is the NEW2 Content, the new ','2023-06-29',1,1,'AlexAlexakis',0),(2,'Toyota Yaris GR review','New Content. The Toyota Yaris is the best car ever. Is the best. Buy it now.','2023-06-29',2,4,'DimitraAlexa',0),(3,'Nissan NEW2 Almera review','The NEW2 NEW2 Almera is one of the of the best cars in the world. Is the best, buy it now!!!!!.','2023-06-29',2,4,'DimitraAlexa',0),(5,'Suzuki hayabusa 2023 review','The Suzuki Hayabusa has \nfirmly established its status as motorcycling’s Ultimate Sportbike. \nThe 2023 version of Suzuki’s flagship sportbike is propelled by a muscular, \nrefined inline four-cylinder engine housed in a proven and thoroughly updated \nchassis with incomparable manners, managed by an unequaled suite of electronic rider \naids within stunning aerodynamic bodywork that is distinctly Hayabusa.','2023-06-30',3,3,'AlexAlexakis',0),(6,'Toyota Corolla specs review','The Toyota Corolla isn\'t here to wow or excite. Instead, it offers a safe predictable ride no matter how far the trip is. Performance isn\'t at the forefront of the Corolla\'s intentions (except for the GR Corolla, reviewed separately), and its most powerful engine packs a stable of 169�lethargic�horses. When compared to the Honda Civic or Mazda 3, the Corolla feels much like a tortoise between two hares. The Corolla is available as either a four-door sedan or five-door hatchback. The former is even available with an incredibly fuel-efficient hybrid powertrain.','2023-06-30',2,1,'DimitraAlexa',1),(7,'Yamaha tmax','In most The respects, the MT-07 is a conventional middleweight naked bike.[11] It uses world a best compact tubular backbone frame.[12] Its rear monoshock unit is placed horizontally car within the subframe to give in a shorter wheelbase, to save weight[13] and to lower the centre of gravity.[14] The world front forks are conventional telescopic items, whereas its 3-cylinder sibling, the MT-09, has inverted forks. The anti-lock braking system is available as an option on 2015�2017 models, but became standard equipment in 2018.\r\n\r\nBoth the MT-07 and the MT-09 are base models from which a range of derivative bikes was intended to follow. Yamaha commissioned designer Shinya Kimura to create a caf� racer special based on the MT-07. In June 2015, Kimura revealed the machine, which he called \"Faster Son\". Motorcycle News said that they expected Yamaha to announce a production version based on \"Faster Son\" in late 2015.[15] The Yamaha XSR700 was launched for the 2016 model year based on the MT-07. The Yamaha T�n�r� 700, which shares the CP2 engine with the MT-07, was launched for the 2019 model year. ','2023-06-30',3,3,'AlexAlexakis',0),(8,'Ferrari Change alert California review','The NEW Ferrari California T bears the burden of being the entry-level member of this most exclusive club. It�ll get you in the door, but that�s about it. It is the low man on the totem pole, and in this world of elitist snobbery, many enjoy looking down on that. But you know what? If we can cast aside the country club mentality for a minute, the California T was, in many ways, the best real-world package Ferrari made. And now that it�s being replaced by the Portofino, the California T is one of the best value grand touring cars you can buy.','2023-06-30',2,1,'AlexAlexakis',0),(9,'BMW new M5 2020 review','Don\'t call it a comeback. The BMW M5 has been here for years. However, the latest generation is rocking its peers and putting fear in the hearts of other sports sedans. Uncorking 600-plus horsepower from its twin-turbo V-8, the 2020 M5 accelerates with unbridled ferocity. Its standard all-wheel-drive system can even send all that power to the rear wheels for drivers who like to hang out the tail. Indeed, BMW has reinvented an icon that had become almost unrecognizable. While the M5 is still a big luxury sedan loaded with high-tech gadgetry, it now has a maniacal side that lately has been missing from the company\'s storied M division. The best car in the word','2023-06-30',2,4,'DimitraAlexa',0),(11,'This the NEW TEST title','This is the NEW MODIFIED content TEST ','2023-07-02',1,4,'GeorgeGEO',0),(12,'Test the search kakoko kpop','kakoko this is the content kpop','2023-07-03',1,1,'AlexAlexakis',0),(13,'TEST TITLE','This is a test to see!!!','2023-07-06',1,4,'DimitraAlexa',0),(14,'This is the Test title','This is the content of the test. new','2023-07-20',1,1,'DimitraAlexa',0),(15,'Toyota Corolla new model 2023','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2023-07-21',2,1,'DimitraAlexa',0),(16,'Test title testing','This is a test that contains, some words that the test method will be relied on. One word is the SportsCar and another word is the Music.','2023-07-21',1,2,'DimitraAlexa',0),(18,'Yamaha tmax 200 2023','testing testing','2023-07-27',3,2,'AlexAlexakis',0),(19,'BN Insider: clearing the air amidst Svi Mykhailiuk\'s Panathinaikos rumors','The 2022-23 basketball season is ending, but the beginning of summer marks another highly anticipated and widely followed stage in the basketball calendar: free agency and market rumors.\r\n\r\nTo bring basketball fans even closer to summer market information and provide a better understanding of the processes involved, BasketNews introduces an exclusive platform where fans can access insider information that is not heard elsewhere.\r\n\r\nThe BasketNews team will provide exclusive behind-the-scenes information circulating among the basketball community regarding transfers, potential signings, interest, and various decisions that have not yet been confirmed or made publicly accessible in BasketNews reports.\r\n\r\nThis information is not quotable to our colleagues in European media as it has not been fully confirmed yet. You can find all the quotable and verified information in the accessible articles on the BasketNews website.','2023-07-31',14,1,'DimitraAlexa',1),(20,'Ranking Reaction: Iga Swiatek kicks off milestone 70th consecutive week at No. 1','With six tour-level events taking place this past week�three men�s, three women�s�there are a number of notable movers on the new rankings today. But the biggest news might come from a player who didn�t move at all, as Iga Swiatek kicks off her milestone 70th consecutive week at No. 1 on the WTA rankings.\r\n\r\nSwiatek made history by winning the title in Warsaw, becoming the first Polish woman in the Open Era to win a WTA title in Poland. And now she�s just the third player in WTA rankings history to amass 70 or more consecutive weeks in her first stint at No. 1.\r\n\r\nWOMEN TO SPEND 70+ WEEKS IN FIRST STINT AT NO. 1\r\n~ Steffi Graf [186 weeks from August 17, 1987 to March 10, 1991]\r\n~ Martina Hingis [80 weeks from March 31, 1997 to October 11, 1998]\r\n~ Iga Swiatek [70 weeks from April 4, 2022 to present]\r\n\r\nShe�s also just the eighth player in WTA rankings history to amass 70 consecutive weeks at No. 1 in any stint at the top spot.','2023-07-31',15,1,'AlexAlexakis',1),(21,'France and T�rkiye Leap Forward in Volleyball Ranking Following Two Challengers','wo exciting Challenger tournaments have concluded in the 20255, serving as qualifiers for the elite VNL2024. These events showcased impressive strides in quality from both French women�s and Turkish men�s teams, putting them squarely on the path to join the world�s volleyball elite.','2023-07-31',21,1,'DimitraAlexa',0),(22,'Michael Jordan�s sale of majority ownership of Hornets finalized','CHARLOTTE, N.C. (AP) � The sale of the majority stake of the Charlotte Hornets from six-time NBA champion Michael Jordan to Gabe Plotkin and Rick Schnall has been finalized, the team announced Thursday.\r\n\r\nThe Hornets planned to introduce Plotkin and Schnall at a news conference later in the day.\r\n\r\nJordan, who has owned the team for the past 13 years, said in a news release that it was a �tremendous honor� to own the Hornets but didn�t give an explanation for why he sold the franchise.','2023-08-03',14,1,'DimitraAlexa',0),(23,'5 underrated moves from 2023 NBA offseason','Much of the chatter around the NBA offseason centered on marquee players that either changed teams: Bradley Beal to Phoenix, Chris Paul to Golden State, Kristaps Porzingis to Boston, Marcus Smart to Memphis, Fred VanVleet and Dillon Brooks to Houston, Jordan Poole to Washington �\r\n\r\nOr decided to re-sign or extend with their current squad to run it back: Jaylen Brown in Boston, Dejounte Murray in Atlanta, Tyrese Haliburton in Indiana, LaMelo Ball in Charlotte, Desmond Bane in Memphis, Anthony Edwards in Minnesota, Domantas Sabonis in Sacramento, Brook Lopez and Khris Middleton in Milwaukee, Austin Reaves, Rui Hachimura and D�Angelo Russell in Los Angeles, Kyrie Irving in Dallas, Draymond Green in Golden State, Cam Johnson in Brooklyn and Kyle Kuzma in Washington �','2023-08-03',14,1,'AlexAlexakis',0);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (14,'I dont like this article is very bad!','2023-07-10',2,3,'GeorgeGEO'),(15,'This is a comment.','2023-07-10',3,3,NULL),(16,'I have owned the car in the past!!!!','2023-07-17',3,3,NULL),(17,'The best car ever build!!!!!','2023-07-17',3,3,'Hraklis1234'),(18,'I dont like this car very bad build quality!!!','2023-07-17',3,3,NULL),(19,'This a final test','2023-07-17',13,3,'NameUser12364'),(20,'This another final Test','2023-07-17',13,3,NULL),(21,'The Yaris is the best car ever build by Toyota','2023-07-17',2,3,'DimitraAlexa'),(24,'Wow!!! nice articleeeee !!!! !!!?!! ','2023-07-31',3,3,'DimitraAlexa'),(26,'Nice Job!!!','2023-08-03',13,1,'ChrisIssak');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `ID` int NOT NULL,
  `DESCRIPTION` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'VISITOR'),(2,'JOURNALIST'),(3,'CURATOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `SESSION_NUMBER` int NOT NULL AUTO_INCREMENT,
  `ID` int DEFAULT NULL,
  `USERNAME_BELONGS` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SESSION_NUMBER`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `USERNAME_BELONGS` (`USERNAME_BELONGS`),
  CONSTRAINT `sessions_ibfk_1` FOREIGN KEY (`USERNAME_BELONGS`) REFERENCES `users` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (29,123456,'ElenaELE');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `states`
--

DROP TABLE IF EXISTS `states`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `states` (
  `ID` int NOT NULL,
  `DESCRIPTION` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `states`
--

LOCK TABLES `states` WRITE;
/*!40000 ALTER TABLE `states` DISABLE KEYS */;
INSERT INTO `states` VALUES (1,'CREATED'),(2,'SUBMITTED'),(3,'APPROVED'),(4,'PUBLISHED');
/*!40000 ALTER TABLE `states` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(250) NOT NULL,
  `DATE_CREATION` date NOT NULL,
  `STATE_ID` int NOT NULL,
  `CREATOR_USERNAME` varchar(100) NOT NULL,
  `PARENT_TOPIC_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `TITLE` (`TITLE`),
  KEY `STATE_ID` (`STATE_ID`),
  KEY `CREATOR_USERNAME` (`CREATOR_USERNAME`),
  KEY `PARENT_TOPIC_ID` (`PARENT_TOPIC_ID`),
  CONSTRAINT `topic_ibfk_1` FOREIGN KEY (`STATE_ID`) REFERENCES `states` (`ID`),
  CONSTRAINT `topic_ibfk_2` FOREIGN KEY (`CREATOR_USERNAME`) REFERENCES `users` (`USERNAME`),
  CONSTRAINT `topic_ibfk_3` FOREIGN KEY (`PARENT_TOPIC_ID`) REFERENCES `topic` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,'This is a topic','2023-06-28',3,'DimitraAlexa',NULL),(2,'Cars','2023-06-13',3,'AlexAlexakis',NULL),(3,'Motorcycle','2022-05-25',3,'AlexAlexakis',NULL),(6,'Sports','2023-07-10',3,'GeorgeGEO',NULL),(13,'Football','2023-07-12',3,'DimitraAlexa',6),(14,'Basketball','2023-07-12',3,'DimitraAlexa',6),(15,'Tennis','2023-07-12',3,'GeorgeGEO',6),(16,'Formula One','2023-07-13',1,'GeorgeGEO',6),(17,'BMW','2023-07-15',1,'AlexAlexakis',2),(18,'Citroen C1','2023-07-16',1,'AlexAlexakis',2),(19,'Citroen C4','2023-07-16',1,'DimitraAlexa',2),(20,'Citroen C2','2023-07-16',3,'GeorgeGEO',2),(21,'Volley','2023-07-17',3,'GeorgeGEO',6),(22,'Life','2023-08-02',1,'AlexAlexakis',NULL);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `NAME` varchar(150) NOT NULL,
  `SURNAME` varchar(150) NOT NULL,
  `ROLE_ID` int NOT NULL,
  PRIMARY KEY (`USERNAME`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('AlexAlexakis','456321','Alexantros','Alexandrakis',2),('ChristosCHR','123456','Chistos','Christakis',2),('DimitraAlexa','6987','Dimitra','Alexa',3),('ElenaELE','12369','Elena','Elenitsa',3),('GeorgeGEO','7896','Giorgos','Giorgakis',2),('MariaMaraki','1234','Maria','Maraki',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-06 18:20:43
