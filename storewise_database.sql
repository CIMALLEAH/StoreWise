-- Create the database
CREATE DATABASE IF NOT EXISTS storewise;

-- Use the database
USE storewise;

-- Create the `products` table
CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(50) COLLATE NOT NULL,
  `ProductGenCat` varchar(50) COLLATE NOT NULL,
  `ProductSpecificCat` varchar(50) COLLATE NOT NULL,
  `StockLevel` int(11) NOT NULL,
  `expirationDate` date DEFAULT NULL,
  `AddedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ProductID`),
  UNIQUE KEY `AddedTime_UNIQUE` (`AddedTime`)
);

-- Create the `equipments` table
CREATE TABLE `equipments` (
  `equipmentID` int(11) NOT NULL AUTO_INCREMENT,
  `equipmentName` varchar(255) COLLATE NOT NULL,
  `equipmentCategory` varchar(255) COLLATE NOT NULL,
  `equipmentStatus` varchar(50) COLLATE NOT NULL,
  `AddedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`equipmentID`)
);

-- Create the `maintenance_records` table
CREATE TABLE `maintenance_records` (
  `recordID` int(11) NOT NULL AUTO_INCREMENT,
  `equipmentID` int(11) NOT NULL,
  `maintenanceDate` date NOT NULL,
  `details` text COLLATE utf8mb4_unicode_ci,
  `nextMaintenanceDate` date DEFAULT NULL,
  `addedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`recordID`),
  KEY `equipmentID` (`equipmentID`),
  CONSTRAINT `maintenance_records_ibfk_1` FOREIGN KEY (`equipmentID`) REFERENCES `equipments` (`equipmentID`) ON DELETE CASCADE
);

-- Create the `users` table
CREATE TABLE `users` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `UserRole` enum('Admin','Employee') COLLATE utf8mb4_unicode_ci NOT NULL,
  `CreationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `UserName` (`UserName`)
) 
  
-- Insert sample data into 'products'
INSERT INTO products (productName, productGenCat, productSpecificCat, stockLevel, expirationDate) VALUES
('Milk', 'Groceries', 'Dairy Products', 50, '2024-01-15'),
('Tomato','Groceries', 'Fresh Produce', 50, '2024-01-15'),
('Eggs','Groceries', 'Dairy Products', 50, '2024-01-15');

-- Insert sample data into 'equipment'
INSERT INTO equipments (equipmentName, equipmentCategory, equipmentStatus) VALUES
('Refrigerator', 'Electronics', 'In Use'),
('Oven', 'Electronics', 'In Use');

-- Insert sample data into `users`
INSERT INTO users (UserName, Password, UserRole) VALUES
('Will', '03221971', 'Admin'),
('Mike', '04071971', 'Employee'),


