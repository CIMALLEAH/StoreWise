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
        User currentUser = UserManager.getCurrentUser(); // Get current user instance
        String currentUsername = currentUser.getUsername(); // Get the username of the current user

        // If it's an admin, allow deletion of any user
        if (currentUser.getRole().equalsIgnoreCase("Admin")) {
            System.out.print(" Enter username to delete: ");
            String deleteUsername = scanner.nextLine().trim();

            while (true) {
                Utils.displayHeader("Delete User");
                System.out.print(" Are you sure you want to delete " + deleteUsername + "? (Y/y or N/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage(deleteUsername + " has been deleted successfully.");
                    userManager.deleteUser(deleteUsername); // Delete the selected user
                    UserManager.logout(); // Log out the current user
                    return;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Account deletion canceled. Returning to menu...");
                    break;
                } else {
                    Utils.displayHeader("Delete User");
                    Utils.displayMessage("Invalid input, please try again...");
                    return;
                }
            }
        } else {
            // If the user is not an admin, they can only delete their own account
            Utils.displayHeader("Delete My Account");
            System.out.print(" Are you sure you want to delete your account " + currentUsername + "? (Y/y or N/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equalsIgnoreCase("y")) {
                userManager.deleteUser(currentUsername); // Delete the current user's account
                Utils.displayHeader("Delete My Account");
                Utils.displayMessage("Your account has been deleted.");
                // Optionally, you may log out the user or perform other necessary actions.
            } else if (confirmation.equalsIgnoreCase("n")) {
                Utils.displayHeader("Delete My Account");
                Utils.displayMessage("Account deletion canceled. Returning to menu...");
            } else {
                Utils.displayHeader("Delete My Account");
                Utils.displayMessage("Invalid input. Please try again.");
            }
        }
    }
}
