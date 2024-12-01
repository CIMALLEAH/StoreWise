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
        Utils.displayHeader("StoreWise");
        Utils.displayMessage("Welcome to StoreWise!!");
        boolean running = true;

        while (running) {
            if (!userManager.isLoggedIn()) {
                showMainMenu();
            } else {
                showRoleBasedMenu();
            }
        }

        scanner.close();
    }

    private static void showMainMenu() {
        int choice = 0;
        while (true) {
            Utils.displayHeader("Main Menu");
            System.out.println("  1. Login");
            System.out.println("  2. Exit");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    Utils.displayHeader("Main Menu");
                    Utils.displayMessage("Invalid input, please enter 1 or 2.");
                }
            } else {
                Utils.displayHeader("Main Menu");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); 
            }
        }

        if (choice == 1) {
            boolean loginSuccess = false;
            while (!loginSuccess) {
                Utils.displayHeader("Login");
                System.out.print(" Enter username: ");
                String username = scanner.nextLine();
                System.out.print(" Enter password: ");
                String password = scanner.nextLine();

                if (userManager.login(username, password)) {
                    Utils.displayHeader("Login");
                    Utils.displayMessage("Login successful!");
                    loginSuccess = true;
                } else {
                    Utils.displayHeader("Login");
                    Utils.displayMessage("Invalid username or password.");

                    while (true) {
                        Utils.displayHeader("Login");
                        System.out.print(" Do you want to try again? (Y/y or N/n): ");
                        String retryChoice = "";
                        retryChoice = scanner.nextLine().trim().toLowerCase();

                        if (retryChoice.equals("n")) {
                            loginSuccess = true;
                            Utils.displayHeader("Login");
                            Utils.displayMessage("Exiting login.....");
                            break;
                        } else if (retryChoice.equals("y")) {
                            Utils.displayHeader("Login");
                            Utils.displayMessage("Retrying login.....");
                            break;
                        } else {
                            Utils.displayHeader("Login");
                            Utils.displayMessage("Invalid input, please enter 'Y/y' or 'N/n'.");
                        }
                    }
                }
            }
        } else if (choice == 2) {
            Utils.displayHeader("Main Menu");
            Utils.displayMessage("Exiting... Goodbye!");
            System.exit(0);
        }
    }

    private static boolean hasSeenGreeting = false;

    private static void showRoleBasedMenu() {
        User currentUser = UserManager.getCurrentUser();
        String role = currentUser.getRole();

        if (!hasSeenGreeting) {
            Utils.printMenuGreeting(currentUser);
            hasSeenGreeting = true;
        }
        
        if (role.equals("Admin")) {
            AdminMenu.showAdminMenu(scanner);
        } else if (role.equals("Employee")) {
            EmployeeMenu.showEmployeeMenu(scanner);
        }
    }

    public static void handleLogout() {
        UserManager.logout();
        hasSeenGreeting = false;
    }
}