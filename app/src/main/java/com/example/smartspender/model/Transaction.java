package com.example.smartspender.model;

public class Transaction {
    private String name;
    private String details;  // Can be date, category, etc.
    private String amount;

    public Transaction(String name, String details, String amount) {
        this.name = name;
        this.details = details;
        this.amount = amount;
    }

    public String getName() { return name; }
    public String getDetails() { return details; }
    public String getAmount() { return amount; }
}