package StoreWise_OOP.Manage_Users;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(new Date(timestamp));
        String formattedTime = timeFormat.format(new Date(timestamp));
        return "User: " + username + ", Action: " + action + ", Date: " + formattedDate + "\n Time: " + formattedTime;
    }
}
