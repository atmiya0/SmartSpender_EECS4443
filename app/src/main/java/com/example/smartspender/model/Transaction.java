package com.example.smartspender.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String budget_name;
    private String budget_category_and_date;  // Category and date information
    private double budget_limit;

    public Transaction(String budget_name, String budget_category_and_date, double budget_limit) {
        this.budget_name = budget_name;
        this.budget_category_and_date = budget_category_and_date;
        this.budget_limit = budget_limit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBudget_name() { return budget_name; }
    public void setBudget_name(String budget_name) { this.budget_name = budget_name; }

    public String getBudget_category_and_date() { return budget_category_and_date; }
    public void setBudget_category_and_date(String budget_category_and_date) { this.budget_category_and_date = budget_category_and_date; }

    public double getBudget_limit() { return budget_limit; }
    public void setBudget_limit(double budget_limit) { this.budget_limit = budget_limit; }

    // Compatibility methods for existing code
    public String getName() { return getBudget_name(); }
    public String getDetails() { return getBudget_category_and_date(); }
    public double getAmount() { return getBudget_limit(); }
}