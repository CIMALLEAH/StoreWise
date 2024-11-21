package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.InventoryManager;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;

public class EmployeeMenu {
    private static InventoryManager manager = new InventoryManager();

    public static void showEmployeeMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();  // Get the current logged-in user
        int choice = 0;

        while (true) {
            Utils.printCentered("Welcome, Employee (" + currentUser.getUsername() + ")");
            Utils.printLine(60);
            System.out.println("Employee Menu:");
            System.out.println("  1. View Products");
            System.out.println("  2. View Equipment");
            System.out.println("  3. Track Sales");
            System.out.println("  4. Logout");
            Utils.printLine(60);
            System.out.print("Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
                if (choice >= 1 && choice <= 4) {
                    break;
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered(currentUser.getRole() + " Menu");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid input, please enter a number between 1 and 7.");
                    Utils.sleepFor(2000);
                    Utils.clearConsole();
                }
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered(currentUser.getRole() + " Menu");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please enter a number between 1 and 7.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine();
            }    
        }   
        
        // Process the user's menu choice
        switch (choice) {
            case 1:
                manager.displayInventory();  // View Products
                break;
            case 2:
                // View Equipment (future implementation can be added here)
                break;
            case 3:
                // Track Sales (future implementation can be added here)
                break;
            case 4:
                UserManager.logout();  // Logout
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
