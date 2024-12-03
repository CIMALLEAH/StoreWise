package StoreWise_OOP.Manage_Sales;

import java.time.LocalDate;

public class Sale {
    private String productName;
    private int quantitySold;
    private double totalPrice;
    private LocalDate saleDate;

    // Constructor
    public Sale(String productName, int quantitySold, double totalPrice, LocalDate saleDate) {
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    // Display Sale Details
    public void displaySaleDetails() {
        System.out.println("Product: " + productName +
                           ", Quantity: " + quantitySold +
                           ", Total Price: $" + totalPrice +
                           ", Date: " + saleDate);
    }
}
