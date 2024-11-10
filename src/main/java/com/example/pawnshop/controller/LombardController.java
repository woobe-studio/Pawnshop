package com.example.pawnshop.controller;

import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.Item;
import com.example.pawnshop.model.TransactionHistory;
import com.example.pawnshop.view.ConsoleView;

public class LombardController {

    private LombardRepository repository;
    private ConsoleView view;
    private TransactionHistory transactionHistory;

    public LombardController(LombardRepository repository, ConsoleView view, TransactionHistory transactionHistory) {
        this.repository = repository;
        this.view = view;
        this.transactionHistory = transactionHistory;
    }

    public void addItem() {
        Item item = view.getItemDetails();

        // Check for duplicate ID
        if (repository.getItemById(item.getId()) != null) {
            view.displayMessage("Error: An item with ID " + item.getId() + " already exists.");
            return;
        }

        // Check for non-negative price
        if (item.getValue() < 0) {
            view.displayMessage("Error: Item price must be 0 or higher.");
            return;
        }

        repository.addItem(item);
        transactionHistory.addHistory("Added item: " + item);
        view.displayMessage("Item added successfully!");
    }

    public void viewItems() {
        // Prompt user for sorting choice first
        view.displaySortMenu();
        int choice = view.getSortChoice();

        // Apply sorting based on user choice
        switch (choice) {
            case 1:
                repository.sortItemsByIdAscending();
                view.displayMessage("Items sorted by ID (Ascending).");
                break;
            case 2:
                repository.sortItemsByIdDescending();
                view.displayMessage("Items sorted by ID (Descending).");
                break;
            case 3:
                repository.sortItemsByPriceAscending();
                view.displayMessage("Items sorted by Price (Ascending).");
                break;
            case 4:
                repository.sortItemsByPriceDescending();
                view.displayMessage("Items sorted by Price (Descending).");
                break;
            case 5:
                view.displayMessage("Items not sorted.");
                break;
            default:
                view.displayMessage("Invalid choice.");
                return;
        }

        // Now display the sorted items
        view.displayItems(repository.getItems());
    }

    public void updateItem() {
        int id = view.getItemId();
        Item existingItem = repository.getItemById(id);

        if (existingItem == null) {
            view.displayMessage("Error: Item not found.");
            return;
        }

        view.displayMessage("Updating item: " + existingItem);
        Item updatedItem = view.getItemDetails();

        // Check for non-negative price on update
        if (updatedItem.getValue() < 0) {
            view.displayMessage("Error: Item price must be 0 or higher.");
            return;
        }

        repository.updateItem(id, updatedItem.getName(), updatedItem.getCategory(), updatedItem.getValue());
        transactionHistory.addHistory("Updated item ID " + id + ": " + updatedItem);
        view.displayMessage("Item updated successfully!");
    }

    public void removeItem() {
        int id = view.getItemId();
        Item item = repository.getItemById(id);
        if (item != null) {
            repository.removeItem(id);
            transactionHistory.addHistory("Removed item ID " + id + ": " + item); // Record the removal
            view.displayMessage("Item removed successfully!");
        } else {
            view.displayMessage("Item not found!");
        }
    }

    public void exit() {
        view.displayMessage("Exiting application. Goodbye!");
    }

    public void viewTransactionHistory() {
        view.displayHistory(transactionHistory.getHistory());
    }
}