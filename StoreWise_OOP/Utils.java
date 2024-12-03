package StoreWise_OOP;

import StoreWise_OOP.Manage_Users.User;

public class Utils {
    private static final int CONSOLE_WIDTH = 60;

    // Utility method to print a line of hyphens
    public static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");  // Print a hyphen for each iteration
        }
        System.out.println();  // Move to the next line after printing the hyphens
    }

    // Utility method to print text centered
    public static void printCentered(String text) {
        if (text != null) {
            int spacesToPad = (CONSOLE_WIDTH - text.length()) / 2;
            String padding = " ".repeat(spacesToPad);
            System.out.println(padding + text);
        }
    }

    // Utility method to clear the console screen
    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name").toLowerCase();
            if (operatingSystem.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based systems (Linux, macOS)
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    // Utility method to pause for a certain amount of time (milliseconds)
    public static void sleepFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Utility method to print a personalized greeting message based on the current user
    public static void printMenuGreeting(User currentUser) {
        printLine(60);
        printCentered(currentUser.getRole() + " Menu");
        printLine(60);
        printCentered("Welcome to StoreWise, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")!");
        sleepFor(2000);
        clearConsole();
    }

    // Utility method to display the menu header based on the user's role
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
        Utils.printCentered(message);
        Utils.sleepFor(1500);
        Utils.clearConsole();
    }
}