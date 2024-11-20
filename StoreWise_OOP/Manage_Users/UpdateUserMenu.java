package StoreWise_OOP.Manage_Users;

import java.util.Scanner;

import StoreWise_OOP.Utils;

public class UpdateUserMenu {
    private UserManager userManager;

    public UpdateUserMenu(UserManager userManager) {
        this.userManager = userManager;
    }

    public void updateUserMenu(Scanner scanner) {
    
        String updateUsername = "";
        User userToUpdate = null;

        while (true) {

            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Update User");
            Utils.printLine(60);
            System.out.print(" Enter username to update: ");
            updateUsername = scanner.nextLine().trim();

            userToUpdate = userManager.findUserByUsername(updateUsername);

            if (userToUpdate != null) {
                if (UserManager.getCurrentUser().getUsername().equals(updateUsername)) {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Update User");
                    Utils.printLine(60);
                    System.out.print(" You are attempting to update your own details. Continue? (y/n): ");
                } else if (UserManager.getCurrentUser().getRole().equals("Admin")) {
                        System.out.print(" You're attempting to update " + updateUsername + "'s Role. \nContinue? (y/n): ");
                } else {
                        Utils.clearConsole();
                        Utils.printLine(60);
                        Utils.printCentered("Update User");
                        Utils.printLine(60);
                        Utils.printCentered("You do not have permission to update other users.");
                        Utils.sleepFor(2000);
                        Utils.clearConsole();
                        Utils.printLine(60);
                        Utils.printCentered("Update User");
                        Utils.printLine(60);
                        System.out.print(" Do you want to update your own details instead? (y/n): ");
                    }
                } else {
                    // Step 3: If user doesn't exist, ask to try again
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Update User");
                    Utils.printLine(60);
                    Utils.printCentered("User not found.");
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Update User");
                    Utils.printLine(60);
                    System.out.print(" Do you want to try again? (y/n): ");

                    String retryConfirmation = scanner.nextLine().trim().toLowerCase();

                    if (retryConfirmation.equalsIgnoreCase("n")) {
                        Utils.clearConsole();
                        Utils.printLine(60);
                        Utils.printCentered("Update User");
                        Utils.printLine(60);
                        Utils.printCentered("Returning to Manage Users menu...");
                        Utils.sleepFor(2000);
                        Utils.clearConsole();
                        return; // Exit and return to Manage Users menu
                    } else if (!retryConfirmation.equalsIgnoreCase("y")) {
                        Utils.clearConsole();
                        Utils.printLine(60);
                        Utils.printCentered("Update User");
                        Utils.printLine(60);
                        Utils.printCentered("Invalid input, please try again...");
                        Utils.sleepFor(2000);
                        Utils.clearConsole();
                    }
                }                                                        
            
            if (UserManager.getCurrentUser().getUsername().equals(updateUsername)) {
                updateSelf(scanner, updateUsername);
            } else if (UserManager.getCurrentUser().getRole().equals("Admin")) {
                updateRole(scanner, updateUsername);
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Update User");
                Utils.printLine(60);
                Utils.printCentered("You do not have permission to update other users.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
            }
        }
    }
        
    private void updateSelf(Scanner scanner, String updateUsername) {
        Utils.clearConsole();
        Utils.printLine(60);
        Utils.printCentered("Update User");
        Utils.printLine(60);
        System.out.println("What would you like to update?");
        System.out.println("1. Username");
        System.out.println("2. Password");
        Utils.printLine(60);
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  

         if (choice == 1) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Update Username");
            Utils.printLine(60);
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine().trim();
            userManager.updateUser(updateUsername, newUsername, -1);
        } else if (choice == 2) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Update Password");
            Utils.printLine(60);
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine().trim();
            userManager.updateUser(updateUsername, newPassword, -1);
        } else {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Invalid option. Returning to menu...");
            Utils.sleepFor(2000);
            Utils.clearConsole();
        }
    }

    private void updateRole(Scanner scanner, String updateUsername) {
        User userToUpdate = userManager.findUserByUsername(updateUsername);
        
        Utils.clearConsole();
        Utils.printLine(60);
        Utils.printCentered("Update User");
        Utils.printLine(60);
        
        int newRoleChoice = 0;
        while (true) {
            // Admin role selection
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printCentered("|     User Roles    |");
            Utils.printCentered("|- - - - - - - - - -|");
            Utils.printCentered("|  1. Admin         |");
            Utils.printCentered("|  2. Employee      |");
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printLine(60);
            System.out.print("Select new role for " + userToUpdate.getUsername() + ": ");
        
            if (scanner.hasNextInt()) {
                newRoleChoice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (newRoleChoice >= 1 && newRoleChoice <= 3) {
                    break;
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Update User");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid role choice, please select 1, 2, or 3.");
                    Utils.sleepFor(2000);
                    Utils.clearConsole();
                }
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Update User");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please enter a number.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine();  // Consume invalid input
            }
        }
    }
}   