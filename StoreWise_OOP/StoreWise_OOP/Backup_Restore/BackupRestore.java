package StoreWise_OOP.Backup_Restore;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import StoreWise_OOP.Manage_Products.StoreItem;
import StoreWise_OOP.Manage_Products.Product;

public class BackupRestore {
    private ArrayList<StoreItem> inventory;

    // Constructor
    public BackupRestore(ArrayList<StoreItem> inventory) {
        this.inventory = inventory;
    }

    // Backup Data (Save inventory data to a file)
    public void backupData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("inventory_backup.dat"))) {
            out.writeObject(inventory);
            System.out.println("Data backed up successfully.");
        } catch (IOException e) {
            System.out.println("Error while backing up data: " + e.getMessage());
        }
    }

    // Restore Data (Load inventory data from a file)
    public void restoreData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("inventory_backup.dat"))) {
            inventory = (ArrayList<StoreItem>) in.readObject();
            System.out.println("Data restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while restoring data: " + e.getMessage());
        }
    }

    // Backup & Restore Menu
    public void manageBackupRestoreMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n|— Backup & Restore");
            System.out.println("1. Backup Data");
            System.out.println("2. Restore Data");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        backupData();
                        break;
                    case 2:
                        restoreData();
                        break;
                    case 3:
                        return; // Exit menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
