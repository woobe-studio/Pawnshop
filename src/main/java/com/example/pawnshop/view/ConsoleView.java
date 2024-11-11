package com.example.pawnshop.view;

import com.example.pawnshop.model.Item;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    // ANSI escape codes for text colors
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String GREEN = "\033[32m";
    private static final String CYAN = "\033[36m";
    private static final String YELLOW = "\033[33m";
    private static final String RED = "\033[31m";
    private static final String BLUE = "\033[34m";
    private static final String MAGENTA = "\033[35m";
    private static final String WHITE = "\033[37m";

    // Display the main menu with an ASCII box
    public void displayMenu() {
        System.out.println(CYAN + "==================== Lombard Application ====================" + RESET);
        System.out.println("| " + GREEN + "1. Add Item" + RESET + "                    |");
        System.out.println("| " + GREEN + "2. View Items" + RESET + "                   |");
        System.out.println("| " + GREEN + "3. Update Item" + RESET + "                 |");
        System.out.println("| " + GREEN + "4. Remove Item" + RESET + "                 |");
        System.out.println("| " + GREEN + "5. View Transaction History" + RESET + "     |");
        System.out.println("| " + GREEN + "6. Exit" + RESET + "                        |");
        System.out.println("============================================================");
        System.out.print(YELLOW + "Choose an option: " + RESET);
    }

    // Display a list of items with a bit of formatting
    public void displayItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println(RED + "No items available." + RESET);
        } else {
            System.out.println(BOLD + "==================== Items List ====================" + RESET);
            System.out.println("| " + BLUE + "ID" + RESET + "   | " + MAGENTA + "Name" + RESET + "    | " + CYAN + "Category" + RESET + " | " + GREEN + "Value" + RESET + "  | " + YELLOW + "Status" + RESET + " |");
            System.out.println("------------------------------------------------------------");
            for (Item item : items) {
                System.out.println("| " + BLUE + item.getId() + RESET + "   | " + MAGENTA + item.getName() + RESET + "    | " + CYAN + item.getCategory() + RESET + " | " + GREEN + item.getValue() + RESET + "  | " + YELLOW + item.getStatus() + RESET + " |");
            }
            System.out.println("============================================================");
        }
    }

    // Get user's choice from the menu
    public int getChoice() {
        return scanner.nextInt();
    }

    // Prompt the user for item details to add or update an item
    public Item getItemDetails() {
        System.out.print(YELLOW + "Enter ID: " + RESET);
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print(YELLOW + "Enter Name: " + RESET);
        String name = scanner.nextLine();
        System.out.print(YELLOW + "Enter Category: " + RESET);
        String category = scanner.nextLine();
        System.out.print(YELLOW + "Enter Value: " + RESET);
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return new Item(id, name, category, value, "Available");
    }

    // Prompt the user for an item ID (to update or remove an item)
    public int getItemId() {
        System.out.print(YELLOW + "Enter the ID of the item to update/remove: " + RESET);
        return scanner.nextInt();
    }

    // Display a message to the user with a visual emphasis
    public void displayMessage(String message) {
        System.out.println(GREEN + message + RESET);
    }

    // Display sorting options with a box
    public void displaySortMenu() {
        System.out.println(CYAN + "==================== Sort Options ====================" + RESET);
        System.out.println("| " + GREEN + "1." + RESET + " Sort by ID (Ascending)        |");
        System.out.println("| " + GREEN + "2." + RESET + " Sort by ID (Descending)       |");
        System.out.println("| " + GREEN + "3." + RESET + " Sort by Price (Ascending)    |");
        System.out.println("| " + GREEN + "4." + RESET + " Sort by Price (Descending)   |");
        System.out.println("| " + GREEN + "5." + RESET + " Do not sort                  |");
        System.out.println("============================================================");
        System.out.print(YELLOW + "Choose a sorting option: " + RESET);
    }

    // Get sorting choice
    public int getSortChoice() {
        return scanner.nextInt();
    }

    // Display transaction history in a box
    public void displayHistory(List<String> history) {
        if (history.isEmpty()) {
            System.out.println(RED + "No history available." + RESET);
        } else {
            System.out.println(CYAN + "==================== Transaction History ====================" + RESET);
            for (String entry : history) {
                System.out.println("| " + BLUE + entry + RESET + " |");
            }
            System.out.println("============================================================");
        }
    }
}
