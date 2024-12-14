package StoreWise_OOP.Manage_Users;

import java.util.Scanner;
import StoreWise_OOP.Utils;

public class UpdateUser {
    private UserManager userManager;

    public UpdateUser(UserManager userManager) {
        this.userManager = userManager;
    }

    int choice = 0;

    public void myAccountMenu(Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();

        while (true) {
            Utils.displayHeader("My Account");
            System.out.println(" Menu:");
            System.out.println("  1. Change Username");
            System.out.println("  2. Change Password");
            System.out.println("  3. Back to " + currentUser.getRole() + " Menu");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    Utils.displayHeader("My Account");
                    Utils.displayMessage("Invalid input, please enter a number between 1, 2, or 3.");
                }
            } else {
                Utils.displayHeader("My Account");
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        switch (choice) {
            case 1:
                updateUsername(scanner, currentUser);
                break;
            case 2:
                updatePassword(scanner, currentUser);
                break;
            case 3:
                Utils.displayHeader("My Account");
                Utils.displayMessage("Exiting.....");
                return;
        }
    }

    private void updateUsername(Scanner scanner, User user) {
        Utils.displayHeader("Change Username");
        System.out.print(" Enter new username: ");
        String newUsername = scanner.nextLine().trim();

        if (newUsername.length() < 4) {
            Utils.displayMessage("Username must be at least 4 characters long.");
            myAccountMenu(scanner);
            return;
        } else if (newUsername.equals(user.getUsername())) {
            Utils.displayMessage("Username retained. No changes were made.");
            myAccountMenu(scanner);
            return;
        } else if (userManager.findUserByUsername(newUsername) != null) {
            Utils.displayMessage("Username is already taken. Please choose another.");
            myAccountMenu(scanner);
            return;
        }

        userManager.updateUserCredentials(user.getUsername(), newUsername, user.getPassword());
        user.setUsername(newUsername);
        UserManager.setCurrentUser(user);

        Utils.displayHeader("Change Username");
        Utils.displayMessage("Username updated successfully!");
        myAccountMenu(scanner);
    }

    private void updatePassword(Scanner scanner, User user) {
        Utils.displayHeader("Change Password");
        System.out.print(" Enter new password: ");
        String newPassword = scanner.nextLine().trim();

        if (newPassword.equals(user.getPassword())) {
            Utils.displayMessage("Password retained. No changes were made.");
            myAccountMenu(scanner);
            return;
        }

        if (newPassword.length() < 8) {
            Utils.displayMessage("Password must be at least 8 characters long.");
            myAccountMenu(scanner);
            return;
        }

        userManager.updateUserCredentials(user.getUsername(), user.getUsername(), newPassword);
        user.setPassword(newPassword);

        Utils.displayHeader("Change Password");
        Utils.displayMessage("Password updated successfully!");
        myAccountMenu(scanner);
    }

    public void updateUser(Scanner scanner) {
        Utils.displayHeader("Update User");
        System.out.print(" Enter username to update role: ");
        String username = scanner.nextLine().trim();

        User userToUpdate = userManager.findUserByUsername(username);
        if (userToUpdate == null) {
            Utils.displayHeader("Update User");
            Utils.displayMessage("User not found.");
            return;
        }

        while (true) {
            Utils.displayHeader("Change Role");
            Utils.role();
            Utils.printLine(60);
            System.out.print(" Select a new role: ");

            if (scanner.hasNextInt()) {
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                String newRole;
                switch (roleChoice) {
                    case 1:
                        newRole = "Admin";
                        break;
                    case 2:
                        newRole = "Employee";
                        break;
                    default:
                        newRole = null;
                        break;
                }

                if (newRole == null) {
                    Utils.displayHeader("Change Role");
                    Utils.displayMessage("Invalid input. Please enter 1 or 2.");
                    continue;
                }

                if (userToUpdate.getRole().equals(newRole)) {
                    Utils.displayHeader("Change Role");
                    Utils.displayMessage("User already has the " + newRole + " role.");
                } else {
                    userManager.updateUserRole(userToUpdate.getUsername(), newRole);
                    Utils.displayHeader("Change Role");
                    Utils.displayMessage("Role updated to " + newRole + " successfully!");
                }
                return;
            } else {
                Utils.displayHeader("Change Role");
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}
