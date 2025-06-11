package com.s22010695.chamuditha;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //create relevant variables
    public static final String DATA_BASE_NAME = "Chamuditha.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "Username";
    public static final String COL_2 = "Password";

    //create database method
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //create table method
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "USERNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //insert data method
    public boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);

        long response = db.insert(TABLE_NAME, null, contentValues);

        return response != -1;
    }
}
