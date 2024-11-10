package com.example.pawnshop.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private List<String> history;
    private static final String FILE_PATH = "transaction_history.json"; // JSON file for saving and loading history

    public TransactionHistory() {
        history = new ArrayList<>();
        loadFromFile(); // Load history from file when the history object is created
    }

    // Method to add a new history entry
    public void addHistory(String entry) {
        history.add(entry);
        saveToFile(); // Save data to file after adding an entry
    }

    // Get the full history list
    public List<String> getHistory() {
        return history;
    }

    // Save the history to a JSON file
    private void saveToFile() {
        JSONArray jsonArray = new JSONArray();
        for (String entry : history) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("entry", entry);
            jsonArray.put(jsonObject);
        }

        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(jsonArray.toString(4)); // Pretty print with an indentation of 4 spaces
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the history from a JSON file
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
                    history.add(jsonObject.getString("entry"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
