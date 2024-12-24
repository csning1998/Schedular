// src/main/java/com/example/schedule/DBHelper.java
package com.example.schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLASSES = "classes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEACHER = "teacher";
    public static final String COLUMN_DAY_INDEX = "day_index";
    public static final String COLUMN_TIME_INDEX = "time_index";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLASSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_TEACHER + " TEXT, " +
                    COLUMN_DAY_INDEX + " INTEGER, " +
                    COLUMN_TIME_INDEX + " INTEGER" +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
