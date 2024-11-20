package StoreWise_OOP.Manage_Users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import StoreWise_OOP.Utils;

public class UserManager {
    private ArrayList<User> users;  // List to store users
    private List<ActivityLog> activityLogs;  // List to store activity logs
    private static User currentUser;

    // Constructor
    public UserManager() {
        users = new ArrayList<>();
        activityLogs = new ArrayList<>();

        // Predefined users (in a real system, this might come from a database or file)
        users.add(new User("admin", "admin123", "Admin"));
        users.add(new User("employee", "emp123", "Employee"));
    }

    // Add a new user and log the activity
    public void addUser(String username, String password, int roleChoice) {
        String role = getRoleFromChoice(roleChoice);
        User user = new User(username, password, role);
        users.add(user);
        logActivity(username, "Added user with role " + role);

        Utils.clearConsole();
        Utils.printLine(60);
        Utils.printCentered("Add User");
        Utils.printLine(60);
        Utils.printCentered("User added successfully.");
        Utils.sleepFor(2000);
        Utils.clearConsole();
    }

    // Update an existing user and log the activity
    public void updateUser(String username, String newPassword, int roleChoice) {
        String newRole = getRoleFromChoice(roleChoice);
        User user = findUserByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
            user.setRole(newRole);
            logActivity(username, "Updated user with new password and role " + newRole);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    // Delete a user and log the activity
    public void deleteUser(String username) {
        Iterator<User> iterator = users.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove();
                logActivity(username, "Deleted user");
                found = true;
                System.out.println("User deleted successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("User not found.");
        }
    }

    // Log user activity
    private void logActivity(String username, String action) {
        ActivityLog log = new ActivityLog(username, action);
        activityLogs.add(log);
    }

    // View all activity logs
    public void viewActivityLogs() {
        if (activityLogs.isEmpty()) {
            System.out.println("No activity logs available.");
        } else {
            System.out.println("Activity Logs:");
            for (ActivityLog log : activityLogs) {
                System.out.println(log);
            }
        }
    }

    // List all users
    public void listUsers() {
        if (users.isEmpty()) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Users List");
            Utils.printLine(60);
            Utils.printCentered("No users found.");
        } else {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Users List");
            Utils.printLine(60);
            System.out.println(" Users List:");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    // Find a user by username
    User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Method to get role based on user choice
    private String getRoleFromChoice(int choice) {
        switch (choice) {
            case 1:
                return "Admin";
            case 2:
                return "Employee";
        }
                return null;
    }

    // Menu for managing users
    public void manageUsers(Scanner scanner) {
        int choice = 0;
        
        while (true) {
            Utils.printLine(60);
            Utils.printCentered("Manage Users");
            Utils.printLine(60);
            System.out.println(" Menu:");
            System.out.println("  1. Add User");
            System.out.println("  2. Update User");
            System.out.println("  3. Delete User");
            System.out.println("  4. View Users List");
            System.out.println("  5. View Activity Logs");
            System.out.println("  6. Back to Admin Menu");
            Utils.printLine(60);
            System.out.print(" Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 6) {
                    break;
                } else {
                    Utils.clearConsole();
                    Utils.printLine(60);
                    Utils.printCentered("Manage Users");
                    Utils.printLine(60);
                    Utils.printCentered("Invalid input, please enter a number between 1 and 6.");
                    Utils.sleepFor(2000);
                    Utils.clearConsole();
                }
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered(" Manage Menu");
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please enter a number.");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                scanner.nextLine(); // Consume invalid input
            }
        }


        switch (choice) {
            case 1:  // Add user
                AddUserMenu addUserMenu = new AddUserMenu(this); // Passing the current UserManager instance
                addUserMenu.addUserMenu(scanner);                
                break;
            case 2:  // Update user
                UpdateUserMenu updateUserMenu = new UpdateUserMenu(this);
                updateUserMenu.updateUserMenu(scanner);
                break;
            case 3:  // Delete user
                deleteUserMenu(scanner);
                break;
            case 4:  // List users
                listUsers();
                break;
            case 5:  // View activity logs
                viewActivityLogs();
                break;
            case 6:  // Back to Admin Menu
                Utils.clearConsole();
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

        manageUsers(scanner); // Recursively call to continue managing users
    }

    
    // Delete user menu logic
    private void deleteUserMenu(Scanner scanner) {
        Utils.clearConsole();
        Utils.printLine(60);
        Utils.printCentered("Delete User");
        Utils.printLine(60);

        System.out.print("Enter username to delete: ");
        String deleteUsername = scanner.nextLine();

        while (true) {
            Utils.clearConsole();
            Utils.printLine(60);
            Utils.printCentered("Delete User");
            Utils.printLine(60);
            System.out.print(" Are you sure you want to delete this user? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equalsIgnoreCase("y")) {
                deleteUser(deleteUsername);
                break;
            } else if (confirmation.equalsIgnoreCase("n")) {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Returning to Manage Users menu...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
                break;
            } else {
                Utils.clearConsole();
                Utils.printLine(60);
                Utils.printCentered("Invalid input, please try again...");
                Utils.sleepFor(2000);
                Utils.clearConsole();
            }
        }
    }

    // Login method
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    // Get the current logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Logout method
    public static void logout() {
        currentUser = null;
    }

    // Check if a user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
