package com.lu.assignment5;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "note_db";
    private static final String CREATE_NOTE_TABLE =
            "CREATE TABLE IF NOT EXISTS notes(" +
                    "_id integer PRIMARY KEY AUTOINCREMENT, " +
                    "content_note text NOT NULL," +
                    "title_note text NOT NULL," +
                    "date_note integer NOT NULL," +
                    "latitude_note double NOT NULL," +
                    "longitude_note double NOT NULL," +
                    "method_note text NOT NULL)";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(database);
    }

}
