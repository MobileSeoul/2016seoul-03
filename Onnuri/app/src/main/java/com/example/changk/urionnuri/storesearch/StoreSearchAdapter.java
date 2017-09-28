package com.example.changk.urionnuri.storesearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.Store;
import com.example.changk.urionnuri.storedetail.StoreDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Changk on 2016-10-05.
 */

public class StoreSearchAdapter extends RecyclerView.Adapter<StoreSearchViewholder>{
    List<Store> storeList=new ArrayList<Store>();
    Context context;
    String[] category = {"음식","축산","의류","신발","수산","농산","가공식품","가정용품","기타소매업","근린생활서비스"};

    public StoreSearchAdapter(Context context, List<Store> storeList) {
        this.context=context;
        this.storeList = storeList;
    }

    @Override
    public StoreSearchViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storesearch_item,null);
        StoreSearchViewholder storesearchViewholder = new StoreSearchViewholder(view);
        return storesearchViewholder;
    }

    @Override
    public void onBindViewHolder(StoreSearchViewholder holder, final int position) {
        Glide.with(context.getApplicationContext()).load(R.drawable.placholder).thumbnail(0.1f).into(holder.storeImg);
        Glide.with(context.getApplicationContext()).load(R.drawable.gps).thumbnail(0.1f).into(holder.gps);
        Glide.with(context.getApplicationContext()).load(R.drawable.phone).thumbnail(0.1f).into(holder.phone);
        holder.storeName.setText(storeList.get(position).getName());
        holder.storeAddr.setText(storeList.get(position).getAddress());
        holder.storeMenu.setText(category[storeList.get(position).getCategory()]);
        if(storeList.get(position).getTel().equals("-")){
            holder.storeTel.setText("없음");
        }else {
            holder.storeTel.setText(storeList.get(position).getTel());
        }
        holder.storeGps.setText(storeList.get(position).getDistance()+"Km");
        holder.linear_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreDetailActivity.class);
                intent.putExtra("store", storeList.get(position));
                intent.putExtra("category",category);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
