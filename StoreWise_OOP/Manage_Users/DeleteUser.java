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

        // Check if the username exists in the user manager
        User userToDelete = userManager.findUserByUsername(deleteUsername);
        if (userToDelete == null) {
            Utils.displayHeader("Delete User");
            Utils.displayMessage("The username " + deleteUsername + " does not exist.");
            return; // Exit the method if the username does not exist
        }

        // Check if the admin wants to delete their own account
        if (deleteUsername.equalsIgnoreCase(currentUsername)) {
            // Ask for confirmation to delete the admin's own account
            Utils.displayMessage(" This is your account.");
            while (true) {
                Utils.displayHeader("Delete User");
                System.out.print(" Are you sure you want to delete your account, " + currentUsername + " (Y/N)? ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Your admin account has been deleted successfully.");
                    userManager.deleteUser(deleteUsername); // Delete the admin's account
                    UserManager.logout(); // Log out the current user
                    return;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Account deletion canceled. Returning to Menu...");
                    return; // Exit the method without deleting the account
                } else {
                    // Retry if the input is invalid
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        } else {
            // If the username is not the current user's account, proceed with the deletion of another user
            while (true) {
                Utils.displayHeader("Delete User");
                System.out.print(" Are you sure you want to delete " + deleteUsername + "? (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage(deleteUsername + " has been deleted successfully.");
                    userManager.deleteUser(deleteUsername); // Delete the selected user
                    return; // Exit the method after deleting the user
                } else if (confirmation.equalsIgnoreCase("n")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Account deletion canceled. Returning to menu...");
                    return; // Exit the method without deleting the account
                } else {
                    // Retry if the input is invalid
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }
}
