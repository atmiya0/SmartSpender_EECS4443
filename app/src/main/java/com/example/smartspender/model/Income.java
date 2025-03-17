package com.example.smartspender.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "incomes")
public class Income {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String income_type;
    private final double income_amount;
    private final String income_date;

    public Income(String income_type, double income_amount, String income_date) {
        this.income_type = income_type;
        this.income_amount = income_amount;
        this.income_date = income_date;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getIncome_type() {
        return income_type;
    }

    public double getIncome_amount() {
        return income_amount;
    }

    public String getIncome_date() {
        return income_date;
    }
}