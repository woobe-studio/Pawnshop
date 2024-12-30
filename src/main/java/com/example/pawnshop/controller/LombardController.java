package com.example.pawnshop.controller;

import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.Item;
import com.example.pawnshop.model.TransactionHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class LombardController {

    private LombardRepository repository;
    private TransactionHistory transactionHistory;

    private TableView<Item> tableView; // For displaying items
    private TextField idField, nameField, categoryField, valueField;
    private ChoiceBox<String> sortChoiceBox;

    public LombardController(LombardRepository repository, TableView<Item> tableView,
                             TextField idField, TextField nameField, TextField categoryField,
                             TextField valueField, ChoiceBox<String> sortChoiceBox,
                             TransactionHistory transactionHistory) {
        this.repository = repository;
        this.tableView = tableView;
        this.idField = idField;
        this.nameField = nameField;
        this.categoryField = categoryField;
        this.valueField = valueField;
        this.sortChoiceBox = sortChoiceBox;
        this.transactionHistory = transactionHistory;

        // Initialize sort options
        this.sortChoiceBox.setItems(FXCollections.observableArrayList(
                "Sort by ID (Ascending)", "Sort by ID (Descending)",
                "Sort by Price (Ascending)", "Sort by Price (Descending)"
        ));
    }

    public void addItem() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String category = categoryField.getText();
            double value = Double.parseDouble(valueField.getText());

            // Check for duplicate ID
            if (repository.getItemById(id) != null) {
                showAlert(AlertType.ERROR, "Error", "An item with ID " + id + " already exists.");
                return;
            }

            // Check for non-negative price
            if (value < 0) {
                showAlert(AlertType.ERROR, "Error", "Item price must be 0 or higher.");
                return;
            }

            Item newItem = new Item(id, name, category, value, "New");
            repository.addItem(newItem);
            transactionHistory.addHistory("Added item: " + newItem);
            refreshTableView();
            showAlert(AlertType.INFORMATION, "Success", "Item added successfully!");

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid input. Please check the fields.");
        }
    }

    public void viewItems() {
        int sortChoice = sortChoiceBox.getSelectionModel().getSelectedIndex();

        switch (sortChoice) {
            case 0:
                repository.sortItemsByIdAscending();
                break;
            case 1:
                repository.sortItemsByIdDescending();
                break;
            case 2:
                repository.sortItemsByPriceAscending();
                break;
            case 3:
                repository.sortItemsByPriceDescending();
                break;
            default:
                // No sorting
                break;
        }

        refreshTableView();
    }

    public void updateItem() {
        try {
            int id = Integer.parseInt(idField.getText());
            String newName = nameField.getText();
            String newCategory = categoryField.getText();
            double newValue = Double.parseDouble(valueField.getText());

            // Validate item existence
            Item existingItem = repository.getItemById(id);
            if (existingItem == null) {
                showAlert(AlertType.ERROR, "Error", "Item with ID " + id + " not found.");
                return;
            }

            // Check for non-negative price
            if (newValue < 0) {
                showAlert(AlertType.ERROR, "Error", "Item price must be 0 or higher.");
                return;
            }

            repository.updateItem(id, newName, newCategory, newValue);
            transactionHistory.addHistory("Updated item ID " + id + ": " + existingItem);
            refreshTableView();
            showAlert(AlertType.INFORMATION, "Success", "Item updated successfully!");

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid input. Please check the fields.");
        }
    }

    public void removeItem() {
        try {
            int id = Integer.parseInt(idField.getText());
            Item item = repository.getItemById(id);
            if (item != null) {
                repository.removeItem(id);
                transactionHistory.addHistory("Removed item ID " + id + ": " + item);
                refreshTableView();
                showAlert(AlertType.INFORMATION, "Success", "Item removed successfully!");
            } else {
                showAlert(AlertType.ERROR, "Error", "Item with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid input. Please enter a valid ID.");
        }
    }

    public void viewTransactionHistory() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Transaction History");
        alert.setHeaderText("Transaction History Log");
        alert.setContentText(String.join("\n", transactionHistory.getHistory()));
        alert.showAndWait();
    }

    private void refreshTableView() {
        ObservableList<Item> observableItems = FXCollections.observableArrayList(repository.getItems());
        tableView.setItems(observableItems);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
