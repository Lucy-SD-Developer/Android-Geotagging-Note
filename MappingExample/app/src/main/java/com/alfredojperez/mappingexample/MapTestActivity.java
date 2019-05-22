package com.alfredojperez.mappingexample;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTestActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker currentLocMarker;
    TextView latitude, longitude, locMethod;

    //Web Service Invocation
    private boolean hasRequested = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        latitude = (TextView) this.findViewById(R.id.latitude);
        longitude = (TextView) this.findViewById(R.id.longitude);
        locMethod =  (TextView) this.findViewById(R.id.method);

        subscribeLocationProvider();


        MapFragment mapFragment = MapFragment.newInstance().newInstance();

        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_space, mapFragment);
        fragmentTransaction.commit();

        mapFragment.getMapAsync(this);
        subscribeLocationProvider();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        currentLocMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        subscribeLocationProvider();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(currentLocMarker != null)
        {
            currentLocMarker.remove();

            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());

            currentLocMarker = mMap.addMarker(new MarkerOptions().position(current).title(location.getProvider()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }

        latitude.setText(""+location.getLatitude());
        longitude.setText(""+location.getLongitude());
        locMethod.setText(""+location.getProvider());

        if(!hasRequested)
        {
            hasRequested = true;
            new WeatherRequesterTask(this).execute(location);
        }
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
        locman.removeUpdates(this);
    }
}
