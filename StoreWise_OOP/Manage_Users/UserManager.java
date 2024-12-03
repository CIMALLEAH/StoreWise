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

    private List<User> users;  // List to store users
    private List<ActivityLog> activityLogs;  // List to store activity logs

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
            String query = "SELECT username, password, role FROM users";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    users.add(new User(username, password, role));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading users from database: " + e.getMessage());
        }
    }

    public void reloadUsersFromDatabase() {
        users.clear(); // Clear the current in-memory list/map
        loadUsersFromDatabase(); // Reload the user list from the database
    }
    
    public void saveUsersToDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "REPLACE INTO users (username, password, role) VALUES (?, ?, ?)";
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

    // Add a new user and log the activity
    public void addUser(String username, String password, int roleChoice) {
        String role = getRoleFromChoice(roleChoice);
        User user = new User(username, password, role);

        try (Connection connection = connectToDatabase()) {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
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
        saveUsersToDatabase();
        logActivity(username, "Added user with role " + role);

        Utils.displayHeader("Add User");
        Utils.displayMessage("User added successfully.");
    }

    // Update an existing user and log the activity
    public void updateUser(String oldUsername, String newUsername, String oldPassword, String newPassword, int roleChoice) {
        String newRole = getRoleFromChoice(roleChoice);
        User user = findUserByUsername(oldUsername);
        if (user != null) {
            user.setUsername(newUsername); // Update username
            user.setPassword(newPassword);
            user.setRole(newRole);
            updateUserInDatabase(oldUsername, newUsername, newPassword, newRole);
            logActivity(newUsername, "Updated user with new password and role " + newRole);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUserInDatabase(String oldUsername, String newUsername, String newPassword, String newRole) {
        try (Connection connection = connectToDatabase()) {
            String query = "UPDATE users SET username = ?";
            
            // Only add the password and role update if they are not null
            if (newPassword != null) {
                query += ", password = ?";
            }
            if (newRole != null) {
                query += ", role = ?";
            }
            
            query += " WHERE username = ?";  // Ensure we are updating the correct user by their old username
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int parameterIndex = 1;
                statement.setString(parameterIndex++, newUsername);  // Set the new username
                
                if (newPassword != null) {
                    statement.setString(parameterIndex++, newPassword);  // Set the new password if it's not null
                }
                
                if (newRole != null) {
                    statement.setString(parameterIndex++, newRole);  // Set the new role if it's not null
                }
                
                statement.setString(parameterIndex, oldUsername);  // Set the old username to identify the user
                
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error updating user in database: " + e.getMessage());
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
                deleteUserFromDatabase(username);
                logActivity(username, "Deleted user");
                found = true;Utils.displayHeader("Delete My Account");
                break;
            }
        }
        if (!found) {
            System.out.println("User not found.");
        }
    }

    private void deleteUserFromDatabase(String username) {
        try (Connection connection = connectToDatabase()) {
            String query = "DELETE FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user from database: " + e.getMessage());
        }
    }

    // Log user activity
    void logActivity(String username, String action) {
        ActivityLog log = new ActivityLog(username, action);
        activityLogs.add(log);
    }

    // View all activity logs
    public void viewActivityLogs() {
        if (activityLogs.isEmpty()) {
            Utils.displayHeader("Activity Logs");
            Utils.displayMessage("No activity logs available.");
        } else {
            Utils.displayHeader("Activity Logs");
            System.out.println("Activities:");
            int index = 1;  // Start the enumeration from 1
            for (ActivityLog log : activityLogs) {
                System.out.println(index + ". " + log);  // Print the log with the index
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

    // List all users
    public void listUsers() {
        if (users.isEmpty()) {
            Utils.displayHeader("Users List");
            Utils.displayMessage("No users found.");
        } else {
            Utils.displayHeader("Users List");
            System.out.println(" Users List:");
            int index = 1;  // Start the enumeration from 1
            for (User user : users) {
                System.out.println(index + ". " + user);
                index++;            
            }
        }

        Utils.printLine(60);
        System.out.println("\n Press Enter to return...");
        try {
            System.in.read();  // Wait for user to press Enter
        } catch (IOException e) {
            System.err.println("Error waiting for input: " + e.getMessage());
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

    // Check if the username is already taken
    boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    // Method to get role based on user choice
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
                updateUser.roleUpdateMenu(scanner);
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

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
