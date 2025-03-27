package com.example.smartspender.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartspender.dao.BudgetDao;
import com.example.smartspender.model.Budget;

@Database(entities = {Budget.class}, version = 3, exportSchema = false)
public abstract class BudgetDatabase extends RoomDatabase{
    private static BudgetDatabase instance;

    public abstract BudgetDao budgetDao();

    public static synchronized BudgetDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BudgetDatabase.class, "budgets_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
