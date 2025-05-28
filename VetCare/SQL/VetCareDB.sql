DROP DATABASE IF EXISTS VetCare_DataBase;
CREATE DATABASE VetCare_DataBase;
USE VetCare_DataBase;

CREATE TABLE Vet(
	vetId VARCHAR(100) NOT NULL,
    passwordVet VARCHAR(100) NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    numberPhone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (vetId)
);

CREATE TABLE Admi(
	adminId VARCHAR(100) NOT NULL,
    passwordAdmin VARCHAR(100) NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    numberPhone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (adminId)
);

CREATE TABLE Customer(
	customerId VARCHAR(100) NOT NULL,
    fistName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(100) NOT NULL,
    PRIMARY KEY(customerId)
);

CREATE TABLE Pet(
	petId VARCHAR(100) NOT NULL,
    petName VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    sex BOOLEAN NOT NULL,
    birthday DATE NOT NULL,
    PRIMARY KEY(petId),
    FOREIGN KEY (customerId) REFERENCEs Customer(customerId)
);
CREATE TABLE Product (
    productId VARCHAR(100) NOT NULL,
    productName VARCHAR(100) NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (productId)
);
CREATE TABLE Alarm (
    alarmId VARCHAR(100) NOT NULL,
    productId VARCHAR(100) NOT NULL,
    threshold INT NOT NULL, 
    message TEXT,
    PRIMARY KEY (alarmId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);


