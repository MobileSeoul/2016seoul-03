package com.example.changk.urionnuri.storedetail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.Store;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by hoc on 2016-10-19.
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Store store;
    private double lati, longi;
    private TextView map_location;
    private CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        map_location = (TextView)findViewById(R.id.map_location);
        map_location.setOnClickListener(onClickListener);

        LinearLayout market_layout = (LinearLayout) findViewById(R.id.market_layout);
        market_layout.setVisibility(View.GONE);

        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");
        lati = store.getLoc_lati();
        longi = store.getLoc_longi();
        ImageView map_back = (ImageView) findViewById(R.id.map_back);
        Glide.with(this).load(R.drawable.back).thumbnail(0.1f).into(map_back);
        map_back.setOnClickListener(onClickListener);

        ImageView marketSeatch_img=(ImageView)findViewById(R.id.marketSeatch_img);
        Glide.with(this).load(R.drawable.placholder).thumbnail(0.1f).into(marketSeatch_img);

        ImageView mappin=(ImageView)findViewById(R.id.mappin);
        Glide.with(this).load(R.drawable.mappin).thumbnail(0.1f).into(mappin);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng storeLocation = new LatLng(lati, longi);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(store.getName());
        markerOptions.position(storeLocation);

        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(storeLocation));
        mMap.setMyLocationEnabled(true);

        cameraPosition = CameraPosition.builder()
                .target(storeLocation)
                .zoom(15)
                .build();

        // Animate the change in camera view over 2 seconds
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.clear();
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.map_back){
                finish();
            }if(v.getId()==R.id.map_location){
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    };
}
