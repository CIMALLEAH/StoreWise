package StoreWise_OOP.Manage_Equipments;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import StoreWise_OOP.Utils;

public class EquipmentManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/storewise";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "MIKS";

    // Establish database connection
    private Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Add equipment
    public void addEquipment(Equipment equipment) {
        String query = "INSERT INTO equipments (equipmentName, equipmentCategory, equipmentStatus) VALUES (?, ?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, equipment.getName());
            statement.setString(2, equipment.getCategory());
            statement.setString(3, equipment.getStatus());
            statement.executeUpdate();

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Update equipment name in database
    private void updateEquipmentNameInDatabase(int ID, String newName) {
        String query = "UPDATE equipments SET equipmentName = ? WHERE equipmentName = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newName);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Equipment name updated successfully in the database.");
            } else {
                System.out.println("Error updating equipment name in the database.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }


    // Update equipment
    public void updateEquipment(int equipmentId, String newStatus) {
        String query = "UPDATE equipments SET equipmentStatus = ? WHERE equipmentID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newStatus);
            statement.setInt(2, equipmentId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Equipment updated successfully.");
            } else {
                System.out.println("Equipment not found.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Delete equipment
    public void deleteEquipment(int equipmentId) {
        String query = "DELETE FROM equipments WHERE equipmentID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, equipmentId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                Utils.displayHeader("Delete Equipment");
                Utils.displayMessage("Equipment deleted successfully.");
            } else {
                System.out.println("Equipment not found.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // View all equipment
    public ArrayList<Equipment> viewEquipments() {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipments";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            // Display header
            System.out.println(String.format("%-4s %-15s %-15s %-15s", "ID", "Name", "Category", "Status"));
            Utils.printLine(60);
    
            // Loop through results and display
            while (resultSet.next()) {
                int equipmentId = resultSet.getInt("equipmentID");
                String equipmentName = resultSet.getString("equipmentName");
                String equipmentCategory = resultSet.getString("equipmentCategory");
                String equipmentStatus = resultSet.getString("equipmentStatus");
    
                Equipment equipment = new Equipment(equipmentId, equipmentName, equipmentCategory, equipmentStatus);
                equipmentList.add(equipment);
    
                // Format each equipment as a row in the table
                System.out.println(String.format("%-4d %-15s %-15s %-15s", equipmentId, equipmentName, equipmentCategory, equipmentStatus));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return equipmentList;
    }

    // Add maintenance record
    public void addMaintenanceRecord(int equipmentId, LocalDate maintenanceDate, String details, LocalDate nextMaintenanceDate) {

        if (!isEquipmentIdValid(equipmentId)) {
            System.out.println("Invalid equipment ID. Maintenance record not added.");
            return;
        }
        String query = "INSERT INTO maintenance_records (equipmentID, maintenanceDate, details, nextMaintenanceDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectToDatabase();
        PreparedStatement statement = connection.prepareStatement(query)) {
        
            statement.setInt(1, equipmentId);
            statement.setDate(2, Date.valueOf(maintenanceDate));
            statement.setString(3, details);
            if (nextMaintenanceDate != null) {
                statement.setDate(4, Date.valueOf(nextMaintenanceDate));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.executeUpdate();
            System.out.println("Maintenance record added successfully.");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    

    public void viewUpcomingMaintenance() {
        String query = "SELECT e.equipmentName, m.nextMaintenanceDate " +
                       "FROM maintenance_records m " +
                       "JOIN equipments e ON m.equipmentID = e.equipmentID " +
                       "WHERE m.nextMaintenanceDate >= CURRENT_DATE";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            Utils.displayHeader("Upcoming Maintenance");
            System.out.println(String.format("%-20s %-15s", "Equipment", "Next Maintenance Date"));
            Utils.printLine(60);
    
            while (resultSet.next()) {
                String equipmentName = resultSet.getString("equipmentName");
                LocalDate nextMaintenanceDate = resultSet.getDate("nextMaintenanceDate").toLocalDate();
                System.out.println(String.format("%-20s %-15s", equipmentName, nextMaintenanceDate));
            }
            Utils.printLine(60);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Helper method to handle SQLException
    private void handleSQLException(SQLException e) {
        System.err.println("SQLException occurred: " + e.getMessage());
        e.printStackTrace();  // Print the stack trace for debugging
    }

    // Check if equipment ID is valid in the database
    private boolean isEquipmentIdValid(int equipmentId) {
        String query = "SELECT COUNT(*) FROM equipments WHERE equipmentID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, equipmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    // Collect equipment details from the user
    private Equipment collectEquipmentDetails(Scanner scanner) {
        String name;
        String category;
        String status;
        Utils.displayHeader("Add Equipment");
        Utils.displayMessage("Loading...");
        while (true) {
            Utils.displayHeader("Add Equipment");
            System.out.println(" Equipment Categories:");
            String[] predefinedCategories = {"Electronics", "Furniture", "Tools", "Vehicles"};
            
            for (int i = 0; i < predefinedCategories.length; i++) {
                System.out.println("  " + (i + 1) + ". " + predefinedCategories[i]);
            }
            System.out.println("  " + (predefinedCategories.length + 1) + ". Other (Specify)");
            Utils.printLine(60);
            Utils.printCentered("Equipment Details");
            System.out.print(" Please select a category: ");

            int categoryChoice;
            if (scanner.hasNextInt()) {
                categoryChoice = scanner.nextInt();
                scanner.nextLine(); 
                if (categoryChoice >= 1 && categoryChoice <= predefinedCategories.length) {
                    category = predefinedCategories[categoryChoice - 1];
                } else if (categoryChoice == predefinedCategories.length + 1) {
                    System.out.print(" Please enter custom category: ");
                    category = scanner.nextLine().trim();
                    if (category.isEmpty()) {
                        Utils.displayMessage("Custom category cannot be empty. Please try again.");
                        continue;
                    }
                } else {
                    Utils.displayMessage("Invalid category choice. Please try again.");
                    continue;
                }
            } else {
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); 
                continue;
            }

            Utils.displayHeader("Add Equipment");
            Utils.printCentered("Equipment Details");
            System.out.println(" Equipment Category: " + category);
            System.out.print(" Please enter equipment name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                Utils.displayMessage("Equipment name cannot be empty. Please try again.");
                continue;
            }

            Utils.displayHeader("Add Equipment");
            System.out.println(" Equipment Status:");
            String[] predefinedStatuses = {"Available", "In Use", "Under Maintenance"};
        
            for (int i = 0; i < predefinedStatuses.length; i++) {
                System.out.println("  " + (i + 1) + ". " + predefinedStatuses[i]);
            }
            System.out.println("  " + (predefinedStatuses.length + 1) + ". Other (Specify)");
            Utils.printLine(60);
            Utils.printCentered("Equipment Details");
            System.out.println(" Equipment Category: " + category);
            System.out.println(" Equipment Name: " + name);
            System.out.print(" Please select a status: ");
            int statusChoice;
            if (scanner.hasNextInt()) {
                statusChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (statusChoice >= 1 && statusChoice <= predefinedStatuses.length) {
                    status = predefinedStatuses[statusChoice - 1];
                } else if (statusChoice == predefinedStatuses.length + 1) {
                    System.out.print(" Please enter custom status: ");
                    status = scanner.nextLine().trim();
                    if (status.isEmpty()) {
                        Utils.displayMessage("Custom status cannot be empty. Please try again.");
                        continue;
                    }
                } else {
                    Utils.displayMessage("Invalid status choice. Please try again.");
                    continue;
                }
            } else {
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            while (true) {
                Utils.displayHeader("Add Equipment");
                Utils.printCentered("Confirm Equipment Details");
                System.out.println(" Equipment Category: " + category);
                System.out.println(" Equipment Name: " + name);
                System.out.println(" Equipment Status: " + status);
                Utils.printLine(60);
                System.out.print(" Are you sure you want to add this equipment? (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("y")) {
                    Equipment equipment = new Equipment(statusChoice, name, category, status);
                    addEquipment(equipment);
                    Utils.displayHeader("Add Equipment");
                    Utils.displayMessage("Equipment(s) added successfully!");
                    manageEquipmentMenu(scanner);
                } else if (confirmation.equals("n")) {
                    Utils.displayHeader("Add Equipment");
                    Utils.displayMessage("Operation cancelled. Returning to menu...");
                    manageEquipmentMenu(scanner);
                } else {
                    Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    // Equipment management menu
    public void manageEquipmentMenu(Scanner scanner) {
        int choice = 0;

        while (true) {
            Utils.displayHeader("Manage Equipments");
            System.out.println(" Menu:");
            System.out.println("  1. Add Equipment");
            System.out.println("  2. Update Equipment");
            System.out.println("  3. Delete Equipment");
            System.out.println("  4. View Equipments");
            System.out.println("  5. Add Maintenance Record");
            System.out.println("  6. View Maintenance Records");
            System.out.println("  7. View Upcoming Maintenance");
            System.out.println("  8. Back to Admin Menu");
            Utils.printLine(60);
            System.out.print(" Select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (choice >= 1 && choice <= 8) {
                    switch (choice) {
                        case 1:
                            Utils.displayHeader("Add Equipments");
                            Utils.displayMessage("Loading.....");
                            Equipment equipment = collectEquipmentDetails(scanner);
                            addEquipment(equipment);
                            break;
                        case 2:
                            Utils.displayHeader("Update Equipments");
                            Utils.displayMessage("Loading.....");
                            updateEquipmentMenu(scanner);
                            break;
                        case 3:
                            Utils.displayHeader("Delete Equipments");
                            Utils.displayMessage("Loading.....");
                            deleteEquipmentMenu(scanner);
                            break;
                        case 4:
                            Utils.displayHeader("View Equipments");
                            Utils.displayMessage("Loading.....");
                            Utils.displayHeader("View Equipments");
                            viewEquipments();
                            Utils.printLine(60);
                            pauseUntilEnter(scanner);
                            break;
                        case 5:
                            Utils.displayHeader("Add Maintenance Records");
                            Utils.displayMessage("Loading.....");
                            addMaintenanceRecordMenu(scanner);
                            break;
                        case 6:
                            Utils.displayHeader("View Maintenance Records");
                            Utils.displayMessage("Loading.....");
                            viewMaintenanceRecords();
                            pauseUntilEnter(scanner);
                            break;
                        case 7:
                            Utils.displayHeader("View Upcoming Maintenance");
                            Utils.displayMessage("Loading.....");
                            viewUpcomingMaintenance();
                            pauseUntilEnter(scanner);
                            break;
                        case 8:
                            Utils.displayHeader("Manage Equipments");
                            Utils.displayMessage("Exiting.....");
                            return; // Exit menu
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } else {
                    Utils.displayHeader("Manage Equipments");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 6.");
                }
            } else {
                Utils.displayHeader("Manage Equipments");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    // Update equipment menu
    private void updateEquipmentMenu(Scanner scanner) {
        Utils.displayHeader("Update Equipment");
        viewEquipments();  // Display equipment
        Utils.printLine(60);
        System.out.print(" Enter equipment ID to update: ");
        int equipmentId = getValidEquipmentId(scanner);  // Get valid equipment ID
        if (equipmentId == -1) return;  // If the ID is invalid, exit the method

        Equipment equipment = getEquipmentById(equipmentId);  // Find equipment by ID
        if (equipment == null) {
            Utils.displayMessage("Equipment not found.");
            return;
        }
        while (true) {
            Utils.displayHeader("Update Equipment");
            System.out.println(" What would you like to update?");
            System.out.println("  1. Equipment Name");
            System.out.println("  2. Equipment Status");
            System.out.println("  3. Cancel");
            Utils.printLine(60);
            System.out.print("Please select an option: ");
    
            int choice = -1; // Default invalid value

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.nextLine(); // Consume the invalid input if it's not an integer
                System.out.println("Invalid input. Please enter a valid number.");
            }
    
            switch (choice) {
                case 1:
                    updateEquipmentName(scanner, equipmentId, equipment);  // Update name
                    break; // Exit the loop after the update
                case 2:
                    updateEquipmentStatus(scanner, equipment);  // Update status
                    break; // Exit the loop after the update
                case 3:
                    System.out.println("Update canceled. Returning to menu...");
                    break; // Exit the loop if canceled
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
                }   
            }
        }

    private void updateEquipmentName(Scanner scanner, int equipmentID, Equipment equipment) {
        scanner.nextLine();  // Consume newline
        Utils.displayHeader("Update Equipment Name");
        System.out.print(" Enter new equipment name: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            Utils.displayHeader("Update Equipment Name");
            Utils.displayMessage("Equipment name retained.");
            return;
        }
        updateEquipmentNameInDatabase(equipmentID, newName);
        Utils.displayHeader("Update Equipment Name");
        Utils.displayMessage("Equipment name updated successfully.");
    }

    private void updateEquipmentStatus(Scanner scanner, Equipment equipment) {
        System.out.println("Current Status: " + equipment.getStatus());
        System.out.print(" Enter new equipment status: ");
        String newStatus = scanner.nextLine().trim();
        updateEquipment(equipment.getId(), newStatus);
        System.out.println("Equipment status updated successfully.");
    }

    // Find equipment by ID
    private Equipment getEquipmentById(int equipmentId) {
        String query = "SELECT * FROM equipments WHERE equipmentID = ?";
        try (Connection connection = connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, equipmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Equipment(
                    resultSet.getInt("equipmentID"),
                    resultSet.getString("equipmentName"),
                    resultSet.getString("equipmentCategory"),
                    resultSet.getString("equipmentStatus")
                );
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;  // Return null if equipment not found
    }


    // Delete equipment menu
    private void deleteEquipmentMenu(Scanner scanner) {
        Utils.displayHeader("Delete Equipment");
        viewEquipments();  // Display equipment
        Utils.printLine(60);
        System.out.print(" Enter equipment ID to delete: ");
        int equipmentId = getValidEquipmentId(scanner);
        if (equipmentId == -1) return;

        // Confirm deletion
        if (confirmDeletion(scanner, equipmentId)) {
            deleteEquipment(equipmentId);
        } else {
            System.out.println("Equipment deletion canceled. Returning to menu...");
        }
    }

    private int getValidEquipmentId(Scanner scanner) {
        int equipmentId = -1;
        if (scanner.hasNextInt()) {
            equipmentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input. Please enter a valid numeric ID.");
            scanner.nextLine(); // Consume invalid input
        }
    
        if (!isEquipmentIdValid(equipmentId)) {
            System.out.println("Equipment with ID " + equipmentId + " not found in the database. Returning to menu...");
            return -1;
        }
        return equipmentId;
    }

    private boolean confirmDeletion(Scanner scanner, int equipmentId) {
        Utils.displayHeader("Delete Equipment");
        viewEquipments();  // Display equipment
        Utils.printLine(60);
        System.out.print(" Equipment ID: " + equipmentId);
        System.out.print(" Delete this equipment? (Y/N): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        return confirmation.equals("y");
    }

    private void addMaintenanceRecordMenu(Scanner scanner) {
        // Step 1: Show all equipment
        Utils.displayHeader("Add Maintenance Record");
        viewEquipments();  // Display equipment list
        Utils.printLine(60);
        
        // Step 2: Get valid equipment ID
        System.out.print(" Enter equipment ID for maintenance: ");
        int equipmentId = getValidEquipmentId(scanner);
        if (equipmentId == -1) return;  // If the ID is invalid, return
    
        // Step 3: Get maintenance details
        System.out.print(" Enter maintenance date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate maintenanceDate;
        try {
            maintenanceDate = LocalDate.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        System.out.print(" Enter next maintenance date (YYYY-MM-DD): ");
        String nextDateInput = scanner.nextLine().trim();
        LocalDate nextMaintenanceDate = null;
        if (!nextDateInput.isEmpty()) {
        try {
            nextMaintenanceDate = LocalDate.parse(nextDateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format for next maintenance. Please try again.");
            return;
        }
    }

    
        System.out.print(" Enter maintenance details: ");
        String details = scanner.nextLine().trim();
        
        // Step 4: Confirm maintenance record addition
        Utils.displayHeader("Confirm Maintenance Record");
        Utils.printCentered("Maintenance Record Details");
        System.out.println(" Equipment ID: " + equipmentId);
        System.out.println(" Maintenance Date: " + maintenanceDate);
        System.out.println(" Details: " + details);
        Utils.printLine(60);
        System.out.print("  Add this maintenance record? (Y/N): ");
        
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y")) {
            Utils.displayHeader("Add Maintenance Record");
            Utils.displayMessage("Maintenance record added successfully.");
            addMaintenanceRecord(equipmentId, maintenanceDate, details, nextMaintenanceDate);
        } else if (confirmation.equals("n")) {
            Utils.displayHeader("Add Maintenance Record");
            System.out.println("Maintenance record addition canceled. Returning to menu...");
        } else {
            Utils.displayHeader("Add Maintenance Record");
            System.out.println("Invalid input. Returning to menu...");
        }
    }

    // View all maintenance records
public void viewMaintenanceRecords() {
    String query = "SELECT m.recordID, e.equipmentName, m.maintenanceDate, m.details, m.nextMaintenanceDate " +
                   "FROM maintenance_records m " +
                   "JOIN equipments e ON m.equipmentID = e.equipmentID";
    try (Connection connection = connectToDatabase();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        Utils.displayHeader("Maintenance Records");
        System.out.println(String.format("%-3s %-10s %-10s %-17s %-10s", "ID", "Equipment", "Date", "Details", "Next Maintenance"));
        Utils.printLine(60);

        while (resultSet.next()) {
            int recordID = resultSet.getInt("recordID");
            String equipmentName = resultSet.getString("equipmentName");
            LocalDate maintenanceDate = resultSet.getDate("maintenanceDate").toLocalDate();
            String details = resultSet.getString("details");
            LocalDate nextMaintenanceDate = resultSet.getDate("nextMaintenanceDate") != null 
                                             ? resultSet.getDate("nextMaintenanceDate").toLocalDate() 
                                             : null;

            System.out.println(String.format("%-3d %-10s %-10s %-17s %-10s", 
                recordID, equipmentName, maintenanceDate, details, 
                nextMaintenanceDate != null ? nextMaintenanceDate : "N/A"));
        }
        Utils.printLine(60);
    } catch (SQLException e) {
        handleSQLException(e);
    }
}

    public void pauseUntilEnter(Scanner scanner) {
        System.out.print("\n Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
    }
    
}