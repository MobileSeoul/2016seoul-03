package com.example.changk.urionnuri.mapsearch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.App;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.MarketMarker;
import com.example.changk.urionnuri.onnuridb.OnnuriDB;
import com.example.changk.urionnuri.storesearch.StoreSearchActivity;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

/**
 * Created by hoc on 2016-10-20.
 */

public class MapSearchActivity extends BaseActivity implements OnMapReadyCallback, ClusterManager.OnClusterItemClickListener, GoogleMap.OnMapClickListener {
    private GoogleMap map;
    private ArrayList<MarketMarker> list;
    private TextView map_title;
    private TextView map_location;
    private TextView marketName;
    private TextView marketGps;
    private TextView marketAddr;
    private LinearLayout market_layout;
    private ClusterManager<MarketMarker> mClusterManager;
    private TextView marketsearch_txt_food;
    private TextView marketsearch_txt_cow;
    private TextView marketsearch_txt_clothes;
    private TextView marketsearch_txt_shoes;
    private TextView marketsearch_txt_fish;
    private TextView marketsearch_txt_rice;
    private TextView marketsearch_txt_msg;
    private TextView marketsearch_txt_appliances;
    private TextView marketsearch_txt_retail;
    private TextView marketsearch_txt_clean;
    private ImageView marketSeatch_img;
    private ImageView mappin;
    int[] market_img = {R.drawable.market2, R.drawable.market3, R.drawable.market4, R.drawable.market5, R.drawable.market6,
            R.drawable.market2, R.drawable.market3, R.drawable.market4, R.drawable.market5, R.drawable.market6};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        init();

        map_title.setText("온누리 시장위치");
        market_layout.setVisibility(View.GONE);
        map_location.setVisibility(View.GONE);
        ImageView map_back = (ImageView) findViewById(R.id.map_back);

        map_back.setImageResource(R.drawable.back);
        map_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(this).load(R.drawable.mappin).thumbnail(0.1f).into(mappin);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void init(){


        OnnuriDB onnuriDB = App.getOnnuriDB();
        Location location=App.getGoogleApiHelper().locationUpdate(getApplicationContext());
        list = (ArrayList<MarketMarker>) onnuriDB.selectOnMap(location.getLatitude(), location.getLongitude());

        market_layout = (LinearLayout) findViewById(R.id.market_layout);
        map_title = (TextView) findViewById(R.id.map_title);
        map_location = (TextView) findViewById(R.id.map_location);
        marketName = (TextView) findViewById(R.id.marketName);
        marketAddr = (TextView) findViewById(R.id.marketAddr);
        marketGps = (TextView) findViewById(R.id.marketGps);

        marketSeatch_img =(ImageView) findViewById(R.id.marketSeatch_img);
        mappin =(ImageView) findViewById(R.id.mappin);
        marketsearch_txt_food =(TextView)findViewById(R.id.marketsearch_txt_food);
        marketsearch_txt_cow =(TextView)findViewById(R.id.marketsearch_txt_cow);
        marketsearch_txt_clothes =(TextView)findViewById(R.id.marketsearch_txt_clothes);
        marketsearch_txt_shoes =(TextView)findViewById(R.id.marketsearch_txt_shoes);
        marketsearch_txt_fish =(TextView)findViewById(R.id.marketsearch_txt_fish);
        marketsearch_txt_rice =(TextView)findViewById(R.id.marketsearch_txt_rice);
        marketsearch_txt_msg =(TextView)findViewById(R.id.marketsearch_txt_msg);
        marketsearch_txt_appliances =(TextView)findViewById(R.id.marketsearch_txt_appliances);
        marketsearch_txt_retail =(TextView)findViewById(R.id.marketsearch_txt_retail);
        marketsearch_txt_clean =(TextView)findViewById(R.id.marketsearch_txt_clean);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        map = googleMap;
        map.setOnMapClickListener(this);
        map.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mClusterManager = new ClusterManager<MarketMarker>(getApplicationContext(), map);
        mClusterManager.setRenderer(new CustomClusterRender(this, map, mClusterManager));

        map.setMyLocationEnabled(true);
        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        Location location = App.getGoogleApiHelper().locationUpdate(this);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
        getMarkerItems();

        mClusterManager.setOnClusterItemClickListener(this);
    }

    private void getMarkerItems() {
        for (int i = 0; i < list.size(); i++) {
            MarketMarker marketMarker= new MarketMarker(list.get(i).getDistance(), list.get(i).getAddress(), list.get(i).getCategory(), list.get(i).getCount(), list.get(i).getLoc_longi(), list.get(i).getLoc_lati(), list.get(i).getName(), list.get(i).getMarket_id());
            mClusterManager.addItem(marketMarker);
        }
    }

    private void changeSelectedMarker(MarketMarker marketMarker){

    }

    private void showStoreLayout(final MarketMarker marketMarker) {
        market_layout.setVisibility(View.VISIBLE);
        marketName.setText(marketMarker.getName());
        marketAddr.setText(marketMarker.getAddress());
        marketGps.setText(marketMarker.getDistance()+"Km");

        if (marketMarker.getCategory()[0] == 0) {
            marketsearch_txt_food.setSelected(true);
        }else{
            marketsearch_txt_food.setSelected(false);
        }
        if (marketMarker.getCategory()[1] == 1) {
            marketsearch_txt_cow.setSelected(true);
        }else{
            marketsearch_txt_cow.setSelected(false);
        }
        if (marketMarker.getCategory()[2] == 2) {
            marketsearch_txt_clothes.setSelected(true);
        }else{
            marketsearch_txt_clothes.setSelected(false);
        }
        if (marketMarker.getCategory()[3] == 3) {
            marketsearch_txt_shoes.setSelected(true);
        }else{
            marketsearch_txt_shoes.setSelected(false);
        }
        if (marketMarker.getCategory()[4] == 4) {
            marketsearch_txt_fish.setSelected(true);
        }else{
            marketsearch_txt_fish.setSelected(false);
        }
        if (marketMarker.getCategory()[5] == 5) {
            marketsearch_txt_rice.setSelected(true);
        }else{
            marketsearch_txt_rice.setSelected(false);
        }
        if (marketMarker.getCategory()[6] == 6) {
            marketsearch_txt_msg.setSelected(true);
        }else{
            marketsearch_txt_msg.setSelected(false);
        }
        if (marketMarker.getCategory()[7] == 7) {
            marketsearch_txt_appliances.setSelected(true);
        }else{
            marketsearch_txt_appliances.setSelected(false);
        }
        if (marketMarker.getCategory()[8] == 8) {
            marketsearch_txt_retail.setSelected(true);
        }else{
            marketsearch_txt_retail.setSelected(false);
        }
        if (marketMarker.getCategory()[9] == 9) {
            marketsearch_txt_clean.setSelected(true);
        }else{
            marketsearch_txt_clean.setSelected(false);
        }

        market_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreSearchActivity.class);
                intent.putExtra("from", "main");
                intent.putExtra("market_id", marketMarker.getMarket_id());
                intent.putExtra("market_name", marketMarker.getName());
                startActivity(intent);
            }
        });

        int arr = (int)(Math.random() * 10);
        Glide.with(this).load(market_img[arr]).thumbnail(01.f).fitCenter().into(marketSeatch_img);

        marketSeatch_img.setColorFilter( 0x55000000);
    }

    @Override
    public boolean onClusterItemClick(final ClusterItem clusterItem) {
        changeSelectedMarker((MarketMarker) clusterItem);
        showStoreLayout((MarketMarker) clusterItem);

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
        market_layout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.clear();
    }
}