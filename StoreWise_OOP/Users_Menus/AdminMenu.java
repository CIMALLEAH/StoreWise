package StoreWise_OOP.Users_Menus;

import java.util.Scanner;

import StoreWise_OOP.MainMenu;
import StoreWise_OOP.Utils;
import StoreWise_OOP.Manage_Equipments.EquipmentManager;
import StoreWise_OOP.Manage_Products.ProductManager;
import StoreWise_OOP.Manage_Users.UpdateUser;
import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;

public class AdminMenu {
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
            System.out.println("  5. Logout");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice >= 1 && choice <= 6) {
                    break; 
                } else {
                    Utils.displayHeader(currentUser.getRole() + " Menu");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 8.");
                }
            } else {
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.displayMessage("Invalid input, please enter a number.");
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
                Utils.displayHeader("Manage Users");
                Utils.displayMessage("Managing users...");

                userManager.manageUsers(scanner);
                break;
            case 3:
                Utils.displayHeader("Manage Products");
                Utils.displayMessage("Managing products...");

                productManager.manageProductMenu(scanner);
                break;
            case 4:
                Utils.displayHeader("Manage Equipment");
                Utils.displayMessage("Managing equipment...");

                equipmentManager.manageEquipmentMenu(scanner);
                break;
            case 5:
                Utils.displayHeader(currentUser.getRole() + " Menu");
                Utils.displayMessage("Logged out successfully.");
                
                MainMenu.handleLogout();
                break;
        }
    }
}
