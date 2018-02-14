-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 14, 2018 alle 18:40
-- Versione del server: 10.1.21-MariaDB
-- Versione PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `preview`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `conferences`
--

CREATE TABLE `conferences` (
  `conference_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `conference_abstract` text NOT NULL,
  `image` varchar(250) NOT NULL,
  `pdf` varchar(250) NOT NULL,
  `city` varchar(25) NOT NULL,
  `date` date NOT NULL,
  `latitude` decimal(20,8) NOT NULL,
  `longitude` decimal(20,8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `conferences`
--

INSERT INTO `conferences` (`conference_id`, `name`, `conference_abstract`, `image`, `pdf`, `city`, `date`, `latitude`, `longitude`) VALUES
('2', 'ICSR 2018', 'The International Conference on Software Reuse (ICSR) is the premier event in the field of software reuse research and technology. The main goal of ICSR is to present the most recent advances and breakthroughs in the area of software reuse and to promote  an intensive and continuous exchange among researchers and practitioners. The theme issue of this year focuses on “New Opportunities for Software Reuse”. ICSR is an old conference which has a long tradition in the Software Engineering field. This year w would like to bring new ideas and and approaches that can increase the visibility of Reuse, as first-class research topic, integarted under other SE disciplines and processes.', 'http://www.conference.city/fb_post_img.php?e_id=166303', 'https://www.ages.at/download/0/0/4c7ec3a83f2ec2abdf4c97df16836c7439e9d558/fileadmin/AGES2015/Service/AGES-Akademie/2018-02-05-07_EudraVigilance_Course/18502_EV_Vienna.pdf', 'Madrid', '2018-05-21', '40.41677500', '-3.70379000'),
('2880vh09seqo9s5g875ifsnflr', 'ICPE 2018', 'The goal of the ACM/SPEC International Conference on Performance Engineering (ICPE) is to integrate theory and practice in the field of performance engineering by providing a forum for sharing ideas and experiences between industry and academia. Nowadays, complex systems of all types, like Web-based systems, data centers and cloud infrastructures, social networks, peer-to-peer, mobile and wireless systems, cyber-physical systems, the Internet of Things, real-time and embedded systems, have increasingly distributed and dynamic system architectures that provide high flexibility, however, also increase the complexity of managing end-to-end application performance. ICPE brings together researchers and industry practitioners to share and present their experiences, discuss challenges, and report state-of-the-art and in-progress research on performance engineering of software and systems, including performance measurement, modeling, benchmark design, and run-time performance management. The focus of ICPE is both on classical metrics such as response time, throughput, resource utilization, and (energy) efficiency, as well as on the relationship to other system properties including but not limited to scalability, elasticity, availability, reliability, and security.', 'https://icpe2018.spec.org/uploads/media/ICPEbannerBerlinGendarmenmarkt_01.jpg', 'http://iesa2018.ipk.fraunhofer.de/fileadmin/user_upload/IESA2018/Documents/IESA_2018_cfp_annoucement.pdf', 'Berlin', '2018-04-09', '52.52000700', '13.40495400');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `conferences`
--
ALTER TABLE `conferences`
  ADD PRIMARY KEY (`conference_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
