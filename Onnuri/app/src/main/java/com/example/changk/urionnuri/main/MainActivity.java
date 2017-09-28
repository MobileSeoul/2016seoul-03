package com.example.changk.urionnuri.main;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.changk.urionnuri.App;
import com.example.changk.urionnuri.BaseActivity;
import com.example.changk.urionnuri.R;
import com.example.changk.urionnuri.mapsearch.MapSearchActivity;
import com.example.changk.urionnuri.marketfaq.MarketFaqActivity;
import com.example.changk.urionnuri.marketsearch.MarketSearchActivity;
import com.example.changk.urionnuri.onnurigoogleapi.GoogleApiHelper;
import com.example.changk.urionnuri.storesearch.StoreSearchActivity;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener,ResultCallback<LocationSettingsResult>, LocationListener {
    private SliderLayout mDemoSlider;
    private LinearLayout linear_refresh;

    private CardView searchOnnuriMarket;
    private CardView searchMarket;
    private CardView onnuriMap;
    private CardView onnuriFaq;

    private TextView gpsText;
    private TextView txt_storesearch;
    private TextView txt_close;

    private TextView txt_food;
    private TextView txt_cow;
    private TextView txt_clothes;
    private TextView txt_shoes;
    private TextView txt_fish;
    private TextView txt_rice;
    private TextView txt_msg;
    private TextView txt_appliances;
    private TextView txt_retail;
    private TextView txt_clean;
    private ImageView onnuri_refresh;
    private SeekBar seekBar;
    private SlidingUpPanelLayout storeSearchPanel;

    private ImageView upButton;
    private FrameLayout frameLayout;

    private GoogleApiHelper googleApiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();

        //테이블 생성
        googleApiHelper = App.getGoogleApiHelper();

        //마시멜로우 버전부터 권한이 주어졌는지 확인하는 메소드
        checkPermission();

        //마시멜로우 이전 버전에서 gps가 켜져 있는지 확인하는 메소드
        googleApiHelper.checkLocationSettings(this);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("마포시장", R.drawable.mapo);
        file_maps.put("사러가시장", R.drawable.saruga);
        file_maps.put("목3동시장", R.drawable.mokdong);
        file_maps.put("신영시장", R.drawable.sinyoung);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    private void init() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        linear_refresh = (LinearLayout) findViewById(R.id.linear_refresh);

        searchOnnuriMarket = (CardView) findViewById(R.id.searchOnnuriMarket);
        searchMarket = (CardView) findViewById(R.id.searchMarket);
        onnuriMap = (CardView) findViewById(R.id.onnuriMap);
        onnuriFaq = (CardView) findViewById(R.id.onnuriFaq);

        gpsText = (TextView) findViewById(R.id.gpsText);
        txt_storesearch = (TextView) findViewById((R.id.txt_storesearch));//검색버튼
        txt_close = (TextView) findViewById(R.id.txt_close);

        txt_food = (TextView) findViewById(R.id.txt_food);
        txt_cow = (TextView) findViewById(R.id.txt_cow);
        txt_clothes = (TextView) findViewById(R.id.txt_clothes);
        txt_shoes = (TextView) findViewById(R.id.txt_shoes);
        txt_fish = (TextView) findViewById(R.id.txt_fish);
        txt_rice = (TextView) findViewById(R.id.txt_rice);
        txt_msg = (TextView) findViewById(R.id.txt_msg);
        txt_appliances = (TextView) findViewById(R.id.txt_appliances);
        txt_retail = (TextView) findViewById(R.id.txt_retail);
        txt_clean = (TextView) findViewById(R.id.txt_clean);
        onnuri_refresh =(ImageView)findViewById(R.id.onnuri_refresh);
        Glide.with(getApplicationContext()).load(R.drawable.onnuri_refresh).thumbnail(0.1f).into(onnuri_refresh);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        storeSearchPanel = (SlidingUpPanelLayout) findViewById(R.id.storeSearchPanel);
        upButton = (ImageView) findViewById(R.id.upButton);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        ImageView gps=(ImageView)findViewById(R.id.gps);
        Glide.with(this).load(R.drawable.gps).thumbnail(0.1f).into(gps);

        ImageView onnuri_search=(ImageView)findViewById(R.id.onnuri_search);
        Glide.with(this).load(R.drawable.onnuri_search).thumbnail(0.1f).into(onnuri_search);

        ImageView onnuri_market=(ImageView)findViewById(R.id.onnuri_market);
        Glide.with(this).load(R.drawable.onnuri_market).thumbnail(0.1f).into(onnuri_market);

        ImageView onnuri_map=(ImageView)findViewById(R.id.onnuri_map);
        Glide.with(this).load(R.drawable.onnuri_map).thumbnail(0.1f).into(onnuri_map);

        ImageView onnuri_faq=(ImageView)findViewById(R.id.onnuri_faq);
        Glide.with(this).load(R.drawable.onnuri_faq).thumbnail(0.1f).into(onnuri_faq);

        ImageView up=(ImageView)findViewById(R.id.up);
        Glide.with(this).load(R.drawable.up).thumbnail(0.1f).into(up);

        ImageView upButton=(ImageView)findViewById(R.id.upButton);
        Glide.with(this).load(R.drawable.up).thumbnail(0.1f).into(upButton);

        searchMarket.setOnClickListener(onClickListener);
        searchOnnuriMarket.setOnClickListener(onClickListener);
        onnuriFaq.setOnClickListener(onClickListener);
        onnuriMap.setOnClickListener(onClickListener);
        txt_food.setOnClickListener(onClickListener);
        txt_cow.setOnClickListener(onClickListener);
        txt_clothes.setOnClickListener(onClickListener);
        txt_shoes.setOnClickListener(onClickListener);
        txt_fish.setOnClickListener(onClickListener);
        txt_rice.setOnClickListener(onClickListener);
        txt_msg.setOnClickListener(onClickListener);
        txt_appliances.setOnClickListener(onClickListener);
        txt_retail.setOnClickListener(onClickListener);
        txt_clean.setOnClickListener(onClickListener);

        txt_close.setOnClickListener(onClickListener);
        txt_storesearch.setOnClickListener(onClickListener);
        linear_refresh.setOnClickListener(onClickListener);

        frameLayout.setOnClickListener(onClickListener);
        storeSearchPanel.setPanelSlideListener(panelSlideListener);
    }

    private void initPanel() {
        txt_food.setSelected(false);
        txt_cow.setSelected(false);
        txt_clothes.setSelected(false);
        txt_shoes.setSelected(false);
        txt_fish.setSelected(false);
        txt_rice.setSelected(false);
        txt_msg.setSelected(false);
        txt_appliances.setSelected(false);
        txt_retail.setSelected(false);
        txt_clean.setSelected(false);
        seekBar.setProgress(3);
    }

    private Intent sendCondition() {
        int arr[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        double distance = 0;
        int count = 0;
        if (txt_food.isSelected()) {
            arr[0] = 0;
            count++;
        }
        if (txt_cow.isSelected()) {
            arr[1] = 1;
            count++;
        }
        if (txt_clothes.isSelected()) {
            arr[2] = 2;
            count++;
        }
        if (txt_shoes.isSelected()) {
            arr[3] = 3;
            count++;
        }
        if (txt_fish.isSelected()) {
            arr[4] = 4;
            count++;
        }
        if (txt_rice.isSelected()) {
            arr[5] = 5;
            count++;
        }
        if (txt_msg.isSelected()) {
            arr[6] = 6;
            count++;
        }
        if (txt_appliances.isSelected()) {
            arr[7] = 7;
            count++;
        }
        if (txt_retail.isSelected()) {
            arr[8] = 8;
            count++;
        }
        if (txt_clean.isSelected()) {
            arr[9] = 9;
            count++;
        }

        switch (seekBar.getProgress()) {
            case 0:
                distance = 0.3;
                break;
            case 1:
                distance = 1;
                break;
            case 2:
                distance = 3;
                break;
            case 3:
                distance = 500;
                break;

        }

        Intent intent = new Intent();
        intent.putExtra("arr", arr);
        intent.putExtra("distance", distance);
        intent.putExtra("count", count);

        return intent;
    }

    // Android SKD 23버전 이상에서 위치에 대한 Permission 받아오는 메소드
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Explain to the user why we need to write the permission.

            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);

            return false;
            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        }else{
            if(checkGPSEnable()) {
                Location location = googleApiHelper.locationUpdate(MainActivity.this);
                if (location != null) {
                    changeAddress(location.getLatitude(), location.getLongitude());
                }

                return true;
            }else{
                return false;
            }
        }
    }

    private boolean checkGPSEnable(){
        if(((LocationManager)getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "GPS(위치)를 켜주셔야 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
            googleApiHelper.checkLocationSettings(MainActivity.this);

            return false;
        }
    }

    // LastLocation에서 받은 Lati, Longi 값을 한글화된 주소로 변환해주는 메소드
    private void changeAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(lati, longi, 5);
            gpsText.setText(addressList.get(0).getAdminArea() + " " + addressList.get(0).getLocality() + " " +
                    addressList.get(0).getThoroughfare() + " " + addressList.get(0).getFeatureName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (storeSearchPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            storeSearchPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    //LocationListener의 implements 메소드 requestLocationUpdates의 콜백 메소드
    @Override
    public void onLocationChanged(Location location) {
        changeAddress(location.getLatitude(), location.getLongitude());
    }

    //ResultCallback<LocationSettingsResult>의 implements 메소드
    //현재 gps status를 확인한다.
    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.d(getClass().getName(), "All location settings are satisfied.");
                checkPermission();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.d(getClass().getName(), "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(MainActivity.this, 100);
                } catch (IntentSender.SendIntentException e) {
                    Log.d(getClass().getName(), "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.d(getClass().getName(), "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    //이 액티비티에서 실행한 액티비티에서 값을 리턴받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        Log.d(getClass().getName(), "onActivitityResult");
        switch (requestCode) {
            case 100:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d(getClass().getName(), "ResultOK");
                        // All required changes were successfully made
                        final ProgressDialog progressDialog=ProgressDialog.show(MainActivity.this, "", "잠시만 기다려 주세요.", true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Location location = googleApiHelper.locationUpdate(MainActivity.this);
                                if(location!=null) {
                                    changeAddress(location.getLatitude(), location.getLongitude());
                                    progressDialog.dismiss();
                                }
                            }
                        }, 2000);

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d(getClass().getName(), "ResultCanceled");
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        Log.d(getClass().getName(), "defualt");
                        break;
                }
                break;
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.searchMarket) {
                if(checkPermission()) {
                    if (checkGPSEnable()) {
                        Intent intent = new Intent(MainActivity.this, MarketSearchActivity.class);
                        intent.putExtra("lati", googleApiHelper.locationUpdate(MainActivity.this).getLatitude());
                        intent.putExtra("longi", googleApiHelper.locationUpdate(MainActivity.this).getLongitude());
                        startActivity(intent);
                    }
                }
            }
            if (v.getId() == R.id.searchOnnuriMarket) {
                storeSearchPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
            if (v.getId() == R.id.onnuriFaq) {
                Intent intent = new Intent(MainActivity.this, MarketFaqActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.onnuriMap) {
                if(checkPermission()) {
                    if (checkGPSEnable()) {
                        Intent intent = new Intent(MainActivity.this, MapSearchActivity.class);
                        startActivity(intent);
                    }
                }
            }
            if (v.getId() == R.id.txt_food) {  //카드뷰안에 글씨색변경
                txt_food.setSelected(!txt_food.isSelected());
            }
            if (v.getId() == R.id.txt_cow) {
                txt_cow.setSelected(!txt_cow.isSelected());
            }
            if (v.getId() == R.id.txt_clothes) {
                txt_clothes.setSelected(!txt_clothes.isSelected());
            }
            if (v.getId() == R.id.txt_shoes) {
                txt_shoes.setSelected(!txt_shoes.isSelected());
            }
            if (v.getId() == R.id.txt_fish) {
                txt_fish.setSelected(!txt_fish.isSelected());
            }
            if (v.getId() == R.id.txt_rice) {
                txt_rice.setSelected(!txt_rice.isSelected());
            }
            if (v.getId() == R.id.txt_msg) {
                txt_msg.setSelected(!txt_msg.isSelected());
            }
            if (v.getId() == R.id.txt_appliances) {
                txt_appliances.setSelected(!txt_appliances.isSelected());
            }
            if (v.getId() == R.id.txt_retail) {
                txt_retail.setSelected(!txt_retail.isSelected());
            }
            if (v.getId() == R.id.txt_clean) {
                txt_clean.setSelected(!txt_clean.isSelected());
            }
            if (v.getId() == R.id.frameLayout) {
                storeSearchPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
            if (v.getId() == R.id.txt_storesearch) {
                if(checkPermission()) {
                    if (checkGPSEnable()) {
                        Intent tempIntent = sendCondition();
                        int count = tempIntent.getIntExtra("count", 0);

                        if (count != 0) {
                            Intent intent = new Intent(MainActivity.this, StoreSearchActivity.class);
                            intent.putExtra("from", "storesearch");
                            intent.putExtra("arr", tempIntent.getIntArrayExtra("arr"));
                            intent.putExtra("distance", tempIntent.getDoubleExtra("distance", 0));

                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "검색할 조건을 선택하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            if (v.getId() == R.id.txt_close) {
                storeSearchPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            if (v.getId() == R.id.linear_refresh) {
                initPanel();
            }
        }
    };

    private final SlidingUpPanelLayout.PanelSlideListener panelSlideListener = new SlidingUpPanelLayout.PanelSlideListener() {
        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            upButton.setRotation(180 * slideOffset);
        }

        @Override
        public void onPanelCollapsed(View panel) {

        }

        @Override
        public void onPanelExpanded(View panel) {

        }

        @Override
        public void onPanelAnchored(View panel) {

        }

        @Override
        public void onPanelHidden(View panel) {

        }
    };

    @Override
    public void onSliderClick(BaseSliderView slider) {
            Intent intent = new Intent(this,StoreSearchActivity.class);
        if (slider.getBundle().get("extra").equals("마포시장")){
            intent.putExtra("market_id", 109);
            intent.putExtra("from", "main");
            intent.putExtra("market_name", "마포시장");
        }
        if (slider.getBundle().get("extra").equals("사러가시장")){
            intent.putExtra("market_id", 160);
            intent.putExtra("from", "main");
            intent.putExtra("market_name", "사러가시장");
        }
        if (slider.getBundle().get("extra").equals("목3동시장")){
            intent.putExtra("market_id", 144);
            intent.putExtra("from", "main");
            intent.putExtra("market_name", "목3동시장");
        }
        if (slider.getBundle().get("extra").equals("신영시장")){
            intent.putExtra("market_id", 146);
            intent.putExtra("from", "main");
            intent.putExtra("market_name", "신영시장");
        }
        startActivity(intent);
    }
}