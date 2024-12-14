package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.MainMenu;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Equipments.EquipmentManager;
import StoreWise_OOP.Manage_Products.ProductManager;
import StoreWise_OOP.Manage_Users.UpdateUser;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;

public class EmployeeMenu {
    private static UserManager userManager = new UserManager();
    private static UpdateUser updateUser = new UpdateUser(userManager);
    private static ProductManager productManager = new ProductManager();
    private static EquipmentManager equipmentManager = new EquipmentManager();


    public static void showEmployeeMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();
        int choice = 0;

        while (true) {
            Utils.displayUserHeader(currentUser);
            System.out.println("  1. My Account");
            System.out.println("  2. View Products");
            System.out.println("  3. View Equipments");
            System.out.println("  4. View Maintenance Records");
            System.out.println("  5. View Upcoming Maintenance");
            System.out.println("  6. Logout");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice >= 1 && choice <= 6) {
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
                Utils.displayHeader("View Products");
                Utils.displayMessage("Loading.....");
                Utils.displayHeader("View Products");
                Utils.printCentered("Products");
                Utils.printLine(60);
                productManager.listProductsWID();
                Utils.printLine(60);
                productManager.pauseUntilEnter(scanner);
                break;
            case 3:
                Utils.displayHeader("View Equipments");
                Utils.displayMessage("Loading.....");
                Utils.displayHeader("View Equipments");
                equipmentManager.viewEquipments();
                Utils.printLine(60);
                equipmentManager.pauseUntilEnter(scanner);
                break;
            case 4:
                Utils.displayHeader("View Maintenance Records");
                Utils.displayMessage("Loading.....");
                equipmentManager.viewMaintenanceRecords();
                equipmentManager.pauseUntilEnter(scanner);
                break;
            case 5:
                Utils.displayHeader("View Upcoming Maintenance");
                Utils.displayMessage("Loading.....");
                equipmentManager.viewUpcomingMaintenance();
                equipmentManager.pauseUntilEnter(scanner);
                break;
            case 6:
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.printCentered("Logged out successfully.");
                MainMenu.handleLogout();
                break;
        }
    }
}
