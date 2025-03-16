package com.example.smartspender.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String budget_name;
    private final String budget_category_and_date; 
    private final double budget_limit;

    public Budget(String budget_name, String budget_category_and_date, double budget_limit) {
        this.budget_name = budget_name;
        this.budget_category_and_date = budget_category_and_date;
        this.budget_limit = budget_limit;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getBudget_name() { return budget_name; }

    public String getBudget_category_and_date() { return budget_category_and_date; }

    public double getBudget_limit() { return budget_limit; }

}