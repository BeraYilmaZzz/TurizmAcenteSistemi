-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 12 Kas 2023, 15:14:58
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `patikatez`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel`
--

CREATE TABLE `hotel` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `city` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `region` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adress` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `star` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel`
--

INSERT INTO `hotel` (`id`, `name`, `city`, `region`, `adress`, `email`, `phone`, `star`) VALUES
(104, 'Hotel Raffle', 'Muğla', 'Ege', 'Ege / Muğla', 'raffle@gmail.om', '123123123', '2'),
(105, 'Hotel Venice', 'İstanbul', 'Marmara', 'Marmara / İstanbul', 'Venice@gmail.om', '2323232', '5'),
(106, 'Patika Otel', 'İstanbul', 'Anadolu', 'Anadolu / İstanbul', 'patika@gmail.com', '123123123', ''),
(107, 'DENEME OTEL', 'İstanbul', 'Anadolu', 'Anadolu / İstanbul', 'patika@gmail.com', '123123123', ''),
(108, 'OTEL DENEME', 'Muğla', 'Ege', 'Ege/ Muğla', '123@gmail.com', '123123123', '2'),
(109, 'Adramis Hotel', 'Burhaniye', 'Marmara', 'Balıkesir / burhaniye', 'adramis@gmail.com', '123123123', '2');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel_pensions`
--

CREATE TABLE `hotel_pensions` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel_pensions`
--

INSERT INTO `hotel_pensions` (`id`, `name`) VALUES
(1, 'Ultra Herşey Dahil\r\n'),
(2, 'Herşey Dahil\r\n'),
(3, 'Oda Kahvaltı\r\n'),
(4, 'Tam Pansiyon\r\n'),
(5, 'Yarım Pansiyon\r\n'),
(6, 'Sadece Yatak\r\n'),
(7, 'Alkol Hariç Full credit\r\n');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel_pensions_relations`
--

