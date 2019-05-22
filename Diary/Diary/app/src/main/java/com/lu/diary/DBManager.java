package com.lu.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    //DB fields for com.lu.diary table
    private static final String DIARY_DB_TABLE = "diaries";
    public static final String DIARY_KEY_ROWID = "_id";
    public static final String DIARY_KEY_DATE_DIARY = "date_diary";
    public static final String DIARY_KEY_TITLE_DIARY = "title_diary";
    public static final String DIARY_KEY_CONTENT_DIARY = "content_diary";

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

    private ContentValues createDiaryValues(int date, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(DIARY_KEY_DATE_DIARY, date);
        values.put(DIARY_KEY_TITLE_DIARY, title);
        values.put(DIARY_KEY_CONTENT_DIARY, content);
        return values;
    }

    public long createDiary(int date, String title, String content) {
        ContentValues initialValues = createDiaryValues(date, title, content);
        return database.insert(DIARY_DB_TABLE, null, initialValues);
    }

    public Cursor getAllDiaries() {
        return database.query(DIARY_DB_TABLE, new String[] {
            DIARY_KEY_ROWID, DIARY_KEY_DATE_DIARY, DIARY_KEY_TITLE_DIARY, DIARY_KEY_CONTENT_DIARY
        }, null, null, null, null, null);
    }

    public Cursor getDiary(long rowID) throws SQLException {
        Cursor mCursor = database.query(true, DIARY_DB_TABLE, new String[] {
            DIARY_KEY_ROWID, DIARY_KEY_DATE_DIARY, DIARY_KEY_TITLE_DIARY, DIARY_KEY_CONTENT_DIARY
        }, DIARY_KEY_ROWID + "=" + rowID, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
