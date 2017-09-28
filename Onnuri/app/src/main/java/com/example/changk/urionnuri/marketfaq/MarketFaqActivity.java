package com.example.changk.urionnuri.marketfaq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.onnuridb.OnnuriDB;

import java.util.List;

/**
 * Created by Changk on 2016-10-19.
 */

public class MarketFaqActivity extends BaseActivity {
    private RecyclerView marketFaqRecylerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter marketFaqAdapter;
    private ImageView marketfaq_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketfaq_layout);

        init();
        marketFaqRecylerView = (RecyclerView) findViewById(R.id.marketFaq_RecyclerView);

        String[] faq_title=getResources().getStringArray(R.array.faq_title);
        String[] faq_content=getResources().getStringArray(R.array.faq_content);

        mLayoutManager = new LinearLayoutManager(this);
        marketFaqRecylerView.setLayoutManager(mLayoutManager);
        marketFaqAdapter = new MarketFaqAdapter(this,faq_title,faq_content);
        marketFaqRecylerView.setAdapter(marketFaqAdapter);
        Glide.with(getApplicationContext()).load(R.drawable.back).thumbnail(0.1f).into(marketfaq_back);
        marketfaq_back.setOnClickListener(onClickListener);
    }

    public void init(){
        marketfaq_back = (ImageView)findViewById(R.id.marketfaq_back);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.marketfaq_back){
                finish();
            }
        }
    };
}
