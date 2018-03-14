-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2018. Már 14. 18:36
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
-- Tábla szerkezet ehhez a táblához `deviza_arfolyamok`
--

DROP TABLE IF EXISTS `deviza_arfolyamok`;
CREATE TABLE IF NOT EXISTS `deviza_arfolyamok` (
  `datum` date NOT NULL,
  `eur` double NOT NULL,
  `usd` double NOT NULL,
  PRIMARY KEY (`datum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ertekpapirok`
--

DROP TABLE IF EXISTS `ertekpapirok`;
CREATE TABLE IF NOT EXISTS `ertekpapirok` (
  `ertekpapir_id` int(5) NOT NULL AUTO_INCREMENT,
  `ticker` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `ceg_nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `tozsde_id` int(5) NOT NULL,
  `tipus_id` int(5) NOT NULL,
  `isin` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `penznem_id` int(5) NOT NULL,
  PRIMARY KEY (`ertekpapir_id`),
  UNIQUE KEY `ticker` (`ticker`),
  KEY `tipus` (`tipus_id`),
  KEY `tozsde` (`tozsde_id`),
  KEY `penznem` (`penznem_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `ertekpapirok`
--

INSERT INTO `ertekpapirok` (`ertekpapir_id`, `ticker`, `ceg_nev`, `tozsde_id`, `tipus_id`, `isin`, `penznem_id`) VALUES
(1, 'MOL', 'Mol Nyrt', 1, 6, '', 1),
(2, 'OTP', 'Otp Nyrt', 1, 1, '123456', 1),
(5, 'AAPL', 'APLE', 2, 1, '1234', 3),
(6, 'TESLA', 'TESLA', 2, 12, '1234', 3),
(7, 'TELEKOM', 'Magyar Telekom Nyrt.', 1, 1, '1234', 1),
(8, 'ZWACK', 'ZWACK Nyrt', 1, 1, '1234', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `penznemek`
--

DROP TABLE IF EXISTS `penznemek`;
CREATE TABLE IF NOT EXISTS `penznemek` (
  `penznem_id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(5) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `teljes_nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`penznem_id`),
  UNIQUE KEY `nev` (`nev`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `penznemek`
--

INSERT INTO `penznemek` (`penznem_id`, `nev`, `teljes_nev`) VALUES
(1, 'HUF', 'Magyar Forint'),
(2, 'EUR', 'Euro'),
(3, 'USD', 'Amarikai dollár'),
(14, 'CAD', 'Kanadai dollár');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szamlak`
--

DROP TABLE IF EXISTS `szamlak`;
CREATE TABLE IF NOT EXISTS `szamlak` (
  `szamla_id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `adomentes` tinyint(1) NOT NULL,
  PRIMARY KEY (`szamla_id`),
  UNIQUE KEY `nev` (`nev`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `szamlak`
--

INSERT INTO `szamlak` (`szamla_id`, `nev`, `adomentes`) VALUES
(1, 'Normal', 0),
(2, 'TBSZ_2015', 1),
(6, 'TBSZ_2016', 1),
(7, 'TBSZ_2017', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `tipusok`
--

DROP TABLE IF EXISTS `tipusok`;
CREATE TABLE IF NOT EXISTS `tipusok` (
  `tipus_id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`tipus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `tipusok`
--

INSERT INTO `tipusok` (`tipus_id`, `nev`) VALUES
(1, 'Részvény'),
(2, 'Befektetési jegy'),
(6, 'Állampapír'),
(12, 'Kötvény');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `tozsdek`
--

DROP TABLE IF EXISTS `tozsdek`;
CREATE TABLE IF NOT EXISTS `tozsdek` (
  `tozsde_id` int(5) NOT NULL AUTO_INCREMENT,
  `nev` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `teljes_nev` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`tozsde_id`),
  UNIQUE KEY `nev` (`nev`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `tozsdek`
--

INSERT INTO `tozsdek` (`tozsde_id`, `nev`, `teljes_nev`) VALUES
(1, 'BET', 'Budapesti Értéktőzsde'),
(2, 'NASDAQ', 'nasdaq'),
(3, 'NYSE', 'NewYork');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ugyletek`
--

DROP TABLE IF EXISTS `ugyletek`;
CREATE TABLE IF NOT EXISTS `ugyletek` (
  `ugylet_id` int(5) NOT NULL AUTO_INCREMENT,
  `ertekpapir_id` int(5) NOT NULL,
  `szamla_id` int(5) NOT NULL,
  `mennyiseg` int(10) NOT NULL,
  `arfolyam` double NOT NULL,
  `datum` date NOT NULL,
  `jutalek` int(5) NOT NULL,
  `dev_arfolyam` double NOT NULL,
  PRIMARY KEY (`ugylet_id`),
  KEY `szamla_id` (`szamla_id`),
  KEY `ertekpapir_id` (`ertekpapir_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `ugyletek`
--

INSERT INTO `ugyletek` (`ugylet_id`, `ertekpapir_id`, `szamla_id`, `mennyiseg`, `arfolyam`, `datum`, `jutalek`, `dev_arfolyam`) VALUES
(1, 6, 1, 10, 310.2, '2018-03-08', 8, 250),
(3, 2, 2, 13, 1234, '2017-03-08', 8, 1),
(4, 1, 1, 20, 10000, '2018-03-09', 0, 1),
(5, 1, 1, 23, 10000, '2018-03-09', 0, 1),
(6, 1, 1, -20, 12000, '2018-03-09', 0, 1),
(7, 1, 6, -3, 10001, '2018-03-09', 0, 1),
(8, 1, 1, -11, 11000, '2018-03-09', 4, 1),
(9, 1, 1, -1, 20000, '2018-03-09', 0, 1),
(10, 6, 1, -10, 310, '2018-03-08', 8, 260);

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `ertekpapirok`
--
ALTER TABLE `ertekpapirok`
  ADD CONSTRAINT `ertekpapirok_ibfk_1` FOREIGN KEY (`tipus_id`) REFERENCES `tipusok` (`tipus_id`),
  ADD CONSTRAINT `ertekpapirok_ibfk_2` FOREIGN KEY (`penznem_id`) REFERENCES `penznemek` (`penznem_id`),
  ADD CONSTRAINT `ertekpapirok_ibfk_3` FOREIGN KEY (`tozsde_id`) REFERENCES `tozsdek` (`tozsde_id`);

--
-- Megkötések a táblához `ugyletek`
--
ALTER TABLE `ugyletek`
  ADD CONSTRAINT `ugyletek_ibfk_1` FOREIGN KEY (`ertekpapir_id`) REFERENCES `ertekpapirok` (`ertekpapir_id`),
  ADD CONSTRAINT `ugyletek_ibfk_2` FOREIGN KEY (`szamla_id`) REFERENCES `szamlak` (`szamla_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
