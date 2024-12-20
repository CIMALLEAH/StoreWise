package StoreWise_OOP.Manage_Products;

import java.sql.Date;

public class Product extends StoreItem {
    private Date expirationDate;


    // Constructor
    public Product(String name, String gencat, String spcat, int stockLevel, String expirationDate) {
        super(name, gencat, spcat, stockLevel);
        if (expirationDate != null && !expirationDate.equalsIgnoreCase("NONE")) {
            try {
                this.expirationDate = Date.valueOf(expirationDate);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
            }
        } else {
            this.expirationDate = null; // No expiration date
        }
    }

    // Getters and setters
    public String getExpirationDate() {
        return (expirationDate != null) ? expirationDate.toString() : "No Expiration";
    }
    

    public void setExpirationDate(String newExpirationDate) {
        if (newExpirationDate != null && !newExpirationDate.equalsIgnoreCase("NONE")) {
            try {
                this.expirationDate = Date.valueOf(newExpirationDate);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
            }
        } else {
            this.expirationDate = null;
        }
    }

    @Override
    public void updateStock(int quantity) {
        int newStock = getStockLevel() + quantity;
        setStockLevel(newStock);
        System.out.println("Stock updated for " + getName() + ". New stock level: " + newStock);
    }

    @Override
    public void displayDetails() {
        System.out.println("Product: " + getName());
        System.out.println("General Category: " + getGenCat());
        System.out.println("Specific Category: " + getSpCat());
        System.out.println("Stock Level: " + getStockLevel());
        System.out.println("Expiration Date: " + (expirationDate != null ? expirationDate : "No Expiration"));
    }
}