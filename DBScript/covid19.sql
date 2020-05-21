-- MySQL dump 10.16  Distrib 10.1.39-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: covid19
-- ------------------------------------------------------
-- Server version	5.7.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `globaldata`
--

DROP TABLE IF EXISTS `globaldata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globaldata` (
  `date_of_updated` date NOT NULL,
  `confirmed` varchar(15) DEFAULT NULL,
  `recovered` varchar(12) DEFAULT NULL,
  `death` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`date_of_updated`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `globaldata`
--

LOCK TABLES `globaldata` WRITE;
/*!40000 ALTER TABLE `globaldata` DISABLE KEYS */;
INSERT INTO `globaldata` VALUES ('2020-05-01','3000','1000','478'),('2020-05-02','5000','3500','876'),('2020-05-03','7950','900','4000');
/*!40000 ALTER TABLE `globaldata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital` (
  `id` varchar(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `city` varchar(50) NOT NULL,
  `district` varchar(50) NOT NULL,
  `capacity` varchar(30) NOT NULL,
  `director` varchar(50) NOT NULL,
  `director_contact_co` varchar(12) NOT NULL,
  `hospital_contact1` varchar(12) NOT NULL,
  `hospital_contact2` varchar(12) NOT NULL,
  `fax` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital`
--

LOCK TABLES `hospital` WRITE;
/*!40000 ALTER TABLE `hospital` DISABLE KEYS */;
INSERT INTO `hospital` VALUES ('H001','Gampaha','Gampaha','Gampaha','2000','sula','03384849','03394994','03388485','48489494','acd@gmail.com'),('H002','Ragama','Ragama',' Gampaha','2000','xxx','12344555','43224555','5667677','3345666','xxx@gmail.com'),('H003','xxx','xxx',' Matale','2000','sss','0332227447','0332227447','0332227447','8494949','www@jgjg.com');
/*!40000 ALTER TABLE `hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc`
--

DROP TABLE IF EXISTS `qc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc` (
  `id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `city` varchar(40) NOT NULL,
  `district` varchar(40) NOT NULL,
  `capacity` varchar(12) NOT NULL,
  `head` varchar(50) NOT NULL,
  `head_contact` varchar(12) NOT NULL,
  `center_contact1` varchar(12) NOT NULL,
  `center_contact2` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc`
--

LOCK TABLES `qc` WRITE;
/*!40000 ALTER TABLE `qc` DISABLE KEYS */;
INSERT INTO `qc` VALUES ('Q001','xxx','Ragama','Gampaha','1000','xxx','93930300','9303030','9595959');
/*!40000 ALTER TABLE `qc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `name` varchar(100) NOT NULL,
  `contact` varchar(12) NOT NULL,
  `email` varchar(20) NOT NULL,
  `unm` varchar(30) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  `role` varchar(30) NOT NULL,
  `hospitalid` varchar(10) DEFAULT NULL,
  `qcid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `contact` (`contact`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `unm` (`unm`),
  UNIQUE KEY `pwd` (`pwd`),
  KEY `user_ibfk_1` (`hospitalid`),
  KEY `qcid` (`qcid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`hospitalid`) REFERENCES `hospital` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`qcid`) REFERENCES `qc` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('amal','1234567890','ss@gmail.com','amal','amal','Hospital-IT',NULL,NULL),('kamal','0713430086','fdf@gmail.com','kamal','xxx','PSTF',NULL,NULL),('nimal','2345678567','sss@ddd.com','nimla','nimal','Hospital-IT',NULL,NULL),('rock','940400','erd@gmail.com','rock','123','Hospital-IT','H001',NULL),('sula','03030303','acd@gmail.com','sula','abcd','admin','H001',NULL);
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

-- Dump completed on 2020-05-21  9:09:49
