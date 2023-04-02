DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `factory` varchar(20) DEFAULT NULL,
  `repairs` int DEFAULT NULL,
  `model` varchar(20) DEFAULT NULL,
  `cardID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `bicycle`;
CREATE TABLE `bicycle` (
  `number` varchar(20) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `card_num` varchar(20) NOT NULL,
  `device` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `post` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `recording`;
CREATE TABLE `recording` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `fault` int DEFAULT '0',
  `number` varchar(20) DEFAULT NULL,
  `cost` varchar(20) DEFAULT NULL,
  `repair` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `temp`;
CREATE TABLE `temp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `type` varbinary(20) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `status` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;