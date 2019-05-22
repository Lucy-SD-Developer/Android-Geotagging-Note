package com.lu.assignment5;

public class Note {
    private long noteID;
    private long date;
    private String title;
    private String content;
    private double latitude;
    private double longitude;
    private String method;

    public Note(long noteID, long date, String title, String content, double latitude, double longitude, String method) {
        this.noteID = noteID;
        this.date = date;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.method = method;
    }

    public long getnoteID() {
        return noteID;
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

    public double getLatitude() {return latitude;}

    public double getLongitude() {return longitude;}

    public String getMethod() {return method;}

    public String toString() {
        return title;
    }
}

