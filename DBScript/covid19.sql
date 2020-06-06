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
-- Table structure for table `auto_remove`
--

DROP TABLE IF EXISTS `auto_remove`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_remove` (
  `id` varchar(20) NOT NULL,
  `date` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `auto_remove_ibfk_1` FOREIGN KEY (`id`) REFERENCES `suspected` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto_remove`
--

LOCK TABLES `auto_remove` WRITE;
/*!40000 ALTER TABLE `auto_remove` DISABLE KEYS */;
/*!40000 ALTER TABLE `auto_remove` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `covid_positive`
--

DROP TABLE IF EXISTS `covid_positive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `covid_positive` (
  `id` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `found` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `covid_positive_ibfk_1` FOREIGN KEY (`id`) REFERENCES `people` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `covid_positive`
--

LOCK TABLES `covid_positive` WRITE;
/*!40000 ALTER TABLE `covid_positive` DISABLE KEYS */;
/*!40000 ALTER TABLE `covid_positive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exit_data`
--

DROP TABLE IF EXISTS `exit_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exit_data` (
  `id` varchar(20) NOT NULL,
  `exit_reason` varchar(100) NOT NULL,
  `from_` varchar(50) NOT NULL,
  `to_` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `exit_data_ibfk_2` (`from_`),
  CONSTRAINT `exit_data_ibfk_1` FOREIGN KEY (`from_`) REFERENCES `qc` (`id`),
  CONSTRAINT `exit_data_ibfk_2` FOREIGN KEY (`from_`) REFERENCES `hospital` (`id`),
  CONSTRAINT `exit_data_ibfk_3` FOREIGN KEY (`id`) REFERENCES `quarantined` (`id`),
  CONSTRAINT `exit_data_ibfk_4` FOREIGN KEY (`id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exit_data`
--

LOCK TABLES `exit_data` WRITE;
/*!40000 ALTER TABLE `exit_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `exit_data` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `globaldata` VALUES ('2020-05-01','2000','1000','300');
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
INSERT INTO `hospital` VALUES ('H001','Gampaha','Gampaha','Gampaha','2000','sula','03384849','03394994','03388485','48489494','acd@gmail.com');
/*!40000 ALTER TABLE `hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` varchar(20) NOT NULL,
  `hospital` varchar(60) NOT NULL,
  `date` date NOT NULL,
  `reason` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `hospital` (`hospital`),
  CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`id`) REFERENCES `people` (`id`),
  CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`hospital`) REFERENCES `hospital` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `people`
--

DROP TABLE IF EXISTS `people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `people` (
  `id` varchar(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(30) NOT NULL,
  `district` varchar(30) NOT NULL,
  `province` varchar(30) NOT NULL,
  `nic` varchar(15) NOT NULL,
  `contact_number` varchar(12) NOT NULL,
  `blood_group` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `people`
--

LOCK TABLES `people` WRITE;
/*!40000 ALTER TABLE `people` DISABLE KEYS */;
/*!40000 ALTER TABLE `people` ENABLE KEYS */;
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
  `head` varchar(50) NOT NULL,
  `head_contact` varchar(12) NOT NULL,
  `center_contact1` varchar(12) NOT NULL,
  `center_contact2` varchar(12) NOT NULL,
  `capacity` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc`
--

LOCK TABLES `qc` WRITE;
/*!40000 ALTER TABLE `qc` DISABLE KEYS */;
INSERT INTO `qc` VALUES ('Q001','xxx','Ragama','Gampaha','xxx','93930300','9303030','9595959','1000');
/*!40000 ALTER TABLE `qc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarantined`
--

DROP TABLE IF EXISTS `quarantined`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quarantined` (
  `id` varchar(20) NOT NULL,
  `reason` varchar(500) NOT NULL,
  `entered_date` date NOT NULL,
  `center` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `center` (`center`),
  CONSTRAINT `quarantined_ibfk_1` FOREIGN KEY (`center`) REFERENCES `qc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `quarantined_ibfk_2` FOREIGN KEY (`id`) REFERENCES `people` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarantined`
--

LOCK TABLES `quarantined` WRITE;
/*!40000 ALTER TABLE `quarantined` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarantined` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reference`
--

DROP TABLE IF EXISTS `reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reference` (
  `suspect_id` varchar(20) NOT NULL,
  `reference_id` varchar(20) NOT NULL,
  `connection` varchar(300) NOT NULL,
  PRIMARY KEY (`reference_id`) USING BTREE,
  UNIQUE KEY `suspect_id` (`suspect_id`) USING BTREE,
  CONSTRAINT `reference_ibfk_1` FOREIGN KEY (`reference_id`) REFERENCES `quarantined` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reference_ibfk_2` FOREIGN KEY (`reference_id`) REFERENCES `people` (`id`),
  CONSTRAINT `reference_ibfk_3` FOREIGN KEY (`reference_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reference`
--

LOCK TABLES `reference` WRITE;
/*!40000 ALTER TABLE `reference` DISABLE KEYS */;
/*!40000 ALTER TABLE `reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspected`
--

DROP TABLE IF EXISTS `suspected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suspected` (
  `id` varchar(20) NOT NULL,
  `reason` varchar(300) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `suspected_ibfk_1` FOREIGN KEY (`id`) REFERENCES `people` (`id`),
  CONSTRAINT `suspected_ibfk_2` FOREIGN KEY (`id`) REFERENCES `reference` (`suspect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspected`
--

LOCK TABLES `suspected` WRITE;
/*!40000 ALTER TABLE `suspected` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspected` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `contact` varchar(12) NOT NULL,
  `email` varchar(20) NOT NULL,
  `unm` varchar(30) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  `role` varchar(30) NOT NULL,
  `hospitalid` varchar(10) DEFAULT NULL,
  `qcid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userid`),
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
INSERT INTO `user` VALUES ('001','sula','03030303','acd@gmail.com','sula','abcd','admin','H001',NULL),('002','rock','940400','erd@gmail.com','rock','123','Hospital-IT','H001',NULL),('003','john','03030302','acd@gmal.com','john','xxx','PSTF',NULL,NULL),('004','xxx','9404004','pcd@gmail.com','sss','sss','Hospital-IT','H001',NULL);
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

-- Dump completed on 2020-06-06 12:21:36
