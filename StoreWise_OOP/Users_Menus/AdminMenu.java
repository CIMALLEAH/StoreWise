package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.InventoryManager;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;

public class AdminMenu {
    public static InventoryManager manager = new InventoryManager();

    public static void showAdminMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();
        int choice = 0;

        while (true) {
            Utils.displayMenuGreeting(currentUser);
            System.out.println("  1. Manage Users");
            System.out.println("  2. Manage Products");
            System.out.println("  3. Manage Equipment");
            System.out.println("  4. Sales Management");
            System.out.println("  5. Settings");
            System.out.println("  6. System Reports");
            System.out.println("  7. Logout");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (choice >= 1 && choice <= 7) {
                    Utils.clearConsole();
                    break; // Valid input, break out of the loop
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
                Utils.printCentered("Invalid input, please enter a number.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine(); // Consume invalid input
            }
        }

        // Process the valid choice
        switch (choice) {
            case 1:
                // Handle manage users
                Utils.printLine(60);
                Utils.printCentered("Manage Users");
                Utils.printLine(60);
                Utils.printCentered("Managing users...");
                Utils.sleepFor(2000);
                Utils.clearConsole();

                UserManager userManager = new UserManager();
                userManager.manageUsers(scanner);
                break;
            case 2:
                // Handle manage products
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Manage Products");
                Utils.printLine(60);
                Utils.printCentered("Managing products...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            case 3:
                // Handle manage equipment
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Manage Equipment");
                Utils.printLine(60);
                Utils.printCentered("Managing equipment...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            case 4:
                // Handle sales management
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Sales Management");
                Utils.printLine(60);
                Utils.printCentered("Managing sales...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            case 5:
                // Handle settings
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Settings");
                Utils.printLine(60);
                Utils.printCentered("Changing settings...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            case 6:
                // Handle system reports
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("System Reports");
                Utils.printLine(60);
                Utils.printCentered("Viewing system reports...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            case 7:
                // Logout
                UserManager.logout();
                Utils.printCentered("Logged out successfully.");
                break;
            default:
                // This should not be reached because we've already validated the input.
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
