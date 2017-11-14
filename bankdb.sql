-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3302
-- Generation Time: Nov 14, 2017 at 11:33 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bankdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `ClientID` int(11) NOT NULL,
  `ClientName` varchar(15) DEFAULT NULL,
  `Balance` int(11) NOT NULL,
  `DematID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ClientID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`ClientID`, `ClientName`, `Balance`, `DematID`) VALUES
(1, 'A', 100, 21),
(3, 'C', 300, 23),
(2, 'B', 200, 22);

-- --------------------------------------------------------

--
-- Table structure for table `demat`
--

DROP TABLE IF EXISTS `demat`;
CREATE TABLE IF NOT EXISTS `demat` (
  `DematID` int(11) NOT NULL,
  `ShareName` varchar(255) DEFAULT NULL,
  `ShareQty` int(11) DEFAULT NULL,
  PRIMARY KEY (`DematID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `demat`
--

INSERT INTO `demat` (`DematID`, `ShareName`, `ShareQty`) VALUES
(21, 'ABC', 100),
(22, 'LMN', 200),
(23, 'XYZ', 300);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
