-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 25, 2022 at 09:05 PM
-- Server version: 5.7.31
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clinique221`
--

-- --------------------------------------------------------

--
-- Table structure for table `antecmedicaux`
--

DROP TABLE IF EXISTS `antecmedicaux`;
CREATE TABLE IF NOT EXISTS `antecmedicaux` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `antecmedicaux`
--

INSERT INTO `antecmedicaux` (`id`, `libelle`, `description`) VALUES
(1, 'Diabète', 'Description diabète'),
(2, 'Hypertension', 'Description hypertension'),
(3, 'Hépatite', 'Description Hépatite'),
(4, 'Asthme', 'Description asthme');

-- --------------------------------------------------------

--
-- Table structure for table `constantes`
--

DROP TABLE IF EXISTS `constantes`;
CREATE TABLE IF NOT EXISTS `constantes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idpatient` int(11) NOT NULL,
  `temperature` int(11) NOT NULL,
  `tension` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `constantes`
--

INSERT INTO `constantes` (`id`, `idpatient`, `temperature`, `tension`) VALUES
(1, 1, 18, 38),
(2, 14, 30, 30);

-- --------------------------------------------------------

--
-- Table structure for table `medicaments`
--

DROP TABLE IF EXISTS `medicaments`;
CREATE TABLE IF NOT EXISTS `medicaments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idpatient` int(11) NOT NULL,
  `idmedecin` int(11) DEFAULT NULL,
  `consouprest` varchar(255) NOT NULL,
  `typeprest` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `statut` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rendezvous`
--

INSERT INTO `rendezvous` (`id`, `idpatient`, `idmedecin`, `consouprest`, `typeprest`, `date`, `statut`) VALUES
(26, 1, 15, 'Prestation', 'Analyse', '2021-11-30', 'Faite'),
(29, 14, 15, 'Prestation', 'Vaccination', '2021-11-30', 'Faite'),
(31, 14, 0, 'Prestation', 'Biopsie', '2021-11-29', 'En attente'),
(32, 14, 15, 'Prestation', 'Vaccination', '2022-01-21', 'Valide'),
(33, 14, 0, 'Prestation', 'IRM', '2022-01-26', 'En attente'),
(37, 1, 0, 'Consultation', NULL, '2021-12-12', 'En attente'),
(35, 14, 15, 'Consultation', '', '2022-01-26', 'Valide'),
(36, 14, 2, 'Prestation', 'Radiotherapie', '2022-01-26', 'Decline'),
(38, 1, 0, 'Prestation', 'Biopsie', '2021-11-19', 'En attente'),
(39, 1, 0, 'Prestation', 'Vaccination', '2021-12-02', 'En attente'),
(40, 1, 0, 'Consultation', '', '2021-12-29', 'En attente'),
(41, 1, 0, 'Prestation', 'IRM', '2021-12-31', 'En attente'),
(42, 1, 0, 'Consultation', '', '2021-12-30', 'En attente'),
(43, 1, 15, 'Consultation', NULL, '2021-12-22', 'En attente'),
(44, 1, 15, 'Prestation', 'Biopsie', '2021-12-30', 'En attente');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `dateInscription` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` int(11) NOT NULL DEFAULT '1',
  `idAntecMed` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nom`, `prenom`, `role`, `mail`, `mdp`, `dateInscription`, `actif`, `idAntecMed`) VALUES
(1, 'Ndiaye', 'Tidiane', 'ROLE_PATIENT', 'patient', '1234', '2021-11-01 17:13:58', 1, 4),
(3, 'Diop', 'Mareme', 'ROLE_SECRETAIRE', 'secretaire', '1234', '2021-11-07 12:23:06', 1, NULL),
(4, 'Ba', 'Amadou', 'ROLE_RESP_PREST', 'responsable', '1234', '2021-11-07 12:23:06', 1, NULL),
(2, 'Diouf', 'Moussa', 'ROLE_MEDECIN', 'medecin', '1234', '2021-11-01 17:13:58', 1, NULL),
(14, 'Timera', 'Abdalla', 'ROLE_PATIENT', 'patient2', '1234', '2021-11-01 17:13:58', 1, NULL),
(15, 'Ndiaye', 'Momar', 'ROLE_MEDECIN', 'medecin2', '1234', '2021-11-01 17:13:58', 1, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
