package StoreWise_OOP.System_Settings;

import java.util.Scanner;

public class SystemSettings {

    // Constructor
    public SystemSettings() {
    }

    // View System Logs
    public void viewSystemLogs() {
        // Here, for simplicity, we are simulating system logs. In a real system, logs might be stored in a file or database.
        System.out.println("\nSystem Logs:");
        System.out.println("------------");
        System.out.println("2024-11-21 14:30: System started.");
        System.out.println("2024-11-21 14:35: User logged in.");
        System.out.println("2024-11-21 14:40: Inventory updated.");
        System.out.println("2024-11-21 14:45: System shut down.");
    }

    // Adjust System Preferences (Example: setting language or timezone)
    public void adjustSystemPreferences() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAdjust System Preferences:");
        System.out.println("1. Set Language");
        System.out.println("2. Set Timezone");
        System.out.println("3. Back to Admin Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                setLanguage(scanner);
                break;
            case 2:
                setTimezone(scanner);
                break;
            case 3:
                return; // Back to admin menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Set Language Preference
    private void setLanguage(Scanner scanner) {
        System.out.print("Enter preferred language (e.g., English, Spanish): ");
        String language = scanner.nextLine();
        System.out.println("Language set to: " + language);
    }

    // Set Timezone Preference
    private void setTimezone(Scanner scanner) {
        System.out.print("Enter preferred timezone (e.g., GMT, UTC+1): ");
        String timezone = scanner.nextLine();
        System.out.println("Timezone set to: " + timezone);
    }

    // Manage System Settings Menu
    public void manageSystemSettingsMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n|— System Settings");
            System.out.println("1. View System Logs");
            System.out.println("2. Adjust System Preferences");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewSystemLogs();
                        break;
                    case 2:
                        adjustSystemPreferences();
                        break;
                    case 3:
                        return; // Exit to Admin Menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
