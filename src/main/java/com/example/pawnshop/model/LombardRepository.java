package com.example.pawnshop.model;


import java.util.ArrayList;
import java.util.List;

public class LombardRepository {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void updateItem(int id, String newName, String newCategory, double newValue) {
        Item item = getItemById(id);
        if (item != null) {
            item.setStatus("Updated");
            items.set(items.indexOf(item), new Item(id, newName, newCategory, newValue, item.getStatus()));
        }
    }

    public void removeItem(int id) {
        Item item = getItemById(id);
        if (item != null) {
            items.remove(item);
        }
    }
}
