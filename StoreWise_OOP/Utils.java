package StoreWise_OOP;

import StoreWise_OOP.Manage_Users.User;

public class Utils {
    private static final int CONSOLE_WIDTH = 60;

    public static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-"); 
        }
        System.out.println(); 
    }

    public static void printCentered(String text) {
        if (text != null) {
            int spacesToPad = (CONSOLE_WIDTH - text.length()) / 2;
            String padding = " ".repeat(spacesToPad);
            System.out.println(padding + text);
        }
    }

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name").toLowerCase();
            if (operatingSystem.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    public static void sleepFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printMenuGreeting(User currentUser) {
        printLine(60);
        printCentered(currentUser.getRole() + " Menu");
        printLine(60);
        printCentered("Welcome to StoreWise, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")!");
        sleepFor(2000);
        clearConsole();
    }

    public static void displayUserHeader(User currentUser) {
        clearConsole();
        printLine(60);
        printCentered(currentUser.getRole() + " Menu");
        printLine(60);
        System.out.println(" What would you like to do today, " + currentUser.getUsername() + "?");
    }

    public static void displayHeader(String title) {
        clearConsole();
        printLine(60);
        printCentered(title);
        printLine(60);
    }

    public static void displayMessage(String message) {
        printCentered(message);
        sleepFor(1500);
        clearConsole();
    }

    public static void role() {
        printCentered("*- - - - - - - - - -*");
        printCentered("|     User Roles    |");
        printCentered("|- - - - - - - - - -|");
        printCentered("|  1. Admin         |");
        printCentered("|  2. Employee      |");
        printCentered("*- - - - - - - - - -*");
    }

    public static void productCat() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|              Product Categories           |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - -| ");
        Utils.printCentered("|  1. Groceries           5. Electronics    |");
        Utils.printCentered("|  2. Home & Kitchen      6. Others         |");
        Utils.printCentered("|  3. Health & Beauty                       |");
        Utils.printCentered("|  4. Books & Stationery                    |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - -*");
    }

    public static void grocery() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|                    Groceries                    |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - - - - -|");
        Utils.printCentered("|  1. Fresh Produce   5. Snacks & Confectionery   |");
        Utils.printCentered("|  2. Dairy Products  6. Beverages                |");
        Utils.printCentered("|  3. Meat & Seafood  7. Canned & Packaged Foods  |");
        Utils.printCentered("|  4. Dry Goods       8. Frozen Foods             |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - -*");
    }

    public static void hK() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|                 Home & Kitchen                |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - - - -|");
        Utils.printCentered("|  1. Furniture          5. Cleaning Supplies   |");
        Utils.printCentered("|  2. Cookware           6. Home Decor          |");
        Utils.printCentered("|  3. Tableware          7. Lighting            |");
        Utils.printCentered("|  4. Storage Solutions                         |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - -*");
    }

    public static void hB() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|                  Health & Beauty                  |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - - - - - -|");
        Utils.printCentered("|  1. Skincare           5. Fitness Equipment       |");
        Utils.printCentered("|  2. Makeup             6. Medical Supplies        |");
        Utils.printCentered("|  3. Hair Care          7. Supplements & Vitamins  |");
        Utils.printCentered("|  4. Personal Hygiene                              |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - -*");
    }

    public static void bS() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|                      Books & Stationery                       |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
        Utils.printCentered("|  1. Fiction & Non-Fiction Books      4. Writing Instruments   |");
        Utils.printCentered("|  2. Textbooks & Educational Material 5. Art Supplies          |");
        Utils.printCentered("|  3. Notebooks & Journals             6. Calendars & Planners  |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*");
    }

    public static void electonics() {
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - -*");
        Utils.printCentered("|                    Electronics                    |");
        Utils.printCentered("|- - - - - - - - - - - - - - - - - - - - - - - - - -|");
        Utils.printCentered("|  1. Smartphones            5. Smart Home Devices  |");
        Utils.printCentered("|  2. Laptops and Computers  6. Wearables           |");
        Utils.printCentered("|  3. Audio Devices          7. Home Appliances     |");
        Utils.printCentered("|  4. Cameras & Photography                         |");
        Utils.printCentered("|     Equipment                                     |");
        Utils.printCentered("*- - - - - - - - - - - - - - - - - - - - - - - - - -*");
    }



}
