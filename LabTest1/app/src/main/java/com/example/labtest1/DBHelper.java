package com.example.labtest1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my.db";
    private static final int _DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, _DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE ITEMS (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT )";
        db.execSQL(query);

    }

    public void addItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", item);
        long result = db.insert("ITEMS", null, cv);
        if (result == -1) {
            Log.d("DBHelper", "Failed to insert data");
        } else {
            Log.d("DBHelper", "Data inserted successfully");
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
