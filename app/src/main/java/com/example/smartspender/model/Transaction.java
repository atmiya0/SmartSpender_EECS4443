package com.example.smartspender.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String details;  // Can be date, category, etc.
    private double amount;

    public Transaction(String name, String details, double amount) {
        this.name = name;
        this.details = details;
        this.amount = amount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getDetails() { return details; }
    public double getAmount() { return amount; }
}