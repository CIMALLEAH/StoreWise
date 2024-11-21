package StoreWise_OOP;

import java.time.LocalDate;
import java.util.ArrayList;

public class InventoryManager {
    private ArrayList<StoreItem> inventory;

    public InventoryManager() {
        inventory = new ArrayList<>();
    }

    // Add item to inventory
    public void addItem(StoreItem item) {
        inventory.add(item);
        System.out.println("Added item: " + item.getName());
    }

    // Remove item from inventory
    public void removeItem(StoreItem item) {
        inventory.remove(item);
        System.out.println("Removed item: " + item.getName());
    }

    // Display inventory details
    public void displayInventory() {
        System.out.println("\nInventory List:");
        for (StoreItem item : inventory) {
            item.displayDetails();
        }
    }

    // Check for low stock items
    public void checkLowStock() {
        System.out.println("\nLow Stock Alerts:");
        for (StoreItem item : inventory) {
            if (item.getStockLevel() < 10) {  // Example threshold for low stock
                System.out.println("Low stock alert: " + item.getName() + " is low on stock!");
            }
        }
    }

    // Find and show items with expiration date approaching
    public void checkExpiringProducts() {
        System.out.println("\nExpiring Products Soon:");
        for (StoreItem item : inventory) {
            if (item instanceof Product) {
                Product product = (Product) item;
                LocalDate today = LocalDate.now();
                if (!product.getExpirationDate().isAfter(today)) {
                    System.out.println("Product " + product.getName() + " has expired or will expire soon.");
                }
            }
        }
    }

    public StoreItem[] getInventory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInventory'");
    }
}
