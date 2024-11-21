package StoreWise_OOP;

abstract class StoreItem {
    private String name;
    private String category;
    private int stockLevel;

    // Constructor
    public StoreItem(String name, String category, int stockLevel) {
        this.name = name;
        this.category = category;
        this.stockLevel = stockLevel;
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

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    // Abstract methods
    public abstract void updateStock(int quantity);
    public abstract void displayDetails();
}
