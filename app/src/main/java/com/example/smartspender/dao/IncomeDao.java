package com.example.smartspender.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.smartspender.model.Income;
import java.util.List;

@Dao
public interface IncomeDao {
    @Insert
    void insert(Income income);

    @Delete
    void delete(Income income);

    @Query("SELECT * FROM incomes ORDER BY id DESC")
    LiveData<List<Income>> getAllIncomes();

}
