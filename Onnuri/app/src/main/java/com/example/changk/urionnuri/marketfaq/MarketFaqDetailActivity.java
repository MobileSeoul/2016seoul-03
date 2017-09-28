package com.example.changk.urionnuri.marketfaq;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-20.
 */

public class MarketFaqDetailActivity extends BaseActivity {

    private TextView faqdetail_title;
    private TextView faqdetail_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketfaqdetail_layout);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        faqdetail_title =(TextView)findViewById(R.id.faqdetail_title);
        faqdetail_content =(TextView)findViewById(R.id.faqdetail_content);

        Intent intent = getIntent();
        String faq_title = intent.getStringExtra("faqtitle");
        String faq_content = intent.getStringExtra("faqcontent");

        faqdetail_title.setText(faq_title);
        faqdetail_content.setText(faq_content);

    }
}
