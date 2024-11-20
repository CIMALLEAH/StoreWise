package StoreWise_OOP;

import java.util.Scanner;

import StoreWise_OOP.Manage_Users.User;
import StoreWise_OOP.Manage_Users.UserManager;
import StoreWise_OOP.Users_Menus.AdminMenu;
import StoreWise_OOP.Users_Menus.EmployeeMenu;

public class MainMenu {
    private static UserManager userManager = new UserManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            // Show Main Menu
            if (!userManager.isLoggedIn()) {
                showMainMenu();
            } else {
                showRoleBasedMenu();
            }
        }

        scanner.close();  // Close the scanner at the end of the program
    }

    private static void showMainMenu() {
        int choice = 0;
        while (true) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Welcome to StoreWise");
            Utils.printLine(60);
            System.out.println(" Main Menu:");
            System.out.println("  1. Login");
            System.out.println("  2. Exit");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character
                if (choice == 1 || choice == 2) {
                    break;  // Exit the loop if input is valid
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Main Menu");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid input, please enter 1 or 2.");
                    Utils.sleepFor(2000);
                    Utils.clearConsole();
                }
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Main Menu");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please enter a number.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine();  // Consume invalid input
            }
        }

        if (choice == 1) {
            // Login process
            boolean loginSuccess = false;
            while (!loginSuccess) {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Login");
                Utils.printLine(60);
                System.out.print(" Enter username: ");
                String username = scanner.nextLine();
                System.out.print(" Enter password: ");
                String password = scanner.nextLine();

                if (userManager.login(username, password)) {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Login");
                    Utils.printLine(60);
                    Utils.printCentered("Login successful!");
                    Utils.sleepFor(2000);
                    Utils.clearConsole(); // Clear console after login
                    loginSuccess = true;
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Login");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid username or password.");
                    Utils.sleepFor(1000);
                    Utils.clearConsole();

                    // Loop until a valid input ('y' or 'n') is entered
                    while (true) {
                        // Ask the user if they want to try again or exit
                        Utils.printLine(60);
                        Utils.printCentered("Login");
                        Utils.printLine(60);
                    
                        String retryChoice = "";
                    
                        System.out.print(" Do you want to try again? (Y/y or N/n): ");
                        
                        retryChoice = scanner.nextLine().trim().toLowerCase();  // Accept both uppercase and lowercase 'y'/'n'

                        if (retryChoice.equals("n")) {
                            Utils.clearConsole();
                            loginSuccess = true;  // Exit the login loop if user chooses 'n'
                            Utils.printLine(60);
                            Utils.printCentered("Login");
                            Utils.printLine(60);
                            Utils.printCentered("Exiting login.....");
                            Utils.sleepFor(2000);
                            Utils.clearConsole();
                            break;
                        } else if (retryChoice.equals("y")) {
                            Utils.clearConsole();
                            Utils.printLine(60);
                            Utils.printCentered("Login");
                            Utils.printLine(60);
                            // Restart the login process by continuing the loop
                            Utils.printCentered("Retrying login.....");
                            Utils.sleepFor(2000);
                            Utils.clearConsole();
                            break;  // Break out of the loop to restart the login process
                        } else {
                            Utils.clearConsole();
                            Utils.printLine(60);
                            Utils.printCentered("Login");
                            Utils.printLine(60);
                            Utils.printCentered("Invalid input. Please enter 'Y/y' or 'N/n'.");
                            Utils.sleepFor(1500);
                            Utils.clearConsole();
                            // The loop will continue and prompt again for a valid input
                        }
                    }
                }
            }
        } else if (choice == 2) {
            // Exit program
            Utils.clearConsole();
            System.exit(0);
        } else {
            System.out.println("Invalid choice, please try again.");
        }
    }

    private static void showRoleBasedMenu() {
        User currentUser = UserManager.getCurrentUser();
        String role = currentUser.getRole();
        Utils.printUserHeader(currentUser);
        if (role.equals("Admin")) {
            AdminMenu.showAdminMenu(scanner);
        } else if (role.equals("Employee")) {
            EmployeeMenu.showEmployeeMenu(scanner);
        }
    }
}