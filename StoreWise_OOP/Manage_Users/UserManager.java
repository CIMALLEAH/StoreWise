package StoreWise_OOP.Manage_Users;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import StoreWise_OOP.Utils;

public class UserManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/storewise";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "MIKS";
    private static User currentUser;

    private List<User> users; // List to store users
    private List<ActivityLog> activityLogs; // List to store activity logs

    // Constructor
    public UserManager() {
        users = new ArrayList<>();
        activityLogs = new ArrayList<>();
        loadUsersFromDatabase();
    }

    private Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private void loadUsersFromDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "SELECT UserName, Password, UserRole FROM Users";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("UserName");
                    String password = resultSet.getString("Password");
                    String role = resultSet.getString("UserRole");
                    users.add(new User(username, password, role));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading users from database: " + e.getMessage());
        }
    }

    public void reloadUsersFromDatabase() {
        users.clear();
        loadUsersFromDatabase();
    }

    public void saveUsersToDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "REPLACE INTO Users (UserName, Password, UserRole) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (User user : users) {
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, user.getRole());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving users to database: " + e.getMessage());
        }
    }

    public void addUser(String username, String password, int roleChoice) {
        String role = getRoleFromChoice(roleChoice);
        if (role == null) {
            Utils.displayMessage("Invalid role choice. User not added.");
            return;
        }

        User user = new User(username, password, role);

        try (Connection connection = connectToDatabase()) {
            String query = "INSERT INTO Users (UserName, Password, UserRole) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error adding user to database: " + e.getMessage());
            return;
        }

        users.add(user);
        logActivity(username, "Added user with role " + role);
        Utils.displayHeader("Add User");
        Utils.displayMessage("User added successfully.");
    }

    public void viewActivityLogs() {
        if (activityLogs.isEmpty()) {
            Utils.displayHeader("Activity Logs");
            Utils.displayMessage("No activity logs available.");
        } else {
            Utils.displayHeader("Activity Logs");
            System.out.println(" Activities:");
            int index = 1;  // Start the enumeration from 1
            for (ActivityLog log : activityLogs) {
                System.out.println("  " + index + ". " + log);  // Print the log with the index
                index++;            
            }
        }
        
        Utils.printLine(60);
        Utils.printCentered("\n Press Enter to return...");
        try {
            System.in.read();  // Wait for user to press Enter
        } catch (IOException e) {
            System.err.println("Error waiting for input: " + e.getMessage());
        }
    }

    private String getRoleFromChoice(int choice) {
        switch (choice) {
            case 1:
                return "Admin";
            case 2:
                return "Employee";
            default:
                return null;
        }
    }

    public void updateUserCredentials(String oldUsername, String newUsername, String newPassword) {
        User user = findUserByUsername(oldUsername);

        if (user != null) {
            if (newUsername != null && !newUsername.isEmpty()) {
                user.setUsername(newUsername);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            try (Connection connection = connectToDatabase()) {
                String query = "UPDATE Users SET UserName = ?, Password = ? WHERE UserName = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, oldUsername);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Error updating user credentials in database: " + e.getMessage());
            }

            logActivity(user.getUsername(), "Updated credentials.");
        } else {
            Utils.displayMessage("User not found.");
        }
    }

    public void updateUserRole(String username, String newRole) {
        User user = findUserByUsername(username);

        if (user != null) {
            user.setRole(newRole);

            try (Connection connection = connectToDatabase()) {
                String query = "UPDATE Users SET UserRole = ? WHERE UserName = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, newRole);
                    statement.setString(2, username);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Error updating user role in database: " + e.getMessage());
            }

            logActivity(username, "Updated role to " + newRole);
            Utils.displayHeader("Update User Role");
            Utils.displayMessage("User role updated successfully.");
        } else {
            Utils.displayMessage("User not found.");
        }
    }

    public void deleteUser(String username) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove();
                try (Connection connection = connectToDatabase()) {
                    String query = "DELETE FROM Users WHERE UserName = ?";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, username);
                        statement.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.err.println("Error deleting user from database: " + e.getMessage());
                }
                logActivity(username, "Deleted user");
                Utils.displayHeader("Delete User");
                Utils.displayMessage("User deleted successfully.");
                return;
            }
        }

        Utils.displayMessage("User not found.");
    }

    public void listUsers() {
        if (users.isEmpty()) {
            Utils.displayHeader("Users List");
            Utils.displayMessage("No users found.");
        } else {
            Utils.displayHeader("Users List");
            System.out.println(" Users:");
            int index = 1;
            for (User user : users) {
                System.out.println("  " + index + ". " + user);
                index++;
            }
        }

        Utils.printLine(60);
        System.out.print("Press Enter to return... ");
        try {
            System.in.read();
        } catch (IOException e) {
            System.err.println("Error waiting for input: " + e.getMessage());
        }
    }

    public User findUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    private void logActivity(String username, String action) {
        ActivityLog log = new ActivityLog(username, action);
        activityLogs.add(log);
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }

    // Menu for managing users
    public void manageUsers(Scanner scanner) {
        int choice = 0;

        while (true) {
            Utils.displayHeader("Manage Users");
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
                    Utils.displayHeader("Manage Users");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 6.");
                }
            } else {
                Utils.displayHeader(" Manage Menu");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        switch (choice) {
            case 1:  // Add user
                AddUser addUserMenu = new AddUser(this); // Passing the current UserManager instance
                addUserMenu.addUserMenu(scanner);                
                break;
            case 2:  // Update user
                UpdateUser updateUser = new UpdateUser(this);
                updateUser.updateUser(scanner);
                break;
            case 3:  // Delete user
                DeleteUser deleteUserMenu = new DeleteUser(this);
                deleteUserMenu.deleteUserMenu(scanner);
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
}