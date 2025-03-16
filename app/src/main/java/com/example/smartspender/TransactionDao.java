package com.example.smartspender;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.smartspender.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    LiveData<List<Transaction>> getAllTransactions();
    
}
