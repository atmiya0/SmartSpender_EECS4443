package com.example.smartspender.model;

public class Transaction {
    private String name;
    private String details;  // Can be date, category, etc.
    private double amount;

    public Transaction(String name, String details, double amount) {
        this.name = name;
        this.details = details;
        this.amount = amount;
    }

    public String getName() { return name; }
    public String getDetails() { return details; }
    public double getAmount() { return amount; }
}