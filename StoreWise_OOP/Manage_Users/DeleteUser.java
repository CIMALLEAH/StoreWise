package StoreWise_OOP.Manage_Users;

import java.util.Scanner;
import StoreWise_OOP.Utils;

public class DeleteUser {
    private UserManager userManager;

    public DeleteUser(UserManager userManager) {
        this.userManager = userManager;
    }

    public void deleteUserMenu(Scanner scanner) {
        Utils.displayHeader("Delete User");
        User currentUser = UserManager.getCurrentUser(); 
        String currentUsername = currentUser.getUsername(); 

        System.out.print(" Enter username to delete: ");
        String deleteUsername = scanner.nextLine().trim();

        User userToDelete = userManager.findUserByUsername(deleteUsername);
        if (userToDelete == null) {
            Utils.displayHeader("Delete User");
            Utils.displayMessage("The username " + deleteUsername + " does not exist.");
            return; 
        }

        if (deleteUsername.equalsIgnoreCase(currentUsername)) {
            Utils.displayMessage(" This is your account.");
            while (true) {
                Utils.displayHeader("Delete User");
                System.out.print(" Are you sure you want to delete your account, " + currentUsername + " (Y/N)? ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Your admin account has been deleted successfully.");
                    userManager.deleteUser(deleteUsername); 
                    UserManager.logout(); 
                    return;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Account deletion canceled. Returning to Menu...");
                    return; 
                } else {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        } else {
            while (true) {
                Utils.displayHeader("Delete User");
                System.out.print(" Are you sure you want to delete " + deleteUsername + "? (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage(deleteUsername + " has been deleted successfully.");
                    userManager.deleteUser(deleteUsername); 
                    return; 
                } else if (confirmation.equalsIgnoreCase("n")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Account deletion canceled. Returning to menu...");
                    return; 
                } else {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }
}
