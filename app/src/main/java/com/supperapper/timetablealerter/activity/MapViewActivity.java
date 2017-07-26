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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.supperapper.timetablealerter.R;

public class MapViewActivity extends Activity implements OnMapReadyCallback{

    private MapView mapView;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
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
    public void onMapReady(GoogleMap googleMap) {

        Log.d("", "onMapReady");
        String name = getIntent().getStringExtra("mapName");
        String address = getIntent().getStringExtra("mapAddess");
        String lat = getIntent().getStringExtra("mapLat");
        String lang = getIntent().getStringExtra("mapLang");
        this.googleMap = googleMap;

        LatLng position = new LatLng(Double.parseDouble(lat), Double.parseDouble(lang));
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(position, 15);
        googleMap.animateCamera(camera);
        addMarker(position, name, address);
        Log.e("error", String.valueOf(position));

    }


    private void addMarker(LatLng position, String title, String snippet){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.snippet(snippet);
        markerOptions.position(position);
        googleMap.addMarker(markerOptions);
    }
}
