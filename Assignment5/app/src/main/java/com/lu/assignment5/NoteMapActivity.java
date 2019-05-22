package com.lu.assignment5;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

public class NoteMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DBManager myDBManager;
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ENTRY = "entry";
    public static final String EXTRA_LATITUDE = "latitude";
    public static final String EXTRA_LONGITUDE = "longitude";
    public static final String EXTRA_METHOD = "method";
    List<Note> notes = new LinkedList<Note>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_map);

        // Load list of notes
        loadNotes();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadNotes() {
        myDBManager = new DBManager(this);
        myDBManager.open();
        Cursor cur = myDBManager.getAllNotes();

        if (cur.getCount() != 0) {
            while(cur.moveToNext()) {
                long id = cur.getLong(cur.getColumnIndex(DBManager.NOTE_KEY_ROWID));
                long date = cur.getLong(cur.getColumnIndex(DBManager.NOTE_KEY_DATE_NOTE));
                String title = cur.getString(cur.getColumnIndex(DBManager.NOTE_KEY_TITLE_NOTE));
                String content = cur.getString(cur.getColumnIndex(DBManager.NOTE_KEY_CONTENT_NOTE));
                int latitude = cur.getInt(cur.getColumnIndex(DBManager.NOTE_KEY_LAT_NOTE));
                int longitude = cur.getInt(cur.getColumnIndex(DBManager.NOTE_KEY_LONG_NOTE));
                String method = cur.getString(cur.getColumnIndex(DBManager.NOTE_KEY_METHOD_NOTE));
                Note newNote = new Note(id, date, title, content, latitude, longitude, method);
                notes.add(newNote);
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Loop through notes and add markers
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            LatLng location = new LatLng(note.getLatitude(), note.getLongitude());
            Marker noteMarker = mMap.addMarker(new MarkerOptions().position(location).title(note.getTitle()));
            noteMarker.setTag(note);

            // Focus on the first note
            if (i == 0) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Note note = (Note)marker.getTag();
                Intent intent = new Intent(NoteMapActivity.this, NoteActivity.class);
                intent.putExtra(NoteMapActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(NoteMapActivity.EXTRA_ENTRY, note.getContent());
                intent.putExtra(NoteMapActivity.EXTRA_LATITUDE, note.getLatitude());
                intent.putExtra(NoteMapActivity.EXTRA_LONGITUDE, note.getLongitude());
                intent.putExtra(NoteMapActivity.EXTRA_METHOD, note.getMethod());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        myDBManager.close();
    }
}
