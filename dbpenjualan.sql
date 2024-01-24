/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.4.32-MariaDB : Database - dbppenjualan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbppenjualan` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;

USE `dbppenjualan`;

/*Table structure for table `barang` */

DROP TABLE IF EXISTS `barang`;

CREATE TABLE `barang` (
  `kdbarang` char(10) NOT NULL,
  `namabarang` varchar(50) DEFAULT NULL,
  `stock` int(100) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  PRIMARY KEY (`kdbarang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `barang` */

insert  into `barang`(`kdbarang`,`namabarang`,`stock`,`harga`) values 
('B001','Air Aqua Galon',16,20000),
('B002','Air Mineral',9,15000),
('B003','Rokok Surya',10,30000),
('B004','Roti Aoka',9,2000),
('B005','Roti Coy',15,2000),
('B006','Mie Indomie ',6,3500);

/*Table structure for table `detailjual` */

DROP TABLE IF EXISTS `detailjual`;

CREATE TABLE `detailjual` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `nofaktur1` char(20) DEFAULT NULL,
  `kdbarang1` char(10) DEFAULT NULL,
  `hargajual` double DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `kdbarang` (`kdbarang1`),
  KEY `nofaktur1` (`nofaktur1`),
  CONSTRAINT `detailjual_ibfk_1` FOREIGN KEY (`kdbarang1`) REFERENCES `barang` (`kdbarang`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `detailjual` */

insert  into `detailjual`(`no`,`nofaktur1`,`kdbarang1`,`hargajual`,`qty`,`subtotal`) values 
(78,'TRX001','B001',20000,1,20000),
(79,'TRX001','B003',30000,2,60000),
(88,'TRX002','B004',2000,1,2000),
(89,'TRX002','B005',2000,1,2000),
(90,'TRX002','B006',3500,2,7000),
(93,'TRX003','B002',15000,1,15000),
(94,'TRX003','B006',3500,1,3500),
(95,'TRX004','B001',20000,1,20000),
(96,'TRX004','B006',3500,2,7000),
(97,'TRX005','B001',20000,1,20000),
(98,'TRX005','B002',15000,1,15000),
(100,'TRX006','B006',3500,2,7000),
(101,'TRX006','B005',2000,2,4000),
(102,'TRX007','B001',20000,1,20000),
(103,'TRX007','B002',15000,1,15000),
(111,'TRX008','B006',3500,2,7000),
(112,'TRX008','B005',2000,2,4000);

/*Table structure for table `konsumen` */

DROP TABLE IF EXISTS `konsumen`;

CREATE TABLE `konsumen` (
  `idkonsumen` char(10) NOT NULL,
  `namakonsumen` varchar(50) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `notelp` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`idkonsumen`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `konsumen` */

insert  into `konsumen`(`idkonsumen`,`namakonsumen`,`alamat`,`notelp`) values 
('K001','Herman','Cendana Mata Air  ',353503),
('K002','Anggun','Anduring',3580353),
('K003','Ariya','Jln.Sutan Syahrir',3053053),
('K004','Cece','Jln.Utama Banuaran Padang',353500),
('K005','Reza','Kolam Indah Padang',3535303),
('K006','Sudirman','Dadok Tunggul Hitam',3539539);

/*Table structure for table `manajemenuser` */

DROP TABLE IF EXISTS `manajemenuser`;

CREATE TABLE `manajemenuser` (
  `pengguna` varchar(50) DEFAULT NULL,
  `username` varchar(22) NOT NULL,
  `password` char(50) NOT NULL,
  `hakakses` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `manajemenuser` */

insert  into `manajemenuser`(`pengguna`,`username`,`password`,`hakakses`) values 
('Budi','budiadm','21232f297a57a5a743894a0e4a801fc3','Admin'),
('Citra','citraksr','c7911af3adbd12a035b289556d96470a','Kasir'),
('Burhanudin','nudinhrd','58399557dae3c60e23c78606771dfa3d','Pemilik'),
('Oki','okirmd ','21232f297a57a5a743894a0e4a801fc3','Admin');

/*Table structure for table `penjualan` */

DROP TABLE IF EXISTS `penjualan`;

CREATE TABLE `penjualan` (
  `nofaktur` char(20) NOT NULL,
  `tgltransaksi` date NOT NULL,
  `idkonsumen1` char(10) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `bayar` double DEFAULT NULL,
  `kembali` double DEFAULT NULL,
  PRIMARY KEY (`nofaktur`),
  KEY `idkonsumen` (`idkonsumen1`),
  CONSTRAINT `penjualan_ibfk_1` FOREIGN KEY (`idkonsumen1`) REFERENCES `konsumen` (`idkonsumen`) ON UPDATE CASCADE,
  CONSTRAINT `penjualan_ibfk_2` FOREIGN KEY (`idkonsumen1`) REFERENCES `konsumen` (`idkonsumen`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `penjualan` */

insert  into `penjualan`(`nofaktur`,`tgltransaksi`,`idkonsumen1`,`total`,`bayar`,`kembali`) values 
('TRX001','2024-01-18','K001',80000,100000,20000),
('TRX002','2024-01-18','K002',11000,12000,1000),
('TRX003','2024-01-18','K003',18500,20000,1500),
('TRX004','2024-01-18','K005',27000,30000,3000),
('TRX005','2024-01-19','K004',35000,40000,5000),
('TRX006','2024-01-19','K001',11000,12000,1000),
('TRX007','2024-01-19','K003',35000,35000,0),
('TRX008','2024-01-20','K006',11000,15000,4000);

/* Trigger structure for table `detailjual` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `tambahbrgjual` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `tambahbrgjual` AFTER INSERT ON `detailjual` FOR EACH ROW BEGIN
	UPDATE barang SET stock = stock - NEW.qty
    WHERE kdbarang = NEW.kdbarang1;
END */$$


DELIMITER ;

/* Trigger structure for table `detailjual` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `batalbrgjual` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `batalbrgjual` AFTER DELETE ON `detailjual` FOR EACH ROW BEGIN
    UPDATE barang SET stock = stock + OLD.qty
    WHERE kdbarang = OLD.kdbarang1;
END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
