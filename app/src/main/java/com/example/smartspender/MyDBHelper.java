package com.example.smartspender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    // Table: BUDGETS (Justin)
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE1_BUDGETS = "Budgets";
    private static final String TABLE_NAME1 = "Budgets";
    private static final String BUDGETS_KEY_ID = "id";
    private static final String BUDGETS_NAME = "budget_name";
    private static final String BUDGETS_CATEGORY = "budgets_category";




    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE1_BUDGETS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME1 + "(" + BUDGETS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + BUDGETS_NAME + " TEXT, " + BUDGETS_CATEGORY + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public void addBudget(String budget_name, String budget_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUDGETS_NAME, budget_name);
        values.put(BUDGETS_CATEGORY, budget_category);
        db.insert(TABLE_NAME1, null, values );
    }

    public ArrayList<ModalContact> fetchContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        ArrayList<ModalContact> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            ModalContact modalContact = new ModalContact();
            modalContact.budgets_key_id = cursor.getInt(0);
            modalContact.budgets_name = cursor.getString(1);
            modalContact.budgets_category = cursor.getString(2);

            arrayList.add(modalContact);
        }
        return arrayList;
    }

    public void updateContact(ModalContact contactModal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUDGETS_CATEGORY, contactModal.budgets_category);

        db.update(TABLE_NAME1, values, BUDGETS_KEY_ID + " = " + contactModal.budgets_key_id,null);
    }




}








