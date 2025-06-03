package com.s22010695.chamuditha;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //create objects
    private GoogleMap map;
    private List<Address> addressList;
    private EditText searchQuery;

    //create variables
    private double longitude = 80.7718;
    private double latitude = 7.8731;
    private float zoom = 7;
    private String title = "Sri Lanka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_google_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng currentLocation = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(currentLocation).title(title));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    /*search location function*/
    public void showLocation(View view) {
        //get search query from id
        searchQuery = findViewById(R.id.searchQuery);
        //convert search query string
        String location = searchQuery.getText().toString();

        //search query validation
        if (location.equals("")) {
            //if query is empty toast a error message
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            addressList = new Geocoder(this).getFromLocationName(location, 1);

            //check searched address is there or not
            if (addressList != null) {
                //get latitude and longitude from addressList
                latitude = addressList.get(0).getLatitude();
                longitude = addressList.get(0).getLongitude();

                //set new LatLng, clear current marker, add new marker and zoom new location
                LatLng newLocation = new LatLng(latitude, longitude);
                map.clear();
                map.addMarker(new MarkerOptions().position(newLocation).title(location));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 16.0f));

            } else {

                //if searched location not found toast a error message
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //if any error toast a error message
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    /*navigate to main activity using intent*/
    public void navToLoginActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*navigate to audio activity using intent*/
    public void navToAudioActivity(View view) {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }
}