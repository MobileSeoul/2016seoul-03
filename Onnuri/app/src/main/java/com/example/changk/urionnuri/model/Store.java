package com.example.changk.urionnuri.model;

import java.io.Serializable;

/**
 * Created by Changk on 2016-10-05.
 */

public class Store implements Serializable {
    private int store_id;
    private String name;
    private double loc_lati;
    private double loc_longi;
    private int category;
    private int market_id;
    private String address;
    private String tel;
    private String market_name;
    private double distance;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMarket_id() {
        return market_id;
    }

    public void setMarket_id(int market_id) {
        this.market_id = market_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
