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

            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printCentered("|     User Roles    |");
            Utils.printCentered("|- - - - - - - - - -|");
            Utils.printCentered("|  1. Admin         |");
            Utils.printCentered("|  2. Employee      |");
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printLine(60);
            System.out.print(" Please select Role of new user: ");

            if (scanner.hasNextInt()) {
                roleChoice = scanner.nextInt();
                scanner.nextLine();
                if (roleChoice == 1 || roleChoice == 2) {

                    break;
                } else {
                    Utils.displayHeader("Add User");
                    Utils.displayMessage("Invalid input, please select 1 or 2.");
                }
            }  else {
                Utils.displayHeader("Add User");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine();
            }   
        }

        String role = (roleChoice == 1) ? "Admin" : "Employee";
        
        String addUsername;
        while (true) {
            Utils.displayHeader("Add User");
            System.out.println(" Role: " + role);
            System.out.print(" Enter username: ");
            addUsername = scanner.nextLine();
            
            if (userManager.isUsernameTaken(addUsername)) {
                Utils.printCentered("Username is already taken. Please choose another one.");
                Utils.sleepFor(1500);
                continue;
            } else {
                break;
            }
        }
        
        String addPassword;
        while (true) {
            Utils.displayHeader("Add User");
            System.out.println(" Role: " + role);
            System.out.println(" Username: " + addUsername);
            System.out.print(" Enter password: ");
            addPassword = scanner.nextLine();
            
            if (addPassword.length() < 8) {
                Utils.printCentered("Password must be at least 8 characters long.");
                Utils.sleepFor(1500);
                continue;
            } else {
                break;
            }
        }
        
        while (true) {
            Utils.displayHeader("Add User");
            System.out.print(" Are you sure you want to add this user? (Y/y or N/n): ");
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
                Utils.displayMessage("Invalid input, please enter 'Y/y' or 'N/n'.");
            }
        }
    }
}
