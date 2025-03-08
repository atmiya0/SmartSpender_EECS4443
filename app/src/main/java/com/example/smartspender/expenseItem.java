package com.example.smartspender;

public class expenseItem {
    private String category;
    private int amount;
    private String date;

    // Constructor
    public expenseItem(String category, int amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getter and Setter
    public String getCategory() {
        return category;
    }

    public void setCategory(String title) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
