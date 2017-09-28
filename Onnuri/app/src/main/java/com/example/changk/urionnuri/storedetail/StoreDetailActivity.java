package com.example.changk.urionnuri.storedetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.main.MainActivity;
import com.example.changk.urionnuri.model.Market;
import com.example.changk.urionnuri.model.Store;
import com.example.changk.urionnuri.onnuridb.OnnuriDB;

import java.util.List;

/**
 * Created by Changk on 2016-10-17.
 */

public class StoreDetailActivity extends BaseActivity {
    private TextView txt_storeName;
    private TextView storeTel;
    private TextView storeName;
    private TextView storeCategory;
    private TextView marketName;
    private TextView storeLocation;
    private TextView call_txt;
    private TextView map_txt;
    private Store store;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storedetail_layout);

        init();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.8),(int)(height*.5));
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");

        txt_storeName.setText(store.getName());

        storeLocation.setText(store.getAddress());
        String[] category = intent.getStringArrayExtra("category");

        storeCategory.setText(category[store.getCategory()]);
        marketName.setText(store.getMarket_name());

        if(store.getTel().equals("-")){
            storeTel.setText("없음");
        }else {
            storeTel.setText(store.getTel());
        }

        ImageView storePic=(ImageView)findViewById(R.id.storePic);
        Glide.with(getApplicationContext()).load(R.drawable.placholder).fitCenter().thumbnail(01.f).into(storePic);
        storePic.setColorFilter( 0x55000000);

        ImageView info=(ImageView)findViewById(R.id.info);
        Glide.with(getApplicationContext()).load(R.drawable.info).thumbnail(0.1f).into(info);

        map_txt.setOnClickListener(onClickListener);
        call_txt.setOnClickListener(onClickListener);

    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.map_txt) {
                Intent intent = new Intent(StoreDetailActivity.this, MapActivity.class);
                intent.putExtra("store", store);
                startActivity(intent);
            }
            if(v.getId()==R.id.call_txt){
                if(store.getTel().equals("-")){
                    Toast.makeText(StoreDetailActivity.this, "전화번호가 없습니다.", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+store.getTel())));
                }
            }
        }
    };

    public void init() {
        call_txt = (TextView) findViewById(R.id.call_txt);
        map_txt = (TextView) findViewById(R.id.map_txt);
        txt_storeName = (TextView) findViewById(R.id.txt_storeName);
        storeLocation = (TextView) findViewById(R.id.storeLocation);
        storeName = (TextView) findViewById(R.id.storeName);
        storeCategory = (TextView) findViewById(R.id.storeCategory);
        marketName = (TextView) findViewById(R.id.marketName);
        storeTel = (TextView) findViewById(R.id.storeTel);
    }
}
