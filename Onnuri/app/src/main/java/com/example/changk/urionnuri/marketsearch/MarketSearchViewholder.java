package com.example.changk.urionnuri.marketsearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-05.
 */

public class MarketSearchViewholder extends RecyclerView.ViewHolder {
    protected ImageView marketSearchImg, mappin;
    protected TextView marketName;
    protected TextView marketAddr;
    protected LinearLayout marketLayout;
    protected TextView storeNum;
    protected TextView txtFood;
    protected TextView marketsearch_txt_food;
    protected TextView marketsearch_txt_cow;
    protected TextView marketsearch_txt_clothes;
    protected TextView marketsearch_txt_shoes;
    protected TextView marketsearch_txt_fish;
    protected TextView marketsearch_txt_rice;
    protected TextView marketsearch_txt_msg;
    protected TextView marketsearch_txt_appliances;
    protected TextView marketsearch_txt_retail;
    protected TextView marketsearch_txt_clean;
    protected TextView distance;

    public MarketSearchViewholder(View view) {
        super(view);
        marketSearchImg = (ImageView)view.findViewById(R.id.marketSearchImg);
        mappin=(ImageView)view.findViewById(R.id.mappin);
        marketAddr = (TextView)view.findViewById(R.id.marketAddr);
        marketName = (TextView)view.findViewById(R.id.marketName);
        marketLayout = (LinearLayout)view.findViewById(R.id.marketLayout);
        storeNum = (TextView)view.findViewById(R.id.storeNum);
        txtFood=(TextView)view.findViewById(R.id.txt_food);
        marketsearch_txt_food =(TextView)view.findViewById(R.id.marketsearch_txt_food);
        marketsearch_txt_cow =(TextView)view.findViewById(R.id.marketsearch_txt_cow);
        marketsearch_txt_clothes =(TextView)view.findViewById(R.id.marketsearch_txt_clothes);
        marketsearch_txt_shoes =(TextView)view.findViewById(R.id.marketsearch_txt_shoes);
        marketsearch_txt_fish =(TextView)view.findViewById(R.id.marketsearch_txt_fish);
        marketsearch_txt_rice =(TextView)view.findViewById(R.id.marketsearch_txt_rice);
        marketsearch_txt_msg =(TextView)view.findViewById(R.id.marketsearch_txt_msg);
        marketsearch_txt_appliances =(TextView)view.findViewById(R.id.marketsearch_txt_appliances);
        marketsearch_txt_retail =(TextView)view.findViewById(R.id.marketsearch_txt_retail);
        marketsearch_txt_clean =(TextView)view.findViewById(R.id.marketsearch_txt_clean);
        distance =(TextView)view.findViewById(R.id.distance);
    }
}
