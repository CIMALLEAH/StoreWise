package StoreWise_OOP.Manage_Equipments;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class EquipmentManager {
    private ArrayList<Equipment> equipmentList;

    // Constructor
    public EquipmentManager() {
        equipmentList = new ArrayList<>();
    }

    // Add Equipment
    public void addEquipment(String name, String category, String status) {
        Equipment equipment = new Equipment(name, category, status);
        equipmentList.add(equipment);
        System.out.println("Equipment added successfully.");
    }

    // Update Equipment
    public void updateEquipment(String name, String newCategory, String newStatus) {
        Equipment equipment = findEquipmentByName(name);
        if (equipment != null) {
            equipment.setCategory(newCategory);
            equipment.setStatus(newStatus);
            System.out.println("Equipment updated successfully.");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    // Delete Equipment
    public void deleteEquipment(String name) {
        Equipment equipment = findEquipmentByName(name);
        if (equipment != null) {
            equipmentList.remove(equipment);
            System.out.println("Equipment deleted successfully.");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    // View All Equipments
    public void viewEquipments() {
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment found.");
        } else {
            System.out.println("Equipments List:");
            for (Equipment equipment : equipmentList) {
                equipment.displayDetails();
                System.out.println("----------------------");
            }
        }
    }

    // Add Maintenance Record
    public void addMaintenanceRecord(String name, LocalDate date, String details) {
        Equipment equipment = findEquipmentByName(name);
        if (equipment != null) {
            MaintenanceRecord record = new MaintenanceRecord(date, details);
            equipment.addMaintenanceRecord(record);
            System.out.println("Maintenance record added successfully.");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    // View Maintenance History
    public void viewMaintenanceHistory(String name) {
        Equipment equipment = findEquipmentByName(name);
        if (equipment != null) {
            ArrayList<MaintenanceRecord> history = equipment.getMaintenanceHistory();
            if (history.isEmpty()) {
                System.out.println("No maintenance history available.");
            } else {
                System.out.println("Maintenance History for " + name + ":");
                for (MaintenanceRecord record : history) {
                    System.out.println(record);
                }
            }
        } else {
            System.out.println("Equipment not found.");
        }
    }

    // Helper Method: Find Equipment by Name
    private Equipment findEquipmentByName(String name) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getName().equalsIgnoreCase(name)) {
                return equipment;
            }
        }
        return null;
    }

    // Manage Equipment Menu
    public void manageEquipments(Scanner scanner) {
        int choice;

        while (true) {
            System.out.println("\n|— Manage Equipments");
            System.out.println("1. Add Equipment");
            System.out.println("2. Update Equipment");
            System.out.println("3. Delete Equipment");
            System.out.println("4. View Equipments");
            System.out.println("5. Add Maintenance Record");
            System.out.println("6. View Maintenance History");
            System.out.println("7. Back to Admin Menu");
            System.out.print("Please select an option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter equipment name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter status: ");
                    String status = scanner.nextLine();
                    addEquipment(name, category, status);
                    break;
                case 2:
                    System.out.print("Enter equipment name to update: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new category: ");
                    String newCategory = scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine();
                    updateEquipment(updateName, newCategory, newStatus);
                    break;
                case 3:
                    System.out.print("Enter equipment name to delete: ");
                    String deleteName = scanner.nextLine();
                    deleteEquipment(deleteName);
                    break;
                case 4:
                    viewEquipments();
                    break;
                case 5:
                    System.out.print("Enter equipment name for maintenance: ");
                    String equipName = scanner.nextLine();
                    System.out.print("Enter maintenance date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter maintenance details: ");
                    String details = scanner.nextLine();
                    addMaintenanceRecord(equipName, date, details);
                    break;
                case 6:
                    System.out.print("Enter equipment name to view maintenance history: ");
                    String historyName = scanner.nextLine();
                    viewMaintenanceHistory(historyName);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
