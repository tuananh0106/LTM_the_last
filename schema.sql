-- Database: messenger

CREATE DATABASE chat;
USE chat;

------------------------------------------------------
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTSUserIdfriendlistmessagesmessages=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
------------------------------------------------------

--
-- TABLE `message_address`
------------------------------------------------------

-- DROP TABLE IF EXISTS `message_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_address` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(200) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages_ddress`
--

LOCK TABLES `message_address` WRITE;
/*!40000 ALTER TABLE `message_address` DISABLE KEYS */;
INSERT INTO `message_address` VALUES (1, "Trần Quang Hưng"), 
(2, "user 1"), (3, "user 2"), (4, "user 3"), (5, "group 1"), (6, "group 2"), (7, "all");
/*!40000 ALTER TABLE `message_address` ENABLE KEYS */;
UNLOCK TABLES;
------------------------------------------------------

--
-- TABLE `user`
------------------------------------------------------

-- DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL UNIQUE,
  `password` varchar(45) NOT NULL,
  FOREIGN KEY (`id`) REFERENCES `message_address`(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1, "admin", "admin"), (2, "user1", "user1"), (3, "user2", "user2"), (4, "user3", "user3");
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
------------------------------------------------------

--
-- TABLE `group`
------------------------------------------------------

-- DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` int(11) NOT NULL,
  FOREIGN KEY (`id`) REFERENCES `message_address`(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (5), (6), (7);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;
------------------------------------------------------


--
-- TABLE `message_box`
------------------------------------------------------

-- DROP TABLE IF EXISTS `message_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_box` (
  `senderId` int(11) NOT NULL,
  `receiverId` int(11) NOT NULL,
  `seen` bool NOT NULL,
  FOREIGN KEY (`senderId`) REFERENCES `user`(`id`),
  FOREIGN KEY (`receiverId`) REFERENCES `message_address`(`id`),
  PRIMARY KEY (`senderId`, `receiverId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `message_box` WRITE;
/*!40000 ALTER TABLE `message_box` DISABLE KEYS */;
INSERT INTO `message_box` VALUES 
(1, 2, 1), (2, 1, 0), (2, 3, 1), 
(3, 2, 0), (2, 5, 0), (3, 5, 0),
(1, 7, 0), (2, 7, 0), (3, 7, 0), (4, 7, 1);
/*!40000 ALTER TABLE `message_box` ENABLE KEYS */;
UNLOCK TABLES;
------------------------------------------------------

--
-- TABLE `mesaage`
------------------------------------------------------

-- DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` int(11) NOT NULL,
  `receiverId` int(11) NOT NULL,
  `time` timestamp NOT NULL,
  `content` text CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`senderId`) REFERENCES `message_box`(`senderid`),
  FOREIGN KEY (`receiverId`) REFERENCES `message_box`(`receiverid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES 
(1, 1, 2, "2022-02-11 00:00:00", "tin nhắn từ admin"),
(2, 1, 2, "2022-02-11 00:00:01", "tin nhắn 2 từ admin"),
(3, 2, 3, "2022-02-11 00:00:02", "tin nhắn từ user 2 -> 3"),
(4, 3, 2, "2022-02-11 00:00:03", "tin nhắn từ user 3 -> 2"),
(5, 2, 5, "2022-02-11 00:00:04", "user 2 nhan vao nhom 5 lan 2"), 
(6, 3, 5, "2022-02-11 00:00:05", "user 3 nhan vao nhom 5"),
(7, 4, 7, "2022-07-11 03:07:00", "check");
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
------------------------------------------------------

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;