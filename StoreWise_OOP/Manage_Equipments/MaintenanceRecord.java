package StoreWise_OOP.Manage_Equipments;

import java.time.LocalDate;

public class MaintenanceRecord {
    private LocalDate maintenanceDate;
    private String details;
    private LocalDate nextMaintenanceDate;

    // Constructor
    public MaintenanceRecord(LocalDate maintenanceDate, String details, LocalDate nextMaintenanceDate) {
        this.maintenanceDate = maintenanceDate;
        this.details = details;
    }

    // Getters and Setters
    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    public LocalDate getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    @Override
    public String toString() {
        return "Date: " + maintenanceDate + ", Details: " + details;
    }
}