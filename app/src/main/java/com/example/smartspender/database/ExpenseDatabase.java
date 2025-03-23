package com.example.smartspender.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartspender.dao.ExpenseDao;
import com.example.smartspender.model.Expense;

@Database(entities = {Expense.class}, version = 3, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase{
    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ExpenseDatabase.class, "expenses_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
