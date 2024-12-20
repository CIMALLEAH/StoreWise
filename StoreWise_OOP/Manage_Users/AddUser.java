package StoreWise_OOP.Manage_Users;

import java.util.Scanner;
import StoreWise_OOP.Utils;

public class AddUser {
    private UserManager userManager;

    public AddUser (UserManager userManager) {
        this.userManager = userManager;
    }

    public void addUserMenu(Scanner scanner) {
        int roleChoice = 0;

        while (true) {
            Utils.displayHeader("Add User");
            Utils.displayMessage("Loading.....");

            Utils.displayHeader("Add User");
            Utils.printCentered("New User Details");
            Utils.role();
            Utils.printLine(60);
            System.out.print(" Please select Role of new user: ");

            if (scanner.hasNextInt()) {
                roleChoice = scanner.nextInt();
                scanner.nextLine();
                if (roleChoice == 1 || roleChoice == 2) {
                    break;
                } else {
                    Utils.displayMessage("Invalid input, please select 1 or 2.");
                }
            }  else {
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine();
            }   
        }

        String role = (roleChoice == 1) ? "Admin" : "Employee";
        
        String addUsername;
        while (true) {
            Utils.displayHeader("Add User");
            Utils.printCentered("New User Details");
            System.out.println(" Role: " + role);
            System.out.print(" Enter username: ");
            addUsername = scanner.nextLine();

            if (addUsername.length() < 4) {
                Utils.displayMessage("Username must be at least 4 characters long.");
                continue;
            } 
            
            if (userManager.isUsernameTaken(addUsername)) {
                Utils.displayMessage("Username is already taken. Please choose another one.");
                continue;
            } else {
                break;
            }
        }
        
        String addPassword;
        while (true) {
            Utils.displayHeader("Add User");
            Utils.printCentered("New User Details");
            System.out.println(" Role: " + role);
            System.out.println(" Username: " + addUsername);
            System.out.print(" Enter password: ");
            addPassword = scanner.nextLine();
            
            if (addPassword.length() < 8) {
                Utils.displayMessage("Password must be at least 8 characters long.");
                continue;
            } else {
                Utils.displayHeader("Add User");
                Utils.printCentered("New User Details");
                System.out.println(" Role: " + role);
                System.out.println(" Username: " + addUsername);
                System.out.println(" Password: " + addPassword);
                Utils.sleepFor(1500);
                break;
            }
        }
        
        while (true) {
            Utils.displayHeader("Add User");
            System.out.print(" Are you sure about adding (" + addUsername + ") as a user? (Y/N): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equalsIgnoreCase("y")) {
                userManager.addUser(addUsername, addPassword, roleChoice);
                break;
            } else if (confirmation.equalsIgnoreCase("n")) {
                Utils.displayHeader("Add User");
                Utils.displayMessage("Returning to Manage Users menu...");
                break;
            } else {
                Utils.displayHeader("Add User");
                Utils.displayMessage("Invalid input, please enter 'Y' or 'N'.");
            }
        }
    }
}