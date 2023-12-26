-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: dbinfox
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
-- Table structure for table `tb_clients`
--

DROP TABLE IF EXISTS `tb_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_clients` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_clients`
--

LOCK TABLES `tb_clients` WRITE;
/*!40000 ALTER TABLE `tb_clients` DISABLE KEYS */;
INSERT INTO `tb_clients` VALUES (2,'Nickolas Martins','rua xxx, 355','94255-4655','nick104@outlook.com'),(8,'Linus Torvalds','rua xxx, 356','5555-5555','linus@globo.com'),(5,'Nilson Barbosa','Avenida do trigo, 170','6565-5656','nilsonn1998@hotmail.com'),(9,'Richard Stallman','Rua FSF, 878','2323-3232','freesoftware@gmail.com');
/*!40000 ALTER TABLE `tb_clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_os`
--

DROP TABLE IF EXISTS `tb_os`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_os` (
  `os` int NOT NULL AUTO_INCREMENT,
  `date_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tipo` varchar(20) NOT NULL,
  `situacao` varchar(50) NOT NULL,
  `equipment` varchar(150) NOT NULL,
  `defect` varchar(150) NOT NULL,
  `service` varchar(150) DEFAULT NULL,
  `technician` varchar(30) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `id_client` int NOT NULL,
  PRIMARY KEY (`os`),
  KEY `id_client` (`id_client`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_os`
--

LOCK TABLES `tb_os` WRITE;
/*!40000 ALTER TABLE `tb_os` DISABLE KEYS */;
INSERT INTO `tb_os` VALUES (3,'2023-12-12 18:57:19','Orcamento','Entrega OK','Acer Aspire 3','tela queimada','trocar tela','Leonardo',250.00,2),(5,'2023-12-17 16:04:54','Orcamento','Na Bancada','Lenovo Ideapad i3','HD corrompido','Troca de HD','Leandro',100.00,2),(6,'2023-12-17 16:07:38','Ordem de Servico','Na Bancada','Acer Aspire 5','Não liga','Troca da fonte','Júlio',50.00,5);
/*!40000 ALTER TABLE `tb_os` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_users`
--

DROP TABLE IF EXISTS `tb_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_users` (
  `id_user` int NOT NULL,
  `user` varchar(50) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `login` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login` (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_users`
--

LOCK TABLES `tb_users` WRITE;
/*!40000 ALTER TABLE `tb_users` DISABLE KEYS */;
INSERT INTO `tb_users` VALUES (1,'Nickolas Gabriel','9999-9999','itsmenicky','45678','admin'),(2,'Administrador','8888-8888','admin','admin','admin'),(3,'Nickolas Jonas','12345-54321','nicknick','5263','admin'),(4,'Thais Bianca','9999-9999','thais','10203040','admin');
/*!40000 ALTER TABLE `tb_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'dbinfox'
--

--
-- Dumping routines for database 'dbinfox'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-17 13:42:09
