////
////
////package com.example.mealmate;
////
////import android.os.Bundle;
////import android.util.Log;
////
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.appcompat.widget.Toolbar;
////
////import com.google.android.gms.maps.CameraUpdateFactory;
////import com.google.android.gms.maps.GoogleMap;
////import com.google.android.gms.maps.OnMapReadyCallback;
////import com.google.android.gms.maps.SupportMapFragment;
////import com.google.android.gms.maps.model.LatLng;
////import com.google.android.gms.maps.model.MarkerOptions;
////import com.example.mealmate.databinding.ActivityShopLocationsBinding;
////
////public class ShopLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {
////
////    private GoogleMap mMap;
////    private ActivityShopLocationsBinding binding;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////
////        binding = ActivityShopLocationsBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////
////        // Set up the Toolbar
////        Toolbar toolbar = findViewById(R.id.toolbarShopLocationList); // Replace with your Toolbar ID in the layout
////        setSupportActionBar(toolbar);
////
////        if (getSupportActionBar() != null) {
////            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enables the back arrow
////            getSupportActionBar().setTitle("Shop Locations");
////        }
////
////        // Obtain the SupportMapFragment and get notified when the map is ready to be used
////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id.map);
////
////        if (mapFragment != null) {
////            mapFragment.getMapAsync(this);
////        } else {
////            Log.e("ShopLocationsActivity", "MapFragment is null");
////        }
////    }
////
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////
////        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
////    }
////
////    @Override
////    public boolean onSupportNavigateUp() {
////        onBackPressed(); // Navigate back to the previous activity
////        return true;
////    }
////}
//
//package com.example.mealmate;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.example.mealmate.databinding.ActivityShopLocationsBinding;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ShopLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private ActivityShopLocationsBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityShopLocationsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Set up the Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbarShopLocationList);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("Shop Locations");
//        }
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        } else {
//            Log.e("ShopLocationsActivity", "MapFragment is null");
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // List of locations (latitude, longitude, and marker titles)
//        List<LatLng> locations = new ArrayList<>();
//        locations.add(new LatLng(54.8855, 1.3740)); // Sunderland,Asda
//        locations.add(new LatLng(54.9210,  1.3800)); // Sunderland,Tesco
//        locations.add(new LatLng(54.9330, 1.3710)); // Sunderland,Morrisons Seaburn
//
//        locations.add(new LatLng(54.9720, 1.5710)); //  Newcastle,Asda Byker Superstore
//        locations.add(new LatLng(55.0200, 1.6300)); //   Newcastle, Tesco
//        locations.add(new LatLng(54.9800, 1.6600)); // Newcastle , Morrisons Cowgate
//
//        for (LatLng location : locations) {
//            mMap.addMarker(new MarkerOptions().position(location).title("Available Supermarket at: " + location.toString()));
//        }
//
//        if (!locations.isEmpty()) {
//            LatLng firstLocation = locations.get(0);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed(); // Navigate back to the previous activity
//        return true;
//    }
//}
package com.example.mealmate;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mealmate.databinding.ActivityShopLocationsBinding;

public class ShopLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShopLocationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarShopLocationList); 
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Shop Locations");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("ShopLocationsActivity", "MapFragment is null");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //  markers for Sunderland
        addMarkerWithColor(new LatLng(54.8855, -1.3740), "Asda Sunderland Superstore", BitmapDescriptorFactory.HUE_RED);
        addMarkerWithColor(new LatLng(54.9210, -1.3800), "Tesco Extra Sunderland", BitmapDescriptorFactory.HUE_ORANGE);
        addMarkerWithColor(new LatLng(54.9330, -1.3710), "Morrisons Seaburn", BitmapDescriptorFactory.HUE_GREEN);

        //  markers for Newcastle
        addMarkerWithColor(new LatLng(54.9720, -1.5710), "Asda Byker Superstore", BitmapDescriptorFactory.HUE_BLUE);
        addMarkerWithColor(new LatLng(55.0200, -1.6300), "Tesco Extra Newcastle Upon Tyne", BitmapDescriptorFactory.HUE_VIOLET);
        addMarkerWithColor(new LatLng(54.9800, -1.6600), "Morrisons Cowgate", BitmapDescriptorFactory.HUE_YELLOW);

        // Move camera to the first marker (Sunderland area)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.8855, -1.3740), 10));
    }

    private void addMarkerWithColor(LatLng position, String title, float color) {
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
