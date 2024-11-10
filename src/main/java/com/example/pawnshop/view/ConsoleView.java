package com.example.pawnshop.view;

import com.example.pawnshop.model.Item;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    // Display the main menu
    public void displayMenu() {
        System.out.println("\nLombard Application");
        System.out.println("1. Add Item");
        System.out.println("2. View Items");
        System.out.println("3. Update Item");
        System.out.println("4. Remove Item");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // Display a list of items
    public void displayItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    // Get user's choice from the menu
    public int getChoice() {
        return scanner.nextInt();
    }

    // Prompt the user for item details to add or update an item
    public Item getItemDetails() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Value: ");
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return new Item(id, name, category, value, "Available");
    }

    // Prompt the user for an item ID (to update or remove an item)
    public int getItemId() {
        System.out.print("Enter the ID of the item to update/remove: ");
        return scanner.nextInt();
    }

    // Display a message to the user
    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Display sorting options (when viewing items)
    public void displaySortMenu() {
        System.out.println("\nSort Options:");
        System.out.println("1. Sort by ID (Ascending)");
        System.out.println("2. Sort by ID (Descending)");
        System.out.println("3. Sort by Price (Ascending)");
        System.out.println("4. Sort by Price (Descending)");
        System.out.println("5. Do not sort");
        System.out.print("Choose a sorting option: ");
    }

    // Get sorting choice
    public int getSortChoice() {
        return scanner.nextInt();
    }

    public void displayHistory(List<String> history) {
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            System.out.println("\nTransaction History:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }
}
