package com.supperapper.timetablealerter.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.internal.LocationRequestUpdateData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.supperapper.timetablealerter.R;

public class MapViewActivity extends Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleMap.OnMapLongClickListener{

    private MapView mapView;

    private GoogleMap googleMap;

    private GoogleApiClient googleApiClient;

    private final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private Double Lat, Lng;

    Location lastKnowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);


        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addConnectionCallbacks(this);
        builder.addApi(LocationServices.API);
        googleApiClient = builder.build();
        googleApiClient.connect();



    }

    @Override
    protected void onStart() {
        super.onStart();

        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION){

            int grantResult = grantResults[0];
            if(grantResult == PackageManager.PERMISSION_GRANTED){
                checkPermissionAndGetCurrentLocation();
            } else {

                Toast.makeText(this, "Some feature can not use", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("", "onMapReady");
        this.googleMap = googleMap;

        LatLng ckccPosition = new LatLng(11.569350, 104.888461);
        CameraUpdate ckccCamera = CameraUpdateFactory.newLatLngZoom(ckccPosition, 15);
        googleMap.animateCamera(ckccCamera);
        addMarker(ckccPosition, "Cambodia-Korea Cooperation Center", "CKCC");

        Log.e("error", String.valueOf(ckccPosition));

        googleMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d("", "onConnected");
        checkPermissionAndGetCurrentLocation();
    }

    private void checkPermissionAndGetCurrentLocation(){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            Log.d("","Permission are not allowed");
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_LOCATION_PERMISSION);
            return;
        }

        Log.d("", "Permission is allowed");
        lastKnowLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(lastKnowLocation != null){
      //      Lat = lastKnowLocation.getLatitude();
     //       Lng = lastKnowLocation.getLongitude();
            LatLng currentPosition = new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude());
            addMarker(currentPosition, "Samnang 12", "");
            Log.e("Samnang 12", String.valueOf(currentPosition));

        }

        Log.d("", "Request for location update");
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    private void addMarker(LatLng position, String title, String snippet){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.snippet(snippet);
        markerOptions.position(position);
        googleMap.addMarker(markerOptions);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

     //   Lat = location.getLatitude();
     //   Lng = location.getLongitude();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        addMarker(latLng, "I'm here", "");
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Log.e("", "onMapLongCLICK");
        addMarker(latLng, "Here", "");
        Log.e("Here", String.valueOf(latLng));
    }
}
