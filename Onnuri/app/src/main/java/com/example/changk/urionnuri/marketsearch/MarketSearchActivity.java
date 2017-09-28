package com.example.changk.urionnuri.marketsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.App;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.Market;
import com.example.changk.urionnuri.onnuridb.OnnuriDB;

import java.util.List;

/**
 * Created by Changk on 2016-09-26.
 */

public class MarketSearchActivity extends BaseActivity {
    private RecyclerView marketSearchRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter marketSearchAdapter;
    private TextView totalMarket;
    private EditText marketSearch_editTxt;
    List<Market> list;
    List<Market> newList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketsearch_layout);

        Intent intent =getIntent();
        double lati = intent.getDoubleExtra("lati", 0);
        double longi = intent.getDoubleExtra("longi", 0);

        OnnuriDB onnuriDB = App.getOnnuriDB();
        list = onnuriDB.select(lati, longi);
        newList = onnuriDB.select(lati, longi);

        marketSearchRecyclerView = (RecyclerView) findViewById(R.id.marketSearchRecyclerView);
        marketSearch_editTxt = (EditText) findViewById(R.id.marketSearch_editTxt);
        mLayoutManager = new LinearLayoutManager(this);
        marketSearchRecyclerView.setLayoutManager(mLayoutManager);
        marketSearchAdapter = new MarketSearchAdapter(this, list);
        marketSearchRecyclerView.setAdapter(marketSearchAdapter);

        ImageView marketsearch_back =(ImageView)findViewById(R.id.marketsearch_back);
        Glide.with(this).load(R.drawable.back).thumbnail(0.1f).into(marketsearch_back);

        marketsearch_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        totalMarket = (TextView) findViewById(R.id.totalMarket);
        totalMarket.setText("총 " + list.size() + "개의 시장");

        marketSearch_editTxt.addTextChangedListener(searchWatcher);
    }

    private final TextWatcher searchWatcher = new TextWatcher() {
        //검색을 하기전의 상태
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        //바뀌고 있는 순간의 처리, 리스트를 두개만들어서 바뀌는순간 검색된 데이타를 새로운 리스트에 덮어서
        //바로 검색이 되는것처럼 처리
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 0) {//텍스트가 다 지워질때는 전체 목록을 보여준다
                marketSearchRecyclerView.setAdapter(marketSearchAdapter);
                marketSearchAdapter.notifyDataSetChanged();
            } else {
                newList.clear();
                for (int j = 0; j < list.size(); j++) {
                    String searchData = list.get(j).getName();
                    String keyWord = charSequence.toString();
                    boolean isData = SoundSearcher.matchString(searchData, keyWord);//검색할 대상 , 검색 키워드로  검색키워드가 검색대상에 있으면  true를 리턴해준다
                    if (isData) {
                        newList.add(list.get(j));//검색대상에 있으면 새로운 리스트를 만들어서 이름을 애드해준다
                    }
                }
            }
            marketSearchAdapter = new MarketSearchAdapter(MarketSearchActivity.this, newList);
            marketSearchRecyclerView.setAdapter(marketSearchAdapter);
            marketSearchAdapter.notifyDataSetChanged();
        }
        //터치를 끝낸순간의 처리, 원래의 list를 덮어서 검색의 전으로 돌아감.
        @Override
        public void afterTextChanged(Editable editable) {
            for(int i = editable.length()-1; i > -1; i--){
                if(editable.charAt(i) == '\n'){
                    editable.delete(i, i + 1);
                    return;
                }
            }
            if (editable.toString().length() == 0) {//텍스트가 다 지워질때는 전체 목록을 보여준다
                marketSearchAdapter = new MarketSearchAdapter(MarketSearchActivity.this, list);
                marketSearchRecyclerView.setAdapter(marketSearchAdapter);
                marketSearchAdapter.notifyDataSetChanged();
            }
        }
    };
}


