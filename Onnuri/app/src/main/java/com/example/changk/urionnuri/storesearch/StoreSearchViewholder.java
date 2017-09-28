package com.example.changk.urionnuri.storesearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-05.
 */

public class StoreSearchViewholder extends RecyclerView.ViewHolder {
    protected ImageView storeImg, gps, phone;
    protected TextView storeName;
    protected TextView storeAddr;
    protected TextView storeMenu;
    protected TextView storeTel;
    protected LinearLayout linear_item;
    protected TextView storeGps;

    public StoreSearchViewholder(View view) {
        super(view);
        storeImg=(ImageView)view.findViewById(R.id.storeImg);
        gps=(ImageView)view.findViewById(R.id.gps);
        phone=(ImageView)view.findViewById(R.id.phone);
        storeName = (TextView)view.findViewById(R.id.storeName);
        storeAddr =(TextView)view.findViewById(R.id.storeAddr);
        storeMenu =(TextView)view.findViewById(R.id.storeMenu);
        storeTel = (TextView)view.findViewById(R.id.storeTel);
        linear_item = (LinearLayout)view.findViewById(R.id.linear_item);
        storeGps =(TextView)view.findViewById(R.id.storeGps);
    }
}
