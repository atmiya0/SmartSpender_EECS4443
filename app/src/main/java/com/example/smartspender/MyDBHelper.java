package com.example.smartspender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Contacts";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phone_no";




    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + KEY_NAME + " TEXT, " + KEY_PHONE_NO + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(String name, String phone_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE_NO, phone_no);
        db.insert(TABLE_NAME, null, values );
    }

    public ArrayList<ModalContact> fetchContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ModalContact> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            ModalContact modalContact = new ModalContact();
            modalContact.id = cursor.getInt(0);
            modalContact.name = cursor.getString(1);
            modalContact.phone_no = cursor.getString(2);

            arrayList.add(modalContact);
        }
        return arrayList;
    }

    public void updateContact(ModalContact contactModal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PHONE_NO, contactModal.phone_no);

        db.update(TABLE_NAME, values, KEY_ID + " = " + contactModal.id,null);
    }




}








