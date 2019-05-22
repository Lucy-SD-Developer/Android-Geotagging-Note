package com.lu.assignment5;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.lu.assignment5.databinding.ActivityNewNoteBinding;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class NewNoteActivity extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback{

    private ActivityNewNoteBinding binding;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            SetUpLocationManager();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_note);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = binding.txtTitle.getText().toString();
                final String entry = binding.txtEntry.getText().toString();

                // Validate
                if (title.trim().length() == 0) {
                    Toast.makeText(view.getContext(), "Title cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (entry.trim().length() == 0) {
                    Toast.makeText(view.getContext(), "Entry cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBManager dbManager = new DBManager(NewNoteActivity.this);
                dbManager.open();

                Date today = new Date();

                dbManager.createNote((int)(today.getTime() / 1000), title, entry, latitude, longitude, method);
                dbManager.close();

                Toast.makeText(NewNoteActivity.this, "New Note added", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Only one permission was requested so just check that
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SetUpLocationManager();
            return;
        }
        Toast.makeText(this, "Unable to access location information of this device!", Toast.LENGTH_SHORT).show();
    }

    private void SetUpLocationManager() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                method = location.getProvider();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) { }

            @Override
            public void onProviderEnabled(String s) { }

            @Override
            public void onProviderDisabled(String s) { }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
}
