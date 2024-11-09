package com.example.pawnshop.controller;


import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.Item;
import com.example.pawnshop.view.ConsoleView;

public class LombardController {

    private LombardRepository repository;
    private ConsoleView view;

    public LombardController(LombardRepository repository, ConsoleView view) {
        this.repository = repository;
        this.view = view;
    }

    public void addItem() {
        Item item = view.getItemDetails();
        repository.addItem(item);
        view.displayMessage("Item added successfully!");
    }

    public void viewItems() {
        view.displayItems(repository.getItems());
    }

    public void updateItem() {
        int id = view.getItemId();
        Item existingItem = repository.getItemById(id);
        if (existingItem != null) {
            view.displayMessage("Updating item: " + existingItem);
            Item updatedItem = view.getItemDetails();
            repository.updateItem(id, updatedItem.getName(), updatedItem.getCategory(), updatedItem.getValue());
            view.displayMessage("Item updated successfully!");
        } else {
            view.displayMessage("Item not found!");
        }
    }

    public void removeItem() {
        int id = view.getItemId();
        Item item = repository.getItemById(id);
        if (item != null) {
            repository.removeItem(id);
            view.displayMessage("Item removed successfully!");
        } else {
            view.displayMessage("Item not found!");
        }
    }

    public void exit() {
        view.displayMessage("Exiting application. Goodbye!");
    }
}