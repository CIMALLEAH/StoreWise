package StoreWise_OOP;

import java.time.LocalDate;

class Product extends StoreItem {
    private LocalDate expirationDate;

    // Constructor
    public Product(String name, String category, int stockLevel, LocalDate expirationDate) {
        super(name, category, stockLevel);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public void updateStock(int quantity) {
        int newStock = getStockLevel() + quantity;
        setStockLevel(newStock);
        System.out.println("Stock updated for " + getName() + ". New stock level: " + newStock);
    }

    @Override
    public void displayDetails() {
        System.out.println("Product: " + getName() + "\nCategory: " + getCategory() +
                           "\nStock Level: " + getStockLevel() + "\nExpiration Date: " + expirationDate);
    }
}
