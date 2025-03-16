package com.example.smartspender;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartspender.model.Transaction;

@Database(entities = {Transaction.class}, version = 3)
public abstract class TransactionDatabase extends RoomDatabase{
    private static TransactionDatabase instance;

    public abstract TransactionDao transactionDao();

    public static synchronized TransactionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "budgets_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
