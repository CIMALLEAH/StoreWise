# 🌟StoreWise: Inventory and Maintenance Management System🌟
*by Michaella P. Balagtas*🌞

## ☀️Project Overview
**StoreWise** is a Java-based inventory and maintenance management system designed to help stores manage their products and essential equipment. The application allows for the addition, deletion, and updating of store items, including tracking their stock levels, categories, and expiration dates. Additionally, it helps users identify low stock items and manage equipment maintenance. This system is ideal for small to medium-sized businesses that want to optimize inventory and equipment management processes.

## 🌈OOP Principles Applied
**StoreWise** effectively demonstrates the following Object-Oriented Programming (OOP) principles:

### 1. Encapsulation🌟
The system uses **private fields** and **public methods** (getters and setters) to control access to the data and behavior. For example, product and equipment details are encapsulated within their respective classes, and access to these details is controlled via methods.

### 2. Inheritance🌟
Several classes in **StoreWise** inherit from common parent classes. For example, equipment-related classes share common properties and methods, while still having specialized features through inheritance, ensuring code reuse and a clean hierarchy.

### 3. Polymorphism🌟
**StoreWise** employs polymorphism through method overloading and overriding. For example, methods for updating products and equipment can be called with different arguments based on the specific requirements, enabling flexibility in handling different types of inputs.

### 4. Abstraction🌟
Abstract classes and interfaces are used to hide implementation details while exposing key functionalities. For instance, database interactions are abstracted into specific methods, allowing users to work with higher-level logic rather than focusing on low-level details.

## 🎯Instructions to Run the Program

###  🌟Prerequisites:
- **Java Development Kit (JDK)** 8 or higher
- **MySQL Database**: Ensure MySQL is installed and running. You'll need to create a database for the project and set up the necessary tables (details provided below).

### 🛠️:Setting Up:

1. **Clone the repository** to your local machine:
    ```bash
    git clone https://github.com/CIMALLEAH/StoreWise.git
    cd StoreWise
    ```

2. **Import the project** into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

3. **Set up your MySQL Database**:
   - Open MySQL Workbench or your preferred MySQL client.
   - Create a new database by running the following SQL command:
     ```sql
     CREATE DATABASE storewise_db;
     ```
   - Import the database schema (provided in the `resources/database.sql` file) to create the necessary tables:
     ```sql
     USE storewise_db;
     SOURCE resources/database.sql;
     ```
   - This will create the required tables, such as `equipments`, `products`, and `maintenance_records`, in the `storewise_db` database.

4. **Update the Database Connection Settings**:
   - In the project, navigate to the file responsible for database connection (e.g., `DatabaseUtils.java` or wherever the database connection is set up).
   - Modify the connection settings (URL, username, password) to match your local MySQL configuration:
     ```java
     String dbUrl = "jdbc:mysql://localhost:3306/storewise_db";
     String dbUser = "root";   // Replace with your MySQL username
     String dbPassword = "";   // Replace with your MySQL password
     ```

### 🚀Running the Program:

Once the setup is complete, you can run the program using the following steps:

- **From your IDE**: Simply run the `StoreWise` class as a Java application.
  
- **Via Terminal**:
   1. Open the terminal and navigate to the project directory.
   2. Compile and run the program with these commands:
      ```bash
      javac StoreWise.java
      java StoreWise
      ```

### 🎮Using the Program:

Once the program is running, you will be presented with a console-based menu for managing products, equipment, and maintenance records. Follow the on-screen instructions to interact with the application, add products, schedule maintenance, and track your inventory.
