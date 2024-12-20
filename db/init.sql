-- Create the database
CREATE DATABASE IF NOT EXISTS storewise;

-- Use the database
USE storewise;

-- Create the `products` table
CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ProductGenCat` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ProductSpecificCat` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `StockLevel` int(11) NOT NULL,
  `expirationDate` date DEFAULT NULL,
  `Date&Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ProductID`),
  UNIQUE KEY `AddedTime_UNIQUE` (`Date&Time`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create the `equipments` table
CREATE TABLE `equipments` (
  `equipmentID` int(11) NOT NULL AUTO_INCREMENT,
  `equipmentName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `equipmentCategory` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `equipmentStatus` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AddedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`equipmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create the `users` table
CREATE TABLE `users` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `UserRole` enum('Admin','Employee') COLLATE utf8mb4_unicode_ci NOT NULL,
  `CreationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
  
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

-- Insert sample data into `maintenance_records`
INSERT INTO maintenance_records (equipmentID, maintenanceDate, details, nextMaintenanceDate) VALUES
('1','2024-12-20', 'Wiring', '2025-01-20');
('2','2024-12-20', 'Engine', '2025-01-20');