CREATE TABLE `hotel_pensions_relations` (
  `hotel_id` int NOT NULL,
  `name_pensions_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel_pensions_relations`
--

INSERT INTO `hotel_pensions_relations` (`hotel_id`, `name_pensions_id`) VALUES
(2, 3),
(3, 6),
(3, 1),
(41, 6),
(42, 6),
(43, 6),
(44, 6),
(45, 4),
(46, 5),
(47, 2),
(47, 4),
(47, 6),
(48, 3),
(48, 4),
(49, 1),
(49, 3),
(50, 4),
(51, 4),
(52, 5),
(53, 4),
(53, 5),
(53, 6),
(55, 4),
(55, 5),
(56, 3),
(56, 4),
(56, 5),
(56, 6),
(57, 4),
(57, 5),
(57, 6),
(52, 7),
(60, 4),
(60, 5),
(61, 1),
(61, 2),
(61, 3),
(61, 4),
(61, 5),
(61, 6),
(61, 7),
(62, 2),
(62, 3),
(62, 4),
(63, 3),
(63, 4),
(65, 3),
(66, 3),
(66, 4),
(67, 3),
(67, 4),
(68, 3),
(69, 3),
(71, 1),
(71, 2),
(71, 3),
(71, 4),
(71, 5),
(71, 6),
(71, 7),
(72, 1),
(72, 2),
(73, 3),
(73, 4),
(74, 3),
(75, 2),
(75, 3),
(76, 2),
(76, 3),
(77, 1),
(77, 2),
(78, 6),
(79, 6),
(80, 4),
(80, 5),
(80, 6),
(83, 1),
(81, 6),
(81, 7),
(81, 5),
(82, 6),
(84, 5),
(85, 5),
(86, 5),
(87, 5),
(84, 4),
(88, 5),
(88, 6),
(91, 5),
(92, 2),
(93, 2),
(94, 1),
(95, 4),
(0, 2),
(97, 3),
(98, 2),
(98, 3),
(98, 1),
(99, 1),
(99, 2),
(100, 4),
(100, 5),
(100, 6),
(100, 2),
(101, 3),
(101, 4),
(102, 3),
(102, 4),
(102, 5),
(103, 3),
(103, 5),
(103, 6),
(103, 7),
(103, 4),
(104, 3),
(105, 3),
(105, 5),
(106, 1),
(106, 6),
(107, 1),
(107, 3),
(107, 6),
(108, 3),
(104, 2),
(109, 4),
(109, 3);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel_prop_relations`
--

CREATE TABLE `hotel_prop_relations` (
  `hotel_id` int NOT NULL,
  `feature_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel_prop_relations`
--

INSERT INTO `hotel_prop_relations` (`hotel_id`, `feature_id`) VALUES
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(4, 6),
(4, 7),
(1, 2),
(15, 1),
(15, 3),
(15, 4),
(20, 6),
(21, 6),
(27, 1),
(27, 2),
(27, 3),
(27, 4),
(28, 2),
(29, 1),
(29, 2),
(29, 3),
(29, 4),
(29, 5),
(20, 2),
(20, 3),
(0, 1),
(104, 6),
(105, 3),
(106, 3),
(106, 4),
(106, 5),
(106, 6),
(107, 3),
(107, 4),
(107, 5),
(107, 6),
(108, 3),
(108, 4),
(104, 5),
(109, 4),
(109, 5),
(109, 6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `oprop`
--

CREATE TABLE `oprop` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `oprop`
--

INSERT INTO `oprop` (`id`, `name`) VALUES
(1, 'Ücretsiz Otopark'),
(2, 'Ücretsiz WiFi\r\n'),
(3, 'Yüzme Havuzu\r\n'),
(4, 'Fitness Center\r\n'),
(5, 'Hotel Concierge\r\n'),
(6, 'SPA\r\n'),
(7, '7/24 Oda Servisi\r\n');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `periods`
--

CREATE TABLE `periods` (
  `id` int NOT NULL,
  `startDate` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `finishDate` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `periods`
--

INSERT INTO `periods` (`id`, `startDate`, `finishDate`) VALUES
(7, '12/10/2023', '01/12/2023'),
(19, '01/01/2023', '31/06/2023'),
(20, '31/06/2023', '31/12/2023'),
(21, '31/06/2022', '31/12/2022'),
(22, '01/01/2022', '31/06/2022'),
(23, '02/02/2022', '03/03/2022'),
(24, '05/05/2023', '07/07/2023');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `period_hotel_relations`
--

CREATE TABLE `period_hotel_relations` (
  `hotel_id` int NOT NULL,
  `period_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `period_hotel_relations`
--

INSERT INTO `period_hotel_relations` (`hotel_id`, `period_id`) VALUES
(2, 1),
(3, 2),
(2, 2),
(56, 7),
(52, 1),
(52, 2),
(56, 7),
(61, 7),
(78, 7),
(92, 7),
(94, 7),
(95, 7),
(100, 7),
(101, 7),
(102, 7),
(103, 7),
(104, 19),
(105, 22),
(106, 20),
(105, 19),
(105, 7),
(106, 22),
(104, 22),
(107, 7),
(108, 7),
(104, 21),
(109, 23),
(109, 21);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `reservations`
--

CREATE TABLE `reservations` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `note` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `room_id` int NOT NULL,
  `room_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pension` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `hotel_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `start_rez` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `finish_rez` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adult_num` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `total_adult_price` int NOT NULL,
  `child_num` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `total_child_price` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `reservations`
--

INSERT INTO `reservations` (`id`, `name`, `note`, `phone`, `room_id`, `room_name`, `pension`, `hotel_name`, `start_rez`, `finish_rez`, `adult_num`, `total_adult_price`, `child_num`, `total_child_price`) VALUES
(26, 'ali', 'nolur', '100ver', 27, 'Suit', 'Yarım Pansiyon\r\n', 'test', '02/12/2022', '12/12/2022', '5', 2500, '2', 8100),
(40, 'Bera Yılmaz', 'Notum yok', '12121212', 72, 'Suit', 'Oda Kahvaltı\r\n', 'Hotel Raffle', '07/07/2023', '09/07/2023', '5', 3000, '2', 600);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `roomlist`
--

CREATE TABLE `roomlist` (
  `hotel_id` int NOT NULL,
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `stock` int NOT NULL,
  `period_start` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `period_finish` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pension` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `bed_num` int NOT NULL,
  `tv` tinyint(1) NOT NULL,
  `minibar` tinyint(1) NOT NULL,
  `playstation` tinyint(1) NOT NULL,
  `msquare` int NOT NULL,
  `security_case` tinyint(1) NOT NULL,
  `projection` tinyint(1) NOT NULL,
  `adult_price` int NOT NULL DEFAULT '0',
  `child_price` int NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `roomlist`
--

INSERT INTO `roomlist` (`hotel_id`, `id`, `name`, `stock`, `period_start`, `period_finish`, `pension`, `bed_num`, `tv`, `minibar`, `playstation`, `msquare`, `security_case`, `projection`, `adult_price`, `child_price`) VALUES
(61, 14, 'YalıKavak', 4, '04/08/1999', '06/11/2023', 'Ultra Herşey Dahil\r\n', 2, 1, 1, 1, 140, 1, 0, 77, 34),
(61, 18, 'YalıKavak', 2, '04/08/1999', '06/11/2023', 'Herşey Dahil\r\n', 1, 1, 0, 0, 123, 0, 0, 50, 20),
(61, 19, 'YalıKavak', 9, '12/10/2023', '01/12/2023', 'Herşey Dahil\r\n', 2, 1, 0, 1, 12, 0, 0, 30, 10),
(61, 20, 'YalıKavak', 4, '04/08/1999', '06/11/2023', 'Oda Kahvaltı\r\n', 2, 1, 0, 1, 12, 0, 0, 120, 80),
(61, 21, 'YalıKavak', 2, '04/08/1999', '06/11/2023', 'Tam Pansiyon\r\n', 2, 1, 0, 1, 12, 0, 0, 30, 10),
(61, 22, 'YalıKavak', 12, '04/08/1999', '06/11/2023', 'Ultra Herşey Dahil\r\n', 2, 1, 0, 0, 120, 0, 0, 55, 15),
(61, 23, 'YalıKavak', 12, '12/10/2023', '01/12/2023', 'Alkol Hariç Full credit\r\n', 2, 1, 0, 0, 120, 0, 0, 55, 15),
(78, 24, 'Suit', 5, '04/08/1999', '06/11/2023', 'Sadece Yatak\r\n', 1, 1, 0, 1, 70, 1, 1, 220, 110),
(78, 25, 'Suit', 2, '12/10/2023', '01/12/2023', 'Sadece Yatak\r\n', 2, 1, 0, 1, 70, 0, 1, 160, 70),
(52, 30, 'Double', 3, '04/08/1999', '06/11/2023', 'Yarım Pansiyon\r\n', 1, 0, 0, 1, 500, 1, 0, 100, 45),
(52, 31, 'Double', 2, '04/08/1999', '06/11/2023', 'Yarım Pansiyon\r\n', 1, 0, 0, 1, 330, 1, 0, 100, 45),
(52, 32, 'Suit', 3, '04/08/1999', '06/11/2023', 'Alkol Hariç Full credit\r\n', 2, 1, 0, 0, 144, 1, 0, 200, 150),
(60, 33, 'Suit', 7, '04/08/1999', '06/11/2023', 'Tam Pansiyon\r\n', 3, 1, 0, 0, 260, 0, 0, 700, 500),
(60, 34, 'Double', 2, '04/08/1999', '06/11/2023', 'Yarım Pansiyon\r\n', 1, 0, 0, 1, 260, 0, 0, 500, 200),
(60, 35, 'Double', 6, '09/11/2023', '18/06/2024', 'Yarım Pansiyon\r\n', 1, 0, 0, 1, 200, 0, 0, 900, 400),
(60, 36, 'Suit', 2, '09/11/2023', '18/06/2024', 'Tam Pansiyon\r\n', 3, 1, 1, 1, 200, 1, 1, 1200, 700),
(61, 37, 'Suit', 2, '12/10/2023', '01/12/2023', 'Yarım Pansiyon\r\n', 3, 1, 0, 1, 444, 0, 0, 222, 111),
(78, 38, 'Suit', 4, '12/10/2023', '01/12/2023', 'Sadece Yatak\r\n', 2, 1, 1, 0, 222, 0, 0, 500, 200),
(92, 39, 'Suit', 5, '12/10/2023', '01/12/2023', 'Herşey Dahil\r\n', 2, 1, 0, 1, 600, 0, 0, 2000, 1300),
(92, 40, 'Double', 2, '12/10/2023', '01/12/2023', 'Herşey Dahil\r\n', 2, 1, 0, 1, 222, 0, 0, 222, 111),
(94, 41, 'Suit', 2, '12/10/2023', '01/12/2023', 'Ultra Herşey Dahil\r\n', 2, 1, 0, 1, 222, 0, 1, 222, 111),
(94, 42, 'Double', 1, '12/10/2023', '01/12/2023', 'Ultra Herşey Dahil\r\n', 1, 1, 0, 1, 111, 0, 1, 4444, 111),
(94, 43, 'Double', 3, '04/08/1999', '06/11/2023', 'Ultra Herşey Dahil\r\n', 3, 1, 0, 0, 3, 0, 0, 333, 222),
(95, 44, 'Suit', 5, '12/10/2023', '01/12/2023', 'Tam Pansiyon\r\n', 2, 1, 0, 1, 354, 0, 0, 224, 112),
(95, 45, 'Double', 2, '12/10/2023', '01/12/2023', 'Tam Pansiyon\r\n', 1, 1, 0, 1, 354, 0, 0, 444, 112),
(95, 46, 'Suit', 2, '04/08/1999', '06/11/2023', 'Tam Pansiyon\r\n', 1, 1, 0, 1, 354, 0, 0, 444, 112),
(95, 47, 'Double', 2, '04/08/1999', '06/11/2023', 'Tam Pansiyon\r\n', 1, 1, 0, 1, 354, 0, 0, 444, 112),
(95, 48, 'Double', 2, '09/11/2023', '18/06/2024', 'Tam Pansiyon\r\n', 1, 1, 0, 1, 354, 0, 0, 444, 112),
(95, 49, 'Suit', 1, '09/11/2023', '18/06/2024', 'Tam Pansiyon\r\n', 1, 1, 0, 1, 333, 0, 0, 5555, 112),
(52, 50, 'Suit', 9, '01/01/2023', '02/02/2023', 'Alkol Hariç Full credit\r\n', 5, 1, 1, 0, 120, 0, 0, 50, 20),
(98, 51, 'Double', 5, '04/08/1999', '06/11/2023', 'Oda Kahvaltı\r\n', 3, 1, 0, 1, 120, 0, 0, 300, 150),
(99, 52, 'Suit', 10, '04/08/1999', '06/11/2023', 'Ultra Herşey Dahil\r\n', 5, 0, 1, 1, 120, 1, 1, 100, 40),
(99, 53, 'Double', 3, '04/08/1999', '06/11/2023', 'Herşey Dahil\r\n', 2, 0, 1, 1, 120, 1, 1, 200, 80),
(99, 54, 'TEST', 2, '04/08/1999', '06/11/2023', 'Ultra Herşey Dahil\r\n', 3, 1, 0, 0, 120, 0, 1, 111, 222),
(99, 55, 'TEST', 2, '01/01/2022', '31/05/2023', 'Ultra Herşey Dahil\r\n', 3, 1, 0, 0, 120, 0, 1, 111, 222),
(99, 56, 'test', 12, '1/1/2022', '2/2/2022', 'Herşey Dahil\r\n', 2, 1, 0, 0, 122, 0, 0, 2, 2),
(101, 57, 'ODA 1 ', 5, '12/10/2023', '01/12/2023', 'Oda Kahvaltı\r\n', 2, 1, 1, 1, 120, 0, 0, 200, 100),
(101, 58, 'ODA 2', 7, '03/03/2023', '04/04/2023', 'Tam Pansiyon\r\n', 3, 1, 1, 1, 120, 0, 0, 100, 50),
(102, 59, 'double', 2, '05/05/2022', '05/07/2022', 'Tam Pansiyon\r\n', 4, 1, 0, 1, 123123, 0, 0, 200, 50),
(102, 60, 'suit', 2, '05/05/2022', '05/07/2022', 'Oda Kahvaltı\r\n', 4, 1, 0, 1, 123123, 0, 0, 200, 100),
(102, 61, 'suit', 2, '04/08/1999', '06/11/2023', 'Oda Kahvaltı\r\n', 4, 1, 0, 1, 123123, 0, 0, 300, 50),
(103, 62, 'Suit', 12, '12/10/2023', '01/12/2023', 'Oda Kahvaltı\r\n', 2, 1, 0, 1, 123, 0, 0, 120, 100),
(103, 63, 'Double', 12, '12/10/2023', '01/12/2023', 'Yarım Pansiyon\r\n', 2, 1, 0, 1, 123, 0, 0, 200, 100),
(103, 64, 'test', 12, '12/10/2023', '01/12/2023', 'Sadece Yatak\r\n', 2, 1, 0, 1, 123, 0, 0, 200, 100),
(103, 65, 'Suit', 12, '04/08/1999', '06/11/2023', 'Sadece Yatak\r\n', 2, 1, 0, 1, 123, 0, 0, 200, 100),
(103, 66, 'Double', 12, '04/08/1999', '06/11/2023', 'Sadece Yatak\r\n', 2, 1, 0, 1, 123, 0, 0, 200, 100),
(106, 67, 'Double', 10, '31/06/2023', '31/12/2023', 'Ultra Herşey Dahil\r\n', 4, 1, 0, 1, 120, 0, 1, 200, 100),
(106, 68, 'Suit', 3, '31/06/2023', '31/12/2023', 'Sadece Yatak\r\n', 4, 1, 1, 0, 150, 1, 1, 500, 200),
(105, 69, 'Double', 2, '01/01/2023', '31/06/2023', 'Yarım Pansiyon\r\n', 2, 1, 1, 1, 200, 0, 0, 2000, 200),
(105, 70, 'Suit', 7, '12/10/2023', '01/12/2023', 'Yarım Pansiyon\r\n', 4, 1, 1, 1, 120, 0, 0, 500, 200),
(104, 71, 'Double', 6, '01/01/2023', '31/06/2023', 'Oda Kahvaltı\r\n', 3, 1, 1, 0, 110, 0, 1, 700, 200),
(104, 72, 'Suit', 1, '01/01/2022', '31/06/2022', 'Oda Kahvaltı\r\n', 3, 1, 1, 0, 110, 0, 1, 300, 150),
(106, 73, 'Suit', 4, '31/06/2023', '31/12/2023', 'Sadece Yatak\r\n', 12, 1, 1, 0, 120, 1, 0, 120, 100),
(107, 74, 'TEST', 4, '12/10/2023', '01/12/2023', 'Ultra Herşey Dahil\r\n', 2, 1, 1, 0, 120, 0, 0, 200, 100),
(109, 75, 'Suit', 12, '02/02/2022', '03/03/2022', 'Tam Pansiyon\r\n', 5, 1, 1, 1, 120, 1, 0, 350, 70),
(109, 76, 'Double', 2, '31/06/2022', '31/12/2022', 'Tam Pansiyon\r\n', 5, 1, 1, 1, 120, 1, 0, 300, 75);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `uname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pass` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `type` enum('Admin','Acente') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `name`, `uname`, `pass`, `type`) VALUES
(15, 'Bera Yılmaz', 'bera', '123', 'Admin'),
(16, 'Acente', 'acente', '123', 'Acente'),
(18, 'Hızlı giriş', 's', 's', 'Acente'),
(19, 'Acente 2 ', 'acente2', '123', 'Acente');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `hotel_pensions`
--
ALTER TABLE `hotel_pensions`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `oprop`
--
ALTER TABLE `oprop`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `periods`
--
ALTER TABLE `periods`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `roomlist`
--
ALTER TABLE `roomlist`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- Tablo için AUTO_INCREMENT değeri `hotel_pensions`
--
ALTER TABLE `hotel_pensions`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `oprop`
--
ALTER TABLE `oprop`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `periods`
--
ALTER TABLE `periods`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Tablo için AUTO_INCREMENT değeri `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- Tablo için AUTO_INCREMENT değeri `roomlist`
--
ALTER TABLE `roomlist`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
