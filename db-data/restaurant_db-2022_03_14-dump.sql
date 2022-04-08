-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restaurant_db
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL,
  `total_price` double DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl70asp4l4w0jmbm1tqyofho4o` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (20,0,19),(33,36,10);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (4,'Beverages'),(1,'Breakfast'),(3,'Dessert'),(2,'Lunch');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_zones`
--

DROP TABLE IF EXISTS `delivery_zones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_zones` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ls2kwnxdrohm8j5fo6r69grle` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_zones`
--

LOCK TABLES `delivery_zones` WRITE;
/*!40000 ALTER TABLE `delivery_zones` DISABLE KEYS */;
INSERT INTO `delivery_zones` VALUES (2,'Centru'),(7,'Floresti'),(3,'Gheorgheni'),(4,'Grigorescu'),(6,'Manastur'),(1,'Marasti'),(5,'Zorilor');
/*!40000 ALTER TABLE `delivery_zones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `portion` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `restaurant_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qkhr2yo38c1g9n5ss0jl7gxk6` (`name`),
  KEY `FKkomdx99dhk2cveaxugl2lws2u` (`category_id`),
  KEY `FKm9xrxt95wwp1r2s7andom1l1c` (`restaurant_id`),
  CONSTRAINT `FKkomdx99dhk2cveaxugl2lws2u` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKm9xrxt95wwp1r2s7andom1l1c` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (3,'From bio oranges','Lemonade',30,30,4,5),(4,'From bio oranges','Orange juice',120,8,4,5),(5,'Cream soup with fresh tomatos and basil','Tomato Soup',200,20,2,5),(6,'Cream soup with fresh cauliflower and cumin','Cauliflower Soup',220,22,2,5),(7,'With cream cheese and cherries','Cheesecake',120,18,3,5),(8,'Caramel and banana cream cake','Banoffe pie',150,12,3,5),(10,'Egg salad on toast','Egg salad',150,12,1,5),(11,'With eggs, ham and cheese','Croque madame',150,10,1,5),(27,'3 pcs of cheese with assorted salad','Fried Cheese',430,13.75,2,26),(28,'With meatballs prepared by our own recipe, tomato sauce, parmesan cheese','Spaghetti and Meatballs',430,15.6,2,26),(29,'crispy chicken, fried potatos, cucumber sauce, garlic mayonnaise, salad','Shaorma wrap',550,16.2,1,26),(30,'With cherry jam','Jam Crepes',200,16,3,26),(31,'orange juice','Granini Orange',150,10,3,26),(38,'very bubbly','Sprite',100,6,4,37),(39,'Old-time favourite','Coca-Cola',250,5,4,37),(40,'Delicious apple tart with cinnamon, raisins and vanilla flavour.','Apple tart',200,12,3,37),(41,'Ingredients: pumpkin, garlic, onion, carrot','Pumpkin cream soup',250,19,2,37),(42,'Ingredients: Bun, beef,  cheese, onion, iceberg salad','Cheese Burger',400,23,2,37),(43,'Ingredients: tuna, cherry tomatoes, cucumber, iceberg salad','Tuna salad with egg',350,34,1,37);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foods_in_cart`
--

DROP TABLE IF EXISTS `foods_in_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foods_in_cart` (
  `cart_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `foods_key` int NOT NULL,
  PRIMARY KEY (`cart_id`,`foods_key`),
  KEY `FKb5he8l16yx3doex23vbd1t9kc` (`foods_key`),
  CONSTRAINT `FKb5he8l16yx3doex23vbd1t9kc` FOREIGN KEY (`foods_key`) REFERENCES `food` (`id`),
  CONSTRAINT `FKpm3w91vkfgwjur21y0yophvlm` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foods_in_cart`
--

LOCK TABLES `foods_in_cart` WRITE;
/*!40000 ALTER TABLE `foods_in_cart` DISABLE KEYS */;
INSERT INTO `foods_in_cart` VALUES (33,2,7);
/*!40000 ALTER TABLE `foods_in_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (58);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordered_foods`
--

DROP TABLE IF EXISTS `ordered_foods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_foods` (
  `order_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `ordered_foods_key` int NOT NULL,
  PRIMARY KEY (`order_id`,`ordered_foods_key`),
  KEY `FK8cp8a1gp78r7plncvimbnaxe2` (`ordered_foods_key`),
  CONSTRAINT `FK8cp8a1gp78r7plncvimbnaxe2` FOREIGN KEY (`ordered_foods_key`) REFERENCES `food` (`id`),
  CONSTRAINT `FKsgnrsci8n60klujavi3h4sep0` FOREIGN KEY (`order_id`) REFERENCES `restaurant_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordered_foods`
--

LOCK TABLES `ordered_foods` WRITE;
/*!40000 ALTER TABLE `ordered_foods` DISABLE KEYS */;
INSERT INTO `ordered_foods` VALUES (15,1,3),(15,1,5),(34,2,27),(34,2,29),(45,2,4),(45,2,6),(45,2,11),(46,2,27),(46,1,30),(47,3,39),(47,1,40),(47,1,42),(49,2,38),(49,1,43),(55,1,29);
/*!40000 ALTER TABLE `ordered_foods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` int NOT NULL,
  `address` varchar(255) NOT NULL,
  `closing_hour` int DEFAULT NULL,
  `delivery_fee` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `opening_hour` int DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i6u3x7opncroyhd755ejknses` (`name`),
  KEY `FKj37ybdgkcgg0xg4hgw2jmdoql` (`admin_id`),
  CONSTRAINT `FKj37ybdgkcgg0xg4hgw2jmdoql` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (5,'21 Decembrie 1989',20,10.7,'La casa',10,1),(26,'Calea Mănăștur 68',21,10,'Big belly',9,17),(37,'George Baritiu 56',16,12.5,'Avenger\'s diner',10,35);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_delivery_zones`
--

DROP TABLE IF EXISTS `restaurant_delivery_zones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_delivery_zones` (
  `restaurant_id` int NOT NULL,
  `zone_id` int NOT NULL,
  PRIMARY KEY (`restaurant_id`,`zone_id`),
  KEY `FKgv94wruu83cmell9fxa13ogye` (`zone_id`),
  CONSTRAINT `FKgv94wruu83cmell9fxa13ogye` FOREIGN KEY (`zone_id`) REFERENCES `delivery_zones` (`id`),
  CONSTRAINT `FKtntot2nieyv9ofb3nh2n3u00w` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_delivery_zones`
--

LOCK TABLES `restaurant_delivery_zones` WRITE;
/*!40000 ALTER TABLE `restaurant_delivery_zones` DISABLE KEYS */;
INSERT INTO `restaurant_delivery_zones` VALUES (5,2),(37,2),(5,3),(37,5),(26,6);
/*!40000 ALTER TABLE `restaurant_delivery_zones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_order`
--

DROP TABLE IF EXISTS `restaurant_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_order` (
  `id` int NOT NULL,
  `date_created` date NOT NULL,
  `delivery_address` varchar(100) NOT NULL,
  `order_number` varchar(6) NOT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `with_cutlery` bit(1) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_54opau4k4r7ev0rfyc0e1ii4w` (`order_number`),
  KEY `FKs8ln3y6f9q4bxxsqbev8tnly0` (`customer_id`),
  KEY `FK4a4c6bxp0ww8rtp8dj6cq92i5` (`restaurant_id`),
  CONSTRAINT `FK4a4c6bxp0ww8rtp8dj6cq92i5` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FKs8ln3y6f9q4bxxsqbev8tnly0` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_order`
--

LOCK TABLES `restaurant_order` WRITE;
/*!40000 ALTER TABLE `restaurant_order` DISABLE KEYS */;
INSERT INTO `restaurant_order` VALUES (15,'2022-04-08','Anna\'s home','778956','IN_DELIVERY','Hurry! I\'m hungry!',60.7,_binary '',10,5),(34,'2022-04-08','Anna\'s home','514622','IN_DELIVERY','',69.9,_binary '',10,26),(45,'2022-04-08','Dori\'s sweet home','898943','PENDING','I would like to receive my lunch before it gets cold!',90.7,_binary '',19,5),(46,'2022-04-08','Dori\'s home in Cluj','398794','DELIVERED','I am very hungry, please hurry!',53.5,_binary '\0',19,26),(47,'2022-04-08','Dori\'s home','767741','IN_DELIVERY','none',62.5,_binary '',19,37),(49,'2022-04-08','Anna\'s home','948021','DECLINED','Careful, I don\'t want my salad mixed up!',58.5,_binary '',10,37),(55,'2022-04-08','Dori\'s home town','941788','PENDING','Bring it in 10 min!',26.2,_binary '',19,26);
/*!40000 ALTER TABLE `restaurant_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$vZXAEIh3MVAkRj8Mqz7Qu.IRzybDvAmY4i979IfOSGP9tBxPDkEYe','ADMIN','bea'),(10,'$2a$10$Va/kmk7nohuDn9792oz1XO9LX4iND.Y115x2VOrhfEWfrgIBrcW6K','CUSTOMER','anna'),(17,'$2a$10$1B076iERNOVfoVnOdJeccetCKDpsugECWoDnXODD9Kv5ByB6tagA.','ADMIN','admin'),(19,'$2a$10$QF8K3ek45mLY9wKQ1aO3ZeSwHFDM.gfMNj/PP4jDRAqnafpRE1muK','CUSTOMER','dori'),(35,'$2a$10$o/netpAkrnMCR2Kv5ilvZuQmMDkQVqxODgLDah9DeO9ATT3sfePZq','ADMIN','captain@marvel');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_session` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs2btlvdomqggby8a3dnlq98ks` (`user_id`),
  CONSTRAINT `FKs2btlvdomqggby8a3dnlq98ks` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-08 22:03:48
