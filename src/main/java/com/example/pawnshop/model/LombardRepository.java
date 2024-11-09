package com.example.pawnshop.model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LombardRepository {

    private List<Item> items = new ArrayList<>();
    private static final String FILE_PATH = "items.json"; // File path for saving and loading data

    public LombardRepository() {
        loadFromFile(); // Load items from file when the repository is created
    }

    public void addItem(Item item) {
        items.add(item);
        saveToFile(); // Save data to file after adding an item
    }

    public void removeItem(int id) {
        Item item = getItemById(id);
        if (item != null) {
            items.remove(item);
            saveToFile(); // Save data to file after removing an item
        }
    }

    public void updateItem(int id, String newName, String newCategory, double newValue) {
        Item item = getItemById(id);
        if (item != null) {
            item.setStatus("Updated");
            items.set(items.indexOf(item), new Item(id, newName, newCategory, newValue, item.getStatus()));
            saveToFile(); // Save data to file after updating an item
        }
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

    // Save items to a JSON file
    private void saveToFile() {
        JSONArray jsonArray = new JSONArray();
        for (Item item : items) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getName());
            jsonObject.put("category", item.getCategory());
            jsonObject.put("value", item.getValue());
            jsonObject.put("status", item.getStatus());
            jsonArray.put(jsonObject);
        }

        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(jsonArray.toString(4)); // Pretty print the JSON with an indentation of 4 spaces
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load items from a JSON file
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String category = jsonObject.getString("category");
                    double value = jsonObject.getDouble("value");
                    String status = jsonObject.getString("status");
                    items.add(new Item(id, name, category, value, status));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}