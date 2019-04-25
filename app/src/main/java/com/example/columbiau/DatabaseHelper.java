package com.example.columbiau;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Database";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE NEWS (_id INTEGER PRIMARY KEY AUTOINCREMENT, HEADLINE TEXT, IMAGEURL TEXT, ARTICLEURL TEXT);");
        db.execSQL("CREATE TABLE EVENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, TIME TEXT, EVENTNAME TEXT, PLACE TEXT, URLLINK TEXT, DOMAIN TEXT, CLUB TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
