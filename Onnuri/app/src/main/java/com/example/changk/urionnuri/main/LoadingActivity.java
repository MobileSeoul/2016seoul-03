package com.example.changk.urionnuri.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;

/**
 * Created by Changk on 2016-10-24.
 */

public class LoadingActivity extends BaseActivity {
    /** 로딩 화면이 떠있는 시간(밀리초단위)  **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.loading_layout);

        ImageView loading=(ImageView)findViewById(R.id.loading);
        Glide.with(this)
                .load(R.drawable.loading)
                .centerCrop()
                .dontAnimate()
                .into(loading);

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                Intent mainIntent = new Intent(LoadingActivity.this,MainActivity.class);
                LoadingActivity.this.startActivity(mainIntent);
                LoadingActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
