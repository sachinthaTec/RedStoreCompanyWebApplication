-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.33 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table red_company_db.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `line_1` text NOT NULL,
  `line_2` text NOT NULL,
  `city_id` int NOT NULL,
  `postal_code` varchar(45) NOT NULL,
  `mobile` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `user_id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_city1_idx` (`city_id`),
  KEY `fk_address_user1_idx` (`user_id`),
  CONSTRAINT `fk_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_address_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.address: ~8 rows (approximately)
INSERT INTO `address` (`id`, `line_1`, `line_2`, `city_id`, `postal_code`, `mobile`, `user_id`, `first_name`, `last_name`) VALUES
	(4, 'No:125', 'Nugegoda', 1, '80808', '0758845688', 2, NULL, NULL),
	(10, 'No:1222', 'Nuwara', 1, '80815', '0771452366', 3, NULL, NULL),
	(11, 'No:714', 'Gangarama', 1, '80050', '0775556688', 5, NULL, NULL),
	(12, 'No:715', 'Rathgama', 2, '81050', '0776624517', 6, NULL, NULL),
	(13, 'No:716', 'Gangarama', 1, '80050', '0774445658', 14, NULL, NULL),
	(14, 'No:711', 'Gangarama', 1, '80045', '0778699985', 16, NULL, NULL),
	(15, 'No:414', 'GrandPas', 1, '80055', '0775552211', 17, NULL, NULL),
	(16, 'No:419', 'GrandPas', 1, '80000', '0778368844', 18, NULL, NULL),
	(17, 'No:714', 'Rathgama', 2, '81050', '0778369927', 19, NULL, NULL),
	(18, 'No:41', 'Grand ', 1, '80001', '0774145222', 20, NULL, NULL),
	(19, 'No:4177', 'GrandPas', 1, '80001', '0778369912', 21, NULL, NULL);

-- Dumping structure for table red_company_db.admin_signin
CREATE TABLE IF NOT EXISTS `admin_signin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.admin_signin: ~1 rows (approximately)
INSERT INTO `admin_signin` (`id`, `email`, `password`) VALUES
	(1, 'sguruge714@gmail.com', 'Sachintha@12345');

-- Dumping structure for table red_company_db.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.brand: ~6 rows (approximately)
INSERT INTO `brand` (`id`, `name`) VALUES
	(1, 'Apple'),
	(2, 'MSI'),
	(3, 'Asus'),
	(4, 'Samsung'),
	(5, 'NVIDIA'),
	(6, 'Sony'),
	(7, 'Intel');

-- Dumping structure for table red_company_db.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_user1_idx` (`user_id`),
  KEY `fk_cart_product1_idx` (`product_id`),
  CONSTRAINT `fk_cart_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_cart_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.cart: ~4 rows (approximately)
INSERT INTO `cart` (`id`, `qty`, `user_id`, `product_id`) VALUES
	(9, 1, 5, 8),
	(11, 1, 17, 8),
	(19, 1, 20, 12);

-- Dumping structure for table red_company_db.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.category: ~2 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Laptops'),
	(2, 'Components'),
	(3, 'Peripherals');

-- Dumping structure for table red_company_db.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.city: ~2 rows (approximately)
INSERT INTO `city` (`id`, `name`) VALUES
	(1, 'Colombo'),
	(2, 'Galle');

-- Dumping structure for table red_company_db.color
CREATE TABLE IF NOT EXISTS `color` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.color: ~2 rows (approximately)
INSERT INTO `color` (`id`, `value`) VALUES
	(1, 'Black'),
	(2, 'Red'),
	(3, 'White');

-- Dumping structure for table red_company_db.delivery_type
CREATE TABLE IF NOT EXISTS `delivery_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.delivery_type: ~2 rows (approximately)
INSERT INTO `delivery_type` (`id`, `name`, `price`) VALUES
	(1, 'Within Colombo', 100),
	(2, 'Out of Colombo', 250);

-- Dumping structure for table red_company_db.model
CREATE TABLE IF NOT EXISTS `model` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `brand_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_model_brand_idx` (`brand_id`),
  CONSTRAINT `fk_model_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.model: ~8 rows (approximately)
INSERT INTO `model` (`id`, `name`, `brand_id`) VALUES
	(1, 'Macbook air', 1),
	(2, 'AirPod max', 1),
	(3, 'MSI Titan', 2),
	(4, 'Zenbook', 3),
	(5, 'Sony-WH', 6),
	(6, 'Samsung_DDR5 RAM', 4),
	(7, 'Intel Processors', 7),
	(8, 'NVIDIA_GeForce', 5),
	(9, 'ASUS Vivobook', 3);

