package com.example.smartspender.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String expense_type;
    private final double expense_amount;
    private final String expense_date;

    public Expense(String expense_type, double expense_amount, String expense_date) {
        this.expense_type = expense_type;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public double getExpense_amount() {
        return expense_amount;
    }

    public String getExpense_date() {
        return expense_date;
    }
}