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
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `auto_remove_ibfk_1` FOREIGN KEY (`id`) REFERENCES `suspected` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto_remove`
--

LOCK TABLES `auto_remove` WRITE;
/*!40000 ALTER TABLE `auto_remove` DISABLE KEYS */;
INSERT INTO `auto_remove` VALUES ('005','2020-06-11');
/*!40000 ALTER TABLE `auto_remove` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `center_exit`
--

DROP TABLE IF EXISTS `center_exit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `center_exit` (
  `quarantined_id` varchar(20) NOT NULL,
  `center_id` varchar(20) NOT NULL,
  PRIMARY KEY (`quarantined_id`,`center_id`),
  KEY `FK_Center` (`center_id`),
  CONSTRAINT `FK_Center` FOREIGN KEY (`center_id`) REFERENCES `qc` (`id`),
  CONSTRAINT `FK_Exit2` FOREIGN KEY (`quarantined_id`) REFERENCES `exit_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `center_exit`
--

LOCK TABLES `center_exit` WRITE;
/*!40000 ALTER TABLE `center_exit` DISABLE KEYS */;
/*!40000 ALTER TABLE `center_exit` ENABLE KEYS */;
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
INSERT INTO `covid_positive` VALUES ('003','2020-06-10','H001');
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
  PRIMARY KEY (`id`,`from_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exit_data`
--

LOCK TABLES `exit_data` WRITE;
/*!40000 ALTER TABLE `exit_data` DISABLE KEYS */;
INSERT INTO `exit_data` VALUES ('003','DISCHARGED','H001','uuu','Hostpital','2020-06-04'),('004','TRANSFERRED','Q001','H001','Quarantined Center','2020-06-10'),('007','DEAD ','H001','Kanaththa','Hostpital','2020-06-11');
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
-- Table structure for table `hospital_exit`
--

DROP TABLE IF EXISTS `hospital_exit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital_exit` (
  `patient_id` varchar(20) NOT NULL,
  `hospital_id` varchar(20) NOT NULL,
  PRIMARY KEY (`patient_id`,`hospital_id`),
  KEY `FK_Hospital` (`hospital_id`),
  CONSTRAINT `FK_Exit` FOREIGN KEY (`patient_id`) REFERENCES `exit_data` (`id`),
  CONSTRAINT `FK_Hospital` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_exit`
--

LOCK TABLES `hospital_exit` WRITE;
/*!40000 ALTER TABLE `hospital_exit` DISABLE KEYS */;
INSERT INTO `hospital_exit` VALUES ('003','H001');
/*!40000 ALTER TABLE `hospital_exit` ENABLE KEYS */;
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
  PRIMARY KEY (`id`,`hospital`),
  KEY `hospital` (`hospital`),
  CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`hospital`) REFERENCES `hospital` (`id`),
  CONSTRAINT `patient_ibfk_2` FOREIGN KEY (`id`) REFERENCES `people` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('003','H001','2020-06-10',''),('007','H001','2020-06-11','jfjfjf');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_exit`
--

DROP TABLE IF EXISTS `patient_exit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_exit` (
  `patient_id` varchar(20) NOT NULL,
  `hospital_id` varchar(20) NOT NULL,
  PRIMARY KEY (`patient_id`,`hospital_id`),
  CONSTRAINT `FK_Exitex` FOREIGN KEY (`patient_id`) REFERENCES `exit_data` (`id`),
  CONSTRAINT `FK_Patientex` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_exit`
--

LOCK TABLES `patient_exit` WRITE;
/*!40000 ALTER TABLE `patient_exit` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_exit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_refs`
--

DROP TABLE IF EXISTS `patient_refs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_refs` (
  `suspected_id` varchar(20) NOT NULL,
  `reference_id` varchar(20) NOT NULL,
  PRIMARY KEY (`suspected_id`,`reference_id`),
  KEY `FK_Patient` (`reference_id`),
  CONSTRAINT `FK_Patient` FOREIGN KEY (`reference_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FK_Reference` FOREIGN KEY (`suspected_id`) REFERENCES `reference` (`suspect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_refs`
--

LOCK TABLES `patient_refs` WRITE;
/*!40000 ALTER TABLE `patient_refs` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_refs` ENABLE KEYS */;
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
INSERT INTO `people` VALUES ('003','sula','dissa','','Gampaha',' Gampaha',' Western','921814399v','03384949',' A+'),('004','anil','xxx','','xxx',' Kalutara',' Eastern','899404v','9494040',' A-'),('005','john','doe','xxx','xxx','Colombo','Western','8695959v','858585','A+'),('006','nimal','perera','','kandy',' Kandy',' Central','9595959v','959595',' A+'),('007','ruwan','dias','jsjsksk','asddk',' Colombo',' Southern','9494949v','1234576543',' B+');
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
INSERT INTO `quarantined` VALUES ('004','fffffdsa','2020-06-03','Q001');
/*!40000 ALTER TABLE `quarantined` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarantined_exit`
--

DROP TABLE IF EXISTS `quarantined_exit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quarantined_exit` (
  `quarantined_id` varchar(20) NOT NULL,
  `center_id` varchar(20) NOT NULL,
  PRIMARY KEY (`quarantined_id`,`center_id`),
  CONSTRAINT `FK_Exitex2` FOREIGN KEY (`quarantined_id`) REFERENCES `exit_data` (`id`),
  CONSTRAINT `FK_Quarantinedex` FOREIGN KEY (`quarantined_id`) REFERENCES `quarantined` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarantined_exit`
--

LOCK TABLES `quarantined_exit` WRITE;
/*!40000 ALTER TABLE `quarantined_exit` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarantined_exit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarantined_refs`
--

DROP TABLE IF EXISTS `quarantined_refs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quarantined_refs` (
  `suspected_id` varchar(20) NOT NULL,
  `reference_id` varchar(20) NOT NULL,
  PRIMARY KEY (`suspected_id`,`reference_id`),
  KEY `FK_Quaran` (`reference_id`),
  CONSTRAINT `FK_Quaran` FOREIGN KEY (`reference_id`) REFERENCES `quarantined` (`id`),
  CONSTRAINT `FK_Ref` FOREIGN KEY (`suspected_id`) REFERENCES `reference` (`suspect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarantined_refs`
--

LOCK TABLES `quarantined_refs` WRITE;
/*!40000 ALTER TABLE `quarantined_refs` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarantined_refs` ENABLE KEYS */;
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
  PRIMARY KEY (`suspect_id`,`reference_id`),
  UNIQUE KEY `suspect_id` (`suspect_id`) USING BTREE,
  KEY `FK_QuaranRef` (`reference_id`),
  CONSTRAINT `FK_PeopleRef` FOREIGN KEY (`suspect_id`) REFERENCES `people` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reference`
--

LOCK TABLES `reference` WRITE;
/*!40000 ALTER TABLE `reference` DISABLE KEYS */;
INSERT INTO `reference` VALUES ('005','003','friend'),('006','004','friend');
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
INSERT INTO `suspected` VALUES ('005','reason','1980-12-17'),('006','hachin yanawa','2020-06-03');
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

-- Dump completed on 2020-06-11 17:10:26
