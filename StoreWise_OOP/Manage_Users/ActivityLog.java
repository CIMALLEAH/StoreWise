package StoreWise_OOP.Manage_Users;

public class ActivityLog {
    private String username;
    private String action;
    private long timestamp;

    // Constructor
    public ActivityLog(String username, String action) {
        this.username = username;
        this.action = action;
        this.timestamp = System.currentTimeMillis();  // Store timestamp of action
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Override toString for easier display
    @Override
    public String toString() {
        return "User: " + username + ", Action: " + action + ", Time: " + timestamp;
    }
}
