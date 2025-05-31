DROP DATABASE IF EXISTS vetcare_db;
CREATE DATABASE vetcare_db;
USE vetcare_db;

-- Tbla de Veterinarios
CREATE TABLE Vet (
    vetId INT NOT NULL AUTO_INCREMENT,
    passwordVet VARCHAR(100) NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    numberPhone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (vetId)
);

-- Tabla de Administradoes
CREATE TABLE Admi (
    adminId INT NOT NULL AUTO_INCREMENT,
    passwordAdmin VARCHAR(100) NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    numberPhone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (adminId)
);

-- Tabla de Clintes
CREATE TABLE Customer (
    customerId INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(100) NOT NULL,
    PRIMARY KEY (customerId)
);

-- Tabla de Mascotas
CREATE TABLE Pet (
    petId INT NOT NULL AUTO_INCREMENT,
    customerId INT NOT NULL,
    petName VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    sex BOOLEAN NOT NULL,
    birthday DATE NOT NULL,
    PRIMARY KEY (petId),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

-- Tabla de Productos
CREATE TABLE Product (
 productId INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
description TEXT,
type VARCHAR(100),
brand VARCHAR(100),
packaging VARCHAR(100),
salePrice DOUBLE NOT NULL,
cost DOUBLE,
stock INT NOT NULL,
minimumStock INT,
expirationDate DATE,
batch VARCHAR(100),
PRIMARY KEY (productId)
);

-- Tabla de Alarmas
CREATE TABLE Alarm (
    alarmId INT NOT NULL AUTO_INCREMENT,
    productId INT NOT NULL,
    threshold INT NOT NULL,
    message TEXT,
    PRIMARY KEY (alarmId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Tabla de Citas
CREATE TABLE Appointment (
    appointmentId INT NOT NULL AUTO_INCREMENT,
    customerId INT NOT NULL,
    petId INT NOT NULL,
    vetId INT NOT NULL,
    appointmentDate DATE NOT NULL,
    appointmentTime TIME NOT NULL,
    reason TEXT,
    status VARCHAR(50) DEFAULT 'Pendiente',
    PRIMARY KEY (appointmentId),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (petId) REFERENCES Pet(petId),
    FOREIGN KEY (vetId) REFERENCES Vet(vetId)
);


