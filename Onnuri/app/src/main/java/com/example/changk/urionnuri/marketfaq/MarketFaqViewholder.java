package com.example.changk.urionnuri.marketfaq;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-20.
 */

public class MarketFaqViewholder extends RecyclerView.ViewHolder{

    protected TextView marketfaq_title;
    protected TextView marketfaq_content;
    protected LinearLayout marketfaq_contentlayout;
    protected TextView marketfaq_more_txt;

    public MarketFaqViewholder(View view) {
        super(view);

        marketfaq_title =(TextView)view.findViewById(R.id.marketfaq_title);
        marketfaq_content =(TextView)view.findViewById(R.id.marketfaq_content);
        marketfaq_contentlayout =(LinearLayout)view.findViewById(R.id.marketfaq_contentlayout);
        marketfaq_more_txt = (TextView)view.findViewById(R.id.marketfaq_more_txt);

        marketfaq_title.setSelected(false);

    }
}
