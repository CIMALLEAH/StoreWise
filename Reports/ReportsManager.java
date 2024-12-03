package StoreWise_OOP.Reports;

import java.util.Scanner;

import StoreWise_OOP.Manage_Equipments.EquipmentManager;
import StoreWise_OOP.Manage_Sales.SalesManager;

public class ReportsManager {
    private InventoryManager inventoryManager;
    private SalesManager salesManager;
    private EquipmentManager equipmentManager;

    // Constructor
    public ReportsManager(InventoryManager inventoryManager, SalesManager salesManager, EquipmentManager equipmentManager) {
        this.inventoryManager = inventoryManager;
        this.salesManager = salesManager;
        this.equipmentManager = equipmentManager;
    }

    // View Inventory Report
    public void viewInventoryReport() {
        System.out.println("\n|— Inventory Report —|");
        inventoryManager.displayInventory(); // Displays all inventory details
    }

    // View Sales Report
    public void viewSalesReport() {
        System.out.println("\n|— Sales Report —|");
        salesManager.viewSalesData(); // Displays all sales records
    }

    // View Equipment Maintenance Report
    public void viewEquipmentMaintenanceReport() {
        System.out.println("\n|— Equipment Maintenance Report —|");
        equipmentManager.viewMaintenanceHistory(null); // Displays all maintenance records
    }

    // Reports Menu
    public void reports(Scanner scanner) {
        int choice;

        while (true) {
            System.out.println("\n|— Reports");
            System.out.println("1. View Inventory Report");
            System.out.println("2. View Sales Report");
            System.out.println("3. View Equipment Maintenance Report");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewInventoryReport();
                    break;
                case 2:
                    viewSalesReport();
                    break;
                case 3:
                    viewEquipmentMaintenanceReport();
                    break;
                case 4:
                    return; // Exit to Admin Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
