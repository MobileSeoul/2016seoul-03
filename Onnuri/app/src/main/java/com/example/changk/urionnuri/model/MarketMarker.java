package com.example.changk.urionnuri.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Changk on 2016-10-26.
 */

public class MarketMarker implements ClusterItem {
    private int market_id;
    private String name;
    private double loc_lati;
    private double loc_longi;
    private int count;
    private String address;
    private int category[];
    private double distance;
    private final LatLng mPosition;

    public MarketMarker (double loc_lati, double loc_longi){
        mPosition=new LatLng(loc_lati, loc_longi);
    }

    public MarketMarker(double distance, String address, int category[], int count, double loc_longi, double loc_lati, String name, int market_id) {
        mPosition=new LatLng(loc_lati, loc_longi);
        this.distance = distance;
        this.address = address;
        this.category=category;
        this.count = count;
        this.loc_longi = loc_longi;
        this.loc_lati = loc_lati;
        this.name = name;
        this.market_id = market_id;
    }

    public int getMarket_id() {
        return market_id;
    }

    public void setMarket_id(int market_id) {
        this.market_id = market_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLoc_lati() {
        return loc_lati;
    }

    public void setLoc_lati(double loc_lati) {
        this.loc_lati = loc_lati;
    }

    public double getLoc_longi() {
        return loc_longi;
    }

    public void setLoc_longi(double loc_longi) {
        this.loc_longi = loc_longi;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int[] getCategory() {
        return category;
    }

    public void setCategory(int[] category) {
        this.category = category;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
