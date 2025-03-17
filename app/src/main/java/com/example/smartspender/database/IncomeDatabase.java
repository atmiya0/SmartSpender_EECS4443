package com.example.smartspender.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartspender.dao.IncomeDao;
import com.example.smartspender.model.Income;

@Database(entities = {Income.class}, version = 3)
public abstract class IncomeDatabase extends RoomDatabase{
    private static IncomeDatabase instance;

    public abstract IncomeDao incomeDao();

    public static synchronized IncomeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            IncomeDatabase.class, "incomes_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
