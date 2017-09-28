package com.example.changk.urionnuri.marketfaq;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-20.
 */

public class MarketFaqAdapter extends RecyclerView.Adapter<MarketFaqViewholder> {
    Context context;
    String[] faq_title;
    String[] faq_content;

    public MarketFaqAdapter(Context context,String[] faq_title, String[] faq_content) {
        this.context =context;
        this.faq_title = faq_title;
        this.faq_content = faq_content;
    }

    @Override
    public MarketFaqViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketfaq_item, null);

        MarketFaqViewholder marketFaqViewholder = new MarketFaqViewholder(view);
        return marketFaqViewholder;
    }

    @Override
    public void onBindViewHolder(final MarketFaqViewholder holder, final int position) {
        holder.marketfaq_title.setText(faq_title[position]);
        holder.marketfaq_content.setText(faq_content[position]);

        holder.marketfaq_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.marketfaq_title.isSelected()){
                    holder.marketfaq_contentlayout.setVisibility(View.VISIBLE);
                    holder.marketfaq_title.setSelected(true);
                    Log.d(getClass().getName(), "selected="+holder.marketfaq_title.isSelected());
                }else{
                    holder.marketfaq_contentlayout.setVisibility(View.GONE);
                    holder.marketfaq_title.setSelected(false);
                    Log.d(getClass().getName(), "selected="+holder.marketfaq_title.isSelected());
                }
            }
        });

        holder.marketfaq_more_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MarketFaqDetailActivity.class);
                intent.putExtra("faqtitle",faq_title[position]);
                intent.putExtra("faqcontent",faq_content[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faq_title.length;
    }
}
