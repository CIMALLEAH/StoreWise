package StoreWise_OOP.Manage_Users;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String role;
        
            // Constructor
            public User(String username, String password, String role) {
                this.username = username;
                this.password = password;
                this.role = role;
            }
        
            // Getters and Setters
            public String getUsername() {
                return username;
            }
        
            public String getPassword() {
                return password;
            }
        
            public void setPassword(String password) {
                this.password = password;  // Consider adding validation for password complexity
            }
        
            public String getRole() {
                return role;
            }
        
            public void setRole(String role) {
                this.role = role;
            }
        
            // Override toString for easier display (password is omitted)
            @Override
            public String toString() {
                return "Username: " + username + ", Role: " + role;
            }
        
            // Override equals and hashCode for proper comparison in collections
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                User user = (User) obj;
                return username.equals(user.username);  // Unique comparison based on username
            }
        
            @Override
            public int hashCode() {
                return Objects.hash(username);  // Hash based on username for consistency
            }
        
            public void setUsername(String newUsername) {
                this.username = newUsername;       
    }
}
