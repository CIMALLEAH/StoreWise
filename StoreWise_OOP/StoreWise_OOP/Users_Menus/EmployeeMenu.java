package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.MainMenu;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Users.UpdateUser;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;
import StoreWise_OOP.Reports.InventoryManager;

public class EmployeeMenu {
    private static InventoryManager manager = new InventoryManager();
    private static UserManager userManager = new UserManager();
    private static UpdateUser updateUser = new UpdateUser(userManager);
    public static void showEmployeeMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();
        int choice = 0;

        while (true) {
            Utils.displayUserHeader(currentUser);
            System.out.println("  1. My Account");
            System.out.println("  2. View Products");
            System.out.println("  3. Track Sales");
            System.out.println("  4. Logout");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice >= 1 && choice <= 4) {
                    break;
                } else {
                    Utils.displayHeader(currentUser.getRole() + " Menu");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 4.");
                }
            } else {
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.displayMessage("Invalid input, please enter a number between 1 and 7.");
                scanner.nextLine();
            }    
        }   
        
        switch (choice) {
            case 1:
                Utils.displayHeader("My Account");
                Utils.displayMessage("Account Settings...");
                
                updateUser.myAccountMenu(scanner);
                break;
            case 2:
                manager.displayInventory();
                break;
            case 3:
                // Track Sales (future implementation can be added here)
                break;
            case 4:
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.printCentered("Logged out successfully.");
                MainMenu.handleLogout();
                break;
        }
    }
}
