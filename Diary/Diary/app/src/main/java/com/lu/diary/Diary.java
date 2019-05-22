package com.lu.diary;

public class Diary {
    private long diaryID;
    private long date;
    private String title;
    private String content;

    public Diary(long diaryID, long date, String title, String content) {
        this.diaryID = diaryID;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public long getDiaryID() {
        return diaryID;
    }

    public long getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return title;
    }
}
