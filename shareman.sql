-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2018. Feb 18. 12:58
-- Kiszolgáló verziója: 5.7.19
-- PHP verzió: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `shareman`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ertekpapirok`
--

DROP TABLE IF EXISTS `ertekpapirok`;
CREATE TABLE IF NOT EXISTS `ertekpapirok` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `ceg_nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `tozsde` int(5) NOT NULL,
  `tipus` int(5) NOT NULL,
  `isin_kod` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `penznem` int(5) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tipus` (`tipus`),
  KEY `tozsde` (`tozsde`),
  KEY `penznem` (`penznem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ertekpapir_tipusok`
--

DROP TABLE IF EXISTS `ertekpapir_tipusok`;
CREATE TABLE IF NOT EXISTS `ertekpapir_tipusok` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `ertekpapir_tipusok`
--

INSERT INTO `ertekpapir_tipusok` (`id`, `nev`) VALUES
(1, 'Részvény'),
(2, 'Befektetési jegy'),
(3, 'Normál'),
(4, 'TBSZ_2015'),
(5, 'Normál'),
(6, 'Részvény'),
(7, 'Normál'),
(8, 'normal'),
(9, 'jkl');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `penznemek`
--

DROP TABLE IF EXISTS `penznemek`;
CREATE TABLE IF NOT EXISTS `penznemek` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(5) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `teljes_nev` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `penznemek`
--

INSERT INTO `penznemek` (`id`, `nev`, `teljes_nev`) VALUES
(1, 'HUF', 'Magyar Forint'),
(2, 'EUR', 'Euro'),
(3, 'USD', 'Amarikai dollár'),
(8, 'JKL', 'jkljkl'),
(9, 'DR', 'Dori'),
(10, 'FRT', 'frtfrt'),
(11, 'TUF', 'tuftuf');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szamlak`
--

DROP TABLE IF EXISTS `szamlak`;
CREATE TABLE IF NOT EXISTS `szamlak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `szamlak`
--

INSERT INTO `szamlak` (`id`, `nev`) VALUES
(1, 'jkjjkjjkj');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `tozsdek`
--

DROP TABLE IF EXISTS `tozsdek`;
CREATE TABLE IF NOT EXISTS `tozsdek` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `teljes_nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `tozsdek`
--

INSERT INTO `tozsdek` (`id`, `nev`, `teljes_nev`) VALUES
(1, 'BET', 'Budapesti Értéktőzsde'),
(2, 'NASDAQ', 'dsjakljdslaa');

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `ertekpapirok`
--
ALTER TABLE `ertekpapirok`
  ADD CONSTRAINT `ertekpapirok_ibfk_1` FOREIGN KEY (`tipus`) REFERENCES `ertekpapir_tipusok` (`id`),
  ADD CONSTRAINT `ertekpapirok_ibfk_2` FOREIGN KEY (`penznem`) REFERENCES `penznemek` (`id`),
  ADD CONSTRAINT `ertekpapirok_ibfk_3` FOREIGN KEY (`tozsde`) REFERENCES `tozsdek` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
