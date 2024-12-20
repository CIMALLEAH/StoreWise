package StoreWise_OOP.Manage_Products;

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
            String query = "SELECT productName, productGenCat, productSpecificCat, stockLevel, expirationDate FROM products";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("productName");
                    String gencat = resultSet.getString("productGenCat");
                    String spcat = resultSet.getString("productSpecificCat");
                    int stockLevel = resultSet.getInt("stockLevel");
                    Date expirationDate = resultSet.getDate("expirationDate");
                    String expirationDateStr = (expirationDate != null) ? expirationDate.toString() : null;
                    products.add(new Product(name, gencat, spcat, stockLevel, expirationDateStr));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading products from database: " + e.getMessage());
        }
    }

    // Save products back to the database
    public void saveProductsToDatabase() {
        try (Connection connection = connectToDatabase()) {
            String query = "REPLACE INTO products (productName, productGenCat, productSpecificCat, stockLevel, expirationDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Product product : products) {
                    statement.setString(1, product.getName());
                    statement.setString(2, product.getGenCat());
                    statement.setString(3, product.getSpCat());
                    statement.setInt(4, product.getStockLevel());                    
                    if (!product.getExpirationDate().equals("No Expiration")) {
                        statement.setDate(5, Date.valueOf(product.getExpirationDate()));
                    } else {
                        statement.setNull(5, Types.DATE);
                    }
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving products to database: " + e.getMessage());
        }
    }

    // Add a new product
    private void addProduct(Product product) {
        try (Connection connection = connectToDatabase()) {
            String query = "INSERT INTO products (productName, productGenCat, productSpecificCat, stockLevel, expirationDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, product.getName());
                statement.setString(2, product.getGenCat());
                statement.setString(3, product.getSpCat());
                statement.setInt(4, product.getStockLevel());
                if (product.getExpirationDate() != null) {
                    statement.setDate(5, Date.valueOf(product.getExpirationDate()));
                } else {
                    statement.setNull(5, Types.DATE);
                }
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
    public void updateProduct(String oldName, String newName, int newStockLevel) {
        String sql = "UPDATE products SET productName = ?, stockLevel = ? WHERE productName = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {
        
            statement.setString(1, newName);
            statement.setInt(2, newStockLevel);
            statement.setString(3, oldName);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated in the database: " + oldName);        
                Product product = findProductByName(oldName);
                if (product != null) {
                    product.setName(newName);
                    product.setStockLevel(newStockLevel);
                    System.out.println("Product updated in memory: " + oldName);
                }
            } else {
                System.out.println("Product not found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                deleteProductById(0);
                found = true;
                System.out.println("Product deleted: " + name);
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    private void deleteProductById(int productId) {
        try (Connection connection = connectToDatabase()) {
            String query = "DELETE FROM products WHERE ProductID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, productId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Product with ID " + productId + " successfully deleted.");
                } else {
                    System.out.println("No product found with ID " + productId + ". Nothing was deleted.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product from database: " + e.getMessage());
        }
    }
    

    // Search for products by name or category
    public void searchProducts(String query) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(query) || product.getGenCat().contains(query) || product.getSpCat().contains(query)) {
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

    // Method to display products with their IDs
    public void listProductsWID() {
        String query = "SELECT productID, productName, productGenCat, stockLevel, expirationDate FROM products";
        try (Connection connection = connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            if (!resultSet.isBeforeFirst()) { // Check if the result set is empty
                System.out.println("No products found in the database.");
                return;
            }

        // Table Header
            System.out.printf("%-4s %-12s %-15s %-7s %-10s%n", 
                              "ID", "Name", "General Category", " Stock", "Expiration Date");
            Utils.printLine(60);

        // Display rows
            while (resultSet.next()) {
                int productId = resultSet.getInt("productID");
                String name = resultSet.getString("productName");
                String category = resultSet.getString("productGenCat");
                int stockLevel = resultSet.getInt("stockLevel");
                String expirationDate = resultSet.getString("expirationDate");

                System.out.printf("%-4d %-15s %-15s %-6d %-10s%n", 
                                  productId, name, category, stockLevel, expirationDate);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
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
            System.out.println("  5. Back to Admin Menu");
            Utils.printLine(60);
            System.out.print("Please select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (choice >= 1 && choice <= 5) {
                    break;
                } else {
                    Utils.displayHeader("Manage Products");
                    Utils.displayMessage("Invalid input, please enter a number between 1 and 5.");
                }
            } else {
                Utils.displayHeader("Manage Products");
                Utils.displayMessage("Invalid input, please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        switch (choice) {
            case 1:  // Add product
                Utils.displayHeader("Add Product");
                Utils.displayMessage("Loading.....");
                addProductMenu(scanner);
                break;
            case 2:  // Update product
                Utils.displayHeader("Update Product");
                Utils.displayMessage("Loading.....");
                updateProductMenu(scanner);
                break;
            case 3:  // Delete product
                Utils.displayHeader("Delete Product");
                Utils.displayMessage("Loading.....");
                deleteProductMenu(scanner);
                break;
            case 4:  // View products
                Utils.displayHeader("View Products");
                Utils.displayMessage("Loading.....");
                Utils.displayHeader("View Products");
                Utils.printCentered("Products");
                Utils.printLine(60);
                listProductsWID();
                Utils.printLine(60);
                pauseUntilEnter(scanner);
                break;
                // searchProductMenu(scanner);
            case 5:  // Back to Admin Menu
                Utils.displayHeader("Manage Products");
                Utils.displayMessage("Exiting.....");
                Utils.clearConsole();
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        manageProductMenu(scanner); // Recursive call to continue managing products
    }

    // Add Product Menu
    private void addProductMenu(Scanner scanner) {
        int genChoice = 0;
        int spChoice = 0;
        String gencat = "";
        String spcat = "";

        while (true) {
            Utils.displayHeader("Add Product");
            Utils.printCentered(" Product Details");
            Utils.productCat();
            Utils.printLine(60);
            System.out.print(" Please select the Product's General Category: ");

            if (scanner.hasNextInt()) {
                genChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (genChoice) {
                    case 1: 
                        gencat = "Groceries"; 
                        break;
                    case 2: 
                        gencat = "Home & Kitchen"; 
                        break;
                    case 3: 
                        gencat = "Health & Beauty"; 
                        break;
                    case 4: 
                        gencat = "Books & Stationery"; 
                        break;
                    case 5: 
                        gencat = "Electronics"; 
                        break;
                    case 6: 
                        gencat = "Others"; 
                        break;
                    default:
                        Utils.displayMessage("Invalid input. Please select a number between 1 and 6.");
                        continue;
                }
                break;
            } else {
                Utils.displayMessage("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        if (!gencat.equals("Others")) {
            while (true) {
                Utils.displayHeader("Add Product");
                Utils.printCentered(" Product Details");
                displaySubcategories(gencat);
                Utils.printLine(60);
                System.out.println(" Product Category: " + gencat);
                System.out.print(" Please select Sub Category: ");
                if (scanner.hasNextInt()) {
                    spChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    spcat = getSpecificCategory(gencat, spChoice);
                    if (spcat != null) {
                        break;
                    } else {
                        Utils.displayMessage("Invalid input. Please select a valid option.");
                    }
                } else {
                    Utils.displayMessage("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
        } else {
            Utils.displayHeader("Add Product");
            Utils.printCentered(" Product Details");
            displaySubcategories(gencat);
            System.out.println(" Product Category: " + gencat);
            System.out.print(" Please enter Sub Category: ");
            spcat = scanner.nextLine();
        }

        Utils.displayHeader("Add Product");
        Utils.printCentered(" Product Details");
        System.out.println(" Product Category: " + gencat);
        System.out.println(" " + gencat + " Category: " + spcat);
        System.out.print(" Please enter Product Name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
        } else if (!isProductNameUnique(name)) {
            System.out.println("A product with this name already exists. Please choose a different name.");
        }

        int stockLevel = 0;
        while (true) {
            Utils.displayHeader("Add Product");
            Utils.printCentered(" Product Details");
            System.out.println(" Product Category: " + gencat);
            System.out.println(" " + gencat + " Category: " + spcat);
            System.out.println(" Product Name: " + name);
            System.out.print(" Please enter Stock Level: ");
            if (scanner.hasNextInt()) {
                stockLevel = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (stockLevel >= 0) {
                    break;
                } else {
                    Utils.displayMessage("Stock level cannot be negative. Please try again.");
                }
            } else {
                Utils.displayMessage("Invalid input. Please enter a positive number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        String expirationDate = null;
        while (true) {
            Utils.displayHeader("Add Product");
            Utils.printCentered(" Product Details");
            System.out.println(" Product Category: " + gencat);
            System.out.println(" " + gencat + " Category: " + spcat);
            System.out.println(" Product Name: " + name);
            System.out.println(" Stock Level: " + stockLevel);
            System.out.print(" Enter Expiration Date (YYYY-MM-DD): ");
            String expirationDateStr = scanner.nextLine().trim();
            if (expirationDateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                expirationDate = expirationDateStr; // Valid date format
                break;
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        Utils.displayHeader("Add Product");
        Utils.printCentered("Confirm Product Details");
        System.out.println(" Product Category: " + gencat);
        System.out.println(" " + gencat + " Category: " + spcat);
        System.out.println(" Product Name: " + name);
        System.out.println(" Stock Level: " + stockLevel);
        System.out.println(" Expiration Date (YYYY-MM-DD): " + expirationDate);
        Product newProduct = new Product(name, gencat, spcat, stockLevel, expirationDate);

        while (true) {
            Utils.printLine(60);
            System.out.print(" Are you sure you want to add " + name + "? (Y/N): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y")) {
                Utils.displayHeader("Add Product");
                Utils.displayMessage("Product '" + name + "' added successfully.");
                addProduct(newProduct);
                break;
            } else if (confirmation.equals("n")) {
                Utils.displayHeader("Add Product");
                Utils.displayMessage("Product addition canceled. Returning to menu...");
                break;
            } else {
                Utils.displayMessage("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }
    
    // Method to handle specific options based on chosen category
    private void displaySubcategories(String gencat) {
        switch (gencat) {
            case "Groceries":
                Utils.grocery();
                break;
            case "Home & Kitchen":
                Utils.hK();
                break;
            case "Health & Beauty":
                Utils.hB();
                break;
            case "Books & Stationery":
                Utils.bS();
                break;
            case "Electronics":
                Utils.electonics();             
                break;
            case "Others":
                break;
            default:
                Utils.displayMessage("No options available for this category.");
                return;
        }
    }

    private String getSpecificCategory(String spcat, int spChoice) {
        switch (spcat) {
            case "Groceries":
                switch (spChoice) {
                    case 1:
                        return "Fresh Produce";
                    case 2:
                        return "Dairy Products";
                    case 3:
                        return "Meat & Seafood";
                    case 4:
                        return "Dry Goods";
                    case 5: 
                        return "Snacks & Confectionery";
                    case 6: 
                        return "Beverages";
                    case 7: 
                        return "Canned & Packaged Foods";
                    case 8: 
                        return "Frozen Foods";
                };
            case "Home & Kitchen":
                switch (spChoice) {
                    case 1: 
                        return "Furniture";
                    case 2: 
                        return "Cookware";
                    case 3: 
                        return "Tableware";
                    case 4: 
                        return "Storage Solutions";
                    case 5: 
                        return "Cleaning Supplies";
                    case 6: 
                        return "Home Decor";
                    case 7:
                        return "Lighting";
                };
            case "Health & Beauty":
                switch (spChoice) {
                    case 1: 
                        return "Skincare";
                    case 2:
                        return "Makeup";
                    case 3: 
                        return "Hair Care";
                    case 4: 
                        return "Personal Hygiene";
                    case 5: 
                        return "Fitness Equipment";
                    case 6: 
                        return "Medical Supplies";
                    case 7: 
                        return "Supplements & Vitamins";
                };
            case "Books & Stationery":
                switch (spChoice) {
                    case 1:  
                        return "Fiction & Non-Fiction Books";
                    case 2:  
                        return "Textbooks & Educational Material";
                    case 3: 
                        return "Notebooks & Journals";
                    case 4:  
                        return "Writing Instruments";
                    case 5:  
                        return "Art Supplies";
                    case 6: 
                        return "Calendars & Planners";
                };
            case "Electronics":
                switch (spChoice) {
                    case 1: 
                        return "Smartphones";
                    case 2: 
                        return "Laptops and Computers";
                    case 3:  
                        return "Audio Devices";
                    case 4:     
                        return "Cameras & Photography Equipment";
                    case 5: 
                        return "Smart Home Devices";
                    case 6:     
                        return "Wearables";
                    case 7:  
                        return "Home Appliances";
                };
            case "Others":
                return null;
            default:
                return null;
        }
    }
    
    
    // Update product menu
    private void updateProductMenu(Scanner scanner) {
        Utils.displayHeader("Update Product");
        System.out.print(" Enter product name to update: ");
        String oldName = scanner.nextLine().trim();

        if (oldName.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }
        Product product = findProductByName(oldName);
        if (product == null) {
            Utils.displayMessage("Product not found.");
            return;
        }

        while (true) {
            int choice = 0;
            Utils.displayHeader("Update Product");
            System.out.println(" What would you like to update?");
            System.out.println("  1. Product Name");
            System.out.println("  2. Stock Level");
            System.out.println("  3. Cancel");
            Utils.printLine(60);
            System.out.print("Plese select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                Utils.displayHeader("Update Product");
                System.out.println(" Product Name: " + oldName);
                switch (choice) {
                    case 1: // Update product name
                        System.out.print(" Enter new product name or leave blank: ");
                        String newName = scanner.nextLine();
                        if (newName.isEmpty()) {
                            Utils.displayMessage("Product name retained.");
                            newName = oldName;
                        }
                        System.out.println(" New Product Name: " + newName);
                        updateProduct(oldName, newName, product.getStockLevel());
                        System.out.println("Product name updated successfully.");
                        return;
                    case 2: // Update stock level
                        int currentStockLevel = product.getStockLevel();
                        System.out.println("Current Stock Level: " + currentStockLevel);
                        System.out.print(" Enter stock adjustment: ");
                        if (scanner.hasNextInt()) {
                            int stockAdjustment = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            int newStockLevel = currentStockLevel + stockAdjustment;
                            if (newStockLevel < 0) {
                                System.out.println("Stock level cannot be negative. Setting stock level to 0.");
                                newStockLevel = 0; // Prevent negative stock level
                            }
                            System.out.println("Current Stock Level: " + currentStockLevel);
                            System.out.println("Stock Adjustment: " + stockAdjustment);
                            System.out.println("New Stock Level: " + newStockLevel);
                    
                            updateProduct(oldName, oldName, newStockLevel);
                            System.out.println("Stock level updated successfully.");
                            return;
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.nextLine(); // Consume invalid input
                        }
                        break;

                    case 3: 
                        System.out.println("Update canceled. Returning to menu...");
                        return;
 
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
            
                        
                    
                    
                    
    // Delete product menu
    private void deleteProductMenu(Scanner scanner) {
        Utils.displayHeader("Delete Product");
        listProductsWID();
        Utils.printLine(60);
        System.out.print(" Enter product ID to delete: ");
        int productId = -1;

        if (scanner.hasNextInt()) {
            productId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input. Please enter a valid numeric ID.");
            scanner.nextLine(); // Consume invalid input
            return;
        }
    
        // Check if the product ID exists in the database
        if (!isProductIdValid(productId)) {
            System.out.println("Product with ID " + productId + " not found in the database. Returning to menu...");
            return;
        }
    
        // Confirm deletion
        System.out.print("Are you sure you want to delete the product with ID " + productId + "? (Y/N): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
    
        if (confirmation.equals("y")) {
            deleteProductById(productId);
            System.out.println("Product with ID " + productId + " has been successfully deleted.");
        } else if (confirmation.equals("n")) {
            System.out.println("Product deletion canceled. Returning to menu...");
        } else {
            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
        }
    }

    private boolean isProductIdValid(int productId) {
        String query = "SELECT 1 FROM products WHERE ProductID = ?";
        try (Connection connection = connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a record is found, the ID is valid
        } catch (SQLException e) {
            System.err.println("Error validating product ID: " + e.getMessage());
            return false;
        }
    }

    private boolean isProductNameUnique(String name) {
        String query = "SELECT 1 FROM products WHERE ProductName = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // Returns true if no record is found
        } catch (SQLException e) {
            System.err.println("Error checking product name uniqueness: " + e.getMessage());
            return false;
        }
    }
    

    public void pauseUntilEnter(Scanner scanner) {
        System.out.print("\n Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
    }
    
    
}

