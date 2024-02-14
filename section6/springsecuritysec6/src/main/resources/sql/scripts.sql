-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

CREATE DATABASE IF NOT EXISTS securebank;

USE securebank;

CREATE TABLE `customer` (
    `id` int NOT NULL AUTO_INCREMENT,
    `email` varchar(45) NOT NULL,
    `pwd` varchar(200) NOT NULL,
    `role` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `customer` (`email`, `pwd`, `role`)
    VALUES ('johndoe@example.com', '54321', 'admin');