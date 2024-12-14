package StoreWise_OOP.Manage_Products;

public abstract class StoreItem {
    private String name;
    private String gencat;
    private String spcat;
    private int stockLevel;
    
    // Constructor
    public StoreItem(String name, String gencat, String spcat, int stockLevel) {
        this.name = name;
        this.gencat = gencat;
        this.spcat = spcat;
        this.stockLevel = stockLevel;

    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenCat() {
        return gencat;
    }

    public void setGenCat(String gencat) {
        this.gencat = gencat;
    }

    public String getSpCat() {
        return spcat;
    }

    public void setSpCat(String spcat) {
        this.spcat = spcat;
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
