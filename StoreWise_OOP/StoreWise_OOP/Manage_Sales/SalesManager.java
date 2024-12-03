package StoreWise_OOP.Manage_Sales;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SalesManager {
    private ArrayList<Sale> salesList;

    // Constructor
    public SalesManager() {
        salesList = new ArrayList<>();
    }

    // Add Sale (Optional for Testing)
    public void addSale(String productName, int quantitySold, double totalPrice, LocalDate saleDate) {
        Sale sale = new Sale(productName, quantitySold, totalPrice, saleDate);
        salesList.add(sale);
        System.out.println("Sale added successfully.");
    }

    // View Sales Data
    public void viewSalesData() {
        if (salesList.isEmpty()) {
            System.out.println("No sales data available.");
        } else {
            System.out.println("\nSales Data:");
            for (Sale sale : salesList) {
                sale.displaySaleDetails();
            }
        }
    }

    // View Sales Analytics
    public void viewSalesAnalytics() {
        if (salesList.isEmpty()) {
            System.out.println("No sales data available for analytics.");
            return;
        }

        double totalRevenue = 0;
        int totalProductsSold = 0;

        for (Sale sale : salesList) {
            totalRevenue += sale.getTotalPrice();
            totalProductsSold += sale.getQuantitySold();
        }

        System.out.println("\nSales Analytics:");
        System.out.println("Total Revenue: $" + totalRevenue);
        System.out.println("Total Products Sold: " + totalProductsSold);
        System.out.println("Average Revenue per Sale: $" + (totalRevenue / salesList.size()));
    }

    // Export Sales Data to a File
    public void exportSalesData(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Product, Quantity Sold, Total Price, Sale Date\n");
            for (Sale sale : salesList) {
                writer.write(sale.getProductName() + ", " +
                             sale.getQuantitySold() + ", " +
                             sale.getTotalPrice() + ", " +
                             sale.getSaleDate() + "\n");
            }
            System.out.println("Sales data exported to " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting sales data: " + e.getMessage());
        }
    }

    // Sales Management Menu
    public void salesManagement(Scanner scanner) {
        int choice;

        while (true) {
            System.out.println("\n|— Sales Management");
            System.out.println("1. View Sales Data");
            System.out.println("2. View Sales Analytics");
            System.out.println("3. Export Sales Data");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewSalesData();
                    break;
                case 2:
                    viewSalesAnalytics();
                    break;
                case 3:
                    System.out.print("Enter file name to export sales data (e.g., sales_data.csv): ");
                    String fileName = scanner.nextLine();
                    exportSalesData(fileName);
                    break;
                case 4:
                    return; // Exit to Admin Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
