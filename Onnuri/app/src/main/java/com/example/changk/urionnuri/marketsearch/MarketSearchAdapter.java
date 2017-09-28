package com.example.changk.urionnuri.marketsearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.Market;
import com.example.changk.urionnuri.storesearch.StoreSearchActivity;

import java.util.List;

/**
 * Created by Changk on 2016-10-05.
 */

public class MarketSearchAdapter extends RecyclerView.Adapter<MarketSearchViewholder> {
    List<Market> list;
    Context context;
    int[] market_img = {R.drawable.market2, R.drawable.market3, R.drawable.market4, R.drawable.market5, R.drawable.market6,
            R.drawable.market2, R.drawable.market3, R.drawable.market4, R.drawable.market5, R.drawable.market6};

    public MarketSearchAdapter(Context context, List<Market> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MarketSearchViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketsearch_item, null);
        MarketSearchViewholder marketSearchViewholder = new MarketSearchViewholder(view);
        return marketSearchViewholder;
    }

    @Override
    public void onBindViewHolder(MarketSearchViewholder holder, final int position) {
        int arr = (int)(Math.random() * 10);
        Glide.with(context).load(market_img[arr]).thumbnail(01.f).fitCenter().into(holder.marketSearchImg);
        Glide.with(context).load(R.drawable.mappin).thumbnail(0.1f).into(holder.mappin);

        holder.marketSearchImg.setColorFilter( 0x55000000);
        holder.marketName.setText(list.get(position).getName());
        holder.marketAddr.setText(list.get(position).getAddress());
        holder.storeNum.setText(list.get(position).getCount() + "");

        /*float distance = calculateDistance(MainActivity.lati, MainActivity.longi, list.get(position).getLoc_lati(), list.get(position).getLoc_longi());
        holder.distance.setText(distance+"km");*/
        holder.distance.setText(list.get(position).getDistance()+"Km");

        if (list.get(position).getCategory()[0] == 0) {
            holder.marketsearch_txt_food.setSelected(true);
        }else{
            holder.marketsearch_txt_food.setSelected(false);
        }
        if (list.get(position).getCategory()[1] == 1) {
            holder.marketsearch_txt_cow.setSelected(true);
        }else{
            holder.marketsearch_txt_cow.setSelected(false);
        }
        if (list.get(position).getCategory()[2] == 2) {
            holder.marketsearch_txt_clothes.setSelected(true);
        }else{
            holder.marketsearch_txt_clothes.setSelected(false);
        }
        if (list.get(position).getCategory()[3] == 3) {
            holder.marketsearch_txt_shoes.setSelected(true);
        }else{
            holder.marketsearch_txt_shoes.setSelected(false);
        }
        if (list.get(position).getCategory()[4] == 4) {
            holder.marketsearch_txt_fish.setSelected(true);
        }else{
            holder.marketsearch_txt_fish.setSelected(false);
        }
        if (list.get(position).getCategory()[5] == 5) {
            holder.marketsearch_txt_rice.setSelected(true);
        }else{
            holder.marketsearch_txt_rice.setSelected(false);
        }
        if (list.get(position).getCategory()[6] == 6) {
            holder.marketsearch_txt_msg.setSelected(true);
        }else{
            holder.marketsearch_txt_msg.setSelected(false);
        }
        if (list.get(position).getCategory()[7] == 7) {
            holder.marketsearch_txt_appliances.setSelected(true);
        }else{
            holder.marketsearch_txt_appliances.setSelected(false);
        }
        if (list.get(position).getCategory()[8] == 8) {
            holder.marketsearch_txt_retail.setSelected(true);
        }else{
            holder.marketsearch_txt_retail.setSelected(false);
        }
        if (list.get(position).getCategory()[9] == 9) {
            holder.marketsearch_txt_clean.setSelected(true);
        }else{
            holder.marketsearch_txt_clean.setSelected(false);
        }

        holder.marketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoreSearchActivity.class);
                intent.putExtra("market", list.get(position));
                intent.putExtra("from", "marketsearch");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
