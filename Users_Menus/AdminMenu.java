package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.MainMenu;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Equipments.EquipmentManager;
import StoreWise_OOP.Manage_Products.ProductManager;
import StoreWise_OOP.Manage_Sales.SalesManager;
import StoreWise_OOP.Manage_Users.UpdateUser;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;
import StoreWise_OOP.Reports.InventoryManager;
import StoreWise_OOP.Reports.ReportsManager;

public class AdminMenu {
    // Declare dependency instances at the top of the class
    private static InventoryManager inventoryManager = new InventoryManager();
    private static SalesManager salesManager = new SalesManager();
    private static EquipmentManager equipmentManager = new EquipmentManager();
    private static ProductManager productManager = new ProductManager();
    private static UserManager userManager = new UserManager();
    private static UpdateUser updateUser = new UpdateUser(userManager);

    public static void showAdminMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();
        int choice = 0;

        while (true) {
            Utils.displayUserHeader(currentUser);
            System.out.println("  1. My Account");
            System.out.println("  2. Manage Users");
            System.out.println("  3. Manage Products");
            System.out.println("  4. Manage Equipment");
            System.out.println("  5. Sales & Reports");
            System.out.println("  6. Alerts & Notifications");
            System.out.println("  7. System Management");
            System.out.println("  8. Logout");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (choice >= 1 && choice <= 8) {
                    break; // Valid input, break out of the loop
                } else {
                    Utils.displayHeader(currentUser.getRole() + " Menu");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 8.");
                }
            } else {
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        switch (choice) {
            case 1:
                Utils.displayHeader("My Account");
                Utils.displayMessage("Account Settings...");
                
                updateUser.myAccountMenu(scanner);
                break;
            case 2:
                // Handle manage users
                Utils.displayHeader("Manage Users");
                Utils.displayMessage("Managing users...");

                userManager.manageUsers(scanner);
                break;
            case 3:
                // Handle manage products
                Utils.displayHeader("Manage Products");
                Utils.displayMessage("Managing products...");

                productManager.manageProductMenu(scanner);
                break;
            case 4:
                // Handle manage equipment
                Utils.displayHeader("Manage Equipment");
                Utils.displayMessage("Managing equipment...");

                equipmentManager.manageEquipments(scanner);
                break;
            case 5:
                // Handle sales management
                Utils.displayHeader("Sales & Reports");
                Utils.displayMessage("Managing sales & & eports...");

                salesManager.salesManagement(scanner);
                ReportsManager reportsManager = new ReportsManager(inventoryManager, salesManager, equipmentManager);
                reportsManager.reports(scanner);
                break;
            case 6:
                // Handle system reports
                Utils.displayHeader("Alerts & Notifications");
                Utils.displayMessage("Viewing alerts & notifications...");

                break;
            case 7:
                Utils.displayHeader("System Management ");
                Utils.printCentered("System...");
            
                break;
            case 8:
                // Logout
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.displayMessage("Logged out successfully.");
                
                MainMenu.handleLogout();
                break;
        }
    }
}
