package com.lu.assignment5;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    //DB fields for com.lu.NOTE table
    private static final String NOTE_DB_TABLE = "notes";
    public static final String NOTE_KEY_ROWID = "_id";
    public static final String NOTE_KEY_DATE_NOTE = "date_note";
    public static final String NOTE_KEY_TITLE_NOTE = "title_note";
    public static final String NOTE_KEY_CONTENT_NOTE = "content_note";
    public static final String NOTE_KEY_LAT_NOTE = "latitude_note";
    public static final String NOTE_KEY_LONG_NOTE = "longitude_note";
    public static final String NOTE_KEY_METHOD_NOTE = "method_note";

    private Context context;
    private SQLiteDatabase database;
    private MyDBHandler dbHandler;

    public DBManager(Context context) {
        this.context = context;
    }

    public void open() throws SQLException{
        dbHandler = new MyDBHandler(context);
        database = dbHandler.getWritableDatabase();
    }


    public void close() {
        dbHandler.close();
    }

    private ContentValues createNoteValues(int date, String title, String content, double latitude, double longitude, String method) {
        ContentValues values = new ContentValues();
        values.put(NOTE_KEY_DATE_NOTE, date);
        values.put(NOTE_KEY_TITLE_NOTE, title);
        values.put(NOTE_KEY_CONTENT_NOTE, content);
        values.put(NOTE_KEY_LAT_NOTE, latitude);
        values.put(NOTE_KEY_LONG_NOTE, longitude);
        values.put(NOTE_KEY_METHOD_NOTE, method);
        return values;
    }

    public long createNote(int date, String title, String content, double latitude, double longitude, String method) {
        ContentValues initialValues = createNoteValues(date, title, content, latitude, longitude, method);
        return database.insert(NOTE_DB_TABLE, null, initialValues);
    }

    public Cursor getAllNotes() {
        return database.query(NOTE_DB_TABLE, new String[] {
                NOTE_KEY_ROWID, NOTE_KEY_DATE_NOTE, NOTE_KEY_TITLE_NOTE, NOTE_KEY_CONTENT_NOTE, NOTE_KEY_LAT_NOTE, NOTE_KEY_LONG_NOTE, NOTE_KEY_METHOD_NOTE
        }, null, null, null, null, null);
    }
}
