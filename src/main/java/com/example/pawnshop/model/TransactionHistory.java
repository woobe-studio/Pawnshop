package com.example.pawnshop.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private List<String> history; // List to store the history of actions

    public TransactionHistory() {
        history = new ArrayList<>();
    }

    // Method to add a new history entry
    public void addHistory(String entry) {
        history.add(entry);
    }

    // Method to display the entire history
    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }

    // Get the full history (optional)
    public List<String> getHistory() {
        return history;
    }
}
