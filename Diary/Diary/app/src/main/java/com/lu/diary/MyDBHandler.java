package com.lu.diary;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "diary_db";
    private static final String CREATE_DIARY_TABLE =
            "CREATE TABLE IF NOT EXISTS diaries(" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "content_diary text NOT NULL," +
            "title_diary text NOT NULL," +
            "date_diary integer NOT NULL)";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_DIARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS diaries");
        onCreate(database);
    }

}
