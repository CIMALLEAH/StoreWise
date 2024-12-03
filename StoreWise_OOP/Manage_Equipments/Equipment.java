package StoreWise_OOP.Manage_Equipments;

import java.util.ArrayList;

public class Equipment {
    private String name;
    private String category;
    private String status;
    private ArrayList<MaintenanceRecord> maintenanceHistory;

    // Constructor
    public Equipment(String name, String category, String status) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.maintenanceHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MaintenanceRecord> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public void addMaintenanceRecord(MaintenanceRecord record) {
        this.maintenanceHistory.add(record);
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Status: " + status);
        System.out.println("Maintenance History: " + maintenanceHistory.size() + " record(s)");
    }
}