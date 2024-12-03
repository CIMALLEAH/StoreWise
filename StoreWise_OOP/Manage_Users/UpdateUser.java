package StoreWise_OOP.Manage_Users;

import java.util.Scanner;
import StoreWise_OOP.Utils;

public class UpdateUser {
    private UserManager userManager;
    private DeleteUser deleteUserMenu;


    public UpdateUser(UserManager userManager) {
        this.userManager = userManager;
        this.deleteUserMenu = new DeleteUser(userManager); 
    }

    int choice = 0;

    public void myAccountMenu (Scanner scanner) {
        User currentUser = UserManager.getCurrentUser();

        while (true) {
            Utils.displayHeader("My Account");
            System.out.println(" Menu:");
            System.out.println("  1. Change Username");
            System.out.println("  2. Change Password");
            System.out.println("  3. Delete My Account");
            System.out.println("  4. Back to " + currentUser.getRole() + " Menu");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 4) {
                   break;
                } else  {
                    Utils.displayHeader("My Account");
                    Utils.displayMessage("Invalid input, please enter a number between 1, 2, or 3.");                }     
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
                deleteUserMenu.deleteUserMenu(scanner);
                break;
            case 4:
                return;
        }
    }
    
    private void updateUsername(Scanner scanner, User user) {
        Utils.displayHeader("Change Username");
        System.out.print(" Enter new username: ");
        String newUsername = scanner.nextLine().trim();

        if (newUsername.equals(user.getUsername())) {
            Utils.displayMessage("Username retained. No changes were made.");
            Utils.sleepFor(1500);
            return;
        }   
        
        if (userManager.isUsernameTaken(newUsername)) {
            Utils.printCentered("Username is already taken. Please choose another.");
            Utils.sleepFor(1500);
            return;
        } else {        
            userManager.updateUserInDatabase(user.getUsername(), newUsername, null, null);
            user.setUsername(newUsername);
            userManager.logActivity(user.getUsername(), "Updated their username.");
            UserManager.setCurrentUser(user); // Update the current user instance
        
            Utils.displayHeader("Change Username");
            Utils.displayMessage("Username updated successfully!");
        }
    }

    private void updatePassword(Scanner scanner, User user) {
        Utils.displayHeader("Change Password");
        System.out.print(" Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        
        if (newPassword.length() < 8) {
            Utils.printCentered("Password must be at least 8 characters long.");
            Utils.sleepFor(1500);
            return;
        }
        
        userManager.updateUserInDatabase(user.getUsername(), null, newPassword, null);
        user.setPassword(newPassword);
        userManager.logActivity(user.getUsername(), "Updated their password.");

        Utils.displayHeader("Change Password");
        Utils.displayMessage("Password updated successfully!");
    }

    public void roleUpdateMenu(Scanner scanner) {
        while (true) {
            Utils.displayHeader("Manage Users");
            System.out.println(" Menu:");
            System.out.println("  1. Update User");
            System.out.println("  2. Back");
            Utils.printLine(60);
            System.out.print(" Please choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1: 
                        updateUser(scanner);
                    case 2:
                        Utils.clearConsole();
                        return; // Exit Manage Users menu
                    default:
                        Utils.displayHeader("Manage Users");
                        Utils.displayMessage("Invalid choice. Please try again.");
                }
            } else {
                Utils.displayHeader("Manage Users");
                Utils.displayMessage("Invalid input. Please enter a number.");
            }
        }
    }

    private void updateUser(Scanner scanner) {
        Utils.displayHeader("Update User");
        System.out.print(" Enter username to update: ");
        String username = scanner.nextLine().trim();

        User userToUpdate = userManager.findUserByUsername(username);
        if (userToUpdate == null) {
            Utils.displayHeader("Update User");
            Utils.displayMessage("User not found.");
            return;
        }

        while (true) {
            Utils.displayHeader("Update User");
            System.out.println(" Menu:");
            System.out.println("  1. Change Role");
            System.out.println("  2. Back");
            Utils.printLine(60);
            System.out.print(" Please choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: 
                    updateRole(scanner, userToUpdate);
                    case 2:
                        Utils.clearConsole();
                        return;
                }
            } else {
                Utils.displayHeader("Update User");
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private void updateRole(Scanner scanner, User user) {
        while (true) {
            Utils.displayHeader("Change Role");
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printCentered("|     User Roles    |");
            Utils.printCentered("|- - - - - - - - - -|");
            Utils.printCentered("|  1. Admin         |");
            Utils.printCentered("|  2. Employee      |");
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printLine(60);
            System.out.print(" Select a new role: ");

            if (scanner.hasNextInt()) {
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (roleChoice) {
                    case 1: 
                        user.setRole("Admin");
                        userManager.logActivity(user.getUsername(), "Admin updated role to Admin.");
                        userManager.saveUsersToDatabase();
                        Utils.displayHeader("Change Role");
                        Utils.displayMessage("Role updated to Admin successfully!");
                        return;
                    
                    case 2: 
                        user.setRole("Employee");
                        userManager.logActivity(user.getUsername(), "Admin updated role to Employee.");
                        userManager.saveUsersToDatabase();
                        Utils.displayHeader("Change Role");
                        Utils.displayMessage("Role updated to Employee successfully!");
                        return;
                    
                    default:
                        Utils.displayHeader("Change Role");
                        Utils.displayMessage("Invalid role choice. Please try again.");
                    
                }
            } else {
                Utils.displayHeader("Change Role");
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}