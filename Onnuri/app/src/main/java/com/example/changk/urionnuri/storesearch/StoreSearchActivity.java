package com.example.changk.urionnuri.storesearch;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.App;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.main.MainActivity;
import com.example.changk.urionnuri.model.Market;
import com.example.changk.urionnuri.model.Store;
import com.example.changk.urionnuri.onnuridb.OnnuriDB;
import com.example.changk.urionnuri.storedetail.StoreDetailActivity;

import java.util.List;

/**
 * Created by Changk on 2016-09-26.
 */

public class StoreSearchActivity extends BaseActivity {
    private TextView SearchToolbar_title;
    private RecyclerView storeSearchRecyclerView;
    private RecyclerView.LayoutManager sLayoutManager;
    private RecyclerView.Adapter storeSearchAdapter;
    private List<Store> storeList;
    private TextView totalStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesearch_layout);

        OnnuriDB onnuriDB = App.getOnnuriDB();
        Location location = App.getGoogleApiHelper().locationUpdate(this);

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");

        SearchToolbar_title=(TextView)findViewById(R.id.SearchToolbar_title);
        storeSearchRecyclerView = (RecyclerView)findViewById(R.id.storeSearchRecyclerView);
        sLayoutManager = new LinearLayoutManager(this);
        storeSearchRecyclerView.setLayoutManager(sLayoutManager);

        if(from.equals("marketsearch")) {
            Market market = (Market) intent.getSerializableExtra("market");
            Log.d(getClass().getName(), "id="+market.getMarket_id());
            storeList = onnuriDB.storeList(market.getMarket_id(), location.getLatitude(), location.getLongitude());
            SearchToolbar_title.setText(market.getName());
        }else if(from.equals("storesearch")) {
            int[] arr=intent.getIntArrayExtra("arr");
            double distance=intent.getDoubleExtra("distance", 0);
            storeList = onnuriDB.storeSelect(arr, distance, location.getLatitude(), location.getLongitude());
        }if(from.equals("main")){
            int market_id=intent.getIntExtra("market_id",0);
            String market_name = intent.getStringExtra("market_name");
            SearchToolbar_title.setText(market_name);
            storeList=onnuriDB.storeList(market_id,location.getLatitude(),location.getLongitude());
        }


        storeSearchAdapter = new StoreSearchAdapter(this, storeList);
        storeSearchRecyclerView.setAdapter(storeSearchAdapter);

        ImageView storesearch_back=(ImageView)findViewById(R.id.storesearch_back);
        Glide.with(this).load(R.drawable.back).thumbnail(0.1f).into(storesearch_back);
        storesearch_back.setOnClickListener(onClickListener);

//       linear_item.setOnClickListener(onClickListener);
        totalStore = (TextView)findViewById(R.id.totalStore);
        totalStore.setText("총 "+ storeList.size()+"개의 매장");

        Toolbar toolbar = (Toolbar) findViewById(R.id.storeSearch_toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.linear_item) {
                Intent intent = new Intent(StoreSearchActivity.this, StoreDetailActivity.class);
                startActivity(intent);
            }
            if (v.getId()== R.id.storesearch_back){
                finish();
            }

        }
    };
}