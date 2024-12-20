package StoreWise_OOP.Manage_Users;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String role;
        
            public User(String username, String password, String role) {
                this.username = username;
                this.password = password;
                this.role = role;
            }
        
            public String getUsername() {
                return username;
            }
        
            public String getPassword() {
                return password;
            }
        
            public void setPassword(String password) {
                this.password = password; 
            }
        
            public String getRole() {
                return role;
            }
        
            public void setRole(String role) {
                this.role = role;
            }
        
            @Override
            public String toString() {
                return "Username: " + username + ", Role: " + role;
            }
        
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                User user = (User) obj;
                return username.equals(user.username); 
            }
        
            @Override
            public int hashCode() {
                return Objects.hash(username);  
            }
        
            public void setUsername(String newUsername) {
                this.username = newUsername;       
    }
}
