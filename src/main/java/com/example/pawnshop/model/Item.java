package com.example.pawnshop.model;


public class Item {
    private int id;
    private String name;
    private String category;
    private double value;
    private String status;

    public Item(int id, String name, String category, double value, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.value = value;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Category: %s, Value: %.2f, Status: %s",
                id, name, category, value, status);
    }
}
