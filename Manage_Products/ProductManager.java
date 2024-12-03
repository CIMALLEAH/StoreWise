package StoreWise_OOP.Manage_Products;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import StoreWise_OOP.Utils;

public class ProductManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/storewise";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "MIKS";

    private List<Product> products;  

    // Constructor
    public ProductManager() {
        products = new ArrayList<>();
        loadProductsFromDatabase();
    }

    // Establish connection to the database
    private Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Load products from the database into the list
    private void loadProductsFromDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "SELECT productName, productCategory, stocklevel, expirationdate FROM products";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("productName");
                    String category = resultSet.getString("productCategory");
                    int stockLevel = resultSet.getInt("stocklevel");
                    Date expirationDate = resultSet.getDate("expirationdate");
                    products.add(new Product(name, category, stockLevel, expirationDate.toLocalDate()));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading products from database: " + e.getMessage());
        }
    }

    // Save products back to the database
    public void saveProductsToDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "REPLACE INTO products (productName, productCategory, stocklevel, expirationdate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Product product : products) {
                    statement.setString(1, product.getName());
                    statement.setString(2, product.getCategory());
                    statement.setInt(3, product.getStockLevel());
                    statement.setDate(4, Date.valueOf(product.getExpirationDate()));
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving products to database: " + e.getMessage());
        }
    }

    // Add a new product
    public void addProduct(Product product) {
        try (Connection connection = connectToDatabase()) {
            String query = "INSERT INTO products (productName, productCategory, stocklevel, expirationdate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, product.getName());
                statement.setString(2, product.getCategory());
                statement.setInt(3, product.getStockLevel());
                statement.setDate(4, Date.valueOf(product.getExpirationDate()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error adding product to database: " + e.getMessage());
        }
        products.add(product);
        saveProductsToDatabase();
        System.out.println("Product added: " + product.getName());
    }

    // Update an existing product
    public void updateProduct(String name, int newStockLevel, Date newExpirationDate) {
        Product product = findProductByName(name);
        if (product != null) {
            product.setStockLevel(newStockLevel);
            product.setExpirationDate(newExpirationDate.toLocalDate());
            saveProductsToDatabase();
            System.out.println("Product updated: " + name);
        } else {
            System.out.println("Product not found.");
        }
    }

    // Delete a product
    public void deleteProduct(String name) {
        Iterator<Product> iterator = products.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().equals(name)) {
                iterator.remove();
                deleteProductFromDatabase(name);
                found = true;
                System.out.println("Product deleted: " + name);
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    private void deleteProductFromDatabase(String name) {
        try (Connection connection = connectToDatabase()) {
            String query = "DELETE FROM products WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product from database: " + e.getMessage());
        }
    }

    // Search for products by name or category
    public void searchProducts(String query) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(query) || product.getCategory().contains(query)) {
                result.add(product);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("Search results:");
            for (Product product : result) {
                System.out.println(product);
            }
        }
    }

    // Adjust stock for a product
    public void adjustProductStock(String name, int adjustment) {
        Product product = findProductByName(name);
        if (product != null) {
            product.setStockLevel(product.getStockLevel() + adjustment);
            saveProductsToDatabase();
            System.out.println("Adjusted stock for " + name + " by " + adjustment);
        } else {
            System.out.println("Product not found.");
        }
    }

    // Find a product by name
    private Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    // List all products
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Product List:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    // Menu for managing products
    public void manageProductMenu(Scanner scanner) {
        int choice = 0;
        while (true) {
            Utils.displayHeader("Manage Products");
            System.out.println(" Menu:");
            System.out.println("  1. Add Product");
            System.out.println("  2. Update Product");
            System.out.println("  3. Delete Product");
            System.out.println("  4. View Products");
            System.out.println("  5. Adjust Product Stock");
            System.out.println("  6. Back to Admin Menu");
            Utils.printLine(60);
            System.out.print("Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (choice >= 1 && choice <= 6) {
                    break;
                } else {
                    Utils.displayHeader("Manage Products");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 6.");
                }
            } else {
                Utils.displayHeader("Manage Products");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        switch (choice) {
            case 1:  // Add product
                addProductMenu(scanner);
                break;
            case 2:  // Update product
                updateProductMenu(scanner);
                break;
            case 3:  // Delete product
                deleteProductMenu(scanner);
                break;
            case 4:  // View products
                listProducts();
                break;
                // searchProductMenu(scanner);
            case 5:  // Adjust stock
                adjustStockMenu(scanner);
                break;
            case 6:  // Back to Admin Menu
                Utils.clearConsole();
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        manageProductMenu(scanner); // Recursive call to continue managing products
    }

    // Add product menu
    private void addProductMenu(Scanner scanner) {
        Utils.displayHeader("Add User");
        System.out.println(" Enter product details:");
        System.out.print("  Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("  Enter product category: ");
        String category = scanner.nextLine();

        System.out.print("  Enter stock level: ");
        int stockLevel = scanner.nextInt();

        System.out.print("  Enter expiration date (YYYY-MM-DD): ");
        String expirationDateStr = scanner.next();
        Date expirationDate = Date.valueOf(expirationDateStr);

        Product newProduct = new Product(name, category, stockLevel, expirationDate.toLocalDate());
        addProduct(newProduct);
    }

    // Update product menu
    private void updateProductMenu(Scanner scanner) {
        System.out.println("\nEnter product name to update:");
        String name = scanner.nextLine();

        System.out.print("Enter new stock level: ");
        int newStockLevel = scanner.nextInt();

        System.out.print("Enter new expiration date (YYYY-MM-DD): ");
        String expirationDateStr = scanner.next();
        Date newExpirationDate = Date.valueOf(expirationDateStr);

        updateProduct(name, newStockLevel, newExpirationDate);
    }

    // Delete product menu
    private void deleteProductMenu(Scanner scanner) {
        System.out.println("\nEnter product name to delete:");
        String name = scanner.nextLine();
        deleteProduct(name);
    }

    // // Search product menu
    // private void searchProductMenu(Scanner scanner) {
    //     System.out.println("\nEnter search term (product name or category):");
    //     String query = scanner.nextLine();
    //     searchProducts(query);
    // }

    // Adjust stock menu
    private void adjustStockMenu(Scanner scanner) {
        System.out.println("\nEnter product name to adjust stock:");
        String name = scanner.nextLine();

        System.out.print("Enter stock adjustment (positive or negative): ");
        int adjustment = scanner.nextInt();

        adjustProductStock(name, adjustment);
    }
}