-- Dumping structure for table red_company_db.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `user_id` int NOT NULL,
  `address_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_user1_idx` (`user_id`),
  KEY `fk_order_address1_idx` (`address_id`),
  CONSTRAINT `fk_order_address1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_order_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.orders: ~8 rows (approximately)
INSERT INTO `orders` (`id`, `created_at`, `user_id`, `address_id`) VALUES
	(1, '2025-07-29 10:10:52', 5, 11),
	(2, '2025-07-29 10:16:29', 5, 11),
	(3, '2025-08-01 11:20:04', 14, 13),
	(4, '2025-08-05 17:53:58', 16, 14),
	(5, '2025-08-07 12:43:09', 16, 14),
	(6, '2025-08-08 13:59:27', 16, 14),
	(7, '2025-08-11 08:47:51', 18, 16),
	(8, '2025-08-12 09:46:41', 19, 17),
	(9, '2025-08-12 17:57:59', 19, 17),
	(10, '2025-08-14 07:23:19', 19, 17),
	(11, '2025-08-15 07:58:50', 16, 14),
	(12, '2025-08-15 18:48:51', 21, 19);

-- Dumping structure for table red_company_db.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` int NOT NULL,
  `orders_id` int NOT NULL,
  `order_status_id` int NOT NULL,
  `delivery_type_id` int NOT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_item_product1_idx` (`product_id`),
  KEY `fk_order_item_order_status1_idx` (`order_status_id`),
  KEY `fk_order_item_delivery_type1_idx` (`delivery_type_id`),
  KEY `fk_order_item_orders1_idx` (`orders_id`),
  CONSTRAINT `fk_order_item_delivery_type1` FOREIGN KEY (`delivery_type_id`) REFERENCES `delivery_type` (`id`),
  CONSTRAINT `fk_order_item_order_status1` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
  CONSTRAINT `fk_order_item_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.order_item: ~13 rows (approximately)
INSERT INTO `order_item` (`id`, `product_id`, `qty`, `orders_id`, `order_status_id`, `delivery_type_id`, `rating`) VALUES
	(1, 6, 2, 1, 2, 1, 0),
	(2, 3, 1, 1, 5, 1, 0),
	(3, 6, 1, 2, 5, 1, 0),
	(4, 7, 1, 3, 5, 1, 0),
	(5, 6, 3, 4, 1, 1, 0),
	(6, 8, 2, 5, 5, 1, 0),
	(7, 6, 1, 6, 2, 1, 0),
	(8, 8, 2, 7, 3, 1, 0),
	(9, 6, 2, 8, 5, 2, 0),
	(10, 8, 1, 9, 5, 2, 0),
	(11, 6, 1, 10, 4, 2, 0),
	(12, 3, 1, 11, 2, 1, 0),
	(13, 8, 1, 11, 5, 1, 0),
	(14, 12, 1, 12, 5, 1, 0);

-- Dumping structure for table red_company_db.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.order_status: ~4 rows (approximately)
INSERT INTO `order_status` (`id`, `value`) VALUES
	(1, 'Paid'),
	(2, 'Processing'),
	(3, 'Shipped'),
	(4, 'Delivered'),
	(5, 'Pending');

