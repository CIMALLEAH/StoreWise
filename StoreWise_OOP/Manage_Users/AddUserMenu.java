package StoreWise_OOP.Manage_Users;

import java.util.Scanner;

import StoreWise_OOP.Utils;

public class AddUserMenu {
    private UserManager userManager;

    public AddUserMenu (UserManager userManager) {
        this.userManager = userManager;
    }

    public void addUserMenu(Scanner scanner) {
        int roleChoice = 0;

        while (true) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Add User");
            Utils.printLine(60);

            // Role selection
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printCentered("|     User Roles    |");
            Utils.printCentered("|- - - - - - - - - -|");
            Utils.printCentered("|  1. Admin         |");
            Utils.printCentered("|  2. Employee      |");
            Utils.printCentered("*- - - - - - - - - -*");
            Utils.printLine(60);
            System.out.print(" Select role of new user: ");

            if (scanner.hasNextInt()) {
                roleChoice = scanner.nextInt();
                scanner.nextLine();
                if (roleChoice >= 1 && roleChoice <=3) {
                    break;
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Add User");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid role choice, please select 1, 2, or 3.");
                    Utils.sleepFor(2000);
                    Utils.clearConsole();
                }
            }  else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Add User");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please enter a number.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine();
            }   
        }
    
        System.out.print(" Enter username: ");
        String addUsername = scanner.nextLine();
        System.out.print(" Enter password: ");
        String addPassword = scanner.nextLine();

        while (true) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Add User");
            Utils.printLine(60);
            System.out.print(" Are you sure you want to add this user? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equalsIgnoreCase("y")) {
                userManager.addUser(addUsername, addPassword, roleChoice);
                break;
            } else if (confirmation.equalsIgnoreCase("n")) {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Add User");
                Utils.printLine(60);
                Utils.printCentered("Returning to Manage Users menu...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Add User");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please try again...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
            }
        }
    }
}
