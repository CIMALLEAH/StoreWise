package StoreWise_OOP.Alerts_Notifications;

import java.time.LocalDate;
import java.util.ArrayList;

import StoreWise_OOP.Manage_Products.Product;
import StoreWise_OOP.Manage_Products.StoreItem;

public class AlertsAndNotifs {
    private ArrayList<StoreItem> inventory;

    // Constructor
    public AlertsAndNotifs(ArrayList<StoreItem> inventory) {
        this.inventory = inventory;
    }

    // Low Stock Alerts
    public void showLowStockAlerts() {
        System.out.println("\nLow Stock Alerts:");
        boolean found = false;
        for (StoreItem item : inventory) {
            if (item.getStockLevel() < 10) {  // Example threshold for low stock
                System.out.println("Low stock: " + item.getName() + " | Current Stock: " + item.getStockLevel());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No low stock alerts at this time.");
        }
    }

    // Expired Product Alerts
    public void showExpiredProductAlerts() {
        System.out.println("\nExpired Product Alerts:");
        boolean found = false;
        LocalDate today = LocalDate.now();
        for (StoreItem item : inventory) {
            if (item instanceof Product) {
                Product product = (Product) item;
                if (product.getExpirationDate().isBefore(today)) {
                    System.out.println("Expired: " + product.getName() + " | Expired On: " + product.getExpirationDate());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No expired products at this time.");
        }
    }

    // Restock Notifications
    public void showRestockNotifications() {
        System.out.println("\nRestock Notifications:");
        boolean found = false;
        for (StoreItem item : inventory) {
            if (item.getStockLevel() == 0) {  // Example condition for restocking
                System.out.println("Out of stock: " + item.getName() + " | Immediate restock required!");
                found = true;
            } else if (item.getStockLevel() < 5) {  // Threshold for restocking notification
                System.out.println("Low stock: " + item.getName() + " | Consider restocking soon.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No restock notifications at this time.");
        }
    }

    // Manage Alerts & Notifications Menu
    public void manageAlertsMenu() {
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            int choice;

            while (true) {
                System.out.println("\n|— Alerts & Notifications");
                System.out.println("1. Low Stock Alerts");
                System.out.println("2. Expired Product Alerts");
                System.out.println("3. Restock Notifications");
                System.out.println("4. Back to Admin Menu");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        showLowStockAlerts();
                        break;
                    case 2:
                        showExpiredProductAlerts();
                        break;
                    case 3:
                        showRestockNotifications();
                        break;
                    case 4:
                        return; // Exit menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
