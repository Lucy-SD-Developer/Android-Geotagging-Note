package com.alfredojperez.locmappingex_v21;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView latitude, longitude, locMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = (TextView) this.findViewById(R.id.latitude);
        longitude = (TextView) this.findViewById(R.id.longitude);
        locMethod =  (TextView) this.findViewById(R.id.method);

        subscribeLocationProvider();
    }

    private void subscribeLocationProvider() {
        LocationManager locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeLocationProvider();
    }

    @Override
    public void onStop() {
        super.onStop();

        LocationManager locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locman.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude.setText(""+location.getLatitude());
        longitude.setText(""+location.getLongitude());
        locMethod.setText(""+location.getProvider());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
