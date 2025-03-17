package com.example.smartspender.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.smartspender.model.Expense;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expenses ORDER BY id DESC")
    LiveData<List<Expense>> getAllExpenses();
    
    @Query("DELETE FROM expenses")
    void deleteAllExpenses();
}
