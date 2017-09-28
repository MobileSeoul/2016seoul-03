package com.example.changk.urionnuri.mapsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.model.MarketMarker;
import com.example.changk.urionnuri.storesearch.StoreSearchActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Changk on 2016-10-26.
 */

public class CustomClusterRender extends DefaultClusterRenderer<MarketMarker> {
    private View marker_root_view;
    private TextView marker_txt;
    private Context context;

    public CustomClusterRender(Context context, GoogleMap map, ClusterManager<MarketMarker> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(final MarketMarker item, MarkerOptions markerOptions) {
        marker_root_view = LayoutInflater.from(context).inflate(R.layout.marker_layout, null);
        marker_txt = (TextView) marker_root_view.findViewById(R.id.map_marker);

        marker_txt.setText(item.getName());
        markerOptions.position(item.getPosition());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_root_view)));

        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}