-- Dumping structure for table red_company_db.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `model_id` int NOT NULL,
  `description` text NOT NULL,
  `price` double NOT NULL,
  `qty` int NOT NULL,
  `color_id` int NOT NULL,
  `Category_id` int NOT NULL,
  `quality_id` int NOT NULL,
  `status_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_model1_idx` (`model_id`),
  KEY `fk_product_color1_idx` (`color_id`),
  KEY `fk_product_Category1_idx` (`Category_id`),
  KEY `fk_product_quality1_idx` (`quality_id`),
  KEY `fk_product_status1_idx` (`status_id`),
  CONSTRAINT `fk_product_Category1` FOREIGN KEY (`Category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_color1` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `fk_product_model1` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`),
  CONSTRAINT `fk_product_quality1` FOREIGN KEY (`quality_id`) REFERENCES `quality` (`id`),
  CONSTRAINT `fk_product_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.product: ~12 rows (approximately)
INSERT INTO `product` (`id`, `title`, `model_id`, `description`, `price`, `qty`, `color_id`, `Category_id`, `quality_id`, `status_id`, `created_at`) VALUES
	(1, 'MacBook Air M4 Chip 13 inch', 1, 'The 13-inch MacBook Air with the M4 chip offers a blend of performance, portability, and a stunning display, making it suitable for both everyday tasks and demanding workloads. It features a vibrant Liquid Retina display, long battery life, and a sleek, lightweight design. The M4 chip provides significant performance improvements over previous generations, especially for AI-based tasks and graphically intensive applications.', 380000, 12, 1, 1, 1, 2, '2025-07-21 19:09:39'),
	(2, 'MacBook Air M3 Chip 13 inch', 1, 'The 13-inch MacBook Air with the M3 chip is a superportable laptop featuring a powerful 8-core CPU and up to a 10-core GPU. It\'s designed for both work and play, offering up to 18 hours of battery life. The laptop boasts a brilliant 13.6-inch Liquid Retina display with support for one billion colors. It also includes a 1080p HD camera, three microphones, and four speakers with Spatial Audio for enhanced visuals and sound.', 290000, 8, 1, 1, 1, 2, '2025-07-21 19:15:34'),
	(3, 'ASUS ZenBook 14 OLED - 14TH Gen', 4, 'The ASUS Zenbook 14 OLED UX3405-581 is a sleek and powerful ultraportable laptop featuring a 14-inch OLED display and powered by an Intel Core Ultra 5 processor. It comes with 16GB of RAM and a 1TB NVMe SSD for storage. The laptop is designed for enhanced productivity and multimedia experiences, boasting features like a 120Hz refresh rate and a 0.2ms response time on the display. ', 4000, 3, 1, 1, 1, 2, '2025-07-21 19:22:45'),
	(4, 'Apple MacBook Air M4 Chip 14 inch', 1, 'The 14-inch MacBook Air with the M4 chip offers a blend of performance, portability, and a stunning display, making it suitable for both everyday tasks and demanding workloads. It features a vibrant Liquid Retina display, long battery life, and a sleek, lightweight design. The M4 chip provides significant performance improvements over previous generations, especially for AI-based tasks and graphically intensive applications.', 400000, 6, 1, 1, 1, 2, '2025-07-22 18:25:25'),
	(5, 'APPLE AIRPODS MAX - MIDNIGHT', 2, 'The AirPods Max in Midnight offer a high-fidelity audio experience with active noise cancellation and personalized spatial audio. The color is described as a dark navy metallic, almost black in some lighting. They feature a USB-C connector for charging and lossless audio when connected to compatible devices. The AirPods Max in Midnight offer a high-fidelity audio experience with active noise cancellation and personalized spatial audio. The color is described as a dark navy metallic, almost black in some lighting. They feature a USB-C connector for charging and lossless audio when connected to compatible devices. ', 145000, 14, 1, 3, 1, 2, '2025-07-24 17:59:10'),
	(6, 'WH-CH520 Wireless Headphones', 5, 'DSEEâ¢ restores musicality lost in compression\r\nFine-tune your sound using the Sony | Headphones Connect app\r\nLong-lasting listening with up to 50 hours of battery and quick charging\r\nBluetoothÂ® wireless technology for unrestricted movement\r\nEasy hands-free calling and voice assistant commands with microphone', 610, 8, 3, 3, 1, 2, '2025-07-24 18:14:19'),
	(7, 'WH-CH720N Wireless  Headphone', 5, 'Sonyâs lightest Wireless Noise-canceling headband ever.\r\nTake noise canceling to the next level with Sonyâs Integrated Processor V1, so you can fully immerse yourself in the music.\r\nSuper comfortable and lightweight design.\r\nAdjustable Ambient Sound mode and Adaptive Sound control features tailors sound to suit the environment around you.\r\nUp to 35-hour battery life with quick charging (3 min charge for up to 1 hour of playback).\r\nHigh sound quality and well-balanced sound tuning.\r\nCrystal clear hands-free calling and voice assistant with Precise Voice Pickup technology.\r\nMultipoint connection allows you to quickly switch between two devices at once.\r\nBoost the quality of compressed music files and enjoy streaming music with high quality sound through DSEEâ¢.\r\nBuild-in microphone', 45000, 21, 1, 3, 1, 2, '2025-07-24 18:21:12'),
	(8, 'Samsung DDR5 16GB 5600MHz Ram', 6, 'Samsung DDR5 16GB 5600MHz PC5-44800 262-Pins 1RX8 1.1V Laptop SODIMM Memory Ram, Memory Ram Features:\r\nÂ·     Type: DDR5 Sodimm DDR5 \r\nÂ·     Capacity: 16GB\r\nÂ·     Speed: PC5-44800 5600Mhz\r\nÂ·     Pin: 262-Pin \r\nÂ·     Voltage: 1.1V\r\nÂ·     Buffering/Ecc Fuction: Non-ECC Unbuffered\r\nÂ·     Warranty: Lifetime', 15500, 18, 1, 2, 1, 2, '2025-08-04 11:13:19'),
	(9, 'INTEL CORE I7 14700 14TH GEN ', 7, 'The Intel Core i7-14700 is a 14th-generation desktop processor featuring a hybrid architecture with 20 cores (8 Performance cores and 12 Efficient cores) and 28 threads. It supports both DDR4 and DDR5 memory, and is compatible with Intel 600 and 700 series chipsets, potentially requiring a BIOS update for the former. The processor offers a maximum Turbo Boost frequency of 5.4 GHz and includes Intel UHD Graphics 770', 115000, 12, 3, 2, 1, 2, '2025-08-04 11:17:13'),
	(10, 'NVIADIA  GeForce RTX 4090 24GB ', 8, 'GeForce RTXÂ® 4090 OC Edition 24GB GDDR6X, NVIDIA Ada Lovelace Streaming Multiprocessors: Up to 2x performance and power efficiency\r\n4th Generation Tensor Cores: Up to 2X AI performance\r\n3rd Generation RT Cores: Up to 2X ray tracing performance\r\nAxial-tech fans scaled up for 23% more airflow\r\nNew patented vapor chamber with milled heatspreader for lower GPU temps \r\n3.5-slot design: massive fin array optimized for airflow from the three Axial-tech fans\r\nDiecast shroud, frame, and backplate add rigidity and are vented to further maximize airflow and heat dissipation\r\nDigital power control with high-current power stages and 15K capacitors to fuel maximum performance\r\nAuto-Extreme precision automated manufacturing for higher reliability\r\nGPU Tweak III software provides intuitive performance tweaking, thermal controls, and system monitoring', 990000, 3, 1, 2, 1, 2, '2025-08-04 11:21:38'),
	(11, 'ASUS Vivobook - 13th Gen i3,', 9, '13th Gen\r\nProcessor	IntelÂ® Coreâ¢ i3-1315U Processor 1.2 GHz (10MB Cache, up to 4.5 GHz, 6 cores, 8 Threads)\r\nIntegrated GPU	IntelÂ® UHD Graphics\r\nStorage	256GB M.2 NVMeâ¢ PCIeÂ® 4.0 SSD\r\nMemory	8GB DDR4 on board\r\nMemory Slots	1x DDR4 SO-DIMM slot\r\nDisplay - Panel Size	15.6-inch\r\nDisplay - Resolution	FHD (1920 x 1080)\r\nDisplay - Refresh Rate	60 Hz\r\nDisplay - Aspect Ratio	16:9 aspect ratio', 130000, 6, 1, 1, 1, 2, '2025-08-09 08:17:33'),
	(12, 'MSI Titan 18 HX - Core I9 14th Gen', 3, '14th Gen Intel Core i9-14900HX Processor\r\n18â³ 16:10 UHD+ (3840Ã2400), MiniLED, 120Hz, 100% DCI-P3 (Typ.), IPS-Level panel\r\nNvidia GeForce RTX 4090, GDDR6 16GB\r\n128GB DDR5 (32GB*4) Ram\r\n4TB (2TB*2) NVMe PCIe Gen4x4 SSD\r\nIntelÂ® Killerâ¢ Wi-Fi 7 BE1750(x)\r\nWindows11 Pro High-End Standard Version', 23500, 8, 1, 1, 1, 2, '2025-08-11 08:58:57'),
	(13, 'MacBook Pro 16 inch (M4 Pro)', 1, 'The MacBook Pro 16 inch (M4 Pro) in Srilanka is Available at Rs 695000, updated on 2025-08-11 This 2024 model is powered by Appleâs M4 Pro chip with a 14-core CPU, 20-core GPU, and options of 24GB or 48GB Unified Memory and 512GB SSD storage LuxuryX provides genuine Apple products with a 1 Year AppleCare warrenty flexible installment options, and free delivery or in-store pickup across Sri Lanka.\r\n', 365000, 8, 1, 1, 1, 2, '2025-08-16 08:52:16');

-- Dumping structure for table red_company_db.quality
CREATE TABLE IF NOT EXISTS `quality` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.quality: ~2 rows (approximately)
INSERT INTO `quality` (`id`, `value`) VALUES
	(1, 'New'),
	(2, 'Used');

-- Dumping structure for table red_company_db.status
CREATE TABLE IF NOT EXISTS `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.status: ~2 rows (approximately)
INSERT INTO `status` (`id`, `value`) VALUES
	(1, 'Pending'),
	(2, 'Active');

-- Dumping structure for table red_company_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  `password` varchar(20) NOT NULL,
  `varification` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table red_company_db.user: ~19 rows (approximately)
INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `mobile`, `password`, `varification`, `created_at`) VALUES
	(1, 'Sachinthan', 'Guruge', 'shintha@gmail.com', '0778369939', 'Guruge@12345', 'Verified', '2025-07-17 18:36:05'),
	(2, 'Dinthi', 'Imasha', 'dinithi@gmail.com', '0758845688', 'Dinithi@1234500', 'Verified', '2025-07-18 17:07:47'),
	(3, 'Lakmal', 'Thinuka', 'lakmal@gmail.com', '0771452366', 'Lakmal@12345', 'Verified', '2025-07-19 15:33:27'),
	(4, 'Hashini', 'Nethma', 'hashini@gmail.com', '0776668899', 'Hashini@12345', 'Verified', '2025-07-19 20:57:27'),
	(5, 'Charaka', 'Dananjaya', 'charaka@gmail.com', '0775556688', 'Charaka@123456', 'Verified', '2025-07-23 08:04:51'),
	(6, 'Dilmi', 'Sanjana', 'dilmisanjanaguruge98@gmail.com', '0776624517', 'Dilmi@12345', 'Verified', '2025-07-29 10:24:08'),
	(7, 'Nilantha', 'Rathnayaka', 'rathnayaka@gmail.com', '0778367711', 'Rathnayaka@12345', 'Verified', '2025-08-01 10:08:34'),
	(8, 'Chathuara', 'Perera', 'chathura@gmail.com', '0778556992', 'Chathura@12345', 'Verified', '2025-08-01 10:16:20'),
	(9, 'Priyanga', 'Jeewani', 'priyanga@gmail.com', '0762585536', 'Priyanga@12345', 'Verified', '2025-08-01 10:31:24'),
	(10, 'Dilwarna', 'Guruge', 'dilwarna@gmail.com', '0774445689', 'Dilwarna@12345', 'Verified', '2025-08-01 10:48:14'),
	(11, 'Thevindu', 'Guruge', 'tevindu@gmail.com', '0771112233', 'Thevindu@12345', 'Verified', '2025-08-01 10:55:21'),
	(12, 'Vishmitha', 'Nirmana', 'vishmitha@gmail.com', '0775623896', 'Vishmitha@12345', 'Verified', '2025-08-01 11:03:02'),
	(13, 'Janidu', 'Silva', 'janidu@gmail.com', '0770002233', 'Janidu@12345', 'Verified', '2025-08-01 11:09:33'),
	(14, 'Ganga ', 'Guruge', 'ganga@gmail.com', '0774445658', 'Ganga@12345', 'Verified', '2025-08-01 11:13:36'),
	(15, 'Siril', 'Sirivardana', 'siril@gmail.com', '0772221245', 'Siril@12345', 'Verified', '2025-08-01 16:45:23'),
	(16, 'Suchini', 'Samarasingha', 'suchini@gmail.com', '0778699985', 'Suchini@12345', 'Verified', '2025-08-01 16:49:51'),
	(17, 'Guruge', 'Dilwarna', 'gurugedilwarna12@gmail.com', '0775552211', 'Guruge@12345', 'Verified', '2025-08-09 08:04:58'),
	(18, 'Sacha ', 'Gura', 'sachagura8011@gmail.com', '0778368844', 'Sacha@12345', 'Verified', '2025-08-11 08:45:00'),
	(19, 'Achini', 'Guruge', 'sachagur@gmail.com', '0778369927', 'Achini@12345', 'Verified', '2025-08-12 08:06:43'),
	(20, 'Jailo', 'Perera', 'sachagura80@gmail.com', '0774145222', 'Jailo@12345', 'Verified', '2025-08-15 10:04:36'),
	(21, 'Nilummm', 'Nimajaa', 'gurugedilwarna@gmail.com', '0778369912', 'Nilum@123456', 'Verified', '2025-08-15 18:44:44');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
